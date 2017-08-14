package com.seowang.mynewstruct.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.Toast;

import com.seowang.mynewstruct.R;
import com.seowang.mynewstruct.base.BaseFragment;
import com.seowang.mynewstruct.factory.FragmentFactory;

/**
 * Created by hm on 2016/4/7.
 */
public class MainActivity extends FragmentActivity implements View.OnClickListener{

    /**
     * 关于界面的控件的定义
     */
    private FrameLayout frame_mainactivity_fragment;
    private RadioButton radiobut_activitymain_home;
    private RadioButton radiobut_activitymain_product;
    private RadioButton radiobut_activitymain_user;
    private RadioButton radiobut_activitymain_more;

    //定义BaseFragment，辅助完成FragmentFactory的操作
    private BaseFragment baseFragment;

    //定义表示当前类的上下文对象
    private Context mContext=MainActivity.this;

    /**
     * 关于登录点击位置的设置操作
     */
    private static final int HOMEFRAGMENT=0;
    private static final int PRODUCTFRAGMENT=1;
    private static final int USERFRAGMENT=2;
    private static final int MOREFRAGMENT=3;


    private static final int ERROR=1;
    private Handler mHandler=new Handler() {
        @Override
        public void handleMessage(Message msg) {

            switch (msg.what){
                case ERROR:
                    int i=1/0;
                    break;


            }

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        frame_mainactivity_fragment=(FrameLayout) findViewById(R.id.frame_mainactivity_fragment);
        radiobut_activitymain_home=(RadioButton) findViewById(R.id.radiobut_activitymain_home);
        radiobut_activitymain_product=(RadioButton) findViewById(R.id.radiobut_activitymain_product);
        radiobut_activitymain_user=(RadioButton) findViewById(R.id.radiobut_activitymain_user);
        radiobut_activitymain_more=(RadioButton) findViewById(R.id.radiobut_activitymain_more);

        radiobut_activitymain_home.setOnClickListener(this);
        radiobut_activitymain_product.setOnClickListener(this);
        radiobut_activitymain_user.setOnClickListener(this);
        radiobut_activitymain_more.setOnClickListener(this);

        radiobut_activitymain_home.setChecked(true);
        crateFragment(0);

//        Message msg=Message.obtain();
//        mHandler.sendEmptyMessageDelayed(ERROR,5000);
    }

    @Override
    public void onClick(View v) {
        /**
         * 根据不同的点击创建不同的Fragment
         * 一个逻辑需求：
         * ？？用户从任何模块进入到登陆模块，在登录模块点击返回操作的时候，要返回到前一次的位置
         * 思路：设置一个sp变量存储点击所在的位置，当用户点击登录位置的返回操作的时候，读取该位置信息，并返回到之前的界面
         */
        switch(v.getId()){

            case R.id.radiobut_activitymain_home:

                crateFragment(0);
                break;
            case R.id.radiobut_activitymain_product:

                crateFragment(1);
                break;
            case R.id.radiobut_activitymain_user:
                Intent intent=new Intent(mContext,LoginActivity.class);
                startActivityForResult(intent, LOGIN);

                break;
            case R.id.radiobut_activitymain_more:
                crateFragment(3);
                break;
        }

    }

    private static final int LOGIN=1;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case LOGIN:
                if(null!=data){
                    boolean bl=data.getBooleanExtra("AA",false);
                    System.out.println("BaseFragment::Intent中保存的值"+bl);
                    if(bl){
                        crateFragment(2);
                    }
                }

                break;
        }
    }

    /**
     * 该方法完成的操作是将上部分的FrameLayout使用创建的Fragment进行替换
     * @param checkedId
     */
    private void crateFragment(int checkedId){
        baseFragment=(BaseFragment) FragmentFactory.createMainActiviryFragment(checkedId);
        if(null!=baseFragment){

            System.out.println("BaseFragment==========替换了新的Fragment");

            /**
             * getSupportFragmentManager()方法在继承自FragmentActivity的Activity中才可以使用
             */

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.frame_mainactivity_fragment,baseFragment).commitAllowingStateLoss();
        }else{
            Toast.makeText(this, "空的", Toast.LENGTH_LONG).show();
        }
    }
}
