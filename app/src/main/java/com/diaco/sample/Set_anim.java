package com.diaco.sample;

import android.content.Context;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.widget.RelativeLayout;

import androidx.core.view.ViewCompat;

import com.diaco.sample.Setting.CustomClasses.CustomRel;

public class Set_anim {
    public static void set(final Context context, View view, int sss){
        Animation a = AnimationUtils.loadAnimation(context,sss);
        a.reset();
        view.clearAnimation();
        view.startAnimation(a);
        a.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                view.clearAnimation();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }
    public static void set_Alpha(CustomRel customRel, RelativeLayout rel_type_namaz, RelativeLayout BAck_general_dasty, String type){

        Animation fadeOut = new AlphaAnimation(1, 0);
        fadeOut.setInterpolator(new AccelerateInterpolator()); //and this
        fadeOut.setDuration(200);
        AnimationSet animation = new AnimationSet(false); //change to false
        animation.addAnimation(fadeOut);
        customRel.clearAnimation();
        customRel.startAnimation(animation);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {



            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }
    public static  void Setaalph(View view,boolean out_in){
        Animation fadeIn = new AlphaAnimation(0, 1);
        fadeIn.setInterpolator(new DecelerateInterpolator()); //add this
        fadeIn.setDuration(300);

        Animation fadeOut = new AlphaAnimation(1, 0);
        fadeOut.setInterpolator(new AccelerateInterpolator()); //and this
        fadeOut.setStartOffset(1000);
        fadeOut.setDuration(1000);
        AnimationSet animation = new AnimationSet(false); //change to false
        if (true){
            animation.addAnimation(fadeIn);
        }else {
            animation.addAnimation(fadeOut);
        }
        view.clearAnimation();
        view.setAnimation(animation);


    }
    public static void dis(final Context context, View view, int sss){
        Animation fadeOut = new AlphaAnimation(1, 0);
        fadeOut.setInterpolator(new AccelerateInterpolator()); //and this
        fadeOut.setDuration(200);

        AnimationSet animation = new AnimationSet(false); //change to false
        animation.addAnimation(fadeOut);
        view.clearAnimation();
        view.startAnimation(animation);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                MainActivity.getGlobal().findViewById(R.id.relMainDialogs)
                        .setVisibility(View.GONE);
                MainActivity.getGlobal().findViewById(R.id.imgBlackMain)
                        .setVisibility(View.GONE);
                ((RelativeLayout) MainActivity.getGlobal()
                        .findViewById(R.id.relMainDialogs)).removeAllViews();
                MainActivity.getGlobal()
                        .findViewById(R.id.relMainDialogs).clearAnimation();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }
}
