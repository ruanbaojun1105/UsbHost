package com.hwx.usbhost.usbhost.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.hwx.usbhost.usbhost.R;
import com.hwx.usbhost.usbhost.db.CocktailFormula;
import com.hwx.usbhost.usbhost.db.manager.CocktailFormulaDaoManager;
import com.hwx.usbhost.usbhost.util.DialogUtil;
import com.hwx.usbhost.usbhost.util.InterFaceUtil;
import com.hwx.usbhost.usbhost.util.ListSort;
import com.hwx.usbhost.usbhost.widget.CommentLinearLayout;
import com.hwx.usbhost.usbhost.widget.ViewReUseFaceListener;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by bj on 2016/10/21.
 */

@Deprecated
public class UpdateTimePositionItemActivity extends AppCompatActivity {

    private TextView name_tv;
    private LinearLayout much_lin;
    private String name;
    private TextView save_item;
    //private ScrollView scrollView;

    public static void openIntance(Context context, String name) {
        Intent intent = new Intent(context, UpdateTimePositionItemActivity.class);
        intent.putExtra("name", name);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_timeposition_item);
        System.gc();
        if (getIntent().getExtras() == null)
            onBackPressed();
        else
            name = getIntent().getExtras().getString("name");
        initView();
        initData(name,this,name_tv,much_lin);
    }

    public static void initData(String name, Activity activity,TextView name_tv,LinearLayout much_lin) {
        List<CocktailFormula> cocktailFormulaList = CocktailFormulaDaoManager.getCocktailFormulaDaoManager().queryList(name);
        Collections.sort(cocktailFormulaList, new ListSort());
        name_tv.setText(activity.getString(R.string.winename) + name);

        CommentLinearLayout.setLinGreat(activity,much_lin,new Object[16], new ViewReUseFaceListener() {
            @Override
            public int backViewRes() {
                return R.layout.activity_position_lin;
            }

            @Override
            public View backView(Context context) {
                return null;
            }

            @Override
            public void justItemToDo(Object data, View item, int position, Context context) {
                TextView position_tv = (TextView) item.findViewById(R.id.position_tv);
                EditText time_tv = (EditText) item.findViewById(R.id.time_tv);
                if (cocktailFormulaList.size() > position) {
                    position_tv.setText(cocktailFormulaList.get(position).getPosition());
                    time_tv.setText(cocktailFormulaList.get(position).getTime());
                }else {
                    position_tv.setText(String.valueOf(position+1));
                    time_tv.setText("00");
                }

                if (context instanceof UpdateTimePositionActivity){
                    position_tv.setFocusable(false);
                    time_tv.setFocusable(false);
                    /*position_tv.setOnClickListener(view -> DialogUtil.showEditDialog((Activity) context, context.getString(R.string.inpfads), "", str -> {
                        if (TextUtils.isDigitsOnly(str)){
                            position_tv.setText(str);
                        }else {
                            Toast.makeText(context, context.getString(R.string.daiuda),Toast.LENGTH_SHORT).show();
                        }
                    }));*/
                    time_tv.setOnClickListener(view -> DialogUtil.showEditDialog((Activity) context, context.getString(R.string.hdfsa)+position_tv.getText()+context.getString(R.string.daskjj), "", str -> {
                        if (TextUtils.isDigitsOnly(str)){
                            time_tv.setText(str);
                        }else {
                            Toast.makeText(context, context.getString(R.string.dfhdasjkkj),Toast.LENGTH_SHORT).show();
                        }
                    }));
                }
            }
        });
    }

    public static void save_item(LinearLayout much_lin,String name) {
        List<CocktailFormula> cocktailFormulaList = new ArrayList<>();
        for (int i = 0; i < much_lin.getChildCount(); i++) {
            View item = much_lin.getChildAt(i);
            TextView position_tv = (TextView) item.findViewById(R.id.position_tv);
            EditText time_tv = (EditText) item.findViewById(R.id.time_tv);
            //if (!(position_tv.getText().toString().equals("0")&&time_tv.getText().toString().equals("00")))
            cocktailFormulaList.add(new CocktailFormula(null, name, position_tv.getText().toString(), time_tv.getText().toString()));
        }
        if (!cocktailFormulaList.isEmpty())
            CocktailFormulaDaoManager.getCocktailFormulaDaoManager().saveCocktailFormulaList(cocktailFormulaList, name, true);
    }

    private void initView() {
        name_tv = (TextView) findViewById(R.id.name_tv);
        much_lin = (LinearLayout) findViewById(R.id.much_lin);
        save_item = (TextView) findViewById(R.id.save_item);
        save_item.setOnClickListener(view -> {
            save_item(much_lin,name);
            onBackPressed();
        });
        TextView clear_item = (TextView) findViewById(R.id.clear_item);
        clear_item.setOnClickListener(view -> {
            CocktailFormulaDaoManager.getCocktailFormulaDaoManager().delOne(name);
            runOnUiThread(() -> {
                initData(name,UpdateTimePositionItemActivity.this,name_tv,much_lin);
            });
        });
        //scrollView = (ScrollView) findViewById(R.id.scrollView);
        /*scrollView.setOnScrollChangeListener((view, i, i1, i2, i3) -> {
            if (testItem==null)
                return;
            InputMethodManager imm = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(testItem.getWindowToken(), 0);
        });*/
    }
}
