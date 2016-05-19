package com.app.rxjava.rxjava.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.rxjava.R;
import com.app.rxjava.rxjava.adapter.AppInfoRecyclerViewAdapter;
import com.app.rxjava.rxjava.entity.AppInfo;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * RxJava的学习Demo
 * RxJava查询手机上所有的应用appx信息
 */
public class AppInfoFragment extends Fragment {

    private int mColumnCount = 1;
    private OnListFragmentInteractionListener mListener;
    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private String TAG = "ItemFragment";
    private ArrayList<AppInfo> mDatas;
    private AppInfoRecyclerViewAdapter mAdapter;

    CompositeSubscription mCompositeSubscription = new CompositeSubscription();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item_list, container, false);

        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swiperefreshlayout);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.list);
        Context context = view.getContext();
        if (mColumnCount <= 1) {
            mRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        } else {
            mRecyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
        }

        mDatas = new ArrayList<>();
        mAdapter = new AppInfoRecyclerViewAdapter(mDatas, (OnListFragmentInteractionListener) getActivity());
        mRecyclerView.setAdapter(mAdapter);

        return view;
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getAppsSortList()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(appInfos -> {
                            Log.i("Thread", "subsriber的线程：" + Thread.currentThread().getName());
                            mDatas.addAll(appInfos);
                            mAdapter.notifyDataSetChanged();
                            mSwipeRefreshLayout.setRefreshing(false);
                            for (AppInfo appInfo : appInfos) {
                                Log.i(TAG, "app应用信息名称：" + appInfo.appName + "  size : " + appInfo.appIcon.getIntrinsicWidth());
                            }
                        }, throwable -> Log.i(TAG, "只能在UI线程刷新界面"));
            }
        });
    }

    public Observable<List<AppInfo>> getApps() {

        Observable<List<ResolveInfo>> observable = Observable
                .create(subscriber -> {
                    PackageManager pm = getActivity().getPackageManager();
                    Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
                    mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);
                    List<ResolveInfo> infos = pm.queryIntentActivities(mainIntent, PackageManager.MATCH_DEFAULT_ONLY);
                    Log.i(TAG, "app应用信息 size：" + infos.size());
                    Log.i("Thread", "observable的线程：" + Thread.currentThread().getName());
                    subscriber.onNext(infos);
                    subscriber.onCompleted();
                });

        return observable
                .flatMap(resolveInfos -> {
                    List<AppInfo> datas = new ArrayList<AppInfo>();
                    for (ResolveInfo resolveInfo : resolveInfos) {
                        String appLabel = (String) resolveInfo.loadLabel(getActivity().getPackageManager()); // 获得应用程序的Label
                        Drawable icon = resolveInfo.loadIcon(getActivity().getPackageManager()); // 获得应用程序图标
                        datas.add(new AppInfo(appLabel, icon));
                    }
                    return Observable.just(datas);
                });
    }


    public Observable<List<AppInfo>> getAppsSortList() {

        Observable<List<ResolveInfo>> observable = Observable
                .create(subscriber -> {
                    PackageManager pm = getActivity().getPackageManager();
                    Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
                    mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);
                    List<ResolveInfo> infos = pm.queryIntentActivities(mainIntent, PackageManager.MATCH_DEFAULT_ONLY);
                    Log.i(TAG, "app应用信息 size：" + infos.size());
                    Log.i("Thread", "observable的线程：" + Thread.currentThread().getName());
                    subscriber.onNext(infos);
                    subscriber.onCompleted();
                });

        return observable
                .flatMap(resolveInfos -> Observable.from(resolveInfos))
                .flatMap(resolveInfo -> {
                    final String appLabel = (String) resolveInfo.loadLabel(getActivity().getPackageManager()); // 获得应用程序的Label
                    Drawable icon = resolveInfo.loadIcon(getActivity().getPackageManager()); // 获得应用程序图标
                    return Observable.just(new AppInfo(appLabel, icon)).filter(new Func1<AppInfo, Boolean>() {
                        @Override
                        public Boolean call(AppInfo appInfo) {
                            return !appLabel.startsWith("L");
                        }
                    });
                })
                        //                .take(3)
                        //                .repeat(3)
                        //                .first()
                        //                .last()
                        //                .skip(2)
                        //                .skipLast(3)
                        //                .elementAt(5)
                .toSortedList();
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction(AppInfo item);
    }




}
