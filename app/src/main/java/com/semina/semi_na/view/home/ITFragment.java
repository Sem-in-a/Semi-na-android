package com.semina.semi_na.view.home;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.semina.semi_na.R;
import com.semina.semi_na.databinding.FragmentITBinding;
import com.semina.semi_na.view.detail.SeminaDetailActivity;

public class ITFragment extends Fragment {
    private FragmentITBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentITBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        binding.cardView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("ITFragment", "cardView1 clicked");
                // Start SeminaDetailActivity
                Intent intent = new Intent(getActivity(), SeminaDetailActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}