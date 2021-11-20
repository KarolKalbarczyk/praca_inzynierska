package com.example.pam.History

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pam.AntiqueList.KEY
import com.example.pam.AntiqueMain.OpinionRecyclerViewAdapter
import com.example.pam.Database.AntiqueHistory
import com.example.pam.Database.HistoryFragmentKind
import com.example.pam.R
import com.example.pam.SightseeingApp
import kotlinx.android.synthetic.main.fragment_history.view.*
import kotlinx.android.synthetic.main.fragment_opinion_list.view.*
import kotlinx.android.synthetic.main.history_photo.view.*
import kotlinx.android.synthetic.main.history_video.view.*


class HistoryAdapter(private val context: Context) : RecyclerView.Adapter<HistoryAdapter.ViewHolder>(){

    fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val a=  selectSegment(list[position], parent)
        return a
    }

    var list: List<AntiqueHistory> = listOf()
    set(value) {
        field = value
        notifyDataSetChanged()
    }

    inner class ViewHolder(val view: View) : RecyclerView.ViewHolder(view)


    private fun selectSegment(segment: AntiqueHistory, parent: ViewGroup) : View {
        when(segment.mediaKind){
            HistoryFragmentKind.Video -> {
                val videoView = LayoutInflater.from(parent.context).inflate(R.layout.history_video, parent, false)
                return videoView
            }
            HistoryFragmentKind.Photo ->{
                val imageView = LayoutInflater.from(parent.context).inflate(R.layout.history_photo, parent, false)
                return imageView
            }
            HistoryFragmentKind.Text -> return TextView(context)
        }
    }

    override fun getItemViewType(position: Int) = position

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(selectSegment(list[viewType], parent))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val segment = list[position]
        when(segment.mediaKind) {
            HistoryFragmentKind.Video -> {
                val videoView = holder.view.historyVideo as VideoView
                videoView.setVideoURI(Uri.parse("android.resource://" + context.packageName + "/" + segment.resourceId))
                val mediaController = MediaController(context)
                videoView.setMediaController(mediaController)
                mediaController.setAnchorView(videoView)
            }
            HistoryFragmentKind.Photo -> {
                val imageView = holder.view.historyPhoto as ImageView
                imageView.setImageResource(segment.resourceId)
            }
            HistoryFragmentKind.Text -> {
                val textView = holder.view as TextView
                textView.apply { text = resources.getText(segment.resourceId) }
            }
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }
}

class HistoryFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_history, container, false) as RecyclerView


        val antiqueId = arguments?.getInt(KEY) ?: 1
        val historyAdapter = HistoryAdapter(requireContext())
        view.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = historyAdapter
        }

        Thread {
            val segments : List<AntiqueHistory> = (requireActivity().application as SightseeingApp).repository.dao.getHistory(antiqueId)
            requireActivity().runOnUiThread { historyAdapter.list = segments }
        }.start()

        return view
    }


}
