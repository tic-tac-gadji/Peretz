package ru.diit.peretz.data.models

import com.google.gson.annotations.SerializedName

data class Products (

  @SerializedName("id") var id: Int,
  @SerializedName("image_app") var image: String,
  @SerializedName("new") var new: Boolean,
  @SerializedName("name") var title: String,
  @SerializedName("price") var price: Int,
  @SerializedName("sort") var sort: Int,
  @SerializedName("date") var date: String,
  @SerializedName("image") var imageApp: String,
  @SerializedName("description") var description: String,
  var quantitiBasket:Int = 0

)

