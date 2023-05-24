package ru.nsu.databases.domain.model.security

data class Credentials(
    val login: String,
    val password: String,
)