package com.example.pghunter.ui.home
import androidx.navigation.fragment.findNavController
import android.widget.Button
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.compose.material3.Button
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pghunter.R
import com.example.pghunter.model.PGItem
import com.example.pghunter.model.PGResponse
import com.example.pghunter.network.ApiClient
import com.example.pghunter.ui.adapters.FeaturedPgAdapter
import com.example.pghunter.ui.details.PgDetailsFragment
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var featuredAdapter: FeaturedPgAdapter
    private lateinit var locationDropdown: AutoCompleteTextView
    private lateinit var genderDropdown: AutoCompleteTextView
    private lateinit var rentEditText: TextInputEditText
    private lateinit var searchButton: MaterialButton

    private val pgList = mutableListOf<PGItem>()      // For RecyclerView
    private val allPGsList = mutableListOf<PGItem>()  // Store all PGs from API

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)



        recyclerView = view.findViewById(R.id.recyclerViewPGs)
        locationDropdown = view.findViewById(R.id.locationDropdown)
        genderDropdown = view.findViewById(R.id.genderDropdown)
        rentEditText = view.findViewById(R.id.rentEditText)
        searchButton = view.findViewById(R.id.btnSearch)

        // RecyclerView setup
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        featuredAdapter = FeaturedPgAdapter(pgList) { pg ->
            // Navigate to PgDetailsFragment
            val bundle = Bundle().apply {
                putParcelable("pg_item", pg)
            }
            parentFragmentManager.beginTransaction()
                .replace(R.id.main_container, PgDetailsFragment().apply { arguments = bundle })
                .addToBackStack(null)
                .commit()
        }
        recyclerView.adapter = featuredAdapter

        setupDropdowns()
        setupSearchButton()
        fetchAllPGs() // load all featured PGs initially

        return view
    }

    private fun setupDropdowns() {
        val locations = listOf("Law Gate", "Bhutani Colony")
        val genders = listOf("Any", "Male", "Female")

        locationDropdown.setAdapter(
            ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, locations)
        )
        genderDropdown.setAdapter(
            ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, genders)
        )
    }

    private fun setupSearchButton() {
        searchButton.setOnClickListener {
            val location = locationDropdown.text.toString().trim()
            val gender = genderDropdown.text.toString().trim()
            val rentText = rentEditText.text.toString().trim()

            val maxRent = rentText.toIntOrNull() ?: Int.MAX_VALUE

            filterPGs(location, gender, maxRent)
        }
    }


    private fun fetchAllPGs() {
        val apiService = ApiClient.apiService
        apiService.getAllPGs().enqueue(object : Callback<PGResponse> {
            override fun onResponse(call: Call<PGResponse>, response: Response<PGResponse>) {
                if (response.isSuccessful && response.body() != null) {
                    val featuredPGs = response.body()!!.data.filter { it.isFeatured }
                    allPGsList.clear()
                    allPGsList.addAll(featuredPGs)

                    // Initially show all featured PGs
                    pgList.clear()
                    pgList.addAll(allPGsList)
                    featuredAdapter.notifyDataSetChanged()
                } else {
                    Toast.makeText(requireContext(), "Failed to load data", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<PGResponse>, t: Throwable) {
                Toast.makeText(requireContext(), "Error: ${t.message}", Toast.LENGTH_SHORT).show()
                Log.e("API_ERROR", t.toString())
            }
        })
    }

    private fun filterPGs(areaInput: String, gender: String, maxRent: Int) {
        val extraKeywords = mapOf(
            "Law Gate" to listOf("LPU", "Lpu"),
            "Bhutani Colony" to listOf("Jazzy Property")
        )

        val filtered = allPGsList.filter { pg ->
            // Area match
            val matchesArea = if (areaInput.isEmpty()) {
                true
            } else {
                val keywords = extraKeywords[areaInput] ?: emptyList()
                pg.area.equals(areaInput, ignoreCase = true) ||
                        keywords.any { kw -> pg.location.contains(kw, ignoreCase = true) }
            }

            // Gender match
            val matchesGender = gender.isEmpty() || gender.equals("Any", ignoreCase = true) || pg.gender.equals(gender, ignoreCase = true)

            // Rent match
            val matchesRent = pg.rent <= maxRent

            Log.d("FILTER_DEBUG_PG", "PG=${pg.id_room}, Area=${pg.area}, Location=${pg.location}, Gender=${pg.gender}, Rent=${pg.rent} -> " +
                    "Area=$matchesArea, Gender=$matchesGender, Rent=$matchesRent")

            matchesArea && matchesGender && matchesRent
        }

        pgList.clear()
        pgList.addAll(filtered)
        featuredAdapter.notifyDataSetChanged()

        Log.d("FILTER_DEBUG", "Area=$areaInput, Gender=$gender, MaxRent=$maxRent, FilteredSize=${filtered.size}")
        Toast.makeText(requireContext(), "Showing ${filtered.size} results", Toast.LENGTH_SHORT).show()
    }

}
