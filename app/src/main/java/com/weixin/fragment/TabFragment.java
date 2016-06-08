package com.weixin.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by yh on 2016/6/8.
 */

public class TabFragment extends Fragment {
    public static final String TITLE = "title";
    private String title;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        TextView textView = new TextView(getActivity().getApplicationContext());
        if (getArguments() != null) {
            Bundle bundle = getArguments();
            title = bundle.getString(TITLE);
            textView.setTextColor(Color.RED);
            textView.setText(title);
            textView.setGravity(Gravity.CENTER);
        }
        return textView;
    }


}
