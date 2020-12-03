package com.arifikhsan.githubfavorite.ui.favorite

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.arifikhsan.githubfavorite.R
import com.arifikhsan.githubfavorite.config.Constant.CONTENT_URI
import com.arifikhsan.githubfavorite.entity.User
import com.arifikhsan.githubfavorite.helper.MappingHelper.mapCursorToArrayList
import com.arifikhsan.githubfavorite.repository.UserRepository
import com.arifikhsan.githubfavorite.ui.adapter.FavoriteAdapter
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_favorite.*

class FavoriteActivity : AppCompatActivity() {

    private var favoriteUsers = ArrayList<User>()

    companion object {
        private val TAG = FavoriteActivity::class.java.simpleName
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite)
        initView()
    }

    private fun initView() {
        val cursor = contentResolver?.query(CONTENT_URI, null, null, null, null)
        if (cursor != null) {
            favoriteUsers = mapCursorToArrayList(cursor)
            cursor.close()
        }
        val adapter = FavoriteAdapter(favoriteUsers)

        rv_favorite.apply {
            layoutManager = LinearLayoutManager(this@FavoriteActivity)
            this.adapter = adapter
        }

        adapter.setOnItemClickCallback(object : FavoriteAdapter.OnItemClickCallback {
            override fun onRemoveFavoriteClicked(view: View, user: User) {
                val uriWithId = Uri.parse(CONTENT_URI.toString() + '/' + user.id)
                contentResolver?.delete(uriWithId, user.id.toString(), null)
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
                intent.putExtra(FavoriteDetailActivity.EXTRA_ID, user.id)
                startActivity(intent)
            }
        })
    }
}