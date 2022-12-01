package com.venkat.http2.service.service;

import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class StatusService {
    public String getStatus(String appCode) {

        return  new Date() + " " + appCode + ": IN-PROGRESS";
    }
}
