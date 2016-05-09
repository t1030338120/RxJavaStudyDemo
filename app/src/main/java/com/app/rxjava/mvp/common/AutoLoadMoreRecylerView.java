package com.app.rxjava.mvp.common;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;


public class AutoLoadMoreRecylerView extends RecyclerView {
    private OnLoadMoreListener loadMoreListener;
    private AutoLoadMoreScrollListener autoLoadScroller;
    private boolean isLoading = false;

    public AutoLoadMoreRecylerView(Context context) {
        this(context, null);
    }

    public AutoLoadMoreRecylerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        autoLoadScroller = new AutoLoadMoreScrollListener();
        addOnScrollListener(autoLoadScroller);
    }


    public void setLoadMoreListener(OnLoadMoreListener loadMoreListener) {
        this.loadMoreListener = loadMoreListener;
    }


    public void setLoading(boolean loading) {
        isLoading = loading;
    }

    public void removeAutoScroller() {
        removeOnScrollListener(autoLoadScroller);
    }

    private class AutoLoadMoreScrollListener extends OnScrollListener {

        boolean isScrollingToDown = true;
        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            isScrollingToDown = dy > 0;
        }

        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            if (getLayoutManager() instanceof LinearLayoutManager) {
                int lastVisiblePos = ((LinearLayoutManager) getLayoutManager()).findLastVisibleItemPosition();
                int itemCount = getAdapter().getItemCount();
                if (loadMoreListener != null && !isLoading && lastVisiblePos > itemCount - 2 && isScrollingToDown) {
                    loadMoreListener.onLoadMore();
                    isLoading = true;
                }
            }
        }
    }

    public interface OnLoadMoreListener {
        void onLoadMore();
    }
}
