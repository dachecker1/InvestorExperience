package com.vk.dachecker.investorsexperience.utils

import android.content.Intent
import com.vk.dachecker.investorsexperience.R
import com.vk.dachecker.investorsexperience.data.model.Company

object ShareHelper {
    const val GOOGLE_PLAY_URL = "https://play.google.com/store/apps/details?id=com.vk.dachecker.investorsexperience"

    fun shareTickerVideo(company: Company) : Intent {
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "text/plane"
        intent.apply {
            putExtra(Intent.EXTRA_TEXT, makeShareText(company))
        }
        return intent
    }

    private fun makeShareText(company: Company) : String {
        val stringBuilder = StringBuilder()
        stringBuilder.append(company.ticker)
        stringBuilder.append("\n")
        stringBuilder.append(company.url)
        stringBuilder.append("\n")
        stringBuilder.append("Качай приложение \"Опыт инвестора\" в Google Play")
        stringBuilder.append("\n")
        stringBuilder.append(GOOGLE_PLAY_URL)
//        вписать ссылку на скачивание приложения с гугл плей.
        return stringBuilder.toString()
    }

}