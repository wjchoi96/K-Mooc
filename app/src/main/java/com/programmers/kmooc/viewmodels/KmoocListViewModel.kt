package com.programmers.kmooc.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.programmers.kmooc.models.Lecture
import com.programmers.kmooc.models.LectureList
import com.programmers.kmooc.repositories.KmoocRepository

class KmoocListViewModel(private val repository: KmoocRepository) : BaseViewModel() {
    /**
     * live data for data
     */
    private val _lectures: MutableLiveData<List<Lecture>> = MutableLiveData()
    val lectures: MutableLiveData<List<Lecture>> = _lectures

    /**
     * live data for event
     */
    private val _startDetailViewEvent: MutableLiveData<Lecture> = MutableLiveData()
    val startDetailViewEvent: LiveData<Lecture> = _startDetailViewEvent

    /**
     * data
     */
    private var lectureList : LectureList? = null
        set(value) {
            field = value
            if(value == null)
                return
            when {
                // 이전 list 가 존재하고, api 결과가 첫페이지라면 -> 이어붙인다
                !_lectures.value.isNullOrEmpty() && value.previous != "null" -> {
                    _lectures.postValue(_lectures.value!! + value.lectures)
                }
                else -> _lectures.postValue(value.lectures)
            }
        }

    fun fetchList() {
        if(loading){
            setRefreshLoading(false)
            return
        }
        setDataLoading(true)
        repository.fetchList { lectureList ->
            setDataLoading(false)
            setRefreshLoading(false)
            this.lectureList = lectureList
        }
    }

    fun fetchNextList() {
        if(loading){
            return
        }
        if(lectureList == null){
            return
        }
        setDataLoading(true)
        repository.fetchNextList(lectureList!!) { lectureList ->
            setDataLoading(false)
            setRefreshLoading(false)
            this.lectureList = lectureList
        }
    }

    fun clickListItem(lecture: Lecture) {
        _startDetailViewEvent.value = lecture
    }
}

class KmoocListViewModelFactory(private val repository: KmoocRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(KmoocListViewModel::class.java)) {
            return KmoocListViewModel(repository) as T
        }
        throw IllegalAccessException("Unkown Viewmodel Class")
    }
}