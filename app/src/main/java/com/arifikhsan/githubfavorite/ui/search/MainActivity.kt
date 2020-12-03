package com.arifikhsan.githubfavorite.ui.search

import android.content.Intent
import android.database.ContentObserver
import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.arifikhsan.githubfavorite.R
import com.arifikhsan.githubfavorite.config.Constant.CONTENT_URI
import com.arifikhsan.githubfavorite.entity.SearchResponse
import com.arifikhsan.githubfavorite.entity.User
import com.arifikhsan.githubfavorite.helper.MappingHelper.mapUserToContentValues
import com.arifikhsan.githubfavorite.repository.GitHubRepository
import com.arifikhsan.githubfavorite.repository.UserRepository
import com.arifikhsan.githubfavorite.ui.adapter.UserAdapter
import com.arifikhsan.githubfavorite.ui.detail.DetailActivity
import com.arifikhsan.githubfavorite.ui.favorite.FavoriteActivity
import com.arifikhsan.githubfavorite.ui.setting.SettingsActivity
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class MainActivity : AppCompatActivity() {
    private var searchUsername = "arif"
    private var searchUserResult = ArrayList<User>()
    private lateinit var userRepository: UserRepository

    companion object {
        private val TAG = MainActivity::class.java.simpleName
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initResolver()
        initView()
        searchUserByUsername() // show search for arif
    }

    private fun initResolver() {
        val handlerThread = HandlerThread("DataObserver")
        handlerThread.start()
        val handler = Handler(handlerThread.looper)

        val myObserver = object : ContentObserver(handler) {
            override fun onChange(selfChange: Boolean) {
                loadUsers()
            }
        }

        contentResolver.registerContentObserver(CONTENT_URI, true, myObserver)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.mi_favorite -> startActivity(Intent(this, FavoriteActivity::class.java))
            R.id.mi_alarm -> startActivity(Intent(this, SettingsActivity::class.java))
        }
        return true
    }

    private fun loadUsers() {
        userRepository = UserRepository(application)
    }

    private fun initView() {
        sv_search_user.setOnClickListener { sv_search_user.isIconified = false }
        sv_search_user.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String?): Boolean = false
            override fun onQueryTextSubmit(query: String?): Boolean {
                loading_indicator.visibility = View.VISIBLE
                searchUserResult.clear()
                sv_search_user.clearFocus()
                searchUsername = query.toString()
                searchUserByUsername()
                return true
            }
        })
    }

    private fun searchUserByUsername() {
        Log.d(TAG, "searchUserByUsername: aaaa")
        tv_begin_search.visibility = View.INVISIBLE

        GitHubRepository().getService().searchUser(searchUsername)
            .enqueue(object : Callback<SearchResponse> {
                override fun onResponse(
                    call: Call<SearchResponse>,
                    response: Response<SearchResponse>
                ) {
                    response.body()?.let {
                        loading_indicator.visibility = View.INVISIBLE

                        it.items.forEach { user ->
                            searchUserResult.add(user)
                        }

                        Log.d(TAG, "aaaaa: $searchUserResult")
                        showSearchUser()
                    }
                }

                override fun onFailure(call: Call<SearchResponse>, t: Throwable) {
                    loading_indicator.visibility = View.INVISIBLE
                    Toast.makeText(this@MainActivity, "Gagal mencari user :(", Toast.LENGTH_SHORT).show()
                    Log.d(TAG, "onFailure: ${t.message}")
                    t.printStackTrace()
                }
            })
    }

    private fun showSearchUser() {
        val userAdapter = UserAdapter(searchUserResult)
        rv_search_result.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = userAdapter
        }
        userAdapter.setItemClickCallback(object : UserAdapter.OnItemClickCallback {
            override fun onDetailClicked(user: User) {
                val intent = Intent(this@MainActivity, DetailActivity::class.java)
                intent.putExtra(DetailActivity.EXTRA_ID, user.id)
                intent.putExtra(DetailActivity.EXTRA_USERNAME, user.login)
                startActivity(intent)
            }

            override fun onAddFavoriteClicked(view: View, user: User) {
                loading_indicator.visibility = View.VISIBLE
                GitHubRepository().getService().getDetailUserByUsername(user.login)
                    .enqueue(object : Callback<User> {
                        override fun onResponse(call: Call<User>, response: Response<User>) {
                            var userDetail = User()
                            response.body()?.let { userDetail = it }

                            contentResolver?.insert(CONTENT_URI, mapUserToContentValues(userDetail))
                            Snackbar.make(
                                view,
                                "Berhasil menambahkan ke favorit",
                                Snackbar.LENGTH_LONG
                            ).show()
                            loading_indicator.visibility = View.GONE
                        }

                        override fun onFailure(call: Call<User>, t: Throwable) {
                            Snackbar.make(view, "Gagal mengambil detail user", Snackbar.LENGTH_LONG)
                                .show()
                            loading_indicator.visibility = View.GONE
                        }
                    })
            }
        })
    }
}