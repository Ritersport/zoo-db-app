package ru.nsu.databases.domain.exceptions

class InvalidCredentialsException(
    message: String = ""
) : RuntimeException(message)