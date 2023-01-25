package com.diaco.sample;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;

public class CustomViewStoryProgress extends RelativeLayout {
    public CustomViewStoryProgress(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.custom_story_progress,this,true);
        TypedArray a=getContext().getTheme().obtainStyledAttributes(attrs,R.styleable.Custom_progress,0,0);


    }
}
