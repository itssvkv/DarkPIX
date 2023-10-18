package com.itssvkv.darkpix.diimport android.content.Contextimport android.content.Intentimport android.util.Logimport androidx.navigation.NavControllerimport androidx.navigation.fragment.NavHostFragmentimport com.itssvkv.darkpix.utils.sharedPref.SharedPrefimport com.itssvkv.darkpix.data.network.ApiCallsimport com.itssvkv.darkpix.data.network.ApiServersimport com.itssvkv.darkpix.utils.Commonimport com.itssvkv.darkpix.utils.Common.ACCESS_TOKENimport com.itssvkv.darkpix.utils.Common.BASE_CALLS_URLimport com.itssvkv.darkpix.utils.Common.BASE_URLimport dagger.Moduleimport dagger.Providesimport dagger.hilt.InstallInimport dagger.hilt.android.qualifiers.ApplicationContextimport dagger.hilt.components.SingletonComponentimport okhttp3.OkHttpClientimport okhttp3.logging.HttpLoggingInterceptorimport retrofit2.Retrofitimport retrofit2.converter.gson.GsonConverterFactoryimport javax.inject.Namedimport javax.inject.Singleton@Module@InstallIn(SingletonComponent::class)object AppModule {    private var accessToken = ""    @Provides    @Singleton    fun providesIntent(): Intent {        return Intent()    }    @Provides    @Singleton    fun provideClient(): OkHttpClient {        val httpClientLoggingInterceptor = HttpLoggingInterceptor { msg ->            Log.i("logInterceptor", "Interceptor : $msg")        }        httpClientLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY        return OkHttpClient().newBuilder().apply {            addInterceptor { chain ->                val newRequest = chain.request().newBuilder()                newRequest.addHeader("Accept", "application/json")                newRequest.addHeader("Authorization", Common.API_KEY)                chain.proceed(newRequest.build())            }            addInterceptor(httpClientLoggingInterceptor)        }.build()    }    @Provides    @Singleton    @Named("authRetrofit")    fun providesRetrofitServers(client: OkHttpClient): Retrofit {        return Retrofit.Builder().apply {            baseUrl(BASE_URL)            addConverterFactory(GsonConverterFactory.create())            client(client)        }.build()    }    @Provides    @Singleton    @Named("mainRetrofit")    fun provideSecondRetrofit(client: OkHttpClient): Retrofit {        return Retrofit.Builder().apply {            baseUrl(BASE_CALLS_URL)            addConverterFactory(GsonConverterFactory.create())            client(client)        }.build()    }    @Provides    @Singleton    fun providesApiCalls(@Named("mainRetrofit") retrofit: Retrofit): ApiCalls {        return retrofit.create(ApiCalls::class.java)    }    @Provides    @Singleton    fun providesApiServers(@Named("authRetrofit") retrofit: Retrofit): ApiServers {        return retrofit.create(ApiServers::class.java)    }    @Provides    @Singleton    fun providesNavController(@ApplicationContext context: Context): NavController {        accessToken = SharedPref.getFromPref(context, ACCESS_TOKEN, "") as String        return NavController(context)    }    @Provides    @Singleton    fun providesNavHostFragment(): NavHostFragment {        return NavHostFragment()    }}