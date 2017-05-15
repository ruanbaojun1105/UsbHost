package com.hwx.usbhost.usbhost.activity.fragment;

/**
 * Created by Administrator on 2016/8/22.
 */

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.hwx.usbhost.usbhost.R;

/**
 * A placeholder fragment containing a simple view.
 */
public class CocktailListFragment extends Fragment {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_TAG = "detail_step";
    private int step=0;
    private int index;
    private int page;
    private String order;
    private ImageView detail_image;

    public CocktailListFragment() {
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static CocktailListFragment newInstance(int step,int index,int page,String order) {
        CocktailListFragment fragment = new CocktailListFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_TAG, step);
        args.putInt("index", index);
        args.putInt("page", page);
        args.putString("order", order);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_cocktaillist, container, false);
        return rootView;
    }

}
