package com.example.todolistyandex.divkit

import android.content.Context
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader

private const val BUFFER_SIZE = 2048

class AssetReader(
    private val context: Context
) {
    fun read(filename: String): JSONObject {
        val data = toString(context.assets.open(filename))
        return JSONObject(data)
    }
}

private fun toString(input: InputStream): String {
    val buffer = CharArray(BUFFER_SIZE)
    val reader = BufferedReader(InputStreamReader(input))
    val builder = StringBuilder(input.available())
    var read: Int
    while (reader.read(buffer).also { read = it } != -1) {
        builder.appendRange(buffer, 0, read)
    }
    return builder.toString()
}