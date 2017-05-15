package com.hwx.usbhost.usbhost.db.manager;

import com.hwx.usbhost.usbhost.db.Cocktail;
import com.hwx.usbhost.usbhost.db.CocktailDao;

import java.util.List;

/**
 * Created by dd on 2016/10/12.
 */

public class CocktailManager {

    private static CocktailManager instance;
    public static CocktailManager getCocktailManager(){
        if (instance==null){
            instance=new CocktailManager();
        }
        return instance;
    }

    private CocktailDao getCocktailDao(){
        return GreenDaoManager.getInstance().getSession().getCocktailDao();
    }

    public void addCocktail(String name, String imageUrl, String accessorie,
                            String glass, String previewImage, String ornament){
        Cocktail cocktail = new Cocktail(null,name,imageUrl,accessorie,glass,previewImage,ornament);
        getCocktailDao().insert(cocktail);
    }

    public long queryRecord(){
        return getCocktailDao().queryBuilder().count();
    }

    public List<Cocktail> queryCocktailListAll(){
        List<Cocktail> cocktails = getCocktailDao().loadAll();
        return cocktails;
    }


    public void deleteCocktailByName(String name){
        Cocktail cocktail = getCocktailDao().queryBuilder().where(CocktailDao.Properties.Name.eq(name)).build().unique();
        if (cocktail == null) {
            //用户不存在
        }else{
            getCocktailDao().delete(cocktail);
        }
    }
    public void deleteCocktail(Cocktail cocktail){
        if (cocktail!=null)
            getCocktailDao().delete(cocktail);
    }
    public void deleteAllCocktail(){
        getCocktailDao().deleteAll();
    }

    //一个是id要大于等于10，同是还要满足username like %90%，注意最后的unique表示只查询一条数据出来即
    public void updateCocktail(Cocktail cocktail){
        //Cocktail cocktail = getCocktailDao().queryBuilder().where(UserDao.Properties.Id.ge(10), UserDao.Properties.Username.like("%90%")).build().unique();
        getCocktailDao().update(cocktail);
    }

    public void addCocktail(Cocktail cocktail){
        getCocktailDao().insert(cocktail);
    }

  //查询id介于2到13之间的数据，limit表示查询5条数据。
    public List<Cocktail> queryUserList(){
        List<Cocktail> list = getCocktailDao().queryBuilder()
                .where(CocktailDao.Properties.Id.between(2, 13)).limit(5).build().list();
        return  list;
    }
}
