package com.jiit.minor2.shubhamjoshi.human.Adapters;

import android.animation.Animator;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.Transition;
import android.view.View;
import android.view.ViewAnimationUtils;

import com.jiit.minor2.shubhamjoshi.human.R;

import io.github.hendraanggrian.circularrevealanimator.CircularRevealAnimator;

public class GetIp extends AppCompatActivity {




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_ip);
        CircularRevealAnimator.of(this).onCreate(R.id.text);

    }

    @Override
    public void onBackPressed() {
        CircularRevealAnimator.of(this).onBackPressed(R.id.text);
    }

}

