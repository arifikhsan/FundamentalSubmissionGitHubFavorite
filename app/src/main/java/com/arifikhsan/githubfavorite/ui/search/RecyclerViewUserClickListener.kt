package com.arifikhsan.githubfavorite.ui.search

import android.view.View
import com.arifikhsan.githubfavorite.entity.UserEntity

interface RecyclerViewUserClickListener {
    fun onItemClicked(view: View, userEntity: UserEntity)
}