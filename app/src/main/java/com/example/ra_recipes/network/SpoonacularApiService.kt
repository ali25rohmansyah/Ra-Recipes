package com.example.ra_recipes.network

import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.squareup.moshi.Moshi
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

private const val apiKey = "542aca0f286e42c99e6d61ba34a040ac"
private val baseUrl = "https://api.spoonacular.com/"

val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(baseUrl)
    .build()

// Api interface / Endpoint
interface SpoonacularService{
    @GET("recipes/findByIngredients?ingredients=chicken, fish&number=10&apiKey="+apiKey)
    suspend fun showList():
            List<RecipesData>

    @GET("recipes/{id}/information?apiKey="+apiKey)
    suspend fun showInstructions(@Path("id") id: String):
            DetailRecipesData

    @GET("recipes/{id}/nutritionWidget.json?apiKey=$apiKey")
    suspend fun showNutritions(@Path("id") id: String):
            NutritionsData
}

object SpoonacularApi{
    val retrofitService: SpoonacularService = retrofit.create(SpoonacularService::class.java)

}
