package com.example.haffa.start

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.haffa.R
import com.example.haffa.databinding.FragmentFinishRouteBinding
import com.example.haffa.databinding.FragmentStartRouteBinding


class FinishRoute : Fragment() {
    private lateinit var binding: FragmentFinishRouteBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentFinishRouteBinding.inflate(inflater, container, false)
        val view = binding.root

        // Obtener una referencia al botón bStartRoute usando View Binding
        val buttonFinishRoute = binding.bFinishRoute

        // Configurar un OnClickListener para el botón
        buttonFinishRoute.setOnClickListener {
            // Crear una instancia del fragmento de destino (FinishRoute)
            val shareRouteFragment = ShareRoute()

            // Obtener el FragmentManager
            val fragmentManager = parentFragmentManager

            // Iniciar la transacción de fragmento para reemplazar StartRoute con FinishRoute
            fragmentManager.beginTransaction()
                .replace(
                    R.id.frame_container,
                    shareRouteFragment
                )
                .addToBackStack(null) // Agregar a la pila de retroceso
                .commit()
        }

        return view
    }
}