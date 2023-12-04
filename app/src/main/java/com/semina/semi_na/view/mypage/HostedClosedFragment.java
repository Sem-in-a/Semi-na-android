package com.semina.semi_na.view.mypage;

import static android.content.Context.MODE_PRIVATE;

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
import com.semina.semi_na.databinding.FragmentHostedClosedBinding;
import com.semina.semi_na.databinding.SeminarCardViewItemBinding;
import com.semina.semi_na.view.detail.SeminaDetailActivity;
import com.semina.semi_na.view.viewHolder.SeminarCardViewHolder;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;


public class HostedClosedFragment extends Fragment {

  private FragmentHostedClosedBinding binding;
  private RecyclerView.Adapter<SeminarCardViewHolder> adapter;
  private List<Semina> closedSeminarsList = new ArrayList<>();

  private String getCurrentUserId() {
    SharedPreferences preferences = getActivity().getSharedPreferences("UserInfo", MODE_PRIVATE);
    return preferences.getString("studentNum", "");
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    binding = FragmentHostedClosedBinding.inflate(inflater, container, false);
    String currentUserId = getCurrentUserId();

    FirebaseFirestore.getInstance()
        .collection("Semina")
        .whereEqualTo("host", currentUserId)
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
                Log.e("HostedClosedFragment", "Error parsing date", e);
              }
            }
          }
          setupRecyclerView();
        })
        .addOnFailureListener(e -> Log.e("HostedClosedFragment", "Error fetching data", e));

    return binding.getRoot();
  }

  private void setupRecyclerView() {
    adapter = new RecyclerView.Adapter<SeminarCardViewHolder>() {
      @NonNull
      @Override
      public SeminarCardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
            .inflate(R.layout.seminar_card_view_item, parent, false);
        SeminarCardViewItemBinding binding = SeminarCardViewItemBinding.bind(itemView);
        return new SeminarCardViewHolder(binding);
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

    binding.hosted2RecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
    binding.hosted2RecyclerView.setAdapter(adapter);
  }

}




