package com.example.movieapp_xml

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.movieapp_xml.databinding.FragmentHomeBinding
import com.example.movieapp_xml.model.RetrofitClient
import com.example.movieapp_xml.view.adapter.CharacterAdapter
import com.example.movieapp_xml.view.adapter.MovieAdapter
import com.example.movieapp_xml.viewmodel.*

class HomeFragment : Fragment(R.layout.fragment_home) {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var trendingViewModel: TrendingViewModel
    private lateinit var personViewModel: PersonViewModel
    private lateinit var collectionViewModel: CollectionViewModel // Thêm ViewModel này

    private val apiKey = "0e7d7148db620788481ce0c35b58fefd"

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHomeBinding.bind(view)

        setupViewModels()
        setupRecyclerViews()
        observeData()

        // Gọi các API để lấy dữ liệu
        trendingViewModel.getTrending(apiKey)
        personViewModel.getPerson(apiKey)

        // Ví dụ: Lấy bộ sưu tập "Star Wars" có ID là 10 (Bạn có thể thay đổi ID này)
        collectionViewModel.getCollection(10, apiKey)
    }

    private fun setupViewModels() {
        val apiService = RetrofitClient.apiService

        trendingViewModel = ViewModelProvider(this, TrendingViewModelFactory(apiService))[TrendingViewModel::class.java]
        personViewModel = ViewModelProvider(this, PersonViewModelFactory(apiService))[PersonViewModel::class.java]

        // Khởi tạo CollectionViewModel qua Factory
        collectionViewModel = ViewModelProvider(this, CollectionViewModelFactory(apiService))[CollectionViewModel::class.java]
    }

    // ... các import khác giữ nguyên

    private fun setupRecyclerViews() {
        // 1. Trending Movies -> Mở chi tiết phim
        val movieAdapter = MovieAdapter { movie ->
            val detailFragment = DetailMovieFragment.newInstance(movie.id)
            navigateTo(detailFragment)
        }
        binding.rvTrending.layoutManager = GridLayoutManager(context, 2)
        binding.rvTrending.adapter = movieAdapter

        // 2. Popular Characters -> Mở chi tiết diễn viên
        val characterAdapter = CharacterAdapter { personId ->
            val detailFragment = DetailCharacterFragment.newInstance(personId)
            navigateTo(detailFragment)
        }
        // Thiết kế cuộn ngang cho danh sách diễn viên
        binding.rvPersons.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.rvPersons.adapter = characterAdapter

        // 3. Collections (Danh sách phim trong bộ sưu tập)
        val collectionAdapter = MovieAdapter { movie ->
            val detailFragment = DetailMovieFragment.newInstance(movie.id)
            navigateTo(detailFragment)
        }
        binding.rvCollections.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.rvCollections.adapter = collectionAdapter
    }

    // Hàm bổ trợ để chuyển Fragment
    private fun navigateTo(fragment: Fragment) {
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment) // ID này phải khớp với container trong activity_main.xml
            .addToBackStack(null) // Cho phép ấn nút back quay lại trang Home
            .commit()
    }

    private fun observeData() {
        trendingViewModel.trendingResponse.observe(viewLifecycleOwner) { movies ->
            (binding.rvTrending.adapter as MovieAdapter).submitList(movies)
        }

        personViewModel.person.observe(viewLifecycleOwner) { persons ->
            (binding.rvPersons.adapter as CharacterAdapter).submitList(persons)
        }

        // Quan sát dữ liệu bộ sưu tập và đẩy danh sách phim (parts) vào adapter
        collectionViewModel.collection.observe(viewLifecycleOwner) { collection ->
            collection?.let {
                (binding.rvCollections.adapter as MovieAdapter).submitList(it.parts)
            }
        }
    }
}