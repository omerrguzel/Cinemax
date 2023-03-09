package com.example.cinemax.utils

import android.content.Context
import android.content.ContextWrapper
import android.content.res.Configuration
import android.content.res.Resources

import android.os.Build
import java.util.*


object LanguageConfig {
    fun changeLanguage(context: Context, languageCode: String): ContextWrapper {
        var context: Context = context
        val resources: Resources = context.resources
        val configuration: Configuration = resources.configuration
        val systemLocale: Locale
        systemLocale = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            configuration.locales.get(0)
        } else {
            configuration.locale
        }
        if (languageCode != "" && !systemLocale.language.equals(languageCode)) {
            val locale = Locale(languageCode)
            Locale.setDefault(locale)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                configuration.setLocale(locale)
            } else {
                configuration.locale = locale
            }
            context = context.createConfigurationContext(configuration)
        }
        return ContextWrapper(context)
    }
}