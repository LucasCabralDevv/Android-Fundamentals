package com.lucascabral.androidfundamentals

import android.Manifest
import android.content.ContentResolver
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.lucascabral.androidfundamentals.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonContacts.setOnClickListener {
            getPhoneContacts()
        }

        binding.buttonBroadcastMessage.setOnClickListener {
            val intent = Intent()
            intent.action = "com.lucascabral.myBroadcastMessage"
            intent.flags = Intent.FLAG_INCLUDE_STOPPED_PACKAGES
            sendBroadcast(intent)
        }

        val intentFilter = IntentFilter("android.intent.action.BATTERY_LOW")
        val objReceiver = MyBroadcastReceiver()
        registerReceiver(objReceiver, intentFilter)
    }

    private fun getPhoneContacts() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS)
            != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_CONTACTS), 0)
        }

        val contentResolver: ContentResolver = contentResolver
        val uri: Uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI
        val cursor: Cursor? = contentResolver.query(uri, null, null, null, null)
        Log.i("CONTACT_PROVIDER_DEMO", "TOTAL of Contacts: ${cursor?.count}")
        cursor?.let {
            while (cursor.moveToNext()) {
                val contactName: String =
                    cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME))
                val contactNumber =
                    cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
                Log.i("CONTACT_PROVIDER_DEMO", "Contact Name: $contactName, Number: $contactNumber")
            }
        }
    }
}