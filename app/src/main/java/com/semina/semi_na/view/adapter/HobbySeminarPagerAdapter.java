package com.semina.semi_na.view.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.semina.semi_na.view.home.HobbyBookFragment;
import com.semina.semi_na.view.home.HobbyExerciseFragment;
import com.semina.semi_na.view.home.HobbyFoodFragment;
import com.semina.semi_na.view.home.HobbyMusicFragment;

public class HobbySeminarPagerAdapter extends FragmentStateAdapter {


    public HobbySeminarPagerAdapter(@NonNull Fragment fragment) {
        super(fragment);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new HobbyExerciseFragment();
            case 1:
                return new HobbyFoodFragment();
            case 2:
                return new HobbyMusicFragment();
            case 3:
                return new HobbyBookFragment();
            default:
                return new HobbyExerciseFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 4;
    }
}