package com.example.movieapp_xml

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
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
        observeData()
    }
    private fun setupViewModel() {
        // Tạo ApiService (Bạn cần đảm bảo RetrofitClient đã được viết sẵn)
        val apiService = RetrofitClient.apiService
        val factory = TrendingViewModelFactory(apiService)

        viewModel = ViewModelProvider(this, factory)[TrendingViewModel::class.java]
    }
    private fun setupRecyclerView() {
        // Khởi tạo Adapter với sự kiện click
        movieAdapter = MovieAdapter { movie ->
            Toast.makeText(this, "Bạn chọn: ${movie.title}", Toast.LENGTH_SHORT).show()
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
}