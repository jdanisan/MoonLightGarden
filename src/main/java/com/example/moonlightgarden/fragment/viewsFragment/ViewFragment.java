package com.example.moonlightgarden.fragment.viewsFragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;



import com.example.moonlightgarden.*;


public class ViewFragment extends Fragment {

    private static final String ARG_VIEW_TYPE = "view_type";

    public static ViewFragment newInstance(String viewType) {
    ViewFragment fragment = new ViewFragment();
    Bundle args = new Bundle();
    args.putString(ARG_VIEW_TYPE, viewType);
    fragment.setArguments(args);
    return fragment;
}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        String viewType = getArguments() != null ? getArguments().getString(ARG_VIEW_TYPE) : "";

        int layoutId;
        switch (viewType) {
            case "calendar":
                layoutId = R.layout.calendar_moon;
                break;
            case "search":
                layoutId = R.layout.search_fragment;
                break;
            case "statistics":
                layoutId = R.layout.statistics_fragment;
                break;
            case "profile":
                layoutId = R.layout.configuser_fragment;
                break;
            default:
                layoutId = R.layout.activity_main; // Default layout
                break;
        }

        return inflater.inflate(layoutId, container, false);
    }

}