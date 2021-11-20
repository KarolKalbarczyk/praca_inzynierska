package com.example.pam.AntiqueMain

import android.Manifest
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pam.Database.PlanPartRepository
import com.example.pam.R
import com.example.pam.SightseeingApp
import com.google.android.gms.location.FusedLocationProviderClient
import kotlinx.android.synthetic.main.fragment_opinion_list.view.*
import java.io.InputStream


class AntiqueFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager
    private val REQUEST_EXTERNAL_STORAGE = 100
    private lateinit var image: ImageView


    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_opinion_list, container, false)
        viewManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        viewAdapter = OpinionRecyclerViewAdapter(
            resources, requireActivity(), findNavController(), ::imageUpload, ::openGoogleMap, (requireActivity().application as SightseeingApp).planRepository
        )
        configureRecycler(view)
        return view
    }

    private fun openGoogleMap(latitude: Double, longitude: Double) {
        val gmmIntentUri = Uri.parse("geo:${latitude},${longitude}")
        val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
        mapIntent.setPackage("com.google.android.apps.maps")
        startActivity(mapIntent)
    }

    private fun configureRecycler(view: View) {
        recyclerView = view.antiqueMainRecycler.apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
        }
        val antiqueId = arguments?.getInt(com.example.pam.AntiqueList.KEY) ?: 1

        Thread {
            val antique =
                (requireActivity().application as SightseeingApp).repository.dao.getAntique(
                    antiqueId
                )
            requireActivity().runOnUiThread { (viewAdapter as OpinionRecyclerViewAdapter).antique = antique }
        }.start()

    }

    private fun imageUpload(imageView: ImageView) {
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                REQUEST_EXTERNAL_STORAGE
            );
        } else {
            image = imageView
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
            intent.type = "image/*"
            startActivityForResult(intent, REQUEST_EXTERNAL_STORAGE, null)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_EXTERNAL_STORAGE && resultCode == RESULT_OK) {
            val imageUri: Uri = data?.data!!
            val inputStream: InputStream? = requireActivity().contentResolver.openInputStream(
                imageUri
            )
            val bitmap = BitmapFactory.decodeStream(inputStream)
            (viewAdapter as OpinionRecyclerViewAdapter).bitmap = bitmap
            }
            /*Thread {
                for (b in bitmaps) {
                    UiThreadStatement.runOnUiThread(Runnable { imageView.setImageBitmap(b) })
                    try {
                        Thread.sleep(3000)
                    } catch (e: InterruptedException) {
                        e.printStackTrace()
                    }
                }
            }.start()*/
        }


}
