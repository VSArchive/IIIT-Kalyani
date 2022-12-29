package com.iiit.iiitkalyani.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.iiit.iiitkalyani.R;

public class FestFragment extends Fragment {

    private ImageView logo;
    private RelativeLayout layout;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_fest, container, false);
        logo = root.findViewById(R.id.logo);
        layout = root.findViewById(R.id.layout);

        //layout.animate().alpha(0f).setDuration(10);

        TranslateAnimation animation = new TranslateAnimation(0, 0, 0, -1000);
        animation.setDuration(2000);
        animation.setFillAfter(false);
        animation.setAnimationListener(new MyAnimationListener());

        logo.setAnimation(animation);

        return root;

    }

    private class MyAnimationListener implements Animation.AnimationListener {

        @Override
        public void onAnimationStart(Animation animation) {
            layout.setVisibility(View.INVISIBLE);
        }

        @Override
        public void onAnimationEnd(Animation animation) {

            logo.clearAnimation();
            logo.setVisibility(View.INVISIBLE);
            layout.setVisibility(View.VISIBLE);
            layout.animate().alpha(1f).setDuration(1000);

        }

        @Override
        public void onAnimationRepeat(Animation animation) {

        }
    }
}