package com.semina.semi_na.view.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.semina.semi_na.data.db.entity.Semina;
import com.semina.semi_na.databinding.SearchCardViewItemBinding;
import com.semina.semi_na.view.viewHolder.SearchCardViewHolder;
import java.util.ArrayList;
import java.util.List;
import android.util.Log;

public class SearchResultsAdapter extends RecyclerView.Adapter<SearchCardViewHolder> {

  private FirebaseFirestore firestore;
  private List<Semina> filteredSeminaList;
  private OnItemClickListener listener;

  //클릭 리스너 추가
  public interface OnItemClickListener {
    void onItemClick(Semina semina);
  }
  public void setOnItemClickListener(OnItemClickListener listener) {
    this.listener = listener;
  }

  public SearchResultsAdapter() {
    firestore = FirebaseFirestore.getInstance();
    filteredSeminaList = new ArrayList<>();
  }

  @NonNull
  @Override
  public SearchCardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
    SearchCardViewItemBinding itemBinding = SearchCardViewItemBinding.inflate(layoutInflater, parent, false);
    return new SearchCardViewHolder(itemBinding);
  }

  @Override
  public void onBindViewHolder(@NonNull SearchCardViewHolder holder, int position) {
    Semina semina = filteredSeminaList.get(position);
    holder.bind(semina);

    holder.itemView.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if (listener != null) {
          listener.onItemClick(semina);
        }
      }
    });
  }

  @Override
  public int getItemCount() {
    return filteredSeminaList.size();
  }

  // 검색 기능 함수
  public void search(String searchWord) {
    firestore.collection("Semina")
        .addSnapshotListener((querySnapshot, e) -> {
          if (e != null) {
            Log.w("SearchAdapter", "Listen failed.", e);
            return;
          }

          if (querySnapshot != null) {
            filteredSeminaList.clear();

            for (DocumentSnapshot document : querySnapshot.getDocuments()) {
              String title = document.getString("title");
              if (title != null && title.toLowerCase().contains(searchWord.toLowerCase())) {
                Semina item = document.toObject(Semina.class);
                if (item != null) {
                  filteredSeminaList.add(item);
                }
              }
            }
            notifyDataSetChanged();
          } else {
            Log.d("SearchAdapter", "Current data: null");
          }
        });
  }

}
