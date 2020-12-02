package com.arifikhsan.githubfavorite.ui.favorite

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.viewpager.widget.ViewPager
import com.arifikhsan.githubfavorite.R
import com.arifikhsan.githubfavorite.entity.User
import com.arifikhsan.githubfavorite.repository.GitHubRepository
import com.arifikhsan.githubfavorite.repository.UserRepository
import com.arifikhsan.githubfavorite.ui.detail.SectionsPagerAdapter
import com.arifikhsan.githubfavorite.ui.detail.UserFollowFragment
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.snackbar.Snackbar
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import kotlinx.android.synthetic.main.activity_detail.*
import org.json.JSONObject


class FavoriteDetailActivity : AppCompatActivity() {

    private lateinit var userRepository: UserRepository

    companion object {
        const val EXTRA_USERNAME = "extra_username"
        private val TAG = FavoriteDetailActivity::class.java.simpleName
        private var username = ""
        private lateinit var user: User
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite_detail)
        username = intent.getStringExtra(EXTRA_USERNAME) ?: "arifikhsan"
        initView()
        searchUserByUsername()
        populateView()
    }

    private fun searchUserByUsername() {
        user = userRepository.find(username)
    }

    @SuppressLint("SetTextI18n")
    private fun populateView() {
        Glide.with(this).load(user.avatarUrl)
            .apply(RequestOptions().override(80, 80)).into(img_avatar)
        tv_name.text = user.name
        tv_username.text = "@${user.login}"
        if (user.bio == "null") {
            tv_bio.visibility = View.GONE
        } else {
            tv_bio.text = user.bio
        }
        tv_follower.text = "Followers: ${user.follower}"
        tv_following.text = "Following: ${user.following}"
        tv_public_repos.text = "${user.publicRepos} Public Repositories"
        tv_public_gists.text = "${user.publicGists} Public Gists"
    }

    private fun initView() {
        supportActionBar?.title = "Detail Favorite"
        userRepository = UserRepository(application)

//        fab_add_favorite.setOnClickListener { view ->
//            userRepository.insert(user)
//            Snackbar.make(view, "Berhasil menambah ke favorit", Snackbar.LENGTH_LONG)
//                .setAction("Action", null).show()
//        }
    }
}