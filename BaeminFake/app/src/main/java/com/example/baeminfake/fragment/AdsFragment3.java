package com.example.baeminfake.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.baeminfake.R;

public class AdsFragment3 extends Fragment {

    public AdsFragment3() {
    }

    public AdsFragment3(int contentLayoutId) {
        super(contentLayoutId);
    }

    public static AdsFragment3 newInstance() {
        AdsFragment3 fragment = new AdsFragment3();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.ads_fragment3, container, false);
    }
}