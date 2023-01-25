package com.diaco.sample;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.diaco.sample.Dialog.story;
import com.diaco.sample.Setting.CustomClasses.CustomAdapter;
import com.diaco.sample.Setting.CustomClasses.CustomFragment;

import java.util.Collections;
import java.util.List;

import jp.shts.android.storiesprogressview.StoriesProgressView;

public class FragStory extends CustomFragment implements StoriesProgressView.StoriesListener {
    ImageView Story;
    //https://github.com/shts/StoriesProgressView/issues/12
    StoriesProgressView progressBar;
    StoryModel item;
    CustomAdapter customAdapter;
    TextView Link;
    int i=0;
    long time=0;
    List<StoryModel> list;

    public FragStory( StoryModel storyModel, CustomAdapter customAdapter, List<StoryModel> list) {
        this.customAdapter=customAdapter;
        this.list=list;
        item=storyModel;
    }

    @Override
    public int layout() {
        return R.layout.dialog_story;
    }

    @Override
    public void onCreateMyView() {
        Link=parent.findViewById(R.id.btnLink);
        if(!item.getLink().equals("")){
            Link.setVisibility(View.VISIBLE);
        }
        Story=parent.findViewById(R.id.ImageStory);
        progressBar=parent.findViewById(R.id.ProgressStory);
        progressBar.setStoriesCount(item.getSrc().size());
        progressBar.setStoryDuration(5000L);
        progressBar.setStoriesListener(this);
        item.setSeen(true);
        Glide.with(getContext())
                .load(item.src.get(i))
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable @org.jetbrains.annotations.Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        progressBar.startStories();
                        return false;
                    }
                })
                .into(Story);
        Story.setOnTouchListener(new View.OnTouchListener() {
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

        Story.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        Link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(item.getLink()));
                getContext().startActivity(browserIntent);
            }
        });

    }

    @Override
    public void onNext() {
        i++;
        if(item.getSrc().size()>i) {
            Glide.with(getContext())
                    .load(item.src.get(i))
                    .listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable @org.jetbrains.annotations.Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            progressBar.startStories(i);
                            progressBar.resume();
                            return false;
                        }
                    })
                    .into(Story);

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
        item.seen=true;
        if(list.indexOf(item)<list.size()-1){
            MainActivity.getGlobal().FinishFragStartFrag(new FragStory(list.get(list.indexOf(item)+1),customAdapter,list));
        }
        else {
            Collections.sort(list);
            customAdapter.notifyDataSetChanged();
            MainActivity.getGlobal().FinishFragStartFrag(new fragment_test());
        }
    }

    @Override
    public void mBackPressed() {
        super.mBackPressed();
        Collections.sort(list);
        customAdapter.notifyDataSetChanged();
        MainActivity.getGlobal().FinishFragStartFrag(new fragment_test());
    }
}
