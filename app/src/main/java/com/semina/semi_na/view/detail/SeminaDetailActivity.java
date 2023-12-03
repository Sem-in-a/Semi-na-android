package com.semina.semi_na.view.detail;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewTreeLifecycleOwner;
import androidx.paging.PagingConfig;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
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
import com.semina.semi_na.view.adapter.SeminarFirestorePagingAdapter;
import com.semina.semi_na.view.adapter.SeminarParticipantAdapter;

import java.util.ArrayList;

public class SeminaDetailActivity extends AppCompatActivity {
    private ActivitySeminaDetailBinding binding;
    private SeminarFirestorePagingAdapter recommendedSeminarAdapter;

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

        // The "base query" is a query with no startAt/endAt/limit clauses that the adapter can use
        // to form smaller queries for each page. It should only include where() and orderBy() clauses
        Query baseQuery = FirebaseFirestore.getInstance()
                .collection("Semina")
                .orderBy("date", Query.Direction.ASCENDING)
                .limit(2);

        // This configuration comes from the Paging 3 Library
        // https://developer.android.com/reference/kotlin/androidx/paging/PagingConfig
        PagingConfig config = new PagingConfig(2, 0, true, 2, 2);

        // The options for the adapter combine the paging configuration with query information
        // and application-specific options for lifecycle, etc.
        FirestorePagingOptions<Semina> options = new FirestorePagingOptions.Builder<Semina>()
                .setLifecycleOwner(this)// an activity or a fragment
                // 정해진 객체 타입으로 FireStore document를 받아 올 수 있게 모델을 제공
                .setQuery(baseQuery, config, Semina.class)
                .build();


        recommendedSeminarAdapter = new SeminarFirestorePagingAdapter(options);

        // 추천 세미나 리사이클러뷰
        binding.recommendedSeminaRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        binding.recommendedSeminaRecyclerView.setAdapter(recommendedSeminarAdapter);

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
                                    showBottomSheetDialog(semina);
                                }
                            }
                        });
            }
        });
    }


    private void showBottomSheetDialog(Semina semina) {
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this, R.style.BottomSheetDialogTheme);
        View bottomSheetView = LayoutInflater.from(this).inflate(R.layout.activity_semina_apply_bottom_sheet_dialog, (LinearLayout) findViewById(R.id.bottomSheetLayout));

        TextView seminarNameTextView = bottomSheetView.findViewById(R.id.seminarNameTextView);
        seminarNameTextView.setText(semina.getTitle());

        TextView seminarDateTextView = bottomSheetView.findViewById(R.id.seminarDateTextView);
        seminarDateTextView.setText(semina.getDate());

        TextView seminarLocationTextView = bottomSheetView.findViewById(R.id.seminarPlaceTextView);
        SeminaCategory category = semina.getSeminaCategory();

        if (category == SeminaCategory.MAJOR) {
            seminarLocationTextView.setText(semina.getLocation().getLocName() + " " + semina.getLocationDetail());
        } else if (category == SeminaCategory.HOBBY) {
            seminarLocationTextView.setText(semina.getHobbyLocation());
        }

        TextView seminarApplyPersonTextView = bottomSheetView.findViewById(R.id.seminarApplyPersonTextView);
        String name = getSharedPreferences("UserInfo", MODE_PRIVATE).getString("name", "");
        seminarApplyPersonTextView.setText(name);

        bottomSheetView.findViewById(R.id.confirmButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("SeminaDetailActivity", "confirmButton clicked");
                bottomSheetDialog.dismiss();

                // get Semina
                FirebaseFirestore.getInstance()
                        .collection("Semina")
                        .whereEqualTo("title", semina.getTitle())
                        .get()
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                QuerySnapshot querySnapshot = task.getResult();
                                if (querySnapshot != null) {
                                    DocumentSnapshot documentSnapshot = querySnapshot.getDocuments().get(0);
                                    Semina semina = documentSnapshot.toObject(Semina.class);
                                    Intent intent = new Intent(SeminaDetailActivity.this, SeminaDetailActivity.class);
                                    intent.putExtra("Semina", semina);

                                    // Activity 재시작
                                    finish();
                                    overridePendingTransition(0, 0);
                                    startActivity(intent);
                                    overridePendingTransition(0, 0);
                                }
                            }
                        });
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
        binding.seminaParticipantTextView.setText(semina.getMemberList().size() + "/" + semina.getCapacity());
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

    @Override
    protected void onStart() {
        super.onStart();
        recommendedSeminarAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        recommendedSeminarAdapter.stopListening();
    }

}