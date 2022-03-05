package com.example.ticketreserve;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;

public class feed extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;

    String userNameStr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);

        Bundle ex = getIntent().getExtras();
        if (ex != null){
            userNameStr = ex.getString("userName");
        }

        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewpage);

        tabLayout.setupWithViewPager(viewPager);

        vpAdapt vpAdapter = new vpAdapt(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);

        vpAdapter.addFragment(new eventList(), "Events");
        vpAdapter.addFragment(new myacc(), "MyAcc");

        viewPager.setAdapter(vpAdapter);

    }
}