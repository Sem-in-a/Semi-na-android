package com.semina.semi_na.view.viewHolder;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.semina.semi_na.data.db.entity.Semina;
import com.semina.semi_na.databinding.SeminarCardViewItemBinding;

public class DetailCardViewHolder extends RecyclerView.ViewHolder {
  private SeminarCardViewItemBinding binding;

  public DetailCardViewHolder(SeminarCardViewItemBinding binding) {
    super(binding.getRoot());
    this.binding = binding;
  }

  public void bind(Semina semina) {
    String imageUrl = semina.getImgUrl();
    Glide.with(binding.getRoot().getContext())
        .load(imageUrl)
        .into(binding.imageView);
    binding.titleTextView.setText(semina.getTitle());
    binding.descriptionTextView.setText(semina.getDescription());
    binding.collegeChipView.setText(semina.getLocation().getLocName());
    binding.locationTextView.setText(semina.getLocationDetail());
    binding.organizerTextView.setText(semina.getHost());
    binding.dateTextView.setText(semina.getDate());
    binding.participantTextView.setText(String.valueOf(semina.getCapacity()));
  }
}