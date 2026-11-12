package com.pokanat.messagemanager

import android.Manifest
import android.app.Activity
import android.os.Bundle
import android.content.pm.PackageManager
import android.widget.*
import android.view.ViewGroup

class MainActivity : Activity() {
 override fun onCreate(savedInstanceState: Bundle?) {
  super.onCreate(savedInstanceState)
  val box=LinearLayout(this).apply { orientation=LinearLayout.VERTICAL; setPadding(32,32,32,32) }
  val title=TextView(this).apply { text="Message Manager Companion"; textSize=22f }
  val notice=TextView(this).apply { text="Stores ordinary SMS locally on this phone. Verification codes are excluded. No messages are uploaded.\n\nThis app does not connect or register WhatsApp accounts." }
  val request=Button(this).apply { text="Allow incoming SMS"; setOnClickListener { if(checkSelfPermission(Manifest.permission.RECEIVE_SMS)!=PackageManager.PERMISSION_GRANTED) requestPermissions(arrayOf(Manifest.permission.RECEIVE_SMS),100) } }
  val list=TextView(this)
  val refresh=Button(this).apply { text="Refresh local inbox"; setOnClickListener { list.text=getSharedPreferences("sms",MODE_PRIVATE).getString("recent","No ordinary SMS received yet.") } }
  box.addView(title);box.addView(notice);box.addView(request);box.addView(refresh);box.addView(list)
  setContentView(box)
  list.text=getSharedPreferences("sms",MODE_PRIVATE).getString("recent","No ordinary SMS received yet.")
 }
}