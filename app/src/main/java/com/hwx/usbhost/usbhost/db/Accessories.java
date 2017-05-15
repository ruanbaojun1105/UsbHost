package com.hwx.usbhost.usbhost.db;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/10/14.
 * 辅料--冰块 苏打水 汤尼水
 */
@Entity
public class Accessories implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    private Long id;
    @Property(nameInDb = "name")//name
    private String name;
    @Generated(hash = 111212533)
    public Accessories(Long id, String name) {
        this.id = id;
        this.name = name;
    }
    @Generated(hash = 1483837939)
    public Accessories() {
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
