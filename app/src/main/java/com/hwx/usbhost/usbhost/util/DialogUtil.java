package com.hwx.usbhost.usbhost.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.InputType;
import android.text.TextUtils;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.hwx.usbhost.usbhost.AppConfig;
import com.hwx.usbhost.usbhost.Application;
import com.hwx.usbhost.usbhost.R;
import com.hwx.usbhost.usbhost.adapter.GlassListAdapter;
import com.hwx.usbhost.usbhost.adapter.SpacesItemDecoration;
import com.hwx.usbhost.usbhost.adapter.TimePositinListAdapter;
import com.hwx.usbhost.usbhost.db.Glass;
import com.hwx.usbhost.usbhost.db.manager.GlassDaoManager;

/**
 * Created by Administrator on 2016/10/17.
 */

public class DialogUtil {
    public static void showEditDialog(final Activity activity, String title, final String arg, final InterFaceUtil.OnclickInterFace onclickInterFace){
        final EditText et = new EditText(activity);
        et.setInputType(InputType.TYPE_CLASS_NUMBER);
        new AlertDialog.Builder(activity).setTitle(title)
                //.setIcon(android.R.drawable.ic_menu_send)
                .setView(et)
                .setPositiveButton(R.string.dfdfd, (dialog, which) -> {
                    if (activity==null)
                        return;
                    InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(et.getWindowToken(), 0);
                    final String input = et.getText().toString();
                    if (TextUtils.isEmpty(input))
                        return;
                    if (!TextUtils.isEmpty(arg))
                        AppConfig.getInstance().putString(arg,input);
                    if (onclickInterFace!=null){
                        activity.runOnUiThread(() -> onclickInterFace.onClick(input));
                    }

                })
                .setNegativeButton(R.string.vdadsr, (dialogInterface, i) -> {
                    InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(et.getWindowToken(), 0);
                })
                .show();
    }
    public static void showEditDialog(final Activity activity, String title, final String arg, final InterFaceUtil.OnclickInterFace onclickInterFace,boolean isContentText){
        final EditText et = new EditText(activity);
        if (!isContentText)
            et.setInputType(InputType.TYPE_CLASS_NUMBER);
        new AlertDialog.Builder(activity).setTitle(title)
                .setIcon(android.R.drawable.ic_menu_send)
                .setView(et)
                .setPositiveButton(R.string.dfdfd, (dialog, which) -> {
                    if (activity==null)
                        return;
                    InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(et.getWindowToken(), 0);
                    final String input = et.getText().toString();
                    if (TextUtils.isEmpty(input))
                        return;
                    if (!TextUtils.isEmpty(arg))
                        AppConfig.getInstance().putString(arg,input);
                    if (onclickInterFace!=null){
                        activity.runOnUiThread(() -> onclickInterFace.onClick(input));
                    }

                })
                .setNegativeButton(R.string.vdadsr, (dialogInterface, i) -> {
                    InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(et.getWindowToken(), 0);
                })
                .show();
    }

    /**
     * 普通列表提示框
     * @param context
     * @param title
     * @param items
     * @param listListener
     */
    public static void showListDialog(Context context, String title, String[] items, final InterFaceUtil.DialogListListener listListener){
        if (context==null)
            return;
        if (items==null||items.length==0)
            return;
        AlertDialog.Builder builder = new AlertDialog.Builder(context,AlertDialog.THEME_HOLO_LIGHT);
        builder.setCancelable(true);
        if (!TextUtils.isEmpty(title))
            builder.setTitle(title);
        builder.setItems(items, (dialog, which) -> {
            if (listListener!=null)
                listListener.todosomething(which);
            dialog.dismiss();
        });
        Dialog dialog=builder.create();
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();

    }

    public static void showGlassEditDialog(final Activity activity,BaseQuickAdapter adapter, final InterFaceUtil.DialogGlassListener onclickInterFace){
        if (activity==null)
            return;
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_glass);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(activity.getResources().getColor(R.color.transparent_alpha)));
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        Display d = activity.getWindowManager().getDefaultDisplay(); // 为获取屏幕宽、高
        WindowManager.LayoutParams p = dialog.getWindow().getAttributes(); // 获取对话框当前的参数值
        p.width =(int)( d.getWidth()*0.7);
        p.height =(int)( d.getHeight()*0.8);
        dialog.getWindow().setAttributes(p);

        RecyclerView mRecyclerView= (RecyclerView) dialog.findViewById(R.id.recycleview_glass);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setHasFixedSize(true);
        //StaggeredGridLayoutManager mLayoutManager = new StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL);
        GridLayoutManager mLayoutManager=new GridLayoutManager(activity,4);
        mRecyclerView.setLayoutManager(mLayoutManager);

        adapter.openLoadAnimation();
        SpacesItemDecoration decoration = new SpacesItemDecoration(10);
        mRecyclerView.addItemDecoration(decoration);
        mRecyclerView.setAdapter(adapter);
        adapter.setOnRecyclerViewItemClickListener((view, i) -> {
            Object item=adapter.getItem(i);
            if (onclickInterFace!=null){
                if (activity!=null)
                    activity.runOnUiThread(() -> onclickInterFace.todosomething(item));
            }
            dialog.dismiss();
        });
        dialog.show();
    }
}
