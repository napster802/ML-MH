package com.mlbb.advisor.overlay

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.PixelFormat
import android.graphics.Typeface
import android.os.Build
import android.os.IBinder
import android.util.TypedValue
import android.view.Gravity
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView
import androidx.core.app.NotificationCompat
import com.mlbb.advisor.R
import com.mlbb.advisor.data.BuildAdvisor
import com.mlbb.advisor.data.Hero
import com.mlbb.advisor.data.HeroRepository
import com.mlbb.advisor.data.ItemAdvice
import com.mlbb.advisor.data.Role
import com.mlbb.advisor.ui.Visuals
import kotlin.math.abs

/**
 * Foreground service that hosts the floating advisor.
 *
 * A small draggable [bubble] is always present; tapping it toggles the
 * [panel]. Hero selection uses an in-panel picker (role filter + scrollable
 * list) rather than a Spinner, because Spinner dropdowns do not render inside
 * TYPE_APPLICATION_OVERLAY windows.
 */
class OverlayService : Service() {

    private lateinit var windowManager: WindowManager
    private var bubble: View? = null
    private var panel: View? = null

    // Selection state.
    private var myHero: String = "Lancelot"
    private val enemies = mutableListOf<String>()

    // Picker state.
    private var pickerForEnemy = false
    private var roleFilter: Role? = null

    // Panel view references (resolved when the panel is built).
    private var resultsContainer: LinearLayout? = null
    private var resultsScroll: View? = null
    private var pickerView: LinearLayout? = null
    private var roleFilterRow: LinearLayout? = null
    private var heroListContainer: LinearLayout? = null
    private var enemyChips: com.google.android.flexbox.FlexboxLayout? = null
    private var myHeroIcon: ImageView? = null
    private var myHeroName: TextView? = null
    private var pickerTitle: TextView? = null

    override fun onBind(intent: Intent?): IBinder? = null

    override fun onCreate() {
        super.onCreate()
        windowManager = getSystemService(Context.WINDOW_SERVICE) as WindowManager
        if (HeroRepository.byName(myHero) == null) myHero = HeroRepository.heroNames.first()
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
            getSystemService(NotificationManager::class.java).createNotificationChannel(channel)
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
            size, size, overlayType(),
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
            PixelFormat.TRANSLUCENT
        ).apply {
            gravity = Gravity.TOP or Gravity.START
            x = dp(12); y = dp(160)
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
        val metrics = resources.displayMetrics
        val panelWidth = minOf(metrics.widthPixels - dp(16), dp(380))
        val contentHeight = (metrics.heightPixels * 0.52f).toInt()

        val params = WindowManager.LayoutParams(
            panelWidth, WindowManager.LayoutParams.WRAP_CONTENT, overlayType(),
            WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL,
            PixelFormat.TRANSLUCENT
        ).apply {
            gravity = Gravity.TOP or Gravity.START
            x = dp(8); y = dp(40)
        }

        resultsContainer = view.findViewById(R.id.resultsContainer)
        resultsScroll = view.findViewById(R.id.resultsScroll)
        pickerView = view.findViewById(R.id.pickerView)
        roleFilterRow = view.findViewById(R.id.roleFilterRow)
        heroListContainer = view.findViewById(R.id.heroListContainer)
        enemyChips = view.findViewById(R.id.enemyChips)
        myHeroIcon = view.findViewById(R.id.myHeroIcon)
        myHeroName = view.findViewById(R.id.myHeroName)
        pickerTitle = view.findViewById(R.id.pickerTitle)

        // Clamp the swappable content area height so the panel fits on screen.
        view.findViewById<FrameLayout>(R.id.contentArea).layoutParams.height = contentHeight

        view.findViewById<TextView>(R.id.collapseButton).setOnClickListener { removePanel() }
        view.findViewById<View>(R.id.heroRow).setOnClickListener { openPicker(forEnemy = false) }
        view.findViewById<View>(R.id.enemyAddRow).setOnClickListener { openPicker(forEnemy = true) }
        attachDrag(view.findViewById(R.id.panelHeader), view, params)

        windowManager.addView(view, params)
        panel = view

        updateHeroHeader()
        refreshChips()
        recompute()
    }

    private fun removePanel() {
        panel?.let { runCatching { windowManager.removeView(it) } }
        panel = null
        resultsContainer = null; resultsScroll = null; pickerView = null
        roleFilterRow = null; heroListContainer = null; enemyChips = null
        myHeroIcon = null; myHeroName = null; pickerTitle = null
    }

