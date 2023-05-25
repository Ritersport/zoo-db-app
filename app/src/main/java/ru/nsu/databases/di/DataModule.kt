package ru.nsu.databases.di

import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.nsu.databases.data.repository.credentils.SharedPrefsCredentialsStorageImpl
import ru.nsu.databases.data.repository.database.OracleZooDatabaseImpl
import ru.nsu.databases.data.repository.database.ZooDatabase
import ru.nsu.databases.data.repository.database.connection_provider.DatabaseConnectionProvider
import ru.nsu.databases.data.repository.database.connection_provider.OracleDatabaseConnectionProviderImpl
import ru.nsu.databases.data.repository.database.daos.animals.AnimalsDao
import ru.nsu.databases.data.repository.database.daos.animals.AnimalsDaoImpl
import ru.nsu.databases.data.repository.database.daos.employee.EmployeeDao
import ru.nsu.databases.data.repository.database.daos.employee.EmployeeDaoImpl
import ru.nsu.databases.data.repository.database.daos.professions.ProfessionsDao
import ru.nsu.databases.data.repository.database.daos.professions.ProfessionsDaoImpl
import ru.nsu.databases.data.repository.database.daos.species.SpeciesDao
import ru.nsu.databases.data.repository.database.daos.species.SpeciesDaoImpl
import ru.nsu.databases.domain.reposiroty.CredentialsStorage


@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {

    @Binds
    abstract fun bindConnectionProvider(impl: OracleDatabaseConnectionProviderImpl): DatabaseConnectionProvider

    @Binds
    abstract fun bindCredentialsStorage(impl: SharedPrefsCredentialsStorageImpl): CredentialsStorage

    @Binds
    abstract fun bindEmployeeDao(impl: EmployeeDaoImpl): EmployeeDao

    @Binds
    abstract fun bindAnimalsDao(impl: AnimalsDaoImpl): AnimalsDao

    @Binds
    abstract fun bindProfessionsDao(impl: ProfessionsDaoImpl): ProfessionsDao

    @Binds
    abstract fun bindSpeciesDao(impl: SpeciesDaoImpl): SpeciesDao

    companion object {

        @Provides
        fun provideDatabase(connectionProvider: DatabaseConnectionProvider): ZooDatabase =
            OracleZooDatabaseImpl(
                connectionProvider = connectionProvider
            )

    }
}