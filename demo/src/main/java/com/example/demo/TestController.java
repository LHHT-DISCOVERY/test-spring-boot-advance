package com.example.demo;

import com.example.demo.common.event_tracking.AuditEventType;
import com.example.demo.common.event_tracking.AuditLogger;
import com.example.demo.common.event_tracking.AuditStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    private static final Logger LOGGER = LoggerFactory.getLogger(TestController.class);

    @GetMapping(path = "/index")
    public String test() throws InterruptedException {
        LOGGER.info("Info log message");
        AuditLogger.info("USER", AuditEventType.TEST_THU, AuditStatus.SUCCESS, "call API /index");
        return "Hello CHị tưởng";
    }
}
