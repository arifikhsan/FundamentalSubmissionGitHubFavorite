package com.arifikhsan.githubfavorite.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.arifikhsan.githubfavorite.R
import com.arifikhsan.githubfavorite.entity.User
import kotlinx.android.synthetic.main.item_favorite_user.view.*

class FavoriteUserAdapter(private val listUser: ArrayList<User>) :
    RecyclerView.Adapter<FavoriteUserAdapter.FavoriteViewHolder>() {


    private lateinit var onItemClickCallback: OnItemClickCallback
    private lateinit var onDetailClickCallback: OnDetailClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    fun setOnDetailClickCallback(onDetailClickCallback: OnDetailClickCallback) {
        this.onDetailClickCallback = onDetailClickCallback
    }

    class FavoriteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        @SuppressLint("SetTextI18n")
        fun bind(user: User) {
            with(itemView) {
                tv_name.text = user.login
                tv_username.text = "@${user.login} - ${user.type}"
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
            onDetailClickCallback.onDetailClicked(user)
        }
        holder.itemView.btn_remove_user.setOnClickListener {
            onItemClickCallback.onItemClicked(user)
        }
    }

    override fun getItemCount(): Int = listUser.size

    interface OnItemClickCallback {
        fun onItemClicked(user: User)
    }

    interface OnDetailClickCallback {
        fun onDetailClicked(user: User)
    }
}



