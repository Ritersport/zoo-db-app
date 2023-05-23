package ru.nsu.databases.data.repository.credentils

import android.content.Context
import com.google.gson.Gson
import dagger.hilt.android.qualifiers.ApplicationContext
import io.reactivex.Completable
import io.reactivex.Single
import ru.nsu.databases.domain.model.Credentials
import ru.nsu.databases.domain.reposiroty.CredentialsStorage
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SharedPrefsCredentialsStorageImpl @Inject constructor(
    @ApplicationContext private val context: Context,
) : CredentialsStorage {

    private val sharedPrefs = context.getSharedPreferences(SHARED_PREFS_FILE, Context.MODE_PRIVATE)

    override fun isLoggedIn(): Single<Boolean> = Single.just(
        isLoggedInBlocking()
    )

    override fun isLoggedInBlocking(): Boolean = sharedPrefs.contains(CREDENTIALS_KEY)

    override fun saveCredentials(credentials: Credentials): Completable =
        Completable.fromAction { saveCredentialsBlocking(credentials) }


    override fun saveCredentialsBlocking(credentials: Credentials) {
        sharedPrefs.edit().putString(CREDENTIALS_KEY, Gson().toJson(credentials)).apply()
    }

    override fun getCredentials(): Single<Credentials> = Single.just(getCredentialsBlocking())

    override fun getCredentialsBlocking(): Credentials = Gson().fromJson(
        sharedPrefs.getString(CREDENTIALS_KEY, null),
        Credentials::class.java
    )

    override fun clearCredentialsBlocking() = sharedPrefs.edit()
        .clear()
        .apply()


    override fun clearCredentials(): Completable =
        Completable.fromAction { clearCredentialsBlocking() }

    companion object {

        const val SHARED_PREFS_FILE = "interesting_file"
        const val CREDENTIALS_KEY = "interesting_key"
    }
}