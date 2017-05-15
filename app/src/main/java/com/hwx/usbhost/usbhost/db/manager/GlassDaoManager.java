package com.hwx.usbhost.usbhost.db.manager;

import com.hwx.usbhost.usbhost.db.Cocktail;
import com.hwx.usbhost.usbhost.db.CocktailDao;
import com.hwx.usbhost.usbhost.db.Glass;
import com.hwx.usbhost.usbhost.db.GlassDao;

import java.util.List;

/**
 * Created by Administrator on 2016/10/25.
 */

public class GlassDaoManager {

    private static GlassDaoManager instance;
    public static GlassDaoManager getGlassManager(){
        if (instance==null){
            instance=new GlassDaoManager();
        }
        return instance;
    }

    private GlassDao getGlassDao(){
        return GreenDaoManager.getInstance().getSession().getGlassDao();
    }
    public List<Glass> queryList(){
        List<Glass> glasses = getGlassDao().loadAll();
        return glasses;
    }

    public void add(Glass glass) {
        getGlassDao().insert(glass);
    }

}
