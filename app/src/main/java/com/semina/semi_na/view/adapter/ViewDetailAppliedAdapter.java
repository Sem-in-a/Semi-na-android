package com.semina.semi_na.view.adapter;

import androidx.viewpager2.adapter.FragmentStateAdapter;
import com.semina.semi_na.view.mypage.AppliedClosedFragment;
import com.semina.semi_na.view.mypage.AppliedProceedingFragment;
import com.semina.semi_na.view.mypage.ViewDetailAppliedActivity;

public class ViewDetailAppliedAdapter extends FragmentStateAdapter {

  public ViewDetailAppliedAdapter(ViewDetailAppliedActivity fa) {
    super(fa);
  }

  public int getItemCount() {
    return 2;
  }

  public androidx.fragment.app.Fragment createFragment(int position) {
    switch (position) {
      case 0:
        return new AppliedProceedingFragment();
      case 1:
        return new AppliedClosedFragment();
      default:
        return new AppliedProceedingFragment();
    }
  }
}

