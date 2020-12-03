package com.arifikhsan.githubfavorite.ui.detail

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.arifikhsan.githubfavorite.R
import com.arifikhsan.githubfavorite.config.Constant.CONTENT_URI
import com.arifikhsan.githubfavorite.entity.User
import com.arifikhsan.githubfavorite.helper.MappingHelper.mapUserToContentValues
import com.arifikhsan.githubfavorite.repository.UserRepository
import com.arifikhsan.githubfavorite.ui.adapter.UserAdapter
import com.google.android.material.snackbar.Snackbar
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import kotlinx.android.synthetic.main.fragment_detail.*
import org.json.JSONArray

/**
 * A placeholder fragment containing a simple view.
 */
class UserFollowFragment : Fragment(), FragmentScrollInterface {

    private val users = ArrayList<User>()
    private lateinit var username: String
    private var tabNumber = 1
    private lateinit var userRepository: UserRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        tabNumber = arguments?.getInt(ARG_SECTION_NUMBER) ?: 0
        username = arguments?.getString(ARG_SECTION_USERNAME) ?: "arifikhsan"
        Log.d(TAG, "number: $tabNumber")
        Log.d(TAG, "username: $username")
        activity?.let {
            userRepository = UserRepository(it.application)
        }
        populateData()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_detail, container, false)
    }

    private fun populateData() {
        val client = AsyncHttpClient()
        client.addHeader("Authorization", "df97872248fb3eecacba97569ad7156b9674c9df")
        client.addHeader("User-Agent", "request")

        val userFollow = if (tabNumber == 1) "followers" else "following"
        client.get(
            "https://api.github.com/users/$username/$userFollow",
            object : AsyncHttpResponseHandler() {
                override fun onSuccess(
                    statusCode: Int,
                    headers: Array<out Header>?,
                    responseBody: ByteArray
                ) {
                    val result = String(responseBody)

                    val items = JSONArray(result)
                    for (i in 0 until items.length()) {
                        val userObject = items.getJSONObject(i)
                        val user = User(
                            id = userObject.getInt("id"),
                            login = userObject.getString("login"),
                            name = userObject.getString("login"),
                            avatarUrl = userObject.getString("avatar_url"),
                            type = userObject.getString("type")
                        )
                        users.add(user)
                    }
                    showUserFollow()
                }

                override fun onFailure(
                    statusCode: Int,
                    headers: Array<out Header>?,
                    responseBody: ByteArray?,
                    error: Throwable?
                ) {
                    Log.d(TAG, "onFailure: $statusCode | ${error?.message}")
                    error?.printStackTrace()
                }
            })
    }

    private fun showUserFollow() {
        val adapter = UserAdapter(users)
        rv_user_follow.layoutManager = LinearLayoutManager(this.context)
        rv_user_follow.adapter = adapter

        adapter.setItemClickCallback(object : UserAdapter.OnItemClickCallback {
            override fun onDetailClicked(user: User) {
                val intent = Intent(context, DetailActivity::class.java)
                intent.putExtra(DetailActivity.EXTRA_USERNAME, user.login)
                startActivity(intent)
            }

            override fun onAddFavoriteClicked(view: View, user: User) {
                activity?.contentResolver?.insert(CONTENT_URI, mapUserToContentValues(user))
//                userRepository.insert(user)
                Snackbar.make(view, "Berhasil menambahkan ke favorit", Snackbar.LENGTH_LONG).show()
            }
        })
    }

    companion object {
        private val TAG = UserFollowFragment::class.java.simpleName
        private const val ARG_SECTION_NUMBER = "section_number"
        private const val ARG_SECTION_USERNAME = "section_username"

        @JvmStatic
        fun newInstance(sectionNumber: Int, sectionUsername: String): UserFollowFragment {
            return UserFollowFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_SECTION_NUMBER, sectionNumber)
                    putString(ARG_SECTION_USERNAME, sectionUsername)
                }
            }
        }
    }

    override fun fragmentBecameVisible() {
        val apa = arguments?.getInt(ARG_SECTION_NUMBER) ?: 1
        val username = arguments?.getString(ARG_SECTION_USERNAME) ?: "arifikhsan"
        Log.d(TAG, "fragmentBecameVisible: $apa")
        Log.d(TAG, "fragmentBecameVisible: $username")
    }
}