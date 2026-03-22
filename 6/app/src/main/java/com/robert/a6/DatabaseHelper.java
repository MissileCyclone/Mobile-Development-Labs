package com.robert.a6;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {


    private static final String DATABASE_NAME = "inventory_storage.db";
    private static final int DATABASE_VERSION = 1;


    private static final String TBL_CATEGORIES = "cat_list";
    private static final String TBL_ITEMS = "items_catalog";

    private static final String COL_ID = "_id";
    private static final String COL_TITLE = "title";
    private static final String COL_INFO = "info";
    private static final String COL_COST = "cost";
    private static final String COL_CAT_REF = "category_link";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE " + TBL_CATEGORIES + " ("
                + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "name TEXT UNIQUE NOT NULL"
                + ")");


        db.execSQL("CREATE TABLE " + TBL_ITEMS + " ("
                + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COL_TITLE + " TEXT NOT NULL, "
                + COL_INFO + " TEXT, "
                + COL_COST + " REAL NOT NULL, "
                + COL_CAT_REF + " INTEGER NOT NULL, "
                + "FOREIGN KEY(" + COL_CAT_REF + ") REFERENCES " + TBL_CATEGORIES + "(" + COL_ID + ")"
                + ")");

        populateInitialData(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TBL_ITEMS);
        db.execSQL("DROP TABLE IF EXISTS " + TBL_CATEGORIES);
        onCreate(db);
    }

    private void populateInitialData(SQLiteDatabase db) {

        long compId = addCategory(db, "Компьютеры");
        long accId = addCategory(db, "Периферия");
        long softId = addCategory(db, "ПО и Сервис");


        addItem(db, "Ноутбук ASUS", "Ryzen 5, 16GB RAM, 512GB SSD", 65000, compId);
        addItem(db, "Системный блок", "Core i5, RTX 3060, 1TB", 82000, compId);
        addItem(db, "Планшет Pro", "Экран 12.9, 256GB", 95000, compId);
        addItem(db, "Моноблок", "24 дюйма, Full HD, 8GB", 48000, compId);
        addItem(db, "Мини-ПК", "Intel NUC, 16GB RAM", 32000, compId);


        addItem(db, "Монитор 4K", "IPS матрица, 27 дюймов", 28500, accId);
        addItem(db, "Механическая клавиатура", "RGB подсветка, Blue switches", 5400, accId);
        addItem(db, "Игровая мышь", "16000 DPI, беспроводная", 4200, accId);
        addItem(db, "Веб-камера", "Full HD 60fps", 3900, accId);
        addItem(db, "Микрофон", "Конденсаторный, USB", 6700, accId);
        addItem(db, "Принтер МФУ", "Лазерный, ч/б печать", 18000, accId);


        addItem(db, "Лицензия ОС", "Версия Professional", 12000, softId);
        addItem(db, "Антивирус", "Подписка на 1 год", 1800, softId);
        addItem(db, "Чистка и профилактика", "Замена термопасты", 2500, softId);
        addItem(db, "Сборка ПК", "Профессиональный кабель-менеджмент", 3000, softId);
        addItem(db, "Настройка сети", "Конфигурация роутера и VPN", 1500, softId);
    }

    private long addCategory(SQLiteDatabase db, String label) {
        ContentValues cv = new ContentValues();
        cv.put("name", label);
        return db.insert(TBL_CATEGORIES, null, cv);
    }

    private void addItem(SQLiteDatabase db, String name, String desc, double price, long catId) {
        ContentValues cv = new ContentValues();
        cv.put(COL_TITLE, name);
        cv.put(COL_INFO, desc);
        cv.put(COL_COST, price);
        cv.put(COL_CAT_REF, catId);
        db.insert(TBL_ITEMS, null, cv);
    }

    public List<Product> getItemsByGroup(String categoryName) {
        List<Product> itemList = new ArrayList<>();
        SQLiteDatabase database = this.getReadableDatabase();


        String sql = "SELECT t1." + COL_TITLE + ", t1." + COL_INFO + ", t1." + COL_COST +
                " FROM " + TBL_ITEMS + " t1 " +
                " JOIN " + TBL_CATEGORIES + " t2 ON t1." + COL_CAT_REF + " = t2." + COL_ID +
                " WHERE t2.name = ?";

        try (Cursor cursor = database.rawQuery(sql, new String[]{categoryName})) {
            if (cursor.moveToFirst()) {
                do {
                    String title = cursor.getString(0);
                    String info = cursor.getString(1);
                    double cost = cursor.getDouble(2);
                    itemList.add(new Product(title, info, cost));
                } while (cursor.moveToNext());
            }
        }
        return itemList;
    }
}