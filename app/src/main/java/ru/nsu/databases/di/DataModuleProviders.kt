package ru.nsu.databases.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import ru.nsu.databases.data.repository.database.DatabaseConnectionProvider
import ru.nsu.databases.data.repository.database.OracleZooDatabaseImpl
import ru.nsu.databases.data.repository.database.ZooDatabase


@Module
@InstallIn(ActivityComponent::class)
object DataModuleProviders {

    @Provides
    fun provideDatabase(connectionProvider: DatabaseConnectionProvider): ZooDatabase =
        OracleZooDatabaseImpl(
            connectionProvider = connectionProvider
        )
}