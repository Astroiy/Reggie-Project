package com.itheima.reggie;

import com.itheima.reggie.common.SmsTemplate;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
public class TestSmsTemplate {

    @Autowired
    private SmsTemplate smsTemplate;

    @Test
    public void testFileUpload(){
        smsTemplate.sendSms("13250817035","123456");
    }
}
