package com.programmers.kmooc.activities.detail

import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.programmers.kmooc.KmoocApplication
import com.programmers.kmooc.databinding.ActivityKmookDetailBinding
import com.programmers.kmooc.models.Lecture
import com.programmers.kmooc.network.ImageLoader
import com.programmers.kmooc.utils.DateUtil
import com.programmers.kmooc.utils.showToast
import com.programmers.kmooc.utils.toVisibility
import com.programmers.kmooc.viewmodels.KmoocDetailViewModel
import com.programmers.kmooc.viewmodels.KmoocDetailViewModelFactory

class KmoocDetailActivity : AppCompatActivity() {

    companion object {
        const val INTENT_PARAM_COURSE_ID = "param_course_id"
    }

    private lateinit var binding: ActivityKmookDetailBinding
    private lateinit var viewModel: KmoocDetailViewModel

    private var courseId : String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initData()
        binding = ActivityKmookDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()
        setUpViewModel()
        viewModel.fetchDetail(courseId)
    }

    private fun initData(){
        val kmoocRepository = (application as KmoocApplication).kmoocRepository
        viewModel = ViewModelProvider(this, KmoocDetailViewModelFactory(kmoocRepository)).get(
            KmoocDetailViewModel::class.java
        )
        courseId = intent.getStringExtra(INTENT_PARAM_COURSE_ID)
        if(courseId.isNullOrBlank()){
            showToast("key 값이 유실되었습니다")
            finish()
        }
    }

    private fun initView(){
        binding.toolbar.setNavigationOnClickListener { viewModel.backClick() }
    }

    private fun setUpViewModel(){
        viewModel.dataLoading .observe(this){
            binding.progressBar.visibility = it.toVisibility()
        }

        viewModel.showToastEvent.observe(this){
            showToast(it)
        }

        viewModel.finishViewEvent.observe(this) {
            if(!it.isNullOrBlank()) showToast(it)
            finish()
        }

        viewModel.lecture.observe(this) {
            setView(it)
        }
    }

    private fun setView(lecture: Lecture){
        loadImage(lecture.courseImageLarge, binding.lectureImage)
        binding.toolbar.title = lecture.name
        binding.lectureNumber.setDescription("• 강좌번호 ::", lecture.number)
        binding.lectureType.setDescription("• 강좌분류 :::", lecture.classfyName)
        binding.lectureOrg.setDescription("• 운영기관 ::", lecture.orgName)
        binding.lectureTeachers.setDescription("• 교수정보 ::", lecture.teachers ?: "")
        val dateStr = "${DateUtil.formatDate(lecture.start)} ~ ${DateUtil.formatDate(lecture.end)}"
        binding.lectureDue.setDescription("• 운영기간 :::", dateStr)
        if(!lecture.overview.isNullOrBlank()){
            binding.webView.settings.javaScriptEnabled = true
            binding.webView.loadData(lecture.overview, "text/html; charset=utf-8", "UTF-8")
        }
    }

    private fun loadImage(urlStr : String, into : ImageView){
        ImageLoader.loadImage(urlStr){
            it?.let { bitmap -> into.setImageBitmap(bitmap) }
        }
    }

}