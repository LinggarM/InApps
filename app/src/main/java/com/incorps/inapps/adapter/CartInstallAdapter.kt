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
import com.incorps.inapps.room.Install
import com.incorps.inapps.room.Rental
import com.incorps.inapps.utils.Tools

class CartInstallAdapter : RecyclerView.Adapter<CartInstallAdapter.GridViewHolder>() {

    private lateinit var context: Context
    private lateinit var alertDialog: AlertDialog
    private lateinit var builder: AlertDialog.Builder

    private var listInstall = emptyList<Install>()
    private lateinit var cartViewModel: CartViewModel

    inner class GridViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imgProduct: ImageView = itemView.findViewById(R.id.img_product)
        var tvTitle: TextView = itemView.findViewById(R.id.tv_title)
        var tvPrice: TextView = itemView.findViewById(R.id.tv_price)
        var btnDelete: TextView = itemView.findViewById(R.id.btn_delete)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): GridViewHolder {
        val view: View = LayoutInflater.from(viewGroup.context).inflate(R.layout.item_cart_install, viewGroup, false)
        return GridViewHolder(view)
    }

    override fun onBindViewHolder(holder: GridViewHolder, position: Int) {
        // Teks Title
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
        holder.tvPrice.text = Tools.getCurrencySeparator(listInstall[position].price.toLong())

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
                    cartViewModel.deleteInstall(listInstall[position])
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
        return listInstall.size
    }

    fun setData(installList: List<Install>) {
        this.listInstall = installList
        notifyDataSetChanged()
    }

    fun setViewModel(viewModel: CartViewModel) {
        this.cartViewModel = viewModel
    }

    fun setContext(context: Context) {
        this.context = context
    }
}