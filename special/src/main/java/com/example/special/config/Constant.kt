package com.arifikhsan.githubfavorite.config

import android.net.Uri

object Constant {
    const val AUTHORITY = "com.arifikhsan.githubfavorite"
    const val SCHEME = "content"
    const val USER_TABLE = "users"

    object USER_ATTRIBUTES {
        const val ID = "id"
        const val LOGIN = "login"
        const val NAME = "name"
        const val AVATAR_URL = "avatar_url"
        const val TYPE = "type"
        const val BIO = "bio"
        const val HTML_URL = "html_url"
        const val FOLLOWERS = "followers"
        const val FOLLOWING = "following"
        const val PUBLIC_REPOS = "public_repos"
        const val PUBLIC_GISTS = "public_gists"
    }

    val CONTENT_URI: Uri = Uri.Builder()
        .scheme(SCHEME)
        .authority(AUTHORITY)
        .appendPath(USER_TABLE)
        .build()
}