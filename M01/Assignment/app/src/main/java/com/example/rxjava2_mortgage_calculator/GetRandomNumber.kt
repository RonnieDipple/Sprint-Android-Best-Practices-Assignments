package com.example.rxjava2_mortgage_calculator

import android.telecom.Call
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface GetRandomNumber {

    @GET("jsonI.php")
    fun getMortgageNum(@Path("type") number: Int): Single<GetRandomNumber>
}