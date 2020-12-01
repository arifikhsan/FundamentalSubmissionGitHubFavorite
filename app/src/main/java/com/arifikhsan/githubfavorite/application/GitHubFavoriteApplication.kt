package com.arifikhsan.githubfavorite.application

import android.app.Application
import com.arifikhsan.githubfavorite.database.AppDatabase
import com.arifikhsan.githubfavorite.repository.UserRepository

class GitHubFavoriteApplication : Application() {

    private val database by lazy { AppDatabase.getDatabase(this) }

    val repository by lazy { UserRepository(database.userDao()) }
}