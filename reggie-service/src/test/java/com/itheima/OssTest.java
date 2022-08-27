package com.itheima;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.itheima.reggie.common.OssTemplate;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class OssTest {
    public static void main(String[] args) throws FileNotFoundException {
        String endpoint = "http://oss-cn-beijing.aliyuncs.com";
        String accessKeyId = "LTAI5tPrFrqdendoF2rd63hB";
        String accessKeySecret = "mbSouVoH7gi7dMrrPgCeY3iLTessdY";

        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        FileInputStream input = new FileInputStream("D:\\upload\\jr.jpg");

        ossClient.putObject("reggie-179-zcy","qinjiu.jpg",input);

        ossClient.shutdown();
    }
}
