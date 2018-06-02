package com.cn.zbin.store.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cn.zbin.store.dto.SlideMasterInfo;
import com.cn.zbin.store.dto.SlideMasterInfoExample;
import com.cn.zbin.store.mapper.SlideMasterInfoMapper;

@Service
public class SlideService {

	@Autowired
	private SlideMasterInfoMapper slideMasterInfoMapper;
	
	public List<SlideMasterInfo> getSildeList() {
		List<SlideMasterInfo> ret = new ArrayList<SlideMasterInfo>();
		SlideMasterInfoExample example = new SlideMasterInfoExample();
		example.createCriteria();
		ret = slideMasterInfoMapper.selectByExample(example);
		return ret;
	}
}
