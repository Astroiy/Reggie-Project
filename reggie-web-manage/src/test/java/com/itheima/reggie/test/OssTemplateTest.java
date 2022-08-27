package com.itheima.reggie.test;

import com.itheima.reggie.common.OssTemplate;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

@SpringBootTest
@Slf4j
public class OssTemplateTest {
    @Autowired
    private OssTemplate ossTemplate;

    @Test
    public void testStartup(){
        log.info("成功启动测试");
    }

    @Test
    public void testOssTemplate() throws FileNotFoundException {
        String filePath = ossTemplate.upload("jr.jpg", new FileInputStream("D:\\upload\\jr.jpg"));

        log.info("文件上传完毕之后的路径{}",filePath);

    }
}
