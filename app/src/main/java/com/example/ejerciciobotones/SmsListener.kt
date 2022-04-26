package com.example.ejerciciobotones

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.provider.Telephony

class SmsListener : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent) {
        if (Telephony.Sms.Intents.SMS_RECEIVED_ACTION == intent.action) {
            for (smsMessage in Telephony.Sms.Intents.getMessagesFromIntent(intent)) {
                val messageBody = smsMessage.messageBody
                
                BroadcastObserver.getInstance().change(messageBody)
            }
        }
    }
}