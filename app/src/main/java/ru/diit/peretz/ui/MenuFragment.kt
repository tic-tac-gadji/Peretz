package ru.diit.peretz.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import kotlinx.android.synthetic.main.fragment_menu.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.diit.peretz.NetworkRequest
import ru.diit.peretz.R
import ru.diit.peretz.data.SharedPreferencesStorage
import ru.diit.peretz.data.models.Products


class MenuFragment : Fragment() {
    //private lateinit var recView:RecyclerView

    private var products = arrayListOf<Products>()
    lateinit var navControleer: NavController
    lateinit var sharedPreferencesStorage: SharedPreferencesStorage
    private val prefsManager by lazy {
        SharedPreferencesStorage(requireContext())
    }
    private var adapterMenu = AdapterMenu(
        counterMinus = {
            removeFromCard(it)
        },
        counterPlus = {
            addToCart(it)
        })

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = layoutInflater.inflate(R.layout.fragment_menu, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recView.adapter = adapterMenu
        navControleer = Navigation.findNavController(view)
        toolbar_main.setNavigationOnClickListener {
            navControleer.navigate(R.id.action_menuFragment_to_emptyFragment)
        }

        getData()
    }

    fun addToCart(products: Products) {

        var newValue = true

        val cart = (prefsManager.getCardItems())
        cart.forEach { (key, value) ->
            if (key == products.id) {
                newValue = false
                cart[key] = value + 1
            }
        }

        if (newValue) cart[products.id] = 1

        prefsManager.updateCartItems(cart)
    }


    fun removeFromCard(products: Products) {

        val cart = (prefsManager.getCardItems())
        cart.forEach { (key, value) ->
            if (key == products.id) {
                if (value == 1)
                    cart.remove(key)
                else cart[key] = value - 1
            }
        }

        prefsManager.updateCartItems(cart)
    }

    private fun getData() {
        NetworkRequest()
            .getRequest()
            .getTestApi()
            .getItems()
            .enqueue(object : Callback<ArrayList<Products>> {

                override fun onResponse(
                    call: Call<ArrayList<Products>>,
                    response: Response<ArrayList<Products>>
                ) {
                    response.body()?.let { products.addAll(it) }
                    adapterMenu.setData(items = getSynchronizedProducts(products))
                }

                override fun onFailure(call: Call<ArrayList<Products>>, t: Throwable) {
                    Toast.makeText(context, "Нет подключения к интернету", Toast.LENGTH_SHORT).show()
                }
            })
    }

    private fun getSynchronizedProducts(products: ArrayList<Products>): ArrayList<Products> {

        val cartHashMap = prefsManager.getCardItems()
        val finalProducts = products

        cartHashMap.forEach { (id, quantity) ->
            finalProducts.firstOrNull { id == it.id }?.let {
                it.quantitiBasket = quantity
            }
        }
        return finalProducts
    }

}


