package com.example.broadcastreceiverstestapp

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

	private val receiver = MyReceiver()
	private var count = 0
	private lateinit var progressBar: ProgressBar

	private val receiverMain = object : BroadcastReceiver() {
		override fun onReceive(context: Context?, intent: Intent?) {
			if (intent?.action == "loaded") {
				val percent = intent.getIntExtra("percent", 0)
				progressBar.progress = percent
			}
		}

	}

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)

		progressBar = findViewById(R.id.pbLoading)

		findViewById<Button>(R.id.btnAction).setOnClickListener {
			Intent(MyReceiver.ACTION_BUTTON_CLICK).apply {
				putExtra(MyReceiver.EXTRA_COUNT, count++)
				sendBroadcast(this)
			}
		}

		val intentFilter = IntentFilter().apply {
			addAction(Intent.ACTION_AIRPLANE_MODE_CHANGED)
			addAction(Intent.ACTION_BATTERY_LOW)
			addAction(MyReceiver.ACTION_BUTTON_CLICK)
			addAction("loaded")
		}
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
			registerReceiver(receiver, intentFilter, RECEIVER_EXPORTED)
			registerReceiver(receiverMain, intentFilter, RECEIVER_EXPORTED)
		} else {
			registerReceiver(receiver, intentFilter)
			registerReceiver(receiverMain, intentFilter)
		}
		Intent(this, MyService::class.java).apply {
			startService(this)
		}
	}

	override fun onDestroy() {
		super.onDestroy()
		unregisterReceiver(receiver)
		unregisterReceiver(receiverMain)
	}
}