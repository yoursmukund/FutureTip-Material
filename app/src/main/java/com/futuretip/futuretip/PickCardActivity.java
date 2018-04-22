package com.futuretip.futuretip;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.transition.Fade;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.formats.NativeAd;

import org.w3c.dom.Text;

import java.util.Calendar;
import java.util.Date;
import java.util.Random;

public class PickCardActivity extends AppCompatActivity{

    private ImageView card;
    private ImageView cardFront;
    private boolean sizeChanged;
    private int savedWidth;
    private int savedHeight;
    private ViewGroup viewRoot;
    private TextView thinkDeeply;
    private TextView text_card_destiny;
    private TextView text_card_karma;
    private TextView text_card_destiny_heading;
    private TextView text_card_karma_heading;
    private TextView text_card_name;
    private Animation thinkDeeply_animation;
    private Animation card_interpretation_animation;
    private int[] photos;
    private AdView mAdView;
    private InterstitialAd mInterstitialAd;
    private String readingType;


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

        //Check reading type
        Intent intent = getIntent();
        readingType = intent.getStringExtra("readingType");

        //Load banner ad
        MobileAds.initialize(this, "ca-app-pub-3404907343922680~3002468520");
        mAdView = findViewById(R.id.adView);
        AdRequest request = new AdRequest.Builder()
                .addTestDevice("58FF272077DF1D58C04A7CD224819BE9")
                .build();
        mAdView.loadAd(request);


        //Load interstitial ad
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");
        mInterstitialAd.loadAd(new AdRequest.Builder().build());



        card = (ImageView) findViewById(R.id.img_card);
        viewRoot = (ViewGroup) findViewById(R.id.activity_pick_card_root);
        thinkDeeply = (TextView)findViewById(R.id.text_think_deeply);
        text_card_destiny = (TextView)findViewById(R.id.text_card_destiny);
        text_card_destiny_heading = (TextView)findViewById(R.id.text_card_destiny_heading);
        text_card_karma = (TextView)findViewById(R.id.text_card_karma);
        text_card_karma_heading = (TextView)findViewById(R.id.text_card_karma_heading);
        text_card_name = (TextView)findViewById(R.id.text_card_name);

        photos = new int []{R.drawable.death, R.drawable.eight_cups, R.drawable.eight_pents,
                R.drawable.eight_swords, R.drawable.eight_wands, R.drawable.five_cups,
                R.drawable.five_pents, R.drawable.five_swords, R.drawable.five_wands,
                R.drawable.four_cups, R.drawable.four_pents, R.drawable.four_swords,
                R.drawable.four_wands, R.drawable.judgement, R.drawable.justice,
                R.drawable.king_cups, R.drawable.king_pents, R.drawable.king_swords,
                R.drawable.king_wands, R.drawable.knight_cups, R.drawable.knight_pents,
                R.drawable.knight_swords, R.drawable.knight_wands, R.drawable.magician,
                R.drawable.nine_cups, R.drawable.nine_pents, R.drawable.nine_swords,
                R.drawable.nine_wands, R.drawable.one_cups, R.drawable.one_pents,
                R.drawable.one_swords, R.drawable.one_wands, R.drawable.page_cups,
                R.drawable.page_pents, R.drawable.page_swords, R.drawable.page_wands,
                R.drawable.queen_cups, R.drawable.queen_pents, R.drawable.queen_swords,
                R.drawable.queen_wands, R.drawable.seven_cups, R.drawable.seven_pents,
                R.drawable.seven_swords, R.drawable.seven_wands, R.drawable.six_cups,
                R.drawable.six_pents, R.drawable.six_swords, R.drawable.six_wands,
                R.drawable.strength, R.drawable.temperance, R.drawable.ten_cups,
                R.drawable.ten_pents, R.drawable.ten_swords, R.drawable.ten_wands,
                R.drawable.the_chariot, R.drawable.the_devil, R.drawable.the_emperor,
                R.drawable.the_empress, R.drawable.the_fool, R.drawable.the_hanged_man,
                R.drawable.the_heirophant, R.drawable.the_hermit, R.drawable.the_high_priestess,
                R.drawable.the_lovers, R.drawable.the_moon, R.drawable.the_star, R.drawable.the_sun,
                R.drawable.the_tower, R.drawable.the_world, R.drawable.three_cups, R.drawable.three_pents,
                R.drawable.three_swords, R.drawable.three_wands, R.drawable.two_cups, R.drawable.two_pents,
                R.drawable.two_swords, R.drawable.two_wands, R.drawable.wheel_of_fortune};


        getWindow().setNavigationBarColor(getResources().getColor(R.color.colorPrimary));


        SharedPreferences prefs = getApplicationContext().getSharedPreferences("prefs", Context.MODE_PRIVATE);
        String hasPicked = prefs.getString("picked_type_"+readingType, "");
        Long midnight = prefs.getLong("midnight_"+readingType, 0);

        //Calculating time left till midnight
        long timeLeftTillMidnight = (midnight-System.currentTimeMillis());

        //Resetting activity picked indicator the next day
        if(hasPicked.equalsIgnoreCase(readingType) && timeLeftTillMidnight<0){
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString("picked_type_"+readingType, "");
            editor.putLong("midnight_"+readingType, 0);
            editor.apply();
        }

