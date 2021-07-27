package com.incorps.inapps.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.incorps.inapps.R
import com.incorps.inapps.model.OrdersDesain
import com.incorps.inapps.utils.Tools

class OrdersDesainAdapter(private val listDesain: List<OrdersDesain>) :
    RecyclerView.Adapter<OrdersDesainAdapter.GridViewHolder>() {

    private lateinit var onItemClickCallback: OnItemClickCallback

    inner class GridViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imgProduct: ImageView = itemView.findViewById(R.id.img_product)
        var tvTitle: TextView = itemView.findViewById(R.id.tv_title)
        var tvLamaPengerjaan: TextView = itemView.findViewById(R.id.tv_lama_pengerjaan)
        var tvPrice: TextView = itemView.findViewById(R.id.tv_price)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): GridViewHolder {
        val view: View = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item_order_desain, viewGroup, false)
        return GridViewHolder(view)
    }

    override fun onBindViewHolder(holder: GridViewHolder, position: Int) {
        // Image Product
        Glide.with(holder.itemView.context)
            .load(Tools.getProductDrawableById(listDesain[position].product.toInt()))
            .into(holder.imgProduct)

        // Teks Title
        holder.tvTitle.text = Tools.getProductNameById(listDesain[position].product.toInt())

        // Teks Lama Pengerjaan
        val lama = listDesain[position].waktu_pengerjaan
        val textLamaPengerjaan = "$lama Hari Pengerjaan"
        holder.tvLamaPengerjaan.text = textLamaPengerjaan

        // Teks Price
        holder.tvPrice.text = Tools.getCurrencySeparator(listDesain[position].price)

        holder.itemView.setOnClickListener {
            onItemClickCallback.onItemClicked(listDesain[holder.adapterPosition])
        }
    }

    override fun getItemCount(): Int {
        return listDesain.size
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: OrdersDesain)
    }
}