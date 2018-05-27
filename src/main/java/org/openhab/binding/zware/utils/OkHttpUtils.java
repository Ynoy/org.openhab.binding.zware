package org.openhab.binding.zware.utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.openhab.binding.zware.internal.ZWareBindingConstants;
import org.openhab.binding.zware.internal.ZWareConfiguration;

import okhttp3.Call;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Http Request Util with cookies!!!
 *
 * @author: Honey
 * @date: 2018年5月24日
 * @tags:
 * @email: xy410257@163.com
 */
public class OkHttpUtils {

    public static ZWareConfiguration config;

    // 初始化Cookie管理器
    static CookieJar cookieJar = new CookieJar() {
        // Cookie缓存区
        private final Map<String, List<Cookie>> cookiesMap = new HashMap<String, List<Cookie>>();

        @Override
        public void saveFromResponse(HttpUrl arg0, List<Cookie> arg1) {
            // TODO Auto-generated method stub
            // 移除相同的url的Cookie
            String host = arg0.host();
            List<Cookie> cookiesList = cookiesMap.get(host);
            if (cookiesList != null) {
                cookiesMap.remove(host);
            }
            // 再重新天添加
            cookiesMap.put(host, arg1);
        }

        @Override
        public List<Cookie> loadForRequest(HttpUrl arg0) {
            // TODO Auto-generated method stub
            List<Cookie> cookiesList = cookiesMap.get(arg0.host());
            // 注：这里不能返回null，否则会报NULLException的错误。
            // 原因：当Request 连接到网络的时候，OkHttp会调用loadForRequest()
            return cookiesList != null ? cookiesList : new ArrayList<Cookie>();
        }
    };

    private static OkHttpClient client = new OkHttpClient.Builder().connectTimeout(15, TimeUnit.SECONDS)
            // 添加OkHttp3的拦截器
            .writeTimeout(20, TimeUnit.SECONDS).readTimeout(20, TimeUnit.SECONDS).cookieJar(cookieJar).build();;

    public static String postRequest(String urlParams, Map<String, String> mapParams) {
        // 创建登陆的表单
        FormBody loginBody = new FormBody.Builder().add("usrname", "sigma").add("passwd", "sigmadesigns").build();// 账号密码自己填

        Request loginRequest = new Request.Builder().url(ZWareBindingConstants.Host + ZWareBindingConstants.URL_LOGIN)
                .post(loginBody).build();
        // 获取需要提交的CookieStr
        StringBuilder cookieStr = new StringBuilder();
        // 从缓存中获取Cookie
        List<Cookie> cookies = client.cookieJar().loadForRequest(loginRequest.url());
        System.out.println(cookies);
        if (cookies.isEmpty()) {
            System.out.println("cookies==null");
            // 上传
            Call loginCall = client.newCall(loginRequest);

            try {
                // 非异步执行
                Response loginResponse = loginCall.execute();
                // 测试是否登陆成功
                System.out.println(loginResponse.body().string());
                // 获取返回数据的头部
                Headers headers = loginResponse.headers();
                HttpUrl loginUrl = loginRequest.url();
                // 获取头部的Cookie,注意：可以通过Cooke.parseAll()来获取
                cookies = Cookie.parseAll(loginUrl, headers);
                // 防止header没有Cookie的情况
                if (cookies != null) {
                    // 存储到Cookie管理器中
                    client.cookieJar().saveFromResponse(loginUrl, cookies);// 这样就将Cookie存储到缓存中了
                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }
        cookies = client.cookieJar().loadForRequest(loginRequest.url());

        // 将Cookie数据弄成一行
        for (Cookie cookie : cookies) {
            cookieStr.append(cookie.name()).append("=").append(cookie.value() + ";");
        }

        // 设置提交的请求
        FormBody.Builder builder = new FormBody.Builder();
        // 遍历集合
        for (String key : mapParams.keySet()) {
            builder.add(key, mapParams.get(key));
        }
        Request attentionRequest = new Request.Builder().url(urlParams).header("Cookie", cookieStr.toString())
                .post(builder.build()).build();
        Call attentionCall = client.newCall(attentionRequest);
        try {
            // 连接网络
            Response attentionResponse = attentionCall.execute();
            if (attentionResponse.isSuccessful()) {
                // 获取返回的数据
                String data = attentionResponse.body().string();
                // 测试
                System.out.println(data);
                return data;

            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }
}