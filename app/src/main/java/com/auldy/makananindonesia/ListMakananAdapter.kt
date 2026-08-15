package com.auldy.makananindonesia

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.auldy.makananindonesia.data.model.Makanan
import com.auldy.makananindonesia.databinding.ItemRowMakananBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class ListMakananAdapter(
    private var listMakanan: List<Makanan> = emptyList()
) : RecyclerView.Adapter<ListMakananAdapter.ListViewHolder>() {

    private var onItemClickCallback: OnItemClickCallback? = null

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
            val currentPos = holder.bindingAdapterPosition
            if (currentPos != RecyclerView.NO_POSITION) {
                onItemClickCallback?.onItemClicked(listMakanan[currentPos])
            }
        }
    }

    override fun getItemCount(): Int = listMakanan.size

    @SuppressLint("NotifyDataSetChanged")
    fun setData(newList: List<Makanan>) {
        this.listMakanan = newList
        notifyDataSetChanged()
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: Makanan)
    }
}