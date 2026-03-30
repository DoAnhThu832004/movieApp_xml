package com.example.movieapp.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.movieapp_xml.databinding.ItemMovieBinding
import com.example.movieapp_xml.model.Trending

class MovieAdapter(private val onMovieClick: (Trending) -> Unit) :
    ListAdapter<Trending, MovieAdapter.MovieViewHolder>(MovieDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding = ItemMovieBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return MovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = getItem(position)
        holder.bind(movie, onMovieClick)
    }

    class MovieViewHolder(private val binding: ItemMovieBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(movie: Trending, onMovieClick: (Trending) -> Unit) {
            // Đổ dữ liệu text
            binding.movieTitle.text = movie.title ?: movie.title ?: "Unknown"
            binding.movieRating.text = String.format("%.1f", movie.vote_average)

            // Load ảnh bằng thư viện Coil (tương tự AsyncImage trong Compose)
            val posterUrl = "https://image.tmdb.org/t/p/w500${movie.poster_path}"
            binding.moviePoster.load(posterUrl) {
                crossfade(true)
                // placeholder(R.drawable.loading_img) // Có thể thêm ảnh chờ
            }

            // Xử lý sự kiện click
            binding.root.setOnClickListener {
                onMovieClick(movie)
            }
        }
    }

    // DiffUtil giúp RecyclerView chỉ cập nhật những item có thay đổi, tối ưu hiệu năng
    class MovieDiffCallback : DiffUtil.ItemCallback<Trending>() {
        override fun areItemsTheSame(oldItem: Trending, newItem: Trending): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Trending, newItem: Trending): Boolean {
            return oldItem == newItem
        }
    }
}