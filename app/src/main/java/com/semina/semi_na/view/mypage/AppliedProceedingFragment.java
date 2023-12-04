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
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.semina.semi_na.R;
import com.semina.semi_na.data.db.entity.Semina;
import com.semina.semi_na.databinding.FragmentAppliedProceedingBinding;
import com.semina.semi_na.databinding.SeminarCardViewItemBinding;
import com.semina.semi_na.view.detail.SeminaDetailActivity;
import com.semina.semi_na.view.viewHolder.SeminarCardViewHolder;

import static android.content.Context.MODE_PRIVATE;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class AppliedProceedingFragment extends Fragment {
  private FragmentAppliedProceedingBinding binding;
  private RecyclerView.Adapter<SeminarCardViewHolder> adapter;
  private List<Semina> proceedingSeminarsList = new ArrayList<>();

  private String getCurrentUserId() {
    SharedPreferences preferences = getActivity().getSharedPreferences("UserInfo", MODE_PRIVATE);
    return preferences.getString("studentNum", "");
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    binding = FragmentAppliedProceedingBinding.inflate(inflater, container, false);
    View view = binding.getRoot();

    String currentUserId = getCurrentUserId();

    FirebaseFirestore.getInstance()
        .collection("Semina")
        .whereArrayContains("memberList", currentUserId)
        .get()
        .addOnSuccessListener(queryDocumentSnapshots -> {
          for (DocumentSnapshot snapshot : queryDocumentSnapshots.getDocuments()) {
            Semina semina = snapshot.toObject(Semina.class);
            if (semina != null) {
              try {
                if (!semina.isClosed()) {  // 진행 중인 세미나만 필터링
                  proceedingSeminarsList.add(semina);
                }
              } catch (ParseException e) {
                Log.e("AppliedProceedingFragment", "Error parsing date", e);
              }
            }
          }
          setupRecyclerView();
        })
        .addOnFailureListener(e -> Log.e("AppliedProceedingFragment", "Error fetching data", e));

    return view;
  }

  private void setupRecyclerView() {
    adapter = new RecyclerView.Adapter<SeminarCardViewHolder>() {
      @NonNull
      @Override
      public SeminarCardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
            .inflate(R.layout.seminar_card_view_item, parent, false);
        return new SeminarCardViewHolder(SeminarCardViewItemBinding.bind(itemView));
      }

      @Override
      public void onBindViewHolder(@NonNull SeminarCardViewHolder holder, int position) {
        Semina semina = proceedingSeminarsList.get(position);
        holder.bind(semina);

        holder.itemView.setOnClickListener(v -> {
          Intent intent = new Intent(getContext(), SeminaDetailActivity.class);
          intent.putExtra("Semina", semina);
          startActivity(intent);
        });
      }

      @Override
      public int getItemCount() {
        return proceedingSeminarsList.size();
      }
    };

    binding.appiledRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
    binding.appiledRecyclerView.setAdapter(adapter);
  }

}
