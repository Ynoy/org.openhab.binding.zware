package org.openhab.binding.zware.utils;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

//import javax.servlet.http.HttpServletRequest;

//import com.alibaba.fastjson.JSONObject;

public class HttpUtils {

    /**
     * 发送get请求图片
     *
     * @param url : 发送目标地址
     * @param params : 传递参数，这里面需要注意每个参数间用&隔开。例如:a=1&b=2
     * @param headers : 需要设置的发送头部信息,如果没有设置为null
     * @return
     */
    // public static Map<String, String> sendGetPic(String url, String params, Map<String, String> headers) {
    // // 返回结果，包含：data和code
    // Map<String, String> map = new HashMap<String, String>();
    // // 数据结果
    // String result = "";
    // BufferedReader in = null;
    // // 要发送的url地址
    // String sendUrl = "";
    // try {
    // // 如果不存在参数，不作处理。如果存在参数需要加？和&
    // if ("".equals(params)) {
    // sendUrl = url;
    // } else {
    // sendUrl = url + "?" + params;
    // }
    // // 设置和目标URL的连接
    // URL realUrl = new URL(sendUrl);
    // HttpURLConnection connection = (HttpURLConnection) realUrl.openConnection();
    // // 设置请求属性
    // if (headers != null && !"".equals(headers) && !headers.isEmpty()) {
    // for (java.util.Map.Entry<String, String> each : headers.entrySet()) {
    // connection.setRequestProperty(each.getKey(), each.getValue());
    // }
    // }
    // // 开始连接
    // connection.connect();
    // // 返回的httpCode
    // int code = connection.getResponseCode();
    // try {
    // // 通过输入流获取图片数据
    // InputStream inStream = connection.getInputStream();
    // // 得到图片的二进制数据，以二进制封装得到数据，具有通用性
    //
    // ByteArrayOutputStream outStream = new ByteArrayOutputStream();
    // // 创建一个Buffer字符串
    // byte[] buffer = new byte[1024];
    // // 每次读取的字符串长度，如果为-1，代表全部读取完毕
    // int len = 0;
    // // 使用一个输入流从buffer里把数据读取出来
    // while ((len = inStream.read(buffer)) != -1) {
    // // 用输出流往buffer里写入数据，中间参数代表从哪个位置开始读，len代表读取的长度
    // outStream.write(buffer, 0, len);
    // }
    // // 关闭输入流
    // inStream.close();
    // // 把outStream里的数据写入内存
    // byte[] data = outStream.toByteArray();
    //
    // // new一个文件对象用来保存图片，默认保存当前工程根目录
    // File imageFile = new File("/Users/lijianwen/Desktop/a/mu1.jpg");
    // // 创建输出流
    // FileOutputStream outStreamFile = new FileOutputStream(imageFile);
    // // 写入数据
    // outStreamFile.write(data);
    // // 关闭输出流
    // outStreamFile.close();
    // } catch (IOException e) {
    // System.out.println("读取输入流失败，可能并没有返回信息,例如404");
    // e.printStackTrace();
    // }
    // map.put("code", String.valueOf(code));
    // map.put("data", result);
    // } catch (Exception e) {
    // // TODO: handle exception
    // e.printStackTrace();
    // } finally {
    // // 尝试关闭输入流
    // try {
    // if (in != null) {
    // in.close();
    // }
    // } catch (Exception e2) {
    // e2.printStackTrace();
    // }
    // }
    // return map;
    // }

    public static String httpPost(String url, Map<String, String> parms) {
        try {
            URL postUrl = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) postUrl.openConnection();
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setRequestMethod("POST");
            connection.connect();
            DataOutputStream out = new DataOutputStream(connection.getOutputStream());
            out.writeBytes(mapToLink(parms));
            out.flush();
            out.close();
            InputStreamReader in = new InputStreamReader(connection.getInputStream(), "UTF-8");
            BufferedReader reader = new BufferedReader(in);
            String line = reader.readLine();
            reader.close();
            connection.disconnect();
            return line;
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ProtocolException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return "Error";
    }

