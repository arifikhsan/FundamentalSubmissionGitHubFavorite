package com.arifikhsan.githubfavorite.ui.favorite

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.arifikhsan.githubfavorite.R
import com.arifikhsan.githubfavorite.entity.UserEntity
import kotlinx.android.synthetic.main.item_favorite_user.view.*

class FavoriteAdapter(private val listUserEntity: ArrayList<UserEntity>) :
    RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder>() {

    class FavoriteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(userEntity: UserEntity) {
            with(itemView) {
                tv_name.text = userEntity.login
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        return FavoriteViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_favorite_user, parent, false)
        )
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        holder.bind(listUserEntity[position])
    }

    override fun getItemCount(): Int = listUserEntity.size
}