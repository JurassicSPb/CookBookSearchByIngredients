package com.github.jurassicspb.cookbooksearchbyingredients.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.jurassicspb.cookbooksearchbyingredients.R;

/**
 * Created by Мария on 12.11.2016.
 */

public class FishFragment extends Fragment{
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.tablayout_with_viewpager, container, false);

        //v.findViewById()...

        return v;
    }
}
