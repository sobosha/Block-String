package com.diaco.sample;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.diaco.sample.Core.Presenter;
import com.diaco.sample.Setting.CustomClasses.CustomRel;
import com.diaco.sample.Setting.CustomSnackBar;
import com.diaco.sample.Setting.Setting;
import com.diaco.sample.Setting.mAnimation;
import com.diaco.sample.Setting.mFragment;

import org.jetbrains.annotations.NotNull;

import io.github.inflationx.viewpump.ViewPumpContextWrapper;

import static android.view.View.VISIBLE;

public class MainActivity extends AppCompatActivity {

    private static MainActivity global;

    public static MainActivity getGlobal() {
        return global;
    }

    public MainActivity() {
        global = this;
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FinishFragStartFrag(new fragment_test());

    }

    CustomRel customRel ;
    public boolean isRelShow ;
    public void FinishRelStartRel (CustomRel customRel) {
        isRelShow = true;
        this.customRel = customRel;
        ((RelativeLayout) findViewById(R.id.relMainDialogs)).removeAllViews();
        findViewById(R.id.relMainDialogs).setVisibility(VISIBLE);
        findViewById(R.id.imgBlackMain).setVisibility(VISIBLE);
        mAnimation.myFadeIn(findViewById(R.id.relMainDialogs), 0, 300);
        ((RelativeLayout) findViewById(R.id.relMainDialogs)).addView(customRel);

        Animation fadeOut = new AlphaAnimation(0, 1);
        fadeOut.setInterpolator(new AccelerateInterpolator()); //and this
        fadeOut.setDuration(200);

        AnimationSet animation = new AnimationSet(false); //change to false
        animation.addAnimation(fadeOut);
        customRel.clearAnimation();
        customRel.startAnimation(animation);
        findViewById(R.id.imgBlackMain).setOnClickListener(view -> HideMyDialog());

    }

    public void HideMyDialog() {
        Set_anim.dis(getApplicationContext(), customRel, R.anim.fade_hide);
        isRelShow=false;
    }

    public mFragment getCurrentFragment() {
        return currentFragment;
    }

    public void setCurrentFragment(mFragment currentFragment) {
        this.currentFragment = currentFragment;
    }

    private mFragment currentFragment;

    public void FinishFragStartFrag(mFragment newFragment) {
        Presenter.get_global().cancelReq();
        currentFragment = newFragment;
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.setCustomAnimations(R.anim.fade_show, R.anim.fade_hide);
        ft.replace(R.id.relMaster, newFragment);
        ft.addToBackStack(null);
        ft.commitAllowingStateLoss();
    }
    public void FinishFragStartFrag(mFragment newFragment,int StartAnim,int EndAnim,View v) {
        Presenter.get_global().cancelReq();
        currentFragment = newFragment;
        FragmentManager fm = getSupportFragmentManager();
        MainActivity.getGlobal().findViewById(R.id.relMaster).setPivotX(v.getPivotX());
        MainActivity.getGlobal().findViewById(R.id.relMaster).setPivotY(v.getPivotY());
        FragmentTransaction ft = fm.beginTransaction();
        ft.setCustomAnimations(StartAnim,EndAnim);
        ft.replace(R.id.relMaster, newFragment);
        ft.addToBackStack(null);
        ft.commitAllowingStateLoss();
    }

    @Override
    public void onBackPressed() {
        currentFragment.mBackPressed();
    }


    public boolean isMainDialogShow = false;

    public void MainDiallog(String type) {
        ((RelativeLayout) findViewById(R.id.relMainDialogs)).removeAllViews();
        isMainDialogShow = true;
        findViewById(R.id.relMainDialogs).setVisibility(VISIBLE);
        findViewById(R.id.imgBlackMain).setVisibility(VISIBLE);
        switch (type) {
        }

        mAnimation.myTransToLeft(findViewById(R.id.relMainDialogs), 0, 500, 2, 0);
        findViewById(R.id.imgBlackMain).setOnClickListener(view -> {
            hideMainDialogs();
        });
    }

