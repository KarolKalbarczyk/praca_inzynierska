package com.example.pam.Gallery

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
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
                view.gallery.adapter = GalleryAdapter(requireContext(), photos.map { it.photoId })
                view.gallery.numColumns = 2
            }
        }.start()

        return view
    }

}


class GalleryAdapter(context: Context, private val list: List<Int>) : ArrayAdapter<Int>(context,0, list){

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = ImageView(context)
        view.setPadding(8,8,8,8)
        view.setImageResource(list[position])
        return view
    }
}