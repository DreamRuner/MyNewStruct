package com.seowang.mynewstruct.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.seowang.mynewstruct.R;
import com.seowang.mynewstruct.domain.LessonList;
import com.seowang.mynewstruct.domain.PostPost;
import com.seowang.mynewstruct.domain.Result;
import com.seowang.mynewstruct.net.async.AsyncHandler;
import com.seowang.mynewstruct.net.async.AsyncUrlConnection;
import com.seowang.mynewstruct.net.base.BaseParser;
import com.seowang.mynewstruct.net.domain.RequestType;
import com.seowang.mynewstruct.net.domain.RequestVo;
import com.seowang.mynewstruct.net.download.DownLoadUtil;
import com.seowang.mynewstruct.net.thread.MyThreadFactory;
import com.seowang.mynewstruct.net.utils.EncryptUtil;
import com.seowang.mynewstruct.net.utils.ZipToFile;
import com.seowang.mynewstruct.parser.LessonParser;
import com.seowang.mynewstruct.parser.PostParser;
import com.seowang.mynewstruct.parser.ResultParser;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class SplashActivity extends AppCompatActivity implements View.OnClickListener{

    //接口请求的类型
    int requestType= RequestType.HTTP_GET;
    //接口的基础链接
    //这个BaseUrl 在一个Config文件中进行统一管理
    String baseUrl=null;
    //字符串形式的参数
    HashMap<String,String> paramStringHashMap=null;
    //对应的上传文件的File对象的参数
    HashMap<String,File> paramFileHashMap=null;
    //网络环境状态的提示。仅限于Wifi与流量之间的提示
    boolean isShowWifi=true;
    //数据是否进行缓存的设置
    boolean isSaveDataWithNet=true;
    // 如果上面选择为true，但是没有给这个时间，框架中默认进行5minute的缓存
    long dataSaveTimeNoNet=200000L;

    private ImageView iv_test1;
    private ImageView iv_test2;
    private ImageView iv_test3;
    private ImageView iv_test4;
    private TextView tv_test1;
    private TextView tv_test2;
    private Button bt_jump;
    private Button bt_do;
    private Button bt_filepost;
    private Button bt_stringpost;
    private Button bt_uppost;
    private Button bt_download;
    private Button bt_jumpjump;
    private Button bt_mainjump;
    private Button bt_langdu;
    private TextView tv_content;

    //上下文
    private Context mContext=SplashActivity.this;

    private long saveTime=10000000L;

    private LessonList lessonList=null;

    private String imageUrl1="https://gd3.alicdn.com/bao/uploaded/i3/TB1uc7pLFXXXXXLXVXXXXXXXXXX_!!0-item_pic.jpg_400x400.jpg";
    private String imageUrl2="https://img.alicdn.com/imgextra/i4/68943620/TB2j8SLkFXXXXabXpXXXXXXXXXX_!!68943620.jpg";
    private String imageUrl3="https://img.alicdn.com/imgextra/i3/482498316/TB1RRbuLFXXXXX2XVXXXXXXXXXX_!!0-tstar.jpg";
    private String imageUrl4="http://img5.duitang.com/uploads/item/201406/14/20140614182603_LyPYk.jpeg";
    private String imageUrl5="http://img.alicdn.com/imgextra/i3/482498316/TB1RRbuLFXXXXX2XVXXXXXXXXXX_!!0-tstar.jpg";
    private String imageUrl6="http://os.alipayobjects.com/rmsportal/QAvKTMiRbqQvkFe.jpg";
    private String imageUrl7="http://h.hiphotos.baidu.com/image/pic/item/4ec2d5628535e5dd2820232370c6a7efce1b623a.jpg";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashs);

        iv_test1= (ImageView) findViewById(R.id.iv_test1);
        iv_test2= (ImageView) findViewById(R.id.iv_test2);
        iv_test3= (ImageView) findViewById(R.id.iv_test3);
        iv_test4= (ImageView) findViewById(R.id.iv_test4);
        tv_test1= (TextView) findViewById(R.id.tv_test1);
        tv_test2= (TextView) findViewById(R.id.tv_test2);
        bt_jump= (Button) findViewById(R.id.bt_jump);
        bt_jump.setOnClickListener(this);
        bt_do= (Button) findViewById(R.id.bt_do);
        bt_do.setOnClickListener(this);
        //进行银屏与视频合成的操作
        bt_langdu= (Button) findViewById(R.id.bt_langdu);
        bt_langdu.setOnClickListener(this);
        //
        bt_jumpjump= (Button) findViewById(R.id.bt_jumpjump);
        bt_jumpjump.setOnClickListener(this);
        //跳转到MainActivity测试Fragment
        bt_mainjump= (Button) findViewById(R.id.bt_mainjump);
        bt_mainjump.setOnClickListener(this);

        //测试下载
        bt_download= (Button) findViewById(R.id.bt_download);
        bt_download.setOnClickListener(this);
        //全部字符串的Post
        bt_stringpost= (Button) findViewById(R.id.bt_stringpost);
        bt_stringpost.setOnClickListener(this);
        //混合的Post
        bt_filepost= (Button) findViewById(R.id.bt_filepost);
        bt_filepost.setOnClickListener(this);
        //线上的Post
        bt_uppost= (Button) findViewById(R.id.bt_uppost);
        bt_uppost.setOnClickListener(this);
        tv_content= (TextView) findViewById(R.id.tv_content);

        AsyncUrlConnection.getBitmap(saveTime, imageUrl1, new AsyncHandler<Bitmap>() {
            @Override
            public void onSuccess(Bitmap bitmap) {
                iv_test1.setImageBitmap(bitmap);
            }
            @Override
            public void onFaile(String str) {}
        });
        AsyncUrlConnection.getBitmap(saveTime,imageUrl2, new AsyncHandler<Bitmap>() {
            @Override
            public void onSuccess(Bitmap bitmap) {
                iv_test2.setImageBitmap(bitmap);
            }
            @Override
            public void onFaile(String str) {}
        });
        AsyncUrlConnection.getBitmap(saveTime,imageUrl4, new AsyncHandler<Bitmap>() {
            @Override
            public void onSuccess(Bitmap bitmap) {
                iv_test3.setImageBitmap(bitmap);
            }
            @Override
            public void onFaile(String str) {}
        });
        AsyncUrlConnection.getBitmap(saveTime,imageUrl5, new AsyncHandler<Bitmap>() {
            @Override
            public void onSuccess(Bitmap bitmap) {
                iv_test4.setImageBitmap(bitmap);
            }
            @Override
            public void onFaile(String str) {}
        });
