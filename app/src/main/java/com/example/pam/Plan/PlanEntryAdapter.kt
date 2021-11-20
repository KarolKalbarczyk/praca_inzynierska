package com.example.pam.Plan

import android.content.res.Resources
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.pam.Plan.PlanPartDTO
import com.example.pam.R
import kotlinx.android.synthetic.main.antique_list_element.view.*
import kotlinx.android.synthetic.main.plan_part.view.*


class PlanEntryAdapter(var dataset: List<PlanPartDTO>,
                          private val resources: Resources,
                          private val redirect: (Int) -> (Unit),
                          private val remove: (Int) -> (Unit),
                          private val complete: (Int) -> (Unit),
                          private val moveUp: (Int) -> (Unit),
                          private val moveDown: (Int) -> (Unit),
) : RecyclerView.Adapter<PlanEntryAdapter.PlanViewHolder>() {

    class PlanViewHolder(val view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlanViewHolder {
        val textView = LayoutInflater.from(parent.context)
            .inflate(R.layout.plan_part, parent, false)
        return PlanViewHolder(textView)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onBindViewHolder(holder: PlanViewHolder, position: Int) {
        val planPart = dataset[position]
        holder.view.planPartName.text = planPart.name
        val dist = planPart.distance
        val distanceText =
            if (dist == null)
                ""
            else if (dist > 1000)
                "${dist / 1000} km"
            else
                "${dist} m"

        holder.view.planPartDistance.text = resources.getString(R.string.from_here, distanceText)
        holder.view.planPartImage.setImageResource(planPart.photo)
        holder.view.completeButton.setOnClickListener { complete(planPart.id) }
        holder.view.removeFromPlanButton.setOnClickListener { remove(planPart.id) }
        holder.view.moveDownButton.setOnClickListener { moveDown(planPart.id) }
        holder.view.moveUpButton.setOnClickListener { moveUp(planPart.id) }
        holder.view.planPartImage.setOnClickListener{ redirect(planPart.id) }
    }

    override fun getItemCount() = dataset.size
}