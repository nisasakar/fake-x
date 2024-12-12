package com.ns.fakex.di

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.chuckerteam.chucker.api.RetentionManager
import com.ns.fakex.data.remote.ApiService
import com.ns.fakex.utils.Constants.ACCESS_TOKEN
import com.ns.fakex.utils.Constants.ACCESS_TOKEN_SECRET
import com.ns.fakex.utils.Constants.BASE_URL
import com.ns.fakex.utils.Constants.CHUCKER_MAX_CONTENT_LENGTH
import com.ns.fakex.utils.Constants.CHUCKER_TEXT
import com.ns.fakex.utils.Constants.CLIENT_KEY
import com.ns.fakex.utils.Constants.CLIENT_SECRET
import com.ns.fakex.utils.Constants.TIME_OUT_SECOND
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import se.akerfeldt.okhttp.signpost.OkHttpOAuthConsumer
import se.akerfeldt.okhttp.signpost.SigningInterceptor
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RemoteDataModule {

    @Singleton
    @Provides
    fun provideMoshi(): Moshi = Moshi.Builder().addLast(KotlinJsonAdapterFactory()).build()

    @Provides
    @Singleton
    fun provideOkHttpClient(
        interceptor: Interceptor,
        @Named(CHUCKER_TEXT) chuckInterceptor: ChuckerInterceptor,
    ): OkHttpClient {
        val consumer = OkHttpOAuthConsumer(CLIENT_KEY, CLIENT_SECRET)
        consumer.setTokenWithSecret(ACCESS_TOKEN, ACCESS_TOKEN_SECRET)
        return OkHttpClient.Builder()
            .connectTimeout(TIME_OUT_SECOND, TimeUnit.SECONDS)
            .readTimeout(TIME_OUT_SECOND, TimeUnit.SECONDS)
            .writeTimeout(TIME_OUT_SECOND, TimeUnit.SECONDS)
            .addInterceptor(interceptor)
            .addInterceptor(SigningInterceptor(consumer))
            .apply {
                val loggingInterceptor = HttpLoggingInterceptor()
                loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
                addInterceptor(loggingInterceptor)
                addInterceptor(chuckInterceptor)
            }
            .build()
    }

    @Singleton
    @Provides
    @Named(CHUCKER_TEXT)
    fun provideChuckerInterceptor(
        @ApplicationContext context: Context,
        chuckerCollector: ChuckerCollector,
    ): ChuckerInterceptor {
        return ChuckerInterceptor.Builder(context).collector(chuckerCollector)
            .maxContentLength(CHUCKER_MAX_CONTENT_LENGTH)
            .redactHeaders("Content-Type", "application/x-www-form-urlencoded")
            .alwaysReadResponseBody(true).build()
    }

    @Singleton
    @Provides
    fun provideChuckerCollector(@ApplicationContext context: Context): ChuckerCollector =
        ChuckerCollector(
            context = context,
            // Toggles visibility of the push notification
            showNotification = true,
            // Allows to customize the retention period of collected data
            retentionPeriod = RetentionManager.Period.ONE_HOUR,
        )

    @Provides
    @Singleton
    @Named("API_TWITTER")
    fun provideTwitterApiInterceptor(): Interceptor {
        return Interceptor { chain ->
            val request = chain.request().newBuilder()
            chain.proceed(request.build())
        }
    }

    @Provides
    @Singleton
    fun provideCustomerServiceRetrofit(
        @Named("API_TWITTER") okHttpClient: OkHttpClient,
        moshi: Moshi,
    ): ApiService {
        return Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .build()
            .create(ApiService::class.java)
    }

    @Provides
    @Singleton
    @Named("API_TWITTER")
    fun provideApiServiceOkHttpClient(
        @Named("API_TWITTER") interceptor: Interceptor,
        @Named(CHUCKER_TEXT) chuckInterceptor: ChuckerInterceptor,
    ): OkHttpClient {
        return provideOkHttpClient(interceptor, chuckInterceptor)
    }
}