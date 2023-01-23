package com.example.mviexample.ui.datastatelistener

import com.example.mviexample.util.DataState

interface DataStateListener {

    fun onDataStateChange(dataState: DataState<*>?)
}