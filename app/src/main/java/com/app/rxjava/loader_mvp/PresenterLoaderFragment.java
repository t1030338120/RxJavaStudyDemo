package com.app.rxjava.loader_mvp;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.rxjava.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class PresenterLoaderFragment extends Fragment implements TastView<String>, LoaderManager.LoaderCallbacks<Presenter>{
    @Override
    public String getData() {
        return "数据获取正常";
    }

    private static final int LOADER_ID = 101;
    private Presenter presenter;

    @Override
    public void onStart() {
        super.onStart();
        if(presenter != null){
            presenter.onViewAttached(this);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_presenter_loader, container, false);
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // 初始化代码
        getLoaderManager().initLoader(LOADER_ID, null, this);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if(presenter != null){
            presenter.onDestroyed();
        }
    }

    @Override
    public Loader<Presenter> onCreateLoader(int id, Bundle args) {
        return null;
    }

    @Override
    public void onLoadFinished(Loader<Presenter> loader, Presenter data) {
        this.presenter = data;
    }

    @Override
    public void onLoaderReset(Loader<Presenter> loader) {
        presenter = null;
    }

}
