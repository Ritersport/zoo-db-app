package ru.nsu.databases.di

import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.components.SingletonComponent
import ru.nsu.databases.data.repository.credentils.ImMemoryCredentialsStorageImpl
import ru.nsu.databases.data.repository.database.DatabaseConnectionProvider
import ru.nsu.databases.data.repository.database.OracleDatabaseConnectionProviderImpl
import ru.nsu.databases.data.repository.database.OracleZooDatabaseImpl
import ru.nsu.databases.data.repository.database.ZooDatabase
import ru.nsu.databases.domain.reposiroty.CredentialsStorage


@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {

    @Binds
    abstract fun bindConnectionProvider(impl: OracleDatabaseConnectionProviderImpl): DatabaseConnectionProvider

    @Binds
    abstract fun bindCredentialsStorage(impl: ImMemoryCredentialsStorageImpl): CredentialsStorage

    companion object {

        @Provides
        fun provideDatabase(connectionProvider: DatabaseConnectionProvider): ZooDatabase =
            OracleZooDatabaseImpl(
                connectionProvider = connectionProvider
            )

    }
}