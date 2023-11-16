package com.semina.semi_na.base;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;


import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.semina.semi_na.R;
import com.semina.semi_na.view.adapter.CollegePagerAdapter;

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class HomeFragment extends Fragment {
    private LinkedHashMap tabTitles = new LinkedHashMap() {{
        put("IT대학", R.drawable.it_college_icon);
        put("공과대학", R.drawable.engineering_college_icon);
        put("자연과학대학", R.drawable.natural_science_college_icon);
        put("사회과학대학", R.drawable.social_science_college_icon);
        put("인문사회대학", R.drawable.humanity_college_icon);
        put("법과대학", R.drawable.law_college_icon);
        put("경제통상대학", R.drawable.economics_college_icon);
        put("경영대학", R.drawable.business_college_icon);
    }};
    private TabLayout tabLayout;
    private ViewPager2 viewPager;
    private CollegePagerAdapter pagerAdapter;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d("[HomeFragment]", "onViewCreated");
        // get keys of tabTitles
        String[] titles = (String[]) tabTitles.keySet().toArray(new String[tabTitles.size()]);

        tabLayout = view.findViewById(R.id.tabLayout);
        viewPager = view.findViewById(R.id.viewPager);
        pagerAdapter = new CollegePagerAdapter(this);

        viewPager.setAdapter(pagerAdapter);
        viewPager.setOffscreenPageLimit(7);

        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
            tab.setText(titles[position]);
            tab.setIcon((Integer) tabTitles.get(titles[position]));
        }).attach();
    }
}