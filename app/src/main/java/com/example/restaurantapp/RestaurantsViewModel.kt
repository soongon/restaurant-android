package com.example.restaurantapp

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RestaurantsViewModel(): ViewModel() {

    private var restInterface: RestaurantApiService
    // 상태값을 ViewModel 에서 관리
    val state: MutableState<List<Restaurant>> =  mutableStateOf(emptyList())

    private lateinit var restaurantCall: Call<List<Restaurant>>

    init {
        val retrofit: Retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://rest-e4662-default-rtdb.asia-southeast1.firebasedatabase.app/")
            .build()
        restInterface = retrofit.create(RestaurantApiService::class.java)
    }

    override fun onCleared() {
        super.onCleared()
        restaurantCall.cancel()
    }

    fun getRestaurants() {
        restaurantCall = restInterface.getRestaurants()
        restaurantCall.enqueue(
            object : Callback<List<Restaurant>> {
                override fun onResponse(
                    call: Call<List<Restaurant>>,
                    response: Response<List<Restaurant>>
                ) {
                    response.body()?.let { restaurants ->
                        state.value = restaurants
                    }
                }
                override fun onFailure(
                    call: Call<List<Restaurant>>,
                    t: Throwable
                ) {
                    t.printStackTrace()
                }

            }
        )
    }

    // 레스토랑 id 값으로 해당 레스토랑의 isFavorite 값을 변경
    fun toggleFavorite(id: Int) {
        val restaurants = state.value.toMutableList()
        val itemIndex = restaurants.indexOfFirst { it.id == id }
        val item = restaurants[itemIndex]
        restaurants[itemIndex] = item.copy(isFavorite = !item.isFavorite)
        state.value = restaurants
    }

}