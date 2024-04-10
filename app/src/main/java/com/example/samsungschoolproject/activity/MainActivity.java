package com.example.samsungschoolproject.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.example.samsungschoolproject.R;
import com.example.samsungschoolproject.database.WorkoutHelperDatabase;
import com.example.samsungschoolproject.fragment.сalendar.CalendarFragment;
import com.example.samsungschoolproject.view_adapter.main.MainFragmentsAdapter;
import com.example.samsungschoolproject.fragment.main.MainMenuFragment;
import com.example.samsungschoolproject.fragment.templates.TemplatesFragment;
import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {
    private ViewPager mainViewPager;
    private TabLayout tabNavigation;
    private MainFragmentsAdapter mainFragmentsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initWidgets();
        initPagerAdapter();
        connectWidgetsWithAdapter();
        initDatabase();
    }

    private void initWidgets(){
        mainViewPager = findViewById(R.id.mainViewPager);
        tabNavigation = findViewById(R.id.tabNavigation);
    }

    private void initPagerAdapter(){
        mainFragmentsAdapter = new MainFragmentsAdapter(
                getSupportFragmentManager(),
                FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
        );

        mainFragmentsAdapter.Add(new CalendarFragment() , getResources().getString(R.string.calendar));
        mainFragmentsAdapter.Add(new MainMenuFragment(), getResources().getString(R.string.menu));
        mainFragmentsAdapter.Add(new TemplatesFragment(), getResources().getString(R.string.templates));
    }

    private void initDatabase(){
        WorkoutHelperDatabase.getInstance(getApplicationContext());
    }

    private void connectWidgetsWithAdapter(){
        mainViewPager.setAdapter(mainFragmentsAdapter);
        tabNavigation.setupWithViewPager(mainViewPager);

        mainViewPager.setCurrentItem(1); // Устанавливает MainMenuFragment при открытии MainActivity
    }
}