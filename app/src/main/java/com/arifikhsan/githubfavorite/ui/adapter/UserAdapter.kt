package com.arifikhsan.githubfavorite.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.arifikhsan.githubfavorite.R
import com.arifikhsan.githubfavorite.entity.User
import com.arifikhsan.githubfavorite.ui.search.RecyclerViewUserClickListener
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.item_list_user.view.*

class UserAdapter(private val listUser: ArrayList<User>) :
    RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        return UserViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_list_user, parent, false)
        )
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user = listUser[holder.adapterPosition]

        holder.bind(listUser[position])
        holder.itemView.btn_goto_detail.setOnClickListener {
            onItemClickCallback.onDetailClicked(user)
        }
        holder.itemView.btn_add_fav.setOnClickListener {
            onItemClickCallback.onAddFavoriteClicked(it, user)
        }
    }

    override fun getItemCount(): Int = listUser.size

    interface OnItemClickCallback {
        fun onDetailClicked(user: User)
        fun onAddFavoriteClicked(view: View, user: User)
    }
}

