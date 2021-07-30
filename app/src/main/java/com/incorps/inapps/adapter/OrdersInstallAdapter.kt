package com.incorps.inapps.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.incorps.inapps.R
import com.incorps.inapps.model.OrdersCetak
import com.incorps.inapps.model.OrdersInstall
import com.incorps.inapps.utils.Tools

class OrdersInstallAdapter(private val listInstall: List<OrdersInstall>) : RecyclerView.Adapter<OrdersInstallAdapter.GridViewHolder>() {

    private lateinit var onItemClickCallback: OnItemClickCallback

    inner class GridViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvTitle: TextView = itemView.findViewById(R.id.tv_title)
        var tvPrice: TextView = itemView.findViewById(R.id.tv_price)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): GridViewHolder {
        val view: View = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item_order_install, viewGroup, false)
        return GridViewHolder(view)
    }

    override fun onBindViewHolder(holder: GridViewHolder, position: Int) {
        // Teks Title
        var textOS = ""
        var textSoftware = ""
        var textGame = ""
        if (listInstall[position].os) {
            textOS = "Operating System"
        }
        if (listInstall[position].software_pc) {
            if (listInstall[position].os) {
                textSoftware = ", Software"
            } else {
                textSoftware = "Software"
            }
        }
        if (listInstall[position].game) {
            if (listInstall[position].os || listInstall[position].software_pc) {
                textGame = ", Game"
            } else {
                textGame = "Game"
            }
        }
        val textTitle = "$textOS$textSoftware$textGame"
        holder.tvTitle.text = textTitle

        // Teks Price
        holder.tvPrice.text = Tools.getCurrencySeparator(listInstall[position].price)

        holder.itemView.setOnClickListener {
            onItemClickCallback.onItemClicked(listInstall[holder.adapterPosition])
        }
    }

    override fun getItemCount(): Int {
        return listInstall.size
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: OrdersInstall)
    }
}