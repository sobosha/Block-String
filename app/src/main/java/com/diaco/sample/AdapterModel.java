package com.diaco.sample;

import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.os.Looper;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.ScaleAnimation;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.diaco.sample.Dialog.story;
import com.diaco.sample.Setting.CustomClasses.CustomAdapter;
import com.diaco.sample.Setting.CustomClasses.CustomRel;
import com.diaco.sample.Story.Stories;
import com.google.android.material.progressindicator.CircularProgressIndicator;
import com.qintong.library.InsLoadingView;

import java.util.List;

public class AdapterModel extends RelativeLayout {

    public AdapterModel(Context context) {
        super(context);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.item_rec_story, this, true);

    }

    public void onClickStory(StoryModel item, int selectItem, int Position, CustomAdapter customAdapter, List<StoryModel> list){
        InsLoadingView instaLoading=findViewById(R.id.instaLoading);
        instaLoading.setRotateDuration(1000);
        instaLoading.setCircleDuration(2000);
        if(item.seen){
            instaLoading.setStatus(InsLoadingView.Status.CLICKED);
            instaLoading.setStartColor(Color.GRAY);
            instaLoading.setEndColor(Color.GRAY);
        }else{
            instaLoading.setStatus(InsLoadingView.Status.UNCLICKED);
        }
        instaLoading.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                instaLoading.setStatus(InsLoadingView.Status.LOADING);
                Handler handler=new Handler(Looper.getMainLooper());
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        instaLoading.setStatus(InsLoadingView.Status.CLICKED);
                        MainActivity.getGlobal().FinishFragStartFrag(new FragStory(item,customAdapter,list));
                    }
                },2000);
            }
        });

    }
}
