package com.example.kotlincoroutinesdemo

import kotlinx.coroutines.delay

class UserRepository {

    suspend fun getUsersList(): List<User> {
        delay(5000)
        val users: List<User> = listOf(
            User(1001, "Manish"),
            User(1004, "Vikash"),
            User(1002, "Rakesh"),
            User(1003, "Virat")
        )
        return users
    }
}