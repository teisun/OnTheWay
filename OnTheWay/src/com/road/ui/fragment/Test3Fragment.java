package com.road.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zhou.ontheway.R;

/**
 * Created by zhou on 2016/6/1.
 */
public class Test3Fragment extends Fragment {

    public static Test3Fragment newInstance() {
        Test3Fragment fragment = new Test3Fragment();
        Bundle bundle = new Bundle();
        // bundle.putString();
        fragment.setArguments(bundle);
        return  fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // 得到数据
        // Bundle args = getArguments();
        View view = inflater.inflate(R.layout.activity_address_book, container, false);
        return view;
    }

}
