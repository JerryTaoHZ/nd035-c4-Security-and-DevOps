package com.example.demo.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestLogController {

    private final static Logger logger = LoggerFactory.getLogger(TestLogController.class);

    @RequestMapping("/log")
    public String testLog() {
        logger.debug("=====测试日志debug级别打印====");
        logger.info("======测试日志info级别打印=====");
        logger.error("=====测试日志error级别打印====");
        logger.warn("======测试日志warn级别打印=====");

        // 可以使用占位符打印出一些参数信息
        String str1 = "str1";
        String str2 = "str2";
        logger.info("======这是用占位符输出的日志 {} {}", str1, str2);

        return "success";
    }
}
