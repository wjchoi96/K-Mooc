package com.programmers.kmooc.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

abstract class BaseViewModel: ViewModel() {
    /**
     * live data for data
     */
    private val _dataLoading: MutableLiveData<Boolean> = MutableLiveData()
    val dataLoading: LiveData<Boolean> = _dataLoading

    private val _refreshLoading: MutableLiveData<Boolean> = MutableLiveData()
    val refreshLoading: LiveData<Boolean> = _refreshLoading

    /**
     * live data for event
     */
    private val _showToastEvent: MutableLiveData<String> = MutableLiveData()
    val showToastEvent: LiveData<String> = _showToastEvent

    /**
     * data
     */
    private var loading : Boolean = false


    protected fun setDataLoading(loading: Boolean){
        this.loading =  loading
        _dataLoading.value = loading
    }

    protected fun setRefreshLoading(loading: Boolean){
        _refreshLoading.value = loading
    }

    protected fun showToast(message: String){
        _showToastEvent.value = message
    }
}