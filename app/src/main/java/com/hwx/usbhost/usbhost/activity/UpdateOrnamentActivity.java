package com.hwx.usbhost.usbhost.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.hwx.usbhost.usbhost.R;
import com.hwx.usbhost.usbhost.adapter.SpacesItemDecoration;
import com.hwx.usbhost.usbhost.adapter.TextListAdapter;
import com.hwx.usbhost.usbhost.adapter.TimePositinListAdapter;
import com.hwx.usbhost.usbhost.db.Cocktail;
import com.hwx.usbhost.usbhost.db.Ornament;
import com.hwx.usbhost.usbhost.db.manager.CocktailManager;
import com.hwx.usbhost.usbhost.db.manager.OrnamentMoreDaoManager;
import com.hwx.usbhost.usbhost.util.DialogUtil;
import com.hwx.usbhost.usbhost.util.InterFaceUtil;

import java.util.List;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 * 修改 装饰品
 */
public class UpdateOrnamentActivity extends BaseActivity {
    private RecyclerView mRecyclerView;
    private TextListAdapter adapter;
    private List<String> list;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_timeposition);
        initData();
        initView();
    }
    private void initData() {
        list = OrnamentMoreDaoManager.getOrnamentMoreDaoManager().queryListNew();
    }

    private void initView() {
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_timeposition_list);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new GridLayoutManager(mRecyclerView.getContext(),3));
        adapter = new TextListAdapter(list);
        adapter.openLoadAnimation();
        View head = getLayoutInflater().inflate(R.layout.add_item, (ViewGroup) mRecyclerView.getParent(), false);
        head.findViewById(R.id.opendevice).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogUtil.showEditDialog(UpdateOrnamentActivity.this, getString(R.string.addas), null, new InterFaceUtil.OnclickInterFace() {
                    @Override
                    public void onClick(String str) {
                        OrnamentMoreDaoManager.getOrnamentMoreDaoManager().addOrnament(new Ornament(null,str));
                        adapter.add(0,str);
                    }
                }, true);
            }
        });
        adapter.addHeaderView(head);
        adapter.setEmptyView( head);
        SpacesItemDecoration decoration = new SpacesItemDecoration(10);
        mRecyclerView.addItemDecoration(decoration);
        mRecyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter1, View view, final int position) {
                DialogUtil.showListDialog(UpdateOrnamentActivity.this, null, new String[]{"edit", "delete"}, new InterFaceUtil.DialogListListener() {
                    @Override
                    public void todosomething(int which) {
                        switch (which){
                            case 0:
                                DialogUtil.showEditDialog(UpdateOrnamentActivity.this, getString(R.string.upddataeda) + adapter.getItem(position), null,
                                        new InterFaceUtil.OnclickInterFace() {
                                            @Override
                                            public void onClick(String str) {
                                                OrnamentMoreDaoManager.getOrnamentMoreDaoManager().updateOrnament(adapter.getItem(position),str);
                                                initData();
                                                adapter.setNewData(list);
                                            }
                                        }, true);
                                break;
                            case 1:
                                OrnamentMoreDaoManager.getOrnamentMoreDaoManager().delOrnament(adapter.getItem(position));
                                adapter.remove(position);
                                break;
                        }
                    }
                });
            }
        });
    }
}
