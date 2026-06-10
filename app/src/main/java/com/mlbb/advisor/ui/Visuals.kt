package com.mlbb.advisor.ui

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.RectF
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import com.mlbb.advisor.data.Hero
import com.mlbb.advisor.data.Role

/**
 * Self-contained, generated icons for heroes and items.
 *
 * Official hero/item artwork is copyrighted and can't be bundled, so instead
 * we draw distinct, color-coded badges with initials: heroes are circles
 * colored by role, items are rounded squares colored by item category. This
 * makes everything visually identifiable at a glance without shipping any
 * third-party images. (To use real art later, drop PNGs in res/drawable and
 * swap these calls for resource lookups.)
 */
object Visuals {

    // --- Role palette (hero circles) ------------------------------------
    fun roleColor(role: Role): Int = when (role) {
        Role.TANK -> Color.parseColor("#5C6BC0")
        Role.FIGHTER -> Color.parseColor("#EF5350")
        Role.ASSASSIN -> Color.parseColor("#AB47BC")
        Role.MAGE -> Color.parseColor("#42A5F5")
        Role.MARKSMAN -> Color.parseColor("#FFA726")
        Role.SUPPORT -> Color.parseColor("#66BB6A")
    }

    fun roleShort(role: Role): String = when (role) {
        Role.TANK -> "TANK"
        Role.FIGHTER -> "FIGHT"
        Role.ASSASSIN -> "ASSN"
        Role.MAGE -> "MAGE"
        Role.MARKSMAN -> "MM"
        Role.SUPPORT -> "SUPP"
    }

    fun heroIcon(hero: Hero): Drawable =
        BadgeDrawable(initials(hero.name), roleColor(hero.role), circle = true)

    // --- Item category palette (item squares) ---------------------------
    enum class ItemCategory(val colorHex: String) {
        ATTACK("#FF7043"),
        MAGIC("#7E57C2"),
        DEFENSE("#78909C"),
        MOVEMENT("#26A69A"),
        JUNGLE("#8D6E63"),
        OTHER("#90A4AE")
    }

    fun itemCategory(name: String): ItemCategory {
        val n = name.lowercase()
        if (n.contains("boots") || n.contains("shoes")) return ItemCategory.MOVEMENT
        if (n.contains("retribution")) return ItemCategory.JUNGLE
        if (DEFENSE_KEYS.any { n.contains(it) }) return ItemCategory.DEFENSE
        if (MAGIC_KEYS.any { n.contains(it) }) return ItemCategory.MAGIC
        if (ATTACK_KEYS.any { n.contains(it) }) return ItemCategory.ATTACK
        return ItemCategory.OTHER
    }

    fun itemIcon(name: String): Drawable {
        val cat = itemCategory(name)
        return BadgeDrawable(itemAbbrev(name), Color.parseColor(cat.colorHex), circle = false)
    }

    // --- helpers --------------------------------------------------------
    private fun initials(name: String): String {
        val words = name.split(Regex("[^A-Za-z]+")).filter { it.isNotEmpty() }
        return when {
            words.isEmpty() -> "?"
            words.size == 1 -> words[0].take(2).uppercase()
            else -> (words[0].first().toString() + words[1].first()).uppercase()
        }
    }

    private fun itemAbbrev(name: String): String {
        val words = name.split(Regex("[^A-Za-z]+"))
            .filter { it.isNotEmpty() && it.lowercase() !in SMALL_WORDS }
        return when {
            words.isEmpty() -> name.take(2).uppercase()
            words.size == 1 -> words[0].take(3).uppercase()
            else -> words.take(3).joinToString("") { it.first().uppercase() }
        }
    }

    private val SMALL_WORDS = setOf("of", "the", "and")

    private val MAGIC_KEYS = listOf(
        "calamity", "glowing wand", "divine glaive", "holy crystal",
        "lightning truncheon", "clock of destiny", "ice queen", "genius wand",
        "blood wings", "winter truncheon", "concentrated energy",
        "enchanted talisman", "necklace of durance", "feather of heaven",
        "starlium", "fleeting time"
    )
    private val DEFENSE_KEYS = listOf(
        "antique cuirass", "athena", "immortality", "dominance ice",
        "guardian helmet", "brute force", "radiant", "twilight armor",
        "thunder belt", "cursed helmet", "oracle", "rose gold",
        "queen's wings", "wind of nature", "wind talker", "dreadnaught",
        "winter truncheon"
    )
    private val ATTACK_KEYS = listOf(
        "endless battle", "blade of despair", "hunter strike", "malefic roar",
        "berserker", "scarlet phantom", "demon hunter", "corrosion scythe",
        "golden staff", "war axe", "bloodlust axe", "sea halberd",
        "great dragon", "haas", "heptaseas", "sky piercer", "blade of heptaseas"
    )
}

/**
 * Draws a colored badge (circle or rounded square) with centered initials.
 * Used for both hero and item icons so the overlay needs no image assets.
 */
class BadgeDrawable(
    private val label: String,
    private val bgColor: Int,
    private val circle: Boolean
) : Drawable() {

    private val bgPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = bgColor
        style = Paint.Style.FILL
    }
    private val textPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.WHITE
        typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
        textAlign = Paint.Align.CENTER
    }
    private val textBounds = Rect()

    override fun draw(canvas: Canvas) {
        val b = bounds
        if (circle) {
            canvas.drawCircle(b.exactCenterX(), b.exactCenterY(), b.width() / 2f, bgPaint)
        } else {
            val r = b.width() * 0.22f
            canvas.drawRoundRect(RectF(b), r, r, bgPaint)
        }
        // Size the text to fit the badge.
        textPaint.textSize = b.height() * (if (label.length >= 3) 0.34f else 0.42f)
        textPaint.getTextBounds(label, 0, label.length, textBounds)
        val y = b.exactCenterY() - textBounds.exactCenterY()
        canvas.drawText(label, b.exactCenterX(), y, textPaint)
    }

    // Nominal intrinsic size so ImageView lays the badge out and scales it.
    override fun getIntrinsicWidth(): Int = 96
    override fun getIntrinsicHeight(): Int = 96

    override fun setAlpha(alpha: Int) { bgPaint.alpha = alpha }
    override fun setColorFilter(cf: android.graphics.ColorFilter?) { bgPaint.colorFilter = cf }
    @Deprecated("Deprecated in Drawable")
    override fun getOpacity(): Int = android.graphics.PixelFormat.TRANSLUCENT
}