    // public static String getParameters(HttpServletRequest request) throws IOException {
    // StringBuffer buffer = new StringBuffer("");
    // InputStream is = request.getInputStream();
    // byte[] buf = new byte[1024];
    // int len = -1;
    // while ((len = is.read(buf)) != -1) {
    // buffer.append(new String(buf, 0, len, "utf-8"));
    // }
    // return buffer.toString();
    // }
    //
    public static String mapToLink(Map<String, String> map) {
        if (map == null) {
            return "";
        }
        List<String> keys = new ArrayList<String>(map.keySet());
        Collections.sort(keys);
        String prestr = "";
        for (int i = 0; i < keys.size(); i++) {
            String key = keys.get(i);
            String value = map.get(key);
            if (StringUtils.isNotEmpty(value)) {// 去除空值
                prestr = prestr + "&" + key + "=" + value;
            }
        }
        return prestr.substring(1);
    }
    // /**
    // * 发送POST方法的请求
    // *
    // * @param url : 请求发送的URL
    // * @param params : 请求参数
    // * @param headers : 请求头部内容
    // * @return
    // */
    // public static Map<String, String> sendPost(String url, String params, Map<String, String> headers) {
    // // 返回结果，包含：data和code
    // Map<String, String> map = new HashMap<String, String>();
    // // 数据结果
    // String result = "";
    // // 要发送的url地址
    // BufferedReader in = null;
    // PrintWriter out = null;
    // try {
    // URL realUrl = new URL(url);
    // // 设置和目标URL的连接
    // HttpURLConnection connection = (HttpURLConnection) realUrl.openConnection();
    // // 设置请求属性
    // if (headers != null && !"".equals(headers) && !headers.isEmpty()) {
    // for (java.util.Map.Entry<String, String> each : headers.entrySet()) {
    // connection.setRequestProperty(each.getKey(), each.getValue());
    // }
    // }
    // // 发送POST请求必须设置如下两行
    // connection.setDoOutput(true);
    // connection.setDoInput(true);
    // // 获取URLConnection对象对应的输出流
    // out = new PrintWriter(connection.getOutputStream());
    // // 发送请求参数
    // out.print(params);
    // // flush输出流的缓冲
    // out.flush();
    // int code = connection.getResponseCode();
    // Map<String, List<String>> responseHeader = connection.getHeaderFields();
    // for (Map.Entry<String, List<String>> entry : responseHeader.entrySet()) {
    // // System.out.println("Key : " + entry.getKey() +
    // // " ,Value : " + entry.getValue());
    // if ("Set-Cookie".equals(entry.getKey())) {
    // System.out.println("获得cookie " + entry.getValue());
    // map.put("cookie", JSONObject.toJSONString(entry.getValue()));
    // }
    // }
    // // Map<String, List<String>> map = conn.getHeaderFields();
    // // List<String> server = map.get("Server");
    // try {
    // in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
    // String line;
    // while ((line = in.readLine()) != null) {
    // result += line;
    // }
    // } catch (IOException e) {
    // System.out.println("读取输入流失败，可能并没有返回信息,例如404");
    // e.printStackTrace();
    // }
    // map.put("data", result);
    // map.put("code", String.valueOf(code));
    // connection.disconnect();
    // } catch (Exception e) {
    // // TODO: handle exception
    // e.printStackTrace();
    // } finally {
    // // 尝试关闭输入流
    // try {
    // if (out != null) {
    // out.close();
    // }
    // if (in != null) {
    // in.close();
    // }
    // } catch (Exception e2) {
    // e2.printStackTrace();
    // }
    // }
    // return map;
    // }
    //
    // /**
    // * 发送DELE方法的请求
    // *
    // * @param url : 请求发送的URL
    // * @param params : 请求参数
    // * @param headers : 请求头部内容
    // * @return
    // */
    // public static Map<String, String> sendDelete(String url, Map<String, String> headers) {
    // // 返回结果，包含：data和code
    // Map<String, String> map = new HashMap<String, String>();
    // // 数据结果
    // String result = "";
    // BufferedReader in = null;
    // try {
    // URL realUrl = new URL(url);
    // HttpURLConnection connection = (HttpURLConnection) realUrl.openConnection();
    // connection.setDoOutput(true);
    // connection.setRequestMethod("DELETE");
    // // 设置请求属性
    // if (headers != null && !"".equals(headers) && !headers.isEmpty()) {
    // for (java.util.Map.Entry<String, String> each : headers.entrySet()) {
    // connection.setRequestProperty(each.getKey(), each.getValue());
    // }
    // }
    // connection.connect();
    // int code = connection.getResponseCode();
    // try {
    // in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
    // String line;
    // while ((line = in.readLine()) != null) {
    // result += line;
    // }
    // } catch (IOException e) {
    // System.out.println("读取输入流失败，可能并没有返回信息,例如404");
    // e.printStackTrace();
    // }
    // map.put("data", result);
    // map.put("code", String.valueOf(code));
    // connection.disconnect();
    // } catch (Exception e) {
    // // TODO Auto-generated catch block
    // e.printStackTrace();
    // } finally {
    // if (in != null) {
    // try {
    // in.close();
    // } catch (IOException e) {
    // // TODO Auto-generated catch block
    // e.printStackTrace();
    // }
    // }
    // }
    // return map;
    // }
    //
    // /**
    // * 发送put方法的请求
    // * 需要导入httpclient和httpcore
    // *
    // * @param url : 请求发送的URL
    // * @param params : 请求参数
    // * @param headers : 请求头部内容
    // * @return
    // */
    // public static Map<String, String> sendPut(String strUrl, String param, Map<String, String> headers) {
    // Map<String, String> map = new HashMap<>();
    // CloseableHttpClient httpclient = HttpClients.createDefault();
    // StringBuffer jsonString = new StringBuffer();
    // try {
    // final HttpPut put = new HttpPut(strUrl);
    // // 设置请求属性
    // if (headers != null && !"".equals(headers) && !headers.isEmpty()) {
    // for (java.util.Map.Entry<String, String> each : headers.entrySet()) {
    // put.setHeader(each.getKey(), each.getValue());
    // }
    // }
    // put.setEntity(new StringEntity(param, "UTF-8"));
    // CloseableHttpResponse response1 = httpclient.execute(put);
    // map.put("code", String.valueOf(response1.getStatusLine().getStatusCode()));
    // try {
    // HttpEntity entity1 = response1.getEntity();
    // BufferedReader br = new BufferedReader(new InputStreamReader(entity1.getContent()));
    // String line;
    // while ((line = br.readLine()) != null) {
    // jsonString.append(line);
    // }
    // map.put("data", jsonString.toString());
    // EntityUtils.consume(entity1);
    // } catch (Exception e) {
    // e.printStackTrace();
    // } finally {
    // response1.close();
    // }
    // } catch (Exception e) {
    // e.printStackTrace();
    // }
    // return map;
    // }
}