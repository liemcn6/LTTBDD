package com.example.baeminfake.controller;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.baeminfake.fragment.AdsFragment1;
import com.example.baeminfake.fragment.AdsFragment2;
import com.example.baeminfake.fragment.AdsFragment3;

public class FragmentBottomNavigation extends FragmentPagerAdapter {

    private int numberPage = 3;


    public FragmentBottomNavigation(@NonNull FragmentManager fragmentManager, int behavior) {
        super(fragmentManager, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new AdsFragment1();
            case 1:
                return new AdsFragment2();
            case 2:
                return new AdsFragment3();

            default:
                return new AdsFragment1();
        }
    }

    @Override
    public int getCount() {
        return numberPage;
    }
}