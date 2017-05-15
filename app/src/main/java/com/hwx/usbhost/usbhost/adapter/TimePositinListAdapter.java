package com.hwx.usbhost.usbhost.adapter;

import android.app.Activity;
import android.support.v7.widget.CardView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hwx.usbhost.usbhost.Application;
import com.hwx.usbhost.usbhost.R;
import com.hwx.usbhost.usbhost.activity.UpdateTimePositionItemActivity;
import com.hwx.usbhost.usbhost.db.Cocktail;
import com.hwx.usbhost.usbhost.db.CocktailFormula;
import com.hwx.usbhost.usbhost.db.manager.CocktailFormulaDaoManager;

import java.util.List;

public class TimePositinListAdapter extends BaseQuickAdapter<Cocktail> {
    public TimePositinListAdapter(List<Cocktail> cocktailList) {
        super(R.layout.activity_update_timeposition_item, cocktailList);
    }


    @Override
    protected void convert(BaseViewHolder baseViewHolder, Cocktail cocktail) {
        ViewGroup.LayoutParams layoutParams = baseViewHolder.convertView.getLayoutParams();
        layoutParams.width = Application.dip2px(300);
        baseViewHolder.convertView.requestLayout();
        baseViewHolder.setText(R.id.save_item,"save");
        baseViewHolder.setText(R.id.name_tv,cocktail.getName());
        UpdateTimePositionItemActivity.initData(
                cocktail.getName(),
                (Activity) baseViewHolder.convertView.getContext(),
                baseViewHolder.getView(R.id.name_tv),
                baseViewHolder.getView(R.id.much_lin));
        baseViewHolder.setOnClickListener(R.id.clear_item, view -> {
            CocktailFormulaDaoManager.getCocktailFormulaDaoManager().delOne(cocktail.getName());
            baseViewHolder.convertView.post(() -> UpdateTimePositionItemActivity.initData(
                    cocktail.getName(),
                    (Activity) baseViewHolder.convertView.getContext(),
                    baseViewHolder.getView(R.id.name_tv),
                    baseViewHolder.getView(R.id.much_lin)));
        });
        baseViewHolder.setOnClickListener(R.id.card_view, view -> {
            UpdateTimePositionItemActivity.save_item(baseViewHolder.getView(R.id.much_lin),cocktail.getName());
            baseViewHolder.setText(R.id.save_item,mContext.getString(R.string.fdsd));
            baseViewHolder.convertView.postDelayed(() -> {
                try {
                    baseViewHolder.setText(R.id.save_item,mContext.getString(R.string.dfddd));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            },700);
        });
    }
}
