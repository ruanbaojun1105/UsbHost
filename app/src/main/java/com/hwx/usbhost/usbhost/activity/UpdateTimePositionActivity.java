package com.hwx.usbhost.usbhost.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.hwx.usbhost.usbhost.R;
import com.hwx.usbhost.usbhost.adapter.SpacesItemDecoration;
import com.hwx.usbhost.usbhost.adapter.TimePositinListAdapter;
import com.hwx.usbhost.usbhost.db.Cocktail;
import com.hwx.usbhost.usbhost.db.manager.CocktailManager;

import java.util.List;

/**
 * Created by bj on 2016/10/22.
 */

public class UpdateTimePositionActivity extends BaseActivity {

    private RecyclerView mRecyclerView;
    private TimePositinListAdapter adapter;
    private List<Cocktail> cocktailList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_timeposition);
        initData();
        initView();
    }

    private void initData() {
        cocktailList = CocktailManager.getCocktailManager().queryCocktailListAll();
    }

    private void initView() {
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_timeposition_list);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        mRecyclerView.setLayoutManager(layoutManager);
        adapter = new TimePositinListAdapter(cocktailList);
        adapter.openLoadAnimation();
        SpacesItemDecoration decoration = new SpacesItemDecoration(10);
        mRecyclerView.addItemDecoration(decoration);
        mRecyclerView.setAdapter(adapter);
    }
}
