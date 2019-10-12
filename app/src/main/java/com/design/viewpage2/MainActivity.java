package com.design.viewpage2;

import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.design.viewpage2.banner.Action;
import com.design.viewpage2.banner.PBanner;
import com.design.viewpage2.banner.PageHelperListener;

import java.util.LinkedList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;
import androidx.viewpager2.widget.ViewPager2;

public class MainActivity extends AppCompatActivity {

    private PBanner      banner;
    private RecyclerView recycler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        banner = findViewById(R.id.banner);
        banner.withAdapter(TestAction::new,getDatas());
        banner.startTurning(2000);


        recycler =findViewById(R.id.recycler);
        LinearLayoutManager layoutManager=new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        recycler.setLayoutManager(layoutManager);
        recycler.setClipToPadding(false);
        Page2Adapter page3Adapter = new Page2Adapter(this, getDatas());
        recycler.setAdapter(page3Adapter);
//        PagerSnapHelper pagerSnapHelper=new PagerSnapHelper();
//        pagerSnapHelper.attachToRecyclerView(recycler);
        LinearSnapHelper linearSnapHelper=new LinearSnapHelper();
        linearSnapHelper.attachToRecyclerView(recycler);

    }

    public List<String> getDatas() {
        List<String> list = new LinkedList<>();
        list.add("https://logpic.9158.com/userPhoto/22150636/1567859795775002.jpg");
        list.add("https://imgr.9158.com/live/72272312/132126569762107026.jpg");
        list.add("https://imgr.9158.com/live/60060328/132118881005638835.jpg");
        list.add("https://logpic.9158.com/userPhoto/25056065/15692122656030202.jpg");
        return list;
    }

    /**
     *  findSnapView
     *  calculateDistanceToFinalSnap
     *
     *  calculateDistanceToFinalSnap
     *  findTargetSnapPosition
     */
}
