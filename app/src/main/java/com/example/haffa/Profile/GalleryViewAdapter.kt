package com.example.haffa.Profile

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import com.squareup.picasso.Picasso

class GalleryGridViewAdapter(private val context: Context, private val galleryImages: Array<String>) : BaseAdapter() {

    override fun getCount(): Int {
        return galleryImages.size
    }

    override fun getItem(position: Int): Any {
        return galleryImages[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val imageView = ImageView(context)
        Picasso.get().load(galleryImages[position]).into(imageView)
        return imageView
    }
}
