package com.mlbb.advisor.data

/**
 * Turns "I'm playing X against these enemies" into a six-item build plus a
 * separate set of counter items aimed at the enemy line-up.
 *
 * The logic is deliberately transparent: it reads the enemy team's damage
 * types and [Threat]s from the knowledge base and proposes counters
 * accordingly. No game data is read — the caller supplies the picks.
 */
object BuildAdvisor {

    fun advise(myHeroName: String, enemyNames: List<String>): Recommendation? {
        val me = HeroRepository.byName(myHeroName) ?: return null
        val enemies = enemyNames.mapNotNull { HeroRepository.byName(it) }

        val sixItems = buildSixItems(me)
        val counters = counterItems(me, enemies)
        val notes = matchupNotes(enemies)

        return Recommendation(
            heroName = me.name,
            role = me.role,
            powerSpike = me.powerSpike,
            emblem = me.emblem,
            emblemTalents = me.emblemTalents,
            battleSpell = me.battleSpell,
            battleSpellAlt = me.battleSpellAlt,
            sixItemBuild = sixItems,
            counterItems = counters,
            tips = me.tips,
            matchupNotes = notes
        )
    }

    // ------------------------------------------------------------------
    // Six-item build: hero's authored items, padded from a role template.
    // ------------------------------------------------------------------
    private fun buildSixItems(me: Hero): List<ItemAdvice> {
        val result = me.coreBuild.toMutableList()
        val present = result.map { normalize(it.name) }.toMutableSet()

        for (fill in roleTemplate(me)) {
            if (result.size >= 6) break
            if (normalize(fill) in present) continue
            present += normalize(fill)
            result += ItemAdvice(fill, "Rounds out the core build for your role.", Priority.LUXURY)
        }
        return result.take(6)
    }

    /** A sensible default six-item path per role + damage type, used to pad. */
    private fun roleTemplate(me: Hero): List<String> {
        val magic = me.damageType == DamageType.MAGIC
        return when (me.role) {
            Role.MARKSMAN -> listOf(
                "Swift Boots", "Demon Hunter Sword", "Berserker's Fury",
                "Scarlet Phantom", "Blade of Despair", "Wind of Nature"
            )
            Role.ASSASSIN -> if (magic) listOf(
                "Magic Shoes", "Calamity Reaper", "Glowing Wand",
                "Divine Glaive", "Holy Crystal", "Immortality"
            ) else listOf(
                "Swift Boots", "Endless Battle", "Blade of Despair",
                "Hunter Strike", "Malefic Roar", "Immortality"
            )
            Role.MAGE -> listOf(
                "Magic Shoes", "Clock of Destiny", "Lightning Truncheon",
                "Divine Glaive", "Holy Crystal", "Blood Wings"
            )
            Role.FIGHTER -> if (magic) listOf(
                "Magic Shoes", "Calamity Reaper", "Glowing Wand",
                "Divine Glaive", "Holy Crystal", "Athena's Shield"
            ) else listOf(
                "Warrior Boots", "Endless Battle", "War Axe",
                "Blade of Despair", "Malefic Roar", "Immortality"
            )
            Role.TANK -> listOf(
                "Tough Boots", "Dominance Ice", "Athena's Shield",
                "Antique Cuirass", "Guardian Helmet", "Immortality"
            )
            Role.SUPPORT -> listOf(
                "Magic Shoes", "Enchanted Talisman", "Oracle",
                "Dominance Ice", "Immortality", "Necklace of Durance"
            )
        }
    }

    // ------------------------------------------------------------------
    // Counter items: react to the enemy damage split and threat tags.
    // ------------------------------------------------------------------
    private fun counterItems(me: Hero, enemies: List<Hero>): List<ItemAdvice> {
        if (enemies.isEmpty()) return emptyList()

        val out = mutableListOf<ItemAdvice>()
        val physical = enemies.count { it.damageType == DamageType.PHYSICAL }
        val magic = enemies.count { it.damageType == DamageType.MAGIC }
        val threats = enemies.flatMap { it.threats }.toSet()
        val squishy = me.role in setOf(Role.MARKSMAN, Role.MAGE, Role.ASSASSIN)

        if (physical >= 2) {
            out += ItemAdvice(
                "Antique Cuirass",
                "$physical physical-damage enemies — stack armor and weaken their attackers.",
                Priority.SITUATIONAL
            )
            if (squishy) out += ItemAdvice(
                "Wind of Nature",
                "Active physical immunity to survive their physical core.",
                Priority.SITUATIONAL
            )
        }
        if (magic >= 2) {
            out += ItemAdvice(
                if (me.role == Role.TANK || me.role == Role.FIGHTER) "Athena's Shield" else "Rose Gold Meteor",
                "$magic magic-damage enemies — pick up magic resistance / a magic shield.",
                Priority.SITUATIONAL
            )
        }
        if (Threat.LIFESTEAL in threats) {
            out += ItemAdvice(
                if (me.damageType == DamageType.MAGIC) "Necklace of Durance" else "Sea Halberd",
                "Enemy has heavy lifesteal/regen — anti-heal cuts their sustain by ~50%.",
                Priority.SITUATIONAL
            )
        }
        if (Threat.TANKY in threats || enemies.count { it.role == Role.TANK } >= 2) {
            out += ItemAdvice(
                if (me.damageType == DamageType.MAGIC) "Divine Glaive" else "Malefic Roar",
                "Enemy is stacking defense — penetration keeps your damage relevant.",
                Priority.SITUATIONAL
            )
        }
        if (Threat.HEAVY_CC in threats) {
            out += ItemAdvice(
                if (squishy) "Tough Boots + Wind of Nature" else "Tough Boots",
                "Lots of crowd control — tenacity helps you survive chain-CC.",
                Priority.SITUATIONAL
            )
        }
        if (Threat.BURST in threats && squishy) {
            out += ItemAdvice(
                "Immortality / Winter Truncheon",
                "Enemy burst can one-combo you — a revive or freeze active buys a second life.",
                Priority.SITUATIONAL
            )
        }
        return out.distinctBy { normalize(it.name) }
    }

    private fun matchupNotes(enemies: List<Hero>): List<String> {
        if (enemies.isEmpty()) return emptyList()
        val notes = mutableListOf<String>()
        val threats = enemies.flatMap { it.threats }.toSet()
        if (Threat.HIGH_MOBILITY in threats)
            notes += "Enemy has high mobility — ward flanks and keep an escape/CC ready for dives."
        if (Threat.POKE in threats)
            notes += "Enemy pokes from range — don't sit in their poke before fights; engage on cooldowns."

        enemies.filter { it.threats.isNotEmpty() }
            .sortedByDescending { it.threats.size }
            .take(3)
            .forEach { e -> notes += "${e.name}: ${describe(e)}" }
        return notes
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

    /** Normalizes item names so "Tough Boots" and "tough boots" dedupe. */
    private fun normalize(name: String) = name.trim().lowercase()
}
