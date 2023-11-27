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
import com.google.type.Date;
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
    // CardView 내의 각 View를 찾아옵니다.
    ImageView imageView = cardView.findViewById(R.id.hosted_img);
    TextView titleTextView = cardView.findViewById(R.id.hosted_title);
    TextView descriptionTextView = cardView.findViewById(R.id.hosted_description);
    TextView locationTextView = cardView.findViewById(R.id.hosted_under_department);
    TextView locationDetailTextView = cardView.findViewById(R.id.hosted_location_detail);
    TextView capacityTextView = cardView.findViewById(R.id.hosted_capacity);
    TextView hostedmajorTextView = cardView.findViewById(R.id.hosted_major);
    TextView hostedgradeTextView = cardView.findViewById(R.id.hosted_grade);
    TextView hostednameTextView = cardView.findViewById(R.id.hosted_name);

    //추가한 이유는 이미지가 느리게 로딩될 경우 null로 먼저 찍히면 앱이 꺼지기 때문입니다.
    if (imageView == null) {
      Log.e("MyPageFragment", "ImageView not found in CardView.");
      // 이 경우 이미지 뷰가 레이아웃에 없는 것이므로 더 이상 진행하지 않고 반환합니다.
      return;
    }

    // DocumentSnapshot에서 데이터 추출
    String imgUrl = document.getString("imgUrl");
    String title = document.getString("title");
    String description = document.getString("description");
    String location = document.getString("location");
    String locationDetail = document.getString("locationDetail");
    //마이페이라, 우선 프로필에서 가져오듯이 데이터 가져왔습니다!
    Long capacity = document.getLong("capacity");SharedPreferences preferences = getActivity().getSharedPreferences("UserInfo", MODE_PRIVATE);
    String userName = preferences.getString("name", "N/A");
    String userMajor = preferences.getString("major", "N/A");
    String userGrade = preferences.getString("studentNum", "N/A");

    if (imgUrl != null && !imgUrl.trim().isEmpty()) {
      Glide.with(this).load(imgUrl).into(imageView);
    } else {
      Log.e("MyPageFragment", "Image URL is null or empty.");
      // 우선 아무 기본 이미지 띄우기
      Glide.with(this).load(R.drawable.board).into(imageView);
    }

    // UI 컴포넌트에 데이터 바인딩
    Glide.with(this).load(imgUrl).into(imageView);
    titleTextView.setText(title);
    descriptionTextView.setText(description);
    locationTextView.setText(location);
    locationDetailTextView.setText(locationDetail);
    capacityTextView.setText(capacity + "명");
    hostednameTextView.setText(userName);
    hostedmajorTextView.setText(userMajor);
    hostedgradeTextView.setText(userGrade);

    // CardView를 보이게 설정
    cardView.setVisibility(View.VISIBLE);
  }


  @Override
  public void onDestroyView() {
    super.onDestroyView();
    binding = null; // View Binding 해제
  }
}


