package ru.diit.peretz.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import kotlinx.android.synthetic.main.fragment_main.*
import ru.diit.peretz.R

class EmptyFragment:Fragment(),View.OnClickListener {
    lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = layoutInflater.inflate(R.layout.fragment_main, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        navController = Navigation.findNavController(view)
        button_show_products.setOnClickListener(this)
        super.onViewCreated(view, savedInstanceState)
    }
    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.button_show_products -> navController.navigate(R.id.action_emptyFragment_to_menuFragment)
        }
    }


}