package com.tta.imagecachex.di

import android.content.Context
import androidx.room.Room
import com.tta.imagecachex.data.api.ImageApiInterceptor
import com.tta.imagecachex.data.api.ImageApiService
import com.tta.imagecachex.data.db.ImageListDao
import com.tta.imagecachex.data.db.ImageListDatabase
import com.tta.imagecachex.data.repository.LocalRepositoryImpl
import com.tta.imagecachex.data.source.RemoteSourceImpl
import com.tta.imagecachex.data.util.Constants.BASE_URL
import com.tta.imagecachex.data.util.Constants.IMAGE_LIST_TABLE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
class HiltModule {
    @Provides
    fun provideOkHttpClient(): OkHttpClient =
        OkHttpClient.Builder().addInterceptor(ImageApiInterceptor()).build()


    @Provides
    fun provideRetrofitInstance(okHttpClient: OkHttpClient): Retrofit =
        Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient).build()

    @Provides
    fun provideApiInstance(retrofit: Retrofit): ImageApiService =
        retrofit.create(ImageApiService::class.java)

    @Provides
    fun provideRemoteSourceInstance(api: ImageApiService): RemoteSourceImpl = RemoteSourceImpl(api)

    @Provides
    fun provideLocalSourceInstance(githubEventListDao: ImageListDao): LocalRepositoryImpl =
        LocalRepositoryImpl(githubEventListDao)


    @Provides
    fun provideImageListDao(imageListDatabase: ImageListDatabase): ImageListDao =
        imageListDatabase.imageListDao()

    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context): ImageListDatabase =
        Room.databaseBuilder(
            context,
            ImageListDatabase::class.java,
            IMAGE_LIST_TABLE_NAME
        )
            .fallbackToDestructiveMigration()
            .build()

}