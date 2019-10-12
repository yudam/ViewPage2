package com.design.viewpage2;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.design.viewpage2.Page2Adapter.Page2Holder;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;

/**
 * User: maodayu
 * Date: 2019/9/27
 * Time: 15:49
 */
public class Page2Adapter extends RecyclerView.Adapter<Page2Holder> {

    private Context      mContext;
    private List<String> mDatas;

    public Page2Adapter(Context context, List<String> mDatas) {
        this.mContext = context;
        this.mDatas = mDatas;
    }

    @NonNull
    @Override
    public Page2Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(mContext).inflate(R.layout.rv_page_item, parent,false);
        return new Page2Holder(rootView);
    }

    @Override
    public void onBindViewHolder(@NonNull Page2Holder vHolder, int position) {
        Glide.with(mContext).load(mDatas.get(position)).into(vHolder.mImageView);
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    class Page2Holder extends ViewHolder {

        private ImageView mImageView;

        public Page2Holder(@NonNull View itemView) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.image);
        }
    }
}
