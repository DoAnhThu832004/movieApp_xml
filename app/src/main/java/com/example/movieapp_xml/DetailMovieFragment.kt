package com.example.movieapp_xml

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import coil.load
import com.example.movieapp_xml.databinding.FragmentDetailMovieBinding
import com.example.movieapp_xml.viewmodel.MovieViewModel
import androidx.recyclerview.widget.LinearLayoutManager

class DetailMovieFragment : Fragment(R.layout.fragment_detail_movie) {
    private lateinit var binding: FragmentDetailMovieBinding
    private lateinit var viewModel: MovieViewModel
    // Bạn cần tạo thêm GenreAdapter tương tự MovieAdapter để hiển thị list này

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentDetailMovieBinding.bind(view)

        val movieId = arguments?.getInt("movie_id") ?: return
        val apiKey = "0e7d7148db620788481ce0c35b58fefd"

        viewModel = ViewModelProvider(this)[MovieViewModel::class.java]
        viewModel.getMovieDetail(movieId, apiKey)

        // Cấu hình RecyclerView cho Genres (cuộn ngang giống Compose)
        binding.rvGenres.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        observeData()
        binding.btnBack.setOnClickListener { parentFragmentManager.popBackStack() }
    }

    private fun observeData() {
        viewModel.movies.observe(viewLifecycleOwner) { movie ->
            movie?.let {
                // ... (Đổ dữ liệu title, overview, rating như cũ) ...
                binding.tvTitle.text = it.title
                binding.tvOverview.text = it.overview
                binding.tvRating.text = String.format("%.1f", it.vote_average)
                binding.ivBackdrop.load("https://image.tmdb.org/t/p/w500${it.backdrop_path}")

                // XỬ LÝ PHẦN COLLECTION
                if (it.belongs_to_collection != null) {
                    // Hiển thị UI bộ sưu tập
                    binding.tvCollectionHeader.visibility = View.VISIBLE
                    binding.cvCollection.visibility = View.VISIBLE

                    // Đổ dữ liệu bộ sưu tập
                    binding.tvCollectionNameBanner.text = it.belongs_to_collection.name
                    binding.ivCollectionBanner.load("https://image.tmdb.org/t/p/w500${it.belongs_to_collection.backdrop_path}")

                    // Sự kiện nhấn để mở DetailCollectionFragment
                    binding.cvCollection.setOnClickListener { _ ->
                        val collectionFragment = DetailCollectionFragment.newInstance(it.belongs_to_collection.id)
                        parentFragmentManager.beginTransaction()
                            .replace(R.id.fragment_container, collectionFragment) // Dùng container của MainActivity
                            .addToBackStack(null)
                            .commit()
                    }
                } else {
                    // Ẩn nếu không có bộ sưu tập
                    binding.tvCollectionHeader.visibility = View.GONE
                    binding.cvCollection.visibility = View.GONE
                }
            }
        }
    }

    companion object {
        fun newInstance(movieId: Int) = DetailMovieFragment().apply {
            arguments = Bundle().apply { putInt("movie_id", movieId) }
        }
    }
}