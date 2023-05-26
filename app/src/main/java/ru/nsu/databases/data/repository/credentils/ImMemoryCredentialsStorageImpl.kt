package ru.nsu.databases.data.repository.credentils

import io.reactivex.Completable
import io.reactivex.Single
import ru.nsu.databases.domain.model.security.Credentials
import ru.nsu.databases.domain.reposiroty.CredentialsStorage
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ImMemoryCredentialsStorageImpl @Inject constructor() : CredentialsStorage {

    private var credentials: Credentials? = null

    override fun hasCredentials(): Single<Boolean> = Single.just(hasCredentialsBlocking())

    override fun hasCredentialsBlocking(): Boolean = credentials != null

    override fun saveCredentials(credentials: Credentials): Completable =
        Completable.fromAction { saveCredentialsBlocking(credentials) }

    override fun saveCredentialsBlocking(credentials: Credentials) {
        this.credentials = credentials
    }

    override fun getCredentials(): Single<Credentials> = Single.just(getCredentialsBlocking())

    override fun getCredentialsBlocking(): Credentials =
        credentials ?: error("Trying to access empty credentials")

    override fun clearCredentialsBlocking() {
        credentials = null
    }

    override fun clearCredentials(): Completable =
        Completable.fromAction { clearCredentialsBlocking() }
}