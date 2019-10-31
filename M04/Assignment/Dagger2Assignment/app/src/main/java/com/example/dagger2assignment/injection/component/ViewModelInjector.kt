package com.example.dagger2assignment.injection.component

import com.example.dagger2assignment.injection.module.NetworkModule
import com.example.dagger2assignment.ui.post.PostListViewModel
import dagger.Component
import javax.inject.Singleton


@Singleton
@Component(modules = [(NetworkModule::class)])
interface ViewModelInjector {

    //Injects dependencies into PostListModel,
    // Think of a way more complicated version of putting a string in println
    fun inject(postListViewModel: PostListViewModel)

    @Component.Builder
    interface Builder{
        fun build(): ViewModelInjector

        fun networkModule(networkModule: NetworkModule): Builder
    }

    //Go to inject the dependencies into BaseViewModel

}