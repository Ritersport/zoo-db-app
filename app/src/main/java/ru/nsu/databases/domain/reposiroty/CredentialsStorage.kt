package ru.nsu.databases.domain.reposiroty


import io.reactivex.Completable
import io.reactivex.Single
import ru.nsu.databases.domain.model.security.Credentials

interface CredentialsStorage {

    fun hasCredentials(): Single<Boolean>

    fun hasCredentialsBlocking(): Boolean

    fun saveCredentials(credentials: Credentials): Completable

    fun saveCredentialsBlocking(credentials: Credentials)

    fun getCredentials(): Single<Credentials>

    fun getCredentialsBlocking(): Credentials

    fun clearCredentialsBlocking()

    fun clearCredentials(): Completable
}