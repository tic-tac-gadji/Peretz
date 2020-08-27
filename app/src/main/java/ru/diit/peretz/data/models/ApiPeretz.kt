package ru.diit.peretz.data.models

import retrofit2.Call
import retrofit2.http.GET

interface ApiPeretz {

    @GET("api/v2/products?category=93&key=47be9031474183ea92958d5e255d888e47bdad44afd5d7b7201d0eb572be5278")
    fun getItems(): Call<ArrayList<Products>>

}