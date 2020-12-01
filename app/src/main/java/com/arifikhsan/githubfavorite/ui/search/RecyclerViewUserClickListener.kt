package com.arifikhsan.githubfavorite.ui.search

import android.view.View
import com.arifikhsan.githubfavorite.model.User

interface RecyclerViewUserClickListener {
    fun onItemClicked(view: View, user: User)
}