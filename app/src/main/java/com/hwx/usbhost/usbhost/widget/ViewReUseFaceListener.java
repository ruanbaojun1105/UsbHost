package com.hwx.usbhost.usbhost.widget;

import android.content.Context;
import android.view.View;

/**
 * Created by bj on 2016/6/2 0002.
 * 控件复用接口
 */
public interface ViewReUseFaceListener {

        int backViewRes();
        View backView(Context context);
        void justItemToDo(Object data, View itemView, int position,Context context);
}
