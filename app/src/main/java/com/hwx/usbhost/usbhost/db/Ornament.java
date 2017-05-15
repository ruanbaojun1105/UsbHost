package com.hwx.usbhost.usbhost.db;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/10/14.
 * 装饰品"--柠檬":"红车匣子"
 */
@Entity
public class Ornament implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    private Long id;
    @Property(nameInDb = "name")//name
    private String name;
    @Generated(hash = 331806227)
    public Ornament(Long id, String name) {
        this.id = id;
        this.name = name;
    }
    @Generated(hash = 1112281293)
    public Ornament() {
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
}
