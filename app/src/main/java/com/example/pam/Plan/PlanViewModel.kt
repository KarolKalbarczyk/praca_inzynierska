package com.example.pam.Plan

import android.content.res.Resources
import android.location.Location
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.pam.Database.PlanPart
import com.example.pam.Database.PlanPartRepository

class PlanViewModel() : ViewModel() {

    private lateinit var repository: PlanPartRepository
    private lateinit var res: Resources
    private var entityList = listOf<PlanPart>()
    private val usedList = mutableListOf<PlanPartDTO>()
    val liveData = MutableLiveData<List<PlanPartDTO>>(usedList)
    private lateinit var runOnUiThread: (Runnable) -> Unit

    fun init(repository: PlanPartRepository, res: Resources, runOnUiThread: (Runnable) -> Unit) {
        this.repository = repository
        this.res = res
        this.runOnUiThread = runOnUiThread
        Thread {
            val parts = repository.getPlan()
            entityList = parts.sortedByDescending { it.planPart.order }.map { it.planPart }

            for((index, part) in parts.withIndex()) {
                val dto = PlanPartDTO(
                    name = res.getString(part.antique.nameId),
                    order = index,
                    id = part.planPart.planPartId,
                    latitude = part.antique.latitude,
                    longitude = part.antique.longitude,
                    photo = part.antique.mainPhotoId,
                    done = part.planPart.done,
                    antiqueId = part.antique.id
                )
                usedList.add(dto)
            }
            val answers = FloatArray(1)
            for (i in 1..usedList.size - 1){
                Location.distanceBetween(
                    usedList[i - 1].latitude,
                    usedList[i - 1].longitude,
                    usedList[i].latitude,
                    usedList[i].longitude,
                    answers
                )
                usedList[i].distance = answers[0].toInt()
            }
            runOnUiThread { liveData.value = usedList }
        }.start()
    }

    fun moveUp(id: Int){
        val index = usedList.indexOfFirst { it.id == id }
        if (index == 0){
            return;
        }

        if (usedList[index - 1].done){
            return
        }

        val temp = usedList[index]
        usedList[index] = usedList[index - 1]
        usedList[index - 1] = temp
        runOnUiThread { liveData.value = usedList }
    }

    fun moveDown(id: Int){
        val index = usedList.indexOfFirst { it.id == id }
        if (index == usedList.size - 1){
            return;
        }
        val temp = usedList[index]
        usedList[index] = usedList[index + 1]
        usedList[index + 1] = temp
        runOnUiThread { liveData.value = usedList }
    }

    fun remove(id: Int){
        val index = usedList.indexOfFirst { it.id == id }
        if (usedList[index].done){
            return
        }
        usedList.removeAt(index)
        runOnUiThread { liveData.value = usedList }
    }

    fun complete(id: Int){
        val index = usedList.indexOfFirst { it.id == id }
        usedList[index].done = true
        runOnUiThread { liveData.value = usedList }
    }
}