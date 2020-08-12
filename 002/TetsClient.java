package com.cxu.web.controller;

import java.io.*;

public class TetsClient {

    public static  void main(String[] args) throws IOException {

//        //1.打开浏览器
//        CloseableHttpClient httpClient = HttpClients.createDefault();
//        //2.声明get请求
//        HttpPost httpPost = new HttpPost("http://www.962121.net/hmfmstest/shanghaiwuye/maintenancefunds/api/v1/balanceSheetYearById");
//        //3.开源中国为了安全，防止恶意攻击，在post请求中都限制了浏览器才能访问
//        //httpPost.addHeader("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/68.0.3440.106 Safari/537.36");
//        //4.判断状态码
//        List<NameValuePair> parameters = new ArrayList<NameValuePair>(0);
//        parameters.add(new BasicNameValuePair("scope", "project"));
//        parameters.add(new BasicNameValuePair("q", "java"));
//
//        UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(parameters,"UTF-8");
//
//        httpPost.setEntity(formEntity);
//
//        //5.发送请求
//        CloseableHttpResponse response = httpClient.execute(httpPost);
//
//        if(response.getStatusLine().getStatusCode()==200){
//            HttpEntity entity = response.getEntity();
//            String string = EntityUtils.toString(entity, "utf-8");
//            System.out.println(string);
//        }
//        //6.关闭资源
//        response.close();
//        httpClient.close();
        TetsClient.copyFileByBufferStream();



    }

    public static void  copyFileByBufferStream(){
        long  beging  = System.currentTimeMillis();
        try {
            FileInputStream fileInputStream  = new FileInputStream("C:\\Users\\13621\\Desktop\\TempTxt.txt");
            BufferedInputStream bu = new BufferedInputStream(fileInputStream);
            FileOutputStream fileOutputStream = new FileOutputStream("C:\\Users\\13621\\Desktop\\TempTxt1.txt");
            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream);
            int i = 0;
            byte[] byt = new byte[1024*10];
            while ((i = bu.read(byt))!=-1){
                bufferedOutputStream.write(byt,0,i);
            }
            bufferedOutputStream.flush();
            bu.close();  bufferedOutputStream.close();
            long  end = System.currentTimeMillis();
            System.out.println("拷贝文件总耗时为："+ (end-beging)+"毫秒");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
