package kr.co.inforexseoul.core_network.di

import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kr.co.inforexseoul.core_network.service.MapApiService
import kr.co.inforexseoul.core_network.service.OpenWeatherMapApiService
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import tech.thdev.network.flowcalladapterfactory.FlowCallAdapterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private const val baseUrlOpenWeatherMap = "https://api.openweathermap.org/"
    /**
     * TODO 버스 API용
     * */
    private const val baseUrlMap = "http://api.gwangju.go.kr/"

    @Singleton
    @Provides
    fun providesOkHttpClient(): OkHttpClient {
        val okHttpClientBuilder = OkHttpClient.Builder()
            .connectTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .readTimeout(20, TimeUnit.SECONDS)

        return okHttpClientBuilder.build()
    }

    @Singleton
    @Provides
    fun providesRetrofitBuilder(okHttpClient: OkHttpClient): Retrofit.Builder =
        Retrofit.Builder()
            .client(okHttpClient)
            .addCallAdapterFactory(FlowCallAdapterFactory())
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(Gson()))

    @Singleton
    @Provides
    fun providesMapApiService(retrofit: Retrofit.Builder): MapApiService =
        retrofit.baseUrl(baseUrlMap).build().create(MapApiService::class.java)

    @Singleton
    @Provides
    fun providesOpenWeatherMapApiService(retrofit: Retrofit.Builder): OpenWeatherMapApiService =
        retrofit.baseUrl(baseUrlOpenWeatherMap).build().create(OpenWeatherMapApiService::class.java)

}