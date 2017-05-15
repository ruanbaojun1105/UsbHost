package com.hwx.usbhost.usbhost.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.hwx.usbhost.usbhost.R;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
@Deprecated
public class FirstActivity extends AppCompatActivity implements View.OnClickListener {

    private Button opendevice;
    private Button setting;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.first);
        initView();
    }

    private void initView() {
        opendevice = (Button) findViewById(R.id.opendevice);
        setting = (Button) findViewById(R.id.setting);

        opendevice.setOnClickListener(this);
        setting.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.opendevice://开始调酒
                Intent intent=new Intent(this,CocktailListActivity.class);
                intent.putExtra(SettingMenuActivity.ISEDIT,false);//编辑配方模式
                startActivity(intent);
                break;
            case R.id.setting:
                startActivity(new Intent(this,LoginActivity.class));
                break;
        }
    }
}
