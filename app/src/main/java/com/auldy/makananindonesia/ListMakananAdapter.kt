package com.auldy.makananindonesia

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class ListMakananAdapter (val listMakanan: ArrayList<Makanan>) : RecyclerView.Adapter<ListMakananAdapter.ListViewHolder>() {

    private lateinit var onItemClickCallback: OnItemClickCallback

    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var lmaNama: TextView = itemView.findViewById(R.id.tv_item_name)
        var lmaDetail: TextView = itemView.findViewById(R.id.tv_item_detail)
        var lmaPhoto: ImageView = itemView.findViewById(R.id.img_item_photo)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ListViewHolder {
        val view: View = LayoutInflater.from(viewGroup.context).inflate(R.layout.item_row_makanan, viewGroup, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val makan = listMakanan[position]
        Glide.with(holder.itemView.context)
            .load(makan.photo)
            .apply(RequestOptions().override(55, 55))
            .into(holder.lmaPhoto)
        holder.lmaNama.text = makan.nama
        holder.lmaDetail.text = makan.detail
        holder.itemView.setOnClickListener { onItemClickCallback.onItemClicked(listMakanan[holder.adapterPosition]) }
    }

    override fun getItemCount(): Int {
        return listMakanan.size
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: Makanan)
    }
}