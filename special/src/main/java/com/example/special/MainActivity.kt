package com.example.special

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.arifikhsan.githubfavorite.config.Constant.CONTENT_URI
import com.arifikhsan.githubfavorite.entity.User
import com.arifikhsan.githubfavorite.helper.MappingHelper.mapCursorToArrayList
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var favoriteUsers = ArrayList<User>()

    companion object {
        private val TAG = MainActivity::class.java.simpleName
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()
    }

    private fun initView() {
        supportActionBar?.title = "Special GitHub User"
        val cursor = contentResolver?.query(CONTENT_URI, null, null, null, null)
        if (cursor != null) {
            favoriteUsers = mapCursorToArrayList(cursor)
            cursor.close()
        }

        val adapter = FavoriteAdapter(favoriteUsers)
        rv_favorite.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
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
                val intent = Intent(this@MainActivity, DetailActivity::class.java)
                intent.putExtra(DetailActivity.EXTRA_ID, user.id)
                startActivity(intent)
            }
        })
    }
}