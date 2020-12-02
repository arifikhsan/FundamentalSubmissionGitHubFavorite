package com.arifikhsan.githubfavorite.ui.favorite

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.arifikhsan.githubfavorite.R
import com.arifikhsan.githubfavorite.application.GitHubFavoriteApplication
import com.arifikhsan.githubfavorite.entity.User
import kotlinx.android.synthetic.main.activity_favorite.*

class FavoriteActivity : AppCompatActivity() {
    private var favoriteUsers = ArrayList<User>()
    private val favoriteViewModel: FavoriteViewModel by viewModels {
        FavoriteViewModelFactory((application as GitHubFavoriteApplication).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite)
        populateView()
    }

    private fun populateView() {
        favoriteUsers = favoriteViewModel.users
        val adapter = FavoriteAdapter(favoriteUsers)
        rv_favorite.apply {
            layoutManager = LinearLayoutManager(this@FavoriteActivity)
            this.adapter = adapter
        }
    }
}