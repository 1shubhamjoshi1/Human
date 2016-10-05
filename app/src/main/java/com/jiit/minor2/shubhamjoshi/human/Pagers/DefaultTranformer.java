package com.jiit.minor2.shubhamjoshi.human.Pagers;

import android.support.v4.view.ViewPager;
import android.view.View;

class DefaultTransformer implements ViewPager.PageTransformer {

    @Override
    public void transformPage(View view, float position) {
        float alpha = 0;
        if (0 <= position && position <= 1) {
            alpha = 1 - position;
        } else if (-1 < position && position < 0) {
            alpha = position + 1;
        }
        System.out.println("alpha--" + alpha);
        view.setAlpha(alpha);
        System.out.println("position--" + position);
        System.out.println("view.getWidth()--" + view.getWidth());
        view.setTranslationX(view.getWidth() * -position);
        float yPosition = position * view.getHeight();
        System.out.println("yPosition---"+yPosition);
        view.setTranslationY(yPosition);
    }
}