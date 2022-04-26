package com.example.ejerciciobotones

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.telephony.SmsManager
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import android.widget.ToggleButton
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.material.switchmaterial.SwitchMaterial

@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val luz: ToggleButton = findViewById(R.id.light)
        val puerta: SwitchMaterial = findViewById(R.id.door)
        val textMessage: TextView = findViewById(R.id.textMessage)
        val permissionRequest = 101
        luz.isEnabled = false
        puerta.isEnabled = false


        if (checkPermission(Manifest.permission.SEND_SMS) && checkPermission(Manifest.permission.RECEIVE_SMS)) {
            luz.isEnabled = true
            puerta.isEnabled = true
        } else {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.SEND_SMS, Manifest.permission.RECEIVE_SMS),
                permissionRequest
            )
        }

        luz.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                val smsManager: SmsManager = SmsManager.getDefault()
                smsManager.sendTextMessage("+573185149924", null, "LUZ ON", null, null)
                Toast.makeText(this, "Mensaje enviado", Toast.LENGTH_SHORT).show()
            } else {
                val smsManager: SmsManager = SmsManager.getDefault()
                smsManager.sendTextMessage("+573185149924", null, "LUZ OFF", null, null)
                Toast.makeText(this, "Mensaje enviado", Toast.LENGTH_SHORT).show()
            }
        }

        puerta.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                val smsManager: SmsManager = SmsManager.getDefault()
                smsManager.sendTextMessage("+573185149924", null, "ABRIR PUERTA", null, null)
                Toast.makeText(this, "Mensaje enviado", Toast.LENGTH_SHORT).show()
            } else {
                val smsManager: SmsManager = SmsManager.getDefault()
                smsManager.sendTextMessage("+573185149924", null, "CERRAR PUERTA", null, null)
                Toast.makeText(this, "Mensaje enviado", Toast.LENGTH_SHORT).show()
            }
        }

        BroadcastObserver.getInstance().addObserver { _, message ->
            Log.e("Message", message.toString())
            when (message) {
                "LUZ ON" -> textMessage.text = "Encender luz"
                "LUZ OFF" -> textMessage.text = "Apagar luz"
                "ABRIR PUERTA" -> textMessage.text = "Abrir puerta"
                "CERRAR PUERTA" -> textMessage.text = "Cerrar puerta"
            }
        }
    }

    private fun checkPermission(permission: String?): Boolean {
        val check = ContextCompat.checkSelfPermission(this, permission!!)
        return check == PackageManager.PERMISSION_GRANTED
    }
}

