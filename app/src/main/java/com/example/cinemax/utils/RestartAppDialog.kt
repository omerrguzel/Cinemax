package com.example.cinemax.utils

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.example.cinemax.R
import java.util.*

class RestartAppDialog(val language:String) : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            builder.setMessage(getString(R.string.this_option_restart))
                .setPositiveButton(getString(R.string.yes)
                ) { _, _ ->
                    if (language == "en") {
                        changeToEnglish()
                    } else if (language == "tr") {
                        changeToTurkish()
                    }
                    val intent =
                        activity?.baseContext?.packageManager?.getLaunchIntentForPackage(activity?.baseContext?.packageName!!)
                    intent?.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                    intent?.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(intent)
                    android.os.Process.killProcess(android.os.Process.myPid())
                }
                .setNegativeButton(getString(R.string.no)
                ) { dialog, _ ->
                    dialog.cancel()
                }
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

    private fun changeToEnglish(){

        val locale = Locale("en")
        val sharedPreferences = context?.getSharedPreferences("MyAppPreferences", Context.MODE_PRIVATE)
        sharedPreferences?.edit()?.putString("language", locale.language)?.commit()
    }

    private fun changeToTurkish(){
        val locale = Locale("tr")
        val sharedPreferences = context?.getSharedPreferences("MyAppPreferences", Context.MODE_PRIVATE)
        sharedPreferences?.edit()?.putString("language", locale.language)?.commit()
    }
}