package com.cn.zbin.management.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cn.zbin.management.dto.MasterCity;
import com.cn.zbin.management.dto.MasterCityExample;
import com.cn.zbin.management.dto.MasterProvince;
import com.cn.zbin.management.dto.MasterProvinceExample;
import com.cn.zbin.management.mapper.MasterCityMapper;
import com.cn.zbin.management.mapper.MasterProvinceMapper;
import com.cn.zbin.management.utils.Utils;

@Service
public class MasterService {
	@Autowired
	private MasterCityMapper masterCityMapper;
	@Autowired
	private MasterProvinceMapper masterProvinceMapper;
	
	public List<MasterCity> getCityList(
			String strSearch, String strProvinceCode, String strCityCode,
			Integer offset, Integer limit) {
		MasterCityExample exam_mc = new MasterCityExample();
		exam_mc.createCriteria();
		if (strSearch != null) exam_mc.getOredCriteria().get(0).andCityNameLike(strSearch);
		if (strProvinceCode != null) exam_mc.getOredCriteria().get(0)
										.andProvinceCodeEqualTo(strProvinceCode);
		if (strCityCode != null) exam_mc.getOredCriteria().get(0).andCityCodeEqualTo(strCityCode);
		
		List<MasterCity> ret = masterCityMapper.selectOnePageByExample(
				exam_mc, offset, limit, "city_code asc");
		if (!Utils.listNotNull(ret)) ret = new ArrayList<MasterCity>();
		
		return ret;
	}

	public List<MasterProvince> getProvinceList(String strSearch, 
			String strProvinceCode, Integer offset, Integer limit) {
		MasterProvinceExample exam_mp = new MasterProvinceExample();
		exam_mp.createCriteria();
		if (strSearch != null) exam_mp.getOredCriteria().get(0).andProvinceNameLike(strSearch);
		if (strProvinceCode != null) exam_mp.getOredCriteria().get(0)
										.andProvinceCodeEqualTo(strProvinceCode);
		
		List<MasterProvince> ret = masterProvinceMapper.selectOnePageByExample(
				exam_mp, offset, limit, "province_code asc");
		if (!Utils.listNotNull(ret)) ret = new ArrayList<MasterProvince>();
		
		return ret;
	}
}
