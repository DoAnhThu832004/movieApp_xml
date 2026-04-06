package com.example.movieapp_xml

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import coil.load
import com.example.movieapp_xml.databinding.FragmentDetailCharacterBinding
import com.example.movieapp_xml.viewmodel.PersonDetailViewModel

class DetailCharacterFragment : Fragment(R.layout.fragment_detail_character) {
    private lateinit var binding: FragmentDetailCharacterBinding
    private lateinit var viewModel: PersonDetailViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentDetailCharacterBinding.bind(view)

        val personId = arguments?.getInt("person_id") ?: return

        viewModel = ViewModelProvider(this)[PersonDetailViewModel::class.java]
        viewModel.getPersonDetail("0e7d7148db620788481ce0c35b58fefd", personId)

        viewModel.personDetail.observe(viewLifecycleOwner) { detail ->
            detail?.let {
                binding.ivProfile.load("https://image.tmdb.org/t/p/w500${it.profile_path}")
                binding.tvCharName.text = it.name
                binding.tvBirthday.text = "Birthday: ${it.birthday ?: "N/A"}"
                binding.tvBiography.text = it.biography
            }
        }

        binding.btnBackChar.setOnClickListener { parentFragmentManager.popBackStack() }
    }

    companion object {
        fun newInstance(personId: Int) = DetailCharacterFragment().apply {
            arguments = Bundle().apply { putInt("person_id", personId) }
        }
    }
}