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
import com.semina.semi_na.data.db.entity.HobbyCategory;
import com.semina.semi_na.data.db.entity.MajorCategory;
import com.semina.semi_na.databinding.FragmentHomeBinding;
import com.semina.semi_na.view.adapter.CollegeSeminarPagerAdapter;
import com.semina.semi_na.view.adapter.HobbySeminarPagerAdapter;

import java.util.ArrayList;
import java.util.LinkedHashMap;

public class HomeFragment extends Fragment {
    private static final LinkedHashMap<MajorCategory, Integer> collegeMap = new LinkedHashMap() {{
        put(MajorCategory.IT, R.drawable.it_college_icon);
        put(MajorCategory.ENGINEERING, R.drawable.engineering_college_icon);
        put(MajorCategory.SCIENCE, R.drawable.natural_science_college_icon);
        put(MajorCategory.SOCIAL, R.drawable.social_science_college_icon);
        put(MajorCategory.HUMANITY, R.drawable.humanity_college_icon);
        put(MajorCategory.LAW, R.drawable.law_college_icon);
        put(MajorCategory.ECONOMIC, R.drawable.economics_college_icon);
        put(MajorCategory.BUSINESS, R.drawable.business_college_icon);
    }};

    private static final ArrayList<HobbyCategory> hobbyList = new ArrayList() {{
        add(HobbyCategory.EXERCISE);
        add(HobbyCategory.FOOD);
        add(HobbyCategory.MUSIC);
        add(HobbyCategory.BOOK);
    }};

    private FragmentHomeBinding binding;

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
        ArrayList<MajorCategory> collegeList = new ArrayList<>(collegeMap.keySet());

        // 전공별로 찾아보는 세미나 ViewPager
        CollegeSeminarPagerAdapter collegeSeminarPagerAdapter = new CollegeSeminarPagerAdapter(collegeList);
        binding.collegeViewPager.setAdapter(collegeSeminarPagerAdapter);

        new TabLayoutMediator(binding.tabLayout, binding.collegeViewPager, (tab, position) -> {
            tab.setText(collegeList.get(position).getMajorName());
            tab.setIcon(collegeMap.get(collegeList.get(position)));
        }).attach();

        // 취미로 찾아보는 세미나 ViewPager
        HobbySeminarPagerAdapter hobbySeminarPagerAdapter = new HobbySeminarPagerAdapter(hobbyList);
        binding.hobbyViewPager.setAdapter(hobbySeminarPagerAdapter);
    }
}