package com.example.pam.AntiqueMain



import android.app.Activity
import android.content.Context
import android.content.res.Resources
import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.os.bundleOf
import androidx.core.widget.doOnTextChanged
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.Database.Antique
import com.example.pam.Database.PlanPartRepository
import com.example.pam.R
import kotlinx.android.synthetic.main.fragment_antique.view.*
import kotlinx.android.synthetic.main.fragment_opinion.view.*


const val KEY  = "KEY"

class OpinionRecyclerViewAdapter(
    private val res: Resources,
    private val activity: Activity,
    private val navController: NavController,
    private val uploadImage: (ImageView) -> Unit,
    private val mapStarter: (Double, Double) -> Unit,
    private val planRepository: PlanPartRepository
) : RecyclerView.Adapter<OpinionRecyclerViewAdapter.ViewHolder>() {

    lateinit var opinionServer: IOpinionServer
    private lateinit var context: Context
    private val values: MutableList<OpinionDTO> = mutableListOf()
    private lateinit var avatar: ImageView
    private var planPartId : Int? = null

    var bitmap: Bitmap? = null
    set(value) {
        field = value
        avatar.setImageBitmap(value)
    }

    var antique: Antique? = null
        set(value) {
            field = value
            values.add(0, OpinionDTO(0, "", 0, null))
            Thread {
                val planPart = planRepository.getPlan().find { it.antique.id == value?.id }
                planPartId = planPart?.planPart?.planPartId
                activity.runOnUiThread { notifyDataSetChanged() }

                opinionServer.getOpinions(value!!.id, activity) { response ->
                    values.addAll(response)
                    activity.runOnUiThread { notifyDataSetChanged() }
                }

            }.start()

        }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        context = recyclerView.context
        opinionServer = OpinionServer(context)
    }

    override fun getItemViewType(position: Int): Int = position

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val textView = LayoutInflater.from(parent.context)
            .inflate(
                if (viewType == 0 && antique != null) R.layout.fragment_antique else R.layout.fragment_opinion,
                parent,
                false
            )
        return ViewHolder(textView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if( antique == null)
            return
        if (position == 0)
            bindGeneral(holder)
        else
            bindOpinion(holder, position)
    }

    private fun AddToPlan(){
        Thread {
            if (planPartId == null) {
                planPartId = planRepository.addToPlan(antique!!).toInt()
            } else {
                planRepository.remove(planPartId!!)
                planPartId = null
            }
            activity.runOnUiThread { notifyDataSetChanged() }
        }.start()
    }

    private fun bindGeneral(holder: OpinionRecyclerViewAdapter.ViewHolder) {
        this.avatar = holder.view.uploadAvatar
        val view = holder.view
        val antique = this.antique!!

        fun changeSendButtonState(){
            if (view.sendMessage.text.isBlank()
                || view.ratingBar.rating.toInt() == 0) {
                view.buttonSend.setOnClickListener {  }
                view.buttonSend.setBackgroundColor(res.getColor(R.color.gray))
            }
            else{
                view.buttonSend.setOnClickListener {
                    view.buttonSend.setOnClickListener {  }
                    val rating = view.ratingBar.rating.toInt()
                    val message = view.sendMessage.text.toString()
                    val copy = bitmap
                    Thread { opinionServer.sendOpinion(rating, message, copy, antique.id) }.start()
                    view.ratingBar.rating = 0.0f
                    view.sendMessage.setText("")
                    bitmap = null
                }
                view.buttonSend.setBackgroundColor(res.getColor(R.color.light_blue))
            }
        }


        view.googleMap.setOnClickListener { mapStarter(antique.latitude, antique.longitude) }
        view.planButton.setOnClickListener { AddToPlan() }
        view.planButton.foreground = (res.getDrawable(if (planPartId == null) R.mipmap.add_plan_foreground else R.mipmap.remove_plan_foreground))
        view.mainPhoto.setImageResource(antique.mainPhotoId)
        view.antiqueName.text = res.getString(antique.nameId)
        view.description.text = res.getString(antique.descriptionId)

        view.ratingBar.setOnRatingBarChangeListener { ratingBar, rating, fromUser -> changeSendButtonState() }
        view.sendMessage.doOnTextChanged { text, start, before, count -> changeSendButtonState() }

        view.uploadAvatar.setImageResource(R.mipmap.default_avatar_foreground)
        view.uploadAvatar.setOnClickListener {
            uploadImage(view.uploadAvatar)
            changeSendButtonState()
        }

        //view.buttonSend.setOnClickListener { opinionServer.test(context) }
        view.buttonSend.setOnClickListener {
            opinionServer.sendOpinion(view.ratingBar.rating.toInt(), view.sendMessage.text.toString(), bitmap, antique.id )
        }

        view.galleryButton.setOnClickListener { navController.navigate(
            R.id.action_antiqueFragment_to_galleryFragment,
            bundleOf(com.example.pam.AntiqueList.KEY to antique.id)
        ) }

        view.history.setOnClickListener { navController.navigate(
            R.id.action_antiqueFragment_to_historyFragment,
            bundleOf(com.example.pam.AntiqueList.KEY to antique.id)
        ) }
    }

    private fun bindOpinion(holder: OpinionRecyclerViewAdapter.ViewHolder, position: Int) {
        val opinion = values[position]
        if (opinion.bitmap == null)
            holder.view.avatar.setImageResource(R.mipmap.default_avatar_foreground)
        else
            holder.view.avatar.setImageBitmap(opinion.bitmap)
        holder.view.ratingStars.rating = opinion.rating.toFloat()
        holder.view.expand.setImageResource(R.mipmap.arrow_down_foreground)
        holder.view.expand.setOnClickListener { expand(holder.view, position) }
        //holder.view.expand.setBackgroundResource()
        //holder.view.expandableView.opinionText.opinionPhoto = values[position].text
    }

    private fun expand(view: View, position: Int){
        view.expandableView.visibility = View.VISIBLE
        view.expandableView.opinionText.text = values[position].text
        view.expand.setImageResource(R.mipmap.arrow_up_foreground)
        view.expand.setOnClickListener { collapse(view, position) }
    }

    private fun collapse(view: View, position: Int){
        view.expandableView.visibility = View.GONE
        view.expand.setImageResource(R.mipmap.arrow_down_foreground)
        view.expand.setOnClickListener { expand(view, position) }
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {}
}

