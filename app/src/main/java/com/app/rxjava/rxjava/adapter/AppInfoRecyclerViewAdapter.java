package com.app.rxjava.rxjava.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.rxjava.R;
import com.app.rxjava.rxjava.entity.AppInfo;
import com.app.rxjava.rxjava.fragment.AppInfoFragment;

import java.util.List;

public class AppInfoRecyclerViewAdapter extends RecyclerView.Adapter<AppInfoRecyclerViewAdapter.ViewHolder> {

    private final List<AppInfo> mValues;
    private final AppInfoFragment.OnListFragmentInteractionListener mListener;

    public AppInfoRecyclerViewAdapter(List<AppInfo> items, AppInfoFragment.OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mAppInfo = mValues.get(position);
        holder.tvAppName.setText(mValues.get(position).appName);
        holder.imgAppIcon.setImageDrawable(mValues.get(position).appIcon);

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mAppInfo);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final ImageView imgAppIcon;
        public final TextView tvAppName;
        public AppInfo mAppInfo;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            tvAppName = (TextView) view.findViewById(R.id.app_name);
            imgAppIcon = (ImageView) view.findViewById(R.id.icon);
        }

    }
}
