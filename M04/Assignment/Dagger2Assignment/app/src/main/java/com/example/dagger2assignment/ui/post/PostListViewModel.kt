package com.example.dagger2assignment.ui.post

import com.example.dagger2assignment.Base.BaseViewModel
import com.example.dagger2assignment.Network.PostApi
import javax.inject.Inject

class PostListViewModel: BaseViewModel(){
//in order to get the result from API. This instance will be injected by Dagger
    @Inject
    lateinit var postApi: PostApi
}