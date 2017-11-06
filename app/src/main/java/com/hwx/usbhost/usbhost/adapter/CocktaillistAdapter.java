package com.hwx.usbhost.usbhost.adapter;

import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hwx.usbhost.usbhost.AppConfig;
import com.hwx.usbhost.usbhost.R;
import com.hwx.usbhost.usbhost.activity.OpenDeviceActivity;
import com.hwx.usbhost.usbhost.activity.UpdateCocktailActivity;
import com.hwx.usbhost.usbhost.activity.UpdateTimePositionItemActivity;
import com.hwx.usbhost.usbhost.db.Cocktail;
import com.hwx.usbhost.usbhost.db.manager.CocktailManager;
import com.hwx.usbhost.usbhost.util.DialogUtil;
import com.hwx.usbhost.usbhost.util.DrawableUtil;
import com.hwx.usbhost.usbhost.util.InterFaceUtil;
import com.hwx.usbhost.usbhost.util.LogUtils;

import java.util.List;

public class CocktaillistAdapter extends BaseQuickAdapter<Cocktail,BaseViewHolder> {
    boolean isEdit;
    boolean isForResult;
    int position;
    public CocktaillistAdapter(List<Cocktail> list, boolean isedit, boolean isForResult,int position){
        super(R.layout.cocktail_list_item,list);
        this.isEdit=isedit;
        this.isForResult=isForResult;
        this.position=position;
    }


    @Override
    protected void convert(final BaseViewHolder baseViewHolder, final Cocktail cocktail) {
        baseViewHolder.setText(R.id.item_title,cocktail.getName());
        baseViewHolder.setVisible(R.id.item_lin,isEdit?true:false);
        if (!TextUtils.isEmpty(cocktail.getPreviewImage())) {
            if (TextUtils.isDigitsOnly(cocktail.getPreviewImage())) {
                DrawableUtil.displayImage(mContext, (ImageView) baseViewHolder.getView(R.id.item_img), ImageListAdapter.getImageTypeUrl(Integer.parseInt(cocktail.getPreviewImage())));
            } else {
                DrawableUtil.displayImage(mContext, (ImageView) baseViewHolder.getView(R.id.item_img), cocktail.getPreviewImage());
            }
        }
        baseViewHolder.setOnClickListener(R.id.item_img, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isEdit){
                    if (!AppConfig.getInstance().getBoolean("peifangjinzhi",false))
                        UpdateCocktailActivity.openIntance(baseViewHolder.getConvertView().getContext(), cocktail);
                    else
                        Toast.makeText(baseViewHolder.getConvertView().getContext(), R.string.dvfdsaioi+"\n"+mContext.getString(R.string.vicicdkr),Toast.LENGTH_SHORT).show();
                }else {
                    if (isForResult){
                        try {
                            Intent data = new Intent();
                            data.putExtra("cocktail", cocktail);
                            data.putExtra("position",position);
                            Activity activity= (Activity) baseViewHolder.getConvertView().getContext();
                            activity.setResult(activity.RESULT_OK, data);
                            activity.finish();
                            activity=null;
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }else
                        OpenDeviceActivity.openIntance(baseViewHolder.getConvertView().getContext(), cocktail);
                }
            }
        });
        baseViewHolder.setOnLongClickListener(R.id.item_img, new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                LogUtils.e("baseViewHolder.getAdapterPosition()：：："+baseViewHolder.getAdapterPosition());
                LogUtils.e("baseViewHolder.getPosition()：：："+baseViewHolder.getPosition());
                if (!isEdit)
                    return true;
                if (!AppConfig.getInstance().getBoolean("peifangjinzhi",false)) {
                    DialogUtil.showListDialog(baseViewHolder.getConvertView().getContext(), null, new String[]{"delete"}, new InterFaceUtil.DialogListListener() {
                        @Override
                        public void todosomething(int which) {
                            CocktailManager.getCocktailManager().deleteCocktail(getItem(baseViewHolder.getAdapterPosition() - 1));
                            remove(baseViewHolder.getAdapterPosition() - 1);
                        }
                    });
                }
                else
                    Toast.makeText(baseViewHolder.getConvertView().getContext(),R.string.dvfdsaioi+"\n"+mContext.getString(R.string.vicicdkr),Toast.LENGTH_SHORT).show();
                return true;
            }
        });
		/*baseViewHolder.setOnClickListener(R.id.item_title, view -> {
            if (isEdit){
                UpdateCocktailActivity.openIntance(baseViewHolder.getConvertView().getContext(), cocktail);
            }else {
                if (isForResult){
                    try {
                        Intent data = new Intent();
                        data.putExtra("cocktail", cocktail);
                        data.putExtra("position",position);
                        Activity activity= (Activity) baseViewHolder.getConvertView().getContext();
                        activity.setResult(activity.RESULT_OK, data);
                        activity.finish();
                        activity=null;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }else
                    OpenDeviceActivity.openIntance(baseViewHolder.getConvertView().getContext(), cocktail);
            }
        });*/
        baseViewHolder.setOnClickListener(R.id.item_delect, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!AppConfig.getInstance().getBoolean("peifangjinzhi",false)){
                    CocktailManager.getCocktailManager().deleteCocktail(getItem(baseViewHolder.getAdapterPosition()-1));
                    remove(baseViewHolder.getAdapterPosition()-1);
                }else
                    Toast.makeText(baseViewHolder.getConvertView().getContext(),R.string.dvfdsaioi+"\n"+mContext.getString(R.string.vicicdkr),Toast.LENGTH_SHORT).show();
            }
        });
        baseViewHolder.setOnClickListener(R.id.item_other, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!AppConfig.getInstance().getBoolean("peifangjinzhi",false)){
                    UpdateCocktailActivity.openIntance(baseViewHolder.getConvertView().getContext(), cocktail);
                }else
                    Toast.makeText(baseViewHolder.getConvertView().getContext(),R.string.dvfdsaioi+"\n"+mContext.getString(R.string.vicicdkr),Toast.LENGTH_SHORT).show();
            }
        });
    }


}
