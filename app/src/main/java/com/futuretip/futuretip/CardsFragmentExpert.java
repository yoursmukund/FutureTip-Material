package com.futuretip.futuretip;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;


public class CardsFragmentExpert extends Fragment implements View.OnClickListener, View.OnTouchListener {

    private ImageView img_card_main_1;
    private ImageView img_card_main_2;
    private ImageView img_card_main_3;
    private ImageView img_card_main_4;
    private CardView card_expert_half_year;
    private CardView card_expert_week;
    private CardView card_expert_month;
    private CardView card_expert_year;
    private AdView mAdView;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        NestedScrollView nestedScrollView = (NestedScrollView) inflater.inflate(R.layout.fragment_cards_expert, container, false);


        img_card_main_1 = (ImageView) nestedScrollView.findViewById(R.id.img_card_main_1);
        img_card_main_2 = (ImageView) nestedScrollView.findViewById(R.id.img_card_main_2);
        img_card_main_3 = (ImageView) nestedScrollView.findViewById(R.id.img_card_main_3);
        img_card_main_4 = (ImageView) nestedScrollView.findViewById(R.id.img_card_main_4);
        card_expert_half_year = (CardView) nestedScrollView.findViewById(R.id.card_expert_half_year);
        card_expert_week = (CardView) nestedScrollView.findViewById(R.id.card_expert_week);
        card_expert_month = (CardView) nestedScrollView.findViewById(R.id.card_expert_month);
        card_expert_year = (CardView) nestedScrollView.findViewById(R.id.card_expert_year);

        Glide.with(getContext()).load(R.drawable.expert_logo_1).fitCenter().into(img_card_main_1);
        Glide.with(getContext()).load(R.drawable.expert_logo_2).fitCenter().into(img_card_main_2);
        Glide.with(getContext()).load(R.drawable.expert_logo_3).fitCenter().into(img_card_main_3);
        Glide.with(getContext()).load(R.drawable.expert_logo_4).fitCenter().into(img_card_main_4);

        //Load banner ad
        MobileAds.initialize(getContext(), "ca-app-pub-3940256099942544/6300978111");
        mAdView = nestedScrollView.findViewById(R.id.self_ad);
        AdRequest request = new AdRequest.Builder()
                .addTestDevice("030F43671FBF23EE4588A4CCD5F8A7DA")
                .build();
        mAdView.loadAd(request);

        return nestedScrollView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        card_expert_half_year.setOnClickListener(this);
        card_expert_week.setOnClickListener(this);
        card_expert_month.setOnClickListener(this);
        card_expert_year.setOnClickListener(this);

        card_expert_half_year.setOnTouchListener(this);
        card_expert_week.setOnTouchListener(this);
        card_expert_month.setOnTouchListener(this);
        card_expert_year.setOnTouchListener(this);

    }


    public void onClick(View view) {
        Intent intent = new Intent(getActivity().getApplicationContext(), PickCardActivity.class);
        switch (view.getId()) {

            case R.id.card_expert_half_year:
                intent.putExtra("readingType", "career");
                startActivity(intent);
                break;
            case R.id.card_expert_week:
                intent.putExtra("readingType", "love");
                startActivity(intent);
                break;
            case R.id.card_expert_month:
                intent.putExtra("readingType", "health");
                startActivity(intent);
                break;
            case R.id.card_expert_year:
                intent.putExtra("readingType", "family");
                startActivity(intent);
                break;
        }
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        switch (motionEvent.getAction()) {
            case MotionEvent.ACTION_DOWN:
                ObjectAnimator upAnim = ObjectAnimator.ofFloat(view, "translationZ", 16);
                upAnim.setDuration(150);
                upAnim.setInterpolator(new DecelerateInterpolator());
                upAnim.start();
                break;

            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                ObjectAnimator downAnim = ObjectAnimator.ofFloat(view, "translationZ", 0);
                downAnim.setDuration(150);
                downAnim.setInterpolator(new AccelerateInterpolator());
                downAnim.start();
                break;
        }
        return false;
    }
}
