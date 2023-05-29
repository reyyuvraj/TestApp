package com.example.testapp.leaderboard.model

import com.example.testapp.R

class RankingData {

    fun rankingList(): List<RankingModel> {
        return listOf(
            RankingModel(
                4000,
                R.drawable.pp1,
                "Alan",
                1
            ),
            RankingModel(
                3985,
                R.drawable.pp2,
                "Sindy",
                2
            ),
            RankingModel(
                3547,
                R.drawable.pp3,
                "Jason",
                3
            ),
            RankingModel(
                3320,
                R.drawable.pp4,
                "Rick",
                4
            ),
            RankingModel(
                3289,
                R.drawable.pp5,
                "Kristen",
                5
            ),
            RankingModel(
                3236,
                R.drawable.pp6,
                "Alex",
                6
            ),
            RankingModel(
                3214,
                R.drawable.pp7,
                "Paul",
                7
            ),
            RankingModel(
                3195,
                R.drawable.pp8,
                "Amanda",
                8
            ),
            RankingModel(
                3155,
                R.drawable.pp9,
                "Misty",
                9
            ),
            RankingModel(
                3091,
                R.drawable.pp10,
                "Amanda",
                10
            ),
            RankingModel(
                3014,
                R.drawable.pp11,
                "Bob",
                11
            )
        )
    }
}