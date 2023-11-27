package com.semina.semi_na.view.home;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.semina.semi_na.R;
import com.semina.semi_na.databinding.FragmentHobbyExerciseBinding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HobbyExerciseFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HobbyExerciseFragment extends Fragment {
    private FragmentHobbyExerciseBinding binding;

    public HobbyExerciseFragment() {
        // Required empty public constructor
    }

    public static HobbyExerciseFragment newInstance() {
        HobbyExerciseFragment fragment = new HobbyExerciseFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHobbyExerciseBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        // Inflate the layout for this fragment
        return view;
    }
}