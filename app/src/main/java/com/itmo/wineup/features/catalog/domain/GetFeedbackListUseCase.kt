package com.itmo.wineup.features.catalog.domain

import com.itmo.wineup.features.catalog.data.FeedbackListRepository

class GetFeedbackListUseCase {

    private val feedbackListRepository = FeedbackListRepository()

    operator fun invoke() = feedbackListRepository.getList()

}