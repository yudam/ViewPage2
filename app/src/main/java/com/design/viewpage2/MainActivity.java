package com.design.viewpage2;

import android.os.Bundle;

import com.banner.pbanner.DefaultPageIndicatorformer;
import com.banner.pbanner.PBanner;

import java.util.LinkedList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MainActivity extends AppCompatActivity {

    private PBanner      banner;
    private RecyclerView recycler;

    private DefaultPageIndicatorformer indicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        indicator=findViewById(R.id.indicator);

        banner = findViewById(R.id.banner);
        banner.setPageIndicator(indicator);
        banner.setAdapter(TestAction::new,getDatas());
        banner.startTurning(2000);

//        recycler =findViewById(R.id.recycler);
//        LinearLayoutManager layoutManager=new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
//        recycler.setLayoutManager(layoutManager);
//        recycler.setClipToPadding(false);
//        Page2Adapter page3Adapter = new Page2Adapter(this, getDatas());
//        recycler.setAdapter(page3Adapter);
//        FPageSnapHelper fPageSnapHelper=new FPageSnapHelper(recycler);
//        fPageSnapHelper.attachToRecyclerView(recycler);
////        PagerSnapHelper pagerSnapHelper=new PagerSnapHelper();
////        pagerSnapHelper.attachToRecyclerView(recycler);
////        LinearSnapHelper linearSnapHelper=new LinearSnapHelper();
////        linearSnapHelper.attachToRecyclerView(recycler);

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
     *  findSnapView  找到需要显示在指定位置的targetView
     *  calculateDistanceToFinalSnap  滑动停止后 计算targetView滚动到指定位置的距离
     *  findTargetSnapPosition  手指放开后，计算滑动的距离，然后得出需要滚动的pos位数。
     *                          调用findSnapView方法得出指定View，得到该View的Pos+
     *                          之前计算的滑动的Pos位数，传递给RecyclerView
     */
}
