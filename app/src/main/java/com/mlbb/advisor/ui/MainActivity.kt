package com.mlbb.advisor.ui

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.mlbb.advisor.databinding.ActivityMainBinding
import com.mlbb.advisor.overlay.OverlayService

/**
 * Launcher screen. Its only jobs are to request the overlay permission and
 * to start/stop the floating advisor service. All the interesting work
 * happens inside the overlay itself.
 */
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        maybeRequestNotificationPermission()

        binding.grantButton.setOnClickListener { requestOverlayPermission() }
        binding.startButton.setOnClickListener {
            if (canDrawOverlays()) {
                OverlayService.start(this)
                finish() // get out of the way so the user can open the game
            } else {
                requestOverlayPermission()
            }
        }
        binding.stopButton.setOnClickListener { OverlayService.stop(this) }
    }

    override fun onResume() {
        super.onResume()
        refreshStatus()
    }

    private fun refreshStatus() {
        val granted = canDrawOverlays()
        binding.statusText.text = if (granted)
            "Overlay permission: granted ✓"
        else
            "Overlay permission: not granted"
        binding.grantButton.isEnabled = !granted
    }

    private fun maybeRequestNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU &&
            ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS)
            != PackageManager.PERMISSION_GRANTED
        ) {
            requestPermissions(arrayOf(Manifest.permission.POST_NOTIFICATIONS), 1001)
        }
    }

    private fun canDrawOverlays(): Boolean =
        Build.VERSION.SDK_INT < Build.VERSION_CODES.M || Settings.canDrawOverlays(this)

    private fun requestOverlayPermission() {
        if (canDrawOverlays()) {
            refreshStatus()
            return
        }
        val intent = Intent(
            Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
            Uri.parse("package:$packageName")
        )
        startActivity(intent)
    }
}
