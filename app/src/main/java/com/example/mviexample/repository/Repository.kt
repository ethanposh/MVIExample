package com.example.mviexample.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.example.mviexample.api.MyRetrofitBuilder
import com.example.mviexample.ui.main.state.MainViewState
import com.example.mviexample.util.ApiEmptyResponse
import com.example.mviexample.util.ApiErrorResponse
import com.example.mviexample.util.ApiSuccessResponse


object Repository {

    fun getBlogPosts(): LiveData<MainViewState> {
        return Transformations
            .switchMap(MyRetrofitBuilder.apiService.getBlogPosts()) { apiResponse ->
                object : LiveData<MainViewState>() {
                    override fun onActive() {
                        super.onActive()
                        when (apiResponse) {
                            is ApiSuccessResponse -> {
                                value = MainViewState(
                                    blogPosts = apiResponse.body
                                )
                            }
                            is ApiErrorResponse -> {
                                value = MainViewState() // Handle Error?
                            }

                            is ApiEmptyResponse -> {
                                value = MainViewState() // Handle Empty? (error)
                            }
                        }
                    }
                }
            }
    }

    fun getUser(userId: String): LiveData<MainViewState> {
        return Transformations
            .switchMap(MyRetrofitBuilder.apiService.getUser(userId)) { apiResponse ->
                object : LiveData<MainViewState>() {
                    override fun onActive() {
                        super.onActive()
                        when (apiResponse) {
                            is ApiSuccessResponse -> {
                                value = MainViewState(
                                    user = apiResponse.body
                                )
                            }
                            is ApiErrorResponse -> {
                                value = MainViewState() // Handle Error?
                            }

                            is ApiEmptyResponse -> {
                                value = MainViewState() // Handle Empty? (error)
                            }
                        }
                    }
                }
            }
    }
}