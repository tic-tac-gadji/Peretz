package ru.diit.peretz.data

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

class SharedPreferencesStorage(context: Context) {

    private val pref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    private val gson = Gson()

    fun getCardItems(): HashMap<Int, Int> {
        val storedGsonString = pref.getString(PREF_CART_ITEMS, "")
        val type = object : TypeToken<Map<Int, Int>>() {}.type

        return if (storedGsonString.isNullOrEmpty())
            hashMapOf()
        else
            gson.fromJson(storedGsonString, type) as HashMap<Int, Int>
    }

    fun updateCartItems(cartProps: HashMap<Int, Int>) {
        pref.edit().putString(PREF_CART_ITEMS, gson.toJson(cartProps)).apply()
    }

    companion object {
        const val PREF_NAME = "SHARED_PREFERENCES"
        const val PREF_CART_ITEMS = "PREF_CART_ITEMS"
    }
}

class MapReference<T>(
    private val pref: SharedPreferences,
    private val key: String,
    private val gson: Gson
) : ReadWriteProperty<Any, HashMap<T, T>> {


    override fun getValue(thisRef: Any, property: KProperty<*>): HashMap<T, T> =
        gson.fromJson(pref.getString(key, ""), object : TypeToken<HashMap<T, T>>() {}.type)
            ?: mutableMapOf<T, T>() as HashMap<T, T>

    override fun setValue(thisRef: Any, property: KProperty<*>, value: HashMap<T, T>) {
        pref.edit().putString(key, gson.toJson(value)).apply()
    }
}