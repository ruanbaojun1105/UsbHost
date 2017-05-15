package com.hwx.usbhost.usbhost.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.hwx.usbhost.usbhost.AppConfig;
import com.hwx.usbhost.usbhost.Application;
import com.hwx.usbhost.usbhost.R;
import com.hwx.usbhost.usbhost.util.DrawableUtil;

/**
 * Created by Administrator on 2016/10/5.
 */
public class LoginActivity extends BaseActivity implements View.OnClickListener {

    private EditText editText2;
    private Button login_backspase;
    private Button button1;
    private Button button2;
    private Button button3;
    private Button button4;
    private Button button5;
    private Button button6;
    private Button button7;
    private Button button8;
    private Button button9;
    private Button button0;
    private Button button_ok;
    private ImageView login_logo;


    String pass_arg="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        initView();
        pass_arg=getIntent().getExtras().getString("pass_arg");
        /*new Handler(this.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                try {
                    Intent intent=new Intent(Application.getContext(), MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    Application.getContext().startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        },2000);*/

    }

    private void submit() {
        // validate
        String pass = editText2.getText().toString().trim();
        if (TextUtils.isEmpty(pass)){
            Toast.makeText(this, R.string.pleaseset, Toast.LENGTH_SHORT).show();
        }else {
            if (AppConfig.getInstance().getString(pass_arg, "123456").equals(pass)) {
                Toast.makeText(this, R.string.passtrue, Toast.LENGTH_SHORT).show();
                if (pass_arg.equals(SettingMenuActivity.PASSWORD))
                    startActivity(new Intent(this, SettingMenuActivity.class));
                if (pass_arg.equals(SettingSlideActivity.Pass))
                    startActivity(new Intent(this, SettingSlideActivity.class));
                finish();
            }else {
                Toast.makeText(this, R.string.passerror, Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void login_backspase() {
        // validate
        //String editText2String = editText2.getText().toString().trim();
        int index = editText2.getSelectionStart();   //获取Edittext光标所在位置
        String str = editText2.getText().toString();
        if (!str.equals("")) {//判断输入框不为空，执行删除
            editText2.getText().delete(index - 1, index);
        }
    }

    private void initView() {
        editText2 = (EditText) findViewById(R.id.editText2);
        login_backspase = (Button) findViewById(R.id.button_del);
        button1 = (Button) findViewById(R.id.button1);
        button2 = (Button) findViewById(R.id.button2);
        button3 = (Button) findViewById(R.id.button3);
        button4 = (Button) findViewById(R.id.button4);
        button5 = (Button) findViewById(R.id.button5);
        button6 = (Button) findViewById(R.id.button6);
        button7 = (Button) findViewById(R.id.button7);
        button8 = (Button) findViewById(R.id.button8);
        button9 = (Button) findViewById(R.id.button9);
        button0 = (Button) findViewById(R.id.button0);
        button_ok = (Button) findViewById(R.id.button_ok);
        login_logo = (ImageView) findViewById(R.id.login_logo);

        editText2.clearFocus();
        editText2.setFocusable(false);
        editText2.setOnTouchListener(null);
        login_backspase.setOnClickListener(this);
        button_ok.setOnClickListener(this);

        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
        button3.setOnClickListener(this);
        button4.setOnClickListener(this);
        button5.setOnClickListener(this);
        button6.setOnClickListener(this);
        button7.setOnClickListener(this);
        button8.setOnClickListener(this);
        button9.setOnClickListener(this);
        button0.setOnClickListener(this);
        DrawableUtil.displayImage(this,login_logo,R.drawable.background_logo);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button1:
                editText2.append("1");
                break;
            case R.id.button2:
                editText2.append("2");
                break;
            case R.id.button3:
                editText2.append("3");
                break;
            case R.id.button4:
                editText2.append("4");
                break;
            case R.id.button5:
                editText2.append("5");
                break;
            case R.id.button6:
                editText2.append("6");
                break;
            case R.id.button7:
                editText2.append("7");
                break;
            case R.id.button8:
                editText2.append("8");
                break;
            case R.id.button9:
                editText2.append("9");
                break;
            case R.id.button0:
                editText2.append("0");
                break;
            case R.id.button_del:
                login_backspase();
                break;
            case R.id.button_ok:
                submit();
                break;
        }
    }
}
