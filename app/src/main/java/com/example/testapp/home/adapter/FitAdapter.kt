package com.example.testapp.home.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.testapp.R
import com.example.testapp.home.model.WorkoutItem

class FitAdapter(private val listener: FitInterface) : RecyclerView.Adapter<FitAdapter.ViewHolder>() {

    var list: List<WorkoutItem> = emptyList()

    interface FitInterface {

        fun itemClicked(id: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.stay_fit_item, parent, false
            )
        )
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]
        holder.title.text = item.name
        holder.image.setImageResource(item.image!!)
        holder.itemView.setOnClickListener {
            listener.itemClicked(item.id ?: 0)
        }
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.sfi_title)
        val image: ImageView = itemView.findViewById(R.id.sfi_exercise_image)
    }

    fun setData(workoutList: List<WorkoutItem>) {
        this.list = workoutList
        notifyDataSetChanged()
    }
}