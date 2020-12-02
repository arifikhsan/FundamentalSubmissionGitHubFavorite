package com.arifikhsan.githubfavorite.ui.favorite

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.arifikhsan.githubfavorite.R
import com.arifikhsan.githubfavorite.entity.User
import com.arifikhsan.githubfavorite.repository.UserRepository
import com.arifikhsan.githubfavorite.ui.adapter.FavoriteAdapter
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_favorite.*

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

        val adapter = FavoriteAdapter(favoriteUsers)

        rv_favorite.apply {
            layoutManager = LinearLayoutManager(this@FavoriteActivity)
            this.adapter = adapter
        }

        adapter.setOnItemClickCallback(object : FavoriteAdapter.OnItemClickCallback {
            override fun onRemoveFavoriteClicked(view: View, user: User) {
                userRepository.delete(user)
                favoriteUsers.remove(user)
                adapter.notifyDataSetChanged()

                Snackbar.make(
                    view,
                    "Kukira hubungan kita istimewa :(",
                    Snackbar.LENGTH_LONG
                ).show()
            }

            override fun onDetailClicked(user: User) {
                val intent = Intent(this@FavoriteActivity, FavoriteDetailActivity::class.java)
                intent.putExtra(FavoriteDetailActivity.EXTRA_USERNAME, user.login)
                startActivity(intent)
            }
        })
    }
}