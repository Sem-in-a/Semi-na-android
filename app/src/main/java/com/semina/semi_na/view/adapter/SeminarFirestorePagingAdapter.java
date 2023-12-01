package com.semina.semi_na.view.adapter;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.firebase.ui.firestore.paging.FirestorePagingAdapter;
import com.firebase.ui.firestore.paging.FirestorePagingOptions;
import com.semina.semi_na.R;
import com.semina.semi_na.data.db.entity.Semina;
import com.semina.semi_na.databinding.SeminarCardViewItemBinding;
import com.semina.semi_na.view.detail.SeminaDetailActivity;
import com.semina.semi_na.view.viewHolder.SeminarCardViewHolder;

public class SeminarFirestorePagingAdapter extends FirestorePagingAdapter<Semina, SeminarCardViewHolder> {

    public SeminarFirestorePagingAdapter(@NonNull FirestorePagingOptions<Semina> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull SeminarCardViewHolder holder, int position, @NonNull Semina model) {
        holder.bind(model);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("[SeminarFirestorePagingAdapter]", "onClick: " + model.getTitle());
                Intent intent = new Intent(holder.itemView.getContext(), SeminaDetailActivity.class);
                intent.putExtra("Semina", model);
                holder.itemView.getContext().startActivity(intent);
            }
        });
    }

    @NonNull
    @Override
    public SeminarCardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.seminar_card_view_item, parent, false);

        return new SeminarCardViewHolder(SeminarCardViewItemBinding.bind(view));
    }
}
