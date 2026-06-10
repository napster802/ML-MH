package com.mlbb.advisor.data

/**
 * Hand-authored knowledge base of heroes, builds and tips.
 *
 * This is intentionally a starter set covering popular picks across every
 * role. Add to [heroes] to grow coverage — the advisor logic in
 * [BuildAdvisor] works off these fields generically, so new heroes need no
 * code changes.
 */
object HeroRepository {

    // --- Shorthand helpers for readability -------------------------------

    private fun core(name: String, reason: String) = ItemAdvice(name, reason, Priority.CORE)
    private fun lux(name: String, reason: String) = ItemAdvice(name, reason, Priority.LUXURY)

    val heroes: List<Hero> = listOf(

        // ----------------------------- ASSASSINS -----------------------------
        Hero(
            name = "Lancelot",
            role = Role.ASSASSIN,
            damageType = DamageType.PHYSICAL,
            threats = setOf(Threat.BURST, Threat.HIGH_MOBILITY),
            coreBuild = listOf(
                core("Swift Boots", "Attack speed helps weave basic attacks between dashes."),
                core("Endless Battle", "True damage on first hit after a skill — your bread and butter."),
                core("Blade of Despair", "Massive flat attack damage to one-combo squishies."),
                lux("Hunter Strike", "Extra penetration and a movement-speed spike for chasing."),
                lux("Malefic Roar", "Shreds armor once enemies start stacking defense."),
                lux("Immortality", "Insurance revive when you dive the back line.")
            ),
            tips = listOf(
                "Your dashes grant brief immunity — time them to dodge skillshots and stuns.",
                "Hit the sweet-spot tip of your second skill for the bonus damage zone.",
                "Don't show yourself early; rotate after level 4 for ganks."
            ),
            powerSpike = "Mid game (after Endless Battle) — pick off isolated targets."
        ),
        Hero(
            name = "Gusion",
            role = Role.ASSASSIN,
            damageType = DamageType.MAGIC,
            threats = setOf(Threat.BURST, Threat.HIGH_MOBILITY),
            coreBuild = listOf(
                core("Magic Shoes", "Cooldown reduction so your dagger combo resets faster."),
                core("Calamity Reaper", "Mana, CDR and a true-damage proc after casting."),
                core("Glowing Wand", "Burn damage that scales beautifully with your burst."),
                lux("Divine Glaive", "Magic penetration once enemies buy resist."),
                lux("Holy Crystal", "Top-end magic power for one-shot combos."),
                lux("Immortality", "Survive the dive, recast, finish.")
            ),
            tips = listOf(
                "Practice the recall-dagger combo: throw daggers, recall, re-throw for double damage.",
                "You're mana-hungry early — clear a jungle camp before roaming.",
                "Save your dash to reposition mid-combo, not just to engage."
            ),
            powerSpike = "Level 4 (ultimate) and again after Calamity Reaper."
        ),

        // ----------------------------- MARKSMEN -----------------------------
        Hero(
            name = "Beatrix",
            role = Role.MARKSMAN,
            damageType = DamageType.PHYSICAL,
            threats = setOf(Threat.SUSTAINED_DPS, Threat.BURST, Threat.POKE),
            coreBuild = listOf(
                core("Swift Boots", "Attack speed for the Renner / Bennett weapons."),
                core("Corrosion Scythe", "Attack speed plus a slow to kite divers."),
                core("Demon Hunter Sword", "Percent-HP damage and lifesteal against tanks."),
                lux("Blade of Despair", "Burst from the Wesker / Bennett single-shot weapons."),
                lux("Wind of Nature", "Immunity to physical damage to survive a dive."),
                lux("Malefic Roar", "Penetration for the late-game tanks.")
            ),
            tips = listOf(
                "Swap weapons for the situation: Renner to poke, Bennett to burst, Wesker to dash.",
                "Each weapon swap keeps you flexible — don't commit to one too long.",
                "Position at max range; you are fragile and a priority target."
            ),
            powerSpike = "Two-item spike; very strong once Corrosion Scythe is online."
        ),
        Hero(
            name = "Wanwan",
            role = Role.MARKSMAN,
            damageType = DamageType.PHYSICAL,
            threats = setOf(Threat.SUSTAINED_DPS, Threat.HIGH_MOBILITY),
            coreBuild = listOf(
                core("Swift Boots", "More attack speed to hit the four weakness points fast."),
                core("Demon Hunter Sword", "Core lifesteal + percent damage for your DPS playstyle."),
                core("Golden Staff", "Guarantees on-hit effects proc on every shot."),
                lux("Wind of Nature", "Your ult needs you alive — physical immunity buys time."),
                lux("Malefic Roar", "Penetration so your ult shreds tanks."),
                lux("Immortality", "Reset to keep ulting.")
            ),
            tips = listOf(
                "Hit all four weakness points to unlock your ultimate's true potential.",
                "You can't be locked on while purifying — bait their CC first.",
                "Your passive grants untargetability on dash; use it to dodge key skills."
            ),
            powerSpike = "Late game scaling monster once weapons + DHS are done."
        ),

        // ------------------------------ MAGES -------------------------------
        Hero(
            name = "Kagura",
            role = Role.MAGE,
            damageType = DamageType.MAGIC,
            threats = setOf(Threat.BURST, Threat.HEAVY_CC, Threat.POKE),
            coreBuild = listOf(
                core("Magic Shoes", "CDR to keep umbrella combos rolling."),
                core("Clock of Destiny", "Scaling HP and magic power for safety + damage."),
                core("Lightning Truncheon", "Extra burst proc that fits the combo windows."),
                lux("Divine Glaive", "Penetration against magic-resist stacking."),
                lux("Holy Crystal", "Raw power for one-shot combos."),
                lux("Winter Truncheon", "Frozen-active to dodge a key engage.")
            ),
            tips = listOf(
                "Master the umbrella pickup combo — separated umbrella means you keep mobility.",
                "Hold your ultimate to reposition the umbrella for a guaranteed stun.",
                "Poke safely with umbrella throws before committing the full combo."
            ),
            powerSpike = "High skill ceiling — strong from level 4 onward in skilled hands."
        ),
        Hero(
            name = "Pharsa",
            role = Role.MAGE,
            damageType = DamageType.MAGIC,
            threats = setOf(Threat.POKE, Threat.BURST),
            coreBuild = listOf(
                core("Demon Shoes", "Mana sustain so you can spam from range."),
                core("Clock of Destiny", "Power + HP to survive being focused."),
                core("Lightning Truncheon", "Adds a burst proc to your ult rain."),
                lux("Divine Glaive", "Penetration for the late game."),
                lux("Holy Crystal", "Crank up the ultimate's per-tick damage."),
                lux("Blood Wings", "Shield + power so you can channel safely.")
            ),
            tips = listOf(
                "Channel your ultimate from max range and behind your frontline.",
                "Use your bird form to reposition before or after ulting.",
                "Pre-aim the ult at choke points and objective fights."
            ),
            powerSpike = "Strong in mid-game teamfights; deadly with a frontline to peel."
        ),

        // ----------------------------- FIGHTERS -----------------------------
        Hero(
            name = "Chou",
            role = Role.FIGHTER,
            damageType = DamageType.PHYSICAL,
            threats = setOf(Threat.HEAVY_CC, Threat.HIGH_MOBILITY, Threat.BURST),
            coreBuild = listOf(
                core("Warrior Boots", "Early physical defense to brawl the lane."),
                core("Endless Battle", "True damage, lifesteal and movement speed — perfect on Chou."),
                core("War Axe", "Stacking power and CDR as you keep fighting."),
                lux("Blade of Despair", "Convert him into a one-combo assassin."),
                lux("Brute Force Breastplate", "Survivability for the front line."),
                lux("Immortality", "Dive, kick, die, revive, repeat.")
            ),
            tips = listOf(
                "First-skill dashes are immune to crowd control — dodge stuns with them.",
                "Kick priority targets out of position into your team.",
                "Cancel your third-skill kick early to combo into a flicker punch."
            ),
            powerSpike = "Strong from level 1; spikes hard with Endless Battle."
        ),
        Hero(
            name = "Paquito",
            role = Role.FIGHTER,
            damageType = DamageType.PHYSICAL,
            threats = setOf(Threat.BURST, Threat.HIGH_MOBILITY),
            coreBuild = listOf(
                core("Tough Boots", "Tenacity against the CC that locks down a brawler."),
                core("Endless Battle", "Sustain, true damage and chase speed."),
                core("Hunter Strike", "Penetration plus a movement spike for your combos."),
                lux("Blade of Despair", "Pure burst to delete the back line."),
                lux("Malefic Roar", "Shred tanks late."),
                lux("Immortality", "Revive after diving.")
            ),
            tips = listOf(
                "Land empowered skills (the glowing version) for the real damage.",
                "Use your ult's knock-up to set up your team's follow-up.",
                "Weave basic attacks between skills to keep your passive charged."
            ),
            powerSpike = "Early-to-mid game lane bully; falls off vs full-build tanks."
        ),

        // ------------------------------ TANKS -------------------------------
        Hero(
            name = "Tigreal",
            role = Role.TANK,
            damageType = DamageType.PHYSICAL,
            threats = setOf(Threat.HEAVY_CC),
            coreBuild = listOf(
                core("Tough Boots", "Tenacity so you reach the back line through their CC."),
                core("Dominance Ice", "Armor, mana, attack-speed slow aura and anti-heal."),
                core("Athena's Shield", "Magic shield against burst mages."),
                lux("Antique Cuirass", "Stacks armor and weakens attackers."),
                lux("Immortality", "Front-line revive to keep peeling."),
                lux("Guardian Helmet", "Huge HP regen for extended sieges.")
            ),
            tips = listOf(
                "Flicker + ultimate is the classic combo to pull multiple enemies in.",
                "Don't waste your ult on one target — wait for a grouped fight.",
                "Build defense reactively: armor vs physical comps, magic resist vs mages."
            ),
            powerSpike = "Useful at all stages; engage potential is there from level 1."
        ),
        Hero(
            name = "Atlas",
            role = Role.TANK,
            damageType = DamageType.MAGIC,
            threats = setOf(Threat.HEAVY_CC),
            coreBuild = listOf(
                core("Tough Boots", "Reach your ult range through their crowd control."),
                core("Glowing Wand", "A little magic damage makes your combo threatening."),
                core("Dominance Ice", "Defense + anti-heal + slow aura on a frontliner."),
                lux("Athena's Shield", "Soak magic burst as you dive."),
                lux("Immortality", "Revive after a big engage."),
                lux("Brute Force Breastplate", "Movement + defense stacking as you fight.")
            ),
            tips = listOf(
                "Land your ultimate on a clumped enemy team for a multi-hero pull.",
                "Detach from your chains (recast skill 2) to flexibly position the grab.",
                "Flicker mid-ult to extend your engage range and surprise the back line."
            ),
            powerSpike = "Mid-game engage king once he has enough defense to survive the dive."
        ),

        // ----------------------------- SUPPORTS -----------------------------
        Hero(
            name = "Estes",
            role = Role.SUPPORT,
            damageType = DamageType.MAGIC,
            threats = setOf(Threat.LIFESTEAL),
            coreBuild = listOf(
                core("Magic Shoes", "CDR so your healing chain stays up."),
                core("Enchanted Talisman", "Mana, CDR and HP to keep healing all fight."),
                core("Oracle", "Boosts shield/heal effects — pure value on Estes."),
                lux("Immortality", "Stay alive so the team keeps its healer."),
                lux("Holy Crystal", "Surprising poke if you go a damage line."),
                lux("Necklace of Durance", "If you ever need to fight an enemy healer.")
            ),
            tips = listOf(
                "Keep your link on a low-HP ally to ramp the heal-over-time, then ult.",
                "Your ultimate is a massive team heal — save it for the decisive fight.",
                "You are immobile and a focus magnet; ward and stay behind the frontline."
            ),
            powerSpike = "Scales with magic power; strong sustained-fight support throughout."
        ),
        Hero(
            name = "Mathilda",
            role = Role.SUPPORT,
            damageType = DamageType.MAGIC,
            threats = setOf(Threat.HIGH_MOBILITY),
            coreBuild = listOf(
                core("Magic Shoes", "CDR for constant roams and engages."),
                core("Enchanted Talisman", "CDR + mana to keep skills flowing."),
                core("Dominance Ice", "Defense + anti-heal aura while you peel."),
                lux("Oracle", "Boosts your shield passive."),
                lux("Immortality", "Insurance for aggressive roams."),
                lux("Necklace of Durance", "Anti-heal vs sustain comps.")
            ),
            tips = listOf(
                "Use your ult to carry an ally into or out of a fight.",
                "Your shield passive procs after skills — weave them to stay tanky.",
                "Roam constantly; you provide vision, engage and escape for the carry."
            ),
            powerSpike = "Roaming threat from early game; great gank setup support."
        )
    )

    /** All hero names, sorted, for the picker UI. */
    val heroNames: List<String> by lazy { heroes.map { it.name }.sorted() }

    fun byName(name: String): Hero? = heroes.firstOrNull { it.name.equals(name, ignoreCase = true) }
}
