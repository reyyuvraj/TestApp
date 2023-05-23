package com.example.testapp.leaderboard.model

import com.example.testapp.R

class LeagueData {

    fun leagueIconsList(): List<LeagueModel> {
        return listOf(
            LeagueModel(
                1,
                R.drawable.bronze,
                "league 1",
                false
            ),
            LeagueModel(
                2,
                R.drawable.gold,
                "league 2",
                false
            ),
            LeagueModel(
                3,
                R.drawable.amethyst,
                "league 3",
                true
            ),
            LeagueModel(
                4,
                R.drawable.ruby,
                "league 4",
                false
            ),
            LeagueModel(
                5,
                R.drawable.diamond,
                "league 5",
                false
            )
        )
    }
}