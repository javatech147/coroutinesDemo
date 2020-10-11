package com.example.kotlincoroutinesdemo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivityViewModel : ViewModel() {
    private val userRepository = UserRepository()
    private var usersMutableList: MutableLiveData<List<User>> = MutableLiveData()

    companion object {
        val TAG = MainActivityViewModel::class.java.simpleName
    }


    init {
        getUserDataFromAPI()
    }

    private fun getUserDataFromAPI() {
        viewModelScope.launch {
            var result: List<User>? = null
            withContext(Dispatchers.IO) {
                result = userRepository.getUsersList()
                usersMutableList.postValue(result)
                //usersMutableList.value = result   // Will Not Work, Exception
            }
            //usersMutableList.value = result   // Will Work
            //usersMutableList.postValue(result)  // Will Work
        }
    }

    fun getUserData(): LiveData<List<User>> {
        return usersMutableList
    }
}