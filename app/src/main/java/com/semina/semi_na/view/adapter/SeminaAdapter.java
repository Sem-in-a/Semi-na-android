package com.semina.semi_na.view.adapter;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.semina.semi_na.data.db.entity.Semina;
import com.semina.semi_na.databinding.SeminarCardViewItemBinding;
import com.semina.semi_na.view.detail.SeminaDetailActivity;
import com.semina.semi_na.view.viewHolder.SeminarCardViewHolder;

import java.util.List;

public class SeminaAdapter extends RecyclerView.Adapter<SeminarCardViewHolder> {
    private List<Semina> seminaList;

    public SeminaAdapter(List<Semina> seminaList) {
        this.seminaList = seminaList;
    }

    @NonNull
    @Override
    public SeminarCardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        SeminarCardViewItemBinding binding = SeminarCardViewItemBinding.inflate(layoutInflater, parent, false);
        return new SeminarCardViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull SeminarCardViewHolder holder, int position) {
        Semina semina = seminaList.get(position);
        holder.bind(semina);
    }

    @Override
    public int getItemCount() {
        return seminaList.size();
    }
}

