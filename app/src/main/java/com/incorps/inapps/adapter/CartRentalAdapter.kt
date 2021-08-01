package com.incorps.inapps.adapter

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.incorps.inapps.R
import com.incorps.inapps.room.CartViewModel
import com.incorps.inapps.room.Rental
import com.incorps.inapps.utils.Tools

class CartRentalAdapter : RecyclerView.Adapter<CartRentalAdapter.GridViewHolder>() {

    private lateinit var context: Context
    private lateinit var alertDialog: AlertDialog
    private lateinit var builder: AlertDialog.Builder

    private var listRental = emptyList<Rental>()
    private lateinit var cartViewModel: CartViewModel

    inner class GridViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imgProduct: ImageView = itemView.findViewById(R.id.img_product)
        var tvTitle: TextView = itemView.findViewById(R.id.tv_title)
        var tvJam : TextView = itemView.findViewById(R.id.tv_jam)
        var tvQty : TextView = itemView.findViewById(R.id.tv_qty)
        var tvPrice: TextView = itemView.findViewById(R.id.tv_price)
        var btnDelete: TextView = itemView.findViewById(R.id.btn_delete)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): GridViewHolder {
        val view: View = LayoutInflater.from(viewGroup.context).inflate(R.layout.item_cart_rental, viewGroup, false)
        return GridViewHolder(view)
    }

    override fun onBindViewHolder(holder: GridViewHolder, position: Int) {
        // Image Product
        Glide.with(holder.itemView.context)
            .load(Tools.getProductDrawableById(listRental[position].product))
            .into(holder.imgProduct)

        // Teks Title
        holder.tvTitle.text = Tools.getProductNameById(listRental[position].product)

        // Teks Jam
        var timeText = ""
        if (listRental[position].lama_peminjaman > 24) {
            timeText = "${listRental[position].lama_peminjaman / 24} Hari"
        } else {
            timeText = if (listRental[position].lama_peminjaman == 24) {
                "1 Hari"
            } else {
                "${listRental[position].lama_peminjaman} Jam"
            }
        }
        holder.tvJam.text = timeText

        // Teks Qty
        val qtyText = " (${listRental[position].quantity} Buah)"
        holder.tvQty.text = qtyText

        // Teks Price
        holder.tvPrice.text = Tools.getCurrencySeparator(listRental[position].price.toLong())

        // Btn Delete
        holder.btnDelete.setOnClickListener {

            //set alert dialog builder
            builder = AlertDialog.Builder(context)

            //set title for alert dialog
            builder.setTitle(R.string.dialogTitleRemoveCart)

            //set message for alert dialog
            builder.setMessage(R.string.dialogMessageRemoveCart)
            builder.setIcon(R.drawable.ic_baseline_warning_24)

            //set positive and negative button
            builder.apply {
                setPositiveButton("Yes") { dialogInterface, i ->
                    cartViewModel.deleteRental(listRental[position])
                    notifyDataSetChanged()
                }
                setNegativeButton("No") { dialogInterface, i ->

                }
            }

            // Create the AlertDialog
            alertDialog = builder.create()
            alertDialog.show()
        }
    }

    override fun getItemCount(): Int {
        return listRental.size
    }

    fun setData(rentalList: List<Rental>) {
        this.listRental = rentalList
        notifyDataSetChanged()
    }

    fun setViewModel(viewModel: CartViewModel) {
        this.cartViewModel = viewModel
    }

    fun setContext(context: Context) {
        this.context = context
    }
}