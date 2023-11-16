package com.semina.semi_na.base;

import static android.content.Context.MODE_PRIVATE;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.FragmentTransaction;
import com.semina.semi_na.databinding.FragmentMyPageBinding;
import com.semina.semi_na.view.mypage.LogoutModalFragment;
import com.semina.semi_na.view.mypage.ViewDetailAppliedActivity;
import com.semina.semi_na.view.mypage.ViewDetailHostedActivity;

public class MyPageFragment extends Fragment {

  private FragmentMyPageBinding binding;

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    binding = FragmentMyPageBinding.inflate(inflater, container, false);
    View view = binding.getRoot();

    SharedPreferences preferences = getActivity().getSharedPreferences("UserInfo", MODE_PRIVATE);
    binding.someIdName.setText(preferences.getString("name", ""));
    binding.someIdUnderDepartment.setText(preferences.getString("depart", ""));
    binding.someIdMajor.setText(preferences.getString("major", ""));
    binding.someIdGrade.setText(preferences.getString("studentNum", ""));

    binding.viewDetailHosted.setOnClickListener(hostedView -> {
      Intent intent = new Intent(getActivity(), ViewDetailHostedActivity.class);
      startActivity(intent);
    });

    binding.viewDetailApplied.setOnClickListener(appliedView -> {
      Intent intent = new Intent(getActivity(), ViewDetailAppliedActivity.class);
      startActivity(intent);
    });

    binding.logoutBtn.setOnClickListener(logoutView ->{
      Fragment logoutFragment = new LogoutModalFragment();
      FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
      //ransaction.replace(R.id.fragment_container, logoutFragment); // 'fragment_container'는 프래그먼트를 로드하는 FrameLayout의 ID입니다
      transaction.addToBackStack(null); // 이 트랜잭션을 백 스택에 추가합니다
      transaction.commit();
    });


    return view;
  }

  @Override
  public void onDestroyView() {
    super.onDestroyView();
    binding = null;
  }

}

