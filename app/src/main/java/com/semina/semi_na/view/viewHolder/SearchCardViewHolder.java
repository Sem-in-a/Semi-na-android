package com.semina.semi_na.view.viewHolder;

import android.util.Log;
import androidx.recyclerview.widget.RecyclerView;
import com.semina.semi_na.data.db.entity.Semina;
import com.semina.semi_na.databinding.SearchCardViewItemBinding;

public class SearchCardViewHolder extends RecyclerView.ViewHolder {

  private SearchCardViewItemBinding binding;

  public SearchCardViewHolder(SearchCardViewItemBinding binding){
    super(binding.getRoot());
    this.binding = binding;
  }

  public void bind(Semina semina){
    Log.d("SearchCardViewHolder", "검색 결과 Title: " + semina.getTitle());
    //Log.d("SearchCardViewHolder", "Date: " + semina.getDate());
    //Log.d("SearchCardViewHolder", "Capacity: " + semina.getCapacity());

    binding.searchTitle.setText(semina.getTitle());
    binding.searchDate.setText(semina.getDate());
    binding.searchCapacity.setText(semina.getCapacity() + "");
  }

}
