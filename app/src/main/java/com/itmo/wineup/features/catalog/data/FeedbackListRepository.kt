package com.itmo.wineup.features.catalog.data

import com.itmo.wineup.features.catalog.models.FeedbackModel

class FeedbackListRepository {

    fun getList() = getHardCodedList()

    private fun getListFromApi(): List<FeedbackModel> {
        //todo
        return emptyList()
    }

    private fun getHardCodedList(): List<FeedbackModel> {
        val feedback = arrayListOf<FeedbackModel>()
        for (i in 0..9){
            feedback.add(
                FeedbackModel(
                    "Марков Павел",
                    "“Очень достойное. В меру фруктовое, прекрасно пьется. В моем личном рейтинге из всех российских вин уверенно занимает первое место..„"
                    4.5F,
                    "24 апреля 2016 г."
                )
            )
        }
        return feedback

    }

}