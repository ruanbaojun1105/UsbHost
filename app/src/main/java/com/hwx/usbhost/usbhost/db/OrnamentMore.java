package com.hwx.usbhost.usbhost.db;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/10/14.
 * 装饰品"--柠檬":"红车匣子"
 */
@Entity
public class OrnamentMore implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    private Long id;
    @Property(nameInDb = "name")//name
    private String name;
    @Property(nameInDb = "ornament")//装饰品名字
    private String ornament;
    @Generated(hash = 5094843)
    public OrnamentMore(Long id, String name, String ornament) {
        this.id = id;
        this.name = name;
        this.ornament = ornament;
    }
    @Generated(hash = 419263201)
    public OrnamentMore() {
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
    public String getOrnament() {
        return this.ornament;
    }
    public void setOrnament(String ornament) {
        this.ornament = ornament;
    }
}
