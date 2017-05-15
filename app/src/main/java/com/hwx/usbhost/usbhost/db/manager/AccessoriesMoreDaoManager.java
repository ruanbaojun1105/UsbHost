package com.hwx.usbhost.usbhost.db.manager;

import com.hwx.usbhost.usbhost.db.Accessories;
import com.hwx.usbhost.usbhost.db.AccessoriesDao;
import com.hwx.usbhost.usbhost.db.AccessoriesMore;
import com.hwx.usbhost.usbhost.db.AccessoriesMoreDao;
import com.hwx.usbhost.usbhost.db.OrnamentDao;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/10/19.
 */

public class AccessoriesMoreDaoManager {
    private static AccessoriesMoreDaoManager instance;
    public static AccessoriesMoreDaoManager getAccessoriesMoreDaoManager(){
        if (instance==null){
            instance=new AccessoriesMoreDaoManager();
        }
        return instance;
    }

    private AccessoriesMoreDao getAccessoriesMoreDaoDao(){
        return GreenDaoManager.getInstance().getSession().getAccessoriesMoreDao();
    }

    private AccessoriesDao getAccessoriesDao(){
        return GreenDaoManager.getInstance().getSession().getAccessoriesDao();
    }

    public List<AccessoriesMore> queryList(String name){
        List<AccessoriesMore> list = null;
        try {
            list = getAccessoriesMoreDaoDao().queryBuilder()
                    .where(AccessoriesMoreDao.Properties.Name.eq(name)).build().list();
        } catch (Exception e) {
            e.printStackTrace();
            list=new ArrayList<>();
        }
        return  list;
    }

    public void addAccessoriesMore(AccessoriesMore more){
        getAccessoriesMoreDaoDao().insert(more);
    }

    public void delAccessoriesMore(AccessoriesMore more){
        getAccessoriesMoreDaoDao().delete(more);
    }

    public void saveAccessoriesMore(AccessoriesMore accessoriesMore){
        getAccessoriesMoreDaoDao().insert(accessoriesMore);
    }
    public void saveOrnamentMoreList(List<AccessoriesMore> accessoriesMoreList,String name,boolean clear){
        if (clear){
            for (AccessoriesMore accessoriesMore:queryList(name))
                delAccessoriesMore(accessoriesMore);
        }
        for (AccessoriesMore accessoriesMore:accessoriesMoreList){
            accessoriesMore.setName(name);
            saveAccessoriesMore(accessoriesMore);
        }
    }

    public void addAccessories(Accessories accessories){
        getAccessoriesDao().insert(accessories);
    }
    public void updateAccessories(String accessoriesName,String newName){
        for (Accessories accessories:getAccessoriesDao().queryBuilder().where(AccessoriesDao.Properties.Name.eq(accessoriesName)).limit(1).build().list()){
            accessories.setName(newName);
            getAccessoriesDao().update(accessories);
        }
    }
    public void delAccessories(String accessoriesName){
        for (Accessories accessories:getAccessoriesDao().queryBuilder().where(AccessoriesDao.Properties.Name.eq(accessoriesName)).limit(1).build().list()){
            getAccessoriesDao().delete(accessories);
        }
    }
    public List<String> queryListNew(){
        List<String> itemBeanList=new ArrayList<>();
        List<Accessories> list = getAccessoriesDao().queryBuilder().build().list();
        for (Accessories accessories:list){
            itemBeanList.add(accessories.getName());
        }
        return itemBeanList;
    }
}
