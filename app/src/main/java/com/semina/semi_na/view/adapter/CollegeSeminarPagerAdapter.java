package com.semina.semi_na.view.adapter;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.semina.semi_na.view.home.BusinessFragment;
import com.semina.semi_na.view.home.EconomicsFragment;
import com.semina.semi_na.view.home.EngineeringFragment;
import com.semina.semi_na.view.home.HumanityFragment;
import com.semina.semi_na.view.home.ITFragment;
import com.semina.semi_na.view.home.LawFragment;
import com.semina.semi_na.view.home.NaturalScienceFragment;
import com.semina.semi_na.view.home.SocialScienceFragment;

public class CollegeSeminarPagerAdapter extends FragmentStateAdapter {
    public CollegeSeminarPagerAdapter(Fragment fa) {
        super(fa);
    }

    @Override
    public int getItemCount() {
        return 8;
    }

    @Override
    public androidx.fragment.app.Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new ITFragment();
            case 1:
                return new EngineeringFragment();
            case 2:
                return new NaturalScienceFragment();
            case 3:
                return new SocialScienceFragment();
            case 4:
                return new HumanityFragment();
            case 5:
                return new LawFragment();
            case 6:
                return new EconomicsFragment();
            case 7:
                return new BusinessFragment();
            default:
                return new ITFragment();
        }
    }
}
