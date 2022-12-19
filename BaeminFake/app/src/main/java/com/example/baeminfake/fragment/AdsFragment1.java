package com.example.baeminfake.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.baeminfake.R;

public class AdsFragment1 extends Fragment {

    public AdsFragment1() {
    }

    public AdsFragment1(int contentLayoutId) {
        super(contentLayoutId);
    }

    public static AdsFragment1 newInstance() {
        AdsFragment1 fragment = new AdsFragment1();
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
        return inflater.inflate(R.layout.ads_fragment1, container, false);
    }
}