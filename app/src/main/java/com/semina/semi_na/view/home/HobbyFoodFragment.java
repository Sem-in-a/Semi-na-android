package com.semina.semi_na.view.home;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.semina.semi_na.R;
import com.semina.semi_na.databinding.FragmentHobbyFoodBinding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HobbyFoodFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HobbyFoodFragment extends Fragment {

    private FragmentHobbyFoodBinding binding;

    public HobbyFoodFragment() {
        // Required empty public constructor
    }

    public static HobbyFoodFragment newInstance() {
        HobbyFoodFragment fragment = new HobbyFoodFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHobbyFoodBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        // Inflate the layout for this fragment
        return view;
    }
}