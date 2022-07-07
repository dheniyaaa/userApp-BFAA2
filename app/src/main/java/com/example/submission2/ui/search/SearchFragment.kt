package com.example.submission2.ui.search

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.submission2.DetailUser2
import com.example.submission2.UserGitHub
import com.example.submission2.databinding.FragmentSearchBinding
import com.example.submission2.ui.home.ListUserAdapter


class SearchFragment : Fragment(), ListUserAdapter.OnItemClickCallback {

    //Digunakan untuk inisiasi kelas SearcViewModel
    private lateinit var searchViewModel: SearchViewModel

    private lateinit var binding: FragmentSearchBinding

    //Digunakan sebagai instance kelas SearchFragment agar nanti mudah dipanggil dari kelas MainActivity
    companion object {
        fun getInstance() : SearchFragment = SearchFragment()
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        //Pemanggilan kelas searchViewModel
        searchViewModel =
            ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(
                SearchViewModel::class.java
            )

        binding = FragmentSearchBinding.inflate(inflater, container, false)

        //Memanggil fungsi showMessage
        showMessage(true, "Waiting to search user...")

        //Memanggil fungsi showLoading
        showLoading()

        //Memanggil fungsi search
        search()

        return binding.root


    }

    /* Digunakan sebagai TextListener pada searchbar
    1. Ketika ingin menuliskan text pada searchbar, maka fungsi onQueryTextSubmit akan dipanggil
    2. Ketika ingin mengubah teks pada searchbar yang telah diketik, maka fungsi onQueryTextChange akan dipanggil
     */
    private fun search(){
        binding.apply {
            searchBar.setOnQueryTextListener(object  : SearchView.OnQueryTextListener{

                /*Fungsi ini digunakan untuk mengetikkan text pada searchbar
                1. Ketikan text / query berupa string pada searchbar
                2. serachbar akan dihilangkan dari fokus utama atau top call
                3. Panggil fungsi searchListUser dengan memasukan parameter query tersebut pada kelas searchViewModel
                 */
                override fun onQueryTextSubmit(query: String?): Boolean {
                    query?.let {
                        searchBar.clearFocus()
                        searchBar.isFocusable = false
                        searchViewModel.searchListUser(it)
                    }
                    return true
                }

                /*Fungsi ini digunakan untuk mengubah text pada searchbar
                Ketika text dalam searchbar tidak ada perubahan, maka list user tidak akan ditampilkan
                dan muncul pesan waiting to seaarch
                 */
                override fun onQueryTextChange(newText: String?): Boolean {
                    if (newText?.isEmpty() == true){
                        listUser.visibility = View.GONE
                        showMessage(true, "Waiting to search...")
                    }
                    return true
                }

            })
        }
    }


    //Fungsi yang digunakan untuk mengatur gambar loading
    private fun showLoading(){
        /*
        1. apabila isLoading bernilai true, maka progressbar akan terlihat dan list user tidak tampak
        2. apabila isLoading bernilai false, maka progressbar akan menghilang
         */
        searchViewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            if (isLoading) {
                binding.progressBar.visibility = View.VISIBLE
                binding.listUser.visibility = View.GONE
                showMessage(false, null)
            } else {
                binding.progressBar.visibility = View.GONE
            }
        }

        /* Dipanggil untuk menangani apabila terdapat user yang tidak ditemukan
        1. Apabila user tidak ditemukan / isNullorEmpity maka akan tampil pesan user not found
        2. Apabila user ditemukan, akan dipanggil fungsi showRecyclerList
         */
        searchViewModel.lisUser.observe(viewLifecycleOwner) {
            if (it.isNullOrEmpty()) {
                binding.listUser.visibility = View.GONE
                showMessage(true, "User not found!")
            } else {
                binding.listUser.visibility = View.VISIBLE
                showMessage(false, null)
                showRecyclerList(it)

            }
        }
    }

    //Fungsi ini digunakan untuk menampilkan list user
    private fun showRecyclerList(listUser: ArrayList<UserGitHub>){
        val layoutmanager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        val adapter = ListUserAdapter(listUser)
        binding.listUser.adapter = adapter
        binding.listUser.layoutManager = layoutmanager
        adapter.setOnItemClickCallback(this)
    }

    //Digunakan untuk menampilkan pesan ketika masih loading mencari user
    /*
    1. Apabila proses pencarian masih loading, maka imgMessage dan tvMessage / pesan akan terlihat
    2. Ketika proses loading selesai, maka imgMessage tvMessage / pesan akan hilang.
     */
    private fun showMessage(visible: Boolean, msg: String?) {
        binding.apply {
            if (visible){
                imgMessage.visibility = View.VISIBLE
                tvMessage.visibility = View.VISIBLE
                tvMessage.text = msg
            }else {
                imgMessage.visibility = View.GONE
                tvMessage.visibility = View.GONE
            }
        }
    }


    //ketika item user diklik, maka akan pindah/intent ke halaman DetailUser2
    override fun onItemClicked(data: UserGitHub) {
        val intent = Intent(context, DetailUser2::class.java)
        intent.putExtra(DetailUser2.DATA_USER, data)
        startActivity(intent)
    }


}