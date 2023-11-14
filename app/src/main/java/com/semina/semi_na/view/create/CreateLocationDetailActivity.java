package com.semina.semi_na.view.create;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.semina.semi_na.R;
import com.semina.semi_na.data.db.entity.Location;
import com.semina.semi_na.data.db.entity.Semina;
import com.semina.semi_na.databinding.ActivityCreateLocationDetailBinding;
import com.semina.semi_na.view.adapter.CreateLocationDetailRVAdapter;
import com.semina.semi_na.view.adapter.CreateLocationRVAdapter;

import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

public class CreateLocationDetailActivity extends AppCompatActivity {

    private ActivityCreateLocationDetailBinding binding;
    private Intent intent;
    private FirebaseFirestore database;
    private ArrayList<String> locationDetailList;
    private RecyclerView recyclerView;
    private CreateLocationDetailRVAdapter adapter;

    ActivityResultLauncher<Intent> launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {

                @Override
                public void onActivityResult(ActivityResult o) {


                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCreateLocationDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        intent = getIntent();
        Semina semina = (Semina) intent.getSerializableExtra("semina");
        String locName = String.valueOf(semina.getLocation());
        binding.createLocationDetailName.setText(semina.getLocation().getLocName());

        locationDetailList = new ArrayList<>(10);

        database = FirebaseFirestore.getInstance();
        DocumentReference docRef = database.collection("Location").document(locName);
        recyclerView = binding.createLocationDetailRecyclerView;
        adapter = new CreateLocationDetailRVAdapter();
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Map<String, Object> documentMap = documentSnapshot.getData();

                if (documentMap != null) {
                    for (Map.Entry<String, Object> entry : documentMap.entrySet()) {
                        String key = entry.getKey();
                        String value = (String) entry.getValue();
                        Log.d("CreateLocationDetailActivity", value);
                        locationDetailList.add(value);
                    }

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            adapter.setLocationDetailList(locationDetailList);
                        }
                    });
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e("CreateLocationDetailActivity", "문서 가져오기 실패", e);
            }
        });

        adapter.setOnItemClickListener(new CreateLocationDetailRVAdapter.OnItemClickListener() {

            @Override
            public void onItemClick(View v, int position) {
                Log.d("CreateLocationDetailActivity",locationDetailList.get(position));
                assert semina != null;
                semina.setLocationDetail(locationDetailList.get(position));
                launcher.launch(new Intent(getApplicationContext(),CreateDateActivity.class).putExtra("semina",semina)
                        .addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY));

            }
        }) ;



    }
}