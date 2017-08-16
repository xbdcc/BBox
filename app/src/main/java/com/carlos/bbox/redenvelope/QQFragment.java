package com.carlos.bbox.redenvelope;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.carlos.bbox.R;

import me.yokeyword.fragmentation.SupportFragment;

/**
 * Created by caochang on 2017/8/11.
 */

public class QQFragment extends SupportFragment{

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_qq, container, false);

        return view;
    }
}
