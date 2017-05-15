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
 * DAO for table "ACCESSORIES_MORE".
*/
public class AccessoriesMoreDao extends AbstractDao<AccessoriesMore, Long> {

    public static final String TABLENAME = "ACCESSORIES_MORE";

    /**
     * Properties of entity AccessoriesMore.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property Name = new Property(1, String.class, "name", false, "name");
        public final static Property Accessories = new Property(2, String.class, "accessories", false, "accessories");
        public final static Property AccessoriesNumber = new Property(3, String.class, "accessoriesNumber", false, "accessoriesNumber");
    }


    public AccessoriesMoreDao(DaoConfig config) {
        super(config);
    }
    
    public AccessoriesMoreDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"ACCESSORIES_MORE\" (" + //
                "\"_id\" INTEGER PRIMARY KEY ," + // 0: id
                "\"name\" TEXT," + // 1: name
                "\"accessories\" TEXT," + // 2: accessories
                "\"accessoriesNumber\" TEXT);"); // 3: accessoriesNumber
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"ACCESSORIES_MORE\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, AccessoriesMore entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String name = entity.getName();
        if (name != null) {
            stmt.bindString(2, name);
        }
 
        String accessories = entity.getAccessories();
        if (accessories != null) {
            stmt.bindString(3, accessories);
        }
 
        String accessoriesNumber = entity.getAccessoriesNumber();
        if (accessoriesNumber != null) {
            stmt.bindString(4, accessoriesNumber);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, AccessoriesMore entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String name = entity.getName();
        if (name != null) {
            stmt.bindString(2, name);
        }
 
        String accessories = entity.getAccessories();
        if (accessories != null) {
            stmt.bindString(3, accessories);
        }
 
        String accessoriesNumber = entity.getAccessoriesNumber();
        if (accessoriesNumber != null) {
            stmt.bindString(4, accessoriesNumber);
        }
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public AccessoriesMore readEntity(Cursor cursor, int offset) {
        AccessoriesMore entity = new AccessoriesMore( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // name
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // accessories
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3) // accessoriesNumber
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, AccessoriesMore entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setName(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setAccessories(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setAccessoriesNumber(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(AccessoriesMore entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(AccessoriesMore entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(AccessoriesMore entity) {
        return entity.getId() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}