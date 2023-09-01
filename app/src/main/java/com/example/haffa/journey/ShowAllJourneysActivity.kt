package com.example.haffa.journey

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.haffa.databinding.ActivityShowAllJorneysBinding


class ShowAllJourneysActivity : AppCompatActivity() {

    private lateinit var binding: ActivityShowAllJorneysBinding
    private lateinit var journeyAdapter: JourneyAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityShowAllJorneysBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val recyclerView =  binding.cardView

        recyclerView.layoutManager =  androidx.recyclerview.widget.LinearLayoutManager(this)

        journeyAdapter = JourneyAdapter()
        recyclerView.adapter = journeyAdapter

    }
}
/*
package com.example.myapplication.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.adapter.CountryListAdapter
import com.example.myapplication.databinding.ActivityListCountriesBinding
import com.example.myapplication.model.Country
import org.json.JSONObject
import java.io.IOException

class ListCountriesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityListCountriesBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Se infla el layout usando binding
        binding = ActivityListCountriesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Se obtiene el valor del spinner de la actividad anterior
        val region = intent.getStringExtra("region").toString()

        //Se cargan los datos desde el archivo JSON
        val elements: List<Country> = loadCountriesFromJSON(region)

        //Se crea el adapter para el RecyclerView
        val adapter = CountryListAdapter(this, elements)

        //Se asignan los datos a los elementos de la actividad
        binding.regionText.text = region
        binding.countriesRecyclerView.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(this)
        binding.countriesRecyclerView.adapter = adapter
    }

    //Se cargan los datos desde el archivo JSON
    private fun loadCountriesFromJSON(region: String): List<Country> {

        //Se obtiene el archivo JSON como String
        val jsonString = loadJSONFromAsset("paises.json")

        //Se convierte el String a JSONObject
        val jsonObject = loadJSONFromString(jsonString)

        //Se obtiene el array de países
        val countriesJsonArray = jsonObject.getJSONArray("Countries")

        //Se crea la lista de países
        var elements: List<Country> = emptyList()

        //Se recorre el array de países y se obtienen los datos de cada país
        //Se crea un objeto Country con los datos obtenidos y se agrega a la lista
        for (i in 0 until countriesJsonArray.length()) {
            val countryObject = countriesJsonArray.getJSONObject(i)
            if (countryObject.getString("Region").equals(region)) {
                val flag = countryObject.getString("FlagPng")
                val name = countryObject.getString("Name")
                val nativeName = countryObject.getString("NativeName")
                val code2 = countryObject.getString("Alpha2Code")
                val code3 = countryObject.getString("Alpha3Code")
                val currencyName = countryObject.getString("CurrencyName")
                val currencySymbol = countryObject.getString("CurrencySymbol")
                val currencyCode = countryObject.getString("CurrencyCode")
                val region = countryObject.getString("Region")
                val subRegion = countryObject.getString("SubRegion")
                val latitude = countryObject.getString("Latitude")
                val longitude = countryObject.getString("Longitude")
                val numericCode = countryObject.getString("NumericCode")


                val country = Country(
                    flag,
                    name,
                    nativeName,
                    code2,
                    code3,
                    currencyName,
                    currencySymbol,
                    currencyCode,
                    region,
                    subRegion,
                    latitude,
                    longitude,
                    numericCode
                )

                elements += country
            }
        }
        return elements
    }

    //Función para obtener el archivo JSON como String
    private fun loadJSONFromAsset(assetName: String): String {
        val json: String = try {
            val inputStream = this.assets.open(assetName)
            val size = inputStream.available()
            val buffer = ByteArray(size)
            inputStream.read(buffer)
            inputStream.close()
            String(buffer, charset("UTF-8"))
        } catch (ex: IOException) {
            ex.printStackTrace()
            ""
        }
        return json
    }

    //Función para convertir el String a JSONObject
    private fun loadJSONFromString(jsonString: String): JSONObject {
        return try {
            JSONObject(jsonString)
        } catch (ex: org.json.JSONException) {
            ex.printStackTrace()
            JSONObject()
        }
    }
}
 */