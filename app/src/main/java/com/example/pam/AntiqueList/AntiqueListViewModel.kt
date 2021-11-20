package com.example.pam.AntiqueList

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.pm.PackageManager
import android.content.res.Resources
import android.location.Location
import androidx.core.app.ActivityCompat
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.myapplication.Database.Antique
import com.example.myapplication.Database.AntiqueRepository
import com.example.myapplication.Database.GalleryPhoto
import com.example.pam.Database.AntiqueHistory
import com.example.pam.Database.PlanPart
import com.example.pam.Database.PlanPartHasAntique
import com.example.pam.R
import com.example.pam.SightseeingApp
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.tasks.Tasks.await
import kotlinx.coroutines.launch


class MyViewModelFactory(private val app: SightseeingApp,
                         private val resources: Resources,
                         private val fusedLocationClient: FusedLocationProviderClient,
                         private val activity: Activity
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return AntiqueListViewModel(app, resources, fusedLocationClient, activity) as T
    }
}

class AntiqueListViewModel(
    private val app: SightseeingApp,
    private val resources: Resources,
    private val fusedLocationClient: FusedLocationProviderClient,
    private val activity: Activity
) : ViewModel(){

    private val usedList = mutableListOf<AntiqueDTO>()
    val liveData = MutableLiveData<List<AntiqueDTO>>(usedList)

    var mock = listOf(Antique(0, 1.0, 1.0, R.mipmap.hala1, R.string.Hala, R.string.hala_desc),
    Antique(1, 2.0 , 2.0, R.mipmap.katedra_foreground, R.string.Katedra, R.string.katedra_desc))

    init {
        Thread {
            initDb()
            if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_COARSE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    activity,
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION),
                    101,
                );
            }
            usedList.addAll(app.repository.allEntries.map { entry ->

                val location = await(fusedLocationClient.lastLocation)
                //mock.map{entry ->
                val answers = FloatArray(1)
                Location.distanceBetween(
                    25.0,//location.latitude,
                    25.0,//location.longitude,
                    entry.latitude,
                    entry.longitude,
                    answers
                )
                AntiqueDTO(
                    resources.getString(entry.nameId),
                    entry.mainPhotoId,
                    answers[0].toInt(),
                    entry.id
                )
            })
        }.start()
    }

    private fun initDb(){
        app.database.antiqueDao().deleteGallery()
        app.database.antiqueDao().deleteHistory()
        app.database.antiqueDao().delete()
        app.database.planDao().delete()

        val antique1 = Antique(1, 1.0, 1.0, R.mipmap.hala1_foreground, R.string.Hala, R.string.hala_desc)
        val antique2 = Antique(2, 2.0 , 2.0, R.mipmap.katedra_foreground, R.string.Katedra, R.string.katedra_desc)
        val antique3 = Antique(3, 40.0 , 40.0, R.mipmap.default_avatar_foreground, R.string.szklarnia, R.string.katedra_desc)

        app.database.antiqueDao().insert(antique1)
        app.database.antiqueDao().insert(antique2)
        app.database.antiqueDao().insert(antique3)
        app.database.antiqueDao().insert(Antique(4, 40.0 , 40.0, R.mipmap.arrow_up_foreground, R.string.szklarnia, R.string.katedra_desc))
        app.database.antiqueDao().insert(Antique(5, 40.0 , 40.0, R.mipmap.arrow_down_foreground, R.string.szklarnia, R.string.katedra_desc))
        app.database.antiqueDao().insert(Antique(6, 40.0 , 40.0, R.mipmap.hala2_foreground, R.string.szklarnia, R.string.katedra_desc))
        app.database.antiqueDao().insert(Antique(7, 40.0 , 40.0, R.mipmap.hala3_foreground, R.string.szklarnia, R.string.katedra_desc))
        app.database.antiqueDao().insert(Antique(8, 40.0 , 40.0, R.mipmap.hala4_foreground, R.string.szklarnia, R.string.katedra_desc))
        app.database.antiqueDao().insert(Antique(9, 40.0 , 40.0, R.mipmap.rynek1_foreground, R.string.szklarnia, R.string.katedra_desc))
        app.database.antiqueDao().insert(Antique(10, 40.0 , 40.0, R.mipmap.rynek2_foreground, R.string.szklarnia, R.string.katedra_desc))
        app.database.antiqueDao().insert(Antique(11, 40.0 , 40.0, R.mipmap.ratusz1_foreground, R.string.szklarnia, R.string.katedra_desc))
        app.database.antiqueDao().insert(Antique(12, 40.0 , 40.0, R.mipmap.ratusz2_foreground, R.string.szklarnia, R.string.katedra_desc))
        app.database.antiqueDao().insert(Antique(13, 40.0 , 40.0, R.mipmap.ratusz3_foreground, R.string.szklarnia, R.string.katedra_desc))
        app.database.antiqueDao().insert(Antique(14, 40.0 , 40.0, R.mipmap.katedra1_foreground, R.string.szklarnia, R.string.katedra_desc))
        app.database.antiqueDao().insert(Antique(15, 40.0 , 40.0, R.mipmap.katedra2_foreground, R.string.szklarnia, R.string.katedra_desc))
        app.database.antiqueDao().insert(Antique(16, 40.0 , 40.0, R.mipmap.katedra3_foreground, R.string.szklarnia, R.string.katedra_desc))
        app.database.antiqueDao().insert(Antique(17, 40.0 , 40.0, R.mipmap.pomnik1_foreground, R.string.szklarnia, R.string.katedra_desc))
        app.database.antiqueDao().insert(Antique(18, 40.0 , 40.0, R.mipmap.pomnik2_foreground, R.string.szklarnia, R.string.katedra_desc))
        app.database.antiqueDao().insert(Antique(19, 40.0 , 40.0, R.mipmap.pomnik3_foreground, R.string.szklarnia, R.string.katedra_desc))
        app.database.antiqueDao().insert(Antique(20, 40.0 , 40.0, R.mipmap.pomnik4_foreground, R.string.szklarnia, R.string.katedra_desc))
        app.database.antiqueDao().insert(Antique(21, 40.0 , 40.0, R.mipmap.pomnik5_foreground, R.string.szklarnia, R.string.katedra_desc))
        app.database.antiqueDao().insert(Antique(22, 40.0 , 40.0, R.mipmap.pomnik6_foreground, R.string.szklarnia, R.string.katedra_desc))
        app.database.antiqueDao().insert(Antique(23, 40.0 , 40.0, R.mipmap.pomnik7_foreground, R.string.szklarnia, R.string.katedra_desc))
        app.database.antiqueDao().insert(GalleryPhoto(0, R.mipmap.hala2_foreground,1))
        app.database.antiqueDao().insert(GalleryPhoto(0, R.mipmap.hala3_foreground,1))
        app.database.antiqueDao().insert(GalleryPhoto(0, R.mipmap.hala4_foreground,1))
        app.database.antiqueDao().insert(AntiqueHistory(0,1, 2, R.string.history1,0, 1))
        app.database.antiqueDao().insert(AntiqueHistory(0,2, 2, R.raw.video,1, 1))
        app.database.antiqueDao().insert(AntiqueHistory(0,1, 1, R.string.history1,2, 1))
        app.database.antiqueDao().insert(AntiqueHistory(0,0, 1, R.mipmap.hala2_foreground,3, 1))

        app.database.planDao().insertPlanPart(PlanPart(1, 3, false, 1))
        app.database.planDao().insertPlanPart(PlanPart(2, 2, false, 2))
        app.database.planDao().insertPlanPart(PlanPart(3, 1, false, 3))
    }

    fun sort(){
        liveData.value = usedList.sortedBy { it.distance }
    }

    fun restore(){
        liveData.value = usedList
    }

    fun search(subString: String){
        liveData.value = usedList
            .filter { it.name.contains(subString) }
            .sortedBy { it.name.indexOf(subString) }
    }
}