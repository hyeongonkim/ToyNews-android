package com.simonkim.toynews.module

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.AsyncTask
import java.net.URL

class URLToImage() : AsyncTask<Void, Void, Bitmap>() {

    lateinit var url: URL

    override fun doInBackground(vararg p0: Void?): Bitmap {
        val bitmap = BitmapFactory.decodeStream(url.openStream())
        return bitmap
    }

}