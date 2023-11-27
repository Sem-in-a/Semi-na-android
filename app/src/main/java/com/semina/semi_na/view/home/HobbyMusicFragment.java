package com.semina.semi_na.view.home;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.semina.semi_na.R;
import com.semina.semi_na.databinding.FragmentHobbyMusicBinding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HobbyMusicFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HobbyMusicFragment extends Fragment {

    private FragmentHobbyMusicBinding binding;

    public HobbyMusicFragment() {
        // Required empty public constructor
    }

    public static HobbyMusicFragment newInstance() {
        HobbyMusicFragment fragment = new HobbyMusicFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHobbyMusicBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        // Inflate the layout for this fragment
        return view;
    }
}