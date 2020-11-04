package com.example.fundamentalsubmissiongithubfavorite.ui.search

import android.view.View
import com.example.fundamentalsubmissiongithubfavorite.model.User

interface RecyclerViewUserClickListener {
    fun onItemClicked(view: View, user: User)
}