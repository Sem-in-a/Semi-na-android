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

    // '내가 주최한 세미나' 리사이클러뷰 설정
    RecyclerView hostedRecyclerView = binding.hostedRecyclerView;
    hostedRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));

    Query seminarQuery = db.collection("Semina").whereIn("host", Arrays.asList(currentUserId));
    seminarQuery.get().addOnCompleteListener(task -> {
      if (task.isSuccessful()) {
        List<DocumentSnapshot> documents = task.getResult().getDocuments();
        if (!documents.isEmpty()) {
          List<Semina> seminaList = new ArrayList<>();
          int maxCardsToShow = 2;

          for (int i = 0; i < Math.min(maxCardsToShow, documents.size()); i++) {
            DocumentSnapshot document = documents.get(i);
            Semina semina = document.toObject(Semina.class);
            seminaList.add(semina);
          }

          if (!seminaList.isEmpty()) {
            SeminaAdapter hostedAdapter = new SeminaAdapter(seminaList);
            hostedRecyclerView.setAdapter(hostedAdapter);
            binding.noHostedSeminer.setVisibility(View.GONE);
            hostedRecyclerView.setVisibility(View.VISIBLE);
            binding.viewDetailHosted.setVisibility(View.VISIBLE);
          } else {
            binding.noHostedSeminer.setVisibility(View.VISIBLE);
            hostedRecyclerView.setVisibility(View.GONE);
            binding.viewDetailHosted.setVisibility(View.GONE);
          }
        }
      } else {
        Log.w("FirestoreError", "Error getting hosted seminar documents: ", task.getException());
      }
    });

// '내가 신청한 세미나' 리사이클러뷰 설정
    RecyclerView appliedRecyclerView = binding.appliedRecyclerView;
    appliedRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));

    Query appliedSeminarQuery = db.collection("Semina").whereArrayContains("memberList", currentUserId);
    appliedSeminarQuery.get().addOnCompleteListener(task -> {
      if (task.isSuccessful()) {
        List<DocumentSnapshot> documents = task.getResult().getDocuments();
        if (!documents.isEmpty()) {
          List<Semina> appliedSeminaList = new ArrayList<>();
          int maxAppliedCardsToShow = 2;

          for (int i = 0; i < Math.min(maxAppliedCardsToShow, documents.size()); i++) {
            DocumentSnapshot document = documents.get(i);
            Semina semina = document.toObject(Semina.class);
            appliedSeminaList.add(semina);
          }

          if (!appliedSeminaList.isEmpty()) {
            SeminaAdapter appliedAdapter = new SeminaAdapter(appliedSeminaList);
            appliedRecyclerView.setAdapter(appliedAdapter);
            binding.noAppliedSeminer.setVisibility(View.GONE);
            appliedRecyclerView.setVisibility(View.VISIBLE);
            binding.viewDetailApplied.setVisibility(View.VISIBLE);
          } else {
            binding.noAppliedSeminer.setVisibility(View.VISIBLE);
            appliedRecyclerView.setVisibility(View.GONE);
            binding.viewDetailApplied.setVisibility(View.GONE);
          }
        }
      } else {
        Log.w("FirestoreError", "Error getting applied seminar documents: ", task.getException());
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
