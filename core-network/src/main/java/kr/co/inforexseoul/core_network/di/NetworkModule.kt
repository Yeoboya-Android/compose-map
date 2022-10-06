package kr.co.inforexseoul.core_network.di

import android.util.Log
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kr.co.inforexseoul.core_network.service.*
import okhttp3.OkHttpClient
import okhttp3.ResponseBody.Companion.toResponseBody
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import tech.thdev.network.flowcalladapterfactory.FlowCallAdapterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    // 동영상 자막
    private const val baseUrlClovaSpeech = "https://clovaspeech-gw.ncloud.com/external/v1/3870/3d04298119dd8cd3dba0af251d646fdccd3625ac18b1b44a9d91d82da5746a18/"

    // 번역
    private const val baseUrlPapago = "https://openapi.naver.com/v1/papago/"

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

    @Singleton
    @Provides
    fun providesPapagoApiService(retrofit: Retrofit.Builder): PapagoApiService =
        retrofit.baseUrl(baseUrlPapago).build().create(PapagoApiService::class.java)

    @Singleton
    @Provides
    fun providesClovaApiService(retrofit: Retrofit.Builder): ClovaSpeechApiService =
        retrofit.baseUrl(baseUrlClovaSpeech).build().create(ClovaSpeechApiService::class.java)

}