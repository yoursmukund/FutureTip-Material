package com.futuretip.futuretip;

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
import android.widget.TextView;

public class PickCardActivity extends AppCompatActivity{

    private ImageView card;
    private boolean sizeChanged;
    private int savedWidth;
    private int savedHeight;
    private ViewGroup viewRoot;
    private TextView thinkDeeply;
    private TextView text_card_interpretation;
    private TextView text_card_interpretation_heading;
    private Animation thinkDeeply_animation;
    private Animation card_interpretation_animation;

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

    //Window fades in
    private void setupWindowAnimations() {
        Fade fade = new Fade();
        fade.setDuration(1000);
        getWindow().setEnterTransition(fade);
    }


    public void initView() {
        card = (ImageView) findViewById(R.id.img_card);
        viewRoot = (ViewGroup) findViewById(R.id.activity_pick_card_root);
        thinkDeeply = (TextView)findViewById(R.id.text_think_deeply);
        text_card_interpretation = (TextView)findViewById(R.id.text_card_interpretation);
        text_card_interpretation_heading = (TextView)findViewById(R.id.text_card_interpretation_heading);

        card.setImageResource(R.drawable.material_design_1);

        //Card floats
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

        //Think deeply text floats down
        thinkDeeply_animation = AnimationUtils.loadAnimation(this, R.anim.anim_prediction_text_down);
        thinkDeeply.startAnimation(thinkDeeply_animation);

        //Card reveals on click and prediction text slides up
        findViewById(R.id.img_card).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bitmap cardImage = BitmapFactory.decodeResource(getApplicationContext().getResources(), R.drawable.magician);
                ImageViewAnimatedChange(getApplicationContext(), card, cardImage);
                scaleAnim.end();

                text_card_interpretation_heading.setText("What it means for your career");
                text_card_interpretation.setText("gsjdgsjldgaksdhlkas ashdkasjdjkasgdlkagsdklasg4" +
                        "dlkgaskdlgaskjdgklasjgdkjOISA4" +
                        "YOISAOIDHSADOIHSAODIHASOIDHAOSHDOASHDOASHDIAHSDIOAHSDlagsdkjaGSDKJLGASJDGALJKSDGKALJSDGKAGSDKJAD");
                card_interpretation_animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.anim_prediction_text_up);
                text_card_interpretation_heading.startAnimation(card_interpretation_animation);
                text_card_interpretation.startAnimation(card_interpretation_animation);

            }
        });

    }

    //Facilitates image reveal
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
