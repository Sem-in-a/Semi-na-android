package com.semina.semi_na.base;

import android.content.Intent;
import android.os.Bundle;

import android.view.View.OnClickListener;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayoutMediator;
import com.semina.semi_na.R;
import com.semina.semi_na.databinding.FragmentHomeBinding;
import com.semina.semi_na.view.adapter.CollegeSeminarPagerAdapter;
import com.semina.semi_na.view.adapter.HobbySeminarPagerAdapter;

import com.semina.semi_na.view.search.SearchResultsActivity;
import java.util.LinkedHashMap;

public class HomeFragment extends Fragment {
    private FragmentHomeBinding binding;
    private LinkedHashMap<String, Integer> tabTitles = new LinkedHashMap() {{
        put("IT대학", R.drawable.it_college_icon);
        put("공과대학", R.drawable.engineering_college_icon);
        put("자연과학대학", R.drawable.natural_science_college_icon);
        put("사회과학대학", R.drawable.social_science_college_icon);
        put("인문사회대학", R.drawable.humanity_college_icon);
        put("법과대학", R.drawable.law_college_icon);
        put("경제통상대학", R.drawable.economics_college_icon);
        put("경영대학", R.drawable.business_college_icon);
    }};

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        // 클릭 시 검색View event
        binding.searchEditText.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), SearchResultsActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d("[HomeFragment]", "onViewCreated");
        // get keys of tabTitles
        String[] titles = tabTitles.keySet().toArray(new String[tabTitles.size()]);

        CollegeSeminarPagerAdapter pagerAdapter = new CollegeSeminarPagerAdapter(this);
        binding.collegeViewPager.setAdapter(pagerAdapter);

        new TabLayoutMediator(binding.tabLayout, binding.collegeViewPager, (tab, position) -> {
            tab.setText(titles[position]);
            tab.setIcon(tabTitles.get(titles[position]));
        }).attach();

        // 취미로 찾아보는 세미나 ViewPager
        HobbySeminarPagerAdapter hobbySeminarPagerAdapter = new HobbySeminarPagerAdapter(this);
        binding.hobbyViewPager.setAdapter(hobbySeminarPagerAdapter);
    }
}