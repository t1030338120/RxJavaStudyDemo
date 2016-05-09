package com.app.rxjava.mvp.ui;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.RelativeLayout;

import com.app.rxjava.R;
import com.app.rxjava.mvp.common.AutoLoadMoreRecylerView;
import com.app.rxjava.mvp.common.DividerItemDecoration;
import com.app.rxjava.mvp.common.OnListItemClickListener;
import com.app.rxjava.mvp.model.ContentlistEntity;
import com.app.rxjava.mvp.presenter.JokePresenter;
import com.app.rxjava.mvp.ui.adapter.JokeAdapter;
import com.app.rxjava.mvp.ui.view.IJokeView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * MVP 学习Demo
 */
public class MvpDemoMainActivity extends BaseMvpActivity implements
        SwipeRefreshLayout.OnRefreshListener, AutoLoadMoreRecylerView.OnLoadMoreListener,
        OnListItemClickListener<ContentlistEntity>, IJokeView {

    @Bind(R.id.record_recycleview)
    AutoLoadMoreRecylerView mLoadMoreRecycleview;
    @Bind(R.id.common_error)
    RelativeLayout commonError;
    @Bind(R.id.joke_refresh_layout)
    SwipeRefreshLayout mJokeRefreshLayout;
    private JokePresenter mJokePresenter;
    private LinearLayoutManager layoutManager;
    private int page = 1;
    private List<ContentlistEntity> mJokeList;
    private JokeAdapter mJokeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mvp_demo_main);
        ButterKnife.bind(this);
        initView();
        initData();
        loadData();
    }


    private void initView() {
        mJokeRefreshLayout.setOnRefreshListener(this);
        layoutManager = new LinearLayoutManager(this);
        mLoadMoreRecycleview.setLayoutManager(layoutManager);
        mLoadMoreRecycleview.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration
                .VERTICAL_LIST));
        mLoadMoreRecycleview.setLoadMoreListener(this);
    }

    private void initData() {
        mJokeList = new ArrayList<>();
        mJokeAdapter = new JokeAdapter(mJokeList, this);
        mLoadMoreRecycleview.setAdapter(mJokeAdapter);

        mJokePresenter = new JokePresenter();
        mJokePresenter.attachView(this);
    }

    private void loadData() {
        mJokePresenter.loadMore(page);
        page++;
    }


    @Override
    public void refresh(List<ContentlistEntity> data) {
        commonError.setVisibility(View.GONE);
        mJokeList.clear();
        mJokeList.addAll(data);
        mJokeAdapter.notifyDataSetChanged();
        mJokeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void loadMore(List<ContentlistEntity> data) {
        commonError.setVisibility(View.GONE);
        mJokeList.addAll(data);
        mJokeAdapter.notifyDataSetChanged();
        mLoadMoreRecycleview.setLoading(false);
    }

    @Override
    public void showError(String msg, View.OnClickListener onClickListener) {
        super.showError(msg, onClickListener);
        commonError.setVisibility(View.VISIBLE);
        mLoadMoreRecycleview.setLoading(false);
        mJokeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onLoadMore() {
        mJokePresenter.loadMore(page);
        page++;
    }

    @Override
    public void onRefresh() {
        mJokePresenter.refresh();
    }

    @OnClick(R.id.retry_btn)
    void setRetryBtnonClick() {
        onRefresh();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mJokePresenter.detachView();
    }


    @Override
    public void onListItemClick(ContentlistEntity item) {
        Snackbar.make(mJokeRefreshLayout, item.getTitle(), Snackbar.LENGTH_SHORT).show();
    }
}
