package com.incorps.inapps.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.incorps.inapps.R
import com.incorps.inapps.model.OrdersRental
import com.incorps.inapps.model.Product
import com.incorps.inapps.utils.Tools

class OrdersRentalAdapter(private val listRental: List<OrdersRental>) : RecyclerView.Adapter<OrdersRentalAdapter.GridViewHolder>() {

    private lateinit var onItemClickCallback: OnItemClickCallback

    inner class GridViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imgProduct: ImageView = itemView.findViewById(R.id.img_product)
        var tvTitle: TextView = itemView.findViewById(R.id.tv_title)
        var tvDate : TextView = itemView.findViewById(R.id.tv_date)
        var tvPrice: TextView = itemView.findViewById(R.id.tv_price)
        var tvQty : TextView = itemView.findViewById(R.id.tv_qty)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): GridViewHolder {
        val view: View = LayoutInflater.from(viewGroup.context).inflate(R.layout.item_order_rental, viewGroup, false)
        return GridViewHolder(view)
    }

    override fun onBindViewHolder(holder: GridViewHolder, position: Int) {
        // Image Product
        Glide.with(holder.itemView.context)
            .load(Tools.getProductDrawableById(listRental[position].product.toInt()))
            .into(holder.imgProduct)

        // Teks Title
        holder.tvTitle.text = Tools.getProductNameById(listRental[position].product.toInt())

        // Teks Date
        var dateText = ""
        var timeText = ""
        if (listRental[position].lama_peminjaman > 24) {
            val datePeminjaman = Tools.getDate(listRental[position].tgl_peminjaman, "EE, dd MMMM")
            val datePengembalian = Tools.getDate(listRental[position].tgl_pengembalian, "EE, dd MMMM")
            dateText = "$datePeminjaman - $datePengembalian"
            timeText = " (${listRental[position].lama_peminjaman / 24} Hari)"
        } else {
            dateText = Tools.getDate(listRental[position].tgl_peminjaman, "EEE, dd MMMM yyyy")
            timeText = if (listRental[position].lama_peminjaman == 24.toLong()) {
                " (1 Hari)"
            } else {
                " (${listRental[position].lama_peminjaman} Jam)"
            }
        }
        val dateTimeText = dateText + timeText
        holder.tvDate.text = dateTimeText

        // Teks Price
        holder.tvPrice.text = Tools.getCurrencySeparator(listRental[position].price)

        // Teks Qty
        val qtyText = "(${listRental[position].quantity} Buah)"
        holder.tvQty.text = qtyText

        holder.itemView.setOnClickListener {
            onItemClickCallback.onItemClicked(listRental[holder.adapterPosition])
        }
    }

    override fun getItemCount(): Int {
        return listRental.size
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: OrdersRental)
    }
}