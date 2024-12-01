package com.example.user_module

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "UserDatabase.db"
        private const val DATABASE_VERSION = 1

        const val TABLE_USERS = "users"
        const val COLUMN_ID = "id"
        const val COLUMN_FIRST_NAME = "first_name"
        const val COLUMN_LAST_NAME = "last_name"
        const val COLUMN_EMAIL = "email"
        const val COLUMN_PASSWORD = "password"
        const val COLUMN_BIRTHDATE = "birthdate"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createTableQuery = """
            CREATE TABLE $TABLE_USERS (
                $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_FIRST_NAME TEXT,
                $COLUMN_LAST_NAME TEXT,
                $COLUMN_EMAIL TEXT UNIQUE,
                $COLUMN_PASSWORD TEXT,
                $COLUMN_BIRTHDATE TEXT
            )
        """.trimIndent()
        db.execSQL(createTableQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_USERS")
        onCreate(db)
    }

    fun insertUser(firstName: String, lastName: String, email: String, password: String, birthdate: String): Boolean {
        val db = writableDatabase
        val values = ContentValues()
        values.put(COLUMN_FIRST_NAME, firstName)
        values.put(COLUMN_LAST_NAME, lastName)
        values.put(COLUMN_EMAIL, email)
        values.put(COLUMN_PASSWORD, password)
        values.put(COLUMN_BIRTHDATE, birthdate)

        return try {
            db.insertOrThrow(TABLE_USERS, null, values) != -1L
        } catch (e: Exception) {
            false
        }
    }

    fun validateUser(email: String, password: String): Boolean {
        val db = readableDatabase
        val query = """
            SELECT * FROM $TABLE_USERS WHERE $COLUMN_EMAIL = ? AND $COLUMN_PASSWORD = ?
        """
        val cursor = db.rawQuery(query, arrayOf(email, password))
        val isValid = cursor.count > 0
        cursor.close()
        return isValid
    }

    fun getAllUsers(): List<User> {
        val userList = mutableListOf<User>()
        val db = readableDatabase
        val query = "SELECT * FROM $TABLE_USERS"
        val cursor = db.rawQuery(query, null)

        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID))
                val firstName = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_FIRST_NAME))
                val lastName = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_LAST_NAME))
                val email = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_EMAIL))
                val birthdate = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_BIRTHDATE))

                val user = User(id, firstName, lastName, email, birthdate)
                userList.add(user)
            } while (cursor.moveToNext())
        }

        cursor.close()
        return userList
    }
}
