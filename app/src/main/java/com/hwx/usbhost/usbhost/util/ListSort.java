package com.hwx.usbhost.usbhost.util;

import com.hwx.usbhost.usbhost.db.CocktailFormula;

import java.util.Comparator;

/**
 * Created by Administrator on 2017/1/10.
 */

public class ListSort implements Comparator<CocktailFormula> {

    @Override
    public int compare(CocktailFormula cocktailFormula, CocktailFormula cocktailFormula2) {
        int a= 0;
        int b= 0;
        try {
            a = Integer.parseInt(cocktailFormula.getPosition());
            b = Integer.parseInt(cocktailFormula2.getPosition());
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        if (b>a)
            return -1;
        if (b<a)
            return 1;
        return 0;
    }
}