        //Checking if user has already drawn the card, if so, show that same card until next day
        if (timeLeftTillMidnight>0 && hasPicked.equalsIgnoreCase(readingType)){

            //Show picked card
            Toast.makeText(getApplicationContext(), "We reset the readings every midnight", Toast.LENGTH_LONG).show();
            initViewWithCardPicked();

            //Disable it and start a new CountdownTimer; this is needed in order for
            //it to to become enabled if you're still in the app and the time ran out
            new CountDownTimer(timeLeftTillMidnight, 1000) {
                public void onTick(long millisUntilFinished) {}

                public void onFinish() {

                    //enable picking card
                    initView();
                }
            }.start();
        } else {

            //Enable picking card
            initView();

        }

    }

    //Window fades in
    private void setupWindowAnimations() {
        Fade fade = new Fade();
        fade.setDuration(1000);
        getWindow().setEnterTransition(fade);
    }

    public void initViewWithCardPicked(){

        //Retrieve previously chosen card
        SharedPreferences prefs = getApplicationContext().getSharedPreferences("prefs", Context.MODE_PRIVATE);
        int k = prefs.getInt("selected_card_index_"+readingType,0);
        card.setImageResource(photos[k]);

        //The text floats down on the card which is already been chosen
        thinkDeeply.setText(R.string.come_back_tomorrow);
        thinkDeeply_animation = AnimationUtils.loadAnimation(this, R.anim.anim_prediction_text_down);
        thinkDeeply.startAnimation(thinkDeeply_animation);
        card.setClickable(false);

        //Showing the reading interpretation
        setReadingText(k, readingType);

    }

    public void initView() {

        card.setImageResource(R.drawable.card_back);

            //Card floats
            ObjectAnimator scaleX = ObjectAnimator.ofFloat(card, "scaleX", 1.0f, 1.1f);
            ObjectAnimator scaleY = ObjectAnimator.ofFloat(card, "scaleY", 1.0f, 1.1f);

            final AnimatorSet scaleAnim = new AnimatorSet();
            scaleAnim.setDuration(1500);
            scaleAnim.play(scaleX).with(scaleY);

            scaleX.setRepeatCount(ObjectAnimator.INFINITE);
            scaleX.setRepeatMode(ObjectAnimator.REVERSE);
            scaleY.setRepeatCount(ObjectAnimator.INFINITE);
            scaleY.setRepeatMode(ObjectAnimator.REVERSE);

            scaleAnim.start();

            //Think deeply text floats down
            thinkDeeply.setText(R.string.focus_on_question);
            thinkDeeply_animation = AnimationUtils.loadAnimation(this, R.anim.anim_prediction_text_down);
            thinkDeeply.startAnimation(thinkDeeply_animation);
            cardFront = findViewById(R.id.img_card);

            //Card reveals on click and prediction text slides up
            cardFront.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Random ran = new Random();
                    int k = ran.nextInt(photos.length);
                    Bitmap cardImage = BitmapFactory.decodeResource(getApplicationContext().getResources(), photos[k]);
                    ImageViewAnimatedChange(getApplicationContext(), card, cardImage);
                    scaleAnim.end();
                    card.setClickable(false);

                    //Saving the picked card and showing it by default till midnight
                    SharedPreferences prefs = getApplicationContext().getSharedPreferences("prefs", Context.MODE_PRIVATE);
                    long currentTime = new Date().getTime();
                    SharedPreferences.Editor editor = prefs.edit();
                    Calendar c = Calendar.getInstance();
                    c.add(Calendar.DAY_OF_MONTH, 1);
                    c.set(Calendar.HOUR_OF_DAY, 0);
                    c.set(Calendar.MINUTE, 0);
                    c.set(Calendar.SECOND, 0);
                    c.set(Calendar.MILLISECOND, 0);
                    editor.putString("picked_type_"+readingType, readingType);
                    editor.putLong("midnight_"+readingType, c.getTimeInMillis());
                    editor.putInt("selected_card_index_"+readingType, k);
                    editor.apply();

                    //Showing the reading interpretation
                    setReadingText(k, readingType);

                }
            });

        }

    //To get the name of the string resource from a variable
    public static int getStringIdentifier(Context context, String name) {
        return context.getResources().getIdentifier(name, "string", context.getPackageName());
    }

    public String toFirstCharUpperAll(String string){
        string = string.replaceAll("_", " ");
        StringBuffer sb=new StringBuffer(string);
        for(int i=0;i<sb.length();i++)
            if(i==0 || sb.charAt(i-1)==' ')//first letter to uppercase by default
                sb.setCharAt(i, Character.toUpperCase(sb.charAt(i)));
        return sb.toString();
    }

    //Sets interpretation text
    public void setReadingText(int cardIndex, String readingType){

        //Animations
        card_interpretation_animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.anim_prediction_text_up);

        //Set card name text
        String card_name = getResources().getResourceEntryName(photos[cardIndex]);
        text_card_name.setText(toFirstCharUpperAll(card_name));
        text_card_name.startAnimation(card_interpretation_animation);

        //Making text views visible
        text_card_name.setVisibility(View.VISIBLE);
        text_card_karma.setVisibility(View.VISIBLE);
        text_card_destiny.setVisibility(View.VISIBLE);
        text_card_destiny_heading.setVisibility(View.VISIBLE);
        text_card_karma_heading.setVisibility(View.VISIBLE);

        //Set destiny text
        String destiny_string = readingType+"_destiny_"+card_name;
        text_card_destiny_heading.setText(R.string.destiny_text);
        text_card_destiny.setText(getStringIdentifier(getApplicationContext(), destiny_string));
        text_card_destiny_heading.startAnimation(card_interpretation_animation);
        text_card_destiny.startAnimation(card_interpretation_animation);

        //Set karma text
        String karma_string = readingType+"_karma_"+card_name;
        text_card_karma_heading.setText(R.string.karma_text);
        text_card_karma.setText(getStringIdentifier(getApplicationContext(), karma_string));
        text_card_karma_heading.startAnimation(card_interpretation_animation);
        text_card_karma.startAnimation(card_interpretation_animation);
    }

    @Override
    protected void onResume() {
        super.onResume();
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


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        }
    }

}


