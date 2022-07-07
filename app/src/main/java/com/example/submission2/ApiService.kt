package com.example.submission2

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

/*
- kelas ini digunakan untuk meletakan end point (bagian akhir url) api dan authorazation
- base url sudah ada pada kelas ApiConfig
- @Headers : untuk menambahkan informasi tambahan pada header request seperti Authorization, jenis data dsb.
- @Path : untuk memasukkan variabel yang dapat diubah-ubah pada endpoint
- @Query : untuk memasukkan parameter pada method @Get.
 */
interface ApiService {
    @GET("users/{username}")
    fun getData(
        @Header("Authorization") auth: String,
        @Path("username")username: String
    ): Call<UserGitHub>

    @GET("search/users")
    fun searchUser(
        @Header("Authorization") auth: String,
        @Query("q") name: String
    ):Call<UserListSearch>

    @GET("users/{username}")
    fun DataDetailUser(
        @Header("Authorization") auth: String,
        @Path("username") username: String?
    ): Call<ParcelDetailUser>

    @GET("users/{username}/followers")
    fun DataFollowersUser(
        @Header("Authorization") auth: String,
        @Path("username") username: String
    ): Call<ArrayList<UserGitHub>>

    @GET("users/{username}/following")
    fun DataFollowingUser(
        @Header("Authorization") auth: String,
        @Path("username") username: String
    ): Call<ArrayList<UserGitHub>>
}