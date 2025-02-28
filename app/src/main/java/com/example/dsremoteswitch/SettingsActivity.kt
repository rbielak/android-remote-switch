package com.example.dsremoteswitch

import android.content.Context
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class SettingsActivity : AppCompatActivity() {

    private lateinit var etPhoneNumber: EditText
    private lateinit var etOnMessage: EditText
    private lateinit var etOffMessage: EditText
    private lateinit var btnSave: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        etPhoneNumber = findViewById(R.id.etPhoneNumber)
        etOnMessage = findViewById(R.id.etOnMessage)
        etOffMessage = findViewById(R.id.etOffMessage)
        btnSave = findViewById(R.id.btnSave)

        loadSettings()

        btnSave.setOnClickListener {
            saveSettings()
        }
    }

    private fun loadSettings() {
        val sharedPreferences = getSharedPreferences("Settings", Context.MODE_PRIVATE)
        etPhoneNumber.setText(sharedPreferences.getString("phoneNumber", ""))
        etOnMessage.setText(sharedPreferences.getString("onMessage", ""))
        etOffMessage.setText(sharedPreferences.getString("offMessage", ""))
    }

    private fun saveSettings() {
        val sharedPreferences = getSharedPreferences("Settings", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("phoneNumber", etPhoneNumber.text.toString())
        editor.putString("onMessage", etOnMessage.text.toString())
        editor.putString("offMessage", etOffMessage.text.toString())
        editor.apply()
        finish() // Close the settings activity
    }
}