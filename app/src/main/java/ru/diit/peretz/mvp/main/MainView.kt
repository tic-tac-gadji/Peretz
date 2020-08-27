package ru.diit.peretz.mvp.main

import ru.diit.peretz.data.models.Products
import ru.diit.peretz.mvp.global.BaseView

interface MainView: BaseView {

    fun showCategories(categories: List<Products?>?)
}