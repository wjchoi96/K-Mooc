package com.programmers.kmooc.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.programmers.kmooc.models.Lecture
import com.programmers.kmooc.repositories.KmoocRepository


class KmoocDetailViewModel(private val repository: KmoocRepository) : BaseViewModel() {
    /**
     * live data for data
     */
    private val _lecture: MutableLiveData<Lecture> = MutableLiveData()
    val lecture: LiveData<Lecture> = _lecture

    /**
     * live data for event
     */
    private val _finishViewEvent: MutableLiveData<String> = MutableLiveData()
    val finishViewEvent: LiveData<String> = _finishViewEvent

    fun fetchDetail(courseId: String?) {
        if(courseId.isNullOrBlank()){
            _finishViewEvent.value = "정보가 유실되었습니다"
            return
        }
        if(loading){
            setRefreshLoading(false)
            return
        }
        setDataLoading(true)
        repository.detail(courseId) {
            setDataLoading(false)
            _lecture.postValue(it)
        }
    }

    fun backClick(){
        _finishViewEvent.value = ""
    }
}

class KmoocDetailViewModelFactory(private val repository: KmoocRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(KmoocDetailViewModel::class.java)) {
            return KmoocDetailViewModel(repository) as T
        }
        throw IllegalAccessException("Unkown Viewmodel Class")
    }
}