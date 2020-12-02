package com.arifikhsan.githubfavorite.ui.favorite

import android.view.View
import com.arifikhsan.githubfavorite.entity.User

interface FavoriteClickListener {
    fun removeUser(view: View, user: User)
}