package com.semina.semi_na.base;

import static android.content.Context.MODE_PRIVATE;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.firestore.DocumentSnapshot;
import com.semina.semi_na.databinding.FragmentMyPageBinding;
import com.semina.semi_na.view.mypage.LogoutModalFragment;
import com.semina.semi_na.view.mypage.ViewDetailAppliedActivity;
import com.semina.semi_na.view.mypage.ViewDetailHostedActivity;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class MyPageFragment extends Fragment {

  private FragmentMyPageBinding binding;
  FirebaseFirestore db = FirebaseFirestore.getInstance();

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    binding = FragmentMyPageBinding.inflate(inflater, container, false);
    View view = binding.getRoot();
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    SharedPreferences preferences = getActivity().getSharedPreferences("UserInfo", MODE_PRIVATE);
    binding.someIdName.setText(preferences.getString("name", ""));
    binding.someIdUnderDepartment.setText(preferences.getString("depart", ""));
    binding.someIdMajor.setText(preferences.getString("major", ""));
    binding.someIdGrade.setText(preferences.getString("studentNum", ""));
    String studentNum = preferences.getString("studentNum", "");


    binding.viewDetailHosted.setOnClickListener(hostedView -> {
      Intent intent = new Intent(getActivity(), ViewDetailHostedActivity.class);
      startActivity(intent);
    });

    binding.viewDetailApplied.setOnClickListener(appliedView -> {
      Intent intent = new Intent(getActivity(), ViewDetailAppliedActivity.class);
      startActivity(intent);
    });

    binding.logoutBtn.setOnClickListener(logoutView ->{
      DialogFragment logoutDialog = new LogoutModalFragment();
      logoutDialog.show(getParentFragmentManager(), "logoutDialog");
    });


    // Firestore에서 해당 사용자의 신청 목록 조회
    Query query = db.collection("Applications").whereEqualTo("studentNum", studentNum);
    query.get().addOnCompleteListener(task -> {
      if (task.isSuccessful()) {
        for (DocumentSnapshot document : task.getResult()) {
          // 여기서 각 문서를 사용하여 데이터 처리
          // 예: SeminaApplication application = document.toObject(SeminaApplication.class);
        }
      } else {
        // 쿼리 실패 처리
      }
    });

    return view;
  }


  @Override
  public void onDestroyView() {
    super.onDestroyView();
    binding = null;
  }
}

