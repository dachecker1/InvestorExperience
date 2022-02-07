package com.vk.dachecker.investorsexperience.utils

import android.content.Intent
import com.vk.dachecker.investorsexperience.db.Company

object ShareHelper {
    fun shareTickerVideo(company: Company) : Intent {
        val intent = Intent(Intent.ACTION_SEND)
//        intent.type = "text|plane"
        intent.type = "*/*"
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
//        вписать ссылку на скачивание приложения с гугл плей.
        return stringBuilder.toString()
    }
}