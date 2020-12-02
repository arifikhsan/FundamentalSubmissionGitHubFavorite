package com.arifikhsan.githubfavorite.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.arifikhsan.githubfavorite.R
import com.arifikhsan.githubfavorite.entity.User
import com.arifikhsan.githubfavorite.ui.favorite.FavoriteClickListener
import com.arifikhsan.githubfavorite.ui.search.RecyclerViewUserClickListener
import kotlinx.android.synthetic.main.item_favorite_user.view.*

class FavoriteUserAdapter(private val listUser: ArrayList<User>) :
    RecyclerView.Adapter<FavoriteUserAdapter.FavoriteViewHolder>() {

//    var clickListener: RecyclerViewUserClickListener? = null
//    var favoriteClickListener: FavoriteClickListener? = null

    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    class FavoriteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        @SuppressLint("SetTextI18n")
        fun bind(user: User) {
            with(itemView) {
                tv_name.text = user.login
//                Glide.with(itemView.context).load(user.avatarUrl)
//                    .apply(RequestOptions().override(55, 55)).into(img_avatar)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        return FavoriteViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_favorite_user, parent, false)
        )
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        holder.bind(listUser[position])
//        holder.itemView.setOnClickListener {
//            clickListener?.onItemClicked(it, listUser[position])
//        }
//        holder.itemView.btn_remove_user.setOnClickListener {
//            Toast.makeText(holder.itemView.context, "Remove", Toast.LENGTH_SHORT).show()
////            favoriteClickListener?.removeUser(it, listUser[position])
//        }

        holder.itemView.setOnClickListener {
            onItemClickCallback.onItemClicked(listUser[holder.adapterPosition])
        }
    }

    override fun getItemCount(): Int = listUser.size
    
    interface OnItemClickCallback {
        fun onItemClicked(user: User)
    }
}


//private fun Any.setOnClickListener(view: View, user: User) {
//    val favoriteClickListener: FavoriteClickListener? = null
//
//    view.btn_remove_user.setOnClickListener {
//        favoriteClickListener?.removeUser(view, user)
//    }
//}


