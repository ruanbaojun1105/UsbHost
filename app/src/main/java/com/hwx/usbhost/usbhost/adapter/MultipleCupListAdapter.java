package com.hwx.usbhost.usbhost.adapter;

import android.graphics.Color;
import android.widget.CheckBox;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hwx.usbhost.usbhost.R;
import com.hwx.usbhost.usbhost.db.Cocktail;

import java.io.Serializable;
import java.util.List;

public class MultipleCupListAdapter extends BaseQuickAdapter<MultipleCupListAdapter.CupItem> {
	public MultipleCupListAdapter(List<CupItem> list){
		super(R.layout.cup_list_item,list);
	}

    @Override
    protected void convert(BaseViewHolder baseViewHolder, CupItem s) {
        baseViewHolder.setText(R.id.text,s.getCocktail()==null?mContext.getString(R.string.dsattttvv):s.getCocktail().getName());
        baseViewHolder.setChecked(R.id.checkBox,s.isHasCheck());
        baseViewHolder.setTextColor(R.id.text,s.getCocktail()==null? Color.WHITE:Color.BLUE);
    }

    public static class CupItem implements Serializable{
        int position;
        Cocktail cocktail;
        boolean hasCheck;

        public CupItem(int position, Cocktail cocktail, boolean hasCheck) {
            this.position = position;
            this.cocktail = cocktail;
            this.hasCheck = hasCheck;
        }

        public int getPosition() {
            return position;
        }

        public CupItem setPosition(int position) {
            this.position = position;
            return this;
        }

        public Cocktail getCocktail() {
            return cocktail;
        }

        public CupItem setCocktail(Cocktail cocktail) {
            this.cocktail = cocktail;
            return this;
        }

        public boolean isHasCheck() {
            return hasCheck;
        }

        public CupItem setHasCheck(boolean hasCheck) {
            this.hasCheck = hasCheck;
            return this;
        }
    }

}
