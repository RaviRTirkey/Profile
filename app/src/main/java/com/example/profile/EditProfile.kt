package com.example.profile

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.profile.databinding.FragmentEditProfileBinding
import com.example.profile.databinding.FragmentProfileHomeBinding
import java.io.File


class EditProfile : Fragment() {

    private var _binding: FragmentEditProfileBinding? = null
    private val binding get() = _binding!!
    private lateinit var imagePickerLauncher: ActivityResultLauncher<Intent>
    private var selectedImageUri: Uri? = null
    private lateinit var sharedPrefManager: SharedPrefManager
    private val viewModel: ProfileViewModel by activityViewModels()
    private var savedImagePath: String? = null 

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentEditProfileBinding.inflate(inflater, container, false)
        sharedPrefManager = SharedPrefManager(requireContext())
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        imagePickerLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                selectedImageUri = result.data?.data
                selectedImageUri?.let { uri ->
                    val imagePath = copyImageToInternalStorage(uri)
                    if (imagePath != null) {
                        savedImagePath = imagePath
                        binding.profileImageView.setImageURI(Uri.fromFile(File(imagePath)))
                    } else {
                        Toast.makeText(requireContext(), "Failed to copy image", Toast.LENGTH_SHORT).show()
                    }
                }

            }
        }
        binding.changePhotoTV.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            imagePickerLauncher.launch(intent)
        }


        binding.saveButton.setOnClickListener {
            val name = binding.nameEditText.text.toString()
            val memberSince = binding.memberEditText.text.toString()
            val memberSinceText = "member since Dec, $memberSince"
            val uriString = savedImagePath?.toString()

            if (name.isNotBlank() && memberSince.isNotBlank()) {

                viewModel.updateProfile(name, memberSinceText, uriString)

                sharedPrefManager.saveProfile(name, memberSinceText, uriString)

                Toast.makeText(requireContext(), "Saved: $name, Since: $memberSinceText", Toast.LENGTH_SHORT).show()
                findNavController().popBackStack()
            } else {
                Toast.makeText(requireContext(), "Please fill in all fields", Toast.LENGTH_SHORT).show()
            }
        }


        binding.changePhotoTV.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            imagePickerLauncher.launch(intent)
        }

        binding.backButton.setOnClickListener {
            findNavController().popBackStack()
        }

    }

    private fun copyImageToInternalStorage(uri: Uri): String? {
        return try {
            val inputStream = requireContext().contentResolver.openInputStream(uri)
            val fileName = "profile_image_${System.currentTimeMillis()}.jpg"
            val file = File(requireContext().filesDir, fileName)
            inputStream?.use { input ->
                file.outputStream().use { output ->
                    input.copyTo(output)
                }
            }
            file.absolutePath
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }


}