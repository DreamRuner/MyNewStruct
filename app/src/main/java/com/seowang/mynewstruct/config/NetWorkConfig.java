package com.seowang.mynewstruct.config;

/**
 * Created by hm on 2016/3/28.
 */
public class NetWorkConfig {

//    http://api.lovek12.cn/v190/index.php?r=resource/get-resource-by-gradex&device_type=ad1&grade_id=0&course_type=0&page=1&size=8

    //测试环境的网络接口总连接
//    private static final String BASE_URL="http://192.168.4.250/v190/index.php";
    //线上环境的网络总连接
    private static final String BASE_URL="http://api.lovek12.com/v190/index.php";

    /**
     * 课程模块首页的网络接口
     * @return
     */
    public static String getLessonList(){

        return BASE_URL+"?r=resource/get-resource-by-gradex";
    }



}
