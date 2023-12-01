package com.semina.semi_na.view.viewHolder;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.semina.semi_na.data.db.entity.Member;
import com.semina.semi_na.databinding.ParticipantCardViewItemBinding;

public class ParticipantCardViewHolder extends RecyclerView.ViewHolder {
    private ParticipantCardViewItemBinding binding;

    public ParticipantCardViewHolder(@NonNull ParticipantCardViewItemBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    public void bind(Member member) {
        binding.participantNameTextView.setText(member.getName());
        binding.participantCollegeChipView.setText(member.getMajor());
        Glide.with(binding.getRoot().getContext())
                .load(member.getImgUrl())
                .into(binding.participantImageView);
    }

}
