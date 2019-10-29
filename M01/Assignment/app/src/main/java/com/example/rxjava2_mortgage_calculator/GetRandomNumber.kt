package com.example.rxjava2_mortgage_calculator

import android.telecom.Call
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GetRandomNumber {

    @GET("jsonI.php")
    fun getRandomNum(@Query("length") length: String, @Query("type") type: String): Single<MortgageNumbers>
}