package ru.nsu.databases.domain.model.zoo

class Vendor(
    val id: Int,
    val organizationName: String,
    val feedType: FeedType,
) {
    override fun toString(): String = "$organizationName (${feedType.name})"
}