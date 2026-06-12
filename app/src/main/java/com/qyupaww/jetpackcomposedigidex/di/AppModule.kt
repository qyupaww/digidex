package com.qyupaww.jetpackcomposedigidex.di

import com.qyupaww.jetpackcomposedigidex.data.remote.DigimonApi
import com.qyupaww.jetpackcomposedigidex.repository.DigimonRepository
import com.qyupaww.jetpackcomposedigidex.util.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideDigimonDatabase(app: android.app.Application): com.qyupaww.jetpackcomposedigidex.data.local.DigimonDatabase {
        return androidx.room.Room.databaseBuilder(
            app,
            com.qyupaww.jetpackcomposedigidex.data.local.DigimonDatabase::class.java,
            "digidex_db"
        ).fallbackToDestructiveMigration().build()
    }

    @Singleton
    @Provides
    fun provideDigimonDao(db: com.qyupaww.jetpackcomposedigidex.data.local.DigimonDatabase): com.qyupaww.jetpackcomposedigidex.data.local.DigimonDao {
        return db.dao
    }

    @Singleton
    @Provides
    fun provideDigimonRepository(
        api: DigimonApi,
        dao: com.qyupaww.jetpackcomposedigidex.data.local.DigimonDao
    ) = DigimonRepository(api, dao)

    @Singleton
    @Provides
    fun provideDigimonApi(): DigimonApi {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(DigimonApi::class.java)
    }
}
