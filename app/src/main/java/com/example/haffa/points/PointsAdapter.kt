package com.example.haffa.points

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.haffa.databinding.CardPointsBinding
import com.example.haffa.model.Route


class PointsAdapter(private val context: Context, private var pointsList: List<Route>) : RecyclerView.Adapter<PointsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = CardPointsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val route = pointsList[position]
        holder.bindData(route)
    }

    override fun getItemCount(): Int = pointsList.size

    fun updateData(newList: List<Route>) {
        pointsList = newList
        notifyDataSetChanged()
    }

    inner class ViewHolder(private val binding: CardPointsBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bindData(route: Route) {
            binding.cardPointRouteTv.text = route.points.toString()
            binding.cardDateRouteTv.text = route.localDate
        }
    }
}
