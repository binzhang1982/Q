USE [POSTransactionCentralDB_Simphony]
GO

/****** Object:  StoredProcedure [dbo].[sp_BOH_POSLog_Sync_Core_Simphony]    Script Date: 8/21/2018 1:38:31 PM ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE procedure [dbo].[sp_BOH_POSLog_Sync_Core_Simphony] 

as 
begin
declare 
@Max_datetime datetime,
@NonSales_MAXDatetime datetime,
@Operations_MAXDatetime datetime,
@CloseDatetime datetime,
@TransCheckStartTime datetime,
@NonSalesEndTime datetime,
@OperationEndTime datetime,
@PoslogControlCnt int,
@PoslogNosalesControlCnt int


/**** create temp tables ****/
create table #TempTable(
	BatchID varchar(60),
	GuestCheckID int, 
	Data_Generation_Flag varchar(1), 
	Error_Message varchar(50)
)
create table #EC_Tables_Temp
(
	LocationID int
)


set @PoslogControlCnt = 18000
set @PoslogNosalesControlCnt = 500

-- **** start to get EC LocationID ****
BEGIN
	delete from #EC_Tables_Temp

	insert into #EC_Tables_Temp(LocationID)
	SELECT
			xdr.XferLocationID AS LocationID
		FROM
			XFER_DB_RVC (nolock) xdr
		LEFT JOIN V_HIERARCHY (nolock) vh ON xdr.LocHierStrucID = vh.HierStrucID
		LEFT JOIN GlobalStore_Mapping (nolock) gm ON vh.ObjectNumber = gm.GlobalStoreNumber
		WHERE
			gm.sourceCode = 'EC'
END
-- **** end to get EC LocationID ****


-- **** start to insert poslog_control ****
BEGIN   
	select
		@Max_datetime= max(pc.lastTransDatetime)
	from
		poslog_control (nolock) pc
		left join poslog_roastery(nolock) pr on 
			pc.Global_StoreID = pr.storeid
	where
		pr.storeid is null

	set @TransCheckStartTime = dateadd(d,-7,getdate())

	BEGIN TRANSACTION POS_CONTROL
		create table #TempTablePosLogControl(
			GuestCheckID int, 
			LocationID int, 
			LastTransDatetime datetime,
			Data_Generation_Flag varchar(1),
			Error_Message varchar(50),
			insert_date_time datetime,
			closedatetime datetime,
			generation_count int
		)
		
		delete from #TempTablePosLogControl
		if @Max_datetime is not null
			insert into #TempTablePosLogControl (GuestCheckID,LocationID,LastTransDatetime,
				Data_Generation_Flag,Error_Message,insert_date_time,closedatetime,generation_count)
			select 
				top(@PoslogControlCnt) 
				gc.GuestCheckID,
				gc.LocationID,
				gc.LastTransDatetime,
				'N' as Data_Generation_Flag,
				'' as Error_Message,
				getdate() as insert_date_time,
				gc.closedatetime,
				0 as generation_count
			from
				GUEST_CHECK (nolock) gc
			where 
				gc.lastTransDateTime >= @Max_datetime
				and gc.closeDatetime is not null
			order by
				gc.lastTransDateTime
		else
			insert into #TempTablePosLogControl (GuestCheckID,LocationID,LastTransDatetime,
				Data_Generation_Flag,Error_Message,insert_date_time,closedatetime,generation_count)
			select 
				top(@PoslogControlCnt) 
				gc.GuestCheckID,
				gc.LocationID,
				gc.LastTransDatetime,
				'N' as Data_Generation_Flag,
				'' as Error_Message,
				getdate() as insert_date_time,
				gc.closedatetime,
				0 as generation_count
			from
				GUEST_CHECK (nolock) gc
			where 
				gc.closeDatetime is not null
			order by
				gc.lastTransDateTime

		insert into poslog_control(
			BatchID,GuestCheckID,StoreID,Global_StoreID,LocationID,LastTransDatetime,
			Data_Generation_Flag,Error_Message,insert_date_time,generation_count)
		select 
			'S' +  
				case 
					when gm.storenumber is not null then
						convert(varchar,gm.storenumber)
					else
						convert(varchar,isnull(gl_store.ObjectNumber,''))
				end + 'ARTSPOSLOG' + 
				convert(varchar(12),ttpc.closedatetime, 23) + 'T' + 
				convert(char(8), getdate(), 108) as BatchID,
			ttpc.GuestCheckID,
			case 
				when gm.storenumber is not null then
					gm.storenumber
				else
					gl_store.ObjectNumber
			end as storeid,
			gl_store.ObjectNumber as global_storeid,
			ttpc.LocationID,
			ttpc.LastTransDatetime,
			ttpc.Data_Generation_Flag,
			ttpc.Error_Message,
			ttpc.insert_date_time,
			ttpc.generation_count
		from
			#TempTablePosLogControl ttpc
			left join poslog_roastery (nolock) pr on 
				ttpc.locationID = pr.locationID
			left join #EC_Tables_Temp ett on 
				ttpc.locationID = ett.LocationID
			left join (
				select xdr.XferLocationID as XferLocationID, 
						max(vh.ObjectNumber) as ObjectNumber
				from XFER_DB_RVC xdr (nolock),
						V_HIERARCHY vh (nolock)
				where xdr.LocHierStrucID = vh.HierStrucID
				group by xdr.XferLocationID
			) gl_store on 
				ttpc.locationID = gl_store.XferLocationID
			left join GlobalStore_Mapping gm (nolock) on
				gm.GlobalStoreNumber = gl_store.ObjectNumber
		where
			ttpc.GuestCheckID not in (
				select
					GuestCheckID
				from
					poslog_control (nolock)
				where
					lastTransDatetime >= @TransCheckStartTime
			)
			and pr.locationID is null
			and ett.LocationID is null

		drop table #TempTablePosLogControl
	COMMIT TRANSACTION POS_CONTROL
