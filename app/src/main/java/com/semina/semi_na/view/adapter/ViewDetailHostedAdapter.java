package com.semina.semi_na.view.adapter;

import androidx.viewpager2.adapter.FragmentStateAdapter;
import com.semina.semi_na.view.mypage.HostedClosedFragment;
import com.semina.semi_na.view.mypage.HostedProceedingFragment;
import com.semina.semi_na.view.mypage.ViewDetailHostedActivity;

public class ViewDetailHostedAdapter extends FragmentStateAdapter {

  public ViewDetailHostedAdapter(ViewDetailHostedActivity fa){
    super(fa);
  }

  public int getItemCount(){
    return 2;
  }

  public androidx.fragment.app.Fragment createFragment(int position){
    switch (position){
      case 0:
        return new HostedProceedingFragment();
      case 1:
        return new HostedClosedFragment();
      default:
        return new HostedProceedingFragment();
    }
  }
}
