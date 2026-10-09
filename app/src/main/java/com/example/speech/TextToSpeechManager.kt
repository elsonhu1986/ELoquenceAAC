package com.example.speech

import android.content.Context
import android.speech.tts.TextToSpeech
import android.speech.tts.UtteranceProgressListener
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.Locale

class TextToSpeechManager(context: Context) : TextToSpeech.OnInitListener {

  private var tts: TextToSpeech? = TextToSpeech(context.applicationContext, this)

  private val _isReady = MutableStateFlow(false)
  val isReady: StateFlow<Boolean> = _isReady.asStateFlow()

  private val _isSpeaking = MutableStateFlow(false)
  val isSpeaking: StateFlow<Boolean> = _isSpeaking.asStateFlow()

  private var currentPitch = 1.0f
  private var currentRate = 1.0f

  override fun onInit(status: Int) {
    if (status == TextToSpeech.SUCCESS) {
      val result = tts?.setLanguage(Locale.getDefault())
      if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
        tts?.setLanguage(Locale.US)
      }
      tts?.setPitch(currentPitch)
      tts?.setSpeechRate(currentRate)
      setupListener()
      _isReady.value = true
    } else {
      _isReady.value = false
    }
  }

  private fun setupListener() {
    tts?.setOnUtteranceProgressListener(object : UtteranceProgressListener() {
      override fun onStart(utteranceId: String?) {
        _isSpeaking.value = true
      }

      override fun onDone(utteranceId: String?) {
        _isSpeaking.value = false
      }

      @Deprecated("Deprecated in Java")
      override fun onError(utteranceId: String?) {
        _isSpeaking.value = false
      }

      override fun onError(utteranceId: String?, errorCode: Int) {
        _isSpeaking.value = false
      }
    })
  }

  fun setPitch(pitch: Float) {
    currentPitch = pitch.coerceIn(0.5f, 2.0f)
    tts?.setPitch(currentPitch)
  }

  fun setSpeechRate(rate: Float) {
    currentRate = rate.coerceIn(0.5f, 2.0f)
    tts?.setSpeechRate(currentRate)
  }

  fun speak(text: String, flush: Boolean = true) {
    if (text.isBlank()) return
    val queueMode = if (flush) TextToSpeech.QUEUE_FLUSH else TextToSpeech.QUEUE_ADD
    val utteranceId = "utterance_${System.currentTimeMillis()}"
    tts?.speak(text, queueMode, null, utteranceId)
  }

  fun stop() {
    tts?.stop()
    _isSpeaking.value = false
  }

  fun shutdown() {
    tts?.stop()
    tts?.shutdown()
    tts = null
  }
}
