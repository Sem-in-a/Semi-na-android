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
import com.semina.semi_na.databinding.FragmentAppliedProceedingBinding;
import com.semina.semi_na.databinding.SeminarCardViewItemBinding;
import com.semina.semi_na.view.detail.SeminaDetailActivity;
import com.semina.semi_na.view.viewHolder.SeminarCardViewHolder;

import static android.content.Context.MODE_PRIVATE;

public class AppliedProceedingFragment extends Fragment {
  private FragmentAppliedProceedingBinding binding;
  private FirestorePagingAdapter<Semina, SeminarCardViewHolder> adapter;

  private String getCurrentUserId() {
    SharedPreferences preferences = getActivity().getSharedPreferences("UserInfo", MODE_PRIVATE);
    return preferences.getString("studentNum", "");
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    binding = FragmentAppliedProceedingBinding.inflate(inflater, container, false);
    View view = binding.getRoot();

    String currentUserId = getCurrentUserId();

    Query baseQuery = FirebaseFirestore.getInstance()
        .collection("Semina")
        .whereArrayContains("memberList", currentUserId)
        .orderBy("date", Query.Direction.DESCENDING);

    FirestorePagingOptions<Semina> options = new FirestorePagingOptions.Builder<Semina>()
        .setLifecycleOwner(this)
        .setQuery(baseQuery, new PagingConfig(10, 5, false), Semina.class)
        .build();

    adapter = new FirestorePagingAdapter<Semina, SeminarCardViewHolder>(options) {
      @Override
      protected void onBindViewHolder(@NonNull SeminarCardViewHolder holder, int position, @NonNull Semina model) {
        holder.bind(model);

        holder.itemView.setOnClickListener(v -> {
          Intent intent = new Intent(getContext(), SeminaDetailActivity.class);
          intent.putExtra("Semina", model);
          startActivity(intent);
        });
      }

      @NonNull
      @Override
      public SeminarCardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
            .inflate(R.layout.seminar_card_view_item, parent, false);
        return new SeminarCardViewHolder(SeminarCardViewItemBinding.bind(itemView));
      }
    };

    binding.appiledRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
    binding.appiledRecyclerView.setAdapter(adapter);

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
