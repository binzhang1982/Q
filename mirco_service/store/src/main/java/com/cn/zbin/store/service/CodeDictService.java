package com.cn.zbin.store.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cn.zbin.store.dto.CodeDictInfo;
import com.cn.zbin.store.dto.CodeDictInfoExample;
import com.cn.zbin.store.mapper.CodeDictInfoMapper;
import com.cn.zbin.store.utils.Utils;

@Service
public class CodeDictService {
	@Autowired
	private CodeDictInfoMapper codeDictInfoMapper;
	
	public List<CodeDictInfo> getCodeDicts(String codeCate) {
		CodeDictInfoExample exam_cd = new CodeDictInfoExample();
		exam_cd.createCriteria().andCodecateEqualTo(codeCate);
		List<CodeDictInfo> ret = codeDictInfoMapper.selectByExample(exam_cd);
		if (!Utils.listNotNull(ret)) {
			ret = new ArrayList<CodeDictInfo>();
		}
		return ret;
	}
}