    // ----------------------------------------------------------------------
    // Hero picker (in-panel, replaces the results area while open)
    // ----------------------------------------------------------------------
    private fun openPicker(forEnemy: Boolean) {
        pickerForEnemy = forEnemy
        roleFilter = null
        pickerTitle?.text = if (forEnemy) "Pick an enemy hero" else "Pick your hero"
        resultsScroll?.visibility = View.GONE
        pickerView?.visibility = View.VISIBLE
        buildRoleFilter()
        buildHeroList()
    }

    private fun closePicker() {
        pickerView?.visibility = View.GONE
        resultsScroll?.visibility = View.VISIBLE
    }

    private fun buildRoleFilter() {
        val row = roleFilterRow ?: return
        row.removeAllViews()
        val entries = listOf<Pair<String, Role?>>(
            "All" to null,
            "Tank" to Role.TANK,
            "Fighter" to Role.FIGHTER,
            "Assassin" to Role.ASSASSIN,
            "Mage" to Role.MAGE,
            "MM" to Role.MARKSMAN,
            "Support" to Role.SUPPORT
        )
        for ((label, role) in entries) {
            val selected = role == roleFilter
            val chip = TextView(this).apply {
                text = label
                textSize = 12f
                setTextColor(if (selected) Color.BLACK else getColor(R.color.text))
                setBackgroundResource(if (selected) R.drawable.button_bg else R.drawable.chip_bg)
                setPadding(dp(12), dp(6), dp(12), dp(6))
                setOnClickListener {
                    roleFilter = role
                    buildRoleFilter()
                    buildHeroList()
                }
            }
            val lp = ViewGroup.MarginLayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT
            ).apply { setMargins(0, 0, dp(6), 0) }
            row.addView(chip, lp)
        }
    }

    private fun buildHeroList() {
        val list = heroListContainer ?: return
        list.removeAllViews()
        val heroes = HeroRepository.heroes
            .filter { roleFilter == null || it.role == roleFilter }
            .sortedBy { it.name }
        for (hero in heroes) {
            list.addView(heroRow(hero))
        }
    }

    private fun heroRow(hero: Hero): View {
        val row = LinearLayout(this).apply {
            orientation = LinearLayout.HORIZONTAL
            gravity = Gravity.CENTER_VERTICAL
            setPadding(dp(4), dp(7), dp(4), dp(7))
            isClickable = true
            setOnClickListener { onHeroPicked(hero.name) }
        }
        row.addView(ImageView(this).apply {
            setImageDrawable(Visuals.heroIcon(hero))
            layoutParams = LinearLayout.LayoutParams(dp(30), dp(30))
        })
        row.addView(TextView(this).apply {
            text = hero.name
            setTextColor(getColor(R.color.text))
            textSize = 14f
            setPadding(dp(10), 0, 0, 0)
            layoutParams = LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1f)
        })
        row.addView(TextView(this).apply {
            text = Visuals.roleShort(hero.role)
            setTextColor(Visuals.roleColor(hero.role))
            textSize = 11f
            setTypeface(typeface, Typeface.BOLD)
        })
        return row
    }

    private fun onHeroPicked(name: String) {
        if (pickerForEnemy) {
            if (name !in enemies && enemies.size < 5) enemies.add(name)
        } else {
            myHero = name
            updateHeroHeader()
        }
        closePicker()
        refreshChips()
        recompute()
    }

    private fun updateHeroHeader() {
        val hero = HeroRepository.byName(myHero) ?: return
        myHeroName?.text = hero.name
        myHeroIcon?.setImageDrawable(Visuals.heroIcon(hero))
    }

    // ----------------------------------------------------------------------
    // Enemy chips
    // ----------------------------------------------------------------------
    private fun refreshChips() {
        val container = enemyChips ?: return
        container.removeAllViews()
        container.visibility = if (enemies.isEmpty()) View.GONE else View.VISIBLE
        for (name in enemies) {
            val hero = HeroRepository.byName(name)
            val chip = LinearLayout(this).apply {
                orientation = LinearLayout.HORIZONTAL
                gravity = Gravity.CENTER_VERTICAL
                setBackgroundResource(R.drawable.chip_bg)
                setPadding(dp(6), dp(4), dp(10), dp(4))
                setOnClickListener {
                    enemies.remove(name)
                    refreshChips()
                    recompute()
                }
            }
            if (hero != null) chip.addView(ImageView(this).apply {
                setImageDrawable(Visuals.heroIcon(hero))
                layoutParams = LinearLayout.LayoutParams(dp(20), dp(20))
            })
            chip.addView(TextView(this).apply {
                text = "  $name  ✕"
                setTextColor(Color.WHITE)
                textSize = 12f
            })
            val lp = ViewGroup.MarginLayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT
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

        addHeader(container, "EMBLEM & SPELL")
        addBody(container, "Emblem: ${rec.emblem}  —  ${rec.emblemTalents}")
        addBody(container, "Spell: ${rec.battleSpell}   (alt: ${rec.battleSpellAlt})")

        addHeader(container, "RECOMMENDED 6-ITEM BUILD")
        rec.sixItemBuild.forEachIndexed { i, item -> addItem(container, item, "${i + 1}") }

        if (rec.counterItems.isNotEmpty()) {
            addHeader(container, "COUNTER ITEMS VS ENEMY LINE-UP")
            rec.counterItems.forEach { addItem(container, it, null) }
        } else if (enemies.isEmpty()) {
            addHeader(container, "COUNTER ITEMS")
            addBody(container, "Add enemy heroes above to get counter-item suggestions.")
        }

        addHeader(container, "POWER SPIKE")
        addBody(container, rec.powerSpike)

        if (rec.matchupNotes.isNotEmpty()) {
            addHeader(container, "MATCHUP NOTES")
            rec.matchupNotes.forEach { addBullet(container, it) }
        }

        addHeader(container, "HERO TIPS")
        rec.tips.forEach { addBullet(container, it) }

        resultsScroll?.let { (it as? ScrollView)?.scrollTo(0, 0) }
    }

    private fun addHeader(parent: LinearLayout, text: String) {
        parent.addView(TextView(this).apply {
            this.text = text
            setTextColor(getColor(R.color.accent))
            textSize = 12f
            setTypeface(typeface, Typeface.BOLD)
            setPadding(0, dp(12), 0, dp(4))
        })
    }

    private fun addBody(parent: LinearLayout, text: String) {
        parent.addView(TextView(this).apply {
            this.text = text
            setTextColor(getColor(R.color.text))
            textSize = 13f
            setPadding(0, dp(2), 0, dp(2))
        })
    }

    /** Item row: generated icon + (optional slot number) name + reason. */
    private fun addItem(parent: LinearLayout, item: ItemAdvice, slot: String?) {
        val row = LinearLayout(this).apply {
            orientation = LinearLayout.HORIZONTAL
            setPadding(0, dp(5), 0, dp(5))
        }
        row.addView(ImageView(this).apply {
            setImageDrawable(Visuals.itemIcon(item.name))
            layoutParams = LinearLayout.LayoutParams(dp(30), dp(30))
        })
        val text = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(dp(10), 0, 0, 0)
            layoutParams = LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1f)
        }
        text.addView(TextView(this).apply {
            this.text = if (slot != null) "$slot. ${item.name}" else item.name
            setTextColor(getColor(R.color.text))
            textSize = 14f
            setTypeface(typeface, Typeface.BOLD)
        })
        text.addView(TextView(this).apply {
            this.text = item.reason
            setTextColor(getColor(R.color.text_dim))
            textSize = 12f
        })
        row.addView(text)
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
        view: View, params: WindowManager.LayoutParams, onTap: () -> Unit
    ) {
        var initialX = 0; var initialY = 0
        var touchX = 0f; var touchY = 0f
        var moved = false
        view.setOnTouchListener { _, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    initialX = params.x; initialY = params.y
                    touchX = event.rawX; touchY = event.rawY
                    moved = false; true
                }
                MotionEvent.ACTION_MOVE -> {
                    val dx = (event.rawX - touchX).toInt()
                    val dy = (event.rawY - touchY).toInt()
                    if (abs(dx) > dp(6) || abs(dy) > dp(6)) moved = true
                    params.x = initialX + dx; params.y = initialY + dy
                    runCatching { windowManager.updateViewLayout(view, params) }; true
                }
                MotionEvent.ACTION_UP -> { if (!moved) onTap(); true }
                else -> false
            }
        }
    }

    private fun attachDrag(handle: View, target: View, params: WindowManager.LayoutParams) {
        var initialX = 0; var initialY = 0
        var touchX = 0f; var touchY = 0f
        handle.setOnTouchListener { _, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    initialX = params.x; initialY = params.y
                    touchX = event.rawX; touchY = event.rawY; true
                }
                MotionEvent.ACTION_MOVE -> {
                    params.x = initialX + (event.rawX - touchX).toInt()
                    params.y = initialY + (event.rawY - touchY).toInt()
                    runCatching { windowManager.updateViewLayout(target, params) }; true
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
