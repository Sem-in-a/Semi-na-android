package com.semina.semi_na.base;

import static android.content.Context.MODE_PRIVATE;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.semina.semi_na.data.db.entity.Semina;
import com.semina.semi_na.databinding.FragmentMyPageBinding;
import com.semina.semi_na.view.adapter.SeminaAdapter;
import com.semina.semi_na.view.mypage.LogoutModalFragment;
import com.semina.semi_na.view.mypage.ViewDetailAppliedActivity;
import com.semina.semi_na.view.mypage.ViewDetailHostedActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MyPageFragment extends Fragment {

  private FragmentMyPageBinding binding;
  FirebaseFirestore db = FirebaseFirestore.getInstance();
  private RecyclerView recyclerView;
  private SeminaAdapter adapter;

  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    binding = FragmentMyPageBinding.inflate(inflater, container, false);
    View view = binding.getRoot();

    SharedPreferences preferences = getActivity().getSharedPreferences("UserInfo", MODE_PRIVATE);
    binding.someIdName.setText(preferences.getString("name", ""));
    binding.someIdUnderDepartment.setText(preferences.getString("depart", ""));
    binding.someIdMajor.setText(preferences.getString("major", ""));
    binding.someIdGrade.setText(preferences.getString("studentNum", ""));
    String imageUrl = preferences.getString("img", "");
    if (!imageUrl.isEmpty()) {
      Glide.with(this).load(imageUrl).circleCrop().into(binding.rectangleProfile);
    }
    String currentUserId = preferences.getString("studentNum", "");
    Log.d("MyPageFragment", "Current User ID: " + currentUserId);

    binding.viewDetailHosted.setOnClickListener(hostedView -> {
      Intent intent = new Intent(getActivity(), ViewDetailHostedActivity.class);
      startActivity(intent);
    });

    binding.viewDetailApplied.setOnClickListener(appliedView -> {
      Intent intent = new Intent(getActivity(), ViewDetailAppliedActivity.class);
      startActivity(intent);
    });

    binding.logoutBtn.setOnClickListener(logoutView -> {
      DialogFragment logoutDialog = new LogoutModalFragment();
      logoutDialog.show(getParentFragmentManager(), "logoutDialog");
    });

    //파이어베이스 연결

    recyclerView = binding.hostedRecyclerView;
    recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));

    List<String> hostIds = Arrays.asList(currentUserId);

    Query seminarQuery = db.collection("Semina").whereIn("host", hostIds);
    seminarQuery.get().addOnCompleteListener(task -> {
      if (task.isSuccessful()) {
        List<DocumentSnapshot> documents = task.getResult().getDocuments();
        if (!documents.isEmpty()) {
          Log.d("MyPageFragment", "Document count: " + documents.size());
          List<Semina> seminaList = new ArrayList<>();

          int maxCardsToShow = 2; // 최대 2개의 카드뷰만 보이도록 설정

          for (int i = 0; i < Math.min(maxCardsToShow, documents.size()); i++) {
            DocumentSnapshot document = documents.get(i);
            Semina semina = document.toObject(Semina.class);
            seminaList.add(semina);
          }

          // 데이터가 있을 경우, 'no_hosted_semina' 이미지를 숨기고 RecyclerView를 보여줍니다.
          binding.noHostedSeminer.setVisibility(View.GONE);
          recyclerView.setVisibility(View.VISIBLE);
          binding.viewDetailHosted.setVisibility(View.VISIBLE);

          // 어댑터 설정
          adapter = new SeminaAdapter(seminaList);
          recyclerView.setAdapter(adapter);
        } else {
          // 데이터가 없을 경우, 'no_hosted_seminer' 이미지를 보여주고 RecyclerView를 숨깁니다.
          binding.noHostedSeminer.setVisibility(View.VISIBLE);
          recyclerView.setVisibility(View.GONE);
          binding.viewDetailHosted.setVisibility(View.GONE);
        }
      } else {
        Log.w("FirestoreError", "Error getting documents: ", task.getException());
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


