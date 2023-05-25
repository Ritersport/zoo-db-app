package ru.nsu.databases.domain.model.zoo

import java.util.Date

class FeedSupply (
    val vendor: Int,
    val supplyDate: Date,
    val amount: Int,
    val price: Int
    )