package com.auldy.makananindonesia

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.auldy.makananindonesia.databinding.ItemRowMakananBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class ListMakananAdapter(val listMakanan: ArrayList<Makanan>) :
    RecyclerView.Adapter<ListMakananAdapter.ListViewHolder>() {

    private lateinit var onItemClickCallback: OnItemClickCallback

    inner class ListViewHolder(val binding: ItemRowMakananBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ListViewHolder {
        val binding = ItemRowMakananBinding.inflate(
            LayoutInflater.from(viewGroup.context), viewGroup, false
        )
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val makan = listMakanan[position]
        Glide.with(holder.itemView.context)
            .load(makan.photo)
            .apply(RequestOptions().override(55, 55))
            .into(holder.binding.imgItemPhoto)
        holder.binding.tvItemName.text = makan.nama
        holder.binding.tvItemDetail.text = makan.detail
        holder.itemView.setOnClickListener {
            onItemClickCallback.onItemClicked(listMakanan[holder.adapterPosition])
        }
    }

    override fun getItemCount(): Int = listMakanan.size

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: Makanan)
    }
}