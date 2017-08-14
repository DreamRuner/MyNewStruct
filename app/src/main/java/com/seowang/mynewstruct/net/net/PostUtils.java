package com.seowang.mynewstruct.net.net;

import com.seowang.mynewstruct.net.domain.RequestVo;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by hm on 2016/3/30.
 *   使用模拟模拟表单的方式记性数据的上传操作
 */
public class PostUtils {

    private static String multipart_form_data = "multipart/form-data";
    private static  String twoHyphens = "--";
    private static  String boundary = "****************fD4fH3gL0hK7aI6";    // 数据分隔符
    private static  String lineEnd = System.getProperty("line.separator");    // The value is "\r\n" in Windows.

    public static InputStream getHttpIOByPost(RequestVo vo){
        HashMap<String,String> params=vo.getParamStringHashMap();
        HashMap<String,File> files=vo.getParamFileHashMap();
        HttpURLConnection conn = null;
        DataOutputStream output = null;
        BufferedReader input = null;
        try {
            URL url = new URL(vo.getBaseUrl());
            conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(120000);
            conn.setDoInput(true);        // 允许输入
            conn.setDoOutput(true);        // 允许输出
            conn.setUseCaches(false);    // 不使用Cache
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Connection", "keep-alive");
            conn.setRequestProperty("Content-Type", multipart_form_data + "; boundary=" + boundary);

            conn.connect();
            output = new DataOutputStream(conn.getOutputStream());

            addImageContent(files, output);    // 添加图片内容

            addFormField(params, output);    // 添加表单字段内容

            output.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);// 数据结束标志
            output.flush();

            int code = conn.getResponseCode();
            if(code != 200) {
                System.out.println("网络的返回码：："+code);
//                throw new RuntimeException("请求‘" + actionUrl +"’失败！");
            }
            InputStream is = conn.getInputStream();
            return is;

//            input = new BufferedReader(new InputStreamReader(conn.getInputStream()));
//            StringBuilder response = new StringBuilder();
//            String oneLine;
//            while((oneLine = input.readLine()) != null) {
//                response.append(oneLine + lineEnd);
//            }
//
//            return response.toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            // 统一释放资源
            try {
                if(output != null) {
                    output.close();
                }
                if(input != null) {
                    input.close();
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            if(conn != null) {
                conn.disconnect();
            }
        }

    }


    /*
     * 构建表单字段内容，格式请参考HTTP 协议格式（用FireBug可以抓取到相关数据）。(以便上传表单相对应的参数值)
     * 格式如下所示：
     * --****************fD4fH3hK7aI6
     * Content-Disposition: form-data; name="action"
     * // 一空行，必须有
     * upload
     */
    private static void addFormField(HashMap<String, String> params, DataOutputStream output) {
        StringBuilder sb = new StringBuilder();
//        for(HashMap<String,String> param : params) {
//            sb.append(twoHyphens + boundary + lineEnd);
//            sb.append("Content-Disposition: form-data; name=\"" + param.getKey() + "\"" + lineEnd);
//            sb.append(lineEnd);
//            sb.append(param.getValue() + lineEnd);
//        }
//        try {
//            output.writeBytes(sb.toString());// 发送表单字段数据
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }

        Set<String> keySet1 = params.keySet();
        for (Iterator<String> it = keySet1.iterator(); it.hasNext(); ) {
            String name = it.next();
            String value = params.get(name);
            sb.append(twoHyphens + boundary + lineEnd);
            sb.append("Content-Disposition: form-data; name=\"" + name + "\"" + lineEnd);
            sb.append(lineEnd);
            sb.append(value + lineEnd);
        }
    }

    /*
     * 上传图片内容，格式请参考HTTP 协议格式。
     * 人人网Photos.upload中的”程序调用“http://wiki.dev.renren.com/wiki/Photos.upload#.E7.A8.8B.E5.BA.8F.E8.B0.83.E7.94.A8
     * 对其格式解释的非常清晰。
     * 格式如下所示：
     * --****************fD4fH3hK7aI6
     * Content-Disposition: form-data; name="upload_file"; filename="apple.jpg"
     * Content-Type: image/jpeg
     *
     * 这儿是文件的内容，二进制流的形式
     */
    private static void addImageContent(HashMap<String, File> files, DataOutputStream output) {
//        for(Image file : files) {
//            StringBuilder split = new StringBuilder();
//            split.append(twoHyphens + boundary + lineEnd);
//            split.append("Content-Disposition: form-data; name=\"" + file.getFormName() + "\"; filename=\"" + file.getFileName() + "\"" + lineEnd);
//            split.append("Content-Type: " + file.getContentType() + lineEnd);
//            split.append(lineEnd);
//            try {
//                // 发送图片数据
//                output.writeBytes(split.toString());
//                output.write(file.getData(), 0, file.getData().length);
//                output.writeBytes(lineEnd);
//            } catch (IOException e) {
//                throw new RuntimeException(e);
//            }
//        }

        Set<String> keySet2 = files.keySet();
        for (Iterator<String> it = keySet2.iterator(); it.hasNext();) {
            String name = it.next();
            File value = files.get(name);
            StringBuilder split = new StringBuilder();
            split.append(twoHyphens + boundary + lineEnd);
            split.append("Content-Disposition: form-data; name=\"" + name + "\"; filename=\"" + value.getName() + "\"" + lineEnd);
            split.append("Content-Type: " + "image/" + lineEnd);
            split.append(lineEnd);
            try {
                // 发送图片数据
                output.writeBytes(split.toString());
                output.write(getBytes(value), 0, getBytes(value).length);
                output.writeBytes(lineEnd);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    //把文件转换成字节数组
    private static byte[] getBytes(File f) throws IOException {
        FileInputStream in = new FileInputStream(f);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        byte[] b = new byte[1024];
        int n;
        while ((n = in.read(b)) != -1) {
            out.write(b, 0, n);
        }
        in.close();
        return out.toByteArray();
    }


}
