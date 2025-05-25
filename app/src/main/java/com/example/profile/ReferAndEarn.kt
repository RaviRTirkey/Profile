package com.example.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.profile.databinding.FragmentReferAndEarnBinding
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent


class ReferAndEarn : Fragment() {
    private var _binding: FragmentReferAndEarnBinding? = null
    private val binding get() = _binding!!
    private lateinit var referralCode: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentReferAndEarnBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        referralCode = generateReferralCode()
        binding.referralCodeBox.text = referralCode

        binding.copyCodeBtn.setOnClickListener {
            val clipboard = requireContext().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clip = ClipData.newPlainText("Referral Code", referralCode)
            clipboard.setPrimaryClip(clip)
            Toast.makeText(requireContext(), "Referral code copied!", Toast.LENGTH_SHORT).show()
        }

        binding.inviteBtn.setOnClickListener {
            val shareText = "Join this app using my referral code: $referralCode"
            val intent = Intent(Intent.ACTION_SEND)
            intent.type = "text/plain"
            intent.putExtra(Intent.EXTRA_TEXT, shareText)
            startActivity(Intent.createChooser(intent, "Invite using"))
        }

        binding.backButton.setOnClickListener {
            findNavController().popBackStack()

        }
    }
    fun generateReferralCode(length: Int = 8): String {
        val allowed = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789"
        var code = ""
        for (i in 1..length) {
            code += allowed.random()
        }
        return code
    }



}