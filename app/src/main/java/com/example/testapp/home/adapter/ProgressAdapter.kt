package com.example.testapp.home.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.testapp.R
import com.example.testapp.home.model.ProgressData
import com.mikhaellopez.circularprogressbar.CircularProgressBar

class ProgressAdapter : RecyclerView.Adapter<ProgressAdapter.ProgressViewHolder>() {

    private var progressList: List<ProgressData> = emptyList()

    inner class ProgressViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val circularProgressBar: CircularProgressBar =
            itemView.findViewById(R.id.cpi_circularProgressBar)
        val circularProgressBar2: CircularProgressBar =
            itemView.findViewById(R.id.cpi_circularProgressBar2)
        val weekday: TextView = itemView.findViewById(R.id.cpi_weekday)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProgressViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.circular_progress_item, parent, false)
        return ProgressViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return progressList.size
    }

    override fun onBindViewHolder(holder: ProgressViewHolder, position: Int) {
        val progressData = progressList[position]
        holder.circularProgressBar.setProgressWithAnimation(progressData.progress1.toFloat())
        holder.circularProgressBar2.setProgressWithAnimation(progressData.progress2.toFloat())
        holder.weekday.text = progressData.weekday
    }

    fun setData(list: List<ProgressData>) {
        this.progressList = list
        notifyDataSetChanged()
    }
}