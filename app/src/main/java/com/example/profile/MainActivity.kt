package com.example.profile

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.profile.databinding.ActivityMainBinding
import java.io.File

class MainActivity : AppCompatActivity() {
    private lateinit var sharedPrefManager: SharedPrefManager
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        sharedPrefManager = SharedPrefManager(this)
        binding.profileImageView.setImageResource(R.drawable.ic_profile)
        binding.userNameTextView.text = "Your Name"
        binding.memberSinceTextView.text = "Member since 2025"

        binding.buttonProfile.setOnClickListener {
            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
        }


    }

    override fun onResume() {
        super.onResume()
        loadProfileData()
    }
    private fun loadProfileData() {
        val name = sharedPrefManager.getName()
        val memberSince = sharedPrefManager.getMemberSince()
        val imagePath = sharedPrefManager.getImageUri()

        binding.userNameTextView.text = name
        binding.memberSinceTextView.text = memberSince

        if (!imagePath.isNullOrEmpty()) {
            val file = File(imagePath)
            if (file.exists()) {
                binding.profileImageView.setImageURI(Uri.fromFile(file))
            }
        }
    }


}