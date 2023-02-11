package ru.otus

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import java.lang.reflect.Type

open class BaseHttpClient {

    val gson = Gson()

    private val client = OkHttpClient.Builder()
        .build()
    private val json = "application/json; charset=utf-8".toMediaType()

    inline fun <reified T> Gson.fromJson(json: String) = fromJson(json, T::class.java)
    inline fun <reified T> Gson.fromJsonArray(json: String, typeToken: Type): T {
        val gson = GsonBuilder().create()
        return gson.fromJson<T>(json, typeToken)
    }


    fun<T> doPostRequest(url: String, body: T?): Response {
        val request = Request.Builder()
            .post(gson.toJson(body).toRequestBody(json))
            .url(url)
            .build()

    return client.newCall(request).execute()
    }

    fun <T> doDeleteRequest(url: String, id: Int, task:T?): Response {
        val request = Request.Builder()
            .delete()
            .url(url + "/$id")
            .build()

        return client.newCall(request).execute()
    }


    fun <T> doPutRequest(url: String, id: Int, body:T?, status: Boolean): Response {
        val request = Request.Builder()
            .put(gson.toJson(body).toRequestBody(json))
            .url(url + "/status/$id?done=$status")
            .build()

        return client.newCall(request).execute()
    }

    fun <T> doGetRequest(url: String, task:T?, status: Boolean): Response {
        val request = Request.Builder()
            .get()
            .url(url + "?done=$status")
            .build()
        println("When call doGetRequest, request = $request")
        return client.newCall(request).execute()
    }
}



