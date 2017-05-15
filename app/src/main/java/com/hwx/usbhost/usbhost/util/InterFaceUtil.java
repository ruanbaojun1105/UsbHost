package com.hwx.usbhost.usbhost.util;

import com.hwx.usbhost.usbhost.db.Glass;

/**
 * Created by Administrator on 2016/9/5.
 */
public class InterFaceUtil {
    public interface OnHttpInterFace {
        void onSuccess(String str);
        void onFail();
    }

    public interface OnclickInterFace {
        void onClick(String str);
    }

    public interface OnclickInterFaceOver {
        void onClick();
    }

    public interface OnTimerInterFace {
        void onClick(int len);
    }
    public interface DialogListListener{
        void todosomething(int which);
    }
    public interface DialogGlassListener{
        void todosomething(Object glass);
    }
}
