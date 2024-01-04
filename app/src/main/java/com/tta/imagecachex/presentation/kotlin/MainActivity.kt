package com.tta.imagecachex.presentation.kotlin

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.tta.imagecachex.databinding.ActivityMainBinding
import com.tta.imagecachex.domain.main.ImageCacheX
import com.tta.imagecachex.domain.util.Resource
import com.tta.imagecachex.presentation.kotlin.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var imageLoader: ImageCacheX

    private lateinit var binding: ActivityMainBinding

    private val mainViewModel by viewModels<MainViewModel>()

    companion object {
        val imageMainAdapter: ImageMainAdapter by lazy { ImageMainAdapter() }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initUI()
        initObservables()
    }

    fun initUI() {
        imageLoader = ImageCacheX.getInstance(this)
        imageMainAdapter.setImageLoader(imageLoader)
        binding.clearBtn.setOnClickListener {
            imageLoader.clearCache()
        }
        binding.mainImageRecyclerView.adapter = imageMainAdapter //mItemAdapter(this, imageList)
        binding.swipeRefreshLayout.setOnRefreshListener {
            stopRefreshingAnimation()
            mainViewModel.getRemoteImageList()
        }
    }

    private fun initObservables() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                mainViewModel.imageList.collect {
                    when (it) {
                        is Resource.Success -> {
                            imageMainAdapter.differ.submitList(it.result)
                        }

                        is Resource.Failure -> {
                            Toast.makeText(this@MainActivity, it.errorMessage, Toast.LENGTH_SHORT).show()
                        }

                        else -> {
                            Log.d("imageList", "loading")
                        }
                    }
                }
            }
        }
    }

    private fun stopRefreshingAnimation() {
        lifecycleScope.launch(Dispatchers.Main) {
            binding.swipeRefreshLayout.isRefreshing = false
        }
    }
}