package com.diaco.sample.Dialog;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.Looper;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.diaco.sample.MainActivity;
import com.diaco.sample.R;
import com.diaco.sample.Setting.CustomClasses.CustomAdapter;
import com.diaco.sample.Setting.CustomClasses.CustomRel;
import com.diaco.sample.StoryModel;
import com.diaco.sample.fragment_test;

import org.w3c.dom.Text;

import java.util.Collections;
import java.util.List;
import java.util.logging.Handler;

import jp.shts.android.storiesprogressview.StoriesProgressView;

public class story extends CustomRel implements StoriesProgressView.StoriesListener {
    ImageView Story;
    //https://github.com/shts/StoriesProgressView/issues/12
    StoriesProgressView progressBar;
    StoryModel item;
    CustomAdapter customAdapter;
    TextView Link;
    int i=0;
    long time=0;
    List<StoryModel> list;
    public story(Context context, StoryModel item, CustomAdapter customAdapter, List<StoryModel> list) {
        super(context, R.layout.dialog_story);
        Link=findViewById(R.id.btnLink);
        if(!item.getLink().equals("")){
            Link.setVisibility(VISIBLE);
        }
        Story=findViewById(R.id.ImageStory);
        progressBar=findViewById(R.id.ProgressStory);
        progressBar.setStoriesCount(item.getSrc().size());
        progressBar.setStoryDuration(5000L);
        progressBar.setStoriesListener(this);
        progressBar.startStories();
        item.setSeen(true);
        Story.setImageResource(Integer.parseInt(item.getSrc().get(i)));
        Story.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == android.view.MotionEvent.ACTION_DOWN ) {
                    progressBar.pause();
                    time=System.currentTimeMillis();
                } else
                if(event.getAction() == android.view.MotionEvent.ACTION_UP){

                    if(System.currentTimeMillis()-time<200){
                        onNext();
                    }
                    else{
                        progressBar.resume();
                    }
                }
                return false;
            }
        });

        Story.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        Link.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(item.getLink()));
                getContext().startActivity(browserIntent);
            }
        });
        this.item=item;
        this.customAdapter=customAdapter;
        this.list=list;
    }

    @Override
    public void onNext() {
        i++;
        progressBar.resume();
        if(item.getSrc().size()>i) {
            progressBar.startStories(i);
            Story.setImageResource(Integer.parseInt(item.getSrc().get(i)));
        }
        else{
            onComplete();
        }
    }

    @Override
    public void onPrev() {

    }

    @Override
    public void onComplete() {
        if(list.indexOf(item)<list.size()-1){
            MainActivity.getGlobal().FinishRelStartRel(new story(getContext(),list.get(list.indexOf(item)+1),customAdapter,list));
        }
        else {
            Collections.sort(list);
            customAdapter.notifyDataSetChanged();
            MainActivity.getGlobal().HideMyDialog();
        }

    }
}
