package com.xheghun.vidit.classes;

import android.content.Context;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.xheghun.vidit.R;

public class Animate {

    public static void fadeOutAnimation(View view,Context context) {
        Animation animation = AnimationUtils.loadAnimation(context,R.anim.fade_out);
        view.startAnimation(animation);
        view.setVisibility(View.GONE);
   }

   public static void fadeInAnimation (View view,Context context) {
        view.setVisibility(View.VISIBLE);
        Animation animation = AnimationUtils.loadAnimation(context,R.anim.fade_in);
        view.startAnimation(animation);
   }

   public static void slideDown(View view, Context context) {
        Animation animation = AnimationUtils.loadAnimation(context,R.anim.slide_down);
        view.startAnimation(animation);
        view.setVisibility(View.GONE);
   }

   public static void slideUp(View view, Context context) {
        view.setVisibility(View.VISIBLE);
       Animation animation = AnimationUtils.loadAnimation(context, R.anim.slide_up);
       view.startAnimation(animation);
   }

}
