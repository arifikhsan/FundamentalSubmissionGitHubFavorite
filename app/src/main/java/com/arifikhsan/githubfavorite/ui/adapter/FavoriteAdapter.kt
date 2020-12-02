package com.arifikhsan.githubfavorite.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.arifikhsan.githubfavorite.R
import com.arifikhsan.githubfavorite.entity.User
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.item_favorite_user.view.*
import kotlinx.android.synthetic.main.item_favorite_user.view.img_avatar
import kotlinx.android.synthetic.main.item_list_user.view.*

class FavoriteAdapter(private val listUser: ArrayList<User>) :
    RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder>() {

    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    class FavoriteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        @SuppressLint("SetTextI18n")
        fun bind(user: User) {
            with(itemView) {
                tv_name.text = user.login
                tv_username.text = "@${user.login} - ${user.type}"
                Glide.with(itemView.context).load(user.avatarUrl)
                    .apply(RequestOptions().override(55, 55)).into(img_avatar)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        return FavoriteViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_favorite_user, parent, false)
        )
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        val user = listUser[holder.adapterPosition]

        holder.bind(listUser[position])
        holder.itemView.btn_fav_detail.setOnClickListener {
            onItemClickCallback.onDetailClicked(user)
        }
        holder.itemView.btn_remove_user.setOnClickListener {
            onItemClickCallback.onRemoveFavoriteClicked(it, user)
        }
    }

    override fun getItemCount(): Int = listUser.size

    interface OnItemClickCallback {
        fun onRemoveFavoriteClicked(view: View, user: User)
        fun onDetailClicked(user: User)
    }
}



