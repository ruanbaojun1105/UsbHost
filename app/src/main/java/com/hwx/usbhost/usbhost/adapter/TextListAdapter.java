package com.hwx.usbhost.usbhost.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hwx.usbhost.usbhost.R;
import com.hwx.usbhost.usbhost.activity.OpenDeviceActivity;
import com.hwx.usbhost.usbhost.activity.UpdateCocktailActivity;
import com.hwx.usbhost.usbhost.db.Cocktail;
import com.hwx.usbhost.usbhost.db.manager.CocktailManager;
import com.hwx.usbhost.usbhost.util.DialogUtil;
import com.hwx.usbhost.usbhost.util.DrawableUtil;
import com.hwx.usbhost.usbhost.util.LogUtils;

import java.util.List;

public class TextListAdapter extends BaseQuickAdapter<String> {
	public TextListAdapter(List<String> list){
		super(R.layout.list_item,list);
	}

    @Override
    protected void convert(BaseViewHolder baseViewHolder, String s) {
        baseViewHolder.setText(R.id.tv,s);
    }


}
