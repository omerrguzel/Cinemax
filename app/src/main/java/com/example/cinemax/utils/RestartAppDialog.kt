package com.example.cinemax.utils

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import java.util.*

class RestartAppDialog(val language:String) : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            // Use the Builder class for convenient dialog construction
            val builder = AlertDialog.Builder(it)
            builder.setMessage("This option will restart the app. Do you agree?")
                .setPositiveButton("Yes",
                    DialogInterface.OnClickListener { dialog, id ->
                        // Restart the app
                        if(language == "en"){
                            changeToEnglish()
                        } else if (language == "tr"){
                            changeToTurkish()
                        }
                        val intent = activity?.baseContext?.packageManager?.getLaunchIntentForPackage(activity?.baseContext?.packageName!!)
                        intent?.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                        intent?.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                        startActivity(intent)
                        android.os.Process.killProcess(android.os.Process.myPid())
                    })
                .setNegativeButton("No",
                    DialogInterface.OnClickListener { dialog, id ->
                        // Close the dialog box
                        dialog.cancel()
                    })
            // Create the AlertDialog object and return it
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

    private fun changeToEnglish(){

        val locale = Locale("en")
//        Locale.setDefault(locale)
//        val config = Configuration()
//        config.locale = locale
//        requireContext().applicationContext.resources.updateConfiguration(config, resources.displayMetrics)

//        val locale = Locale("en")
//        val resources = context?.resources
//        val configuration = resources?.configuration
//        Locale.setDefault(Locale.ENGLISH)

        val sharedPreferences = context?.getSharedPreferences("MyAppPreferences", Context.MODE_PRIVATE)
        sharedPreferences?.edit()?.putString("language", locale.language)?.commit()
    }

    private fun changeToTurkish(){
        val locale = Locale("tr")
//        val resources = context?.resources
//        val configuration = resources?.configuration
//        configuration?.setLocale(locale)
//
//        val locale = Locale("tr")
//        Locale.setDefault(locale)
//        val config = Configuration()
//        config.locale = locale
//        requireContext().applicationContext.resources.updateConfiguration(config, resources.displayMetrics)

        val sharedPreferences = context?.getSharedPreferences("MyAppPreferences", Context.MODE_PRIVATE)
        sharedPreferences?.edit()?.putString("language", locale.language)?.commit()
    }
}