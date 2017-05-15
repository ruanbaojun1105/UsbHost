package com.hwx.usbhost.usbhost.db;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.ToMany;
import org.greenrobot.greendao.annotation.ToOne;

import java.io.Serializable;

@Entity
public class Cocktail implements Serializable{
    private static final long serialVersionUID = 1L;
    @Id
    private Long id;
    @Property(nameInDb = "name")//name
    private String name;
    @Deprecated
    @Property(nameInDb = "imageUrl")//iamge
    private String imageUrl;
    @Deprecated
    @Property(nameInDb = "accessories")//辅料
    private String accessorie;
    @Property(nameInDb = "glass")//酒杯
    private String glass;
    @Property(nameInDb = "previewImage")//预览图
    private String previewImage;
    @Deprecated
    @Property(nameInDb = "ornament")//装饰品
    private String ornament;
    @Generated(hash = 1397910512)
    public Cocktail(Long id, String name, String imageUrl, String accessorie,
            String glass, String previewImage, String ornament) {
        this.id = id;
        this.name = name;
        this.imageUrl = imageUrl;
        this.accessorie = accessorie;
        this.glass = glass;
        this.previewImage = previewImage;
        this.ornament = ornament;
    }
    @Generated(hash = 806485141)
    public Cocktail() {
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
    public String getImageUrl() {
        return this.imageUrl;
    }
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
    public String getAccessorie() {
        return this.accessorie;
    }
    public void setAccessorie(String accessorie) {
        this.accessorie = accessorie;
    }
    public String getGlass() {
        return this.glass;
    }
    public void setGlass(String glass) {
        this.glass = glass;
    }
    public String getOrnament() {
        return this.ornament;
    }
    public void setOrnament(String ornament) {
        this.ornament = ornament;
    }
    public String getPreviewImage() {
        return this.previewImage;
    }
    public void setPreviewImage(String previewImage) {
        this.previewImage = previewImage;
    }
}