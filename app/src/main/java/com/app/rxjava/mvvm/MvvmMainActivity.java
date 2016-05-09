package com.app.rxjava.mvvm;

import android.databinding.DataBindingUtil;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.app.rxjava.R;
import com.app.rxjava.databinding.ActivityMvvmMainBinding;
import com.app.rxjava.mvvm.bean.ObservableUser;
import com.app.rxjava.mvvm.bean.PlainUser;
import com.app.rxjava.mvvm.bean.User;

public class MvvmMainActivity extends AppCompatActivity {

    private ObservableUser mObserver_user;
    private ActivityMvvmMainBinding mBinding;
    private PlainUser mPlainUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_mvvm_main);

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_mvvm_main);
        User user = new User("唐永超", 26);
        mBinding.setUser(user);

        mObserver_user = new ObservableUser("唐永超", 26);
        mBinding.setObserverUser(mObserver_user);


        mPlainUser = new PlainUser(new ObservableField<>("tang"), new ObservableInt(26));
        mBinding.setPlainUser(mPlainUser);
    }


    public void btnClick(View view) {
        switch (view.getId()) {
            case R.id.btn_mvvp_01:
                mObserver_user.setName("东方");
                mObserver_user.setAge(30);
                break;
            case R.id.btn_mvvp_02:
                mPlainUser.getName().set("东方不败");
                mPlainUser.getAge().set(40);
                break;
        }
    }


}
