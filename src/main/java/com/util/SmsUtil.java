package com.util;

import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import org.json.JSONObject;

/**阿里云发送短信*/
public class SmsUtil {
    /**发送短信*/
    public Integer SendMsg(String PhoneNumbers, String TemplateParam,Integer type) {
        DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", "AccessKey ID", "AccessKeySecret");
        IAcsClient client = new DefaultAcsClient(profile);
        CommonRequest request = new CommonRequest();
        //request.setProtocol(ProtocolType.HTTPS);
        request.setMethod(MethodType.POST);
        request.setDomain("dysmsapi.aliyuncs.com");
        request.setVersion("2017-05-25");
        request.setAction("SendSms");
        request.putQueryParameter("RegionId", "cn-hangzhou");
        request.putQueryParameter("PhoneNumbers", PhoneNumbers);
        request.putQueryParameter("SignName", "签名名称");
        if(type == 0){//发送注册验证码
            request.putQueryParameter("TemplateCode", "模板");
        }else if(type == 1){//发送重置密码验证码
            request.putQueryParameter("TemplateCode", "模板");
        }else if (type == 2){//发送更换手机号验证码
            request.putQueryParameter("TemplateCode", "模板");
        }
        request.putQueryParameter("TemplateParam", "{\"code\":\""+TemplateParam+"\"}");
        CommonResponse response=null;
        try {
            response = client.getCommonResponse(request);
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            e.printStackTrace();
        }
        JSONObject result = new JSONObject(response.getData());
        if(result.getString("Code").equals("OK")){
            return 1;
        }
        if(result.getString("Code").equals("isv.MOBILE_NUMBER_ILLEGAL")){
            return 2;//非法手机号
        }
        return 0;
    }
}
