package com.hwx.usbhost.usbhost.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.hwx.usbhost.usbhost.R;
import com.hwx.usbhost.usbhost.bluetooth.BluetoothService;
import com.hwx.usbhost.usbhost.util.LogUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 一键测试
 */
public class TestPageActivity extends BaseActivity implements View.OnClickListener {


    private Button totest;
    private Button pause;
    private Button test1;
    private Button test2;
    private Button test3;
    private Button test4;
    private Button test5;
    private Button test6;
    private Button test7;
    private Button test8;
    private Button test9;
    private Button test10;
    private Button test11;
    private Button test12;
    private Button test13;
    private Button test14;
    private Button test15;
    private Button test16;
    private List<Button> buttons=new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_page);
        initView();
    }

    private void initView() {
        totest = (Button) findViewById(R.id.totest);
        pause = (Button) findViewById(R.id.pause);

        totest.setOnClickListener(this);
        pause.setOnClickListener(this);
        test1 = (Button) findViewById(R.id.test1);
        test1.setOnClickListener(this);
        test2 = (Button) findViewById(R.id.test2);
        test2.setOnClickListener(this);
        test3 = (Button) findViewById(R.id.test3);
        test3.setOnClickListener(this);
        test4 = (Button) findViewById(R.id.test4);
        test4.setOnClickListener(this);
        test5 = (Button) findViewById(R.id.test5);
        test5.setOnClickListener(this);
        test6 = (Button) findViewById(R.id.test6);
        test6.setOnClickListener(this);
        test7 = (Button) findViewById(R.id.test7);
        test7.setOnClickListener(this);
        test8 = (Button) findViewById(R.id.test8);
        test8.setOnClickListener(this);
        test9 = (Button) findViewById(R.id.test9);
        test9.setOnClickListener(this);
        test10 = (Button) findViewById(R.id.test10);
        test10.setOnClickListener(this);
        test11 = (Button) findViewById(R.id.test11);
        test11.setOnClickListener(this);
        test12 = (Button) findViewById(R.id.test12);
        test12.setOnClickListener(this);
        test13 = (Button) findViewById(R.id.test13);
        test13.setOnClickListener(this);
        test14 = (Button) findViewById(R.id.test14);
        test14.setOnClickListener(this);
        test15 = (Button) findViewById(R.id.test15);
        test15.setOnClickListener(this);
        test16 = (Button) findViewById(R.id.test16);
        test16.setOnClickListener(this);
        buttons.add(test1);
        buttons.add(test2);
        buttons.add(test3);
        buttons.add(test4);
        buttons.add(test5);
        buttons.add(test6);
        buttons.add(test7);
        buttons.add(test8);
        buttons.add(test9);
        buttons.add(test10);
        buttons.add(test11);
        buttons.add(test12);
        buttons.add(test13);
        buttons.add(test14);
        buttons.add(test15);
        buttons.add(test16);

    }

    @Override
    protected void onPause() {
        super.onPause();
        hasPause=true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        hasPause=false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        hasPause=true;
    }

    boolean hasPause;
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.totest:
                Toast.makeText(this, R.string.vavcxzxd, Toast.LENGTH_SHORT).show();
                BluetoothService.getInstance().sendData((byte)0x05,new byte[]{0x00},true);
                /*try {
                    for (Button button : buttons) {
                        try {
                            button.postDelayed(() -> {
                                try {
                                    if (!isFinishing() || !hasPause) {
                                        button.performClick();
                                        LogUtils.e("test:" + button.getId());
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }}, 500);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }*/
                break;
            case R.id.pause:
                BluetoothService.getInstance().sendData((byte)0x02,new byte[]{0x00},true);
                if (hasPause)hasPause=false;
                else hasPause=true;
                Toast.makeText(this, R.string.vccadrasear, Toast.LENGTH_SHORT).show();
                break;
            case R.id.back:
                onBackPressed();
                break;
            case R.id.test1:
                Toast.makeText(this, "test1", Toast.LENGTH_SHORT).show();
                BluetoothService.getInstance().sendData((byte)0x04,new byte[]{0x01},true);
                break;
            case R.id.test2:
                Toast.makeText(this, "test2", Toast.LENGTH_SHORT).show();
                BluetoothService.getInstance().sendData((byte)0x04,new byte[]{0x02},true);
                break;
            case R.id.test3:
                Toast.makeText(this, "test3", Toast.LENGTH_SHORT).show();
                BluetoothService.getInstance().sendData((byte)0x04,new byte[]{0x03},true);
                break;
            case R.id.test4:
                Toast.makeText(this, "test4", Toast.LENGTH_SHORT).show();
                BluetoothService.getInstance().sendData((byte)0x04,new byte[]{0x04},true);
                break;
            case R.id.test5:
                Toast.makeText(this, "test5", Toast.LENGTH_SHORT).show();
                BluetoothService.getInstance().sendData((byte)0x04,new byte[]{0x05},true);
                break;
            case R.id.test6:
                Toast.makeText(this, "test6", Toast.LENGTH_SHORT).show();
                BluetoothService.getInstance().sendData((byte)0x04,new byte[]{0x06},true);
                break;
            case R.id.test7:
                Toast.makeText(this, "test7", Toast.LENGTH_SHORT).show();
                BluetoothService.getInstance().sendData((byte)0x04,new byte[]{0x07},true);
                break;
            case R.id.test8:
                Toast.makeText(this, "test8", Toast.LENGTH_SHORT).show();
                BluetoothService.getInstance().sendData((byte)0x04,new byte[]{0x08},true);
                break;
            case R.id.test9:
                Toast.makeText(this, "test9", Toast.LENGTH_SHORT).show();
                BluetoothService.getInstance().sendData((byte)0x04,new byte[]{0x09},true);
                break;
            case R.id.test10:
                Toast.makeText(this, "test10", Toast.LENGTH_SHORT).show();
                BluetoothService.getInstance().sendData((byte)0x04,new byte[]{0x010},true);
                break;
            case R.id.test11:
                Toast.makeText(this, "test11", Toast.LENGTH_SHORT).show();
                BluetoothService.getInstance().sendData((byte)0x04,new byte[]{0x011},true);
                break;
            case R.id.test12:
                Toast.makeText(this, "test12", Toast.LENGTH_SHORT).show();
                BluetoothService.getInstance().sendData((byte)0x04,new byte[]{0x012},true);
                break;
            case R.id.test13:
                Toast.makeText(this, "test13", Toast.LENGTH_SHORT).show();
                BluetoothService.getInstance().sendData((byte)0x04,new byte[]{0x013},true);
                break;
            case R.id.test14:
                Toast.makeText(this, "test14", Toast.LENGTH_SHORT).show();
                BluetoothService.getInstance().sendData((byte)0x04,new byte[]{0x014},true);
                break;
            case R.id.test15:
                Toast.makeText(this, "test15", Toast.LENGTH_SHORT).show();
                BluetoothService.getInstance().sendData((byte)0x04,new byte[]{0x015},true);
                break;
            case R.id.test16:
                Toast.makeText(this, "test16", Toast.LENGTH_SHORT).show();
                BluetoothService.getInstance().sendData((byte)0x04,new byte[]{0x016},true);
                break;

        }
    }
}
