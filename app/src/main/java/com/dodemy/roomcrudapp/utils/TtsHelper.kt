package com.dodemy.roomcrudapp.utils

import android.content.Context
import android.speech.tts.TextToSpeech
import java.util.Locale


class TtsHelper(private val context: Context, private val onInitialized: () -> Unit) {
    private lateinit var ttsInstance: TextToSpeech
    private var isInitialized = false

    private val tts: TextToSpeech
        get() {
            if (!::ttsInstance.isInitialized) {
                ttsInstance = TextToSpeech(context) { status ->
                    if (status == TextToSpeech.SUCCESS) {
                        ttsInstance.language = Locale.getDefault()
                        isInitialized = true
                        onInitialized()
                    }
                }
            }
            return ttsInstance
        }

    fun speak(text: String) {
        if (isInitialized) {
            tts.speak(text, TextToSpeech.QUEUE_FLUSH, null, null)
        }
    }

    fun shutdown() {
        tts.shutdown()
    }
}

//class TtsHelper(private val context: Context, private val onInitialized: () -> Unit) {
//    private lateinit var ttsInstance: TextToSpeech
//
//    private val tts: TextToSpeech
//        get() {
//            if (!::ttsInstance.isInitialized) {
//                ttsInstance = TextToSpeech(context) { status ->
//                    if (status == TextToSpeech.SUCCESS) {
//                        ttsInstance.language = Locale.getDefault()
//                        onInitialized()
//                    }
//                }
//            }
//            return ttsInstance
//        }
//
//    fun speak(text: String) {
//        tts.speak(text, TextToSpeech.QUEUE_FLUSH, null, null)
//    }
//
//    fun shutdown() {
//        tts.shutdown()
//    }
//}
