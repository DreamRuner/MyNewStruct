package com.seowang.mynewstruct.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.seowang.mynewstruct.R;
import com.seowang.mynewstruct.domain.LangDu;
import com.seowang.mynewstruct.net.async.AsyncHandler;
import com.seowang.mynewstruct.net.async.AsyncUrlConnection;
import com.seowang.mynewstruct.net.base.BaseParser;
import com.seowang.mynewstruct.net.domain.RequestType;
import com.seowang.mynewstruct.net.domain.RequestVo;
import com.seowang.mynewstruct.parser.LangDuParser;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by hm on 2016/4/11.
 *
 *   代码逻辑简述：
 *   1、这一部分完成的操作是关于趣平方应用中的朗读中心模块的主要功能（下载视频的压缩包、解压zip文件、将视频与音频进行合成操作）
 *   2、需要的变动：
 *           a、现在做的方式：一个背景视频、一个背景音乐、一个泛读声音、（来自用户的声音）
 *                        1、先进行视频的资源合成，之后播放
 *                        2、当用户需要进行朗读练习的时候再将视频合成后上传服务器
 *           b、希望的格式：一个完整的视频、一个背景视频、一个背景音乐、（来自用户的声音）
 *                        1、去除起始的视频合成操作
 *                        2、当用户进行朗读练习的时候将之前的：背景视频、背景音乐、用户的朗读声音进行合成
 *           c、注：利用比较成熟的下载操作的时间消耗代替音视频合成的时间占用！
 *    3、我要做什么？？
 *       a、首先需要完善多线程下载的逻辑和效率
 *       b、关于压缩包的解压操作
 *       c、对于压缩文件和视频文件的存储位置必须明了
 *       d、最重要的部分---音视频合成操作
 *       e、视频的上传操作（混合post）
 *
 */
public class ReadActivity extends Activity implements View.OnClickListener {

    private Context mContext=ReadActivity.this;
    private ArrayList<LangDu.LangDuDetail> arrayList=null;
    //关于下载的资源的MD5值
    private String md5Key=null;
    //关于资源的下载链接
    private String downUrl=null;
    //Adapter
    private MyAdapter myAdapter;

    //开始下载
    private Button bt_start;
    //暂停下载
    private Button bt_pause;
    //停止下载
    private Button bt_stop;
    //展示下载进度
    private ProgressBar pb_process;
    //进行播放的控制按钮
    private ImageView iv_play;
    //ListView列表
    private ListView lv_down;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read);
        //首先要请求一个借口获取对应的可以下载的数据的列表，将名称和大小展示出来

        getData();
        //开始下载
        bt_start= (Button) findViewById(R.id.bt_start);
        bt_start.setOnClickListener(this);
        //暂停下载
        bt_pause= (Button) findViewById(R.id.bt_pause);
        bt_pause.setOnClickListener(this);
        //停止下载
        bt_stop= (Button) findViewById(R.id.bt_stop);
        bt_stop.setOnClickListener(this);
        //展示下载进度
        pb_process= (ProgressBar) findViewById(R.id.pb_process);
        //进行播放的控制按钮
        iv_play= (ImageView) findViewById(R.id.iv_play);
        iv_play.setOnClickListener(this);
        lv_down= (ListView) findViewById(R.id.lv_down);
    }

    private void getData() {
        //接口请求的类型
        int requestType= RequestType.HTTP_GET;
        //接口的基础链接
//        http://api.lovek12.com/v190/index.php?r=recite/index&device_type=ad1&size=8
        String baseUrl="http://api.lovek12.com/v190/index.php?r=recite/index";
        //字符串形式的参数
        HashMap<String,String> paramStringHashMap=new HashMap<>();
        paramStringHashMap.put("device_type", "ad1");
        paramStringHashMap.put("size", "1");
        //对应的上传文件的File对象的参数
        HashMap<String,File> paramFileHashMap=new HashMap<>();
        //对应JavaBean的解析Parser
        BaseParser<LangDu> jsonParser=new LangDuParser();
        //网络环境状态的提示。仅限于Wifi与流量之间的提示
        boolean isShowWifi=true;
        //数据是否进行缓存的设置
        boolean isSaveDataWithNet=false;
        //数据缓存的时间长度，毫秒级  1minute==60000ms，
        // 如果上面选择为true，但是没有给这个时间，框架中默认进行5minute的缓存
        long dataSaveTimeNoNet=200000000L;
        //RequestVo类的对象创建
        RequestVo vo=new RequestVo(requestType,baseUrl,paramStringHashMap,paramFileHashMap,jsonParser,
                mContext,isShowWifi,isSaveDataWithNet,dataSaveTimeNoNet);
        AsyncUrlConnection.getObject(vo, new AsyncHandler<LangDu>() {
            @Override
            public void onSuccess(LangDu result) {
                if (null != result) {
                    arrayList = result.getData();
                    setData();
                } else {
                }
            }

            @Override
            public void onFaile(String str) {}
        });
    }

    private void setData() {

        myAdapter=new MyAdapter();
        lv_down.setAdapter(myAdapter);
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()){
            //开始下载
            case R.id.bt_start:

                break;
            //暂停下载
            case R.id.bt_pause:

                break;
            //停止下载
            case R.id.bt_stop:

                break;
            //播放视频
            case R.id.iv_play:

                break;

        }

    }

    class MyViewHolder{
        TextView tv_title;
        TextView tv_url;
    }
    class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return arrayList.size();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            View view;
            final MyViewHolder holder;//存储ListView中item的各个控件
            if(convertView==null){
                view=View.inflate(mContext, R.layout.item_listview, null);
                holder=new MyViewHolder();
                holder.tv_title= (TextView) view.findViewById(R.id.tv_title);
                holder.tv_url= (TextView) view.findViewById(R.id.tv_url);
                view.setTag(holder);
            }else{
                view=convertView;
                holder=(MyViewHolder) view.getTag();
            }
            LangDu.LangDuDetail langDuDetail=arrayList.get(position);

            //设置资源的名称
            if(null!=langDuDetail.getTitle()){
                holder.tv_title.setText(langDuDetail.getTitle());
            }else{
                holder.tv_title.setText("哈哈");
            }
            //设置资源的URl
            if(null!=langDuDetail.getDown_file_url()){
                holder.tv_url.setText(langDuDetail.getDown_file_url());
                downUrl=langDuDetail.getDown_file_url();
            }else{
                holder.tv_url.setText("http://---------");
            }
            if(null!=langDuDetail.getDown_file_md5()){
                md5Key=langDuDetail.getDown_file_md5();
            }

            return view;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }
    }
}
