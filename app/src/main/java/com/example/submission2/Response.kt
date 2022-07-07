package com.example.submission2


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

/*
1. Class yang berisi berbagai macam model untuk menampung data dari JSON
2. Pastikan bahwa property yang di dalam SerializedName memiliki
nilai yang sama persis dengan key yang pada respon JSON.
3. SerializedName berfungsi untuk menandai suatu variabel untuk dimasukkan
data dengan key yang sesuai dari JSON. Misalnya variabel avatarUrl akan dimasukkan data json dengan key "avatar_url"


*/
@Parcelize
data class UserListSearch(
	val items: ArrayList<UserGitHub>
):Parcelable

@Parcelize
data class UserGitHub(

	@field:SerializedName("followers")
	val followers: Int,

	@field:SerializedName("avatar_url")
	val avatarUrl: String,

	@field:SerializedName("following")
	val following: Int,

	@field:SerializedName("name")
	val name : String?,

	@field:SerializedName("login")
	val username: String,

): Parcelable


@Parcelize
class ParcelDetailUser(
	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("login")
	val username: String,

	@field:SerializedName("company")
	val company: String,

	@field:SerializedName("location")
	val location: String,

	@field:SerializedName("public_repos")
	val publicRepos: Int,

	@field:SerializedName("avatar_url")
	val avatarUrl: String,

	@field:SerializedName("followers")
	val followers: Int,

	@field:SerializedName("following")
	val following: Int,
): Parcelable
