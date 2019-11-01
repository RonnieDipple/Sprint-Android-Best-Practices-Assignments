package com.example.dagger2assignment.Base

import androidx.lifecycle.ViewModel
import com.example.dagger2assignment.injection.component.DaggerViewModelInjector
import com.example.dagger2assignment.injection.component.ViewModelInjector
import com.example.dagger2assignment.injection.module.NetworkModule
import com.example.dagger2assignment.ui.post.PostListViewModel

//This will be used for dependency injection
//Must be abstract so it can be inherited from
abstract class BaseViewModel: ViewModel() {
//DaggerViewModelInjector needs to be generated, you do that by REBUILDING THE PROJECT

    //Setup
    private val injector: ViewModelInjector = DaggerViewModelInjector
        .builder()
        .networkModule(NetworkModule)
        .build()
    //Method
    private fun inject(){
        when(this){
            is PostListViewModel -> injector.inject(this)
        }
    }
    //Execution
    init {
        inject()
    }


}