package com.semina.semi_na.view.mypage;

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
import com.semina.semi_na.databinding.FragmentHostedProceedingBinding;
import com.semina.semi_na.databinding.SeminarCardViewItemBinding;
import com.semina.semi_na.view.viewHolder.SeminarCardViewHolder;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class HostedProceedingFragment extends Fragment {

  private FragmentHostedProceedingBinding binding;
  private RecyclerView.Adapter<SeminarCardViewHolder> adapter;
  private List<Semina> proceedingSeminarsList = new ArrayList<>();

  private String getCurrentUserId() {
    SharedPreferences preferences = getActivity().getSharedPreferences("UserInfo", MODE_PRIVATE);
    return preferences.getString("studentNum", "");
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    binding = FragmentHostedProceedingBinding.inflate(inflater, container, false);
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
                if (!semina.isClosed()) {
                  proceedingSeminarsList.add(semina);
                }
              } catch (ParseException e) {
                Log.e("HostedProceedingFragment", "Error parsing date", e);
              }
            }
          }
          setupRecyclerView();
        })
        .addOnFailureListener(e -> Log.e("HostedProceedingFragment", "Error fetching data", e));

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
        Semina semina = proceedingSeminarsList.get(position);
        holder.bind(semina);
      }

      @Override
      public int getItemCount() {
        return proceedingSeminarsList.size();
      }
    };

    binding.hostedRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
    binding.hostedRecyclerView.setAdapter(adapter);
  }
}
