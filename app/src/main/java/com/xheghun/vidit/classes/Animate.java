package com.xheghun.vidit.classes;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.view.View;

public class Animate {

    public static void fadeOutAnimation(View view) {
        ObjectAnimator fadeAnimator = ObjectAnimator.ofFloat(view, "alpha", 1.0f, 0.0f);
       fadeAnimator.setDuration(500);
       fadeAnimator.start();
       fadeAnimator.addListener(new Animator.AnimatorListener() {
           @Override
           public void onAnimationStart(Animator animation) {

           }

           @Override
           public void onAnimationEnd(Animator animation) {
               view.setVisibility(View.GONE);
           }

           @Override
           public void onAnimationCancel(Animator animation) {

           }

           @Override
           public void onAnimationRepeat(Animator animation) {

           }
       });
   }

   public static void fadeInAnimation (View view) {
       ObjectAnimator fadeAnimator = ObjectAnimator.ofFloat(view, "alpha", 0.0f, 1.0f);
       fadeAnimator.setDuration(500);
       fadeAnimator.start();
       fadeAnimator.addListener(new Animator.AnimatorListener() {
           @Override
           public void onAnimationStart(Animator animation) {

           }

           @Override
           public void onAnimationEnd(Animator animation) {
               view.setVisibility(View.VISIBLE);
           }

           @Override
           public void onAnimationCancel(Animator animation) {

           }

           @Override
           public void onAnimationRepeat(Animator animation) {

           }
       });
   }
}
