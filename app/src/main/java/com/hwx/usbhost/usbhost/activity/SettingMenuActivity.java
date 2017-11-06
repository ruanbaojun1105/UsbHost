package com.hwx.usbhost.usbhost.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.hwx.usbhost.usbhost.AppConfig;
import com.hwx.usbhost.usbhost.R;
import com.hwx.usbhost.usbhost.util.DialogUtil;
import com.hwx.usbhost.usbhost.util.InterFaceUtil;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class SettingMenuActivity extends BaseActivity implements View.OnClickListener {

//    private Button reset_count;
//    private EditText editText_chuiqi;
//    private EditText editText_jiange;
//    private EditText editText_chongshui;
//    private EditText editText_tingdun;
//    private TextView count_number;
    public static final String PASSWORD = "password";
    public static final String ISEDIT = "isEdit";
    private Button ornament_set;
    private Button accessories_set;
    private Button timeposition_set;
    private Button peifang_set;
    private Button password_set;
    private Button slide_set;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settingmenu);
        initView();
    }

    private void initView() {

        peifang_set = (Button) findViewById(R.id.peifang_set);
        password_set = (Button) findViewById(R.id.password_set);

        peifang_set.setOnClickListener(this);
        password_set.setOnClickListener(this);
        ornament_set = (Button) findViewById(R.id.ornament_set);
        ornament_set.setOnClickListener(this);
        accessories_set = (Button) findViewById(R.id.accessories_set);
        accessories_set.setOnClickListener(this);
        timeposition_set = (Button) findViewById(R.id.timeposition_set);
        timeposition_set.setOnClickListener(this);
        slide_set = (Button) findViewById(R.id.slide_set);
        slide_set.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.peifang_set:
                Intent intent = new Intent(this, CocktailListActivity.class);
                intent.putExtra(ISEDIT, true);//编辑配方模式
                startActivity(intent);
                break;
            case R.id.password_set:
                DialogUtil.showEditDialog(SettingMenuActivity.this, getString(R.string.shurumima), PASSWORD, new InterFaceUtil.OnclickInterFace() {
                    @Override
                    public void onClick(String str) {
                        Toast.makeText(SettingMenuActivity.this, R.string.cdasdv, Toast.LENGTH_SHORT).show();
                    }
                });
                break;
            case R.id.ornament_set:
                startActivity(new Intent(this, UpdateOrnamentActivity.class));
                break;
            case R.id.accessories_set:
                startActivity(new Intent(this, UpdateAccessoriesActivity.class));
                break;
            case R.id.timeposition_set:
                startActivity(new Intent(this, UpdateTimePositionActivity.class));
                break;
            case R.id.slide_set:
                Intent intent1 = new Intent(this, LoginActivity.class);
                intent1.putExtra("pass_arg",SettingSlideActivity.Pass);
                startActivity(intent1);
                break;
        }
    }

    @Override
    protected void onDestroy() {
        submit();
        super.onDestroy();
    }


    private void submit() {
        //保存数据
//        String chuiqi = editText_chuiqi.getText().toString().trim();
//        if (!TextUtils.isEmpty(chuiqi)) {
//            AppConfig.getInstance().putString("chuiqi_time", chuiqi);
//        }
//
//        String jiange = editText_jiange.getText().toString().trim();
//        if (!TextUtils.isEmpty(jiange)) {
//            AppConfig.getInstance().putString("jiange_time", chuiqi);
//        }
//
//        String chongshui = editText_chongshui.getText().toString().trim();
//        if (!TextUtils.isEmpty(chongshui)) {
//            AppConfig.getInstance().putString("chongshui_time", chuiqi);
//        }
//
//        String tingdun = editText_tingdun.getText().toString().trim();
//        if (!TextUtils.isEmpty(tingdun)) {
//            AppConfig.getInstance().putString("tingdun_time", chuiqi);
//        }
    }
}
