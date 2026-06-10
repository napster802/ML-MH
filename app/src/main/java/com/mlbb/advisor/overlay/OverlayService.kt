package com.mlbb.advisor.overlay

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.PixelFormat
import android.os.Build
import android.os.IBinder
import android.util.TypedValue
import android.view.Gravity
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import android.widget.Spinner
import android.widget.TextView
import androidx.core.app.NotificationCompat
import com.mlbb.advisor.R
import com.mlbb.advisor.data.BuildAdvisor
import com.mlbb.advisor.data.HeroRepository
import com.mlbb.advisor.data.ItemAdvice
import com.mlbb.advisor.data.Priority
import kotlin.math.abs

/**
 * Foreground service that hosts the floating advisor.
 *
 * Two windows are managed: a small draggable [bubble] that's always present,
 * and an [panel] that expands when the bubble is tapped. The panel is the
 * focusable window (so spinners and back-press work); the bubble is not, so
 * it never steals touches from the game.
 */
class OverlayService : Service() {

    private lateinit var windowManager: WindowManager
    private var bubble: View? = null
    private var panel: View? = null

    // Selection state.
    private var myHero: String = HeroRepository.heroNames.first()
    private val enemies = mutableListOf<String>()

    // UI references inside the panel (resolved when the panel is built).
    private var resultsContainer: LinearLayout? = null
    private var enemyChips: com.google.android.flexbox.FlexboxLayout? = null

    override fun onBind(intent: Intent?): IBinder? = null

