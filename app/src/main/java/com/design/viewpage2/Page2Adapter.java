package com.design.viewpage2;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

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

    private int number=0;

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
        vHolder.mImageView.setImageResource(R.mipmap.icon);
        //Glide.with(mContext).load(mDatas.get(position)).into(vHolder.mImageView);
        vHolder.mImageView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("MDY", "onClick------position="+position);
            }
        });
        vHolder.mTextView.setText(""+number++);
    }

    @Override
    public int getItemCount() {
        return 100;
    }

    class Page2Holder extends ViewHolder {

        private ImageView mImageView;
        private TextView  mTextView;

        public Page2Holder(@NonNull View itemView) {
            super(itemView);
            mTextView=itemView.findViewById(R.id.text);
            mImageView = itemView.findViewById(R.id.image);
        }
    }
}
