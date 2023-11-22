package com.example.haffa.routes

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.example.haffa.R
import com.example.haffa.databinding.CardRouteBinding
import com.example.haffa.model.Route

class RouteAdapter(private val context: Context, private var routes: List<Route>) : RecyclerView.Adapter<RouteAdapter.ViewHolder>() {

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

    @SuppressLint("NotifyDataSetChanged")
    fun updateData(newRoutes: List<Route>) {
        this.routes = newRoutes
        notifyDataSetChanged()
    }

    inner class ViewHolder(private val binding: CardRouteBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindData(currentRoute: Route){
            binding.title.text = currentRoute.name
            binding.date.text = currentRoute.localDate

            Glide.with(context)
                .load(currentRoute.imgUrl)
                .centerCrop()
                .transform(CircleCrop())
                .into(binding.cardImg)
        }
    }
}