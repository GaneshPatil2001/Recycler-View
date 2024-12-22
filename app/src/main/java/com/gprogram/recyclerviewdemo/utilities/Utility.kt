
package com.gprogram.recyclerviewdemo.utilities

import android.content.Context
import java.io.IOException

object Utility {
    fun loadJSONFromAsset(context: Context): String? {
        return try {
            val inputStream = context.assets.open("response.json")
            val size = inputStream.available()
            val buffer = ByteArray(size)
            inputStream.read(buffer)
            inputStream.close()
            String(buffer, Charsets.UTF_8)
        } catch (ex: IOException) {
            ex.printStackTrace()
            null
        }
    }
}
