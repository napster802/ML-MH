package com.mlbb.advisor.data

/**
 * Turns "I'm playing X against these enemies" into a prioritized set of
 * suggestions.
 *
 * The logic is deliberately transparent: it reads the enemy team's damage
 * types and [Threat]s from the knowledge base and proposes situational items
 * and matchup notes accordingly. No game data is read — the caller supplies
 * the picks.
 */
object BuildAdvisor {

    /**
     * @param myHeroName the hero the player is using.
     * @param enemyNames the enemy heroes that have been locked in so far.
     */
    fun advise(myHeroName: String, enemyNames: List<String>): Recommendation? {
        val me = HeroRepository.byName(myHeroName) ?: return null
        val enemies = enemyNames.mapNotNull { HeroRepository.byName(it) }

        val situational = mutableListOf<ItemAdvice>()
        val notes = mutableListOf<String>()

        // --- Tally the enemy composition --------------------------------
        val physical = enemies.count { it.damageType == DamageType.PHYSICAL }
        val magic = enemies.count { it.damageType == DamageType.MAGIC }
        val threats = enemies.flatMap { it.threats }.toSet()

        // --- Defensive itemization based on damage split ----------------
        val iAmSquishy = me.role in setOf(Role.MARKSMAN, Role.MAGE, Role.ASSASSIN)

        if (physical >= 2) {
            situational += ItemAdvice(
                "Antique Cuirass",
                "Enemy has $physical physical-damage heroes — stack armor and weaken their attackers.",
                Priority.SITUATIONAL
            )
            if (iAmSquishy) situational += ItemAdvice(
                "Wind of Nature / Wind Talker",
                "Active physical immunity to survive being focused by their physical core.",
                Priority.SITUATIONAL
            )
        }
        if (magic >= 2) {
            situational += ItemAdvice(
                if (me.role == Role.TANK || me.role == Role.FIGHTER) "Athena's Shield" else "Rose Gold Meteor",
                "Enemy has $magic magic-damage heroes — pick up magic resistance / a magic shield.",
                Priority.SITUATIONAL
            )
        }

        // --- Threat-driven reactions ------------------------------------
        if (Threat.LIFESTEAL in threats) {
            val antiHeal = if (me.damageType == DamageType.MAGIC)
                "Necklace of Durance" else "Sea Halberd"
            situational += ItemAdvice(
                antiHeal,
                "Enemy has heavy lifesteal/regen — anti-heal cuts their sustain by ~50%.",
                Priority.SITUATIONAL
            )
            notes += "Apply anti-heal BEFORE they ramp — heal reduction matters most in long fights."
        }
        if (Threat.TANKY in threats || enemies.count { it.role == Role.TANK } >= 2) {
            val pen = if (me.damageType == DamageType.MAGIC) "Divine Glaive" else "Malefic Roar"
            situational += ItemAdvice(
                pen,
                "Enemy is stacking defense — penetration keeps your damage relevant late.",
                Priority.SITUATIONAL
            )
        }
        if (Threat.HEAVY_CC in threats) {
            situational += ItemAdvice(
                if (iAmSquishy) "Tough Boots + Wind of Nature" else "Tough Boots",
                "Lots of crowd control on the enemy — tenacity helps you survive chain-CC.",
                Priority.SITUATIONAL
            )
            notes += "Bait or dodge their key CC before you commit; getting chain-stunned loses fights."
        }
        if (Threat.BURST in threats && iAmSquishy) {
            situational += ItemAdvice(
                "Immortality / Wind of Nature",
                "Enemy burst can one-combo you — a revive or immunity active buys a second life.",
                Priority.SITUATIONAL
            )
        }
        if (Threat.HIGH_MOBILITY in threats) {
            notes += "Enemy has high mobility — ward flanks and keep an escape/CC ready for dives."
        }
        if (Threat.POKE in threats) {
            notes += "Enemy pokes from range — don't sit in their poke before fights; engage on cooldowns."
        }

        // --- Per-enemy callouts (top threats) ---------------------------
        enemies.filter { it.threats.isNotEmpty() }
            .sortedByDescending { it.threats.size }
            .take(3)
            .forEach { e ->
                notes += "${e.name}: ${describe(e)}"
            }

        // Deduplicate situational items by name, keeping the first reason.
        val dedupSituational = situational.distinctBy { it.name }

        return Recommendation(
            heroName = me.name,
            powerSpike = me.powerSpike,
            coreBuild = me.coreBuild.filter { it.priority == Priority.CORE },
            situational = dedupSituational + me.coreBuild.filter { it.priority == Priority.LUXURY },
            tips = me.tips,
            matchupNotes = notes
        )
    }

    private fun describe(e: Hero): String {
        val t = e.threats
        return when {
            Threat.BURST in t && Threat.HIGH_MOBILITY in t -> "mobile burst assassin — respect their dive timing."
            Threat.SUSTAINED_DPS in t -> "scaling carry — kill them fast or shut down their farm."
            Threat.HEAVY_CC in t -> "primary engage/CC — track their ultimate."
            Threat.LIFESTEAL in t -> "sustain threat — prioritize anti-heal."
            Threat.POKE in t -> "ranged poke — don't get chipped before the fight."
            else -> "watch their cooldowns and play around them."
        }
    }
}
