package com.semina.semi_na.view.mypage;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.paging.PagingConfig;
import androidx.recyclerview.widget.GridLayoutManager;
import com.firebase.ui.firestore.paging.FirestorePagingAdapter;
import com.firebase.ui.firestore.paging.FirestorePagingOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.semina.semi_na.R;
import com.semina.semi_na.data.db.entity.Semina;
import com.semina.semi_na.databinding.FragmentHostedClosedBinding;
import com.semina.semi_na.databinding.SeminarCardViewItemBinding;
import com.semina.semi_na.view.viewHolder.SeminarCardViewHolder;
import java.util.Date;

// 내가 주최한 세미나 - 마감 된 : 프론트 단에서 현재 날짜 비교
public class HostedClosedFragment extends Fragment {

  private FragmentHostedClosedBinding binding;
  private FirestorePagingAdapter<Semina, SeminarCardViewHolder> adapter;

  private String getCurrentUserId() {
    SharedPreferences preferences = getActivity().getSharedPreferences("UserInfo", MODE_PRIVATE);
    return preferences.getString("studentNum", "");
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {

    binding = FragmentHostedClosedBinding.inflate(inflater, container, false);
    View view = binding.getRoot();

    String currentUserId = getCurrentUserId();

    Date currentDate = new Date();
    Query closedSeminaQuery = FirebaseFirestore.getInstance()
        .collection("Semina")
        .whereEqualTo("host", currentUserId)
        .whereLessThanOrEqualTo("date", currentDate)
        .orderBy("date", Query.Direction.DESCENDING);

    // 페이징 구성
    PagingConfig config = new PagingConfig(10, 5, false);

    // 어댑터 옵션 설정
    FirestorePagingOptions<Semina> closedSeminaOptions = new FirestorePagingOptions.Builder<Semina>()
        .setLifecycleOwner(this)
        .setQuery(closedSeminaQuery, config, Semina.class)
        .build();

    // 어댑터 설정
    adapter = new FirestorePagingAdapter<Semina, SeminarCardViewHolder>(closedSeminaOptions) {
      @Override
      protected void onBindViewHolder(@NonNull SeminarCardViewHolder holder, int position, @NonNull Semina model) {
        holder.bind(model);
        Log.d("HostedClosedFragment", "onBindViewHolder is called with position: " + position);
      }

      @NonNull
      @Override
      public SeminarCardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d("HostedClosedFragment", "onCreateViewHolder is called");
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



