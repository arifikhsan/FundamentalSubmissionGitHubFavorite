package com.arifikhsan.githubfavorite.repository

import android.app.Application
import android.content.ContentValues
import com.arifikhsan.githubfavorite.database.AppDatabase
import com.arifikhsan.githubfavorite.database.UserDao
import com.arifikhsan.githubfavorite.entity.User
import com.arifikhsan.githubfavorite.helper.MappingHelper.mapContentValuesToObject
import com.arifikhsan.githubfavorite.helper.MappingHelper.mapCursorToArrayList
import com.arifikhsan.githubfavorite.helper.MappingHelper.mapCursorToObject

class UserRepository(application: Application) {

    private var userDao: UserDao

    init {
        val database = AppDatabase.getDatabase(application)
        userDao = database.userDao()
    }

    fun allUsers(): List<User> {
        return mapCursorToArrayList(userDao.getUsers())
    }

    fun findByUsername(username: String): User {
        return mapCursorToObject(userDao.findByUsername(username))
    }

    fun findById(id: Int): User {
        return mapCursorToObject(userDao.findById(id))
    }

    fun insert(values: ContentValues): Long {
        return userDao.insertUser(mapContentValuesToObject(values))
    }

//    fun delete(user: User) {
//        userDao.deleteUser(user)
//    }

    fun deleteById(id: Int): Int {
        return userDao.deleteById(id)
    }
}