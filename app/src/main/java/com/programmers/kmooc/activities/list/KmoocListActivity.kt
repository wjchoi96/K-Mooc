package com.programmers.kmooc.activities.list

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.programmers.kmooc.KmoocApplication
import com.programmers.kmooc.activities.detail.KmoocDetailActivity
import com.programmers.kmooc.databinding.ActivityKmookListBinding
import com.programmers.kmooc.models.Lecture
import com.programmers.kmooc.utils.showToast
import com.programmers.kmooc.utils.toVisibility
import com.programmers.kmooc.viewmodels.KmoocListViewModel
import com.programmers.kmooc.viewmodels.KmoocListViewModelFactory

class KmoocListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityKmookListBinding
    private lateinit var viewModel: KmoocListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initData()
        binding = ActivityKmookListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()

        viewModel.fetchList()
    }

    private fun initData(){
        val kmoocRepository = (application as KmoocApplication).kmoocRepository
        viewModel = ViewModelProvider(this, KmoocListViewModelFactory(kmoocRepository)).get(
            KmoocListViewModel::class.java
        )
    }

    private fun initView(){
        setUpRecyclerView()
        setUpPullToRefresh()
        bind()
    }

    private fun setUpRecyclerView(){
        val adapter = LecturesAdapter()
            .apply { onClick = viewModel::clickListItem }

        binding.lectureList.apply {
            this.adapter = adapter
            addOnScrollListener(pagingListener)
        }
    }

    private val pagingListener = object : RecyclerView.OnScrollListener() {
        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            if(!recyclerView.canScrollVertically(1)){ // direction 은 Vertically 기준으로 -1이 위쪽, 1이 아래쪽이다
                Log.d("paging", "vertical end")
                if((binding.lectureList.adapter as LecturesAdapter).currentItemSize != 0){
                    viewModel.fetchNextList()
                }
            }
        }
    }

    private fun setUpPullToRefresh(){
        binding.pullToRefresh.setOnRefreshListener {
            viewModel.fetchList()
        }
    }

    private fun bind(){
        viewModel.dataLoading .observe(this){
            binding.progressBar.visibility = it.toVisibility()
        }

        viewModel.refreshLoading .observe(this){
            binding.pullToRefresh.isRefreshing = it
        }

        viewModel.showToastEvent.observe(this){
            showToast(it)
        }

        viewModel.startDetailViewEvent.observe(this) {
            startActivity(
                Intent(this, KmoocDetailActivity::class.java)
                    .apply { putExtra(KmoocDetailActivity.INTENT_PARAM_COURSE_ID, it.id) }
            )
        }

        viewModel.lectures.observe(this) {
            (binding.lectureList.adapter as LecturesAdapter).updateLectures(it)
        }
    }
}
