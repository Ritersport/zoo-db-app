package ru.nsu.databases.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import ru.nsu.databases.data.repository.credentils.CredentialsRepositoryImpl
import ru.nsu.databases.data.repository.database.DatabaseConnectionProvider
import ru.nsu.databases.data.repository.database.OracleDatabaseConnectionProviderImpl
import ru.nsu.databases.domain.reposiroty.CredentialsRepository

@Module
@InstallIn(ViewModelComponent::class)
interface DataModuleBindings {

    @Binds
    fun bindCredentialsRepository(impl: CredentialsRepositoryImpl): CredentialsRepository

    @Binds
    fun bindConnectionProvider(impl: OracleDatabaseConnectionProviderImpl): DatabaseConnectionProvider
}