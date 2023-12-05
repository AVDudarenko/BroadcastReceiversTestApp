package com.example.broadcastreceiverstestapp

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

class MyReceiver : BroadcastReceiver() {
	override fun onReceive(context: Context?, intent: Intent?) {
		when (intent?.action) {
			Intent.ACTION_AIRPLANE_MODE_CHANGED -> {
				val tunedOn = intent.getBooleanExtra("state", false)
				Toast.makeText(
					context,
					"Airplane mode changed, turned on: $tunedOn",
					Toast.LENGTH_LONG
				).show()
			}

			Intent.ACTION_BATTERY_LOW -> {
				Toast.makeText(context, "Battery Low", Toast.LENGTH_SHORT).show()
			}

			ACTION_BUTTON_CLICK -> {
				val count = intent.getIntExtra("count", 0)
				Toast.makeText(context, "Button Clicked, count = $count", Toast.LENGTH_SHORT).show()
			}

			"loaded" -> {
				val percent = intent.getIntExtra("percent", 0)
				Toast.makeText(context, "Loading $percent", Toast.LENGTH_SHORT).show()

			}
		}
	}

	companion object {
		const val ACTION_BUTTON_CLICK = "button_clicked"
		const val EXTRA_COUNT = "count"
	}
}