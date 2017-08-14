package com.seowang.mynewstruct.net.net;

import com.seowang.mynewstruct.net.domain.RequestVo;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.UUID;

import javax.net.ssl.HttpsURLConnection;


//import com.seowang.mystruct.mystruct.net.utils.ToastUtil;

/**
 * Created by hm on 2016/1/12.
 *
 *   这里封装的操作是关于http、https相关的网络访问的具体执行操作、
 *   1、http的（Get、Post、混合的post）
 *   2、https的（Get、Post、混合的post）
 *   3、在该类中定义的方法的返回值是将网络请求后的IO流转化成为的字符串，并将字符串转换成为JavaBean
 *
 *   封装思路：
 *   1、之前使用的控制都是在存在网络的情况下进行的数据请求操作，这种思路的作用在于可以优化同一个接口的网络请求次数
 *      当用户处于移动流量的环境下的时候可以节省一部分流量
 *   2、新的思路：
 *      a、首先根据网络的链接状态划分出两条分支，存在网络的时候，执行相关的网络请求的部分，同时这一部分也包含从本地
 *         缓存中获取数据的操作
 *      b、考虑到用户处于的网络可能不存在，这个时候之前只要进行过网络连接的操作就会出现数据的缓存，这个时候可以将本地
 *         的缓存数据展示到应用中。
 *      c、此时出现一个问题：返回的接口中包含图片资源的网络连接时候，这个时候进行第一次联网展示的时候使用的是二级缓存
 *         的机制将图片进行了缓存，所以就会解决图片没有网络的时候的展示问题
 *      d、之前我的疑问在于：怎么讲Json数据中的所有真实展示信息完全缓存，现在使用二级缓存等类似的机制的时候就能够解决。
 *   3、关于新的思路继续补充：
 *      a、缓存的时间
 *      b、具体的Json是否需要缓存（关于数据展示类型的Json数据需要完全缓存，但是像支付一类的接口Json是不能缓存的）
 *      c、文件的缓存位置（利用工具类完成）考虑到部分手机不存在SD等问题
 *      d、以上这些参数的数据是存在默认值的（用户也可以自己设置）
 *      e、一定要强调健壮性！！！！！
 *
 *   4、换一种思路：
 *      之前看到好多人书写代码的时候，经常把一些需要的公共的变量等数据放在构造方法中
 *      可以模仿一下！！
 *
 *   5、几点要求：
 *      a、一定要健壮性
 *      b、友好性，作为一个网络的操作封装，要将网络相关的状态一定要提示清楚
 *
 */
public class Net {




    //以下的四个方法完成的操作都是将网络返回的IO流转化成为字符串的操作
    //===========Http_Get========================


    /**
     * 进行网络交互的HTTP_GET请求
     * 业务逻辑描述：
     * 1、在Vo中将Get、Post请求的参数列表存储到Map集合中，无论Get还是Post都要进行集合的拼接操作
     *    a、对于Get请求进行集合拼接的操作的作用：直接作为了网络请求的链接
     *    b、对于Post请求进行集合拼接的操作的作用不是为了网络请求的链接，对于Post请求的链接直接就是BaseUrl
     *       对于Post请求的参数直接使用表单进行封装操作
     *       那么拼接出来的链接的作用是作为保存的Json文件的文件名称
     *
     *====================================================
     *    业务添加
     *    为了更为细致的划分模块减少冗余，进行进行一步代码拆分
     *
     * @param netUrl   网络请求的链接
     * @return
     * @throws IOException
     */
    public static InputStream getHttpIOByGet(String netUrl) throws IOException {

        System.out.println("网络的链接："+netUrl);

        URL url = new URL(netUrl);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setConnectTimeout(5000);
        int code = conn.getResponseCode();
        System.out.println("返回码：："+code);
        if (code == 200) {
            InputStream is = conn.getInputStream();
            return is;
        }else{
            /**
             * 当前的数据返回的应该使用什么比较好？？
             * 既然获取的到返回的状态码，是不是直接将状态码返回就可以了呢？？
             */

            return null;
        }

    }

    //以下的四个方法完成的操作都是将网络返回的IO流转化成为字符串的操作
    //===========Https_Get========================


    /**
     * 这里的方法完成的操作是对于Https中的Get方法进行访问的操作
     *
     * ====================================================
     *    业务添加
     *    为了更为细致的划分模块减少冗余，进行进行一步代码拆分
     *
     * @param netUrl    网络的链接
     * @return
     * @throws IOException
     */
    public static InputStream getHttpsIOByGet(String netUrl) throws IOException {


        System.out.println("网络的链接："+netUrl);
        URL url = new URL(netUrl);
        HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setConnectTimeout(5000);
        int code = conn.getResponseCode();
        if (code == 200) {
            InputStream is = conn.getInputStream();

            return is;
        }else{
            /**
             * 当前的数回的应该使用什么比较好？？
             * 既然获取的到返回的状态码，是不是直接将状态码返回就可以了呢？？
             */
            return null;
        }
    }