//        AsyncUrlConnection.getBitmap(saveTime,imageUrl5, new AsyncHandler<Bitmap>() {
//            @Override
//            public void onSuccess(Bitmap bitmap) {
//                iv_test4.setImageBitmap(bitmap);
//            }
//            @Override
//            public void onFaile(String str) {}
//        });
//        AsyncUrlConnection.getBitmap(saveTime,imageUrl5, new AsyncHandler<Bitmap>() {
//            @Override
//            public void onSuccess(Bitmap bitmap) {
//                iv_test4.setImageBitmap(bitmap);
//            }
//            @Override
//            public void onFaile(String str) {}
//        });
//        AsyncUrlConnection.getBitmap(saveTime,imageUrl5, new AsyncHandler<Bitmap>() {
//            @Override
//            public void onSuccess(Bitmap bitmap) {
//                iv_test4.setImageBitmap(bitmap);
//            }
//            @Override
//            public void onFaile(String str) {}
//        });


//        http://api.lovek12.com/v190/index.php?r=resource/get-resource-by-gradex
//        &device_type=ad1
//        &grade_id=0




    }

    @Override
    public void onClick(View v) {




        switch (v.getId()){

            case R.id.bt_langdu:
                Intent intentreadjump=new Intent(mContext, ReadActivity.class);
                startActivity(intentreadjump);
                break;

            case R.id.bt_mainjump:
                Intent intentmainjump=new Intent(mContext, MainActivity.class);
                startActivity(intentmainjump);
                break;

            case R.id.bt_jumpjump:
                Intent intentjumpjump=new Intent(mContext, LayoutActivity.class);
                startActivity(intentjumpjump);
                break;
            case R.id.bt_download:

                String patha="/data/data/com.seowang.mynewstruct/cache/com.seowang.mynewstruct/E8EB1840C1BEB391CC7B3DF5D14DB4D8";
                File filea=new File(patha);
                if(filea.exists()){
                    filea.delete();
                    System.out.println("DownLoad::SD卡中存储文件删除了");
                }else {
                    System.out.println("DownLoad::SD卡中存储文件不存在");
                }

                final String netUrl="http://api.lovek12.com/index.php?r=recite/retcite-download&id=24";
                MyThreadFactory.getThreadPoolExecutor().execute(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            DownLoadUtil.downLoadFile(netUrl);
                        } catch (IOException e) {
                            System.out.println("SplashActivity出现IOException");
                            e.printStackTrace();
                        }
                    }
                });
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                String path="/data/data/com.seowang.mynewstruct/cache/com.seowang.mynewstruct/E8EB1840C1BEB391CC7B3DF5D14DB4D8";
                String md5="165e8a79075cad0c39f49576d7037a3f";
                File file=new File(path);
                if(file.exists()){
                    System.out.println("DownLoad::HMHMHMHMHMH");
                    System.out.println("DownLoad::SD卡中存储文件大小："+file.length());
                    System.out.println("DownLoad::HMHMHMHMHMH");
                    System.out.println("DownLoad::下载文件的MD5值1::"+ EncryptUtil.getFileMD5(file));
                    System.out.println("DownLoad::下载文件的MD5值2::"+ md5);
//                    try {
//                        ZipToFile.upZipFile(path);
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                        System.out.println("DownLoad::文件解压出错！！");
//                    }
                    ZipToFile.upZipFile(path);
                }else {
                    System.out.println("SD卡中存储文件不存在");
                }

                break;
            case R.id.bt_do:
                //接口请求的类型
                requestType= RequestType.HTTP_GET;
                //接口的基础链接
                //这个BaseUrl 在一个Config文件中进行统一管理
                baseUrl="http://api.lovek12.com/v190/index.php?r=resource/get-resource-by-gradex";
                //字符串形式的参数
                paramStringHashMap=new HashMap<>();
                paramStringHashMap.put("device_type", "ad1");
                paramStringHashMap.put("grade_id","0");
                //对应的上传文件的File对象的参数
                paramFileHashMap=new HashMap<>();
                //对应JavaBean的解析Parser
                BaseParser<LessonList> jsonParser=new LessonParser();
                //网络环境状态的提示。仅限于Wifi与流量之间的提示
                isShowWifi=true;
                //数据是否进行缓存的设置
                isSaveDataWithNet=true;
                //数据缓存的时间长度，毫秒级  1minute==60000ms，
                // 如果上面选择为true，但是没有给这个时间，框架中默认进行5minute的缓存
                dataSaveTimeNoNet=20000L;
                //RequestVo类的对象创建
                RequestVo vo=new RequestVo(requestType,baseUrl,paramStringHashMap,paramFileHashMap,jsonParser,
                        mContext,isShowWifi,isSaveDataWithNet,dataSaveTimeNoNet);

                AsyncUrlConnection.getObject(vo, new AsyncHandler<LessonList>() {
                    @Override
                    public void onSuccess(LessonList result) {
                        if(null!=result){
                            //JavaBean赋值
                            lessonList=result;
                            setData();
                        }else{
                            //异常的提示
                        }
                        System.out.println("=============================");
                        System.out.println("课程首页的JavaBean"+result.toString());
                        tv_content.setTextColor(Color.BLUE);
                        tv_content.setText(result.toString());
                        System.out.println("=============================");
                    }
                    @Override
                    public void onFaile(String str) {
//                        str =1;
                    }
                });
                break;

            case R.id.bt_jump:
                Intent intentjump=new Intent(mContext, ListsActivity.class);
                startActivity(intentjump);
                break;
            //混合Post上传
            case R.id.bt_filepost:

                //接口请求的类型
                requestType= RequestType.HTTP_POST;
                //接口的基础链接
                //这个BaseUrl 在一个Config文件中进行统一管理
                baseUrl="http://192.168.4.250/index.php?r=recite/picture";
                //字符串形式的参数
                paramStringHashMap=new HashMap<>();
                paramStringHashMap.put("userName", "HMHMHM");
                //对应的上传文件的File对象的参数
                paramFileHashMap=new HashMap<>();
                paramFileHashMap.put("photoimg",new File("/data/data/com.seowang.mynewstruct/cache/com.seowang.mynewstruct/CB51B68E5C900399DA0B2D2420307E3C.jpg"));
                //对应JavaBean的解析Parser
                BaseParser<PostPost> postParser=new PostParser();
                //网络环境状态的提示。仅限于Wifi与流量之间的提示
                isShowWifi=false;
                //数据是否进行缓存的设置
                isSaveDataWithNet=false;
                //数据缓存的时间长度，毫秒级  1minute==60000ms，
                // 如果上面选择为true，但是没有给这个时间，框架中默认进行5minute的缓存
                dataSaveTimeNoNet=20000L;
                //RequestVo类的对象创建
                RequestVo vo1=new RequestVo(requestType,baseUrl,paramStringHashMap,paramFileHashMap,postParser,
                        mContext,isShowWifi,isSaveDataWithNet,dataSaveTimeNoNet);

                AsyncUrlConnection.getObject(vo1, new AsyncHandler<PostPost>() {
                    @Override
                    public void onSuccess(PostPost result) {
                        System.out.println("进行混合资料上传的结果："+result);
                    }
                    @Override
                    public void onFaile(String str) {}
                });

