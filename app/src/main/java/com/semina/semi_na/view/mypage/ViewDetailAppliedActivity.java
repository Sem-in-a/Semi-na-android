package com.semina.semi_na.view.mypage;

import android.os.Bundle;
import android.util.Log;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.semina.semi_na.R;
import com.semina.semi_na.base.MyPageFragment;
import com.semina.semi_na.view.adapter.ViewDetailAppliedAdapter;

public class ViewDetailAppliedActivity extends AppCompatActivity {

  private TabLayout tabLayout;
  private ViewPager2 viewPager;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_view_detail_applied);

    tabLayout = findViewById(R.id.select_text_tabLayout);
    viewPager = findViewById(R.id.applied_view_pager);

    ViewDetailAppliedAdapter adapter = new ViewDetailAppliedAdapter(this);
    viewPager.setAdapter(adapter);

    new TabLayoutMediator(tabLayout, viewPager,
        (tab, position) -> {
          if (position == 0) {
            tab.setText("진행중");
          } else if (position == 1) {
            tab.setText("마감");
          }
        }
    ).attach();
  }
}

