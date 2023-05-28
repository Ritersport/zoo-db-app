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
import ru.nsu.databases.data.repository.database.daos.animal_settling.AnimalSettlingDao
import ru.nsu.databases.data.repository.database.daos.animal_settling.AnimalSettlingDaoImpl
import ru.nsu.databases.data.repository.database.daos.animals.AnimalsDao
import ru.nsu.databases.data.repository.database.daos.animals.AnimalsDaoImpl
import ru.nsu.databases.data.repository.database.daos.diet_type.DietTypeDao
import ru.nsu.databases.data.repository.database.daos.diet_type.DietTypeDaoImpl
import ru.nsu.databases.data.repository.database.daos.employee.EmployeeDao
import ru.nsu.databases.data.repository.database.daos.employee.EmployeeDaoImpl
import ru.nsu.databases.data.repository.database.daos.feed_in_stock.FoodInStockDao
import ru.nsu.databases.data.repository.database.daos.feed_in_stock.FoodInStockDaoImpl
import ru.nsu.databases.data.repository.database.daos.feed_rations.FeedRationDao
import ru.nsu.databases.data.repository.database.daos.feed_rations.FeedRationDaoImpl
import ru.nsu.databases.data.repository.database.daos.feed_supplies.FeedSuppliesDao
import ru.nsu.databases.data.repository.database.daos.feed_supplies.FeedSuppliesDaoImpl
import ru.nsu.databases.data.repository.database.daos.feed_types.FeedTypesDao
import ru.nsu.databases.data.repository.database.daos.feed_types.FeedTypesDaoImpl
import ru.nsu.databases.data.repository.database.daos.genders.GendersDao
import ru.nsu.databases.data.repository.database.daos.genders.GendersDaoImpl
import ru.nsu.databases.data.repository.database.daos.professions.ProfessionsDao
import ru.nsu.databases.data.repository.database.daos.professions.ProfessionsDaoImpl
import ru.nsu.databases.data.repository.database.daos.species.SpeciesDao
import ru.nsu.databases.data.repository.database.daos.species.SpeciesDaoImpl
import ru.nsu.databases.data.repository.database.daos.vendor.VendorsDao
import ru.nsu.databases.data.repository.database.daos.vendor.VendorsDaoImpl
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
    abstract fun bindGendersDao(impl: GendersDaoImpl): GendersDao

    @Binds
    abstract fun bindAnimalSettlingDao(impl: AnimalSettlingDaoImpl): AnimalSettlingDao

    @Binds
    abstract fun bindFeedSuppliesDao(impl: FeedSuppliesDaoImpl): FeedSuppliesDao

    @Binds
    abstract fun bindFeedTypesDao(impl: FeedTypesDaoImpl): FeedTypesDao

    @Binds
    abstract fun bindFeedInStockDao(impl: FoodInStockDaoImpl): FoodInStockDao

    @Binds
    abstract fun bindDietTypeDao(impl: DietTypeDaoImpl): DietTypeDao

    @Binds
    abstract fun bindFeedRationDao(impl: FeedRationDaoImpl): FeedRationDao

    @Binds
    abstract fun bindSpeciesDao(impl: SpeciesDaoImpl): SpeciesDao

    @Binds
    abstract fun bindVendorsDao(impl: VendorsDaoImpl): VendorsDao


    companion object {

        @Provides
        fun provideDatabase(connectionProvider: DatabaseConnectionProvider): ZooDatabase =
            OracleZooDatabaseImpl(connectionProvider = connectionProvider)

    }
}