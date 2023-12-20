package com.example.jayasales.di

import android.content.Context
import com.example.jayasales.repository.ApiInterface
import com.example.jayasales.repository.MockRepositoryImpl
import com.example.jayasales.repository.Repository
import com.example.jayasales.repository.preference.PrefRepository
import com.example.jayasales.repository.preference.PrefRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Provides
    @Singleton
    fun provideRepository(myPref : PrefRepository, apiHelper: ApiInterface): Repository = MockRepositoryImpl(myPref,apiHelper)


    @Provides
    @Singleton
    fun providePrefRepository(@ApplicationContext context: Context) : PrefRepository = PrefRepositoryImpl(context)

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit = Retrofit
        .Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl("https://apis.jayaindustries.in/jayasalesapi/v1/")
        .client(okHttpClient)
        //.addConverterFactory(ScalarsConverterFactory.create())
        .build()

    @Provides
    @Singleton
    fun provideOkhttpClient(@ApplicationContext context: Context): OkHttpClient {

        val intercepter = HttpLoggingInterceptor().apply {
            this.level = HttpLoggingInterceptor.Level.BODY
        }
        return OkHttpClient
            .Builder()
            //.addInterceptor(ChuckerInterceptor(context))
            .addInterceptor(intercepter)
            .build()
    }

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit):ApiInterface = retrofit.create(ApiInterface::class.java)

}