    public void hideMainDialogs() {
        isMainDialogShow = false;
        findViewById(R.id.relMainDialogs).setVisibility(View.GONE);
        findViewById(R.id.imgBlackMain).setVisibility(View.GONE);
        mAnimation.myTransToLeft(findViewById(R.id.relMainDialogs), 0, 500, 0, 2).setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                findViewById(R.id.relMainDialogs).clearAnimation();
                ((RelativeLayout) findViewById(R.id.relMainDialogs)).removeAllViews();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });

        findViewById(R.id.imgBlackMain).setOnClickListener(view -> hideMainDialogs());

    }


    boolean canShowSnack = true, showSnack;
    Handler handler;
    Runnable runnable;

    public void showSnackBar(String type, String desc, int duration) {
        if (canShowSnack) {
            showSnack = true;
            canShowSnack = false;
            findViewById(R.id.relSnackBar).setVisibility(VISIBLE);
            if (type.equals("accept") || type.equals("pending") || type.equals("reject") || type.equals("warning") || type.equals("normal")) {
                mAnimation.myTrans_ToTopSnack(findViewById(R.id.relSnackBar), 0, 500);
                ((RelativeLayout) findViewById(R.id.relSnackBar)).addView(new CustomSnackBar(this, type, desc));
                if (handler == null)
                    handler = new Handler(Looper.getMainLooper());
                runnable = () -> hideSnackBar(false);
                handler.postDelayed(runnable, duration);
            } else {
                this.type = desc;
                mAnimation.myTrans_ToBottom(findViewById(R.id.relSnackBar), 0, 1500);
                ((RelativeLayout) findViewById(R.id.relSnackBar)).addView(new CustomSnackBar(this, type, desc));
            }
        }

    }

    String type = "";

    public void hideSnackBar(boolean isTop) {
        showSnack = false;
        if (isTop) {
            mAnimation.myTrans_ToBottomBAck(findViewById(R.id.relSnackBar), 0, 1000).setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    ((RelativeLayout) findViewById(R.id.relSnackBar)).removeAllViews();
                    findViewById(R.id.relSnackBar).setVisibility(View.GONE);
                    findViewById(R.id.relSnackBar).clearAnimation();
                    if (handler != null)
                        handler.removeCallbacks(runnable);
                    canShowSnack = true;
                    if (type.length() > 0) {
                        MainDiallog(type);
                        type = "";
                    }
                }

                @Override
                public void onAnimationRepeat(Animation animation) {
                }
            });
        } else {
            mAnimation.myTrans_ToBottom(findViewById(R.id.relSnackBar), 0, 500, 0.15f).setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    ((RelativeLayout) findViewById(R.id.relSnackBar)).removeAllViews();
                    findViewById(R.id.relSnackBar).setVisibility(View.GONE);
                    findViewById(R.id.relSnackBar).clearAnimation();
                    if (handler != null)
                        handler.removeCallbacks(runnable);
                    canShowSnack = true;
                }

                @Override
                public void onAnimationRepeat(Animation animation) {
                }
            });
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull @NotNull String[] permissions, @NonNull @NotNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case 1:
                if (grantResults.length > 0 &&
                        grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    Setting.Download(MainActivity.getGlobal(),"https://namatek.com/wp-content/uploads/2020/04/%D9%85%D9%82%D8%A7%D9%88%D9%85%D8%AA-%D8%A7%D9%84%DA%A9%D8%AA%D8%B1%DB%8C%DA%A9%DB%8C.pdf", Environment.DIRECTORY_DOWNLOADS);
                }
                else{
                    MainActivity.getGlobal().showSnackBar("warning","برای دانلود به دسترسی نیاز داریم",1000);
                }
                return;

        }
    }



}