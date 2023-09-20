package com.example.haffa.start

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.example.haffa.R
import com.example.haffa.databinding.ActivityLogInBinding
import com.example.haffa.databinding.FragmentStartRouteBinding


class StartRoute : Fragment() {

    private lateinit var binding: FragmentStartRouteBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentStartRouteBinding.inflate(inflater, container, false)
        val view = binding.root

        // Obtener una referencia al botón bStartRoute usando View Binding
        val buttonStartRoute = binding.bStartRoute

        // Configurar un OnClickListener para el botón
        buttonStartRoute.setOnClickListener {
            // Crear una instancia del fragmento de destino (FinishRoute)
            val finishRouteFragment = FinishRoute()

            // Obtener el FragmentManager
            val fragmentManager = parentFragmentManager

            // Iniciar la transacción de fragmento para reemplazar StartRoute con FinishRoute
            fragmentManager.beginTransaction()
                .replace(
                    R.id.frame_container,
                    finishRouteFragment
                )
                .addToBackStack(null) // Agregar a la pila de retroceso
                .commit()
        }

        return view
    }
}
