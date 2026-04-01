package com.example.movieapp_xml

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import coil.load
import com.example.movieapp_xml.databinding.FragmentDetailTrendingBinding

// Trong DetailTrendingFragment.kt
class DetailTrendingFragment : Fragment() {
    private var _binding: FragmentDetailTrendingBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailTrendingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Nhận dữ liệu từ arguments
        arguments?.let {
            binding.tvTitle.text = it.getString("title")
            binding.tvOverview.text = it.getString("overview")
            binding.tvVoteAverage.text = it.getString("vote")
            binding.tvReleaseDate.text = "Release date: ${it.getString("date")}"

            // Load ảnh backdrop bằng Coil
            val backdropUrl = "https://image.tmdb.org/t/p/w780${it.getString("backdrop")}"
            binding.ivBackdrop.load(backdropUrl)
        }

        // Xử lý nút quay lại
        binding.btnBack.setOnClickListener {
            parentFragmentManager.popBackStack()
            // Hiện lại RecyclerView trong MainActivity nếu cần
            (activity as? MainActivity)?.findViewById<View>(R.id.rvTrending)?.visibility = View.VISIBLE
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(title: String, overview: String, backdrop: String, vote: String, date: String) =
            DetailTrendingFragment().apply {
                arguments = Bundle().apply {
                    putString("title", title)
                    putString("overview", overview)
                    putString("backdrop", backdrop)
                    putString("vote", vote)
                    putString("date", date)
                }
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}