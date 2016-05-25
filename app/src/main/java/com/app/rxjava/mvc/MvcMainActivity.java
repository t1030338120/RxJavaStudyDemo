package com.app.rxjava.mvc;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.app.rxjava.BaseActivity;
import com.app.rxjava.R;
import com.app.rxjava.mvc.model.IMvcIpInfoModel;
import com.app.rxjava.mvc.model.MvcIpInfoModelImpl;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MvcMainActivity extends BaseActivity {

    @Bind(R.id.edt_mvc_ip)
    EditText mEdtIp;
    @Bind(R.id.btn_mvc_01)
    Button mButton;
    @Bind(R.id.tv_mvc_detail)
    TextView mTvIpDetail;
    private IMvcIpInfoModel mMvcIpInfoModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setCusContentView(R.layout.activity_mvc_main);
        ButterKnife.bind(this);
        initData();
    }

    private void initData(){
        mMvcIpInfoModel = new MvcIpInfoModelImpl();
    }


    @OnClick(R.id.btn_mvc_01)
    public void onClick() {
        mMvcIpInfoModel.getIpInfo(mEdtIp.getText().toString())
                .subscribe(ipInfoEntity -> mTvIpDetail.setText(ipInfoEntity.toString())
                        , throwable -> System.out.println("==错误处理 : "+throwable.getMessage()));
    }
}
