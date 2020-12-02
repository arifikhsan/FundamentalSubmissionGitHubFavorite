package com.arifikhsan.githubfavorite.ui.search

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.arifikhsan.githubfavorite.R
import com.arifikhsan.githubfavorite.entity.User
import com.arifikhsan.githubfavorite.receiver.AlarmReceiver
import com.arifikhsan.githubfavorite.repository.GitHubRepository
import com.arifikhsan.githubfavorite.repository.UserRepository
import com.arifikhsan.githubfavorite.ui.adapter.UserAdapter
import com.arifikhsan.githubfavorite.ui.detail.DetailActivity
import com.arifikhsan.githubfavorite.ui.favorite.FavoriteActivity
import com.arifikhsan.githubfavorite.ui.setting.SettingsActivity
import com.google.android.material.snackbar.Snackbar
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject

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
        initView()
        searchUserByUsername() // show search for arif
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

    private fun initView() {
        userRepository = UserRepository(application)
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
        tv_begin_search.visibility = View.INVISIBLE

        val client = AsyncHttpClient()
        client.addHeader("Authorization", "df97872248fb3eecacba97569ad7156b9674c9df")
        client.addHeader("User-Agent", "request")

        client.get(
            "${GitHubRepository.BASE_URL}search/users?q=$searchUsername",
            object : AsyncHttpResponseHandler() {
                override fun onSuccess(
                    statusCode: Int,
                    headers: Array<out Header>?,
                    responseBody: ByteArray
                ) {
                    loading_indicator.visibility = View.INVISIBLE
                    val result = String(responseBody)

                    val jsonObject = JSONObject(result)
                    val items = jsonObject.getJSONArray("items")
                    for (i in 0 until items.length()) {
                        val userObject = items.getJSONObject(i)
                        val user = User(
                            id = userObject.getInt("id"),
                            login = userObject.getString("login"),
                            name = userObject.getString("login"),
                            avatarUrl = userObject.getString("avatar_url"),
                            type = userObject.getString("type")
                        )
                        searchUserResult.add(user)
                    }
                    showSearchUser()
                }

                override fun onFailure(
                    statusCode: Int,
                    headers: Array<out Header>?,
                    responseBody: ByteArray?,
                    error: Throwable?
                ) {
                    loading_indicator.visibility = View.INVISIBLE
                    Log.d(TAG, "onFailure: $statusCode | ${error?.message}")
                    error?.printStackTrace()
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
                intent.putExtra(DetailActivity.EXTRA_USERNAME, user.login)
                startActivity(intent)
            }

            override fun onAddFavoriteClicked(view: View, user: User) {
                userRepository.insert(user)
                Snackbar.make(view, "Berhasil menambahkan ke favorit", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
            }
        })
    }
}