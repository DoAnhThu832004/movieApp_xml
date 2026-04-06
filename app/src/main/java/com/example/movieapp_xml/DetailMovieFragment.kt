package com.example.movieapp_xml

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import coil.load
import com.example.movieapp_xml.databinding.FragmentDetailMovieBinding
import com.example.movieapp_xml.viewmodel.MovieViewModel

class DetailMovieFragment : Fragment(R.layout.fragment_detail_movie) {
    private lateinit var binding: FragmentDetailMovieBinding
    private lateinit var viewModel: MovieViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentDetailMovieBinding.bind(view)

        val movieId = arguments?.getInt("movie_id") ?: return
        val apiKey = "0e7d7148db620788481ce0c35b58fefd" // Key từ MainActivity

        // Thiết lập ViewModel (Giả định bạn đã có MovieViewModel bản XML)
        viewModel = ViewModelProvider(this)[MovieViewModel::class.java]
        viewModel.getMovieDetail(movieId, apiKey)

        observeData()

        binding.btnBack.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
    }

    private fun observeData() {
        viewModel.movies.observe(viewLifecycleOwner) { movie ->
            movie?.let {
                binding.ivBackdrop.load("https://image.tmdb.org/t/p/w500${it.backdrop_path}")
                binding.tvTitle.text = it.title
                binding.tvOverview.text = it.overview
                binding.tvRating.text = String.format("%.1f", it.vote_average)
                // Logic cho Genres RecyclerView ở đây
            }
        }
    }

    companion object {
        fun newInstance(movieId: Int) = DetailMovieFragment().apply {
            arguments = Bundle().apply { putInt("movie_id", movieId) }
        }
    }
}