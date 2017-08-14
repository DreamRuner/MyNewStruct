package com.seowang.mynewstruct.net.cache;

import com.seowang.mynewstruct.net.domain.RequestType;
import com.seowang.mynewstruct.net.domain.RequestVo;
import com.seowang.mynewstruct.net.net.Net;
import com.seowang.mynewstruct.net.utils.EncryptUtil;
import com.seowang.mynewstruct.net.utils.FileUtil;
import com.seowang.mynewstruct.net.utils.NetCheckUtil;
import com.seowang.mynewstruct.net.utils.StreamTool;
import com.seowang.mynewstruct.net.utils.ToastUtil;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by hm on 2016/1/12.
 *   包含缓存操作的网络交互
 *
 *   ********************************************
 *   业务分析：
 *   1、要进行网络数据的缓存操作，需要按照不同的小模块进行划分，同时对于可以处于同一等级的操作使用线程池进行操作
 *   2、此类中主要完成的框架关系是对于数据的缓存控制，
 *   3、关于网络请求的操作主要位于Net类中完成
 *   4、此外这两个类中都添加关于线程池的操作
 *      a、StructNetUtils类中线程池主要用于数据从网络请求回来之后的缓存处理阶段
 *      b、Net类中主要进行网络的请求操作，自然需要进行子线程的操作
 *      c、一个疑问：
 *         !!!???对于这两个类中都进行线程的操作，是否会出现，线程运行先后的问题!!!???
 *
 *   当前类完成的操作是作为网络缓存框架中的框架部分，主要是作为主体的框架，同时将网络操作、缓存操作添加到当前的类
 *   的空框架中
 */
public class JsonCacheUtil {

//    private RequestVo vovo;
//    private String path;
//    public static void setVo(RequestVo vo){
//        this.vovo=vo;
//    }
//
//    private void setPath(){
//
//    }
//
//    public void doJob(){
//
//        getBeanByNetORCache(vovo);
//    }

    /**
     * 这里完成的操作是关于网络的不同的请求类型的分拣操作，
     * 根据不同网络请求方式和类型执行不同的代码逻辑
     * 该方法利用RequestVo封装的解析器Parser完成将字符串解析成为JavaBean的过程
     * @param vo
     * @return
     */
    public static Object getBeanByNetORCache(RequestVo vo) throws IOException {

        //无论get还是post都进行参数的拼接，使用这个拼接内容作为保存数据的标志
        String netUrl1= NetMapUtil.getTotalUrlFromVo(vo);
        //利用整体的Url作为标志生成一个MD5串，使用该加密串作为保存的Json文件的文件名称
        final String md5Url = EncryptUtil.getStringMD5(netUrl1);

        /**
         * 关于网络连接正常的情况下的缓存设置与获取！
         * 1、如果选择了缓存数据，首先从缓存中获取数据，并对数据进行判断和操作
         * 2、如果没有选择缓存数据，直接请求网络，获取数据后返回
         *
         * 关于网络未正常连接的情况缓存操作与获取
         * 1、此时选择了缓存数据，直接获取
         *    a、时间未超时，返回数据正常处理
         *    b、时间超时，进行对应的提示
         * 2、没有选择数据缓存，进行对应的提示！
         *
         *
         *
         */
        String result = null;
        if (NetCheckUtil.hasConnectedNetwork(vo)) {

            if(vo.isSaveDataWithNet()){
                //利用加密后的网络连接标志从文件中获取数据
                result= FileUtil.getStringFromFile(vo.getDataSaveTimeNoNet(),md5Url);
                if(null!=result){
                    return vo.getJsonParser().parseJSON(result);

                }else{
                    //此时表示文件中不存在对应的数据，或者文件存储时间已经超时。需要进行网络请求
                    result=getStringByNet(vo);
                    if(null!=result){
                        final String finalResult = result;
                        new Thread(){
                            @Override
                            public void run() {
                                try {
                                    FileUtil.writeJsonToFile(md5Url, finalResult);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }.start();

                        return vo.getJsonParser().parseJSON(result);
                    }else{
                        //如果网络返回的数据为null说明网络存在问题！
                        return vo.getJsonParser().parseJSON(null);
                    }
                }
            }else{
                //此时没有选择数据缓存，直接请求网络
                result=getStringByNet(vo);
                if(null!=result){
                    //如果选择了缓存数据
                    if(vo.isSaveDataWithNet()){
                        final String finalResult = result;
                        new Thread(){
                            @Override
                            public void run() {
                                try {
                                    FileUtil.writeJsonToFile(md5Url, finalResult);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }.start();
                    }
                    return vo.getJsonParser().parseJSON(result);
                }else{
                    //如果网络返回的数据为null说明网络存在问题！
                    return vo.getJsonParser().parseJSON(null);
                }
            }

        } else {
            ToastUtil.showToastSafe(vo.getmContext(), "您的手机没有连接网络，请先连接网络！");
            /**
             * 当前不存在网络连接，但是假如本地数据中具有缓存，仍然可以获取数据
             */
            if(vo.isSaveDataWithNet()){
                //获取缓存的数据
                result=FileUtil.getStringFromFile(vo.getDataSaveTimeNoNet(),md5Url);
                if(null==result){
                    ToastUtil.showToastSafe(vo.getmContext(), "您的缓存数据已经超时，请连接网络！");
                    return vo.getJsonParser().parseJSON(null);
                }else{
                    return vo.getJsonParser().parseJSON(result);
                }
            }else{
                ToastUtil.showToastSafe(vo.getmContext(), "您的手机没有连接网络，请先连接网络！");
                return vo.getJsonParser().parseJSON(null);
            }

        }

    }



    private static String getStringByNet(RequestVo vo) throws IOException {
        //无论get还是post都进行参数的拼接，使用这个拼接内容作为保存数据的标志
        String netUrl2= NetMapUtil.getTotalUrlFromVo(vo);
        String result=null;
        InputStream is=null;
        switch (vo.getRequestType()) {

            case RequestType.HTTP_GET:

                // 进行Http的Get请求操作的时候进行文件操作的时候出现IO异常
                is= Net.getHttpIOByGet(netUrl2);
                if(null==is){
                    System.out.println("数据流为NULL！！");
                }
                result = StreamTool.getStringByIO(is);

                break;
            case RequestType.HTTP_POST:

                // 进行Http的Post请求操作的时候进行文件操作的时候出现IO异常
//                    result = getHttpJsonByNetPostORCache(vo);
                InputStream inputStream=Net.getHttpIOByPost(vo);
//                InputStream inputStream= PostUtils.getHttpIOByPost(vo);
//                InputStream inputStream= PPUtil.getHttpIOByPost(vo);
                String res=StreamTool.getStringByIO(inputStream);
                System.out.println("========JsonUtil==============");
                System.out.println("========JsonUtil==============");
                System.out.println("框架中返回的Post数据：："+res);

                break;
            case RequestType.HTTPS_GET:

                // 进行Https的Get请求操作的时候进行文件操作的时候出现IO异常
                // 进行Http的Get请求操作的时候进行文件操作的时候出现IO异常
                is= Net.getHttpIOByGet(netUrl2);
                result = StreamTool.getStringByIO(is);

                break;
            case RequestType.HTTPS_POST:
                // 进行Https的Post请求操作的时候进行文件操作的时候出现IO异常
//                    result = getHttpsJsonByNetPostORCache(vo);

                break;
        }
        return result;
    }

}
