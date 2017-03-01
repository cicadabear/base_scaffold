package cc.cicadabear.business.common.utils;


import cc.cicadabear.business.domain.entity.enums.ValiCodeTypeEnum;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Created by Jack on 8/4/16.
 */
public class SMSUtils {


    public static final String DEF_CHATSET = "UTF-8";
    public static final int DEF_CONN_TIMEOUT = 30000;
    public static final int DEF_READ_TIMEOUT = 30000;
    public static String userAgent = "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/29.0.1547.66 Safari/537.36";

    public static final Logger logger = LoggerFactory.getLogger(SMSUtils.class);
    //配置您申请的KEY
    public static final String APPKEY = "*****************************";

    public static final HashMap<Integer, String> TPL = new HashMap<Integer, String>();

    static {
//        $tpl = array(
//            '0' => '17037',//注册模板
//            '1' => '17038',//忘记密码
//            '2' => '17039',//修改密码
//            '3' => '17040',//绑定手机更换
//            '4' => '17041',//设置修改支付密码
//        );
        TPL.put(ValiCodeTypeEnum.register.getType(), "17037");
        TPL.put(ValiCodeTypeEnum.forgetPassword.getType(), "17038");
    }


    //    //1.屏蔽词检查测
//    public static void getRequest1() {
//        String result = null;
//        String url = "http://v.juhe.cn/sms/black";//请求接口地址
//        Banner params = new HashMap();//请求参数
//        params.put("word", "");//需要检测的短信内容，需要UTF8 URLENCODE
//        params.put("key", APPKEY);//应用APPKEY(应用详细页查询)
//        try {
//            result = net(url, params, "GET");
//            JSONObject object = new JSONObject(result);
//            if (object.getInt("error_code") == 0) {
//                System.out.println(object.get("result"));
//            } else {
//                System.out.println(object.get("error_code") + ":" + object.get("reason"));
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
    public static boolean sendCode(String mobile, String code, Integer type) {
        if (TPL.get(type) == null) {
            System.out.println("不支持的验证码类型");
            return false;
        }
        String result = null;
        String url = "http://v.juhe.cn/sms/send";//请求接口地址
        Map params = new HashMap();//请求参数
        params.put("mobile", mobile);//接收短信的手机号码
        params.put("tpl_id", TPL.get(type));//短信模板ID，请参考个人中心短信模板设置
        params.put("tpl_value", URLEncoder.encode("#code#=" + code));//变量名和变量值对。如果你的变量名或者变量值中带有#&=中的任意一个特殊符号，请先分别进行urlencode编码后再传递，<a href="http://www.juhe.cn/news/index/id/50" target="_blank">详细说明></a>
        params.put("key", APPKEY);//应用APPKEY(应用详细页查询)
        params.put("dtype", "");//返回数据的格式,xml或json，默认json

        try {
//            result = net(url, params, "GET");
            result = "{\"reason\":\"操作成功\",\"result\":{\"sid\":\"59106102250126001885\",\"fee\":1,\"count\":1},\"error_code\":0}";
            JSONObject object = JSONObject.parseObject(result);

            if (object.getInteger("error_code") == 0) {
                System.out.println(object.get("result"));
                return true;
            } else {
                System.out.println(object.get("error_code") + ":" + object.get("reason"));
                logger.error(object.get("error_code") + ":" + object.get("reason"));
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.toString());
            return false;
        }

    }

//    //2.发送短信
//    public static void getRequest2() {
//        String result = null;
//        String url = "http://v.juhe.cn/sms/send";//请求接口地址
//        Banner params = new HashMap();//请求参数
//        params.put("mobile", "");//接收短信的手机号码
//        params.put("tpl_id", "");//短信模板ID，请参考个人中心短信模板设置
//        params.put("tpl_value", "");//变量名和变量值对。如果你的变量名或者变量值中带有#&=中的任意一个特殊符号，请先分别进行urlencode编码后再传递，<a href="http://www.juhe.cn/news/index/id/50" target="_blank">详细说明></a>
//        params.put("key", APPKEY);//应用APPKEY(应用详细页查询)
//        params.put("dtype", "");//返回数据的格式,xml或json，默认json
//
//        try {
//            result = net(url, params, "GET");
//            JSONObject object = new JSONObject(result);
//            if (object.getInt("error_code") == 0) {
//                System.out.println(object.get("result"));
//            } else {
//                System.out.println(object.get("error_code") + ":" + object.get("reason"));
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }


    public static void main(String[] args) {

    }

    /**
     * @param strUrl 请求地址
     * @param params 请求参数
     * @param method 请求方法
     * @return 网络请求字符串
     * @throws Exception
     */
    public static String net(String strUrl, Map params, String method) throws Exception {
        HttpURLConnection conn = null;
        BufferedReader reader = null;
        String rs = null;
        try {
            StringBuffer sb = new StringBuffer();
            if (method == null || method.equals("GET")) {
                strUrl = strUrl + "?" + urlencode(params);
            }
            URL url = new URL(strUrl);
            conn = (HttpURLConnection) url.openConnection();
            if (method == null || method.equals("GET")) {
                conn.setRequestMethod("GET");
            } else {
                conn.setRequestMethod("POST");
                conn.setDoOutput(true);
            }
            conn.setRequestProperty("user-agent", userAgent);
            conn.setUseCaches(false);
            conn.setConnectTimeout(DEF_CONN_TIMEOUT);
            conn.setReadTimeout(DEF_READ_TIMEOUT);
            conn.setInstanceFollowRedirects(false);
            conn.connect();
            if (params != null && method.equals("POST")) {
                try {
                    DataOutputStream out = new DataOutputStream(conn.getOutputStream());
                    out.writeBytes(urlencode(params));
                } catch (Exception e) {
                    // TODO: handle exception
                }
            }
            InputStream is = conn.getInputStream();
            reader = new BufferedReader(new InputStreamReader(is, DEF_CHATSET));
            String strRead = null;
            while ((strRead = reader.readLine()) != null) {
                sb.append(strRead);
            }
            rs = sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                reader.close();
            }
            if (conn != null) {
                conn.disconnect();
            }
        }
        return rs;
    }

    //将map型转为请求参数型
    public static String urlencode(Map<String, Object> data) {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry i : data.entrySet()) {
            try {
                sb.append(i.getKey()).append("=").append(URLEncoder.encode(i.getValue() + "", "UTF-8")).append("&");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }



}