//
////

                break;
            //字符串Post上传
            case R.id.bt_stringpost:

                //接口请求的类型
                requestType= RequestType.HTTP_POST;
                //接口的基础链接
                //这个BaseUrl 在一个Config文件中进行统一管理
                baseUrl="http://192.168.4.250/index.php?r=recite/testform";
                //字符串形式的参数
                paramStringHashMap=new HashMap<>();
                paramStringHashMap.put("userName", "HMHMHM");
                paramStringHashMap.put("userSex", "XXXXXX");
                //对应的上传文件的File对象的参数
                paramFileHashMap=new HashMap<>();
                //对应JavaBean的解析Parser
                BaseParser<PostPost> postParser1=new PostParser();
                //网络环境状态的提示。仅限于Wifi与流量之间的提示
                isShowWifi=false;
                //数据是否进行缓存的设置
                isSaveDataWithNet=false;
                //数据缓存的时间长度，毫秒级  1minute==60000ms，
                // 如果上面选择为true，但是没有给这个时间，框架中默认进行5minute的缓存
                dataSaveTimeNoNet=20000L;
                //RequestVo类的对象创建
                RequestVo vo2=new RequestVo(requestType,baseUrl,paramStringHashMap,paramFileHashMap,postParser1,
                        mContext,isShowWifi,isSaveDataWithNet,dataSaveTimeNoNet);

                AsyncUrlConnection.getObject(vo2, new AsyncHandler<PostPost>() {
                    @Override
                    public void onSuccess(PostPost result) {
                        System.out.println("进行字符串上传的结果："+result);
                    }
                    @Override
                    public void onFaile(String str) {}
                });
                break;
            //线上接口上传
            case R.id.bt_uppost:

                //接口请求的类型
                requestType= RequestType.HTTP_POST;
                //接口的基础链接
                //这个BaseUrl 在一个Config文件中进行统一管理
                baseUrl="http://api.lovek12.com/v190/index.php?r=user/upload-avatar";
                //字符串形式的参数
