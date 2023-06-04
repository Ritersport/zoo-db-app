package ru.nsu.databases.ui.animals.details

sealed interface DetailsState {
    object Edit : DetailsState
    object View : DetailsState
    object Add : DetailsState
}