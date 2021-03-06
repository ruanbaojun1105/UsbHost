package com.hwx.usbhost.usbhost.db;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "COCKTAIL".
*/
public class CocktailDao extends AbstractDao<Cocktail, Long> {

    public static final String TABLENAME = "COCKTAIL";

    /**
     * Properties of entity Cocktail.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property Name = new Property(1, String.class, "name", false, "name");
        public final static Property ImageUrl = new Property(2, String.class, "imageUrl", false, "imageUrl");
        public final static Property Accessorie = new Property(3, String.class, "accessorie", false, "accessories");
        public final static Property Glass = new Property(4, String.class, "glass", false, "glass");
        public final static Property PreviewImage = new Property(5, String.class, "previewImage", false, "previewImage");
        public final static Property Ornament = new Property(6, String.class, "ornament", false, "ornament");
    }


    public CocktailDao(DaoConfig config) {
        super(config);
    }
    
    public CocktailDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"COCKTAIL\" (" + //
                "\"_id\" INTEGER PRIMARY KEY ," + // 0: id
                "\"name\" TEXT," + // 1: name
                "\"imageUrl\" TEXT," + // 2: imageUrl
                "\"accessories\" TEXT," + // 3: accessorie
                "\"glass\" TEXT," + // 4: glass
                "\"previewImage\" TEXT," + // 5: previewImage
                "\"ornament\" TEXT);"); // 6: ornament
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"COCKTAIL\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, Cocktail entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String name = entity.getName();
        if (name != null) {
            stmt.bindString(2, name);
        }
 
        String imageUrl = entity.getImageUrl();
        if (imageUrl != null) {
            stmt.bindString(3, imageUrl);
        }
 
        String accessorie = entity.getAccessorie();
        if (accessorie != null) {
            stmt.bindString(4, accessorie);
        }
 
        String glass = entity.getGlass();
        if (glass != null) {
            stmt.bindString(5, glass);
        }
 
        String previewImage = entity.getPreviewImage();
        if (previewImage != null) {
            stmt.bindString(6, previewImage);
        }
 
        String ornament = entity.getOrnament();
        if (ornament != null) {
            stmt.bindString(7, ornament);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, Cocktail entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String name = entity.getName();
        if (name != null) {
            stmt.bindString(2, name);
        }
 
        String imageUrl = entity.getImageUrl();
        if (imageUrl != null) {
            stmt.bindString(3, imageUrl);
        }
 
        String accessorie = entity.getAccessorie();
        if (accessorie != null) {
            stmt.bindString(4, accessorie);
        }
 
        String glass = entity.getGlass();
        if (glass != null) {
            stmt.bindString(5, glass);
        }
 
        String previewImage = entity.getPreviewImage();
        if (previewImage != null) {
            stmt.bindString(6, previewImage);
        }
 
        String ornament = entity.getOrnament();
        if (ornament != null) {
            stmt.bindString(7, ornament);
        }
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public Cocktail readEntity(Cursor cursor, int offset) {
        Cocktail entity = new Cocktail( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // name
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // imageUrl
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // accessorie
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // glass
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // previewImage
            cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6) // ornament
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, Cocktail entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setName(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setImageUrl(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setAccessorie(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setGlass(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setPreviewImage(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setOrnament(cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(Cocktail entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(Cocktail entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(Cocktail entity) {
        return entity.getId() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
