package com.example.samsungschoolproject.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.example.samsungschoolproject.R;
import com.example.samsungschoolproject.enums.PagesEnum;
import com.example.samsungschoolproject.fragment.WeekCalendarFragment;
import com.example.samsungschoolproject.view_adapter.ViewPagerAdapter;
import com.example.samsungschoolproject.fragment.MonthCalendarFragment;
import com.example.samsungschoolproject.fragment.MainMenuFragment;
import com.example.samsungschoolproject.fragment.WorkoutTemplatesFragment;
import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private ViewPagerAdapter viewPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initWidgets();
        initPagerAdapter();
        connectWidgetsWithAdapter();
    }

    private void initWidgets(){
        viewPager = findViewById(R.id.viewpager);
        tabLayout = findViewById(R.id.tablayout);
    }

    private void initPagerAdapter(){
        viewPagerAdapter = new ViewPagerAdapter(
                getSupportFragmentManager(),
                FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
        );

        viewPagerAdapter.Add(new MonthCalendarFragment(viewPagerAdapter), PagesEnum.calendar);
        viewPagerAdapter.Add(new MainMenuFragment(), PagesEnum.menu);
        viewPagerAdapter.Add(new WorkoutTemplatesFragment(), PagesEnum.templates);
    }

    private void connectWidgetsWithAdapter(){
        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);

        viewPager.setCurrentItem(1);
    }
}