package com.example.submission2

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.submission2.Const.Const
import com.example.submission2.databinding.ActivityDetailUser2Binding
import com.example.submission2.ui.home.ListUserAdapter
import com.google.android.material.tabs.TabLayout


class DetailUser2 : AppCompatActivity(), ListUserAdapter.OnItemClickCallback {
    private lateinit var binding: ActivityDetailUser2Binding

    //Inisiasi kelas DetailViewModel
    private lateinit var getdetailViewModel: DetailViewModel

    //Inisiasi class ParcelDetailUser pada file Response.kt
    private var pdetailUser: ParcelDetailUser? = null

    //Digunakan sebagai instance kelas DetailUser2 agar nanti mudah dipanggil dari kelas MainActivity
    companion object{
        const val DATA_USER = "DATA_USER"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailUser2Binding.inflate(layoutInflater)
        setContentView(binding.root)


        //Digunakan untuk memanggil fungsi initViewModel
        initViewModel()

        //Digunakan untuk memanggil fungsi initListener
        initListener()

        //Digunakan untuk memanggil kelas observerliveData
        observerLiveData()



    }

    /*
    1. Digunakan untuk inisiasi  kelas DetailViewModel
    2. Digunakan panggil fungsi funDetailUser dari kelas DetaiViewModel
     */
    private fun initViewModel(){
        getdetailViewModel =  ViewModelProvider(this).get(DetailViewModel::class.java)

        val user: UserGitHub? = intent.getParcelableExtra(DATA_USER)
        user?.let {
            getdetailViewModel.funDetailUser(it.username) }

    }

    /*Fungsi ini digunakan untuk mendapatkan follower dan followimmg
    1. Apabila tablayout yang dipilih pada index / urutan ke  0, maka tab tersebut
    akan menampilkan data followers
    2.Apabila tablayout yang dipilih pada index / urutan BUKAN ke 0, maka tab tersebut
    akan menampilkan data following

     */
    private fun initListener(){

        //Digunakan sebagai listener ketika pindah tab
        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {

                //Akan mmemanggil fungsi getFollowersUser pada kelas DetailViewModel
                if (tab == binding.tabLayout.getTabAt(0)){
                    pdetailUser?.username?.let { username ->
                        getdetailViewModel.getFollowersUser(username)
                    }
                }
                //Akan mmemanggil fungsi getFollowing User pada kelas DetailViewModel
                else {
                    pdetailUser?.username?.let { username ->
                        getdetailViewModel.getFollowingUser(username)
                    }
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }
        })

    }

    /*

     */
    private fun observerLiveData(){

        /*Digunakan untuk mengatur gambar progressnCircular pada detail user
        1. Apabila isloading bernilai true, maka progressnCircular akan keliahatan
        2. jika sebaliknya, maka progressnCircular akan hilang
         */

        getdetailViewModel.isLoading.observe(this) { isLoading ->
            if (isLoading) {
                binding.progressCircular.visibility = View.VISIBLE
            } else {
                binding.progressCircular.visibility = View.GONE
            }
        }


        //Digunakan untuk get data detail user
        getdetailViewModel.detailUser.observe(this) {
            getdetailViewModel.getFollowersUser(it.username)
            pdetailUser = it
            setView()
        }


        //Digunakan untuk mengatur loading pada tab follower dan followiing
        getdetailViewModel.isLoadingTab.observe(this) { isLoading ->
            if (isLoading) {
                showMessage(false)
                binding.rvUsers.visibility = View.GONE
                binding.progressCircularTab.visibility = View.VISIBLE
            } else {
                binding.progressCircularTab.visibility = View.GONE
            }
        }

        //Digunakan untuk mengaatur list user follower dan following
        getdetailViewModel.lisUser.observe(this) {
            if (it.isNullOrEmpty()) {
                binding.rvUsers.visibility = View.GONE
                showMessage(true)
            } else {
                binding.rvUsers.visibility = View.VISIBLE
                setRecyclerView(it)
                showMessage(false)
            }
        }


    }

    /*
    RecyclerView digunakan ketika data kita mmeiliki data yang elemenya berubah - ubah pada saat
    user action atau network event
     */
    private fun setRecyclerView(list: ArrayList<UserGitHub>){

        //Inisiasi adapter
        val adapter = ListUserAdapter(list)

        //Inisiasi layout manager Memposisikan item secara vertikal
        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        adapter.setOnItemClickCallback(this)

        //Untuk nge handle data dan di ikat / dimasukan ke tampilan
        // Attach the adapter to the recyclerview to populate items (melekatkan / mengikatkan adapter ke recyclerview untuk mengisi item
        binding.rvUsers.adapter = adapter

        //Digunakan untuk memposisikan item secara vertikal
        // Set layout manager to position the items
        binding.rvUsers.layoutManager = layoutManager
    }

    //Digunakan untuk meletakan setiap data yang di get ke dalam file xml yang sudah dibuat
    private fun setView(){
        binding.apply {
            name.text = pdetailUser?.name?:"Unknown"
            username.text = pdetailUser?.username?:"-"
            company.text = pdetailUser?.company?:"-"
            location.text = pdetailUser?.location?:"-"
            val textRepo = " Repo : ${pdetailUser?.publicRepos?: 0}"
            publicRepo.text = textRepo
            val textFollowers = "  ${pdetailUser?.followers?: 0} FOLLOWERS"
            followers.text = textFollowers
            val textFollowings = "  ${pdetailUser?.following?: 0} FOLLOWINGS"
            followings.text = textFollowings

            Glide.with(root.context)
                .load(pdetailUser?.avatarUrl?: Const.UriDefaultImage)
                .into(photo)
        }

    }

    //Digunakan untuk mengatur pesan yang ingin ditampilkan pada tab follower dan following
    private fun showMessage(visible: Boolean) {
        val message = if(binding.tabLayout.selectedTabPosition == 0) "They don't have followers."
        else "Not following anyone"

        if (visible){
            binding.tvMessage.apply {
                visibility = View.VISIBLE
                text = message
            }
        } else {
            binding.tvMessage.visibility = View.GONE
        }

    }

    override fun onItemClicked(data: UserGitHub) {
       // TODO("Not yet implemented")
    }
}