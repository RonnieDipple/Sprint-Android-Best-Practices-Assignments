package com.example.dagger2assignment.Network

import com.example.dagger2assignment.Model.Post
import io.reactivex.Observable
import retrofit2.http.GET


//Interface contains methods which get results from the web

interface PostApi {

    //Will get a list of posts from Api

    @GET("/posts")
    //Make sure the imports are correct for the observable
    fun getposts(): Observable<List<Post>>
}