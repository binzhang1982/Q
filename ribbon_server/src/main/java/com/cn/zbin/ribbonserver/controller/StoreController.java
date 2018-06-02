package com.cn.zbin.ribbonserver.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cn.zbin.ribbonserver.service.StoreService;

@RestController
@RequestMapping("store")
public class StoreController {
    @Autowired
    StoreService storeService;

    @GetMapping(value = "/slide/list")
    public String getSlideList() {
    	return storeService.getSlideList();
    }
}
