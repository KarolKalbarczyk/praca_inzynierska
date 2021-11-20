package com.example.pam.Plan

import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.RectShape
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pam.AntiqueList.KEY
import com.example.pam.R
import com.example.pam.SightseeingApp
import kotlinx.android.synthetic.main.plan_fragment.view.*

class PlanFragment : Fragment() {

    private lateinit var viewModel: PlanViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: PlanEntryAdapter
    private lateinit var viewManager: RecyclerView.LayoutManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.plan_fragment, container, false)
        viewManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        viewModel = ViewModelProvider(this).get(PlanViewModel::class.java)

        viewAdapter = PlanEntryAdapter(
            listOf(),
            resources,
            complete = viewModel::complete,
            moveDown = viewModel::moveDown,
            moveUp = viewModel::moveUp,
            remove = viewModel::remove,
            redirect = { id -> findNavController().navigate(
                R.id.action_planFragment_to_antiqueFragment,
                bundleOf(KEY to id)
            ) }
        )
        configureRecycler(view)
        viewModel.init((requireActivity().application as SightseeingApp).planRepository, resources, requireActivity()::runOnUiThread)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

    private fun configureRecycler(view: View) {
        recyclerView = view.planPartRecycler.apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
        }
        recyclerView.addItemDecoration(
            DividerItemDecoration(
                requireContext(),
                DividerItemDecoration.VERTICAL
            ).also { deco ->
                with(ShapeDrawable(RectShape())) {
                    intrinsicHeight = (resources.displayMetrics.density).toInt()
                    alpha = resources.getInteger(R.integer.alpha)
                    deco.setDrawable(this)
                }
            })

        viewModel.liveData.observe(requireActivity()) {
                viewAdapter.dataset = it
                viewAdapter.notifyDataSetChanged()
        }
    }

    }