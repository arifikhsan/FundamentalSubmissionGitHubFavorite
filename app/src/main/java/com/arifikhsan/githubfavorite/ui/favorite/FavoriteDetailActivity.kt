package com.arifikhsan.githubfavorite.ui.favorite

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.arifikhsan.githubfavorite.R
import com.arifikhsan.githubfavorite.config.Constant
import com.arifikhsan.githubfavorite.config.Constant.CONTENT_URI
import com.arifikhsan.githubfavorite.entity.User
import com.arifikhsan.githubfavorite.helper.MappingHelper
import com.arifikhsan.githubfavorite.helper.MappingHelper.mapCursorToObject
import com.arifikhsan.githubfavorite.repository.UserRepository
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_detail.img_avatar
import kotlinx.android.synthetic.main.activity_detail.tv_bio
import kotlinx.android.synthetic.main.activity_detail.tv_follower
import kotlinx.android.synthetic.main.activity_detail.tv_following
import kotlinx.android.synthetic.main.activity_detail.tv_name
import kotlinx.android.synthetic.main.activity_detail.tv_public_gists
import kotlinx.android.synthetic.main.activity_detail.tv_public_repos
import kotlinx.android.synthetic.main.activity_detail.tv_username
import kotlinx.android.synthetic.main.activity_favorite_detail.*


class FavoriteDetailActivity : AppCompatActivity() {

//    private lateinit var userRepository: UserRepository

    companion object {
        const val EXTRA_ID = "extra_id"
        const val EXTRA_USERNAME = "extra_username"
        private val TAG = FavoriteDetailActivity::class.java.simpleName
        private var id = 0
//        private var username = ""
        private lateinit var user: User
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite_detail)
        id = intent.getIntExtra(EXTRA_ID, 0)
//        username = intent.getStringExtra(EXTRA_USERNAME) ?: "arifikhsan"
        initView()
        searchUserByUsername()
        populateView()
    }

    private fun searchUserByUsername() {
        val uriWithId = Uri.parse("$CONTENT_URI/$id")
        val cursor = contentResolver?.query(uriWithId, null, null, null, null)
        if (cursor != null) {
            user = mapCursorToObject(cursor)
            cursor.close()
        }

//        user = userRepository.findByUsername(username)
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
//        userRepository = UserRepository(application)

        fab_fav_open_in_browser.setOnClickListener { view ->
            Snackbar.make(view, "Membuka di browser...", Snackbar.LENGTH_LONG).show()

            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse("https://www.github.com/${user.login}")
            startActivity(intent)
        }
    }
}