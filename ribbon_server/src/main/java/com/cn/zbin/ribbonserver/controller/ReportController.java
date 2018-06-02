package com.cn.zbin.ribbonserver.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cn.zbin.ribbonserver.service.ReportService;

@RestController
@RequestMapping("rpt")
public class ReportController {
	@Autowired
	ReportService reportService;

}
