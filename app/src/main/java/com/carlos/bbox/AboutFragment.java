package com.carlos.bbox;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.carlos.bbox.base.BaseFragment;

/**
 * Created by caochang on 2017/6/27.
 */

public class AboutFragment extends BaseFragment{

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_zhihudaily,container,false);
        return view;
    }

//    @Override
//    protected int getLayoutId() {
//        return 0;
//    }
}
