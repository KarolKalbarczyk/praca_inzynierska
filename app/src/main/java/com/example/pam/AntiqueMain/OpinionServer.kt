package com.example.pam.AntiqueMain

import android.app.Activity
import android.content.Context
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.example.pam.Auth.AuthState
import com.example.pam.R
import com.google.android.gms.common.util.JsonUtils
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONArray
import org.json.JSONObject
import java.io.ByteArrayOutputStream
import java.util.concurrent.TimeUnit


public interface IOpinionServer{
    fun sendOpinion(rating: Int, opinion: String, bitmap: Bitmap?, antiqueId: Int)
    fun getOpinions(antiqueId: Int,  activity: Activity, populateFunction: (List<OpinionDTO>) -> Unit,)
}

class OpinionServer(private val context: Context, resources: Resources) : IOpinionServer {

    private val url = resources.getString(R.string.serverUrl)

    val JSON: MediaType = "application/json; charset=utf-8".toMediaType()


    override fun sendOpinion(rating: Int, opinion: String, bitmap: Bitmap?, antiqueId: Int){
        val token = AuthState.token

        if (token == null){
            return
        }

        val string64 = if (bitmap != null) encodeTobase64(bitmap) else ""

        val client = OkHttpClient.Builder().connectTimeout(100, TimeUnit.MINUTES).build();
        val json = JSONObject()
        json.put("text", opinion)
        json.put("rating", rating)
        json.put("avatar", string64)
        json.put("antiqueId", antiqueId)
        val body: RequestBody = json.toString().toRequestBody(JSON)
        val request: Request = Request.Builder()
            .url(url)
            .header("authorization", token)
            .post(body)
            .build()
        client.newCall(request).execute().use {  }
    }

    override fun getOpinions(antiqueId: Int,  activity: Activity, populateFunction: (List<OpinionDTO>) -> Unit,){

        val client = OkHttpClient.Builder().connectTimeout(100, TimeUnit.MINUTES).build();

        val request: Request = Request.Builder()
            .url(url + "?antiqueId=" + antiqueId)
            .build()

        client.newCall(request).execute().use { response ->
            val body  = response.body?.string()
            val jsonArray = JSONArray(body)
            val answer = mutableListOf<OpinionDTO>()
            for (i in 0..jsonArray.length() - 1) {
                val dto = jsonArray.getJSONObject(i)
                val opinion = dto.getString("text")
                val rating = dto.getInt("rating")
                val string64 = dto.getString("avatar")
                val bitmap = decodeBase64(string64)
                answer.add(OpinionDTO(rating, opinion, antiqueId, bitmap))
            }
            activity.runOnUiThread { populateFunction(answer) }
        }
    }

    private fun encodeTobase64(image: Bitmap): String? {
        val baos = ByteArrayOutputStream()
        image.compress(Bitmap.CompressFormat.PNG, 100, baos)
        val b: ByteArray = baos.toByteArray()
        return Base64.encodeToString(b, Base64.DEFAULT)
    }

    private fun decodeBase64(input: String?): Bitmap? {
        if (input.isNullOrBlank())
            return null
        val decodedByte: ByteArray = Base64.decode(input, 0)
        return BitmapFactory.decodeByteArray(decodedByte, 0, decodedByte.size)
    }
}