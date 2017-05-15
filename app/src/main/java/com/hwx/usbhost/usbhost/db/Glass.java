package com.hwx.usbhost.usbhost.db;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Unique;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/10/14.
 * 酒杯
 */
@Entity
public class Glass implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    private Long id;
    @Unique
    @Property(nameInDb = "name")//name
    private String name;
    @Property(nameInDb = "type")//type
    private int type;
    @Generated(hash = 297884962)
    public Glass(Long id, String name, int type) {
        this.id = id;
        this.name = name;
        this.type = type;
    }
    @Generated(hash = 984291407)
    public Glass() {
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
    public int getType() {
        return this.type;
    }
    public void setType(int type) {
        this.type = type;
    }

}
