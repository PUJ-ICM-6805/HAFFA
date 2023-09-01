package com.example.haffa.journey

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.haffa.R

class JourneyAdapter : RecyclerView.Adapter<JourneyAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.journey_card_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // You can set data or customize your empty card view here
    }

    override fun getItemCount(): Int {
        // Return the number of items in your list
        return 1/* the number of items in your list */
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}

/*
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myapplication.activity.ShowCountryActivity
import com.example.myapplication.databinding.CountryCardLayoutBinding
import com.example.myapplication.model.Country

class CountryListAdapter(private val context: Context, private val countries: List<Country>) :
    RecyclerView.Adapter<CountryListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        //Se infla el layout de la tarjeta usando binding
        val binding = CountryCardLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        //Se retorna el ViewHolder con el layout inflado
        return ViewHolder(binding)
    }

    //Se asignan los datos a cada elemento de la lista
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        //Se obtiene el elemento actual de la lista
        val currentCountry = countries[position]

        //Se asignan los datos al ViewHolder
        holder.bindData(currentCountry)

        //Se asigna el listener al elemento de la lista
        holder.itemView.setOnClickListener {

            //Se crea el intent para pasar a la siguiente actividad
            val intent = Intent(holder.itemView.context, ShowCountryActivity::class.java)
            intent.putExtra("country", currentCountry)
            holder.itemView.context.startActivity(intent)
        }
    }

     //Se retorna la cantidad de elementos de la lista
    override fun getItemCount(): Int {
        return countries.size
    }

    //Se crea la clase ViewHolder que extiende de RecyclerView.ViewHolder y recibe como parámetro el layout de la tarjeta
    //El viewHolder es el encargado de asignar los datos a cada elemento de la lista
    inner class ViewHolder(private val binding: CountryCardLayoutBinding) : RecyclerView.ViewHolder(binding.root) {

        //Se asignan los datos a cada elemento de la tarjeta
        fun bindData(currentCountry: Country) {
            binding.cardName.text = currentCountry.name
            binding.cardNativeName.text = currentCountry.nativeName
            binding.cardCode3.text = currentCountry.code3
            binding.cardCurrencyName.text = currentCountry.currencyName
            binding.cardCurrencySymbol.text = "(" + currentCountry.currencySymbol + ")"

            //Se carga la imagen de la bandera usando Glide
            Glide.with(context).load(currentCountry.flag).into(binding.cardFlag)

            binding.button.setOnClickListener {
                val numericCode = currentCountry.numericCode
                //Se crea el intent para pasar a la siguiente actividad
                val intent = Intent(Intent.ACTION_DIAL)
                intent.data = Uri.parse("tel:$numericCode")
                binding.root.context.startActivity(intent)
            }
        }
    }
}
 */