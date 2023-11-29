package com.semina.semi_na.base;

import static android.content.Context.MODE_PRIVATE;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import com.bumptech.glide.Glide;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.semina.semi_na.R;
import com.semina.semi_na.databinding.FragmentMyPageBinding;
import com.semina.semi_na.view.mypage.LogoutModalFragment;
import com.semina.semi_na.view.mypage.ViewDetailAppliedActivity;
import com.semina.semi_na.view.mypage.ViewDetailHostedActivity;

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

    List<String> hostIds = Arrays.asList(currentUserId);

    Query seminarQuery = db.collection("Semina").whereIn("host", hostIds);
    seminarQuery.get().addOnCompleteListener(task -> {
      if (task.isSuccessful()) {
        List<DocumentSnapshot> documents = task.getResult().getDocuments();
        getActivity().runOnUiThread(() -> {
          if (!documents.isEmpty()) {
            Log.d("MyPageFragment", "Document count: " + documents.size());
            // 데이터가 있을 경우, 'no_hosted_semina' 이미지를 숨기고 'hosted_grid'와 'viewDetailHosted'를 보여줍니다
            binding.noHostedSeminer.setVisibility(View.GONE);
            binding.hostedGrid.setVisibility(View.VISIBLE);
            binding.viewDetailHosted.setVisibility(View.VISIBLE);

            // 가져온 데이터로 UI를 채웁니다.
            for (int i = 0; i < documents.size(); i++) {
              DocumentSnapshot document = documents.get(i);
              Log.d("MyPageFragment", "Document " + i + ": " + document.getData());

              if (i == 0) {
                // 첫 번째 CardView를 채웁니다.
                updateCardViewWithData(binding.cardView1, document);
              } else if (i == 1) {
                // 두 번째 CardView를 채웁니다.
                updateCardViewWithData(binding.cardView2, document);
              }else {
                Log.d("MyPageFragment", "No documents found");
              }
            }
          } else {
            // 데이터가 없을 경우, 'no_hosted_seminer' 이미지를 보여주고 'hosted_grid'와 'viewDetailHosted'을 숨깁니다.
            binding.noHostedSeminer.setVisibility(View.VISIBLE);
            binding.hostedGrid.setVisibility(View.GONE);
            binding.viewDetailHosted.setVisibility(View.GONE);
          }
        });
      } else {
        Log.w("FirestoreError", "Error getting documents: ", task.getException());
      }
    });

    return view;
  }

  // CardView에 데이터를 설정하는 메서드
  private void updateCardViewWithData(CardView cardView, DocumentSnapshot document) {
    // 데이터 추출
    String imgUrl = document.getString("imgUrl");
    String title = document.getString("title");
    String description = document.getString("description");
    String location = document.getString("location");
    String locationDetail = document.getString("locationDetail");
    String host = document.getString("host");
    Long capacity = document.getLong("capacity");
    String date = document.getString("date");

    // CardView1 바인딩
    bindCardViewData(cardView, R.id.hosted_img, R.id.hosted_title, R.id.hosted_description,
        R.id.hosted_under_department, R.id.hosted_location_detail, R.id.hosted_grade,
        R.id.hosted_capacity, R.id.hosted_date,
        imgUrl, title, description, location, locationDetail, host, capacity, date);

    // CardView2 바인딩
    bindCardViewData(cardView, R.id.hosted2_img, R.id.hosted2_title, R.id.hosted2_description,
        R.id.hosted2_under_department, R.id.hosted2_location_detail, R.id.hosted2_grade,
        R.id.hosted2_capacity, R.id.hosted2_date,
        imgUrl, title, description, location, locationDetail, host, capacity, date);

    // CardView를 보이게 설정
    cardView.setVisibility(View.VISIBLE);
  }

  // 각 CardView에 데이터 바인딩을 수행하는 보조 메서드
  private void bindCardViewData(CardView cardView, int imageViewId, int titleViewId, int descriptionViewId, int underDepartmentViewId,
      int locationDetailViewId,int gradeViewId,  int capacityViewId, int dateViewId, String imgUrl, String title,
      String description, String location, String locationDetail, String host, Long capacity, String date) {
    ImageView imageView = cardView.findViewById(imageViewId);
    TextView titleTextView = cardView.findViewById(titleViewId);
    TextView descriptionTextView = cardView.findViewById(descriptionViewId);
    TextView locationTextView = cardView.findViewById(underDepartmentViewId);
    TextView locationDetailTextView = cardView.findViewById(locationDetailViewId);
    TextView gradeTextView = cardView.findViewById(gradeViewId);
    TextView capacityTextView = cardView.findViewById(capacityViewId);
    TextView dateTextView = cardView.findViewById(dateViewId);

    // 이미지 뷰가 없으면 여기서 종료, 필요 이유: 바로 파베 연동이 안될 시 널 값을 줘야 안꺼짐
    if (imageView == null) {
      return;
    }

    // 이미지 로딩
    if (imgUrl != null && !imgUrl.trim().isEmpty()) {
      Glide.with(cardView.getContext()).load(imgUrl).into(imageView);
    } else {
      imageView.setImageResource(R.drawable.board);
    }

    // 나머지 텍스트 데이터 바인딩
    titleTextView.setText(title);
    descriptionTextView.setText(description);
    locationTextView.setText(location);
    locationDetailTextView.setText(locationDetail);
    gradeTextView.setText(host);
    capacityTextView.setText(String.format("%d명", capacity));
    if (date != null && !date.trim().isEmpty() && dateTextView != null) {
      dateTextView.setText(date);
    }
  }


  @Override
  public void onDestroyView() {
    super.onDestroyView();
    binding = null;
  }
}


