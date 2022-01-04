package com.example.pam.AntiqueList

import android.content.res.Resources
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.os.bundleOf
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.example.pam.R
import kotlinx.android.synthetic.main.antique_list_element.view.*
const val KEY  = "KEY"

class AntiqueEntryAdapter(var dataset: List<AntiqueDTO>,
                          private val resources: Resources,
                          private val redirect: (Int) -> (Unit),
) : RecyclerView.Adapter<AntiqueEntryAdapter.AntiqueViewHolder>() {

    class AntiqueViewHolder(val view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AntiqueViewHolder {
        val textView = LayoutInflater.from(parent.context)
            .inflate(R.layout.antique_list_element, parent, false)
        return AntiqueViewHolder(textView)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onBindViewHolder(holder: AntiqueViewHolder, position: Int) {
        val antique = dataset[position]
        holder.view.name.text = antique.name
        val distanceText = if (antique.distance > 1000)
            "${antique.distance.toDouble() / 1000} km"
        else
            "${antique.distance} m"
        holder.view.distance.text = resources.getString(R.string.from_here, distanceText)
        holder.view.image.setImageResource(antique.photo)

        holder.itemView.setOnClickListener{ redirect(antique.id) }

    }

    override fun getItemCount() = dataset.size
}