package com.example.haffa.start

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.haffa.R
import com.example.haffa.databinding.FragmentFinishRouteBinding
import com.example.haffa.databinding.FragmentShareRouteBinding


class ShareRoute : Fragment() {
    private lateinit var binding: FragmentShareRouteBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentShareRouteBinding.inflate(inflater, container, false)
        val view = binding.root

        // Obtener una referencia al botón bStartRoute usando View Binding
        val buttonbShareRoute = binding.bShareRoute

        // Configurar un OnClickListener para el botón
        buttonbShareRoute.setOnClickListener {
            // Crear una instancia del fragmento de destino (FinishRoute)
            val startRouteFragment = StartRoute()

            // Obtener el FragmentManager
            val fragmentManager = parentFragmentManager

            // Iniciar la transacción de fragmento para reemplazar StartRoute con FinishRoute
            fragmentManager.beginTransaction()
                .replace(
                    R.id.frame_container,
                    startRouteFragment
                )
                .addToBackStack(null) // Agregar a la pila de retroceso
                .commit()
        }

        return view
    }
}