package com.example.jettlee.photoviewer;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;


public class LightBoxActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lightbox);
        Bundle bundle = this.getIntent().getExtras();
        int imageFile_position = bundle.getInt("position");
        ViewPager mViewPager = (ViewPager) findViewById(R.id.viewpager);
        mViewPager.setAdapter(new ViewPagerAdapter(LightBoxActivity.this));
        mViewPager.setCurrentItem(imageFile_position);
    }
}
