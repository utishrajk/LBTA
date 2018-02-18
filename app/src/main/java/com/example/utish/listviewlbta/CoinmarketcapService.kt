package com.example.utish.listviewlbta


import io.reactivex.Observable
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface CoinmarketcapService {

    @GET("/v1/ticker/")
    fun getCoins(@Query("limit") limit: String): Observable<List<CoinmarketcapDataSet>>

    companion object {
        fun create(): CoinmarketcapService {
            val retrofit = Retrofit.Builder()
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl("https://api.coinmarketcap.com")
                    .build()
            return retrofit.create(CoinmarketcapService::class.java)
        }
    }


}