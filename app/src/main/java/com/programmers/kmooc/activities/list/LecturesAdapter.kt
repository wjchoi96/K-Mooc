package com.programmers.kmooc.activities.list

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.programmers.kmooc.R
import com.programmers.kmooc.databinding.ViewKmookListItemBinding
import com.programmers.kmooc.models.Lecture
import com.programmers.kmooc.network.ImageLoader
import com.programmers.kmooc.utils.DateUtil

class LecturesAdapter : RecyclerView.Adapter<LectureViewHolder>() {

    private val lectures = mutableListOf<Lecture>()
    var onClick: (Lecture) -> Unit = {}
    val currentItemSize: Int
        get() = lectures.size

    fun updateLectures(lectures: List<Lecture>) {
        this.lectures.clear()
        this.lectures.addAll(lectures)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return lectures.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LectureViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.view_kmook_list_item, parent, false)
        val binding = ViewKmookListItemBinding.bind(view)
        return LectureViewHolder(binding)
    }

    override fun onBindViewHolder(holder: LectureViewHolder, position: Int) {
        val lecture = lectures[position]
        holder.itemView.setOnClickListener { onClick(lecture) }
        holder.bind(lecture)
    }
}

class LectureViewHolder(
    private val binding: ViewKmookListItemBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(lecture: Lecture){
        binding.lectureTitle.text = lecture.name
        binding.lectureFrom.text = lecture.classfyName
        val dateStr = "${DateUtil.formatDate(lecture.start)} ~ ${DateUtil.formatDate(lecture.end)}"
        binding.lectureDuration.text = dateStr
        loadImage(lecture.courseImage, binding.lectureImage)
    }

    private fun loadImage(urlStr : String, into : ImageView) {
        ImageLoader.loadImage(urlStr) {
            it?.let { bitmap -> into.setImageBitmap(bitmap) }
        }
    }
}