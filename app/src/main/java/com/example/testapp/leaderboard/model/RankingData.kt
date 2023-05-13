package com.example.testapp.leaderboard.model

import com.example.testapp.R

class RankingData {

    fun rankingList(): List<RankingModel> {
        return listOf(
            RankingModel(
                1,
                R.drawable.pp1,
                "Alan",
                1
            ),
            RankingModel(
                2,
                R.drawable.pp2,
                "Sindy",
                2
            ),
            RankingModel(
                3,
                R.drawable.pp3,
                "Jason",
                3
            ),
            RankingModel(
                4,
                R.drawable.pp4,
                "Rick",
                4
            ),
            RankingModel(
                5,
                R.drawable.pp5,
                "Kristen",
                5
            ),
            RankingModel(
                6,
                R.drawable.pp6,
                "Alex",
                6
            ),
            RankingModel(
                7,
                R.drawable.pp7,
                "Paul",
                7
            ),
            RankingModel(
                8,
                R.drawable.pp8,
                "Amanda",
                8
            ),
            RankingModel(
                9,
                R.drawable.pp9,
                "Misty",
                9
            ),
            RankingModel(
                10,
                R.drawable.pp10,
                "Amanda",
                10
            ),
            RankingModel(
                11,
                R.drawable.pp11,
                "Bob",
                11
            )
        )
    }
}