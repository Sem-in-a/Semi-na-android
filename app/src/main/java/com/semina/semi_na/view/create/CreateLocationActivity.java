package com.semina.semi_na.view.create;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.semina.semi_na.R;
import com.semina.semi_na.data.db.entity.Location;
import com.semina.semi_na.data.db.entity.Semina;
import com.semina.semi_na.databinding.ActivityCreateLocationBinding;
import com.semina.semi_na.view.adapter.CreateLocationRVAdapter;

import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;
import java.util.Arrays;

public class CreateLocationActivity extends AppCompatActivity {

    private ActivityCreateLocationBinding binding;
    private RecyclerView recyclerView;
    private CreateLocationRVAdapter adapter;
    ArrayList<Location> locationList = new ArrayList<>(Arrays.asList(Location.IT,Location.Law,Location.Business,Location.Engineer,Location.Jomansik));
    private Intent intent;

    private Location location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCreateLocationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        recyclerView = binding.createLocationRecyclerView;
        adapter = new CreateLocationRVAdapter();
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter.setLocationList(locationList);
        intent = getIntent();
        Semina semina = (Semina) intent.getSerializableExtra("semina");

        ActivityResultLauncher<Intent> launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {

                    @Override
                    public void onActivityResult(ActivityResult o) {


                    }
                });

        adapter.setOnItemClickListener(new CreateLocationRVAdapter.OnItemClickListener() {

            @Override
            public void onItemClick(View v, int position) {

                Log.d("CreateLocationActivity",locationList.get(position).getLocName());
                location = locationList.get(position);
                assert semina != null;
                semina.setLocation(locationList.get(position));
                launcher.launch(new Intent(getApplicationContext(),CreateLocationDetailActivity.class).putExtra("semina",semina)
                        .addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY));

            }
        }) ;


    }
}