END
-- **** end to insert poslog_control ****

-- **** start to insert poslog_nonsales_control ****
BEGIN
	select
		@NonSales_MAXDatetime = max(pnc.TransDateTime)
	from
		poslog_nonsales_control (nolock) pnc
		left join poslog_roastery (nolock) pr on 
			pnc.locationID = pr.locationID
	where
		pr.locationID is null
		
	set @NonSalesEndTime = dateadd(s,-1,getdate())

	BEGIN TRANSACTION POS_NONSALES_CONTROL
		create table #TempTablePosLogNosalesControl(
			NonSalesID bigint,
			LocationID int,
			RevenueCenterID int,
			BusinessDate smalldatetime,
			NonSalesTransType int,
			uwsID int,
			Amount decimal(12, 2),
			GuestCheckID int,
			TransDateTime datetime,
			CheckNum int,
			employeeShiftNum int,
			EmployeeID bigint,
			TenderMediaID int,
			ReasonCodeID int
		)
		
		delete from #TempTablePosLogNosalesControl
		if @NonSales_MAXDatetime is not null
			insert into #TempTablePosLogNosalesControl(NonSalesID,LocationID,RevenueCenterID,BusinessDate,
				NonSalesTransType,uwsID,Amount,GuestCheckID,TransDateTime,CheckNum,employeeShiftNum,
				EmployeeID,TenderMediaID,ReasonCodeID)
			select 
				top(@PoslogNosalesControlCnt) 
				nsd.NonSalesID,
				nsd.LocationID,
				nsd.RevenueCenterID,
				nsd.BusinessDate,
				nsd.NonSalesTransType,
				nsd.uwsID,
				nsd.Amount,
				nsd.GuestCheckID,
				nsd.TransDateTime,
				nsd.CheckNum,
				nsd.employeeShiftNum,
				nsd.EmployeeID,
				nsd.TenderMediaID,
				nsd.ReasonCodeID
			from NON_SALES_DETAIL(nolock) nsd
			where 
				nsd.NonSalesTranstype in (3,4)
				and nsd.TransDateTime >= @NonSales_MAXDatetime
				and nsd.TransDateTime <= @NonSalesEndTime
			order by nsd.TransDateTime
		else
			insert into #TempTablePosLogNosalesControl(NonSalesID,LocationID,RevenueCenterID,BusinessDate,
				NonSalesTransType,uwsID,Amount,GuestCheckID,TransDateTime,CheckNum,employeeShiftNum,
				EmployeeID,TenderMediaID,ReasonCodeID)
			select 
				top(@PoslogNosalesControlCnt) 
				nsd.NonSalesID,
				nsd.LocationID,
				nsd.RevenueCenterID,
				nsd.BusinessDate,
				nsd.NonSalesTransType,
				nsd.uwsID,
				nsd.Amount,
				nsd.GuestCheckID,
				nsd.TransDateTime,
				nsd.CheckNum,
				nsd.employeeShiftNum,
				nsd.EmployeeID,
				nsd.TenderMediaID,
				nsd.ReasonCodeID
			from NON_SALES_DETAIL(nolock) nsd
			where nsd.NonSalesTranstype in (3,4)
				and nsd.TransDateTime <= @NonSalesEndTime
			order by nsd.TransDateTime

		insert into poslog_nonsales_control(NonSalesID,LocationID,RevenueCenterID,BusinessDate,
			NonSalesTransType,uwsID,Amount,GuestCheckID,TransDateTime,CheckNum,employeeShiftNum,
			EmployeeID,TenderMediaID,ReasonCodeID)
		select 
			ttplnc.NonSalesID,
			ttplnc.LocationID,
			ttplnc.RevenueCenterID,
			ttplnc.BusinessDate,
			ttplnc.NonSalesTransType,
			ttplnc.uwsID,
			ttplnc.Amount,
			ttplnc.GuestCheckID,
			ttplnc.TransDateTime,
			ttplnc.CheckNum,
			ttplnc.employeeShiftNum,
			ttplnc.EmployeeID,
			ttplnc.TenderMediaID,
			ttplnc.ReasonCodeID
		from #TempTablePosLogNosalesControl ttplnc
			left join poslog_roastery(nolock) pr on 
				ttplnc.locationID = pr.locationID
			left join poslog_nonsales_control(nolock) pnc on
				ttplnc.NonSalesID=pnc.NonSalesID
			left join #EC_Tables_Temp ett on 
				ttplnc.locationID = ett.LocationID
		where pr.locationID is null
			and pnc.NonSalesID is null
			and ett.LocationID is null

		drop table #TempTablePosLogNosalesControl
	COMMIT TRANSACTION POS_NONSALES_CONTROL   
