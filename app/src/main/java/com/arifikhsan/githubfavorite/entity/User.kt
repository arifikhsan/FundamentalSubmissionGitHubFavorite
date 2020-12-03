package com.arifikhsan.githubfavorite.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.arifikhsan.githubfavorite.config.Constant.USER_ATTRIBUTES.AVATAR_URL
import com.arifikhsan.githubfavorite.config.Constant.USER_ATTRIBUTES.HTML_URL
import com.arifikhsan.githubfavorite.config.Constant.USER_ATTRIBUTES.PUBLIC_GISTS
import com.arifikhsan.githubfavorite.config.Constant.USER_ATTRIBUTES.PUBLIC_REPOS
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "users")
data class User(
    @PrimaryKey
    val id: Int = 0,
    val login: String = "",
    val name: String = "",

    @ColumnInfo(name = AVATAR_URL)
    @SerializedName(AVATAR_URL)
    val avatarUrl: String = "",
    val type: String = "",
    val bio: String? = "",

    @ColumnInfo(name = HTML_URL)
    @SerializedName(HTML_URL)
    val htmlUrl: String = "",
    val followers: Int = 0,
    val following: Int = 0,

    @ColumnInfo(name = PUBLIC_REPOS)
    @SerializedName(PUBLIC_REPOS)
    val publicRepos: Int = 0,

    @ColumnInfo(name = PUBLIC_GISTS)
    @SerializedName(PUBLIC_GISTS)
    val publicGists: Int = 0,
) :
    Parcelable