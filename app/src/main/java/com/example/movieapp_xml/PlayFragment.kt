package com.example.movieapp_xml

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.movieapp_xml.databinding.FragmentPlayBinding
import com.example.movieapp_xml.model.RetrofitClient
import com.example.movieapp_xml.view.MovieAdapter // Có thể dùng lại hoặc tạo Adapter mới
import com.example.movieapp_xml.viewmodel.TrendingViewModel
import com.example.movieapp_xml.viewmodel.TrendingViewModelFactory
import com.google.android.material.tabs.TabLayout

class PlayFragment : Fragment() {
    private lateinit var binding: FragmentPlayBinding
    private lateinit var viewModel: TrendingViewModel
    private lateinit var adapter: MovieAdapter
    private val apiKey = "YOUR_API_KEY" // Thay bằng key của bạn

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentPlayBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Khởi tạo ViewModel (Sử dụng chung TrendingViewModel hoặc tạo mới tùy cấu trúc project)
        val apiService = RetrofitClient.instance
        val factory = TrendingViewModelFactory(apiService)
        viewModel = ViewModelProvider(this, factory)[TrendingViewModel::class.java]

        setupRecyclerView()
        setupTabLayout()

        // Mặc định tải "Now Playing"
        viewModel.fetchTrendingMovies(apiKey)
    }

    private fun setupRecyclerView() {
        adapter = MovieAdapter { movie ->
            // Xử lý click xem chi tiết
        }
        binding.rvPlayMovies.adapter = adapter

        viewModel.trendingMovies.observe(viewLifecycleOwner) { movies ->
            adapter.submitList(movies)
        }
    }

    private fun setupTabLayout() {
        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                when (tab?.position) {
                    0 -> {
                        updateTheme("#6C63FF")
                        // viewModel.fetchNowPlaying(apiKey) // Gọi hàm tương ứng trong ViewModel
                    }
                    1 -> {
                        updateTheme("#FF4D86")
                        // viewModel.fetchPopular(apiKey)
                    }
                    2 -> {
                        updateTheme("#2ED3B7")
                        // viewModel.fetchTopRated(apiKey)
                    }
                    3 -> {
                        updateTheme("#FFFFC107")
                        // viewModel.fetchUpcoming(apiKey)
                    }
                }
            }
            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })
    }

    private fun updateTheme(colorStr: String) {
        val color = Color.parseColor(colorStr)
        binding.headerBackground.setBackgroundColor(color)
        binding.tabLayout.setSelectedTabIndicatorColor(Color.WHITE)
    }
}