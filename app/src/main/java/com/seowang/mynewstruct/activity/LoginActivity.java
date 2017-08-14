package com.seowang.mynewstruct.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.seowang.mynewstruct.R;

/**
 * Created by hm on 2016/4/13.
 */
public class LoginActivity extends Activity implements View.OnClickListener {

    private Button bt_submit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        bt_submit= (Button) findViewById(R.id.bt_submit);
        bt_submit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.bt_submit:

                Intent intent=new Intent();
                intent.putExtra("AA", true);
                setResult(0, intent);
                finish();
                break;

        }

    }
}
