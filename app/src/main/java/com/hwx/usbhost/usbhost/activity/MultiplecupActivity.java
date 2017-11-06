package com.hwx.usbhost.usbhost.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.hwx.usbhost.usbhost.R;
import com.hwx.usbhost.usbhost.adapter.MultipleCupListAdapter;
import com.hwx.usbhost.usbhost.adapter.SpacesItemDecoration;
import com.hwx.usbhost.usbhost.bluetooth.BluetoothService;
import com.hwx.usbhost.usbhost.db.Cocktail;
import com.hwx.usbhost.usbhost.db.manager.CocktailFormulaDaoManager;
import com.hwx.usbhost.usbhost.util.LogUtils;
import java.util.ArrayList;
import java.util.List;

/**
 * 多杯点单
 */
public class MultiplecupActivity extends BaseActivity implements View.OnClickListener {

    public static int FORRESULT_AA = 110;
    public static final String ISFORRESULT = "ISFORRESULT";
    private RecyclerView mRecyclerView;
    private Button next_btn;
    private Button back;
    private MultipleCupListAdapter adapter;
    private List<MultipleCupListAdapter.CupItem> list =new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.multiple_cup);
        initView();
    }

    private void initView() {
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_list);
        next_btn = (Button) findViewById(R.id.next_btn);
        back = (Button) findViewById(R.id.back);

        next_btn.setOnClickListener(this);
        back.setOnClickListener(this);

        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new GridLayoutManager(mRecyclerView.getContext(),3));
        for (int i = 0; i < 9; i++) {
            list.add(new MultipleCupListAdapter.CupItem(i,null,false));
        }
        adapter = new MultipleCupListAdapter(list);
        adapter.openLoadAnimation();
        SpacesItemDecoration decoration = new SpacesItemDecoration(10);
        mRecyclerView.addItemDecoration(decoration);
        mRecyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(MultiplecupActivity.this, CocktailListActivity.class);
                intent.putExtra(ISFORRESULT, true);
                intent.putExtra("position", position);
                LogUtils.e("发送了位置为："+position);
                startActivityForResult(intent, FORRESULT_AA);
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == FORRESULT_AA && resultCode == RESULT_OK) {
            Cocktail cocktail = (Cocktail) data.getExtras().getSerializable("cocktail");
            int position=data.getIntExtra("position",0);
            list.set(position,list.get(position).setHasCheck(false).setCocktail(cocktail));
            adapter.notifyItemChanged(position);
        }
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.next_btn:
                for (MultipleCupListAdapter.CupItem item:list) {
                    if (item.isHasCheck())
                        continue;
                    else {
                        if (item.getCocktail()!=null) {
                            byte[] bytes= CocktailFormulaDaoManager.getCocktailFormulaDaoManager().getCocktailFormulaBytes(item.getCocktail().getName());
                            BluetoothService.getInstance().sendData((byte)0x01,bytes,true);
                            item.setHasCheck(true);
                            adapter.notifyDataSetChanged();
                            break;
                        }else {
                            continue;
                        }
                    }
                }
                break;
            case R.id.back:
                onBackPressed();
                break;
        }
    }
}
