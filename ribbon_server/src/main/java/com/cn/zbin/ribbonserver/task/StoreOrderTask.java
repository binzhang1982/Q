package com.cn.zbin.ribbonserver.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.cn.zbin.ribbonserver.service.AsyncService;

@Component
public class StoreOrderTask {
	protected static final Logger logger = LoggerFactory.getLogger(StoreOrderTask.class);

	@Autowired
	private AsyncService asyncService;

	@Scheduled(cron="0 0 0/1 * * ?")
	public void cancelOrderTask() {
		asyncService.executeCancelUnpaidOrderAsync();
	}
	
	@Scheduled(cron="0 0/5 * * * ?")
	public void scanPayOrderTask() {
		asyncService.executeScanPayOrderAsync();
	}

	@Scheduled(cron="0 0 1 * * ?")
	public void commentDefaultOrderTask() {
		asyncService.executeCommentDefaultOrderAsync();
	}

	@Scheduled(cron="0 0 7 * * ?")
	public void dueLeaseOrderTask() {
		asyncService.executDueLeaseOrderAsync();
	}
	
	@Scheduled(cron="0 0 0/1 * * ?")
	public void dueNotifyTask() {
		asyncService.executDueNotifyAsync();
	}
}
