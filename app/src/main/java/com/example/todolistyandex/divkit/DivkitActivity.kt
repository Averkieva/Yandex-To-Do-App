package com.example.todolistyandex.divkit

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.ui.viewinterop.AndroidView
import com.yandex.div.core.Div2Context
import com.yandex.div.core.DivConfiguration
import com.yandex.div.glide.GlideDivImageLoader

class DivkitActivity : ComponentActivity() {

    private val assetsReader = AssetReader(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val imageLoader = GlideDivImageLoader(this)
            val configuration = DivConfiguration.Builder(imageLoader)
                .actionHandler(SampleDivActionHandler())
                .build()

            val divJson = assetsReader.read("sample.json")
            val templatesJson = divJson.optJSONObject("templates")
            val cardJson = divJson.getJSONObject("card")

            val divContext = Div2Context(
                baseContext = this,
                configuration = configuration,
                lifecycleOwner = this
            )
            val divView = Div2ViewFactory(divContext, templatesJson).createView(cardJson)
            AndroidView(factory = { context -> divView })
        }
    }
}