package com.semina.semi_na.view.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.semina.semi_na.R;
import com.semina.semi_na.data.db.entity.Member;
import com.semina.semi_na.databinding.ParticipantCardViewItemBinding;
import com.semina.semi_na.view.viewHolder.ParticipantCardViewHolder;

import java.util.ArrayList;

public class SeminarParticipantAdapter extends RecyclerView.Adapter<ParticipantCardViewHolder> {
    private ArrayList<Member> memberList;

    public SeminarParticipantAdapter(ArrayList<Member> memberList) {
        this.memberList = memberList;
    }

    @NonNull
    @Override
    public ParticipantCardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.participant_card_view_item, parent, false);
        return new ParticipantCardViewHolder(ParticipantCardViewItemBinding.bind(view));
    }

    @Override
    public void onBindViewHolder(@NonNull ParticipantCardViewHolder holder, int position) {
        holder.bind(memberList.get(position));
    }

    @Override
    public int getItemCount() {
        return memberList.size();
    }
}
