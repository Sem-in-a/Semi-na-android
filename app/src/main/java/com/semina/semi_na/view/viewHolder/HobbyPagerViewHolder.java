package com.semina.semi_na.view.viewHolder;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.semina.semi_na.R;
import com.semina.semi_na.data.db.entity.HobbyCategory;
import com.semina.semi_na.databinding.HobbyViewPagerItemBinding;
import com.semina.semi_na.view.adapter.SeminarFirestorePagingAdapter;

import java.util.ArrayList;
import java.util.LinkedHashMap;

public class HobbyPagerViewHolder extends RecyclerView.ViewHolder {
    private static final LinkedHashMap<HobbyCategory, Integer> hobbyImageMap = new LinkedHashMap() {{
        put(HobbyCategory.EXERCISE, R.drawable.seminar_hobby_exercise);
        put(HobbyCategory.FOOD, R.drawable.seminar_hobby_food);
        put(HobbyCategory.MUSIC, R.drawable.seminar_hobby_music);
        put(HobbyCategory.BOOK, R.drawable.seminar_hobby_book);
    }};
    private HobbyViewPagerItemBinding binding;

    public HobbyPagerViewHolder(@NonNull HobbyViewPagerItemBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    public void bind(SeminarFirestorePagingAdapter adapter, HobbyCategory hobbyCategory) {
        binding.hobbyImageView.setImageResource(hobbyImageMap.get(hobbyCategory));
        binding.hobbyViewPagerRecyclerView.setLayoutManager(new GridLayoutManager(binding.getRoot().getContext(), 2));
        binding.hobbyViewPagerRecyclerView.setAdapter(adapter);
    }
}
