package com.semina.semi_na.view.viewHolder;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.semina.semi_na.databinding.CollegeViewPagerItemBinding;
import com.semina.semi_na.view.adapter.SeminarFirestorePagingAdapter;

public class CollegePagerViewHolder extends RecyclerView.ViewHolder {
    private CollegeViewPagerItemBinding binding;

    public CollegePagerViewHolder(@NonNull CollegeViewPagerItemBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    public void bind(SeminarFirestorePagingAdapter adapter) {
        binding.collegeViewPagerRecyclerView.setLayoutManager(new GridLayoutManager(binding.getRoot().getContext(), 2));
        binding.collegeViewPagerRecyclerView.setAdapter(adapter);
    }
}
