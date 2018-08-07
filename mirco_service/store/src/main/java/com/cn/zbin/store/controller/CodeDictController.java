package com.cn.zbin.store.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cn.zbin.store.dto.CodeDictInfo;
import com.cn.zbin.store.service.CodeDictService;

@RestController
@RequestMapping("code")
public class CodeDictController {
	protected static final Logger logger = LoggerFactory.getLogger(CodeDictController.class);
	@Autowired
	private CodeDictService codeDictService;
	
	@RequestMapping(value = "/list", 
			produces = {"application/json;charset=UTF-8"}, 
			method = { RequestMethod.GET })
	public List<CodeDictInfo> getCodeDicts(@RequestParam("cate") String codeCate) {
		logger.info("get api: /code/list || cate: " + codeCate);
		return codeDictService.getCodeDicts(codeCate);
	}
}
