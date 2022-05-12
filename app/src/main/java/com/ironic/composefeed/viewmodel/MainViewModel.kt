package com.ironic.composefeed.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ironic.composefeed.api.NewsService
import com.ironic.composefeed.api.RetrofitInstance
import com.ironic.composefeed.model.News
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    private val newsService: NewsService = RetrofitInstance.api

    private val _loadingState = MutableLiveData(false)
    val loadingState: LiveData<Boolean> get() = _loadingState

    private val _errorState = MutableLiveData(false)
    val errorState: LiveData<Boolean> get() = _errorState

    val postList = mutableStateOf(emptyList<News>())

    fun fetchNews() {
        _loadingState.value = true
        viewModelScope.launch {
            val response = newsService.getHeadlines()
            _loadingState.value = false
            if (response.status.equals("ok", ignoreCase = true)) {
                postList.value = response.articles
            } else {
                _errorState.value = true
            }
        }
    }

}