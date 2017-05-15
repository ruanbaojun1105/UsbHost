package com.hwx.usbhost.usbhost.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hwx.usbhost.usbhost.Application;
import com.hwx.usbhost.usbhost.R;
import com.hwx.usbhost.usbhost.activity.broadcast.CommandReceiver;
import com.hwx.usbhost.usbhost.adapter.GlassListAdapter;
import com.hwx.usbhost.usbhost.adapter.ImageListAdapter;
import com.hwx.usbhost.usbhost.bluetooth.BluetoothService;
import com.hwx.usbhost.usbhost.db.AccessoriesMore;
import com.hwx.usbhost.usbhost.db.Cocktail;
import com.hwx.usbhost.usbhost.db.OrnamentMore;
import com.hwx.usbhost.usbhost.db.manager.AccessoriesMoreDaoManager;
import com.hwx.usbhost.usbhost.db.manager.CocktailFormulaDaoManager;
import com.hwx.usbhost.usbhost.db.manager.OrnamentMoreDaoManager;
import com.hwx.usbhost.usbhost.util.DrawableUtil;
import com.hwx.usbhost.usbhost.widget.CommentLinearLayout;
import com.hwx.usbhost.usbhost.widget.ViewReUseFaceListener;

import java.util.Collections;
import java.util.List;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class OpenDeviceActivity extends BaseActivity implements View.OnClickListener {

    private Cocktail cocktail;
    private TextView name_tv;
    private ImageView image_glass;
    private ImageView image_img;
    private LinearLayout accessories_lin;
    private LinearLayout ornament_lin;
    private List<AccessoriesMore> accessoriesList;
    private List<OrnamentMore> ornamentList;
    private Button open_device;
    private Button stop_device;
    private boolean isOk=true;

    public static void openIntance(Context context, Cocktail cocktail) {
        if (cocktail == null)
            return;
        Intent intent = new Intent(context, OpenDeviceActivity.class);
        intent.putExtra("cocktail", cocktail);//编辑配方模式
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opendevicea_activity);
        initView();
        if (getIntent().getExtras() != null) {
            cocktail = (Cocktail) getIntent().getExtras().getSerializable("cocktail");
        }
        if (cocktail == null)
            onBackPressed();
        initData();
        new CommandReceiver() {
            @Override
            public void onDataReceived(byte[] buffer, byte function, byte safeCod) {
                //Toast.makeText(Application.getContext(),"get data : "+function,Toast.LENGTH_SHORT).show();
                if (function==1){
                    isOk=true;
                }
            }

            @Override
            public void onFail() {
                Toast.makeText(Application.getContext(),"Device connection failed!",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLost() {
                Toast.makeText(Application.getContext(),"Device connection loss!",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onDeviceInfo(String name, String address) {

            }

            @Override
            public void onStadeTag(int stade) {

            }
        }.regiest();
    }

    private void initView() {
        name_tv = (TextView) findViewById(R.id.name_tv);
        image_glass = (ImageView) findViewById(R.id.image_glass);
        image_img = (ImageView) findViewById(R.id.image_img);
        accessories_lin = (LinearLayout) findViewById(R.id.accessories_lin);
        ornament_lin = (LinearLayout) findViewById(R.id.ornament_lin);
        open_device = (Button) findViewById(R.id.open_device);

        open_device.setOnClickListener(this);
        stop_device = (Button) findViewById(R.id.stop_device);
        stop_device.setOnClickListener(this);
    }

    private void initData() {
        accessoriesList = AccessoriesMoreDaoManager.getAccessoriesMoreDaoManager().queryList(cocktail.getName());
        ornamentList = OrnamentMoreDaoManager.getOrnamentMoreDaoManager().queryList(cocktail.getName());
        if (!TextUtils.isEmpty(cocktail.getGlass())) {
            try {
                DrawableUtil.displayImage(this, image_glass, GlassListAdapter.getTypeUrl(Integer.parseInt(cocktail.getGlass())));
                //image_glass.setImageResource(Integer.parseInt(cocktail.getGlass()));
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        name_tv.setText(cocktail.getName());
        if (!TextUtils.isEmpty(cocktail.getPreviewImage())) {
            if (TextUtils.isDigitsOnly(cocktail.getPreviewImage())) {
                DrawableUtil.displayImage(this, image_img, ImageListAdapter.getImageTypeUrl(Integer.parseInt(cocktail.getPreviewImage())));
            } else {
                DrawableUtil.displayImage(this, image_img, cocktail.getPreviewImage());
            }
        }
        initAccessories(accessoriesList);
        initOrnament(ornamentList);
    }

    private void initOrnament(List<OrnamentMore> ornamens) {
        CommentLinearLayout.setLinGreat(this, ornament_lin, ornamens.toArray(), new ViewReUseFaceListener() {
            @Override
            public int backViewRes() {
                return 0;
            }

            @Override
            public View backView(Context context) {
                View itemView = getLayoutInflater().inflate(R.layout.add_item_update, ornament_lin, false);
                return itemView;
            }

            @Override
            public void justItemToDo(Object data, View itemView, int position, Context context) {
                TextView item_del = (TextView) itemView.findViewById(R.id.item_del);
                TextView item_text = (TextView) itemView.findViewById(R.id.item_text);
                OrnamentMore aa = (OrnamentMore) data;
                item_text.setText(aa.getOrnament());
                item_del.setVisibility(View.GONE);
            }
        });
    }

    private void initAccessories(List<AccessoriesMore> accessories) {
        CommentLinearLayout.setLinGreat(this, accessories_lin, accessories.toArray(), new ViewReUseFaceListener() {
            @Override
            public int backViewRes() {
                return 0;
            }

            @Override
            public View backView(Context context) {
                View itemView = getLayoutInflater().inflate(R.layout.add_item_update, accessories_lin, false);
                return itemView;
            }

            @Override
            public void justItemToDo(Object data, View itemView, int position, Context context) {
                TextView item_del = (TextView) itemView.findViewById(R.id.item_del);
                TextView item_text = (TextView) itemView.findViewById(R.id.item_text);
                AccessoriesMore aa = (AccessoriesMore) data;
                item_text.setText(aa.getAccessories() + "*" + aa.getAccessoriesNumber());
                item_del.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.open_device:
                if (isOk) {
                    isOk=false;
                    Toast.makeText(this, "device has been started!", Toast.LENGTH_SHORT).show();
                    byte[] bytes = CocktailFormulaDaoManager.getCocktailFormulaDaoManager().getCocktailFormulaBytes(cocktail.getName());
                    BluetoothService.getInstance().sendData((byte) 0x01, bytes, true);
                }
                break;
            case R.id.stop_device:
                Toast.makeText(this, "device status has changed!", Toast.LENGTH_SHORT).show();
                BluetoothService.getInstance().sendData((byte)0x02,new byte[]{0x00},true);
                break;
        }
    }
}
