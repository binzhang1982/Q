package com.cn.zbin.management.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cn.zbin.management.dto.SlideMasterInfo;
import com.cn.zbin.management.dto.SlideMasterInfoExample;
import com.cn.zbin.management.mapper.SlideMasterInfoMapper;

@Service
public class SlideService {

	@Autowired
	private SlideMasterInfoMapper slideMasterInfoMapper;
	
	public List<SlideMasterInfo> getAllSlides() {
		List<SlideMasterInfo> ret = new ArrayList<SlideMasterInfo>();
		SlideMasterInfoExample example = new SlideMasterInfoExample();
		example.createCriteria();
		ret = slideMasterInfoMapper.selectByExample(example);
		return ret;
	}
}
