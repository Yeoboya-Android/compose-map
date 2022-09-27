package kr.co.inforexseoul.core_network.di

import android.util.Log
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kr.co.inforexseoul.core_network.service.MapApiService
import kr.co.inforexseoul.core_network.service.OpenWeatherApiService
import kr.co.inforexseoul.core_network.service.VillageForecastApiService
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

    // 날씨
    private const val baseUrlVillageForecast = "http://apis.data.go.kr/"
    private const val baseUrlOpenWeather = "https://api.openweathermap.org/"
    /**
     * TODO 버스 API용
     * */
    private const val baseUrlMap = "http://api.gwangju.go.kr/"

    @Singleton
    @Provides
    fun providesOkHttpClient(): OkHttpClient {
        val okHttpClientBuilder = OkHttpClient.Builder()
            .addInterceptor { chain->
                val request = chain.request()
                Log.d("qwe123", "request url: ${request.url}")
                chain.proceed(request)
            }
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
    fun providesOpenWeatherApiService(retrofit: Retrofit.Builder): OpenWeatherApiService =
        retrofit.baseUrl(baseUrlOpenWeather).build().create(OpenWeatherApiService::class.java)

    @Singleton
    @Provides
    fun providesVillageForecastApiService(retrofit: Retrofit.Builder): VillageForecastApiService =
        retrofit.baseUrl(baseUrlVillageForecast).build().create(VillageForecastApiService::class.java)

}