END
-- **** end to insert poslog_nonsales_control ****


-- **** start to insert poslog_operations_control ****
BEGIN
	select
		@Operations_MAXDatetime = max(BusinessDate)
	from
		poslog_operations_control (nolock) poc
		left join poslog_roastery (nolock) pr on
			poc.locationID = pr.locationID
	where
		pr.locationID is null
	
	set @OperationEndTime = dateadd(d,-1,dateadd(hour,-4,getdate()))

	BEGIN TRANSACTION POS_OPERATIONS_CONTROL
		if @Operations_MAXDatetime is not null
			insert into poslog_operations_control
				(OperationsDailyTotalID,LocationID,BusinessDate,NumChecks,CheckOpenCount,CheckOpenTotal,
					CheckClosedCount,CheckClosedTotal,CreditTotal,ItemDiscountTotal,ErrorCorrectCount,
					ErrorCorrectTotal,CumulativeGrandTotal,ManagerVoidCount,ManagerVoidTotal,NetSalesTotal,
					ReturnTotal,ReturnCount,RoundingTotal,ServiceChargeTotal,TaxCollectedTotal,VoidTotal,VoidCount)
			select odt.OperationsDailyTotalID,
				odt.LocationID,
				odt.BusinessDate,
				odt.NumChecks,
				odt.CheckOpenCount,
				odt.CheckOpenTotal,
				odt.CheckClosedCount,
				odt.CheckClosedTotal,
				odt.CreditTotal,
				odt.ItemDiscountTotal,
				odt.ErrorCorrectCount,
				odt.ErrorCorrectTotal,
				odt.CumulativeGrandTotal,
				odt.ManagerVoidCount,
				odt.ManagerVoidTotal,
				odt.NetSalesTotal,
				odt.ReturnTotal,
				odt.ReturnCount,
				odt.RoundingTotal,
				odt.ServiceChargeTotal,
				odt.TaxCollectedTotal,
				odt.VoidTotal,
				odt.VoidCount
			from OPERATIONS_DAILY_TOTAL(nolock) odt
				left join poslog_roastery(nolock) pr on 
					odt.locationID = pr.locationID
				left join poslog_operations_control(nolock) poc on 
					odt.OperationsDailyTotalID = poc.OperationsDailyTotalID
				left join #EC_Tables_Temp ett on 
					odt.locationID = ett.LocationID
			where odt.BusinessDate > @Operations_MAXDatetime 
				and odt.BusinessDate <= @OperationEndTime 
				and pr.locationID is null
				and poc.OperationsDailyTotalID is null
				and ett.LocationID is null
		else
			insert into poslog_operations_control
				(OperationsDailyTotalID,LocationID,BusinessDate,NumChecks,CheckOpenCount,CheckOpenTotal,
					CheckClosedCount,CheckClosedTotal,CreditTotal,ItemDiscountTotal,ErrorCorrectCount,
					ErrorCorrectTotal,CumulativeGrandTotal,ManagerVoidCount,ManagerVoidTotal,NetSalesTotal,
					ReturnTotal,ReturnCount,RoundingTotal,ServiceChargeTotal,TaxCollectedTotal,VoidTotal,VoidCount)
			select odt.OperationsDailyTotalID,
				odt.LocationID,
				odt.BusinessDate,
				odt.NumChecks,
				odt.CheckOpenCount,
				odt.CheckOpenTotal,
				odt.CheckClosedCount,
				odt.CheckClosedTotal,
				odt.CreditTotal,
				odt.ItemDiscountTotal,
				odt.ErrorCorrectCount,
				odt.ErrorCorrectTotal,
				odt.CumulativeGrandTotal,
				odt.ManagerVoidCount,
				odt.ManagerVoidTotal,
				odt.NetSalesTotal,
				odt.ReturnTotal,
				odt.ReturnCount,
				odt.RoundingTotal,
				odt.ServiceChargeTotal,
				odt.TaxCollectedTotal,
				odt.VoidTotal,
				odt.VoidCount
			from OPERATIONS_DAILY_TOTAL(nolock) odt
				left join poslog_roastery(nolock) pr on 
					odt.locationID = pr.locationID
				left join poslog_operations_control(nolock) poc on 
					odt.OperationsDailyTotalID = poc.OperationsDailyTotalID
				left join #EC_Tables_Temp ett on 
					odt.locationID = ett.LocationID
			where odt.BusinessDate <= @OperationEndTime 
				and pr.locationID is null
				and poc.OperationsDailyTotalID is null
				and ett.LocationID is null
	COMMIT TRANSACTION POS_OPERATIONS_CONTROL
END
-- **** end to insert poslog_operations_control ****


-- **** start to check invalid batch records ****
BEGIN
	BEGIN TRANSACTION POS_BATCH_REPROCESS_CONTROL
	
	delete from #TempTable
	insert into #TempTable
	select 
		BatchID,
		GuestCheckID, 
		Data_Generation_Flag, 
		Error_Message
	from dbo.Check_BOH_Pending_Data_Simphony('N')
	
	update pc 
	set pc.BatchID = tt.BatchID,
		pc.Data_Generation_Flag = tt.Data_Generation_Flag,
		pc.Error_Message = tt.Error_Message
	from poslog_control pc
		inner join #TempTable tt on
			tt.GuestCheckID = pc.GuestCheckID
	where pc.Data_Generation_Flag='P'
	
	COMMIT TRANSACTION POS_BATCH_REPROCESS_CONTROL   
END
-- **** end to check invalid batch records ****

drop table #TempTable
drop table #EC_Tables_Temp

end


























































GO

