package ru.diit.peretz

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.diit.peretz.data.models.ApiPeretz

class NetworkRequest {

        private var request: NetworkRequest? = null
        private val BASE_URL = "https://peretz-group.ru/"
        private val retrofit: Retrofit
        private val client: OkHttpClient

        val apiPeretz: ApiPeretz
            get() {
                return retrofit.create(ApiPeretz::class.java)
            }

        init {
            client = OkHttpClient().newBuilder().build()

            retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }

        fun getRequest(): NetworkRequest {

            if (request == null) request = NetworkRequest()

            return request as NetworkRequest
        }

        fun getTestApi(): ApiPeretz {
            return retrofit.create(ApiPeretz::class.java)
        }
    }
