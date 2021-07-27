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

class OrdersRentalAdapter(private val listRental: List<OrdersRental>) : RecyclerView.Adapter<OrdersRentalAdapter.GridViewHolder>() {

    private lateinit var onItemClickCallback: OnItemClickCallback

    inner class GridViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
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
        holder.tvTitle.text = listRental[position].product
        holder.tvDate.text = listRental[position].tgl_peminjaman.toString()
        holder.tvPrice.text = listRental[position].price.toString()
        holder.tvQty.text = listRental[position].quantity.toString()
    }

    override fun getItemCount(): Int {
        return listRental.size
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: Product)
    }
}