//                设备类型	device_type	必填
//                用户ID	user_id	必填
//                模拟表单上传的Key	name	UserInfo[avatar](固定)
//                源图像文件名称	filename	必填
//                原图像文件的对象	file	必填

                paramStringHashMap=new HashMap<>();
                paramStringHashMap.put("device_type", "ad1");
                paramStringHashMap.put("user_id", "155");
//                paramStringHashMap.put("UserInfo[avatar]", "MHXXXXHM");
//                paramStringHashMap.put("filename", "AAAAA");
                //对应的上传文件的File对象的参数
                paramFileHashMap=new HashMap<>();
                paramFileHashMap.put("UserInfo[avatar]",new File("/data/data/com.seowang.mynewstruct/cache/com.seowang.mynewstruct/CB51B68E5C900399DA0B2D2420307E3C.jpg"));
                //对应JavaBean的解析Parser
                BaseParser<Result> resultParser=new ResultParser();
                //网络环境状态的提示。仅限于Wifi与流量之间的提示
                isShowWifi=false;
                //数据是否进行缓存的设置
                isSaveDataWithNet=false;
                //数据缓存的时间长度，毫秒级  1minute==60000ms，
                // 如果上面选择为true，但是没有给这个时间，框架中默认进行5minute的缓存
                dataSaveTimeNoNet=20000L;
                //RequestVo类的对象创建
                RequestVo vo3=new RequestVo(requestType,baseUrl,paramStringHashMap,paramFileHashMap,resultParser,
                        mContext,isShowWifi,isSaveDataWithNet,dataSaveTimeNoNet);

                AsyncUrlConnection.getObject(vo3, new AsyncHandler<Result>() {
                    @Override
                    public void onSuccess(Result result) {
                        System.out.println("线上接口的返回结果："+result);
                    }
                    @Override
                    public void onFaile(String str) {}
                });
                break;

        }
    }

    /**
     * 1、判断Status
     * 2、
     */
    private void setData(){


    }
}