    private static final String BOUNDARY = UUID.randomUUID().toString(); // 边界标识
    // 随机生成
    private static final String PREFIX = "--";
    private static final String LINE_END = "\r\n";
    private static final String CONTENT_TYPE = "multipart/form-data"; // 内容类型

    private static final String CHARSET = "utf-8"; // 设置编码

    /**
     * 这个方法完成的操作是使用Post进行数据的请求操作，并包含文件的上传操作！
     *
     * @param vo     封装的网络请求对象
     * @return
     */
    public static InputStream getHttpIOByPost(RequestVo vo) {


        HashMap<String,String> param=vo.getParamStringHashMap();
        HashMap<String,File> files=vo.getParamFileHashMap();
        URL url = null;
        try {
            url = new URL(vo.getBaseUrl());
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(10000);
            conn.setDoInput(true); // 允许输入流
            conn.setDoOutput(true); // 允许输出流
            conn.setUseCaches(false); // 不允许使用缓存
            conn.setRequestMethod("POST"); // 请求方式
            conn.setRequestProperty("Charset", CHARSET); // 设置编码
            conn.setRequestProperty("connection", "keep-alive");
            conn.setRequestProperty("user-agent", "Mozilla/5.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)");
            conn.setRequestProperty("Content-Type", CONTENT_TYPE + ";boundary=" + BOUNDARY);
//            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

            // 当文件不为空，把文件包装并且上传
            DataOutputStream dos = new DataOutputStream(conn.getOutputStream());
            StringBuffer sb = null;
            String params = "";

            /***
             * 以下是用于上传参数
             */
            if (param != null && param.size() > 0) {
                Iterator<String> it = param.keySet().iterator();
                while (it.hasNext()) {
                    sb = null;
                    sb = new StringBuffer();
                    String key = it.next();
                    String value = param.get(key);
                    sb.append(PREFIX).append(BOUNDARY).append(LINE_END);
                    sb.append("Content-Disposition: form-data; name=\"").append(key).append("\"").append(LINE_END)
                            .append(LINE_END);
                    sb.append(value).append(LINE_END);
                    params = sb.toString();
                    dos.write(params.getBytes());
                    // dos.flush();
                }
            }


            sb = null;
            params = null;
            sb = new StringBuffer();
            /**
             * 这里重点注意： name里面的值为服务器端需要key 只有这个key 才可以得到对应的文件
             * filename是文件的名字，包含后缀名的 比如:abc.png
             */
            File value=null;
            Set<String> keySet2 = files.keySet();
            for (Iterator<String> it = keySet2.iterator(); it.hasNext();) {
                String name = it.next();
                value = files.get(name);
                sb.append(PREFIX).append(BOUNDARY).append(LINE_END);
                sb.append("Content-Disposition:form-data; name=\"" + name + "\"; filename=\"" + value.getName() + "\""
                        + LINE_END);
                System.out.println("上传文件的格式："+value.getName());
                sb.append("Content-Type:image/jpg" + LINE_END); // 这里配置的Content-type很重要的
                // ，用于服务器端辨别文件的类型的
                sb.append(LINE_END);
                params = sb.toString();
                dos.write(params.getBytes());

                InputStream is = new FileInputStream(value);
                byte[] bytes = new byte[1024];
                int len = 0;
                int curLen = 0;
                while ((len = is.read(bytes)) != -1) {
                    curLen += len;
                    dos.write(bytes, 0, len);
                }
                is.close();
            }



            dos.write(LINE_END.getBytes());
            byte[] end_data = (PREFIX + BOUNDARY + PREFIX + LINE_END).getBytes();
            dos.write(end_data);
            dos.flush();
            int res = conn.getResponseCode();
            if (res == 200) {
                InputStream input = conn.getInputStream();
                return input;
            }else{
                System.out.println("网络的返回码是：："+res);
                return null;
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        } catch (ProtocolException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }




        //======================================================
        //==========以下代码不正确
//        String boundary = "--------httppost123";
//
//
//        URL url = null;
//        try {
//            url = new URL(vo.getBaseUrl());
//            System.out.println("Post访问连接：" + vo.getBaseUrl());
//            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//            conn.setDoOutput(true);
//            conn.setUseCaches(false);
//            conn.setConnectTimeout(10000); //连接超时为10秒
//            conn.setRequestMethod("POST");
//            conn.setRequestProperty("Charset", "UTF-8"); // 设置编码
//            conn.setRequestProperty("Connection", "Keep-Alive");
//            conn.setRequestProperty("User-Agent", "Mozilla/5.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)");
////            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
//            conn.setRequestProperty("Content-Type", "multipart/form-data; boundary=---------------------------263182588415055");
////            conn.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);
//            conn.connect();
//            DataOutputStream ds = new DataOutputStream(conn.getOutputStream());
//
//            Map<String, String> textParams = vo.getParamStringHashMap();
//            Map<String, File> fileparams = vo.getParamFileHashMap();
//
//            Set<String> keySet1 = textParams.keySet();
//            for (Iterator<String> it = keySet1.iterator(); it.hasNext(); ) {
//                String name = it.next();
//                String value = textParams.get(name);
//                ds.writeBytes("--" + boundary + "\r\n");
////                ds.writeBytes("Content-Disposition: form-data; name=\"" + name
////                        + "\"\r\n");
////                ds.writeBytes("\r\n");
//                ds.writeBytes("Content-Disposition: form-data; name=\"" + name
//                        + "=");
//                ds.writeBytes(encode(value) + "\r\n");
//            }
//
//            System.out.println("进行文件的写入操作！");
//            Set<String> keySet2 = fileparams.keySet();
//            for (Iterator<String> it = keySet2.iterator(); it.hasNext();) {
//                String name = it.next();
//                File value = fileparams.get(name);
//                ds.writeBytes("--" + boundary + "\r\n");
//                ds.writeBytes("Content-Disposition: form-data; name=\"" + name
//                        + "\"; filename=\"" + encode(value.getName()) + "\"\r\n");
//                ds.writeBytes("Content-Type: " + getContentType(value) + "\r\n");
//                ds.writeBytes("\r\n");
//                ds.write(getBytes(value));
//                ds.writeBytes("\r\n");
//            }
//            ds.writeBytes("--" + boundary + "--" + "\r\n");
//            ds.writeBytes("\r\n");
//
//            //============================================
//            int code = conn.getResponseCode();
//            System.out.println("网络返回码是：："+code);
//            if (code == 200) {
//
//                InputStream is = conn.getInputStream();
//
//                return is;
//            }else{
//                /**
//                 * 当前的数回的应该使用什么比较好？？
//                 * 既然获取的到返回的状态码，是不是直接将状态码返回就可以了呢？？
//                 */
//
//                return null;
//            }
//            //============================================
////            InputStream in = conn.getInputStream();
////            ByteArrayOutputStream out = new ByteArrayOutputStream();
////            int b;
////            while ((b = in.read()) != -1) {
////                out.write(b);
////            }
////            conn.disconnect();
////            System.out.println("============Net===================");
////            System.out.println("============Net===================");
////            System.out.println("Post返回的结果："+out.toByteArray());
////
////            return out.toByteArray();
//
//
//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//            return null;
//        } catch (ProtocolException e) {
//            e.printStackTrace();
//            return null;
//        } catch (IOException e) {
//            e.printStackTrace();
//            return null;
//        }


//        try {
//            URL url = new URL(vo.getBaseUrl());
//            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//            //1.设置请求方式为POST
//            conn.setRequestMethod("POST"); //注意单词必须大写.
//            conn.setConnectTimeout(10000);
//            //2.设置http请求数据的类型为表单类型
////            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
//            conn.setRequestProperty("Content-Type", "application/form-data; boundary=---------------------------123693993592");
//            //3.设置给服务器写的数据的长度
//            //qq=10000&pwd=abcde
////            String data = "userName="+URLEncoder.encode("HMHMHM", "utf-8");
////            conn.setRequestProperty("Content-Length", String.valueOf(data.length()));
//            //4.记得指定要给服务器写数据
//            conn.setDoOutput(true);
//            conn.connect();
//
////            Content-Type: multipart/form-data; boundary=---------------------------123693993592
////            Content-Length: 778126
////
////                    -----------------------------123693993592
////            Content-Disposition: form-data; name="photoimg"; filename="Penguins.jpg"
////            Content-Type: image/jpeg
//
//            //5.开始向服务器写数据
//            DataOutputStream ds=new DataOutputStream(conn.getOutputStream());
////            ds.write(data.getBytes());
//            ds.writeBytes("\r\n");ds.writeBytes("\r\n");
//            Map<String, File> fileparams = vo.getParamFileHashMap();
//            Set<String> keySet2 = fileparams.keySet();
//            for (Iterator<String> it = keySet2.iterator(); it.hasNext();) {
//                String name = it.next();
//                File value = fileparams.get(name);
////                ds.writeBytes("--" + boundary + "\r\n");
//                ds.writeBytes("Content-Disposition: form-data; name=\"" + name
//                        + "\"; filename=\"" + encode(value.getName()) + "\"\r\n");
////                ds.writeBytes("Content-Type: " + getContentType(value) + "\r\n");
//                ds.writeBytes("Content-Type: " + "image/jpeg" + "\r\n");
//                ds.writeBytes("\r\n");
//                ds.write(getBytes(value));
//                ds.writeBytes("\r\n");
//            }
//
////            -----------------------------123693993592
////            Content-Disposition: form-data; name="userName"
////
////            ASD
////            -----------------------------123693993592--
//            ds.writeBytes("-----------------------------123693993592");
//            ds.writeBytes("Content-Disposition: form-data; name=\"" + "userName"
//                    + "\"\r\n");
//            ds.writeBytes("ASD");
//            ds.writeBytes("-----------------------------123693993592");
//            int code = conn.getResponseCode();
//            System.out.println("网络返回码是：："+code);
//            if(code == 200){
//                InputStream is = conn.getInputStream();
//                return is;
//
//            }else{
//                return null;
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//
//        }

//        URL url = null;
//        try {
//            url = new URL(vo.getBaseUrl());
//            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//            //1.设置请求方式为POST
//            conn.setRequestMethod("POST"); //注意单词必须大写.
//            conn.setConnectTimeout(5000);
//            //2.设置http请求数据的类型为表单类型
////            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
//            conn.setRequestProperty("Content-Type", "application/form-data; boundary=---------------------------123693993592");
//            //3.设置给服务器写的数据的长度
////            String data = "userName="+URLEncoder.encode("HMHMHM", "utf-8")+"&userSex="+URLEncoder.encode("NNNNN", "utf-8");
////            conn.setRequestProperty("Content-Length", String.valueOf(data.length()));
//
//            //4.记得指定要给服务器写数据
//            conn.setDoOutput(true);
//            conn.connect();
////            ds.writeBytes("Content-Disposition: form-data; name=\"" + name
//////                        + "\"\r\n");
//            DataOutputStream ds = new DataOutputStream(conn.getOutputStream());
//
//            ds.writeBytes("Content-Disposition: form-data; name=\"" + "userName" + "\"\r\n");
//            ds.writeBytes(encode("HMHMHM") + "\r\n");
//            ds.writeBytes("Content-Disposition: form-data; name=\"" + "userSex" + "\"\r\n");
//            ds.writeBytes(encode("XXXXX") + "\r\n");
//            ds.writeBytes("\r\n");
//
//
//
//            //5.开始向服务器写数据
////            conn.getOutputStream().write(data.getBytes());
//            int code = conn.getResponseCode();
//            if(code == 200) {
//                InputStream is = conn.getInputStream();
//                return is;
//            }else{
//                System.out.println("200以外的返回码是：："+code);
//                return null;
//            }
//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//            return null;
//        } catch (ProtocolException e) {
//            e.printStackTrace();
//            return null;
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//            return null;
//        } catch (IOException e) {
//            e.printStackTrace();
//            return null;
//        }


    }

//    // 对包含中文的字符串进行转码，此为UTF-8。服务器那边要进行一次解码
//
//    private static String encode(String value) throws UnsupportedEncodingException {
//        return URLEncoder.encode(value, "UTF-8");
//    }

//    //获取文件的上传类型，图片格式为image/png,image/jpg等。非图片为application/octet-stream
//    private static String getContentType(File f) {
//
////      return "application/octet-stream";  // 此行不再细分是否为图片，全部作为application/octet-stream 类型
////        ImageInputStream imagein = ImageIO.createImageInputStream(f);
////        if (imagein == null) {
////            return "application/octet-stream";
////        }
////        Iterator<ImageReader> it = ImageIO.getImageReaders(imagein);
////        if (!it.hasNext()) {
////            imagein.close();
////            return "application/octet-stream";
////        }
////        imagein.close();
////        return "image/" + it.next().getFormatName().toLowerCase();//将FormatName返回的值转换成小写，默认为大写
//
//        return "image/jpg";
//    }
//    //把文件转换成字节数组
//    private static byte[] getBytes(File f) throws IOException {
//        FileInputStream in = new FileInputStream(f);
//        ByteArrayOutputStream out = new ByteArrayOutputStream();
//        byte[] b = new byte[1024];
//        int n;
//        while ((n = in.read(b)) != -1) {
//            out.write(b, 0, n);
//        }
//        in.close();
//        return out.toByteArray();
//    }

//    //添加结尾数据
//    private void paramsEnd() throws Exception {
//        ds.writeBytes("--" + boundary + "--" + "\r\n");
//        ds.writeBytes("\r\n");
//    }

}

