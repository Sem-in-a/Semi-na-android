package com.semina.semi_na.view.detail;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewTreeLifecycleOwner;
import androidx.paging.PagingConfig;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.firebase.ui.firestore.paging.FirestorePagingAdapter;
import com.firebase.ui.firestore.paging.FirestorePagingOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.model.mutation.ArrayTransformOperation;
import com.semina.semi_na.R;
import com.semina.semi_na.data.db.entity.HobbyCategory;
import com.semina.semi_na.data.db.entity.MajorCategory;
import com.semina.semi_na.data.db.entity.Member;
import com.semina.semi_na.data.db.entity.Semina;
import com.semina.semi_na.data.db.entity.SeminaCategory;
import com.semina.semi_na.databinding.ActivitySeminaDetailBinding;
import com.semina.semi_na.view.adapter.SeminaAdapter;
import com.semina.semi_na.view.adapter.SeminarParticipantAdapter;

import java.util.ArrayList;

public class SeminaDetailActivity extends AppCompatActivity {
    private ActivitySeminaDetailBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySeminaDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Semina semina = (Semina) getIntent().getSerializableExtra("Semina");
        bindDataToView(semina);

        if (semina.getMemberList().size() == 0) {
            binding.participantLinearLayout.setVisibility(View.GONE);
            binding.participantDivider.setVisibility(View.GONE);
        } else {
            Query participantQuery = FirebaseFirestore.getInstance()
                    .collection("Member")
                    .whereIn("studentNum", semina.getMemberList());

            participantQuery.get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    QuerySnapshot querySnapshot = task.getResult();
                    if (querySnapshot != null) {
                        ArrayList<Member> memberList = new ArrayList<>();
                        for (DocumentSnapshot documentSnapshot : querySnapshot.getDocuments()) {
                            Member member = documentSnapshot.toObject(Member.class);
                            memberList.add(member);
                        }

                        // 세미나 참가자 리사이클러뷰
                        binding.participantRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
                        binding.participantRecyclerView.setAdapter(new SeminarParticipantAdapter(memberList));
                    }
                }
            });
        }

        Query recommendedSeminarQuery = FirebaseFirestore.getInstance()
                .collection("Semina")
                .limit(2);

        recommendedSeminarQuery.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                QuerySnapshot querySnapshot = task.getResult();
                if (querySnapshot != null) {
                    ArrayList<Semina> seminaList = new ArrayList<>();
                    for (DocumentSnapshot documentSnapshot : querySnapshot.getDocuments()) {
                        Semina recommendedSemina = documentSnapshot.toObject(Semina.class);
                        seminaList.add(recommendedSemina);
                    }

                    // 추천 세미나 리사이클러뷰
                    binding.recommendedSeminaRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
                    binding.recommendedSeminaRecyclerView.setAdapter(new SeminaAdapter(seminaList));
                }
            }
        });


        binding.applyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("SeminaDetailActivity", "applyButton clicked");

                String studentNum = getSharedPreferences("UserInfo", MODE_PRIVATE).getString("studentNum", "");
                // add studentNum to the Semina Collection memberList field
                FirebaseFirestore.getInstance()
                        .collection("Semina")
                        .whereEqualTo("title", binding.seminaTitleTextView.getText().toString())
                        .get()
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                QuerySnapshot querySnapshot = task.getResult();
                                if (querySnapshot != null) {
                                    DocumentSnapshot documentSnapshot = querySnapshot.getDocuments().get(0);
                                    documentSnapshot.getReference().update("memberList", FieldValue.arrayUnion(studentNum));
                                    showBottomSheetDialog();
                                }
                            }
                        });
            }
        });
    }

    private void showBottomSheetDialog() {
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this, R.style.BottomSheetDialogTheme);
        View bottomSheetView = LayoutInflater.from(this).inflate(R.layout.activity_semina_apply_bottom_sheet_dialog, (LinearLayout) findViewById(R.id.bottomSheetLayout));
        bottomSheetView.findViewById(R.id.confirmButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("SeminaDetailActivity", "confirmButton clicked");
                bottomSheetDialog.dismiss();
            }
        });

        bottomSheetDialog.setContentView(bottomSheetView);
        bottomSheetDialog.show();
    }

    private void bindDataToView(Semina semina) {
        Glide.with(this).load(semina.getImgUrl()).into(binding.seminaImageView);
        binding.seminaTitleTextView.setText(semina.getTitle());
        binding.seminaDateTextView.setText(semina.getDate());
        setChipViewText(semina);
        setLocationTextView(semina);
        binding.seminaParticipantTextView.setText(String.valueOf(semina.getCapacity()));
        binding.seminaDescriptionTextView.setText(semina.getDescription());
        setHostInfo(semina.getHost());
    }

    private void setChipViewText(Semina semina) {
        SeminaCategory category = semina.getSeminaCategory();

        if (category == SeminaCategory.MAJOR) {
            MajorCategory major = semina.getMajorCategory();
            if (major == null) {
                binding.seminaCollegeChipView.setText(MajorCategory.NULL.getMajorName());
            } else {
                binding.seminaCollegeChipView.setText(major.getMajorName());
            }
            return;
        }

        if (category == SeminaCategory.HOBBY) {
            HobbyCategory hobby = semina.getHobbyCategory();
            if (hobby == null) {
                binding.seminaCollegeChipView.setText(HobbyCategory.NULL.getHobbyName());
            } else {
                binding.seminaCollegeChipView.setText(hobby.getHobbyName());
            }
        }
    }

    private void setLocationTextView(Semina semina) {
        SeminaCategory category = semina.getSeminaCategory();

        if (category == SeminaCategory.MAJOR) {
            binding.seminaLocationTextView.setText(semina.getLocation().getLocName() + " " + semina.getLocationDetail());
            return;
        }

        if (category == SeminaCategory.HOBBY) {
            binding.seminaLocationTextView.setText(semina.getHobbyLocation());
        }
    }

    private void setHostInfo(String host) {
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
                                binding.hostNameTextView.setText(member.getName());
                                binding.hostInfoTextView.setText(member.getDepartment() + " " + member.getMajor());
                                binding.hostDescriptionTextView.setText(member.getMessage());
                                Glide.with(SeminaDetailActivity.this)
                                        .load(member.getImgUrl())
                                        .into(binding.hostImageView);
                            }
                        }
                    }
                });
    }
}