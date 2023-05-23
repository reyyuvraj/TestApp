package com.example.testapp.leaderboard

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.testapp.R
import com.example.testapp.leaderboard.adapter.HorizontalSpaceItemDecoration
import com.example.testapp.leaderboard.adapter.LeagueAdapter
import com.example.testapp.leaderboard.adapter.RankingAdapter
import com.example.testapp.leaderboard.model.LeagueData
import com.example.testapp.leaderboard.model.RankingData

class LeaderboardFragment : Fragment(R.layout.fragment_leaderboard) {

    private lateinit var rankingAdapter: RankingAdapter
    private lateinit var leagueAdapter: LeagueAdapter
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpView()
        val leagueRecyclerView: RecyclerView = view.findViewById(R.id.fl_league_rv)
        leagueRecyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        leagueAdapter = LeagueAdapter()
        leagueRecyclerView.adapter = leagueAdapter
        leagueAdapter.setData(LeagueData().leagueIconsList())

        val rankingRecyclerView: RecyclerView = view.findViewById(R.id.fl_ranking_rv)
        rankingRecyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        rankingAdapter = RankingAdapter()
        rankingRecyclerView.adapter = rankingAdapter
        rankingAdapter.setData(RankingData().rankingList())
    }

    private fun setUpView() {
        view?.findViewById<TextView>(R.id.log_tv)?.apply {
            visibility = View.GONE
        }
    }

    /*private fun calculateHorizontalSpacing(itemCount: Int, availableWidth: Int = resources.displayMetrics.widthPixels): Int {
        val totalItemWidth = (itemCount - 1) * 100
        val remainingSpace = availableWidth - totalItemWidth
        return remainingSpace / (itemCount - 1)
    }*/
}