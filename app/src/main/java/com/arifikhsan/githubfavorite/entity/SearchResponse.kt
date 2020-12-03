package com.arifikhsan.githubfavorite.entity

data class SearchResponse(
    val totalCount: Int,
    val incompleteResults: Boolean,
    val items: ArrayList<User>
)