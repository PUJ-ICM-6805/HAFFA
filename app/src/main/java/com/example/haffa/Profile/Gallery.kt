package com.example.haffa.Profile

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.GridView

import com.example.haffa.R


class Gallery : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gallery)

        val galleryImages: Array<String> = resources.getStringArray(R.array.gallery_images)

        val adapter = GalleryGridViewAdapter(this, galleryImages)
        val gridView = findViewById<GridView>(R.id.gridItem)
        gridView.adapter = adapter

        gridView.setOnItemClickListener { _, _, position, _ ->
            val intent = Intent(this, publishView::class.java)
            startActivity(intent)
        }
    }
}
