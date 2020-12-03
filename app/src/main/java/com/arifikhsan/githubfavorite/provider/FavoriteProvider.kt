package com.arifikhsan.githubfavorite.provider

import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import com.arifikhsan.githubfavorite.config.Constant.AUTHORITY
import com.arifikhsan.githubfavorite.config.Constant.CONTENT_URI
import com.arifikhsan.githubfavorite.config.Constant.USER_TABLE
import com.arifikhsan.githubfavorite.database.AppDatabase
import com.arifikhsan.githubfavorite.database.UserDao
import com.arifikhsan.githubfavorite.helper.MappingHelper.mapContentValuesToUser

class FavoriteProvider : ContentProvider() {

    companion object {
        private const val USER = 1
        private const val USER_ID = 2
        private val sUriMatcher = UriMatcher(UriMatcher.NO_MATCH)
        private lateinit var userDao: UserDao
    }

    init {
        // content://com.arifikhsan.githubfavorite/users
        sUriMatcher.addURI(AUTHORITY, USER_TABLE, USER)

        // content://com.arifikhsan.githubfavorite/users/id
        sUriMatcher.addURI(AUTHORITY, "$USER_TABLE/#", USER_ID)
    }

    override fun onCreate(): Boolean {
        context?.let {
            userDao = AppDatabase.getDatabase(it).userDao()
        }
        return true
    }

    override fun query(
        uri: Uri, projection: Array<String>?, selection: String?,
        selectionArgs: Array<String>?, sortOrder: String?
    ): Cursor? {
        return when (sUriMatcher.match(uri)) {
            USER -> userDao.getUsers()
            USER_ID -> uri.lastPathSegment?.let { userDao.findById(it.toInt()) }
            else -> null
        }
    }

    override fun getType(uri: Uri): String? {
        return null
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        val added: Long = when (USER) {
            sUriMatcher.match(uri) -> userDao.insertUser(mapContentValuesToUser(values))
            else -> 0
        }

        context?.contentResolver?.notifyChange(CONTENT_URI, null)
        return Uri.parse("$CONTENT_URI/$added")
    }

    override fun update(
        uri: Uri, values: ContentValues?, selection: String?,
        selectionArgs: Array<String>?
    ): Int {
        if (uri.lastPathSegment == null) {
            return 0
        }

        val updated = when (USER_ID) {
            sUriMatcher.match(uri) -> userDao.updateUser(mapContentValuesToUser(values))
            else -> 0
        }

        context?.contentResolver?.notifyChange(CONTENT_URI, null)
        return updated
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int {
        if (uri.lastPathSegment == null) {
            return 0
        }
        val deleted: Int = when (USER_ID) {
            sUriMatcher.match(uri) -> userDao.deleteById(uri.lastPathSegment!!.toInt())
            else -> 0
        }

        context?.contentResolver?.notifyChange(CONTENT_URI, null)
        return deleted
    }
}