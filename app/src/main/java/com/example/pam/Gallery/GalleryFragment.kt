package com.example.pam.Gallery

import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.ArrayAdapter
import android.widget.ImageView
import androidx.core.os.bundleOf
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.pam.AntiqueList.KEY
import com.example.pam.R
import com.example.pam.SightseeingApp
import kotlinx.android.synthetic.main.fragment_gallery.view.*


class GalleryFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view =  inflater.inflate(R.layout.fragment_gallery, container, false)
        val antiqueId = arguments?.getInt(KEY) ?: 1

        Thread {
            val photos = (requireActivity().application as SightseeingApp).repository.dao.getPhotos(antiqueId)
            requireActivity().runOnUiThread {
                view.gallery.adapter = GalleryAdapter(requireContext(), photos.map { it.photoId }, findNavController())
                view.gallery.numColumns = 2
            }
        }.start()
        setHasOptionsMenu(true)

        return view
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.main_menu, menu)
    }
}

class GalleryAdapter(context: Context, private val list: List<Int>, private val navController: NavController) : ArrayAdapter<Int>(context,0, list){

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = ImageView(context)
        view.setPadding(8,8,8,8)
        view.setImageResource(list[position])
        view.setOnClickListener {
            navController.navigate(
                R.id.action_galleryFragment_to_photoFragment,
                bundleOf(KEY to list[position])
            )
        }
        return view
    }
}