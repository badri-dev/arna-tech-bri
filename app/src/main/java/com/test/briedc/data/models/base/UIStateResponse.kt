package com.test.briedc.data.models.base

/**
Sealed classes and interfaces provide controlled inheritance of your class hierarchies.
All direct subclasses of a sealed class are known at compile time.
this interface will being pattern for our response API **/
sealed interface UIStateResponse<out T> {
    object Idle : UIStateResponse<Nothing> // we need empty state for handle infinity recompose
    object Loading : UIStateResponse<Nothing>
    data class Success<out T>(val response: T) : UIStateResponse<T>
    data class Error(val message: String) : UIStateResponse<Nothing>
}