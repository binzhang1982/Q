package com.cn.zbin.ribbonserver.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cn.zbin.ribbonserver.service.ManagementService;

@RestController
public class ManagementController {
    @Autowired
    ManagementService managementService;
    
    @GetMapping(value = "/slides/all")
    public String getAllSlides() {
    	return managementService.getAllSlides();
    }
}
