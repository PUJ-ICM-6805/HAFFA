package com.example.haffa.friends

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.haffa.databinding.FragmentFriendsBinding

import com.example.haffa.routes.Route
import java.util.Date

class Friends : Fragment() {

    private lateinit var binding: FragmentFriendsBinding
    private lateinit var adapter: FriendAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentFriendsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView = binding.cardView

        val layoutManager = LinearLayoutManager(requireContext())
        recyclerView.layoutManager = layoutManager

        adapter = FriendAdapter(requireContext(), sampleFriendsData())
        recyclerView.adapter = adapter
    }

    private fun sampleFriendsData(): MutableList<Friend> {
        val sampleFriends = mutableListOf<Friend>()

        sampleFriends.add(
            Friend(
                imgUrl = "https://www.havolinedeportivo.com/wp-content/uploads/2022/08/Lewis-Hamilton.jpg",
                name = "Lewis Hamilton",
                routes = listOf(
                    Route(
                        distance = 50.5,
                        name = "Monserrate",
                        points = 100,
                        duration = 2.5,
                        altitude = 3000,
                        imgUrl = "https://e00-expansion.uecdn.es/assets/multimedia/imagenes/2023/03/27/16799149846473.jpg",
                        date = Date()
                    ),
                    Route(
                        distance = 30.0,
                        name = "La Conejera",
                        points = 75,
                        duration = 1.8,
                        altitude = 3000,
                        imgUrl = "https://e00-expansion.uecdn.es/assets/multimedia/imagenes/2023/03/27/16799149846473.jpg",
                        date = Date()
                    )
                )
            )
        )

        sampleFriends.add(
            Friend(
                imgUrl = "https://as01.epimg.net/motor/imagenes/2021/07/14/formula_1/1626278627_407814_1626295263_noticia_normal_recorte1.jpg",
                name = "Lando Norris",
                routes = listOf(
                    Route(
                        distance = 70.2,
                        name = "Patios",
                        points = 150,
                        duration = 3.2,
                        altitude = 3000,
                        imgUrl = "https://e00-expansion.uecdn.es/assets/multimedia/imagenes/2023/03/27/16799149846473.jpg",
                        date = Date()
                    )
                )
            )
        )

        sampleFriends.add(
            Friend(
                imgUrl = "https://www.f1aldia.com/images/articulos/43000/43884-n3.jpg",
                name = "George Russell",
                routes = listOf(
                    Route(
                        distance = 50.5,
                        name = "Monserrate",
                        points = 100,
                        duration = 2.5,
                        altitude = 3000,
                        imgUrl = "https://e00-expansion.uecdn.es/assets/multimedia/imagenes/2023/03/27/16799149846473.jpg",
                        date = Date()
                    )
                )
            )
        )

        return sampleFriends
    }
}