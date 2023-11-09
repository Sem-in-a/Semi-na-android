package com.semina.semi_na.base;

import static android.content.Context.MODE_PRIVATE;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.semina.semi_na.R;
import com.semina.semi_na.view.mypage.ViewDetailAppliedActivity;
import com.semina.semi_na.view.mypage.ViewDetailHostedActivity;

public class MyPageFragment extends Fragment {

  private ImageView viewDetailHostedBtn;
  private ImageView viewDetailAppliedBtn;
  private ScrollView scrollView;

  TextView studentNumTextView;
  TextView departmentTextView;
  TextView majorTextView;
  TextView nameTextView;

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_my_page, container, false);

    scrollView = view.findViewById(R.id.scrollView);
    viewDetailHostedBtn = view.findViewById(R.id.viewDetail_hosted);
    viewDetailAppliedBtn = view.findViewById(R.id.viewDetail_applied);

    studentNumTextView = view.findViewById(R.id.some_id_name);
    departmentTextView = view.findViewById(R.id.some_id_under_department);
    majorTextView = view.findViewById(R.id.some_id_major);
    nameTextView = view.findViewById(R.id.some_id_grade);

    SharedPreferences preferences = getActivity().getSharedPreferences("UserInfo", MODE_PRIVATE);
    String studentNum = preferences.getString("studentNum", "");
    String department = preferences.getString("depart", "");
    String major = preferences.getString("major", "");
    String name = preferences.getString("name", "");

    studentNumTextView.setText(studentNum);
    departmentTextView.setText(department);
    majorTextView.setText(major);
    nameTextView.setText(name);

    viewDetailHostedBtn.setOnClickListener(hostedView -> {
      Intent intent = new Intent(getActivity(), ViewDetailHostedActivity.class);
      startActivity(intent);
    });

    viewDetailAppliedBtn.setOnClickListener(appliedView -> {
      Intent intent = new Intent(getActivity(), ViewDetailAppliedActivity.class);
      startActivity(intent);
    });

    return view;
  }
}
