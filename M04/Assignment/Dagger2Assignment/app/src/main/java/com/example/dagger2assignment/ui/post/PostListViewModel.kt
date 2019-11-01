package com.example.dagger2assignment.ui.post

import android.view.View
import androidx.lifecycle.MutableLiveData
import com.example.dagger2assignment.Base.BaseViewModel
import com.example.dagger2assignment.Model.Post
import com.example.dagger2assignment.Model.PostDao
import com.example.dagger2assignment.Network.PostApi
import com.example.dagger2assignment.R
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class PostListViewModel(private val postDao: PostDao):BaseViewModel(){
//in order to get the result from API. This instance will be injected by Dagger
    @Inject
    lateinit var postApi: PostApi
//All this gets the results
    private lateinit var subscription: Disposable
    val postListAdapter: PostListAdapter = PostListAdapter()

    //MutableLiveData
    val loadingVisibility: MutableLiveData<Int> = MutableLiveData()

    //For error handling
    val errorMessage:MutableLiveData<Int> = MutableLiveData()
    val errorClickListener = View.OnClickListener { loadPosts() }

    init {
        loadPosts()
    }

    private fun loadPosts(){
        subscription = Observable.fromCallable { postDao.all }
            .concatMap {
                    dbPostList ->
                if(dbPostList.isEmpty())
                    postApi.getposts().concatMap {
                            apiPostList -> postDao.insertAll(*apiPostList.toTypedArray())
                        Observable.just(apiPostList)
                    }
                else
                    Observable.just(dbPostList)
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { onRetrievePostListStart() }
            .doOnTerminate { onRetrievePostListFinish() }
            .subscribe(
                { result -> onRetrievePostListSuccess(result) },
                { onRetrievePostListError() }
            )
    }

    private fun onRetrievePostListStart(){
        //makes the view visible, pay attention to the function names
        loadingVisibility.value = View.VISIBLE
        errorMessage.value = null

    }

    private fun onRetrievePostListFinish(){
        //makes the view gone
        loadingVisibility.value = View.GONE

    }

    private fun onRetrievePostListSuccess(postList:List<Post>){
        postListAdapter.updatePostList(postList)

    }

    private fun onRetrievePostListError(){
        //error message
        errorMessage.value = R.string.post_error

    }

    //when the view model is no longer in use it should be disposed of
    override fun onCleared() {
        super.onCleared()
        subscription.dispose()
    }
}