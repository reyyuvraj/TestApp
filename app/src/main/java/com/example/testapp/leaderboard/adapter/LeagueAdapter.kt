package com.example.testapp.leaderboard.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.testapp.R
import com.example.testapp.leaderboard.model.LeagueModel

class LeagueAdapter : RecyclerView.Adapter<LeagueAdapter.ViewHolder>() {

    private var list: List<LeagueModel> = emptyList()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.league_item, parent, false
            )
        )
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]

        if (item.isActive == true) {
            holder.imageLarger.setImageResource(item.leagueIcon!!)
            holder.imageSmaller.visibility = View.GONE
        } else {
            holder.imageLarger.visibility = View.GONE
            holder.imageSmaller.setImageResource(item.leagueIcon!!)
        }
    }


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageSmaller: ImageView = itemView.findViewById(R.id.li_image_smaller)
        val imageLarger: ImageView = itemView.findViewById(R.id.li_image_larger)
    }

    fun setData(list: List<LeagueModel>) {
        this.list = list
        notifyDataSetChanged()
    }
}