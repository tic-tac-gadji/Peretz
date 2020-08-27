package ru.diit.peretz.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_food.*
import kotlinx.android.synthetic.main.item_food.view.*
import ru.diit.peretz.R
import ru.diit.peretz.data.models.Products


class AdapterMenu(
    val counterPlus: ((Products)->Unit)?,
    val counterMinus: ((Products)->Unit)?

) : RecyclerView.Adapter<AdapterMenu.ViewHolder>() {

    private var products = arrayListOf<Products>()

    fun setData(items: ArrayList<Products>) {

        this.products.run {
            clear()
            addAll(items)
        }
        notifyDataSetChanged()
    }

    override fun getItemCount() = products.size


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =

        ViewHolder(
            LayoutInflater
                .from(parent.context)
                .inflate(R.layout.item_food, parent, false)
        )


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindData(products[position])
    }

    inner class ViewHolder(
        override val containerView: View
    ) : RecyclerView.ViewHolder(containerView), LayoutContainer {


        fun bindData(products: Products) {

            titleview.text = products.title
            descriptionview.text = products.description
            priceview.text = itemView.resources.getString(R.string.price, products.price)
            products.id

            Picasso.get()
                .load(products.image)
                .into(imageview)

            if (products.quantitiBasket < 1) {
                itemView.buttonminus.visibility = View.GONE
                itemView.textView.visibility = View.GONE
            } else {
                itemView.buttonminus.visibility = View.VISIBLE
                itemView.textView.visibility = View.VISIBLE
                itemView.textView.text = products.quantitiBasket.toString()
            }

            buttonminus.setOnClickListener {
                counterMinus?.invoke(products)
                products.quantitiBasket--
                textView.text = products.quantitiBasket.toString()

                if (products.quantitiBasket == 0) {
                    itemView.buttonminus.visibility = View.GONE
                    itemView.textView.visibility = View.GONE
                }
            }

            buttonplus.setOnClickListener {
                counterPlus?.invoke(products)
                products.quantitiBasket++
                textView.text = products.quantitiBasket.toString()
                itemView.buttonminus.visibility = View.VISIBLE
                itemView.textView.visibility = View.VISIBLE
            }

        }
    }
}
