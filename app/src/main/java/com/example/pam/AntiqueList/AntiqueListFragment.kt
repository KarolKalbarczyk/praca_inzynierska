package com.example.pam.AntiqueList

import android.graphics.Color.alpha
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.RectShape
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.*
import android.widget.Button
import androidx.annotation.RequiresApi
import androidx.core.os.bundleOf
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pam.R
import com.example.pam.SightseeingApp
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import kotlinx.android.synthetic.main.antique_recycler.view.*


class AntiqueListFragment : Fragment() {
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: AntiqueEntryAdapter
    private lateinit var viewManager: RecyclerView.LayoutManager
    private val viewModel: AntiqueListViewModel by viewModels {
        MyViewModelFactory((requireActivity().application as SightseeingApp), resources, LocationServices.getFusedLocationProviderClient(requireActivity()), requireActivity())
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.antique_recycler, container, false)
        viewManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        view.sort.setOnClickListener { viewModel.sort() }
        view.search.doOnTextChanged { text, start, before, count -> viewModel.search(text.toString()) }
        viewAdapter = AntiqueEntryAdapter(viewModel.liveData.value!!, resources, { id -> findNavController().navigate(
                R.id.action_listActivity_to_antiqueFragment,
            bundleOf(KEY to id)
            ) })

        configureRecycler(view)
        setHasOptionsMenu(true)
        return view
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.main_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.see_plan){
            findNavController().navigate(
                R.id.action_listActivity_to_planFragment
            )
        } else if (item.itemId == R.id.see_signin){
            findNavController().navigate(
                R.id.action_listActivity_to_signinFragment
            )
        }

        return super.onOptionsItemSelected(item)
    }

    private fun configureRecycler(view: View) {
        recyclerView = view.my_recycler_view.apply {
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