    override fun onCreate() {
        super.onCreate()
        windowManager = getSystemService(Context.WINDOW_SERVICE) as WindowManager
        startInForeground()
        showBubble()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int = START_STICKY

    override fun onDestroy() {
        super.onDestroy()
        removePanel()
        bubble?.let { runCatching { windowManager.removeView(it) } }
        bubble = null
    }

    // ----------------------------------------------------------------------
    // Foreground notification
    // ----------------------------------------------------------------------
    private fun startInForeground() {
        val channelId = "advisor_overlay"
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                getString(R.string.overlay_channel_name),
                NotificationManager.IMPORTANCE_LOW
            )
            (getSystemService(NotificationManager::class.java)).createNotificationChannel(channel)
        }
        val notification = NotificationCompat.Builder(this, channelId)
            .setContentTitle(getString(R.string.notif_title))
            .setContentText(getString(R.string.notif_text))
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setOngoing(true)
            .setPriority(NotificationCompat.PRIORITY_LOW)
            .build()
        startForeground(1, notification)
    }

    // ----------------------------------------------------------------------
    // Bubble window
    // ----------------------------------------------------------------------
    private fun showBubble() {
        val size = dp(52)
        val view = TextView(this).apply {
            text = "MA"
            gravity = Gravity.CENTER
            setTextColor(Color.WHITE)
            textSize = 16f
            setBackgroundResource(R.drawable.bubble_bg)
        }
        val params = WindowManager.LayoutParams(
            size, size,
            overlayType(),
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
            PixelFormat.TRANSLUCENT
        ).apply {
            gravity = Gravity.TOP or Gravity.START
            x = dp(12)
            y = dp(160)
        }
        attachDragAndTap(view, params) { togglePanel() }
        windowManager.addView(view, params)
        bubble = view
    }

    // ----------------------------------------------------------------------
    // Panel window
    // ----------------------------------------------------------------------
    private fun togglePanel() {
        if (panel == null) showPanel() else removePanel()
    }

    private fun showPanel() {
        val view = LayoutInflater.from(this).inflate(R.layout.overlay_panel, null)
        val params = WindowManager.LayoutParams(
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.WRAP_CONTENT,
            overlayType(),
            // Focusable so spinner dropdowns and back-press work, but not
            // touch-modal so taps outside still reach the game.
            WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL,
            PixelFormat.TRANSLUCENT
        ).apply {
            gravity = Gravity.TOP or Gravity.START
            x = dp(12)
            y = dp(220)
        }

        // Wire up controls.
        resultsContainer = view.findViewById(R.id.resultsContainer)
        enemyChips = view.findViewById(R.id.enemyChips)

        view.findViewById<TextView>(R.id.collapseButton).setOnClickListener { removePanel() }
        attachDrag(view.findViewById(R.id.panelHeader), view, params)

        setupHeroSpinner(view.findViewById(R.id.myHeroSpinner))
        setupEnemyAdder(
            view.findViewById(R.id.enemySpinner),
            view.findViewById(R.id.addEnemyButton)
        )

        windowManager.addView(view, params)
        panel = view
        refreshChips()
        recompute()
    }

    private fun removePanel() {
        panel?.let { runCatching { windowManager.removeView(it) } }
        panel = null
        resultsContainer = null
        enemyChips = null
    }

    // ----------------------------------------------------------------------
    // Spinners / pickers
    // ----------------------------------------------------------------------
    private fun spinnerAdapter(items: List<String>): ArrayAdapter<String> =
        ArrayAdapter(this, android.R.layout.simple_spinner_item, items).apply {
            setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        }

    private fun setupHeroSpinner(spinner: Spinner) {
        spinner.adapter = spinnerAdapter(HeroRepository.heroNames)
        spinner.setSelection(HeroRepository.heroNames.indexOf(myHero).coerceAtLeast(0))
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p: AdapterView<*>?, v: View?, pos: Int, id: Long) {
                myHero = HeroRepository.heroNames[pos]
                recompute()
            }
            override fun onNothingSelected(p: AdapterView<*>?) {}
        }
    }

    private fun setupEnemyAdder(spinner: Spinner, addButton: View) {
        spinner.adapter = spinnerAdapter(HeroRepository.heroNames)
        addButton.setOnClickListener {
            val pick = HeroRepository.heroNames[spinner.selectedItemPosition]
            if (pick !in enemies && enemies.size < 5) {
                enemies.add(pick)
                refreshChips()
                recompute()
            }
        }
    }

    // ----------------------------------------------------------------------
    // Enemy chips
    // ----------------------------------------------------------------------
    private fun refreshChips() {
        val container = enemyChips ?: return
        container.removeAllViews()
        container.visibility = if (enemies.isEmpty()) View.GONE else View.VISIBLE
        enemies.forEach { name ->
            val chip = TextView(this).apply {
                text = "$name  ✕"
                setTextColor(Color.WHITE)
                textSize = 12f
                setBackgroundResource(R.drawable.chip_bg)
                setPadding(dp(12), dp(6), dp(12), dp(6))
                setOnClickListener {
                    enemies.remove(name)
                    refreshChips()
                    recompute()
                }
            }
            val lp = ViewGroup.MarginLayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            ).apply { setMargins(0, dp(2), dp(6), dp(4)) }
            container.addView(chip, lp)
        }
    }

    // ----------------------------------------------------------------------
    // Rendering the recommendation
    // ----------------------------------------------------------------------
    private fun recompute() {
        val container = resultsContainer ?: return
        container.removeAllViews()

        val rec = BuildAdvisor.advise(myHero, enemies) ?: return

        addHeader(container, "POWER SPIKE")
        addBody(container, rec.powerSpike)

        addHeader(container, "CORE BUILD")
        rec.coreBuild.forEach { addItem(container, it, getColor(R.color.core)) }

        if (rec.situational.isNotEmpty()) {
            addHeader(container, if (enemies.isEmpty()) "EXTRA OPTIONS" else "VS THIS ENEMY LINE-UP")
            rec.situational.forEach {
                val color = if (it.priority == Priority.SITUATIONAL)
                    getColor(R.color.situational) else getColor(R.color.text_dim)
                addItem(container, it, color)
            }
        }

        if (rec.matchupNotes.isNotEmpty()) {
            addHeader(container, "MATCHUP NOTES")
            rec.matchupNotes.forEach { addBullet(container, it) }
        }

        addHeader(container, "HERO TIPS")
        rec.tips.forEach { addBullet(container, it) }
    }

    private fun addHeader(parent: LinearLayout, text: String) {
        val tv = TextView(this).apply {
            this.text = text
            setTextColor(getColor(R.color.accent))
            textSize = 12f
            setTypeface(typeface, android.graphics.Typeface.BOLD)
            setPadding(0, dp(10), 0, dp(4))
        }
        parent.addView(tv)
    }

    private fun addBody(parent: LinearLayout, text: String) {
        parent.addView(TextView(this).apply {
            this.text = text
            setTextColor(getColor(R.color.text))
            textSize = 13f
        })
    }

    private fun addItem(parent: LinearLayout, item: ItemAdvice, dotColor: Int) {
        val row = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(0, dp(4), 0, dp(4))
        }
        row.addView(TextView(this).apply {
            text = "• ${item.name}"
            setTextColor(dotColor)
            textSize = 14f
            setTypeface(typeface, android.graphics.Typeface.BOLD)
        })
        row.addView(TextView(this).apply {
            text = item.reason
            setTextColor(getColor(R.color.text_dim))
            textSize = 12f
            setPadding(dp(12), 0, 0, 0)
        })
        parent.addView(row)
    }

    private fun addBullet(parent: LinearLayout, text: String) {
        parent.addView(TextView(this).apply {
            this.text = "› $text"
            setTextColor(getColor(R.color.text))
            textSize = 13f
            setPadding(0, dp(3), 0, dp(3))
        })
    }

    // ----------------------------------------------------------------------
    // Touch handling: drag + tap
    // ----------------------------------------------------------------------
    private fun attachDragAndTap(
        view: View,
        params: WindowManager.LayoutParams,
        onTap: () -> Unit
    ) {
        var initialX = 0
        var initialY = 0
        var touchX = 0f
        var touchY = 0f
        var moved = false
        view.setOnTouchListener { _, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    initialX = params.x; initialY = params.y
                    touchX = event.rawX; touchY = event.rawY
                    moved = false
                    true
                }
                MotionEvent.ACTION_MOVE -> {
                    val dx = (event.rawX - touchX).toInt()
                    val dy = (event.rawY - touchY).toInt()
                    if (abs(dx) > dp(6) || abs(dy) > dp(6)) moved = true
                    params.x = initialX + dx
                    params.y = initialY + dy
                    runCatching { windowManager.updateViewLayout(view, params) }
                    true
                }
                MotionEvent.ACTION_UP -> {
                    if (!moved) onTap()
                    true
                }
                else -> false
            }
        }
    }

    /** Drag-only handler used for the panel header. */
    private fun attachDrag(handle: View, target: View, params: WindowManager.LayoutParams) {
        var initialX = 0
        var initialY = 0
        var touchX = 0f
        var touchY = 0f
        handle.setOnTouchListener { _, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    initialX = params.x; initialY = params.y
                    touchX = event.rawX; touchY = event.rawY
                    true
                }
                MotionEvent.ACTION_MOVE -> {
                    params.x = initialX + (event.rawX - touchX).toInt()
                    params.y = initialY + (event.rawY - touchY).toInt()
                    runCatching { windowManager.updateViewLayout(target, params) }
                    true
                }
                else -> false
            }
        }
    }

    // ----------------------------------------------------------------------
    // Helpers
    // ----------------------------------------------------------------------
    private fun overlayType(): Int =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
            WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
        else
            @Suppress("DEPRECATION") WindowManager.LayoutParams.TYPE_PHONE

    private fun dp(value: Int): Int = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP, value.toFloat(), resources.displayMetrics
    ).toInt()

    companion object {
        fun start(context: Context) {
            val intent = Intent(context, OverlayService::class.java)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
                context.startForegroundService(intent)
            else context.startService(intent)
        }

        fun stop(context: Context) {
            context.stopService(Intent(context, OverlayService::class.java))
        }
    }
}
