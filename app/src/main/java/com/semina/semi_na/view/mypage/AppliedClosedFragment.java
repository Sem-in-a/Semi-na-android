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
import com.semina.semi_na.databinding.FragmentAppliedClosedBinding;
import com.semina.semi_na.databinding.SeminarCardViewItemBinding;
import com.semina.semi_na.view.detail.SeminaDetailActivity;
import com.semina.semi_na.view.viewHolder.SeminarCardViewHolder;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class AppliedClosedFragment extends Fragment {
  private FragmentAppliedClosedBinding binding;
  private RecyclerView.Adapter<SeminarCardViewHolder> adapter;
  private List<Semina> closedSeminarsList = new ArrayList<>();

  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    binding = FragmentAppliedClosedBinding.inflate(inflater, container, false);
    View view = binding.getRoot();

    SharedPreferences preferences = getActivity().getSharedPreferences("UserInfo", MODE_PRIVATE);
    String currentUserId = preferences.getString("studentNum", "");

    FirebaseFirestore.getInstance()
        .collection("Semina")
        .whereArrayContains("memberList", currentUserId)
        .get()
        .addOnSuccessListener(queryDocumentSnapshots -> {
          for (DocumentSnapshot snapshot : queryDocumentSnapshots.getDocuments()) {
            Semina semina = snapshot.toObject(Semina.class);
            if (semina != null) {
              try {
                if (semina.isClosed()) {
                  closedSeminarsList.add(semina);
                }
              } catch (ParseException e) {
                Log.e("AppliedClosedFragment", "Error parsing date", e);
              }
            }
          }
          setupRecyclerView();
        })
        .addOnFailureListener(e -> Log.e("AppliedClosedFragment", "Error fetching data", e));

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
        Semina semina = closedSeminarsList.get(position);
        holder.bind(semina);
        holder.itemView.setOnClickListener(v -> {
          Intent intent = new Intent(getContext(), SeminaDetailActivity.class);
          intent.putExtra("Semina", semina);
          startActivity(intent);
        });
      }

      @Override
      public int getItemCount() {
        return closedSeminarsList.size();
      }
    };

    RecyclerView recyclerView = binding.appiled2RecyclerView;
    recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
    recyclerView.setAdapter(adapter);
  }

}