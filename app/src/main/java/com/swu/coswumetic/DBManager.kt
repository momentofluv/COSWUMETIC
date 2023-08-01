package com.swu.coswumetic

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBManager(
    context: Context,
    name: String?,
    factory: SQLiteDatabase.CursorFactory?,
    version: Int
) : SQLiteOpenHelper(context, name, factory, version) {
    override fun onCreate(db: SQLiteDatabase?) {

        db!!.execSQL("CREATE TABLE IF NOT EXISTS cosListTBL (cosName text PRIMARY KEY, category text, openDate DATE, expDate DATE, memo text, photo text);")

    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
    }
}