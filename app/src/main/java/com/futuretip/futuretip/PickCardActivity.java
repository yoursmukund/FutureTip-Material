package com.futuretip.futuretip;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.transition.Fade;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class PickCardActivity extends AppCompatActivity{

    private ImageView card;
    private boolean sizeChanged;
    private int savedWidth;
    private int savedHeight;
    private ViewGroup viewRoot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pick_card);
        setupWindowAnimations();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_about);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        getWindow().setNavigationBarColor(getResources().getColor(R.color.colorPrimary));

        initView();
    }

    private void setupWindowAnimations() {
        Fade fade = new Fade();
        fade.setDuration(1000);
        getWindow().setEnterTransition(fade);
    }


    public void initView() {
        card = (ImageView) findViewById(R.id.img_card);
        viewRoot = (ViewGroup) findViewById(R.id.activity_pick_card_root);

        card.setImageResource(R.drawable.material_design_1);

        ObjectAnimator scaleX = ObjectAnimator.ofFloat(card, "scaleX", 1.0f, 1.1f);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(card, "scaleY", 1.0f, 1.1f);

        final AnimatorSet scaleAnim = new AnimatorSet();
        scaleAnim.setDuration(1000);
        scaleAnim.play(scaleX).with(scaleY);

        scaleX.setRepeatCount(ObjectAnimator.INFINITE);
        scaleX.setRepeatMode(ObjectAnimator.REVERSE);
        scaleY.setRepeatCount(ObjectAnimator.INFINITE);
        scaleY.setRepeatMode(ObjectAnimator.REVERSE);

        scaleAnim.start();


        findViewById(R.id.img_card).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bitmap cardImage = BitmapFactory.decodeResource(getApplicationContext().getResources(), R.drawable.magician);
                ImageViewAnimatedChange(getApplicationContext(), card, cardImage);
                scaleAnim.end();
            }
        });
    }

    public static void ImageViewAnimatedChange(Context c, final ImageView v, final Bitmap new_image) {
        final Animation anim_out = AnimationUtils.loadAnimation(c, android.R.anim.fade_out);
        final Animation anim_in  = AnimationUtils.loadAnimation(c, android.R.anim.fade_in);
        anim_out.setAnimationListener(new Animation.AnimationListener()
        {
            @Override public void onAnimationStart(Animation animation) {}
            @Override public void onAnimationRepeat(Animation animation) {}
            @Override public void onAnimationEnd(Animation animation)
            {
                v.setImageBitmap(new_image);
                anim_in.setAnimationListener(new Animation.AnimationListener() {
                    @Override public void onAnimationStart(Animation animation) {}
                    @Override public void onAnimationRepeat(Animation animation) {}
                    @Override public void onAnimationEnd(Animation animation) {}
                });
                v.startAnimation(anim_in);
            }
        });
        v.startAnimation(anim_out);
    }


}
