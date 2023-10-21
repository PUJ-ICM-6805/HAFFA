package com.example.haffa.service

import com.example.haffa.model.Route
import com.example.haffa.repository.RouteRepository

class RouteService {
    private val routeRepository = RouteRepository()

    fun save(route: Route) {
        routeRepository.save(route)
    }

    fun getAll(callback: (List<Route>) -> Unit) {
        routeRepository.getAll(callback)
    }
}