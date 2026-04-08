package com.example.movieapp_xml

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.movieapp_xml.view.adapter.MovieAdapter
import com.example.movieapp_xml.databinding.FragmentPlayBinding
import com.example.movieapp_xml.model.RetrofitClient
import com.example.movieapp_xml.model.Trending
import com.example.movieapp_xml.viewmodel.*
import com.google.android.material.tabs.TabLayout

class PlayFragment : Fragment() {
    private var _binding: FragmentPlayBinding? = null
    private val binding get() = _binding!!

    // Khai báo các ViewModel cho từng loại danh sách
    private lateinit var nowPlayingViewModel: NowPlayingViewModel
    private lateinit var popularViewModel: PopularViewModel
    private lateinit var topRatedViewModel: TopRatedViewModel
    private lateinit var upcomingViewModel: UpcomingViewModel

    private lateinit var movieAdapter: MovieAdapter
    private val apiKey = "0e7d7148db620788481ce0c35b58fefd" // Lấy từ MainActivity của bạn

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPlayBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViewModels()
        setupRecyclerView()
        setupTabLayout()
        observeData()

        // Mặc định tải trang "Now Playing" khi vào fragment
        nowPlayingViewModel.getNowPlaying(apiKey)
    }

    private fun setupViewModels() {
        val apiService = RetrofitClient.apiService

        // Khởi tạo các ViewModel thông qua Factory tương ứng
        nowPlayingViewModel = ViewModelProvider(this, NowPlayingViewModelFactory(apiService))[NowPlayingViewModel::class.java]
        popularViewModel = ViewModelProvider(this, PopularViewModelFactory(apiService))[PopularViewModel::class.java]
        topRatedViewModel = ViewModelProvider(this, TopRatedViewModelFactory(apiService))[TopRatedViewModel::class.java]
        upcomingViewModel = ViewModelProvider(this, UpcomingViewModelFactory(apiService))[UpcomingViewModel::class.java]
    }

    private fun setupRecyclerView() {
        movieAdapter = MovieAdapter { movie ->
            // Xử lý khi click vào phim (có thể mở DetailFragment tương tự MainActivity)
        }

        binding.rvPlayMovies.apply {
            // Sử dụng GridLayoutManager 2 cột để đồng bộ với giao diện chung
            layoutManager = GridLayoutManager(requireContext(), 2)
            adapter = movieAdapter
        }
    }

    private fun setupTabLayout() {
        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                when (tab?.position) {
                    0 -> {
                        updateTheme("#6C63FF")
                        nowPlayingViewModel.getNowPlaying(apiKey)
                    }
                    1 -> {
                        updateTheme("#FF4D86")
                        popularViewModel.getPopular(apiKey)
                    }
                    2 -> {
                        updateTheme("#2ED3B7")
                        topRatedViewModel.getTopRated(apiKey)
                    }
                    3 -> {
                        updateTheme("#FFFFC107")
                        upcomingViewModel.getUpComing(apiKey)
                    }
                }
            }
            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })
    }

    private fun observeData() {
        // Quan sát dữ liệu từ Now Playing và chuyển đổi sang list Trending để Adapter nhận diện
        nowPlayingViewModel.nowPlaying.observe(viewLifecycleOwner) { list ->
            val mappedList = list.map {
                Trending(it.adult, it.backdrop_path, it.id, it.title, it.original_title,
                    it.overview, it.poster_path, "", it.original_language,
                    it.genre_ids, it.popularity, it.release_date, it.video, it.vote_average, it.vote_count)
            }
            movieAdapter.submitList(mappedList)
        }

        // Quan sát Popular
        popularViewModel.popular.observe(viewLifecycleOwner) { list ->
            val mappedList = list.map {
                Trending(it.adult, it.backdrop_path, it.id, it.title, it.original_title,
                    it.overview, it.poster_path, "", it.original_language,
                    it.genre_ids, it.popularity, it.release_date, it.video, it.vote_average, it.vote_count)
            }
            movieAdapter.submitList(mappedList)
        }

        // Tương tự cho Top Rated và Upcoming...
        topRatedViewModel.topRated.observe(viewLifecycleOwner) { list ->
            val mappedList = list.map {
                Trending(it.adult, it.backdrop_path, it.id, it.title, it.original_title,
                    it.overview, it.poster_path, "", it.original_language,
                    it.genre_ids, it.popularity, it.release_date, it.video, it.vote_average, it.vote_count)
            }
            movieAdapter.submitList(mappedList)
        }

        upcomingViewModel.upComing.observe(viewLifecycleOwner) { list ->
            val mappedList = list.map {
                Trending(it.adult, it.backdrop_path, it.id, it.title, it.original_title,
                    it.overview, it.poster_path, "", it.original_language,
                    it.genre_ids, it.popularity, it.release_date, it.video, it.vote_average, it.vote_count)
            }
            movieAdapter.submitList(mappedList)
        }
    }

    private fun updateTheme(colorStr: String) {
        val color = Color.parseColor(colorStr)
        binding.headerBackground.setBackgroundColor(color)
        binding.tabLayout.setSelectedTabIndicatorColor(Color.WHITE)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}