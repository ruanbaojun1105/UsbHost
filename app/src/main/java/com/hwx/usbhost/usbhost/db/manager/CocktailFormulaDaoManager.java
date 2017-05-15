package com.hwx.usbhost.usbhost.db.manager;

import com.hwx.usbhost.usbhost.db.Accessories;
import com.hwx.usbhost.usbhost.db.AccessoriesDao;
import com.hwx.usbhost.usbhost.db.AccessoriesMore;
import com.hwx.usbhost.usbhost.db.AccessoriesMoreDao;
import com.hwx.usbhost.usbhost.db.CocktailFormula;
import com.hwx.usbhost.usbhost.db.CocktailFormulaDao;
import com.hwx.usbhost.usbhost.util.ListSort;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Administrator on 2016/10/19.
 */

public class CocktailFormulaDaoManager {
    private static CocktailFormulaDaoManager instance;
    public static CocktailFormulaDaoManager getCocktailFormulaDaoManager(){
        if (instance==null){
            instance=new CocktailFormulaDaoManager();
        }
        return instance;
    }

    private CocktailFormulaDao getCocktailFormulaDao(){
        return GreenDaoManager.getInstance().getSession().getCocktailFormulaDao();
    }
    public List<CocktailFormula> queryList(String name){
        List<CocktailFormula> list = getCocktailFormulaDao().queryBuilder()
                .where(CocktailFormulaDao.Properties.Name.eq(name))/*.orderDesc(CocktailFormulaDao.Properties.Position)*/.build().list();
        return  list;
    }
    public byte[] getCocktailFormulaBytes(String name){
        List<CocktailFormula> cocktailFormulaList = CocktailFormulaDaoManager.getCocktailFormulaDaoManager().queryList(name);
        Collections.sort(cocktailFormulaList, new ListSort());
        byte[] bytes=new byte[32];
        if (cocktailFormulaList.size()!=16)
            return bytes;
        List<String> list=new ArrayList<>();
        for (int i = 0; i < cocktailFormulaList.size(); i++) {
            int d= 0;
            try {
                d = Integer.parseInt(cocktailFormulaList.get(i).getTime());
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
            list.add(String.valueOf(d/256));
            list.add(String.valueOf(d%256));
        }
        for (int i = 0; i < list.size(); i++) {
            try {
                bytes[i]=(byte)Integer.parseInt(list.get(i));
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        return bytes;
    }

    public void delCocktailFormula(CocktailFormula formula){
        getCocktailFormulaDao().delete(formula);
    }

    public void delOne(String name) {
        for (CocktailFormula cocktailFormula:getCocktailFormulaDao().queryBuilder().where(CocktailFormulaDao.Properties.Name.eq(name)).build().list()){
            getCocktailFormulaDao().delete(cocktailFormula);
        }
    }

    public void saveCocktailFormula(CocktailFormula cocktailFormula){
        getCocktailFormulaDao().insertOrReplace(cocktailFormula);
    }
    public void saveCocktailFormulaList(List<CocktailFormula> cocktailFormulas,String name,boolean clear){
        if (clear){
            for (CocktailFormula cocktailFormula:queryList(name))
                delCocktailFormula(cocktailFormula);
        }
        for (CocktailFormula cocktailFormula:cocktailFormulas){
            saveCocktailFormula(cocktailFormula);
        }
    }
}
