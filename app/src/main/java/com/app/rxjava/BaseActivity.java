package com.app.rxjava;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.widget.FrameLayout;

public class BaseActivity extends AppCompatActivity {

    private FrameLayout flContentContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        initView();
    }


    private void initView(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("RxJava主页");
        flContentContainer = (FrameLayout) findViewById(R.id.fl_content_container);
    }


    protected void setCusContentView(int layoutId){
        flContentContainer.addView(LayoutInflater.from(this).inflate(layoutId, null));
    }

}
