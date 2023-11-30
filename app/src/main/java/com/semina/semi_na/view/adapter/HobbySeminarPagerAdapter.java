package com.semina.semi_na.view.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import androidx.lifecycle.ViewTreeLifecycleOwner;
import androidx.paging.PagingConfig;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.paging.FirestorePagingOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.semina.semi_na.R;
import com.semina.semi_na.data.db.entity.HobbyCategory;
import com.semina.semi_na.data.db.entity.Semina;
import com.semina.semi_na.databinding.HobbyViewPagerItemBinding;
import com.semina.semi_na.view.viewHolder.HobbyPagerViewHolder;
import com.semina.semi_na.view.viewHolder.SeminarCardViewHolder;

import java.util.ArrayList;

public class HobbySeminarPagerAdapter extends RecyclerView.Adapter<HobbyPagerViewHolder> {
    ArrayList<HobbyCategory> hobbyList;
    SeminarFirestorePagingAdapter adapter;

    public HobbySeminarPagerAdapter(ArrayList<HobbyCategory> hobbyList) {
        this.hobbyList = hobbyList;
    }

    @NonNull
    @Override
    public HobbyPagerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.hobby_view_pager_item, parent, false);
        return new HobbyPagerViewHolder(HobbyViewPagerItemBinding.bind(view));
    }

    @Override
    public void onBindViewHolder(@NonNull HobbyPagerViewHolder holder, int position) {
    }


    // ViewHolder의 View가 RecyclerView에 붙는 onViewAttachedToWindow 시점 이후에
    // ViewHolder의 LifecycleOwner를 찾을 수 있음
    @Override
    public void onViewAttachedToWindow(@NonNull HobbyPagerViewHolder holder) {
        super.onViewAttachedToWindow(holder);

        HobbyCategory hobbyCategory = hobbyList.get(holder.getAbsoluteAdapterPosition());
        Log.d("[HobbySeminarPager]", "onViewAttachedToWindow: " + hobbyCategory);

        // The "base query" is a query with no startAt/endAt/limit clauses that the adapter can use
        // to form smaller queries for each page. It should only include where() and orderBy() clauses
        Query baseQuery = FirebaseFirestore.getInstance()
                .collection("Semina")
                .whereEqualTo("seminaCategory", "HOBBY")
                .whereEqualTo("hobbyCategory", hobbyCategory);

        // This configuration comes from the Paging 3 Library
        // https://developer.android.com/reference/kotlin/androidx/paging/PagingConfig
        PagingConfig config = new PagingConfig(/* page size */ 4, /* prefetchDistance */ 2,
                /* enablePlaceHolders */ false);

        // The options for the adapter combine the paging configuration with query information
        // and application-specific options for lifecycle, etc.
        FirestorePagingOptions<Semina> options = new FirestorePagingOptions.Builder<Semina>()
                .setLifecycleOwner(ViewTreeLifecycleOwner.get(holder.itemView)) // an activity or a fragment
                // 정해진 객체 타입으로 FireStore document를 받아 올 수 있게 모델을 제공
                .setQuery(baseQuery, config, Semina.class)
                .build();

        adapter = new SeminarFirestorePagingAdapter(options);

        holder.bind(adapter, hobbyCategory);

        adapter.startListening();
    }

    @Override
    public void onViewDetachedFromWindow(@NonNull HobbyPagerViewHolder holder) {
        super.onViewDetachedFromWindow(holder);

        Log.d("[HobbySeminarPager]", "onViewDetachedFromWindow: " + holder.getAbsoluteAdapterPosition());

        adapter.stopListening();
    }

    @Override
    public int getItemCount() {
        return hobbyList.size();
    }
}