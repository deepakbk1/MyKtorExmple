package com.example.myktorexmple.ui

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myktorexmple.adapter.PostAdapter
import com.example.myktorexmple.data.util.ApiState
import com.example.myktorexmple.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val mainViewModel: MainViewModel by viewModels()

    @Inject
    lateinit var postAdapter: PostAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initRecyclerview()
        mainViewModel.getPost()
        handleResponse()
    }

    private fun handleResponse() {
        lifecycleScope.launchWhenStarted {
            mainViewModel.apiStateFlow.collect {
                when (it) {
                    is ApiState.Loading -> {
                        binding.apply {
                            progressBar.isVisible = true
                            recyclerview.isVisible = false
                            error.isVisible = false
                        }
                    }
                    is ApiState.Success -> {
                        binding.apply {
                            progressBar.isVisible = false
                            recyclerview.isVisible = true
                            error.isVisible = false
                        }
                        Log.d("main", "handleResponse: ${it.response}")
                        postAdapter.submitList(it.response)
                    }
                    is ApiState.Failure -> {
                        binding.apply {
                            progressBar.isVisible = false
                            recyclerview.isVisible = false
                            error.isVisible = true
                            error.text = it.error.toString()
                        }

                    }
                    is ApiState.Empty -> {

                    }
                }
            }
        }
    }

    private fun initRecyclerview() {
        binding.apply {
            recyclerview.apply {
                setHasFixedSize(true)
                layoutManager = LinearLayoutManager(this@MainActivity)
                adapter = postAdapter
            }
        }
    }
}