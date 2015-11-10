package com.example.viewpageend;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

// Instances of this class are fragments representing a single
// object in our collection.
public class DemoObjectFragment extends Fragment {
    public static final String ARG_OBJECT = "object";

    @Override
    public View onCreateView(LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {
        // The last two arguments ensure LayoutParams are inflated
        // properly.
        View rootView = inflater.inflate(
                R.layout.fragment_collection_object, container, false);
        //注意这里是从MainActivity里面获取的Bundle，而不是参数savedInstanceState！！！！
        Bundle args = getArguments();
        TextView tv_fragment = (TextView) rootView.findViewById(R.id.tv_fragment);
        tv_fragment.setText(args.getInt(ARG_OBJECT) + "fed");
        return rootView;
    }
}