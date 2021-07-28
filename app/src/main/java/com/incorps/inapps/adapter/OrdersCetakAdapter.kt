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

import com.incorps.inapps.model.OrdersCetak

class OrdersCetakAdapter(private val listCetak: List<OrdersCetak>) :
    RecyclerView.Adapter<OrdersCetakAdapter.GridViewHolder>() {

    private lateinit var onItemClickCallback: OnItemClickCallback

    inner class GridViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imgProduct: ImageView = itemView.findViewById(R.id.img_product)
        var tvTitle: TextView = itemView.findViewById(R.id.tv_title)
        var tvQty: TextView = itemView.findViewById(R.id.tv_qty)
        var tvPrice: TextView = itemView.findViewById(R.id.tv_price)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): GridViewHolder {
        val view: View = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item_order_cetak, viewGroup, false)
        return GridViewHolder(view)
    }

    override fun onBindViewHolder(holder: GridViewHolder, position: Int) {
        // Image Product
        Glide.with(holder.itemView.context)
            .load(Tools.getProductDrawableById(listCetak[position].product.toInt()))
            .into(holder.imgProduct)

        // Teks Title
        holder.tvTitle.text = Tools.getProductNameById(listCetak[position].product.toInt())

        // Teks Lama Pengerjaan
        val qty = listCetak[position].quantity
        val textQty = "$qty Buah"
        holder.tvQty.text = textQty

        // Teks Price
        holder.tvPrice.text = Tools.getCurrencySeparator(listCetak[position].price)

        holder.itemView.setOnClickListener {
            onItemClickCallback.onItemClicked(listCetak[holder.adapterPosition])
        }
    }

    override fun getItemCount(): Int {
        return listCetak.size
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: OrdersCetak)
    }
}