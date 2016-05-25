package com.app.rxjava.rxjava_3_cache;

import android.os.Bundle;
import android.view.View;

import com.app.rxjava.BaseActivity;
import com.app.rxjava.R;

public class RxJavaCacheMainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setCusContentView(R.layout.activity_rx_java_cache_main);
    }

    public void btnClick(View view) {
        switch (view.getId()) {
            case R.id.btn_cache_01:
                CacheManager.getData();
                break;
        }
    }
}
