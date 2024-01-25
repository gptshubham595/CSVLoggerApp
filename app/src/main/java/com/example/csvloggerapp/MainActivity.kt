package com.example.csvloggerapp

import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.csvloggerapp.databinding.ActivityMainBinding
import java.io.File
import java.io.FileWriter
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var counter: Int = 0
    private lateinit var logFile : File

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val directory = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS), "event_log.csv")
        if (!directory.isDirectory) {
            directory.mkdir()
        }
        logFile = File(directory, "event_log.csv")

        binding.logButton.setOnClickListener {
            logEvent(++counter)
        }
    }

    private fun logEvent(counter: Int) {
        try {
            val timeStamp: String =
                SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(Date())
            val event = "EVENT = ${counter}"

            if (!logFile.exists()) {
                logFile.createNewFile()
                // Write CSV header if the file is newly created
                val writer = FileWriter(logFile, true)
                writer.append("TimeStamp,Event\n")
                writer.flush()
                writer.close()
            }
            val writer = FileWriter(logFile, true)
            writer.append(timeStamp).append(",").append(event).append("\n")
            writer.flush()
            writer.close()

            // You can add a Toast or other UI feedback here
        } catch (e: IOException) {
            Log.e("Log",e.stackTraceToString())
            // Handle the exception
        }

    }
}