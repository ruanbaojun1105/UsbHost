package com.hwx.usbhost.usbhost.db;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/10/14.
 * 配方对应出酒位置
 */
@Entity
public class CocktailFormula implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    private Long id;
    @Property(nameInDb = "name")//name
    private String name;
    @Property(nameInDb = "position")//出酒位置
    private String position;
    @Property(nameInDb = "time")//出酒时间0
    private String time;
    @Generated(hash = 221625945)
    public CocktailFormula(Long id, String name, String position, String time) {
        this.id = id;
        this.name = name;
        this.position = position;
        this.time = time;
    }
    @Generated(hash = 624273892)
    public CocktailFormula() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getPosition() {
        return this.position;
    }
    public void setPosition(String position) {
        this.position = position;
    }
    public String getTime() {
        return this.time;
    }
    public void setTime(String time) {
        this.time = time;
    }
}
