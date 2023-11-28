package com.semina.semi_na.view.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.semina.semi_na.data.db.entity.Semina;
import com.semina.semi_na.databinding.SeminarCardViewItemBinding;
import com.semina.semi_na.view.viewHolder.DetailCardViewHolder;
import java.util.List;

public class SeminaAdapter extends RecyclerView.Adapter<DetailCardViewHolder> {
  private List<Semina> seminaList;

  public SeminaAdapter(List<Semina> seminaList) {
    this.seminaList = seminaList;
  }

  @NonNull
  @Override
  public DetailCardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
    SeminarCardViewItemBinding binding = SeminarCardViewItemBinding.inflate(layoutInflater, parent, false);
    return new DetailCardViewHolder(binding);
  }

  @Override
  public void onBindViewHolder(@NonNull DetailCardViewHolder holder, int position) {
    Semina semina = seminaList.get(position);
    holder.bind(semina);
  }

  @Override
  public int getItemCount() {
    return seminaList.size();
  }
}
