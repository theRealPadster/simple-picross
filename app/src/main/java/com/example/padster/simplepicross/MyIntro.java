package com.example.padster.simplepicross;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import com.github.paolorotolo.appintro.AppIntro;

import activity.MainActivity;


public class MyIntro extends AppIntro {

    // Please DO NOT override onCreate. Use init
    @Override
    public void init(Bundle savedInstanceState) {

        // Add your slide's fragments here
        // AppIntro will automatically generate the dots indicator and buttons.
        addSlide(SampleSlide.newInstance(R.layout.intro));
        addSlide(SampleSlide.newInstance(R.layout.intro2));
        addSlide(SampleSlide.newInstance(R.layout.intro3));
        addSlide(SampleSlide.newInstance(R.layout.intro4));
        addSlide(SampleSlide.newInstance(R.layout.intro5));
        addSlide(SampleSlide.newInstance(R.layout.intro6));

        // OPTIONAL METHODS
        // Override bar/separator color
        setBarColor(Color.parseColor("#3F51B5"));
        setSeparatorColor(Color.parseColor("#2196F3"));

        // Hide Skip button
        showSkipButton(true);

        // Turn vibration on and set intensity
        // NOTE: you will probably need to ask VIBRATE permesssion in Manifest
        setVibrate(false);
        //setVibrateIntensity(30);
    }

    private void loadMainActivity(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void onSkipPressed() { // TODO - make this go to previous activity, not always MainActivity
        // Do something when users tap on Skip button.
        loadMainActivity();
    }

    @Override
    public void onDonePressed() {
        loadMainActivity();
    }

    public void getStarted(View v){
        loadMainActivity();
    }
}
