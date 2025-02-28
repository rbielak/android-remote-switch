package com.example.dsremoteswitch

import android.Manifest.permission.READ_SMS
import android.Manifest.permission.RECEIVE_SMS
import android.Manifest.permission.SEND_SMS
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.telephony.SmsManager
import android.telephony.SmsMessage
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat

class MainActivity : AppCompatActivity() {

    private lateinit var btnOn: Button
    private lateinit var btnOff: Button
    private lateinit var btnSettings: Button
    private lateinit var tvResponse: TextView

    private var phoneNumber: String = ""
    private var onMessage: String = ""
    private var offMessage: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnOn = findViewById(R.id.btnOn)
        btnOff = findViewById(R.id.btnOff)
        btnSettings = findViewById(R.id.btnSettings)
        tvResponse = findViewById(R.id.tvResponse)

        // Load saved settings
        loadSettings()

        btnOn.setOnClickListener {
            sendSMS(phoneNumber, onMessage, "ON message sent.")
        }

        btnOff.setOnClickListener {
            sendSMS(phoneNumber, offMessage, "OFF message sent.")
        }

        btnSettings.setOnClickListener {
            val intent = Intent(this, SettingsActivity::class.java)
            startActivity(intent)
        }

        requestPermissions()
    }

    private fun requestPermissions() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(SEND_SMS, RECEIVE_SMS, READ_SMS),
            1
        )
    }

    private fun sendSMS(phoneNumber: String, message: String, userMsg: String) {
        try {
            val smsManager: SmsManager = SmsManager.getDefault()
            smsManager.sendTextMessage(phoneNumber, null, message, null, null)
            Toast.makeText(this, "Message sent", Toast.LENGTH_SHORT).show()
            waitForResponse(userMsg)
        } catch (e: Exception) {
            Toast.makeText(this, "Failed to send message", Toast.LENGTH_SHORT).show()
        }
    }

    private fun waitForResponse(userMsg: String) {
        tvResponse.text = userMsg
    }

    private fun loadSettings() {
        val sharedPreferences = getSharedPreferences("Settings", MODE_PRIVATE)
        phoneNumber = sharedPreferences.getString("phoneNumber", "") ?: ""
        onMessage = sharedPreferences.getString("onMessage", "") ?: ""
        offMessage = sharedPreferences.getString("offMessage", "") ?: ""
    }

    private val smsReceiver = object : BroadcastReceiver() {
        // TODO: fix this - this code does not work.
        override fun onReceive(context: Context, intent: Intent) {
            if (intent.action == "android.provider.Telephony.SMS_RECEIVED") {
                val bundle = intent.extras
                if (bundle != null) {
                    val pdus = bundle.get("pdus") as Array<*>
                    for (pdu in pdus) {
                        val smsMessage = SmsMessage.createFromPdu(pdu as ByteArray)
                        val sender = smsMessage.displayOriginatingAddress
                        if (sender == phoneNumber) {
                            val messageBody = smsMessage.messageBody
                            tvResponse.text = "Response: $messageBody"
                        }
                    }
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        val filter = IntentFilter("android.provider.Telephony.SMS_RECEIVED")
        registerReceiver(smsReceiver, filter)
        // Reload settings when returning from SettingsActivity
        loadSettings()
    }

    override fun onPause() {
        super.onPause()
        unregisterReceiver(smsReceiver)
    }
}
