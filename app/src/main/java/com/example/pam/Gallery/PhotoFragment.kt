package com.example.pam.Gallery

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import com.example.pam.AntiqueList.KEY
import com.example.pam.R
import kotlinx.android.synthetic.main.fragment_photo.view.*

class PhotoFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_photo, container, false)
        val photoId = arguments?.getInt(KEY) ?: 1
        val photo = view.galleryBigPhoto
        photo.setImageResource(photoId)
        setHasOptionsMenu(true)
        return view
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.main_menu, menu)
    }
}