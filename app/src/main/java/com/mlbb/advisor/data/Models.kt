package com.mlbb.advisor.data

/**
 * Core data model for the advisor's knowledge base.
 *
 * Everything here is static, hand-authored game knowledge — the kind of
 * information you'd find on a hero guide site. The app never reads anything
 * from the running game; the player tells it who is playing what.
 */

enum class Role { TANK, FIGHTER, ASSASSIN, MAGE, MARKSMAN, SUPPORT }

/** Primary damage type a hero deals — drives defensive item suggestions. */
enum class DamageType { PHYSICAL, MAGIC, MIXED }

/** A trait an enemy hero can have that the advisor reacts to. */
enum class Threat {
    BURST,          // deletes you in one combo (e.g. assassins, burst mages)
    SUSTAINED_DPS,  // marksmen / continuous damage
    HEAVY_CC,       // lots of stuns / suppression
    LIFESTEAL,      // spell vamp / regen heroes — counter with anti-heal
    HIGH_MOBILITY,  // blink / dash heavy — counter with CC / movement slow
    TANKY,          // stacks defense — counter with penetration / true damage
    POKE            // long-range chip damage
}

/** An item recommendation, tagged with why it is being suggested. */
data class ItemAdvice(
    val name: String,
    val reason: String,
    val priority: Priority
)

enum class Priority { CORE, SITUATIONAL, LUXURY }

/** A single hero entry in the knowledge base. */
data class Hero(
    val name: String,
    val role: Role,
    val damageType: DamageType,
    val threats: Set<Threat>,
    /** The default build path you almost always want on this hero. */
    val coreBuild: List<ItemAdvice>,
    /** General tips for piloting this hero well. */
    val tips: List<String>,
    /** Power-spike note: when this hero is strongest in the match. */
    val powerSpike: String,
    /** Recommended emblem set (e.g. "Assassin", "Custom Mage"). */
    val emblem: String,
    /** Key emblem talent line, top to bottom. */
    val emblemTalents: String,
    /** Primary battle spell. */
    val battleSpell: String,
    /** A viable alternative battle spell for other situations. */
    val battleSpellAlt: String
)

/** The full set of suggestions the overlay renders for a match. */
data class Recommendation(
    val heroName: String,
    val role: Role,
    val powerSpike: String,
    val emblem: String,
    val emblemTalents: String,
    val battleSpell: String,
    val battleSpellAlt: String,
    /** Exactly six items: the recommended full build for the picked hero. */
    val sixItemBuild: List<ItemAdvice>,
    /** Items to consider swapping in to counter the enemy line-up. */
    val counterItems: List<ItemAdvice>,
    val tips: List<String>,
    val matchupNotes: List<String>
)
