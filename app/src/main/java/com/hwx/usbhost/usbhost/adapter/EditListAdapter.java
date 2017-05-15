package com.hwx.usbhost.usbhost.adapter;

import android.app.Activity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hwx.usbhost.usbhost.R;
import com.hwx.usbhost.usbhost.util.DialogUtil;
import com.hwx.usbhost.usbhost.util.LogUtils;

import java.util.List;

public class EditListAdapter extends BaseQuickAdapter<EditListAdapter.Item> {

    public static String POSITION_DISTANCE_ARG="POSITION_DISTANCE_ARG";
	public EditListAdapter(List<Item> list){
		super(R.layout.editlist_item,list);
	}

    @Override
    protected void convert(BaseViewHolder baseViewHolder, Item s) {
        baseViewHolder.setText(R.id.text,mContext.getString(R.string.vfasdfa)+s.getIndex()+mContext.getString(R.string.vfavds));
        baseViewHolder.setText(R.id.edit,s.getDistance());
        if (s.getIndex()==1||s.getIndex()==16) {
            baseViewHolder.getView(R.id.edit).setEnabled(true);
            baseViewHolder.getView(R.id.edit).setBackgroundResource(R.drawable.btn_rect_bg_while);
            baseViewHolder.setOnClickListener(R.id.edit, view -> DialogUtil.showEditDialog((Activity) mContext, "please input:", "", str -> {
                if (TextUtils.isEmpty(str))
                    return;
                try {
                    s.setDistance(str);
                    notifyItemChanged(s.getIndex()-1);
                    baseViewHolder.getConvertView().post(() -> {
                        try {
                            for (int j = 1; j < 16; j++) {
                                int mo = 0;
                                int lit1 = Integer.parseInt(getData().get(15).getDistance().toString());
                                int lit2 = Integer.parseInt(getData().get(0).getDistance().toString());
                                int a = lit1 - lit2;
                                if (a > 0) {
                                    mo = a / 15;
                                } else break;
                                int li = Integer.parseInt(getData().get(j - 1).getDistance());
                                getData().get(j).setDistance(String.valueOf(li + mo));
                                notifyItemChanged(j);
                            }
                        } catch (NumberFormatException e) {
                            e.printStackTrace();
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }));
        }else {
            baseViewHolder.getView(R.id.edit).setEnabled(false);
            baseViewHolder.getView(R.id.edit).setBackgroundResource(0);
        }
    }


    public static class Item{
        private String distance;
        private int index;

        public Item(String distance, int index) {
            this.distance = distance;
            this.index = index;
        }

        public String getDistance() {
            return distance;
        }

        public void setDistance(String distance) {
            this.distance = distance;
        }

        public int getIndex() {
            return index;
        }

        public void setIndex(int index) {
            this.index = index;
        }
    }
}
