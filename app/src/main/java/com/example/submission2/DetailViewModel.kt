package com.example.submission2

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback

//Kelas view model digunakan untuk memisahkan logic data dengan ui
class DetailViewModel : ViewModel() {

    //inisiasi variabel loading ke dalam type mutableLiveData dan boolean
    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> = _isLoading

    //inisiasi variabel isloading ke dalam type mutableLiveData dan boolean
    private val _isLoadingTab = MutableLiveData(false)
    val isLoadingTab: LiveData<Boolean> = _isLoadingTab

    //inisiasi variabel data user github ke dalam type mutableLiveData, arraylist, dan memasukkan parcelable API dari kelas Response.kt
    private val _listUser = MutableLiveData<ArrayList<UserGitHub>>()
    val lisUser: LiveData<ArrayList<UserGitHub>> = _listUser

    //inisiasi variabel data user github ke dalam type mutableLiveData dan memasukkan parcelable API dari kelas Response.kt
    private val _detailUser= MutableLiveData<ParcelDetailUser>()
    val detailUser: LiveData<ParcelDetailUser> = _detailUser

    //Memanggil key API dari gradle
    private val apiKey = BuildConfig.API_KEY



    /*Fungsi yang digunakan untuk mendapatkan data detail dari user github
    1. Apabila isLoading bernilai true, maka dilakukan request API
    2.Jika server API merespon , maka fungsi onResponse berjalan,
    - Akan memanggil class ParcelDetailuser pada Response.kt
    - isloading akan bernilai flase, dan
    - Detail user  akan ditampilkan
    3. Jika server gagal, makan fungsi onFailure akan berjalan dan loading bernilai false
     */
    fun funDetailUser(username: String?){
        _isLoading.value = true

        //Digunakan untuk membungkus link API, config API, dan authorization API
        val client = ApiConfig.getApiService().DataDetailUser(apiKey, username)

        //Digunakan untuk melakukan request API
        client.enqueue(object : Callback<ParcelDetailUser> {

            //Fungsi yang dijalankan ketika server API merespon
            override fun onResponse(
                call: Call<ParcelDetailUser>,
                response: retrofit2.Response<ParcelDetailUser>
            ) {
                _isLoading.value = false
                _detailUser.value = response.body()
            }

            //Fungsi yang dijalankan ketika server API gagal merespon
            override fun onFailure(call: Call<ParcelDetailUser>, t: Throwable) {
                _isLoading.value = false
            }

        })
    }



    /* Fungsi ini digunakan untuk mendapatkan data follower dari user
    1. Apabila isLoadingTab bernilai true, maka dilakukan request API
    2.Jika server API merespon , maka fungsi onResponse berjalan,
    - Akan memanggil class UserGithub pada Response.kt
    - isloadingTab akan bernilai false
    - list user  akan ditampilkan
    3. Jika server gagal, makan fungsi onFailure akan berjalan dan isloadingTab bernilai false, tidak ada data yang ditampilkan
     */
    fun getFollowersUser(username: String){
        _isLoadingTab.value = true
        val client = ApiConfig.getApiService().DataFollowersUser(apiKey, username)
        client.enqueue(object : Callback<ArrayList<UserGitHub>>{
            override fun onResponse(
                call: Call<ArrayList<UserGitHub>>,
                response: retrofit2.Response<ArrayList<UserGitHub>>
            ) {
                _isLoadingTab.value = false
                _listUser.value = response.body()
            }

            override fun onFailure(call: Call<ArrayList<UserGitHub>>, t: Throwable) {
                _isLoadingTab.value = false
                _listUser.value = ArrayList()
            }

        })
    }

    /*Fungsi ini digunakan untuk mendapatkan data following dari user
    1. Apabila isLoadingTab bernilai true, maka dilakukan request API
    2.Jika server API merespon , maka fungsi onResponse berjalan,
    - Akan memanggil class UserGithub pada Response.kt
    - isloadingTab akan bernilai false
    - list user  akan ditampilkan
    3. Jika server gagal, makan fungsi onFailure akan berjalan dan isloadingTab bernilai false, tidak ada data yang ditampilkan
     */
    fun getFollowingUser(username: String){
        _isLoadingTab.value = true
        val client = ApiConfig.getApiService().DataFollowingUser(apiKey, username)
        client.enqueue(object : Callback<ArrayList<UserGitHub>>{
            override fun onResponse(
                call: Call<ArrayList<UserGitHub>>,
                response: retrofit2.Response<ArrayList<UserGitHub>>
            ) {
                _isLoadingTab.value = false
                _listUser.value = response.body()
            }

            override fun onFailure(call: Call<ArrayList<UserGitHub>>, t: Throwable) {
                _isLoadingTab.value = false
                _listUser.value = ArrayList()
            }

        })
    }
}