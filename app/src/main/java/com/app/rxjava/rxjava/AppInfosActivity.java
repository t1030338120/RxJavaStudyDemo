package com.app.rxjava.rxjava;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.app.rxjava.R;
import com.app.rxjava.rxjava.entity.AppInfo;
import com.app.rxjava.rxjava.fragment.AppInfoFragment;

/**
 * RxJava的学习Demo
 * 查询手机上所有的app信息
 */
public class AppInfosActivity extends AppCompatActivity implements AppInfoFragment.OnListFragmentInteractionListener {

    String tag = "recyleView";
    static String TAG = "RxJavaDemo";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycel_view);
        if (getSupportFragmentManager().findFragmentByTag(tag) == null) {
            getSupportFragmentManager().beginTransaction().add(R.id.content, new AppInfoFragment(), tag).commit();
        }

    }

    @Override
    public void onListFragmentInteraction(AppInfo item) {
        Toast.makeText(this, "应用名称：" + item.appName, Toast.LENGTH_LONG).show();
    }


}
