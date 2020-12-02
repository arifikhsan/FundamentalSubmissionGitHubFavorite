package com.arifikhsan.githubfavorite.ui.favorite

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.arifikhsan.githubfavorite.R
import com.arifikhsan.githubfavorite.entity.User
import com.arifikhsan.githubfavorite.repository.UserRepository
import com.arifikhsan.githubfavorite.ui.adapter.FavoriteUserAdapter
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_favorite.*

class FavoriteActivity : AppCompatActivity(), FavoriteClickListener {

    private var favoriteUsers = ArrayList<User>()
    private lateinit var userRepository: UserRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite)
        populateView()
    }

    private fun populateView() {
        userRepository = UserRepository(application)
        favoriteUsers = userRepository.allUsers.toCollection(ArrayList())
        val adapter = FavoriteUserAdapter(favoriteUsers)

        rv_favorite.apply {
            layoutManager = LinearLayoutManager(this@FavoriteActivity)
            this.adapter = adapter
        }

        rv_favorite.setOnClickListener(object : FavoriteUserAdapter.OnItemClickCallback,
            View.OnClickListener {
            override fun onItemClicked(user: User) {
                Toast.makeText(this@FavoriteActivity, "aaakhkjhkjh", Toast.LENGTH_SHORT).show()
            }

            override fun onClick(v: View?) {
                Toast.makeText(this@FavoriteActivity, "aaa", Toast.LENGTH_SHORT).show()
            }
        })
    }

    override fun removeUser(view: View, user: User) {
        Toast.makeText(this@FavoriteActivity, "aaa", Toast.LENGTH_SHORT).show()
        Snackbar.make(view, "Kukira persahabatan kita istimewa 😭😭😭", Snackbar.LENGTH_LONG).show()
        userRepository.delete(user)
    }
}