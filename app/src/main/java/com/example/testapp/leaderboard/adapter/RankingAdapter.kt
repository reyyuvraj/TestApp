package com.example.testapp.leaderboard.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.testapp.R
import com.example.testapp.leaderboard.model.RankingModel

class RankingAdapter : RecyclerView.Adapter<RankingAdapter.ViewHolder>() {

    var list: List<RankingModel> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RankingAdapter.ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.leaderboard_list_item, parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: RankingAdapter.ViewHolder, position: Int) {
        val item = list[position]
        holder.rank.text = item.rank.toString()
        holder.image.setImageResource(item.profileImage!!)
        holder.name.text = item.profileName
        holder.xp.text = item.id.toString()
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val rank: TextView = itemView.findViewById(R.id.lli_rank)
        val image: ImageView = itemView.findViewById(R.id.lli_profile_photo)
        val name: TextView = itemView.findViewById(R.id.lli_name)
        val xp: TextView = itemView.findViewById(R.id.lli_points)
    }

    fun setData(list: List<RankingModel>) {
        this.list = list
        notifyDataSetChanged()
    }
}