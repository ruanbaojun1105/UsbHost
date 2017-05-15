package com.hwx.usbhost.usbhost.db;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/10/14.
 * 辅料--冰块 苏打水 汤尼水
 */
@Entity
public class AccessoriesMore implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    private Long id;
    @Property(nameInDb = "name")//鸡尾酒名字
    private String name;
    @Property(nameInDb = "accessories")//accessories
    private String accessories;
    @Property(nameInDb = "accessoriesNumber")//辅料数量
    private String accessoriesNumber;
    @Generated(hash = 2028972578)
    public AccessoriesMore(Long id, String name, String accessories,
            String accessoriesNumber) {
        this.id = id;
        this.name = name;
        this.accessories = accessories;
        this.accessoriesNumber = accessoriesNumber;
    }
    @Generated(hash = 1227541114)
    public AccessoriesMore() {
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
    public String getAccessories() {
        return this.accessories;
    }
    public void setAccessories(String accessories) {
        this.accessories = accessories;
    }
    public String getAccessoriesNumber() {
        return this.accessoriesNumber;
    }
    public void setAccessoriesNumber(String accessoriesNumber) {
        this.accessoriesNumber = accessoriesNumber;
    }
}
