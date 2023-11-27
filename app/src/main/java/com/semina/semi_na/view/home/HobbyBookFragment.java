package com.semina.semi_na.view.home;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.semina.semi_na.R;
import com.semina.semi_na.databinding.FragmentHobbyBookBinding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HobbyBookFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HobbyBookFragment extends Fragment {
    private FragmentHobbyBookBinding binding;

    public HobbyBookFragment() {
        // Required empty public constructor
    }

    public static HobbyBookFragment newInstance() {
        HobbyBookFragment fragment = new HobbyBookFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHobbyBookBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        // Inflate the layout for this fragment
        return view;
    }
}