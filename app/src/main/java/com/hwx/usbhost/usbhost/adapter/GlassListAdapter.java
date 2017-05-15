package com.hwx.usbhost.usbhost.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hwx.usbhost.usbhost.R;
import com.hwx.usbhost.usbhost.db.Glass;
import com.hwx.usbhost.usbhost.util.DrawableUtil;

import java.util.List;

public class GlassListAdapter extends BaseQuickAdapter<Glass> {
	public GlassListAdapter(List<Glass> list){
		super(R.layout.glass_item,list);
	}

    @Override
    protected void convert(BaseViewHolder baseViewHolder, Glass glass) {
        baseViewHolder.setText(R.id.type,glass.getName());
        //baseViewHolder.setImageResource(R.id.image,getTypeUrl(glass.getType()));
        DrawableUtil.displayImage(baseViewHolder.convertView.getContext(),baseViewHolder.getView(R.id.image),getTypeUrl(glass.getType()));
    }

    public static int getTypeUrl(int type) {
        int res=0;
        switch (type){
            case 1:
                res=R.drawable.aa1;
                break;
            case 2:
                res=R.drawable.aa2;
                break;
            case 3:
                res=R.drawable.aa3;
                break;
            case 4:
                res=R.drawable.aa4;
                break;
            case 5:
                res=R.drawable.aa5;
                break;
            case 6:
                res=R.drawable.aa6;
                break;
            case 7:
                res=R.drawable.aa7;
                break;
            case 8:
                res=R.drawable.aa8;
                break;
            case 9:
                res=R.drawable.aa9;
                break;
            case 10:
                res=R.drawable.aa10;
                break;
            case 11:
                res=R.drawable.aa11;
                break;
            case 12:
                res=R.drawable.aa12;
                break;
            case 13:
                res=R.drawable.aa13;
                break;
            case 14:
                res=R.drawable.aa14;
                break;
            case 15:
                res=R.drawable.aa15;
                break;
            case 16:
                res=R.drawable.aa16;
                break;
            case 17:
                res=R.drawable.a17;
                break;
            case 18:
                res=R.drawable.a18;
                break;
            case 19:
                res=R.drawable.a19;
                break;
            case 20:
                res=R.drawable.a20;
                break;
            case 21:
                res=R.drawable.a21;
                break;
            case 22:
                res=R.drawable.a22;
                break;
            case 23:
                res=R.drawable.a23;
                break;
            case 24:
                res=R.drawable.a24;
                break;
            case 25:
                res=R.drawable.a25;
                break;
            case 26:
                res=R.drawable.a26;
                break;
            case 27:
                res=R.drawable.a27;
                break;
            case 28:
                res=R.drawable.a28;
                break;
            case 29:
                res=R.drawable.a29;
                break;
            case 30:
                res=R.drawable.a30;
                break;
            case 31:
                res=R.drawable.a31;
                break;
            case 32:
                res=R.drawable.a32;
                break;
            case 33:
                res=R.drawable.a33;
                break;
            case 34:
                res=R.drawable.a34;
                break;
            case 35:
                res=R.drawable.a35;
                break;
        }
        return res;
    }
    public static int getImageTypeUrl(int type) {
        int res=0;
        switch (type){
            case 1:
                res=R.drawable.a1;
                break;
            case 2:
                res=R.drawable.a2;
                break;
            case 3:
                res=R.drawable.a3;
                break;
            case 4:
                res=R.drawable.a4;
                break;
            case 5:
                res=R.drawable.a5;
                break;
            case 6:
                res=R.drawable.a6;
                break;
            case 7:
                res=R.drawable.a7;
                break;
            case 8:
                res=R.drawable.a8;
                break;
            case 9:
                res=R.drawable.a9;
                break;
            case 10:
                res=R.drawable.a10;
                break;
            case 11:
                res=R.drawable.a11;
                break;
            case 12:
                res=R.drawable.a12;
                break;
            case 13:
                res=R.drawable.a13;
                break;
            case 14:
                res=R.drawable.a14;
                break;
            case 15:
                res=R.drawable.a15;
                break;
            case 16:
                res=R.drawable.a16;
                break;
            case 17:
                res=R.drawable.a17;
                break;
            case 18:
                res=R.drawable.a18;
                break;
            case 19:
                res=R.drawable.a19;
                break;
            case 20:
                res=R.drawable.a20;
                break;
            case 21:
                res=R.drawable.a21;
                break;
            case 22:
                res=R.drawable.a22;
                break;
            case 23:
                res=R.drawable.a23;
                break;
            case 24:
                res=R.drawable.a24;
                break;
            case 25:
                res=R.drawable.a25;
                break;
            case 26:
                res=R.drawable.a26;
                break;
            case 27:
                res=R.drawable.a27;
                break;
            case 28:
                res=R.drawable.a28;
                break;
            case 29:
                res=R.drawable.a29;
                break;
            case 30:
                res=R.drawable.a30;
                break;
            case 31:
                res=R.drawable.a31;
                break;
            case 32:
                res=R.drawable.a32;
                break;
            case 33:
                res=R.drawable.a33;
                break;
            case 34:
                res=R.drawable.a34;
                break;
            case 35:
                res=R.drawable.a35;
                break;
        }
        return res;
    }
}
