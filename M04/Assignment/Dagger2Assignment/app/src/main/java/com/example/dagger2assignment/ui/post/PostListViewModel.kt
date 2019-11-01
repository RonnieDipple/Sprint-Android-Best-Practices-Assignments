package com.example.dagger2assignment.ui.post

import android.view.View
import androidx.lifecycle.MutableLiveData
import com.example.dagger2assignment.Base.BaseViewModel
import com.example.dagger2assignment.Network.PostApi
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class PostListViewModel: BaseViewModel(){
//in order to get the result from API. This instance will be injected by Dagger
    @Inject
    lateinit var postApi: PostApi
//All this gets the results
    private lateinit var subscription: Disposable

    //MutableLiveData
    val loadingVisibility: MutableLiveData<Int> = MutableLiveData()
    init {
        loadPosts()
    }

    private fun loadPosts(){
        subscription = postApi.getposts()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { onRetrievePostListStart()}
            .doOnTerminate{ onRetrievePostListFinish()}
            .subscribe({ onRetrievePostListSuccess()},
                {onRetrievePostListError()}

            )
    }

    private fun onRetrievePostListStart(){
        //makes the view visible, pay attention to the function names
        loadingVisibility.value = View.VISIBLE

    }

    private fun onRetrievePostListFinish(){
        //makes the view gone
        loadingVisibility.value = View.GONE

    }

    private fun onRetrievePostListSuccess(){

    }

    private fun onRetrievePostListError(){

    }

    //when the view model is no longer in use it should be disposed of
    override fun onCleared() {
        super.onCleared()
        subscription.dispose()
    }
}