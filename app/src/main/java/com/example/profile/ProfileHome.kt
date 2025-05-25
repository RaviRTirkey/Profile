package com.example.profile

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.profile.databinding.FragmentProfileHomeBinding
import kotlin.getValue
import androidx.core.net.toUri
import androidx.navigation.fragment.findNavController
import java.io.File


class ProfileHome : Fragment() {
    private var _binding: FragmentProfileHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var sharedPrefManager: SharedPrefManager
    private val viewModel: ProfileViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProfileHomeBinding.inflate(inflater, container, false)
        sharedPrefManager = SharedPrefManager(requireContext())
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loadProfileData()
        buttonClick()


    }

    private fun loadProfileData() {
        val name = sharedPrefManager.getName()
        val memberSince = sharedPrefManager.getMemberSince()
        val imagePath = sharedPrefManager.getImageUri()

        binding.userNameTV.text = name
        binding.userDetailsTV.text = memberSince

        if (!imagePath.isNullOrEmpty()) {
            val file = File(imagePath)
            if (file.exists()) {
                binding.profilePic.setImageURI(Uri.fromFile(file))
            }
        }
    }

    fun buttonClick()
    {
        binding.backButton.setOnClickListener {
            activity?.finish()
        }
        binding.supportButton.setOnClickListener {
            val url = "https://www.google.com"
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = url.toUri()
            startActivity(intent)
        }
        binding.editButton.setOnClickListener {
            findNavController().navigate(R.id.action_profileHome_to_editProfile)
        }

        binding.CREDgarageBtn.setOnClickListener {
            val url = "https://cred.club/garage"
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = url.toUri()
            startActivity(intent)
        }

        val navigate = {
            findNavController().navigate(R.id.action_profileHome_to_scoreAndBalance)
        }

        binding.creditScoreLayout.setOnClickListener {navigate()}
        binding.cashbackLayout.setOnClickListener {navigate()}
        binding.bankBalanceLayout.setOnClickListener {navigate()}
        binding.cashbackForward.setOnClickListener {navigate()}
        binding.coinsForward.setOnClickListener {navigate()}
        binding.transactionForward.setOnClickListener {navigate()}

        binding.referAndEarnForward.setOnClickListener {
            findNavController().navigate(R.id.action_profileHome_to_referAndEarn)
        }

    }

}