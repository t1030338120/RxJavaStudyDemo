package com.app.rxjava.loader;

import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.app.rxjava.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public class LoaderStudyActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<String>{

    @Bind(R.id.tv_loader_content)
    TextView mTvLoaderContent;

    String value = "原始数据";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loader_study);
        ButterKnife.bind(this);

        getSupportLoaderManager().initLoader(0, null, this);
    }



    public void click(View view){
//        getSupportLoaderManager().restartLoader(0, null, this);
//        getSupportLoaderManager().destroyLoader(0);

        getSupportLoaderManager().restartLoader(0, null, this);
    }


    @Override
    public Loader onCreateLoader(int id, Bundle args) {
        System.out.println("=========LoaderManager.LoaderCallbacks ：onCreateLoader( )执行了");
//        return new TastLoader(this);
        return new TastAsyncLoader(this);
    }

    @Override
    public void onLoadFinished(Loader loader, String data) {
        System.out.println("=========LoaderManager.LoaderCallbacks ：onLoadFinished( )执行了");
        mTvLoaderContent.setText(data);
    }

    @Override
    public void onLoaderReset(Loader loader) {

        System.out.println("=========LoaderManager.LoaderCallbacks ：onLoaderReset( )执行了");
        value = "原始数据";
    }





}
