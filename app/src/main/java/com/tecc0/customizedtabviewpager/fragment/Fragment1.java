package com.tecc0.customizedtabviewpager.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tecc0.customizedtabviewpager.R;

/**
 * Created by makotonishimoto on 2015/10/11.
 */
public class Fragment1 extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,
                             Bundle savedInstanceState){

        View v = inflater.inflate(R.layout.fragment1, null);

        return v;
    }
}
