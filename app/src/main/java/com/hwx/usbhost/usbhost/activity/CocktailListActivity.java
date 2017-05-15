package com.hwx.usbhost.usbhost.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.hwx.usbhost.usbhost.AppConfig;
import com.hwx.usbhost.usbhost.R;
import com.hwx.usbhost.usbhost.adapter.CocktaillistAdapter;
import com.hwx.usbhost.usbhost.adapter.SpacesItemDecoration;
import com.hwx.usbhost.usbhost.db.manager.CocktailManager;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class CocktailListActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener, View.OnClickListener {

    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mSwipeRefreshWidget;
    private CocktaillistAdapter adapter;
    private boolean isEdit = false;
    private Button test_btn;
    private Button more_btn;
    private LinearLayout bottom_lin;
    private boolean isForResult;
    private int position=0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cocktaillist);
        if (getIntent().getExtras() != null) {
            isEdit = getIntent().getExtras().getBoolean(SettingMenuActivity.ISEDIT);
            isForResult= getIntent().getExtras().getBoolean(MultiplecupActivity.ISFORRESULT);
            if (isForResult)
                position=getIntent().getIntExtra("position",0);
        }
        initView();
    }
    private void initView() {
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_cocktail_list);
        mSwipeRefreshWidget = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_widget);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setHasFixedSize(true);
        //StaggeredGridLayoutManager mLayoutManager = new StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL);
        GridLayoutManager mLayoutManager = new GridLayoutManager(this, 5);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mSwipeRefreshWidget.setOnRefreshListener(this);
        adapter = new CocktaillistAdapter(CocktailManager.getCocktailManager().queryCocktailListAll(), isEdit,isForResult,position);
        adapter.openLoadAnimation();
        if (isEdit) {
            if (!AppConfig.getInstance().getBoolean("peifangjinzhi",false)) {
                View head = getLayoutInflater().inflate(R.layout.add_item, (ViewGroup) mRecyclerView.getParent(), false);
                head.findViewById(R.id.opendevice).setOnClickListener(view -> {
                    UpdateCocktailActivity.openIntance(this, null);
                });
                adapter.addHeaderView(head);
                adapter.setEmptyView(false, head);
            }
        }
        //设置item之间的间隔
        SpacesItemDecoration decoration = new SpacesItemDecoration(10);
        mRecyclerView.addItemDecoration(decoration);
        mRecyclerView.setAdapter(adapter);
        test_btn = (Button) findViewById(R.id.test_btn);
        test_btn.setOnClickListener(this);
        more_btn = (Button) findViewById(R.id.more_btn);
        more_btn.setOnClickListener(this);
        bottom_lin = (LinearLayout) findViewById(R.id.bottom_lin);
        bottom_lin.setVisibility(isEdit?View.GONE:(isForResult?View.GONE:View.VISIBLE));
    }

    @Override
    protected void onResume() {
        super.onResume();
        onRefresh();
    }

    @Override
    public synchronized void onRefresh() {
        if (adapter != null) {
            adapter.setNewData(CocktailManager.getCocktailManager().queryCocktailListAll());
            mSwipeRefreshWidget.setRefreshing(false);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.test_btn:
                startActivity(new Intent(this, TestPageActivity.class));
                break;
            case R.id.more_btn:
                startActivity(new Intent(this, MultiplecupActivity.class));
                break;
            case R.id.back:
                onBackPressed();
                break;
        }
    }
}
