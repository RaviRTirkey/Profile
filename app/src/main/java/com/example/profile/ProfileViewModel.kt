package com.example.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ProfileViewModel : ViewModel() {
    private val _name = MutableLiveData<String>()
    val name: LiveData<String> get() = _name

    private val _memberSince = MutableLiveData<String>()
    val memberSince: LiveData<String> get() = _memberSince

    private val _imageUri = MutableLiveData<String?>()
    val imageUri: LiveData<String?> get() = _imageUri

    fun updateProfile(name: String, memberSince: String, imageUri: String?) {
        _name.value = name
        _memberSince.value = memberSince
        _imageUri.value = imageUri
    }
}
