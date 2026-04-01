package com.example.movieapp_xml

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.movieapp.view.adapter.MovieAdapter
import com.example.movieapp_xml.databinding.ActivityMainBinding
import com.example.movieapp_xml.model.RetrofitClient
import com.example.movieapp_xml.viewmodel.TrendingViewModel
import com.example.movieapp_xml.viewmodel.TrendingViewModelFactory

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: TrendingViewModel
    private lateinit var movieAdapter: MovieAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        setupViewModel()
        setupRecyclerView()
        setupBottomNavigation()
        observeData()
    }
    // Trong MainActivity.kt
    private fun setupViewModel() {
        val apiService = RetrofitClient.apiService
        val factory = TrendingViewModelFactory(apiService)

        viewModel = ViewModelProvider(this, factory)[TrendingViewModel::class.java]

        // THÊM DÒNG NÀY: Gọi API với key của bạn (ví dụ lấy từ TMDB)
        viewModel.getTrending("0e7d7148db620788481ce0c35b58fefd")
    }
    private fun setupRecyclerView() {
        // Khởi tạo Adapter với sự kiện click
        movieAdapter = MovieAdapter { movie ->
            // Tạo Fragment mới và truyền dữ liệu phim qua arguments
            val detailFragment = DetailTrendingFragment.newInstance(
                movie.title ?: "",
                movie.overview ?: "",
                movie.backdrop_path ?: "",
                movie.vote_average.toString(),
                movie.release_date ?: ""
            )

            // Thực hiện chuyển Fragment
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, detailFragment) // Thay thế nội dung trong container
                .addToBackStack(null) // Cho phép nhấn nút Back để quay lại danh sách
                .commit()

            // (Tùy chọn) Ẩn RecyclerView đi để Fragment chiếm toàn màn hình
            binding.rvTrending.visibility = android.view.View.GONE
        }

        // Cấu hình RecyclerView hiển thị dạng lưới 2 cột
        binding.rvTrending.apply {
            layoutManager = GridLayoutManager(this@MainActivity, 2)
            adapter = movieAdapter
            setHasFixedSize(true)
        }
    }
    private fun observeData() {
        // Lắng nghe dữ liệu từ ViewModel
        viewModel.trendingResponse.observe(this) { movies ->
            if (movies != null) {
                movieAdapter.submitList(movies)
            }
        }

        // Lắng nghe trạng thái lỗi (nếu có)
        viewModel.errorMessage.observe(this) { message ->
            message?.let {
                Toast.makeText(this, it, Toast.LENGTH_LONG).show()
            }
        }
    }
    private fun setupBottomNavigation() {
        binding.bottomNavigation.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_home -> {
                    // Xử lý khi chọn Home
                    binding.rvTrending.visibility = View.VISIBLE
                    binding.fragmentContainer.visibility = View.GONE
                    true
                }

                R.id.nav_play -> {
                    showFragment(PlayFragment())
                    true
                }
                R.id.nav_profile -> {
                    // Khi nhấn Profile: Ẩn danh sách, hiện container và nạp ProfileFragment
                    showFragment(BlankFragment())
                    true
                }
                else -> false
            }
        }
    }
    private fun showFragment(fragment: Fragment) {
        binding.rvTrending.visibility = View.GONE
        binding.fragmentContainer.visibility = View.VISIBLE

        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .addToBackStack(null)
            .commit()
    }
}