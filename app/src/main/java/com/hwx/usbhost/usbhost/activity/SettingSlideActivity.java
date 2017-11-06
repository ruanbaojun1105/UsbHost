package com.hwx.usbhost.usbhost.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.hwx.usbhost.usbhost.AppConfig;
import com.hwx.usbhost.usbhost.R;
import com.hwx.usbhost.usbhost.adapter.EditListAdapter;
import com.hwx.usbhost.usbhost.adapter.SpacesItemDecoration;
import com.hwx.usbhost.usbhost.adapter.TextListAdapter;
import com.hwx.usbhost.usbhost.bluetooth.BluetoothService;
import com.hwx.usbhost.usbhost.db.CocktailFormula;
import com.hwx.usbhost.usbhost.db.manager.CocktailFormulaDaoManager;
import com.hwx.usbhost.usbhost.util.DialogUtil;
import com.hwx.usbhost.usbhost.util.InterFaceUtil;
import com.hwx.usbhost.usbhost.util.ListSort;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class SettingSlideActivity extends BaseActivity implements View.OnClickListener {


    private TextView count_number;
    private Button reset_count;
    private EditText editText_yaodongjuli;
    private EditText editText_yaodongcishu;
    private EditText editText_yunxingsudu;
    private EditText editText_yaodongsudu;
    private EditText editText_jiasushijian;
    private EditText editText_tingdunshijian;
    private RecyclerView mRecyclerView;
    private Button peifangjinzhi;
    private Button password_up;
    private Button save;
    private Button back;

    private EditListAdapter adapter;

    public static String Pass="senior_pass";
    public static  String CUP_NUMBER="reset_count";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settingslide);
        initView();
        initData();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void initData() {
        count_number.setText("Total cup number:\t" + AppConfig.getInstance().getInt("reset_count", 0));//总杯数
        editText_yaodongjuli.setText(AppConfig.getInstance().getString("yaodongjuli", "0"));
        editText_yaodongcishu.setText(AppConfig.getInstance().getString("yaodongcishu", "0"));
        editText_yunxingsudu.setText(AppConfig.getInstance().getString("yunxingsudu", "0"));
        editText_yaodongsudu.setText(AppConfig.getInstance().getString("yaodongsudu", "0"));
        editText_jiasushijian.setText(AppConfig.getInstance().getString("jiasushijian", "0"));
        editText_tingdunshijian.setText(AppConfig.getInstance().getString("tingdunshijian", "0"));
        peifangjinzhi.setText(getString(R.string.vicicdkr)+(AppConfig.getInstance().getBoolean("peifangjinzhi",false)?"ON":"OFF") );
        editText_yaodongjuli.clearFocus();
        editText_yaodongcishu.clearFocus();
        editText_yunxingsudu.clearFocus();
        editText_yaodongsudu.clearFocus();
        editText_jiasushijian.clearFocus();
        editText_tingdunshijian.clearFocus();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.reset_count:
                AppConfig.getInstance().putInt("CUP_NUMBER", 0);
                count_number.setText("Total cup number:\t" + AppConfig.getInstance().getInt("reset_count", 0) );
                break;
            case R.id.peifangjinzhi:
                if (AppConfig.getInstance().getBoolean("peifangjinzhi",false)){
                    AppConfig.getInstance().putBoolean("peifangjinzhi",false);
                }else {
                    AppConfig.getInstance().putBoolean("peifangjinzhi",true);
                }
                peifangjinzhi.setText(getString(R.string.vicicdkr)+(AppConfig.getInstance().getBoolean("peifangjinzhi",false)?"ON":"OFF") );
                break;
            case R.id.password_up:
                DialogUtil.showEditDialog(SettingSlideActivity.this, getString(R.string.shurumima), Pass, new InterFaceUtil.OnclickInterFace() {
                    @Override
                    public void onClick(String str) {
                        Toast.makeText(SettingSlideActivity.this, R.string.kcvjcadjf, Toast.LENGTH_SHORT).show();
                    }
                });
                break;
            case R.id.save:
                submit();
                break;
            case R.id.back:
                onBackPressed();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }



    private void initView() {
        count_number = (TextView) findViewById(R.id.count_number);
        reset_count = (Button) findViewById(R.id.reset_count);
        editText_yaodongjuli = (EditText) findViewById(R.id.editText_yaodongjuli);
        editText_yaodongcishu = (EditText) findViewById(R.id.editText_yaodongcishu);
        editText_yunxingsudu = (EditText) findViewById(R.id.editText_yunxingsudu);
        editText_yaodongsudu = (EditText) findViewById(R.id.editText_yaodongsudu);
        editText_jiasushijian = (EditText) findViewById(R.id.editText_jiasushijian);
        editText_tingdunshijian = (EditText) findViewById(R.id.editText_tingdunshijian);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_slide_list);
        peifangjinzhi = (Button) findViewById(R.id.peifangjinzhi);
        password_up = (Button) findViewById(R.id.password_up);
        save = (Button) findViewById(R.id.save);
        back = (Button) findViewById(R.id.back);

        reset_count.setOnClickListener(this);
        peifangjinzhi.setOnClickListener(this);
        password_up.setOnClickListener(this);
        save.setOnClickListener(this);
        back.setOnClickListener(this);

        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new GridLayoutManager(mRecyclerView.getContext(),4));
        List<EditListAdapter.Item> list=new ArrayList<>();
        for (int i = 1; i < 17; i++) {
            list.add(new  EditListAdapter.Item(AppConfig.getInstance().getString(EditListAdapter.POSITION_DISTANCE_ARG+i,"0"),i));
        }
        adapter = new EditListAdapter(list);
        adapter.openLoadAnimation();
        SpacesItemDecoration decoration = new SpacesItemDecoration(10);
        mRecyclerView.addItemDecoration(decoration);
        mRecyclerView.setAdapter(adapter);
    }

    private void submit() {
        for (EditListAdapter.Item item:adapter.getData()){
            AppConfig.getInstance().putString(EditListAdapter.POSITION_DISTANCE_ARG+item.getIndex(),item.getDistance());
        }
        // validate
        String yaodongjuli = editText_yaodongjuli.getText().toString().trim();
        if (!TextUtils.isEmpty(yaodongjuli)) {
            AppConfig.getInstance().putString("yaodongjuli", yaodongjuli);
        }

        String yaodongcishu = editText_yaodongcishu.getText().toString().trim();
        if (!TextUtils.isEmpty(yaodongcishu)) {
            AppConfig.getInstance().putString("yaodongcishu", yaodongcishu);
        }

        String yunxingsudu = editText_yunxingsudu.getText().toString().trim();
        if (!TextUtils.isEmpty(yunxingsudu)) {
            AppConfig.getInstance().putString("yunxingsudu", yunxingsudu);
        }

        String yaodongsudu = editText_yaodongsudu.getText().toString().trim();
        if (!TextUtils.isEmpty(yaodongsudu)) {
            AppConfig.getInstance().putString("yaodongsudu", yaodongsudu);
        }

        String jiasushijian = editText_jiasushijian.getText().toString().trim();
        if (!TextUtils.isEmpty(jiasushijian)) {
            AppConfig.getInstance().putString("jiasushijian", jiasushijian);
        }

        String tingdunshijian = editText_tingdunshijian.getText().toString().trim();
        if (!TextUtils.isEmpty(tingdunshijian)) {
            AppConfig.getInstance().putString("tingdunshijian", tingdunshijian);
        }

        BluetoothService.sendData((byte) 0x03,saveSendByte(),true);
        // TODO validate success, do something
        onBackPressed();

    }


    public byte[] saveSendByte(){
        List<String> listda=new ArrayList<>();
        listda.add(AppConfig.getInstance().getString("yaodongjuli", "0"));
        listda.add(AppConfig.getInstance().getString("yaodongcishu", "0"));
        listda.add(AppConfig.getInstance().getString("yunxingsudu", "0"));
        listda.add(AppConfig.getInstance().getString("yaodongsudu", "0"));
        listda.add(AppConfig.getInstance().getString("jiasushijian", "0"));
        listda.add(AppConfig.getInstance().getString("tingdunshijian", "0"));
        listda.add(AppConfig.getInstance().getString(EditListAdapter.POSITION_DISTANCE_ARG+1,"0"));
        listda.add(AppConfig.getInstance().getString(EditListAdapter.POSITION_DISTANCE_ARG+16,"0"));
        byte[] bytes=new byte[16];
        List<String> list=new ArrayList<>();
        for (int i = 0; i < listda.size(); i++) {
            int d= 0;
            try {
                d = Integer.parseInt(listda.get(i));
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
            list.add(String.valueOf(d/256));
            list.add(String.valueOf(d%256));
        }
        for (int i = 0; i < list.size(); i++) {
            try {
                bytes[i]=(byte)Integer.parseInt(list.get(i));
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        return bytes;
    }
}
