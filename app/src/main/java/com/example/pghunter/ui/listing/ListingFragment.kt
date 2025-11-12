package com.example.pghunter.ui.listing

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
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

class ListingFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: FeaturedPgAdapter
    private lateinit var rentEditText: TextInputEditText
    private lateinit var btnLowToHigh: MaterialButton
    private lateinit var btnHighToLow: MaterialButton

    private val pgList = mutableListOf<PGItem>()
    private val allPGs = mutableListOf<PGItem>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_listing, container, false)

        recyclerView = view.findViewById(R.id.recyclerViewPGs)
        rentEditText = view.findViewById(R.id.rentEditText)
        btnLowToHigh = view.findViewById(R.id.btnSortLowToHigh)
        btnHighToLow = view.findViewById(R.id.btnSortHighToLow)

        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        adapter = FeaturedPgAdapter(pgList) { pg ->
            val bundle = Bundle().apply { putParcelable("pg_item", pg) }
            parentFragmentManager.beginTransaction()
                .replace(R.id.main_container, PgDetailsFragment().apply { arguments = bundle })
                .addToBackStack(null)
                .commit()
        }
        recyclerView.adapter = adapter

        fetchAllPGs()

        btnLowToHigh.setOnClickListener { filterAndSort(lowToHigh = true) }
        btnHighToLow.setOnClickListener { filterAndSort(lowToHigh = false) }

        return view
    }

    private fun fetchAllPGs() {
        val apiService = ApiClient.apiService
        apiService.getAllPGs().enqueue(object : retrofit2.Callback<PGResponse> {
            override fun onResponse(call: retrofit2.Call<PGResponse>, response: retrofit2.Response<PGResponse>) {
                if (response.isSuccessful && response.body() != null) {
                    allPGs.clear()
                    allPGs.addAll(response.body()!!.data)
                    filterAndSort(lowToHigh = true) // default: low to high
                } else {
                    Toast.makeText(requireContext(), "Failed to load PGs", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: retrofit2.Call<PGResponse>, t: Throwable) {
                Toast.makeText(requireContext(), "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun filterAndSort(lowToHigh: Boolean) {
        val maxRent = rentEditText.text.toString().toIntOrNull() ?: Int.MAX_VALUE

        val filtered = allPGs.filter { it.rent <= maxRent }
            .sortedWith(if (lowToHigh) compareBy { it.rent } else compareByDescending { it.rent })

        pgList.clear()
        pgList.addAll(filtered)
        adapter.notifyDataSetChanged()

        Toast.makeText(requireContext(), "Showing ${filtered.size} results", Toast.LENGTH_SHORT).show()
    }
}
