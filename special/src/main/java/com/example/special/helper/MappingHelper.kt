package com.arifikhsan.githubfavorite.helper

import android.content.ContentValues
import android.database.Cursor
import com.arifikhsan.githubfavorite.config.Constant.USER_ATTRIBUTES.AVATAR_URL
import com.arifikhsan.githubfavorite.config.Constant.USER_ATTRIBUTES.BIO
import com.arifikhsan.githubfavorite.config.Constant.USER_ATTRIBUTES.FOLLOWERS
import com.arifikhsan.githubfavorite.config.Constant.USER_ATTRIBUTES.FOLLOWING
import com.arifikhsan.githubfavorite.config.Constant.USER_ATTRIBUTES.HTML_URL
import com.arifikhsan.githubfavorite.config.Constant.USER_ATTRIBUTES.ID
import com.arifikhsan.githubfavorite.config.Constant.USER_ATTRIBUTES.LOGIN
import com.arifikhsan.githubfavorite.config.Constant.USER_ATTRIBUTES.NAME
import com.arifikhsan.githubfavorite.config.Constant.USER_ATTRIBUTES.PUBLIC_GISTS
import com.arifikhsan.githubfavorite.config.Constant.USER_ATTRIBUTES.PUBLIC_REPOS
import com.arifikhsan.githubfavorite.config.Constant.USER_ATTRIBUTES.TYPE
import com.arifikhsan.githubfavorite.entity.User
import org.json.JSONObject

object MappingHelper {
    fun mapCursorToArrayList(usersCursor: Cursor?): ArrayList<User> {
        val users = ArrayList<User>()

        usersCursor?.apply {
            while (moveToNext()) {
                val user = User(
                    id = getInt(getColumnIndexOrThrow(ID)),
                    login = getString(getColumnIndexOrThrow(LOGIN)),
                    name = getString(getColumnIndexOrThrow(NAME)),
                    avatarUrl = getString(getColumnIndexOrThrow(AVATAR_URL)),
                    type = getString(getColumnIndexOrThrow(TYPE)),
                    bio = getString(getColumnIndexOrThrow(BIO)),
                    htmlUrl = getString(getColumnIndexOrThrow(HTML_URL)),
                    followers = getInt(getColumnIndexOrThrow(FOLLOWERS)),
                    following = getInt(getColumnIndexOrThrow(FOLLOWING)),
                    publicRepos = getInt(getColumnIndexOrThrow(PUBLIC_REPOS)),
                    publicGists = getInt(getColumnIndexOrThrow(PUBLIC_GISTS)),
                )
                users.add(user)
            }
        }

        return users
    }

    fun mapCursorToUser(userCursor: Cursor?): User {
        var user = User()

        userCursor?.apply {
            moveToFirst()
            user = User(
                id = getInt(getColumnIndexOrThrow(ID)),
                login = getString(getColumnIndexOrThrow(LOGIN)),
                name = getString(getColumnIndexOrThrow(NAME)),
                avatarUrl = getString(getColumnIndexOrThrow(AVATAR_URL)),
                type = getString(getColumnIndexOrThrow(TYPE)),
                bio = getString(getColumnIndexOrThrow(BIO)),
                htmlUrl = getString(getColumnIndexOrThrow(HTML_URL)),
                followers = getInt(getColumnIndexOrThrow(FOLLOWERS)),
                following = getInt(getColumnIndexOrThrow(FOLLOWING)),
                publicRepos = getInt(getColumnIndexOrThrow(PUBLIC_REPOS)),
                publicGists = getInt(getColumnIndexOrThrow(PUBLIC_GISTS)),
            )
        }

        return user
    }

    fun mapContentValuesToObject(contentValues: ContentValues?): User {
        var user = User()

        contentValues?.apply {
            user = User(
                id = getAsInteger(ID),
                login = getAsString(LOGIN),
                name = getAsString(NAME),
                avatarUrl = getAsString(AVATAR_URL),
                type = getAsString(TYPE),
                bio = getAsString(BIO),
                htmlUrl = getAsString(HTML_URL),
                followers = getAsInteger(FOLLOWERS),
                following = getAsInteger(FOLLOWING),
                publicRepos = getAsInteger(PUBLIC_REPOS),
                publicGists = getAsInteger(PUBLIC_GISTS),
            )
        }
        return user
    }

    fun mapUserToContentValues(user: User): ContentValues {
        val values = ContentValues()

        values.apply {
            put(ID, user.id)
            put(LOGIN, user.login)
            put(NAME, user.name)
            put(AVATAR_URL, user.avatarUrl)
            put(TYPE, user.type)
            put(BIO, user.bio)
            put(HTML_URL, user.htmlUrl)
            put(FOLLOWERS, user.followers)
            put(FOLLOWING, user.following)
            put(PUBLIC_REPOS, user.publicRepos)
            put(PUBLIC_GISTS, user.publicGists)
        }

        return values
    }

    fun mapJsonObjectToUser(userObject: JSONObject): User {
        var user: User

        userObject.apply {
            user = User(
                id = getInt("id"),
                login = getString("login"),
                name = getString("login"),
                avatarUrl = getString("avatar_url"),
                type = getString("type"),
                bio = getString("bio"),
                htmlUrl = getString("html_url"),
                followers = getInt("followers"),
                following = getInt("following"),
                publicRepos = getInt("public_repos"),
                publicGists = getInt("public_gists"),
            )
        }

        return user
    }
}