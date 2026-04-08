package com.example.movieapp_xml

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import coil.load
import com.example.movieapp_xml.databinding.FragmentDetailCharacterBinding
import com.example.movieapp_xml.model.RetrofitClient // Cần import RetrofitClient
import com.example.movieapp_xml.viewmodel.PersonDetailViewModel
import com.example.movieapp_xml.viewmodel.PersonDetailViewModelFactory // Cần import Factory

class DetailCharacterFragment : Fragment(R.layout.fragment_detail_character) {
    private lateinit var binding: FragmentDetailCharacterBinding
    private lateinit var viewModel: PersonDetailViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentDetailCharacterBinding.bind(view)

        val personId = arguments?.getInt("person_id") ?: return
        val apiKey = "0e7d7148db620788481ce0c35b58fefd"

        // --- PHẦN SỬA LỖI TẠI ĐÂY ---
        // 1. Lấy ApiService từ RetrofitClient
        val apiService = RetrofitClient.apiService

        // 2. Khởi tạo Factory với ApiService vừa lấy được
        val factory = PersonDetailViewModelFactory(apiService)

        // 3. Truyền factory vào ViewModelProvider để hệ thống biết cách tạo ViewModel
        viewModel = ViewModelProvider(this, factory)[PersonDetailViewModel::class.java]
        // ----------------------------

        viewModel.getPersonDetail(apiKey, personId)

        observeData()

        binding.btnBackChar.setOnClickListener { parentFragmentManager.popBackStack() }
    }

    private fun observeData() {
        viewModel.personDetail.observe(viewLifecycleOwner) { detail ->
            detail?.let {
                binding.ivProfile.load("https://image.tmdb.org/t/p/w500${it.profile_path}")
                binding.tvCharName.text = it.name
                binding.tvBirthday.text = "Birthday: ${it.birthday ?: "N/A"}"
                binding.tvBiography.text = it.biography
            }
        }
    }

    companion object {
        fun newInstance(personId: Int) = DetailCharacterFragment().apply {
            arguments = Bundle().apply { putInt("person_id", personId) }
        }
    }
}