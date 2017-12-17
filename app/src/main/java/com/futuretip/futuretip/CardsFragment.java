package com.futuretip.futuretip;

import android.animation.ObjectAnimator;
import android.app.ActivityOptions;
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


public class CardsFragment extends Fragment implements View.OnClickListener, View.OnTouchListener {

    private ImageView img_card_main_1;
    private ImageView img_card_main_2;
    private ImageView img_card_main_3;
    private ImageView img_card_main_4;
    private CardView card_main_1;
    private CardView card_main_2;
    private CardView card_main_3;
    private CardView card_main_4;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        NestedScrollView nestedScrollView = (NestedScrollView) inflater.inflate(R.layout.fragment_cards, container, false);

        img_card_main_1 = (ImageView) nestedScrollView.findViewById(R.id.img_card_main_1);
        img_card_main_2 = (ImageView) nestedScrollView.findViewById(R.id.img_card_main_2);
        img_card_main_3 = (ImageView) nestedScrollView.findViewById(R.id.img_card_main_3);
        img_card_main_4 = (ImageView) nestedScrollView.findViewById(R.id.img_card_main_4);
        card_main_1 = (CardView) nestedScrollView.findViewById(R.id.card_main_1);
        card_main_2 = (CardView) nestedScrollView.findViewById(R.id.card_main_2);
        card_main_3 = (CardView) nestedScrollView.findViewById(R.id.card_main_3);
        card_main_4 = (CardView) nestedScrollView.findViewById(R.id.card_main_4);

        Glide.with(getContext()).load(R.drawable.career).fitCenter().into(img_card_main_1);
        Glide.with(getContext()).load(R.drawable.love).fitCenter().into(img_card_main_2);
        Glide.with(getContext()).load(R.drawable.health).fitCenter().into(img_card_main_3);
        Glide.with(getContext()).load(R.drawable.family).fitCenter().into(img_card_main_4);

        return nestedScrollView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        card_main_1.setOnClickListener(this);
        card_main_2.setOnClickListener(this);
        card_main_3.setOnClickListener(this);
        card_main_4.setOnClickListener(this);

        card_main_1.setOnTouchListener(this);
        card_main_2.setOnTouchListener(this);
        card_main_3.setOnTouchListener(this);
        card_main_4.setOnTouchListener(this);

    }


    public void onClick(View view) {
        Intent intent = new Intent(getActivity().getApplicationContext(), PickCardActivity.class);
        switch (view.getId()) {

            case R.id.card_main_1:
                intent.putExtra("readingType", "career");
                startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(getActivity()).toBundle());
                break;
            case R.id.card_main_2:
                intent.putExtra("readingType", "love");
                startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(getActivity()).toBundle());
                break;
            case R.id.card_main_3:
                intent.putExtra("readingType", "health");
                startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(getActivity()).toBundle());
                break;
            case R.id.card_main_4:
                intent.putExtra("readingType", "family");
                startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(getActivity()).toBundle());
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
