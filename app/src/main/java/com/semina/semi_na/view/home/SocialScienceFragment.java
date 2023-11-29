package com.semina.semi_na.view.home;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.paging.PagingConfig;
import androidx.recyclerview.widget.GridLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.firestore.paging.FirestorePagingAdapter;
import com.firebase.ui.firestore.paging.FirestorePagingOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.semina.semi_na.R;
import com.semina.semi_na.data.db.entity.Semina;
import com.semina.semi_na.databinding.FragmentSocialScienceBinding;
import com.semina.semi_na.databinding.SeminarCardViewItemBinding;
import com.semina.semi_na.view.detail.SeminaDetailActivity;
import com.semina.semi_na.view.viewHolder.SeminarCardViewHolder;

public class SocialScienceFragment extends Fragment {
    private FragmentSocialScienceBinding binding;
    private FirestorePagingAdapter<Semina, SeminarCardViewHolder> adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentSocialScienceBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        // The "base query" is a query with no startAt/endAt/limit clauses that the adapter can use
        // to form smaller queries for each page. It should only include where() and orderBy() clauses
        Query baseQuery = FirebaseFirestore.getInstance()
                .collection("Semina")
                .whereEqualTo("seminaCategory", "MAJOR")
                .whereEqualTo("majorCategory", "SOCIAL");

        // This configuration comes from the Paging 3 Library
        // https://developer.android.com/reference/kotlin/androidx/paging/PagingConfig
        PagingConfig config = new PagingConfig(/* page size */ 4, /* prefetchDistance */ 2,
                /* enablePlaceHolders */ false);

        // The options for the adapter combine the paging configuration with query information
        // and application-specific options for lifecycle, etc.
        FirestorePagingOptions<Semina> options = new FirestorePagingOptions.Builder<Semina>()
                .setLifecycleOwner(this) // an activity or a fragment
                // 정해진 객체 타입으로 FireStore document를 받아 올 수 있게 모델을 제공
                .setQuery(baseQuery, config, Semina.class)
                .build();

        adapter = new FirestorePagingAdapter<Semina, SeminarCardViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull SeminarCardViewHolder holder, int position, @NonNull Semina model) {
                holder.bind(model);
            }

            @NonNull
            @Override
            public SeminarCardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.seminar_card_view_item, parent, false);

                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.d("ITFragment", "SeminarCardView Clicked");
                        Intent intent = new Intent(getActivity(), SeminaDetailActivity.class);
                        startActivity(intent);
                    }
                });

                return new SeminarCardViewHolder(SeminarCardViewItemBinding.bind(view));
            }
        };

        binding.socialScienceRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        binding.socialScienceRecyclerView.setAdapter(adapter);
    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}