package com.incorps.inapps.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.incorps.inapps.R
import com.incorps.inapps.room.CartViewModel
import com.incorps.inapps.room.Cetak
import com.incorps.inapps.utils.Tools

class CartCetakAdapter : RecyclerView.Adapter<CartCetakAdapter.GridViewHolder>() {

    private var listCetak = emptyList<Cetak>()
    private lateinit var cartViewModel: CartViewModel

    inner class GridViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imgProduct: ImageView = itemView.findViewById(R.id.img_product)
        var tvTitle: TextView = itemView.findViewById(R.id.tv_title)
        var tvQty : TextView = itemView.findViewById(R.id.tv_qty)
        var tvPrice: TextView = itemView.findViewById(R.id.tv_price)
        var btnDelete: TextView = itemView.findViewById(R.id.btn_delete)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): GridViewHolder {
        val view: View = LayoutInflater.from(viewGroup.context).inflate(R.layout.item_cart_cetak, viewGroup, false)
        return GridViewHolder(view)
    }

    override fun onBindViewHolder(holder: GridViewHolder, position: Int) {
        // Image Product
        Glide.with(holder.itemView.context)
            .load(Tools.getProductDrawableById(listCetak[position].product))
            .into(holder.imgProduct)

        // Teks Title
        holder.tvTitle.text = Tools.getProductNameById(listCetak[position].product)

        // Teks Qty
        val qty = listCetak[position].quantity
        val textQty = "$qty Pcs"
        holder.tvQty.text = textQty

        // Teks Price
        holder.tvPrice.text = Tools.getCurrencySeparator(listCetak[position].price.toLong())

        // Btn Delete
        holder.btnDelete.setOnClickListener {
            cartViewModel.deleteCetak(listCetak[position])
            notifyDataSetChanged()
        }
    }

    override fun getItemCount(): Int {
        return listCetak.size
    }

    fun setData(cetakList: List<Cetak>) {
        this.listCetak = cetakList
        notifyDataSetChanged()
    }

    fun setViewModel(viewModel: CartViewModel) {
        this.cartViewModel = viewModel
    }
}