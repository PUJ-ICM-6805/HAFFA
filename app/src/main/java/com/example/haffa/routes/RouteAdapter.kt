package com.example.haffa.routes

import android.annotation.SuppressLint
import android.content.Context
import android.icu.text.SimpleDateFormat
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.haffa.R
import com.example.haffa.databinding.CardRouteBinding
import java.util.Locale

class RouteAdapter(private val context: Context, private val routes: List<Route>) : RecyclerView.Adapter<RouteAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val binding =
            CardRouteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentRoute = routes[position]
        holder.bindData(currentRoute)

        holder.itemView.setOnClickListener{

            val bundle = Bundle()
            bundle.putSerializable("route", currentRoute)

            val fragmentTransaction = (holder.itemView.context as AppCompatActivity).supportFragmentManager.beginTransaction()
            val newFragment = ShowSelectedRouteFragment()
            newFragment.arguments = bundle
            fragmentTransaction.replace(R.id.frame_container, newFragment)
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
        }

    }

    override fun getItemCount(): Int {
        return routes.size
    }

    inner class ViewHolder(private val binding: CardRouteBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SimpleDateFormat")
        fun bindData(currentRoute: Route){
            binding.title.text = currentRoute.name


            val dateFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
            val formattedDate = dateFormat.format(currentRoute.date)
            binding.date.text = formattedDate

            Glide.with(context).load(currentRoute.imgUrl).into(binding.cardImg)

        }
    }
}