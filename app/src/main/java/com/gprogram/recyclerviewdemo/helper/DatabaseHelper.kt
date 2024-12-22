package com.gprogram.recyclerviewdemo.helper

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class DatabaseHelper(context: Context):SQLiteOpenHelper(context,DB_NAME,null,DB_VERSION) {
    companion object
    {
        const val DB_NAME="gprogram.db"
        const val DB_VERSION =1
        const val TABLE_NAME = "user"
        const val COLUMN_ID ="id"
        const val COLUMN_NAME ="name"
        const val COLUMN_AGE ="age"
        const val COLUMN_ADDRESS ="address"
        const val COLUMN_CONTACT ="contact"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val CREATE_TABLE = """
            CREATE TABLE $TABLE_NAME (
                $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_NAME TEXT NOT NULL,
                $COLUMN_AGE INTEGER NOT NULL,
                $COLUMN_ADDRESS TEXT NOT NULL,
                $COLUMN_CONTACT TEXT NOT NULL
            )
        """
        db!!.execSQL(CREATE_TABLE)
        Log.i("TAG","Table created")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    fun insertUser(name: String, age: Int, address: String, contact: String): Long {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_NAME, name)
            put(COLUMN_AGE, age)
            put(COLUMN_ADDRESS, address)
            put(COLUMN_CONTACT, contact)
        }
        return db.insert(TABLE_NAME, null, values)
    }

    fun getAllUsers(): List<Map<String, Any>> {
        val db = readableDatabase
        val cursor: Cursor = db.query(TABLE_NAME, null, null, null, null, null, null)
        val users = mutableListOf<Map<String, Any>>()

        with(cursor) {
            while (moveToNext()) {
                val user = mapOf(
                    COLUMN_ID to getInt(getColumnIndexOrThrow(COLUMN_ID)),
                    COLUMN_NAME to getString(getColumnIndexOrThrow(COLUMN_NAME)),
                    COLUMN_AGE to getInt(getColumnIndexOrThrow(COLUMN_AGE)),
                    COLUMN_ADDRESS to getString(getColumnIndexOrThrow(COLUMN_ADDRESS)),
                    COLUMN_CONTACT to getString(getColumnIndexOrThrow(COLUMN_CONTACT))
                )
                users.add(user)
            }
            close()
        }
        return users
    }

    fun getUserById(id: Int): Map<String, Any>? {
        val db = readableDatabase
        val cursor = db.query(
            TABLE_NAME,
            null,
            "$COLUMN_ID=?",
            arrayOf(id.toString()),
            null,
            null,
            null
        )
        return if (cursor.moveToFirst()) {
            val user = mapOf(
                COLUMN_ID to cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID)),
                COLUMN_NAME to cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME)),
                COLUMN_AGE to cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_AGE)),
                COLUMN_ADDRESS to cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ADDRESS)),
                COLUMN_CONTACT to cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CONTACT))
            )
            cursor.close()
            user
        } else {
            cursor.close()
            null
        }
    }

    fun updateUser(id: Int, name: String, age: Int, address: String, contact: String): Int {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_NAME, name)
            put(COLUMN_AGE, age)
            put(COLUMN_ADDRESS, address)
            put(COLUMN_CONTACT, contact)
        }
        return db.update(TABLE_NAME, values, "$COLUMN_ID=?", arrayOf(id.toString()))
    }

    fun deleteUser(id: Int): Int {
        val db = writableDatabase
        return db.delete(TABLE_NAME, "$COLUMN_ID=?", arrayOf(id.toString()))
    }


}