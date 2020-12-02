package com.arifikhsan.githubfavorite.ui.favorite

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.arifikhsan.githubfavorite.R
import com.arifikhsan.githubfavorite.entity.User
import com.arifikhsan.githubfavorite.repository.UserRepository
import com.arifikhsan.githubfavorite.ui.adapter.FavoriteUserAdapter
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_favorite.*
import kotlinx.android.synthetic.main.item_favorite_user.*

class FavoriteActivity : AppCompatActivity() {

    private var favoriteUsers = ArrayList<User>()
    private lateinit var userRepository: UserRepository

    companion object {
        private val TAG = FavoriteActivity::class.java.simpleName
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite)
        initView()
    }

    private fun initView() {
        userRepository = UserRepository(application)
        favoriteUsers = userRepository.allUsers.toCollection(ArrayList())

        val adapter = FavoriteUserAdapter(favoriteUsers)

        rv_favorite.apply {
            layoutManager = LinearLayoutManager(this@FavoriteActivity)
            this.adapter = adapter
        }

        adapter.setOnItemClickCallback(object : FavoriteUserAdapter.OnItemClickCallback {
            override fun onItemClicked(user: User) {
                userRepository.delete(user)
                favoriteUsers.remove(user)
                adapter.notifyDataSetChanged()

                Snackbar.make(
                    btn_remove_user,
                    "Kukira persahabatan kita istimewa 😭😭😭",
                    Snackbar.LENGTH_LONG
                ).show()
            }
        })

        adapter.setOnDetailClickCallback(object : FavoriteUserAdapter.OnDetailClickCallback {
            override fun onDetailClicked(user: User) {
                val intent = Intent(this@FavoriteActivity, FavoriteDetailActivity::class.java)
                intent.putExtra(FavoriteDetailActivity.EXTRA_USERNAME, user.login)
                startActivity(intent)
            }
        })
    }
}