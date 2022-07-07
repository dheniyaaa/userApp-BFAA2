package com.example.submission2.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.submission2.R
import com.example.submission2.UserGitHub


/*
Di sini kita perlu membuat kelas adaptor yang digunakan untuk mengisi data ke dalam RecyclerView.
Peran adaptor adalah untuk mengubah objek pada suatu posisi menjadi item baris daftar yang akan disisipkan.
 */
class ListUserAdapter(private val userData: ArrayList<UserGitHub>): RecyclerView.Adapter<ListUserAdapter.ListViewHolder>() {

    private var onItemClickCallback: OnItemClickCallback? = null


    interface OnItemClickCallback {
        fun onItemClicked(data: UserGitHub)

    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback){
        this.onItemClickCallback = onItemClickCallback

    }


    //Digunakan untuk inflate / memanggil layout yang akan dipakek dan mengembalikan ke holder (ListViewHolder)
    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ListViewHolder {
        val view: View = LayoutInflater.from(viewGroup.context).inflate(R.layout.item_row_user, viewGroup, false)

        // Return a new holder instance
        return ListViewHolder(view)
    }


    /*
    Adaptor memerlukan keberadaan objek "ViewHolder" yang mendeskripsikan dan menyediakan
    akses ke semua tampilan dalam setiap baris item.
     */
    inner class ListViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        // semua variabel harus di inisiasi
        private var photo: ImageView = itemView.findViewById(R.id.image_profile)
        private var nama: TextView = itemView.findViewById(R.id.name_user)


        //Digunakan untuk membungkus data yang ingin ditampilkan pada setiap item
        fun bind( user: UserGitHub ){

            nama.text = user.username

            Glide.with(itemView.context)
                .load(user.avatarUrl)
                .into(photo)


            itemView.setOnClickListener {
                onItemClickCallback?.onItemClicked(user)
            }
        }


        }


    //fungsi onBindViewHolder untuk mengatur atribut tampilan berdasarkan data
    //// Melibatkan pengisian data ke dalam item melalui holder
    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {

        // Get the data model based on position
        val DATA = userData[position]

        // Set item views based on your views and data model
        holder.bind(DATA)

        //holder yang digunakan ketika item di klik
        holder.itemView.setOnClickListener {
            onItemClickCallback?.onItemClicked(userData[holder.adapterPosition])

        }

    }

    //Digunakan untuk menentukan jumlah item yang ingin ditampilkan
    override fun getItemCount(): Int {
        return userData.size
    }



}










