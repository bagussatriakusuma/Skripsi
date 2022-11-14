package com.example.ecommerceskripsi.UI.main.account

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ecommerceskripsi.Data.api.auth.getUser.GetUserResponse
import com.example.ecommerceskripsi.Repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class AccountViewModel @Inject constructor(private val repo: AuthRepository, private val repoAuth: AuthRepository): ViewModel() {
    val showLogin: MutableLiveData<Boolean> = MutableLiveData()
    val showError: MutableLiveData<String> = MutableLiveData()
    val showUser: MutableLiveData<GetUserResponse> = MutableLiveData()

    fun clearCredential(){
        viewModelScope.launch {
            repo.clearToken()
            withContext(Dispatchers.Main){
                showLogin.postValue(true)
            }
        }
    }

    fun getUserData(){
        CoroutineScope(Dispatchers.IO).launch {
            val result = repoAuth.getUser(token = repoAuth.getToken()!!)
            withContext(Dispatchers.Main){
                if (result.isSuccessful){
                    //show data
                    val data = result.body()
                    showUser.postValue(data!!)
                }else{
                    //show error
                    val data = result.errorBody()
                    showError.postValue(data.toString())
                }
            }
        }
    }
}