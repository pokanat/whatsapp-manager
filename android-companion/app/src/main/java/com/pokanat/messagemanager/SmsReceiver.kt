package com.pokanat.messagemanager

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.provider.Telephony
import java.text.DateFormat
import java.util.Date

class SmsReceiver : BroadcastReceiver() {
 override fun onReceive(context: Context, intent: Intent) {
  if(intent.action != Telephony.Sms.Intents.SMS_RECEIVED_ACTION) return
  val sms=Telephony.Sms.Intents.getMessagesFromIntent(intent)
  if(sms.isEmpty()) return
  val message=sms.joinToString("") { it.messageBody ?: "" }
  // Avoid retaining likely authentication messages.
  if(Regex("(?i)\\b(otp|verification|verify|security code|one.time password|login code|authentication|2fa|passcode)\\b").containsMatchIn(message)) return
  if(Regex("\\b\\d{4,8}\\b").containsMatchIn(message)) return
  val sender=sms.first().originatingAddress ?: "Unknown"
  val entry="${DateFormat.getDateTimeInstance().format(Date())} — $sender\n$message"
  val prefs=context.getSharedPreferences("sms",Context.MODE_PRIVATE)
  val old=prefs.getString("recent","") ?: ""
  prefs.edit().putString("recent",(entry+"\n\n"+old).take(20000)).apply()
 }
}