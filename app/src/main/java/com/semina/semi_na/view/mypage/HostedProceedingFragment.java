package com.semina.semi_na.view.mypage;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.paging.PagingConfig;
import androidx.recyclerview.widget.GridLayoutManager;
import com.firebase.ui.firestore.paging.FirestorePagingAdapter;
import com.firebase.ui.firestore.paging.FirestorePagingOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.semina.semi_na.R;
import com.semina.semi_na.data.db.entity.Semina;
import com.semina.semi_na.databinding.FragmentHostedProceedingBinding;
import com.semina.semi_na.databinding.SeminarCardViewItemBinding;
import com.semina.semi_na.view.detail.SeminaDetailActivity;
import com.semina.semi_na.view.viewHolder.SeminarCardViewHolder;

import static android.content.Context.MODE_PRIVATE;

//내가 주최한 세미나 - 진행 중
public class HostedProceedingFragment extends Fragment {
  private FragmentHostedProceedingBinding binding;
  private FirestorePagingAdapter<Semina, SeminarCardViewHolder> adapter;

  // 현재 로그인한 사용자의 ID를 가져오는 메서드
  private String getCurrentUserId() {
    SharedPreferences preferences = getActivity().getSharedPreferences("UserInfo", MODE_PRIVATE);
    return preferences.getString("studentNum", "");
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    binding = FragmentHostedProceedingBinding.inflate(inflater, container, false);
    View view = binding.getRoot();

    String currentUserId = getCurrentUserId();
    //Date currentDate = new Date();

    Log.d("HostedProceedingFragment", "Current User ID: " + currentUserId);

    // 파이어스토어 쿼리 설정
    Query baseQuery = FirebaseFirestore.getInstance()
        .collection("Semina")
        .whereEqualTo("host", currentUserId)
        .orderBy("date", Query.Direction.DESCENDING);

    // 페이징 구성
    PagingConfig config = new PagingConfig(10, 5, false);

    // 어댑터 옵션 설정
    FirestorePagingOptions<Semina> options = new FirestorePagingOptions.Builder<Semina>()
        .setLifecycleOwner(this)
        .setQuery(baseQuery, config, Semina.class)
        .build();

    // 어댑터 설정
    adapter = new FirestorePagingAdapter<Semina, SeminarCardViewHolder>(options) {
      @Override
      protected void onBindViewHolder(@NonNull SeminarCardViewHolder holder, int position, @NonNull Semina model) {
        holder.bind(model);

        // 클릭 리스너 추가
        holder.itemView.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
            Intent intent = new Intent(getContext(), SeminaDetailActivity.class);
            intent.putExtra("Semina", model);
            startActivity(intent);
          }
        });
      }

      @NonNull
      @Override
      public SeminarCardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
            .inflate(R.layout.seminar_card_view_item, parent, false);
        SeminarCardViewItemBinding binding = SeminarCardViewItemBinding.bind(itemView);
        return new SeminarCardViewHolder(binding);
      }
    };

    // 리사이클러뷰 설정
    binding.hostedRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
    binding.hostedRecyclerView.setAdapter(adapter);

    return view;
  }

  @Override
  public void onStart() {
    super.onStart();
    if (adapter != null) {
      adapter.startListening();
    }
  }

  @Override
  public void onStop() {
    super.onStop();
    if (adapter != null) {
      adapter.stopListening();
    }
  }
}

