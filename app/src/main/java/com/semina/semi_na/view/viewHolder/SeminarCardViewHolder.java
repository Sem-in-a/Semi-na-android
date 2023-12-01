package com.semina.semi_na.view.viewHolder;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.semina.semi_na.data.db.entity.HobbyCategory;
import com.semina.semi_na.data.db.entity.MajorCategory;
import com.semina.semi_na.data.db.entity.Member;
import com.semina.semi_na.data.db.entity.Semina;
import com.semina.semi_na.data.db.entity.SeminaCategory;
import com.semina.semi_na.databinding.SeminarCardViewItemBinding;

public class SeminarCardViewHolder extends RecyclerView.ViewHolder {
    private SeminarCardViewItemBinding binding;

    public SeminarCardViewHolder(SeminarCardViewItemBinding binding) {
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
        binding.dateTextView.setText(semina.getDate());
        binding.participantTextView.setText(semina.getMemberList().size() + "/" + semina.getCapacity());
        setOrganizerInfo(semina.getHost());
        setLocationTextView(semina);
        setChipViewText(semina);
    }

    private void setOrganizerInfo(String host) {
        Member member;
        // Get a document whose studentNum matches host from the Member collection in Firestore
        FirebaseFirestore.getInstance()
                .collection("Member")
                .whereEqualTo("studentNum", host)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            QuerySnapshot querySnapshot = task.getResult();
                            if (querySnapshot != null) {
                                DocumentSnapshot documentSnapshot = querySnapshot.getDocuments().get(0);
                                Member member = documentSnapshot.toObject(Member.class);
                                binding.organizerTextView.setText(member.getMajor() + " " + member.getName());
                            }
                        }
                    }
                });
    }

    private void setLocationTextView(Semina semina) {
        SeminaCategory category = semina.getSeminaCategory();

        if (category == SeminaCategory.MAJOR) {
            binding.locationTextView.setText(semina.getLocation().getLocName() + " " + semina.getLocationDetail());
            return;
        }

        if (category == SeminaCategory.HOBBY) {
            binding.locationTextView.setText(semina.getHobbyLocation());
        }
    }

    private void setChipViewText(Semina semina) {
        SeminaCategory category = semina.getSeminaCategory();

        if (category == SeminaCategory.MAJOR) {
            MajorCategory major = semina.getMajorCategory();
            if (major == null) {
                binding.collegeChipView.setText(MajorCategory.NULL.getMajorName());
            } else {
                binding.collegeChipView.setText(major.getMajorName());
            }
            return;
        }

        if (category == SeminaCategory.HOBBY) {
            HobbyCategory hobby = semina.getHobbyCategory();
            if (hobby == null) {
                binding.collegeChipView.setText(HobbyCategory.NULL.getHobbyName());
            } else {
                binding.collegeChipView.setText(hobby.getHobbyName());
            }
        }
    }
}
