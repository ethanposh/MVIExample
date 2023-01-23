package com.example.mviexample.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.mviexample.model.BlogPost
import com.example.mviexample.model.User
import com.example.mviexample.repository.Repository
import com.example.mviexample.ui.main.state.MainStateEvent
import com.example.mviexample.ui.main.state.MainStateEvent.*
import com.example.mviexample.ui.main.state.MainViewState
import com.example.mviexample.util.AbsentLiveData
import com.example.mviexample.util.DataState

class MainViewModel : ViewModel() {

    private val _stateEvent: MutableLiveData<MainStateEvent> = MutableLiveData()
    private val _viewState: MutableLiveData<MainViewState> = MutableLiveData()

    val viewState: LiveData<MainViewState>
        get() = _viewState

    val dataState: LiveData<DataState<MainViewState>> = Transformations
        .switchMap(_stateEvent) { stateEvent ->
            stateEvent?.let {
                handleStateEvent(it)
            }
        }

    private fun handleStateEvent(stateEvent: MainStateEvent): LiveData<DataState<MainViewState>> {
        when (stateEvent) {// for each case we need return livedata object
            is GetBlogPostsEvent -> {
                return Repository.getBlogPosts()
            }
            is GetUserEvent -> {
                return Repository.getUser(stateEvent.userId)
            }
            is None -> {
                return AbsentLiveData.create()
            }
        }
    }

    //2
    fun setBlogListData(blogPosts: List<BlogPost>) {
        val update = getCurrentViewStateOrNew()
        update.blogPosts = blogPosts
        _viewState.value = update
    }

    //3
    fun setUser(user: User) {
        val update = getCurrentViewStateOrNew()
        update.user = user
        _viewState.value = update
    }

    //1
    fun getCurrentViewStateOrNew(): MainViewState {
        val value = viewState.value?.let {
            it
        } ?: MainViewState()
        return value
    }

    //4
    fun setStateEvent(event: MainStateEvent) {
        _stateEvent.value = event
    }
}