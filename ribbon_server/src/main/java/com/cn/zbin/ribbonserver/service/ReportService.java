package com.cn.zbin.ribbonserver.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ReportService {
    @Autowired
    RestTemplate restTemplate;

}
