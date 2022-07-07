package com.example.submission2.ui.search


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.submission2.ApiConfig
import com.example.submission2.BuildConfig
import com.example.submission2.UserGitHub
import com.example.submission2.UserListSearch
import retrofit2.Call
import retrofit2.Callback

//Kelas view model digunakan untuk memisahkan logic data dengan ui
class SearchViewModel : ViewModel() {

    //inisiasi variabel loading ke dalam type mutableLiveData dan boolean
    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> = _isLoading

    //inisiasi variabel data user github ke dalam type mutableLiveData dan arraylist
    private val _listUser = MutableLiveData<ArrayList<UserGitHub>>()
    val lisUser: LiveData<ArrayList<UserGitHub>> = _listUser

    //Memanggil key API dari gradle
    private val apiKey = BuildConfig.API_KEY


    /* Cara Kerja kode fungsi dibawah :
    1. Apabila isLoading bernilai true, maka dilakukan request API
    2.Jika server API merespon , maka fungsi onResponse berjalan, isloading akan bernilai flase, dan
    listuser akan ditampilkan
    3. Jika server gagal, makan fungsi onFailure akan berjalan, loading bernilai false, dan
    list user tidak ditampilkan
     */
    fun searchListUser(username: String) {
        _isLoading.value = true

        //Digunakan untuk membungkus link API, config API, dan authorization API
        val client = ApiConfig.getApiService().searchUser(apiKey, username)

        //Digunakan untuk melakukan request API
        client.enqueue(object : Callback<UserListSearch> {

            //Fungsi yang dijalankan ketika server API merespon
            override fun onResponse(call: Call<UserListSearch>, response: retrofit2.Response<UserListSearch>) {
                _isLoading.value = false
                _listUser.value = response.body()?.items
            }

            //Fungsi yang dijalankan ketika server API gagal merespon
            override fun onFailure(call: Call<UserListSearch>, t: Throwable) {
                _isLoading.value = false
                _listUser.value = ArrayList()
            }

        })
    }


}