package com.hwx.usbhost.usbhost.db.manager;

import com.hwx.usbhost.usbhost.db.Ornament;
import com.hwx.usbhost.usbhost.db.OrnamentDao;
import com.hwx.usbhost.usbhost.db.OrnamentMore;
import com.hwx.usbhost.usbhost.db.OrnamentMoreDao;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bj on 2016/10/19.
 */

public class OrnamentMoreDaoManager {
    private static OrnamentMoreDaoManager instance;
    public static OrnamentMoreDaoManager getOrnamentMoreDaoManager(){
        if (instance==null){
            instance=new OrnamentMoreDaoManager();
        }
        return instance;
    }

    private OrnamentMoreDao getOrnamentMoreDao(){
        return GreenDaoManager.getInstance().getSession().getOrnamentMoreDao();
    }

    private OrnamentDao getOrnamentDao(){
        return GreenDaoManager.getInstance().getSession().getOrnamentDao();
    }

    public void addOrnamentMore(OrnamentMore more){
        getOrnamentMoreDao().insert(more);
    }

    public void addOrnament(Ornament ornament){
        getOrnamentDao().insert(ornament);
    }
    public void updateOrnament(Ornament ornament){
        getOrnamentDao().update(ornament);
    }
    public void updateOrnament(String ornamentName,String newName){
        for (Ornament ornament:getOrnamentDao().queryBuilder().where(OrnamentDao.Properties.Name.eq(ornamentName)).limit(1).build().list()){
            ornament.setName(newName);
            updateOrnament(ornament);
        }
    }
    public void delOrnament(String ornamentName){
        for (Ornament ornament:getOrnamentDao().queryBuilder().where(OrnamentDao.Properties.Name.eq(ornamentName)).limit(1).build().list()){
            getOrnamentDao().delete(ornament);
        }
    }

    public void delOrnamentMore(OrnamentMore ornament){
        getOrnamentMoreDao().delete(ornament);
    }

    public void saveOrnamentMore(OrnamentMore ornamentMore){
        getOrnamentMoreDao().insert(ornamentMore);
    }
    public void saveOrnamentMoreList(List<OrnamentMore> ornamentList,String name,boolean clear){
        if (clear){
            for (OrnamentMore ornamentMore:queryList(name))
                delOrnamentMore(ornamentMore);
        }
        for (OrnamentMore ornamentMore:ornamentList){
            ornamentMore.setName(name);
            saveOrnamentMore(ornamentMore);
        }
    }

    //查询id介于2到13之间的数据，limit表示查询5条数据。
    public List<OrnamentMore> queryList(String name){
        List<OrnamentMore> list = null;
        try {
            list = getOrnamentMoreDao().queryBuilder()
                    .where(OrnamentMoreDao.Properties.Name.eq(name)).build().list();
        } catch (Exception e) {
            e.printStackTrace();
            list=new ArrayList<>();
        }
        return  list;
    }

    public List<String> queryListNew(){
        List<String> itemBeanList=new ArrayList<>();
        List<Ornament> list = getOrnamentDao().queryBuilder().build().list();
        for (Ornament ornament:list){
            itemBeanList.add(ornament.getName());
        }
        return itemBeanList;
    }
}
