package com.arifikhsan.githubfavorite.ui.detail

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.arifikhsan.githubfavorite.R
import com.arifikhsan.githubfavorite.model.User
import kotlinx.android.synthetic.main.item_list_user.view.*

class UserFollowAdapter(private val listUser: ArrayList<User>) :
    RecyclerView.Adapter<UserFollowAdapter.FollowViewHolder>() {
    class FollowViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        @SuppressLint("SetTextI18n")
        fun bind(user: User) {
            with(itemView) {
                tv_item_username.text = user.login
                tv_item_name.text = "@${user.login} - ${user.type}"
                Glide.with(itemView.context).load(user.avatarUrl)
                    .apply(RequestOptions().override(55, 55)).into(img_avatar)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FollowViewHolder {
        return FollowViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_list_user, parent, false)
        )
    }

    override fun onBindViewHolder(holder: FollowViewHolder, position: Int) {
        holder.bind(listUser[position])
    }

    override fun getItemCount(): Int = listUser.size
}