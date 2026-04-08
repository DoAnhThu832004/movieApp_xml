package com.example.movieapp_xml

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import com.example.movieapp_xml.databinding.FragmentDetailCollectionBinding
import com.example.movieapp_xml.model.RetrofitClient
import com.example.movieapp_xml.view.adapter.MovieAdapter
import com.example.movieapp_xml.viewmodel.CollectionViewModel
import com.example.movieapp_xml.viewmodel.CollectionViewModelFactory

class DetailCollectionFragment : Fragment(R.layout.fragment_detail_collection) {
    private lateinit var binding: FragmentDetailCollectionBinding
    private lateinit var viewModel: CollectionViewModel
    private lateinit var partsAdapter: MovieAdapter // Dùng MovieAdapter để hiển thị danh sách phim trong bộ sưu tập

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentDetailCollectionBinding.bind(view)

        // Lấy collection_id từ arguments
        val collectionId = arguments?.getInt("collection_id") ?: return
        val apiKey = "0e7d7148db620788481ce0c35b58fefd"

        setupViewModel()
        setupRecyclerView()
        observeData()

        // Gọi API lấy dữ liệu bộ sưu tập
        viewModel.getCollection(collectionId, apiKey)
    }

    private fun setupViewModel() {
        val apiService = RetrofitClient.apiService
        val factory = CollectionViewModelFactory(apiService)
        viewModel = ViewModelProvider(this, factory)[CollectionViewModel::class.java]
    }

    private fun setupRecyclerView() {
        // Khởi tạo adapter cho danh sách phim thành phần
        partsAdapter = MovieAdapter { movie ->
            // Logic khi click vào một phim trong bộ sưu tập (ví dụ: mở chi tiết phim đó)
        }

        binding.rvCollectionParts.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = partsAdapter
        }
    }

    private fun observeData() {
        viewModel.collection.observe(viewLifecycleOwner) { collection ->
            collection?.let {
                // Đổ dữ liệu lên giao diện bộ sưu tập
                binding.ivCollectionBackdrop.load("https://image.tmdb.org/t/p/w500${it.backdrop_path}")
                binding.tvCollectionName.text = it.name
                binding.tvCollectionOverview.text = it.overview

                // Cập nhật danh sách phim trong bộ sưu tập vào RecyclerView
                partsAdapter.submitList(it.parts)
            }
        }
    }

    companion object {
        // Factory method để tạo Fragment với ID bộ sưu tập cụ thể
        fun newInstance(collectionId: Int) = DetailCollectionFragment().apply {
            arguments = Bundle().apply {
                putInt("collection_id", collectionId)
            }
        }
    }
}