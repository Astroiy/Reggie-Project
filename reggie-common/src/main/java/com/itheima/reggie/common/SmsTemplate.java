package com.itheima.reggie.common;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix="reggie.sms")
public class SmsTemplate {
    private String key;
    private String secret;
    private String signName;
    private String templateCode;

    public void sendSms(String phoneNumbers,String code){
        System.setProperty("sun.net.client.defaultConnectTimeout","10000");
        System.setProperty("sun.net.client.defaultReadTimeout","10000");

        try{
            IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou",key,secret);
            DefaultProfile.addEndpoint("cn-hangzhou","cn-hangzhou","Dysmsapi","dysmsapi.aliyuncs.com");
            IAcsClient acsClient = new DefaultAcsClient(profile);

            SendSmsRequest request = new SendSmsRequest();
            request.setPhoneNumbers(phoneNumbers);
            request.setSignName(signName);
            request.setTemplateCode(templateCode);
            request.setTemplateParam("{\"code\":\"" + code + "\"}");
            SendSmsResponse sendSmsResponse = acsClient.getAcsResponse(request);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
