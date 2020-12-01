package com.arifikhsan.githubfavorite.ui.detail

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.arifikhsan.githubfavorite.R
import com.arifikhsan.githubfavorite.entity.UserEntity
import kotlinx.android.synthetic.main.item_list_user.view.*

class UserFollowAdapter(private val listUserEntity: ArrayList<UserEntity>) :
    RecyclerView.Adapter<UserFollowAdapter.FollowViewHolder>() {

    class FollowViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        @SuppressLint("SetTextI18n")
        fun bind(userEntity: UserEntity) {
            with(itemView) {
                tv_item_username.text = userEntity.login
                tv_item_name.text = "@${userEntity.login} - ${userEntity.type}"
                Glide.with(itemView.context).load(userEntity.avatarUrl)
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
        holder.bind(listUserEntity[position])
    }

    override fun getItemCount(): Int = listUserEntity.size
}