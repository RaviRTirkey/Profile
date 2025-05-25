package com.example.profile

import android.content.Context
import android.net.Uri

class SharedPrefManager(context: Context) {
    private val prefs = context.getSharedPreferences("profile_prefs", Context.MODE_PRIVATE)

    fun saveProfile(name: String, memberSince: String, imageUri: String?) {
        prefs.edit().apply {
            putString("name", name)
            putString("memberSince", memberSince)
            putString("imageUri", imageUri)
            apply()
        }
    }

    fun getName() = prefs.getString("name", null) ?: "username"
    fun getMemberSince() = prefs.getString("memberSince", null) ?: "member since Dec, 2020"
    fun getImageUri() = prefs.getString("imageUri", null)
}
