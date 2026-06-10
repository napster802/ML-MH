package com.mlbb.advisor.data

/**
 * Hand-authored knowledge base of heroes, builds, emblems, spells and tips.
 *
 * Coverage focuses on the meta-relevant and most-played heroes across every
 * role. The advisor logic in [BuildAdvisor] works off these fields
 * generically, so adding a hero needs no code changes — just append a
 * [Hero] entry below.
 *
 * Builds reflect common, sensible itemization paths; tune to taste. Nothing
 * here is read from the game — the player supplies the picks.
 */
object HeroRepository {

    // --- Shorthand helpers for readability -------------------------------
    private fun core(name: String, reason: String) = ItemAdvice(name, reason, Priority.CORE)
    private fun lux(name: String, reason: String) = ItemAdvice(name, reason, Priority.LUXURY)

    val heroes: List<Hero> = listOf(

        // ============================ ASSASSINS ============================
        Hero(
            name = "Lancelot", role = Role.ASSASSIN, damageType = DamageType.PHYSICAL,
            threats = setOf(Threat.BURST, Threat.HIGH_MOBILITY),
            coreBuild = listOf(
                core("Swift Boots", "Attack speed to weave basics between dashes."),
                core("Endless Battle", "True damage after a skill — your bread and butter."),
                core("Blade of Despair", "Flat attack damage to one-combo squishies."),
                lux("Hunter Strike", "Penetration plus a move-speed spike for chasing."),
                lux("Malefic Roar", "Shreds armor once enemies stack defense.")
            ),
            tips = listOf(
                "Dashes grant brief immunity — time them to dodge skillshots and stuns.",
                "Hit the sweet-spot tip of your second skill for the bonus damage zone."
            ),
            powerSpike = "Mid game after Endless Battle — pick off isolated targets.",
            emblem = "Assassin", emblemTalents = "Lethal Ignition · adaptive attack + pen",
            battleSpell = "Retribution (jungle)", battleSpellAlt = "Flicker"
        ),
        Hero(
            name = "Gusion", role = Role.ASSASSIN, damageType = DamageType.MAGIC,
            threats = setOf(Threat.BURST, Threat.HIGH_MOBILITY),
            coreBuild = listOf(
                core("Magic Shoes", "Cooldown reduction so your dagger combo resets faster."),
                core("Calamity Reaper", "Mana, CDR and a true-damage proc after casting."),
                core("Glowing Wand", "Burn damage that scales with your burst."),
                lux("Divine Glaive", "Magic penetration once enemies buy resist."),
                lux("Holy Crystal", "Top-end magic power for one-shot combos.")
            ),
            tips = listOf(
                "Practice the recall-dagger combo: throw, recall, re-throw for double damage.",
                "Clear a jungle camp before roaming — you are mana-hungry early."
            ),
            powerSpike = "Level 4 (ultimate) and again after Calamity Reaper.",
            emblem = "Assassin", emblemTalents = "Lethal Ignition · magic pen focus",
            battleSpell = "Retribution (jungle)", battleSpellAlt = "Flicker"
        ),
        Hero(
            name = "Hayabusa", role = Role.ASSASSIN, damageType = DamageType.PHYSICAL,
            threats = setOf(Threat.BURST, Threat.HIGH_MOBILITY),
            coreBuild = listOf(
                core("Warrior/Swift Boots", "Defense or attack speed depending on enemy threat."),
                core("Endless Battle", "Sustain, true damage and chase speed."),
                core("Blade of Despair", "Burst to delete the back line under your ult."),
                lux("Malefic Roar", "Penetration for diving tanky cores."),
                lux("Immortality", "Revive after diving the back line.")
            ),
            tips = listOf(
                "Your ultimate makes you untargetable between shadow strikes — dodge CC with it.",
                "Plant shadows around objectives to teleport for ganks and escapes."
            ),
            powerSpike = "Strong from level 4; snowballs hard once Endless Battle is done.",
            emblem = "Assassin", emblemTalents = "Lethal Ignition · attack + pen",
            battleSpell = "Retribution (jungle)", battleSpellAlt = "Flicker"
        ),
        Hero(
            name = "Ling", role = Role.ASSASSIN, damageType = DamageType.PHYSICAL,
            threats = setOf(Threat.BURST, Threat.HIGH_MOBILITY),
            coreBuild = listOf(
                core("Swift Boots", "Attack speed to maximize crit uptime on walls."),
                core("Berserker's Fury", "Crit + crit damage — your core scaling."),
                core("Blade of Despair", "Pairs with crit for one-shot leaps."),
                lux("Malefic Roar", "Penetration vs defensive cores."),
                lux("Immortality", "Insurance when you dive deep.")
            ),
            tips = listOf(
                "Stay on walls to regen energy and stay untargetable between leaps.",
                "Bait key CC before committing your ult dive."
            ),
            powerSpike = "Two-item crit spike; a late-game one-shot threat.",
            emblem = "Assassin", emblemTalents = "Weapons Master · crit scaling",
            battleSpell = "Retribution (jungle)", battleSpellAlt = "Flicker"
        ),
        Hero(
            name = "Fanny", role = Role.ASSASSIN, damageType = DamageType.PHYSICAL,
            threats = setOf(Threat.BURST, Threat.HIGH_MOBILITY),
            coreBuild = listOf(
                core("Warrior/Tough Boots", "Survive a single CC mid-cable run."),
                core("Endless Battle", "True damage + sustain on your high-frequency hits."),
                core("Blade of Despair", "Flat damage to delete squishies on the swoop."),
                lux("Hunter Strike", "Penetration and movement for chains."),
                lux("Malefic Roar", "Shred tanks late.")
            ),
            tips = listOf(
                "Manage energy: don't double-cable when one will reach the target.",
                "Purify or Tough Boots counters the CC that ends your runs."
            ),
            powerSpike = "Skill-dependent; lethal from level 4 in practiced hands.",
            emblem = "Assassin", emblemTalents = "Lethal Ignition · attack + pen",
            battleSpell = "Purify", battleSpellAlt = "Retribution (jungle)"
        ),
        Hero(
            name = "Natalia", role = Role.ASSASSIN, damageType = DamageType.PHYSICAL,
            threats = setOf(Threat.BURST, Threat.HIGH_MOBILITY),
            coreBuild = listOf(
                core("Swift Boots", "Attack speed for the enhanced basic combo."),
                core("Endless Battle", "True damage and sustain on your first hit."),
                core("Blade of Despair", "Burst from stealth on isolated targets."),
                lux("Hunter Strike", "Penetration + mobility."),
                lux("Immortality", "Survive a failed pick.")
            ),
            tips = listOf(
                "Break line of sight to enter stealth and silence your target on the opener.",
                "Pick off lone supports and marksmen; avoid grouped fights."
            ),
            powerSpike = "Strong picker mid game; great vs split-pushing enemies.",
            emblem = "Assassin", emblemTalents = "Lethal Ignition · burst focus",
            battleSpell = "Retribution (jungle)", battleSpellAlt = "Flicker"
        ),
        Hero(
            name = "Benedetta", role = Role.ASSASSIN, damageType = DamageType.PHYSICAL,
            threats = setOf(Threat.BURST, Threat.HIGH_MOBILITY),
            coreBuild = listOf(
                core("Warrior Boots", "Early defense to brawl while you ramp."),
                core("Endless Battle", "True damage and sustain fit her basics perfectly."),
                core("Blade of Despair", "Flat damage spike on the dash combo."),
                lux("Queen's Wings", "Lifeline + lifesteal spike when low."),
                lux("Malefic Roar", "Penetration for the late game.")
            ),
            tips = listOf(
                "Hold the parry (skill 2) to block a key skill, then dash through enemies.",
                "Her ult shield + sword trail lets you engage and disengage safely."
            ),
            powerSpike = "Scales into a durable late-game duelist.",
            emblem = "Assassin", emblemTalents = "Killing Spree · sustain on kills",
            battleSpell = "Flicker", battleSpellAlt = "Retribution (jungle)"
        ),
        Hero(
            name = "Saber", role = Role.ASSASSIN, damageType = DamageType.PHYSICAL,
            threats = setOf(Threat.BURST, Threat.HIGH_MOBILITY),
            coreBuild = listOf(
                core("Swift Boots", "Attack speed to land more enhanced basics."),
                core("Blade of Despair", "Flat damage so your ult deletes a carry."),
                core("Hunter Strike", "Penetration and chase mobility."),
                lux("Malefic Roar", "More penetration vs defense stackers."),
                lux("Immortality", "Trade your life for their carry's.")
            ),
            tips = listOf(
                "Ult locks a single target — use it to isolate and kill the enemy carry.",
                "Flicker mid-ult to extend lock range and surprise the back line."
            ),
            powerSpike = "Sharp single-target pick threat once Blade of Despair is online.",
            emblem = "Assassin", emblemTalents = "Lethal Ignition · burst focus",
            battleSpell = "Retribution (jungle)", battleSpellAlt = "Flicker"
        ),
        Hero(
            name = "Karina", role = Role.ASSASSIN, damageType = DamageType.MAGIC,
            threats = setOf(Threat.BURST, Threat.HIGH_MOBILITY),
            coreBuild = listOf(
                core("Magic Shoes", "CDR so your ult resets keep coming."),
                core("Calamity Reaper", "Mana, CDR and true-damage proc."),
                core("Glowing Wand", "Magic power + burn for the combo."),
                lux("Divine Glaive", "Penetration vs magic resist."),
                lux("Immortality", "Reset to keep ult-chaining.")
            ),
            tips = listOf(
                "Her ult refreshes on a kill/assist — chain through fights for resets.",
                "True damage on her ult ignores defense; hunt squishies first."
            ),
            powerSpike = "Snowballs hard — one kill can cascade through the whole team.",
            emblem = "Assassin", emblemTalents = "Lethal Ignition · magic burst",
            battleSpell = "Retribution (jungle)", battleSpellAlt = "Flicker"
        ),
        Hero(
            name = "Aamon", role = Role.ASSASSIN, damageType = DamageType.MAGIC,
            threats = setOf(Threat.BURST, Threat.HIGH_MOBILITY),
            coreBuild = listOf(
                core("Magic Shoes", "CDR so shards return faster."),
                core("Calamity Reaper", "Mana + true damage proc on your bursts."),
                core("Glowing Wand", "Burn + power for the shard combo."),
                lux("Divine Glaive", "Penetration vs resist."),
                lux("Immortality", "Survive a deep pick.")
            ),
            tips = listOf(
                "You go invisible after casting — reposition before the shards return.",
                "Pick isolated targets; you are fragile in extended fights."
            ),
            powerSpike = "Strong picker once Calamity Reaper is online.",
            emblem = "Assassin", emblemTalents = "Lethal Ignition · magic burst",
            battleSpell = "Retribution (jungle)", battleSpellAlt = "Flicker"
        ),

        // ============================ MARKSMEN =============================
        Hero(
            name = "Beatrix", role = Role.MARKSMAN, damageType = DamageType.PHYSICAL,
            threats = setOf(Threat.SUSTAINED_DPS, Threat.BURST, Threat.POKE),
            coreBuild = listOf(
                core("Swift Boots", "Attack speed for the Renner / Bennett weapons."),
                core("Corrosion Scythe", "Attack speed plus a slow to kite divers."),
                core("Demon Hunter Sword", "Percent-HP damage and lifesteal vs tanks."),
                lux("Blade of Despair", "Burst from single-shot weapons."),
                lux("Wind of Nature", "Physical immunity to survive a dive.")
            ),
            tips = listOf(
                "Swap weapons for the job: Renner poke, Bennett burst, Wesker dash, Nibiru wave.",
                "Position at max range; you are fragile and a priority target."
            ),
            powerSpike = "Two-item spike; very strong once Corrosion Scythe is online.",
            emblem = "Marksman", emblemTalents = "Weapons Master · attack speed + crit",
            battleSpell = "Flicker", battleSpellAlt = "Purify"
        ),
        Hero(
            name = "Wanwan", role = Role.MARKSMAN, damageType = DamageType.PHYSICAL,
            threats = setOf(Threat.SUSTAINED_DPS, Threat.HIGH_MOBILITY),
            coreBuild = listOf(
                core("Swift Boots", "Attack speed to hit the four weakness points fast."),
                core("Demon Hunter Sword", "Core lifesteal + percent damage."),
                core("Golden Staff", "Guarantees on-hit effects proc every shot."),
                lux("Wind of Nature", "Stay alive to keep ulting."),
                lux("Malefic Roar", "Penetration so your ult shreds tanks.")
            ),
            tips = listOf(
                "Hit all four weakness points to unlock your ultimate.",
                "You can't be locked on while purifying — bait their CC first."
            ),
            powerSpike = "Late-game scaling monster once weapons + DHS are done.",
            emblem = "Marksman", emblemTalents = "Weapons Master · attack speed",
            battleSpell = "Purify", battleSpellAlt = "Flicker"
        ),
        Hero(
            name = "Layla", role = Role.MARKSMAN, damageType = DamageType.PHYSICAL,
            threats = setOf(Threat.SUSTAINED_DPS, Threat.POKE),
            coreBuild = listOf(
                core("Swift Boots", "Attack speed for steady DPS."),
                core("Berserker's Fury", "Crit + crit damage core."),
                core("Scarlet Phantom", "Attack speed + crit that refreshes on crit."),
                lux("Wind of Nature", "Survive divers from max range."),
                lux("Blade of Despair", "Late-game crit nuke.")
            ),
            tips = listOf(
                "Your damage and range grow with distance — stay at the very back.",
                "Beginner-friendly: focus on positioning over fancy plays."
            ),
            powerSpike = "Late-game hyper-carry with the longest basic range.",
            emblem = "Marksman", emblemTalents = "Weapons Master · crit",
            battleSpell = "Flicker", battleSpellAlt = "Sprint"
        ),
        Hero(
            name = "Granger", role = Role.MARKSMAN, damageType = DamageType.PHYSICAL,
            threats = setOf(Threat.BURST, Threat.POKE),
            coreBuild = listOf(
                core("Swift Boots", "Reload speed feel + mobility."),
                core("Berserker's Fury", "Crit scales his six-shot burst hugely."),
                core("Blade of Despair", "Massive per-bullet damage."),
                lux("Malefic Roar", "Penetration vs tanks."),
                lux("Wind of Nature", "Survive a dive to keep firing.")
            ),
            tips = listOf(
                "Manage your six-bullet magazine — the last shot crits naturally.",
                "Use your ult cannon for long-range burst and to dash-reposition."
            ),
            powerSpike = "Strong two-item burst spike in the mid game.",
            emblem = "Marksman", emblemTalents = "Weapons Master · crit + adaptive",
            battleSpell = "Flicker", battleSpellAlt = "Purify"
        ),
        Hero(
            name = "Claude", role = Role.MARKSMAN, damageType = DamageType.PHYSICAL,
            threats = setOf(Threat.SUSTAINED_DPS),
            coreBuild = listOf(
                core("Swift Boots", "Attack speed amplifies the Dexterity passive."),
                core("Corrosion Scythe", "Attack speed + slow to kite while you ramp."),
                core("Demon Hunter Sword", "Percent damage + lifesteal core."),
                lux("Golden Staff", "Caps attack speed for on-hit shredding."),
                lux("Wind of Nature", "Survive the dive.")
            ),
            tips = listOf(
                "Your ult mirrors your basics — fire it in a grouped fight for double DPS.",
                "Use Dexterity stacks: keep attacking to ramp move + attack speed."
            ),
            powerSpike = "Scales into a late-game AoE shredder.",
            emblem = "Marksman", emblemTalents = "Weapons Master · attack speed",
            battleSpell = "Flicker", battleSpellAlt = "Sprint"
        ),
        Hero(
            name = "Brody", role = Role.MARKSMAN, damageType = DamageType.PHYSICAL,
            threats = setOf(Threat.BURST, Threat.POKE),
            coreBuild = listOf(
                core("Tough/Warrior Boots", "Survivability since you fight at mid range."),
                core("Demon Hunter Sword", "Percent damage scales his heavy hits."),
                core("Blade of Despair", "Big per-stack basic damage."),
                lux("Malefic Roar", "Penetration vs tanks."),
                lux("Bloodlust Axe", "Spell vamp sustain in fights.")
            ),
            tips = listOf(
                "Each basic stacks marks; your ult detonates them for burst — set up first.",
                "Use your dash to reposition between slow, heavy shots."
            ),
            powerSpike = "Item-light: dangerous even at one or two items.",
            emblem = "Marksman", emblemTalents = "Weapons Master · adaptive + pen",
            battleSpell = "Flicker", battleSpellAlt = "Retribution (jungle)"
        ),
        Hero(
            name = "Bruno", role = Role.MARKSMAN, damageType = DamageType.PHYSICAL,
            threats = setOf(Threat.SUSTAINED_DPS, Threat.POKE),
            coreBuild = listOf(
                core("Swift Boots", "Attack speed builds crit-chance passive faster."),
                core("Berserker's Fury", "Crit synergizes with his passive crit stacks."),
                core("Scarlet Phantom", "Attack speed + crit refresh."),
                lux("Blade of Despair", "Late crit nuke."),
                lux("Wind of Nature", "Survive a dive.")
            ),
            tips = listOf(
                "Hitting enemies builds crit chance — keep attacking to ramp it.",
                "Bounce your energy wave off walls to poke and slow before fights."
            ),
            powerSpike = "Strong sustained DPS once attack speed + crit come online.",
            emblem = "Marksman", emblemTalents = "Weapons Master · crit",
            battleSpell = "Flicker", battleSpellAlt = "Inspire"
        ),
        Hero(
            name = "Karrie", role = Role.MARKSMAN, damageType = DamageType.PHYSICAL,
            threats = setOf(Threat.SUSTAINED_DPS, Threat.TANKY),
            coreBuild = listOf(
                core("Swift Boots", "Attack speed to fire more Lightwheel hits."),
                core("Demon Hunter Sword", "Attack speed + percent damage."),
                core("Corrosion Scythe", "Attack speed + slow to kite."),
                lux("Golden Staff", "On-hit reliability for true-damage stacks."),
                lux("Wind of Nature", "Survive the dive.")
            ),
            tips = listOf(
                "Your passive deals true damage based on max HP — you melt tanks fast.",
                "Use your ult's bouncing wheels to dodge skills while DPSing."
            ),
            powerSpike = "Anti-tank specialist; strong from two items onward.",
            emblem = "Marksman", emblemTalents = "Weapons Master · attack speed",
            battleSpell = "Flicker", battleSpellAlt = "Purify"
        ),
        Hero(
            name = "Melissa", role = Role.MARKSMAN, damageType = DamageType.PHYSICAL,
            threats = setOf(Threat.SUSTAINED_DPS),
            coreBuild = listOf(
                core("Swift Boots", "Attack speed for Eyes-on-You stacking."),
                core("Berserker's Fury", "Crit core."),
                core("Demon Hunter Sword", "Percent damage + sustain."),
                lux("Corrosion Scythe", "Slow + attack speed."),
                lux("Wind of Nature", "Survive bursts.")
            ),
            tips = listOf(
                "Your ult zone blocks enemy dashes/summons from reaching you — drop it when dived.",
                "Muddles ignore minions and lock onto the nearest hero."
            ),
            powerSpike = "Anti-dive carry; very strong vs assassin-heavy teams.",
            emblem = "Marksman", emblemTalents = "Weapons Master · attack speed + crit",
            battleSpell = "Flicker", battleSpellAlt = "Sprint"
        ),
        Hero(
            name = "Moskov", role = Role.MARKSMAN, damageType = DamageType.PHYSICAL,
            threats = setOf(Threat.SUSTAINED_DPS),
            coreBuild = listOf(
                core("Swift Boots", "Attack speed = extra-attack passive procs."),
                core("Demon Hunter Sword", "Attack speed + percent damage."),
                core("Berserker's Fury", "Crit for spear burst."),
                lux("Corrosion Scythe", "Attack speed + slow to kite."),
                lux("Wind of Nature", "Survive divers.")
            ),
            tips = listOf(
                "Your spears pierce — line up enemies behind your target for extra hits.",
                "Use skill 2 to knock back and stun targets against walls."
            ),
            powerSpike = "Fast-attacking late-game shredder.",
            emblem = "Marksman", emblemTalents = "Weapons Master · attack speed",
            battleSpell = "Flicker", battleSpellAlt = "Purify"
        ),
        Hero(
            name = "Clint", role = Role.MARKSMAN, damageType = DamageType.PHYSICAL,
            threats = setOf(Threat.BURST, Threat.POKE),
            coreBuild = listOf(
                core("Swift Boots", "Mobility for the enhanced basic after skills."),
                core("Berserker's Fury", "Crit for the heavy enhanced shots."),
                core("Blade of Despair", "Per-shot burst."),
                lux("Malefic Roar", "Penetration vs tanks."),
                lux("Wind of Nature", "Survive a dive.")
            ),
            tips = listOf(
                "Weave a basic after every skill to trigger the empowered shot.",
                "Strong laning bully — abuse your early all-in."
            ),
            powerSpike = "Early-to-mid lane bully with strong burst.",
            emblem = "Marksman", emblemTalents = "Weapons Master · adaptive + crit",
            battleSpell = "Flicker", battleSpellAlt = "Sprint"
        ),
        Hero(
            name = "Lesley", role = Role.MARKSMAN, damageType = DamageType.PHYSICAL,
            threats = setOf(Threat.BURST, Threat.POKE),
            coreBuild = listOf(
                core("Swift Boots", "Mobility to maintain sniping distance."),
                core("Berserker's Fury", "Crit core; her passive shots crit."),
                core("Blade of Despair", "Huge per-shot burst."),
                lux("Malefic Roar", "Penetration vs tanks."),
                lux("Wind of Nature", "Survive divers.")
            ),
            tips = listOf(
                "Stay out of vision to charge passive energy for empowered crit shots.",
                "Use ult to snipe low targets and reveal/burst from max range."
            ),
            powerSpike = "Long-range burst sniper; strong two-item spike.",
            emblem = "Marksman", emblemTalents = "Weapons Master · crit",
            battleSpell = "Flicker", battleSpellAlt = "Inspire"
        ),
        Hero(
            name = "Irithel", role = Role.MARKSMAN, damageType = DamageType.PHYSICAL,
            threats = setOf(Threat.SUSTAINED_DPS),
            coreBuild = listOf(
                core("Swift Boots", "Attack speed for the split-arrow passive."),
                core("Demon Hunter Sword", "Attack speed + percent damage."),
                core("Berserker's Fury", "Crit for the multi-arrow burst."),
                lux("Corrosion Scythe", "Slow + attack speed to kite."),
                lux("Wind of Nature", "Survive a dive.")
            ),
            tips = listOf(
                "You can move while basic-attacking after a skill — kite freely.",
                "Each basic fires arrows that crit; stack attack speed and crit."
            ),
            powerSpike = "Mobile late-game kiting carry.",
            emblem = "Marksman", emblemTalents = "Weapons Master · attack speed + crit",
            battleSpell = "Flicker", battleSpellAlt = "Sprint"
        ),
        Hero(
            name = "Natan", role = Role.MARKSMAN, damageType = DamageType.MAGIC,
            threats = setOf(Threat.SUSTAINED_DPS, Threat.POKE),
            coreBuild = listOf(
                core("Demon Shoes", "Mana sustain for constant skill use."),
                core("Holy Crystal", "Magic power scales his hybrid damage."),
                core("Lightning Truncheon", "Burst proc fits his combo."),
                lux("Divine Glaive", "Magic penetration vs resist."),
                lux("Wind of Nature", "Survive divers.")
            ),
            tips = listOf(
                "Your basics deal magic damage — build like a hybrid mage-marksman.",
                "Your ult spawns an anti-clone that mirrors and amplifies your damage."
            ),
            powerSpike = "Scales into a strong magic-damage hyper-carry.",
            emblem = "Marksman", emblemTalents = "Magic Worship · magic power",
            battleSpell = "Flicker", battleSpellAlt = "Sprint"
        ),

        // ============================== MAGES ==============================
        Hero(
            name = "Kagura", role = Role.MAGE, damageType = DamageType.MAGIC,
            threats = setOf(Threat.BURST, Threat.HEAVY_CC, Threat.POKE),
            coreBuild = listOf(
                core("Magic Shoes", "CDR to keep umbrella combos rolling."),
                core("Clock of Destiny", "Scaling HP and magic power for safety + damage."),
                core("Lightning Truncheon", "Extra burst proc that fits the combo windows."),
                lux("Divine Glaive", "Penetration against magic-resist stacking."),
                lux("Holy Crystal", "Raw power for one-shot combos.")
            ),
            tips = listOf(
                "Master the umbrella pickup combo to keep mobility and burst.",
                "Hold your ultimate to reposition the umbrella for a guaranteed stun."
            ),
            powerSpike = "High skill ceiling — strong from level 4 in skilled hands.",
            emblem = "Mage", emblemTalents = "Impure Rage · mana + true damage",
            battleSpell = "Flicker", battleSpellAlt = "Purify"
        ),
        Hero(
            name = "Pharsa", role = Role.MAGE, damageType = DamageType.MAGIC,
            threats = setOf(Threat.POKE, Threat.BURST),
            coreBuild = listOf(
                core("Demon Shoes", "Mana sustain so you can spam from range."),
                core("Clock of Destiny", "Power + HP to survive being focused."),
                core("Lightning Truncheon", "Adds a burst proc to your ult rain."),
                lux("Divine Glaive", "Penetration for the late game."),
                lux("Blood Wings", "Shield + power so you can channel safely.")
            ),
            tips = listOf(
                "Channel your ultimate from max range and behind your frontline.",
                "Use bird form to reposition before or after ulting."
            ),
            powerSpike = "Strong mid-game teamfighter with a frontline to peel.",
            emblem = "Mage", emblemTalents = "Magic Worship · power + cooldown",
            battleSpell = "Flameshot", battleSpellAlt = "Flicker"
        ),
        Hero(
            name = "Eudora", role = Role.MAGE, damageType = DamageType.MAGIC,
            threats = setOf(Threat.BURST),
            coreBuild = listOf(
                core("Magic Shoes", "CDR so your combo comes back faster."),
                core("Lightning Truncheon", "Burst proc that pairs with your stun combo."),
                core("Genius Wand", "Magic resist shred to amplify burst."),
                lux("Divine Glaive", "Penetration vs resist."),
                lux("Holy Crystal", "Max power to one-shot.")
            ),
            tips = listOf(
                "Pre-stack passive with a skill, then stun + ult to one-combo a squishy.",
                "You are immobile — flicker in, burst, and rely on your team to peel."
            ),
            powerSpike = "Sharp single-combo burst from level 4 onward.",
            emblem = "Mage", emblemTalents = "Impure Rage · burst focus",
            battleSpell = "Flicker", battleSpellAlt = "Petrify"
        ),
        Hero(
            name = "Aurora", role = Role.MAGE, damageType = DamageType.MAGIC,
            threats = setOf(Threat.BURST, Threat.HEAVY_CC),
            coreBuild = listOf(
                core("Magic Shoes", "CDR for frequent freeze combos."),
                core("Lightning Truncheon", "Burst proc on your frozen targets."),
                core("Ice Queen Wand", "Slow + spell vamp to kite and sustain."),
                lux("Divine Glaive", "Penetration vs resist."),
                lux("Holy Crystal", "Max burst on the freeze combo.")
            ),
            tips = listOf(
                "Land your ult to freeze, then dump your combo for guaranteed burst.",
                "Your passive ult-on-stacks freezes a whole group — wait for the clump."
            ),
            powerSpike = "Reliable AoE freeze-and-burst from the mid game.",
            emblem = "Mage", emblemTalents = "Impure Rage · burst focus",
            battleSpell = "Flicker", battleSpellAlt = "Flameshot"
        ),
        Hero(
            name = "Harith", role = Role.MAGE, damageType = DamageType.MAGIC,
            threats = setOf(Threat.HIGH_MOBILITY, Threat.SUSTAINED_DPS),
            coreBuild = listOf(
                core("Magic Shoes", "CDR so dashes reset and you spam basics."),
                core("Calamity Reaper", "Mana + true-damage proc after skills."),
                core("Concentrated Energy", "Magic lifesteal + HP to brawl."),
                lux("Divine Glaive", "Penetration vs resist."),
                lux("Holy Crystal", "Crank his sustained damage.")
            ),
            tips = listOf(
                "Dash through enemies to reduce cooldowns — weave basics between dashes.",
                "Your ult boosts dash uptime and shields you; fight in melee range."
            ),
            powerSpike = "Strong sustained-damage duelist once Calamity Reaper is done.",
            emblem = "Mage", emblemTalents = "Magic Worship · sustained power",
            battleSpell = "Flicker", battleSpellAlt = "Sprint"
        ),
        Hero(
            name = "Valir", role = Role.MAGE, damageType = DamageType.MAGIC,
            threats = setOf(Threat.POKE, Threat.HEAVY_CC),
            coreBuild = listOf(
                core("Magic Shoes", "CDR to chain fireballs and knockbacks."),
                core("Clock of Destiny", "Power + HP for a durable poke mage."),
                core("Ice Queen Wand", "Slow + spell vamp to kite."),
                lux("Divine Glaive", "Penetration vs resist."),
                lux("Winter Truncheon", "Freeze active to escape divers.")
            ),
            tips = listOf(
                "Chain skill 1 to keep your passive burn ramping for big poke.",
                "Skill 2 knockback purges your own CC and peels divers off you."
            ),
            powerSpike = "Safe poke and anti-dive mage all game.",
            emblem = "Mage", emblemTalents = "Magic Worship · sustained burn",
            battleSpell = "Flameshot", battleSpellAlt = "Flicker"
        ),
        Hero(
            name = "Lunox", role = Role.MAGE, damageType = DamageType.MAGIC,
            threats = setOf(Threat.BURST, Threat.SUSTAINED_DPS),
            coreBuild = listOf(
                core("Magic Shoes", "CDR for fluid Order/Chaos swaps."),
                core("Clock of Destiny", "Power + HP scaling."),
                core("Lightning Truncheon", "Burst proc for the Chaos side."),
                lux("Divine Glaive", "Penetration vs resist."),
                lux("Holy Crystal", "Max burst.")
            ),
            tips = listOf(
                "Chaos side bursts; Order side gives sustained damage + an invuln dash — swap fluidly.",
                "Use the Order ult's brief invulnerability to dodge key skills."
            ),
            powerSpike = "Flexible burst-or-sustain mage; strong from mid game.",
            emblem = "Mage", emblemTalents = "Impure Rage · burst focus",
            battleSpell = "Flicker", battleSpellAlt = "Purify"
        ),
        Hero(
            name = "Cecilion", role = Role.MAGE, damageType = DamageType.MAGIC,
            threats = setOf(Threat.POKE, Threat.SUSTAINED_DPS),
            coreBuild = listOf(
                core("Demon Shoes", "Mana to fuel constant batsong stacking."),
                core("Clock of Destiny", "Power + HP while you scale."),
                core("Lightning Truncheon", "Burst proc on stacked power."),
                lux("Divine Glaive", "Penetration vs resist."),
                lux("Blood Wings", "Shield + huge power late.")
            ),
            tips = listOf(
                "Stack magic power with every bat hit — farm and poke relentlessly early.",
                "Your ult is a long-range AoE; cast it across the whole enemy team."
            ),
            powerSpike = "Infinite-scaling late-game monster once stacked.",
            emblem = "Mage", emblemTalents = "Magic Worship · scaling power",
            battleSpell = "Flameshot", battleSpellAlt = "Flicker"
        ),
        Hero(
            name = "Xavier", role = Role.MAGE, damageType = DamageType.MAGIC,
            threats = setOf(Threat.POKE, Threat.BURST),
            coreBuild = listOf(
                core("Demon Shoes", "Mana for constant poke."),
                core("Clock of Destiny", "Power + HP scaling."),
                core("Lightning Truncheon", "Burst proc."),
                lux("Divine Glaive", "Penetration vs resist."),
                lux("Holy Crystal", "Max power for the global ult.")
            ),
            tips = listOf(
                "Stack Transcendence (skill hits) to enter empowered state for free CDR + range.",
                "Your ult is map-wide — use it to snipe and zone whole teamfights."
            ),
            powerSpike = "Strong poke and global pressure from the mid game.",
            emblem = "Mage", emblemTalents = "Impure Rage · mana + true damage",
            battleSpell = "Flameshot", battleSpellAlt = "Flicker"
        ),
        Hero(
            name = "Yve", role = Role.MAGE, damageType = DamageType.MAGIC,
            threats = setOf(Threat.POKE, Threat.HEAVY_CC),
            coreBuild = listOf(
                core("Demon Shoes", "Mana for sustained casting."),
                core("Clock of Destiny", "Power + HP scaling."),
                core("Lightning Truncheon", "Burst proc."),
                lux("Divine Glaive", "Penetration vs resist."),
                lux("Holy Crystal", "Max ult damage.")
            ),
            tips = listOf(
                "Your ult creates a control field — pre-aim the grid over the enemy team.",
                "Poke safely with skill 1 from outside their range before committing."
            ),
            powerSpike = "Dominant zone-control mage in the mid-to-late game.",
            emblem = "Mage", emblemTalents = "Magic Worship · sustained power",
            battleSpell = "Flameshot", battleSpellAlt = "Flicker"
        ),
        Hero(
            name = "Lylia", role = Role.MAGE, damageType = DamageType.MAGIC,
            threats = setOf(Threat.BURST, Threat.HIGH_MOBILITY),
            coreBuild = listOf(
                core("Magic Shoes", "CDR for combo and dash uptime."),
                core("Clock of Destiny", "Power + HP scaling."),
                core("Lightning Truncheon", "Burst proc."),
                lux("Divine Glaive", "Penetration vs resist."),
                lux("Holy Crystal", "Max burst.")
            ),
            tips = listOf(
                "Drop your shadow then teleport back to it to dodge skills mid-combo.",
                "Your passive deals extra damage at low HP — bait dives then burst."
            ),
            powerSpike = "Slippery burst mage; strong from the mid game.",
            emblem = "Mage", emblemTalents = "Impure Rage · burst focus",
            battleSpell = "Flicker", battleSpellAlt = "Flameshot"
        ),
        Hero(
            name = "Chang'e", role = Role.MAGE, damageType = DamageType.MAGIC,
            threats = setOf(Threat.POKE, Threat.SUSTAINED_DPS),
            coreBuild = listOf(
                core("Demon Shoes", "Mana for constant beam uptime."),
                core("Clock of Destiny", "Power + HP scaling."),
                core("Lightning Truncheon", "Burst proc."),
                lux("Divine Glaive", "Penetration vs resist."),
                lux("Holy Crystal", "Max sustained damage.")
            ),
            tips = listOf(
                "Hold your ult beam on the enemy backline; you can move freely while channeling.",
                "Passive grants move speed + extra damage per basic — weave them in."
            ),
            powerSpike = "Strong sustained-DPS mage from the mid game.",
            emblem = "Mage", emblemTalents = "Magic Worship · sustained power",
            battleSpell = "Flameshot", battleSpellAlt = "Flicker"
        ),
        Hero(
            name = "Vale", role = Role.MAGE, damageType = DamageType.MAGIC,
            threats = setOf(Threat.BURST, Threat.HEAVY_CC),
            coreBuild = listOf(
                core("Magic Shoes", "CDR for the burst combo."),
                core("Lightning Truncheon", "Burst proc on the combo."),
                core("Clock of Destiny", "Power + HP scaling."),
                lux("Divine Glaive", "Penetration vs resist."),
                lux("Holy Crystal", "Max one-shot burst.")
            ),
            tips = listOf(
                "Your ult's second cast is a huge AoE knock-up — land it on a clump.",
                "Combo skill 1 into ult for guaranteed burst on a caught target."
            ),
            powerSpike = "AoE burst-and-CC mage; strong in grouped fights.",
            emblem = "Mage", emblemTalents = "Impure Rage · burst focus",
            battleSpell = "Flicker", battleSpellAlt = "Flameshot"
        ),
        Hero(
            name = "Cyclops", role = Role.MAGE, damageType = DamageType.MAGIC,
            threats = setOf(Threat.BURST),
            coreBuild = listOf(
                core("Magic Shoes", "CDR — his passive also lowers cooldowns."),
                core("Lightning Truncheon", "Burst proc fits his combo."),
                core("Clock of Destiny", "Power + HP scaling."),
                lux("Divine Glaive", "Penetration vs resist."),
                lux("Holy Crystal", "Max burst.")
            ),
            tips = listOf(
                "Land your ult's star to lock a target, then combo for guaranteed burst.",
                "Skill 2 spheres home in — kite while they orbit and chip enemies."
            ),
            powerSpike = "Reliable single-target burst from the mid game.",
            emblem = "Mage", emblemTalents = "Impure Rage · burst focus",
            battleSpell = "Flicker", battleSpellAlt = "Flameshot"
        ),
        Hero(
            name = "Luo Yi", role = Role.MAGE, damageType = DamageType.MAGIC,
            threats = setOf(Threat.HEAVY_CC, Threat.POKE),
            coreBuild = listOf(
                core("Demon Shoes", "Mana for sustained casting."),
                core("Clock of Destiny", "Power + HP scaling."),
                core("Lightning Truncheon", "Burst proc."),
                lux("Divine Glaive", "Penetration vs resist."),
                lux("Holy Crystal", "Max burst.")
            ),
            tips = listOf(
                "Pair Yin/Yang marks to detonate a stun, then ult to pull the team together.",
                "Your ult repositions your whole team — use it to engage or escape."
            ),
            powerSpike = "Strong AoE setup mage in coordinated fights.",
            emblem = "Mage", emblemTalents = "Impure Rage · burst focus",
            battleSpell = "Flicker", battleSpellAlt = "Flameshot"
        ),

        // ============================= FIGHTERS ============================
        Hero(
            name = "Chou", role = Role.FIGHTER, damageType = DamageType.PHYSICAL,
            threats = setOf(Threat.HEAVY_CC, Threat.HIGH_MOBILITY, Threat.BURST),
            coreBuild = listOf(
                core("Warrior Boots", "Early physical defense to brawl the lane."),
                core("Endless Battle", "True damage, lifesteal and move speed — perfect on Chou."),
                core("War Axe", "Stacking power and CDR as you keep fighting."),
                lux("Blade of Despair", "Convert him into a one-combo assassin."),
                lux("Immortality", "Dive, kick, die, revive, repeat.")
            ),
            tips = listOf(
                "First-skill dashes are immune to crowd control — dodge stuns with them.",
                "Kick priority targets out of position into your team."
            ),
            powerSpike = "Strong from level 1; spikes hard with Endless Battle.",
            emblem = "Fighter", emblemTalents = "Festival of Blood · spell vamp",
            battleSpell = "Flicker", battleSpellAlt = "Sprint"
        ),
        Hero(
            name = "Paquito", role = Role.FIGHTER, damageType = DamageType.PHYSICAL,
            threats = setOf(Threat.BURST, Threat.HIGH_MOBILITY),
            coreBuild = listOf(
                core("Tough Boots", "Tenacity against the CC that locks down a brawler."),
                core("Endless Battle", "Sustain, true damage and chase speed."),
                core("Hunter Strike", "Penetration plus a movement spike for your combos."),
                lux("Blade of Despair", "Pure burst to delete the back line."),
                lux("Malefic Roar", "Shred tanks late.")
            ),
            tips = listOf(
                "Land empowered (glowing) skills for the real damage.",
                "Use your ult's knock-up to set up your team's follow-up."
            ),
            powerSpike = "Early-to-mid lane bully; falls off vs full-build tanks.",
            emblem = "Fighter", emblemTalents = "Killing Spree · sustain on kills",
            battleSpell = "Flicker", battleSpellAlt = "Retribution (jungle)"
        ),
        Hero(
            name = "Yu Zhong", role = Role.FIGHTER, damageType = DamageType.PHYSICAL,
            threats = setOf(Threat.SUSTAINED_DPS, Threat.LIFESTEAL),
            coreBuild = listOf(
                core("Warrior Boots", "Defense to sit in extended fights."),
                core("Bloodlust Axe", "Spell vamp turns his AoE into self-healing."),
                core("Oracle", "Boosts his shield + regen passive massively."),
                lux("Antique Cuirass", "Armor to brawl through physical comps."),
                lux("Immortality", "Front-line revive.")
            ),
            tips = listOf(
                "Stack your passive (Sha residue) to ramp self-healing in fights.",
                "Ult transforms you into a dragon with a fear — engage the whole team."
            ),
            powerSpike = "Durable bruiser that gets stronger the longer fights last.",
            emblem = "Fighter", emblemTalents = "Festival of Blood · spell vamp",
            battleSpell = "Flicker", battleSpellAlt = "Petrify"
        ),
        Hero(
            name = "X.Borg", role = Role.FIGHTER, damageType = DamageType.PHYSICAL,
            threats = setOf(Threat.SUSTAINED_DPS, Threat.POKE),
            coreBuild = listOf(
                core("Warrior Boots", "Defense while you brawl in flamethrower range."),
                core("War Axe", "Stacking power + CDR for sustained pressure."),
                core("Bloodlust Axe", "Spell vamp through your flame ticks."),
                lux("Antique Cuirass", "Armor for the front line."),
                lux("Brute Force Breastplate", "Move + defense stacking.")
            ),
            tips = listOf(
                "Your armor is a separate health bar — ult to detonate it for huge AoE, then re-equip.",
                "Stay at flamethrower range; you have true-damage burn but little burst."
            ),
            powerSpike = "Strong sustained-DPS bruiser through the mid game.",
            emblem = "Fighter", emblemTalents = "Festival of Blood · spell vamp",
            battleSpell = "Flicker", battleSpellAlt = "Sprint"
        ),
        Hero(
            name = "Dyrroth", role = Role.FIGHTER, damageType = DamageType.PHYSICAL,
            threats = setOf(Threat.BURST, Threat.TANKY),
            coreBuild = listOf(
                core("Warrior Boots", "Defense to all-in safely."),
                core("Bloodlust Axe", "Spell vamp sustain through skills."),
                core("Malefic Roar", "Penetration synergizes with his natural pen passive."),
                lux("Blade of Despair", "Burst spike."),
                lux("Queen's Wings", "Lifeline when diving.")
            ),
            tips = listOf(
                "Your passive ignores a chunk of enemy armor — you punish defensive cores.",
                "Empowered (rage) skills hit much harder — manage your rage bar."
            ),
            powerSpike = "Strong early all-in; great anti-tank bruiser.",
            emblem = "Fighter", emblemTalents = "Killing Spree · sustain on kills",
            battleSpell = "Flicker", battleSpellAlt = "Execute"
        ),
        Hero(
            name = "Aldous", role = Role.FIGHTER, damageType = DamageType.PHYSICAL,
            threats = setOf(Threat.BURST),
            coreBuild = listOf(
                core("Warrior Boots", "Defense so you survive to stack."),
                core("Bloodlust Axe", "Spell vamp from your enhanced fists."),
                core("Brute Force Breastplate", "Tanky stacking to dive."),
                lux("Immortality", "Two lives to deliver the global ult."),
                lux("Antique Cuirass", "Armor for diving.")
            ),
            tips = listOf(
                "Stack skill 1 kills/hits early for permanent, infinite-scaling fist damage.",
                "Ult flies you across the map to a marked target — gank and snipe globally."
            ),
            powerSpike = "Snowballs with stacks; a late-game one-punch threat.",
            emblem = "Fighter", emblemTalents = "Festival of Blood · spell vamp",
            battleSpell = "Flicker", battleSpellAlt = "Sprint"
        ),
        Hero(
            name = "Guinevere", role = Role.FIGHTER, damageType = DamageType.MAGIC,
            threats = setOf(Threat.BURST, Threat.HEAVY_CC),
            coreBuild = listOf(
                core("Magic Shoes", "CDR for combo uptime."),
                core("Calamity Reaper", "Mana + true-damage proc on her bursts."),
                core("Glowing Wand", "Burn + power for the combo."),
                lux("Divine Glaive", "Penetration vs resist."),
                lux("Holy Crystal", "Max burst.")
            ),
            tips = listOf(
                "Knock a target up with skill 2, then ult-suplex for a guaranteed combo.",
                "Passive boosts your next basic after a skill — weave it in."
            ),
            powerSpike = "Burst-and-CC magic bruiser; strong from level 4.",
            emblem = "Fighter", emblemTalents = "Impure Rage · burst focus",
            battleSpell = "Flicker", battleSpellAlt = "Petrify"
        ),
        Hero(
            name = "Roger", role = Role.FIGHTER, damageType = DamageType.PHYSICAL,
            threats = setOf(Threat.BURST, Threat.HIGH_MOBILITY),
            coreBuild = listOf(
                core("Swift Boots", "Attack speed for wolf-form DPS."),
                core("Berserker's Fury", "Crit core for the wolf burst."),
                core("Demon Hunter Sword", "Percent damage + lifesteal."),
                lux("Blade of Despair", "Big per-shot/bite burst."),
                lux("Wind of Nature", "Survive a dive.")
            ),
            tips = listOf(
                "Human form pokes and slows; wolf form bursts and dashes — swap to combo.",
                "Open in human form to slow, then transform and leap for the kill."
            ),
            powerSpike = "Strong dueler from two items; flexible MM/fighter hybrid.",
            emblem = "Fighter", emblemTalents = "Weapons Master · crit",
            battleSpell = "Retribution (jungle)", battleSpellAlt = "Flicker"
        ),
        Hero(
            name = "Lapu-Lapu", role = Role.FIGHTER, damageType = DamageType.PHYSICAL,
            threats = setOf(Threat.BURST, Threat.HEAVY_CC),
            coreBuild = listOf(
                core("Warrior Boots", "Defense to brawl."),
                core("Endless Battle", "True damage + sustain on his combos."),
                core("War Axe", "Stacking power + CDR."),
                lux("Blade of Despair", "Burst spike."),
                lux("Immortality", "Dive insurance.")
            ),
            tips = listOf(
                "Build energy with single-sword skills, then ult into twin-sword AoE burst.",
                "Twin-sword ult skills knock up and crit — catch multiple enemies."
            ),
            powerSpike = "AoE teamfight bruiser; strong in the mid game.",
            emblem = "Fighter", emblemTalents = "Festival of Blood · spell vamp",
            battleSpell = "Flicker", battleSpellAlt = "Petrify"
        ),
        Hero(
            name = "Leomord", role = Role.FIGHTER, damageType = DamageType.PHYSICAL,
            threats = setOf(Threat.SUSTAINED_DPS, Threat.HEAVY_CC),
            coreBuild = listOf(
                core("Warrior Boots", "Defense to sustain in fights."),
                core("Endless Battle", "True damage + sustain on basics."),
                core("Berserker's Fury", "Crit for the enhanced attacks."),
                lux("Blade of Despair", "Burst spike."),
                lux("Immortality", "Front-line revive.")
            ),
            tips = listOf(
                "Drop a target low, then ult to mount Barbiel for an enhanced charging onslaught.",
                "Your passive crits on cooldown — weave basics between skills."
            ),
            powerSpike = "Strong sustained bruiser from two items.",
            emblem = "Fighter", emblemTalents = "Weapons Master · crit",
            battleSpell = "Flicker", battleSpellAlt = "Execute"
        ),
        Hero(
            name = "Masha", role = Role.FIGHTER, damageType = DamageType.PHYSICAL,
            threats = setOf(Threat.TANKY, Threat.SUSTAINED_DPS),
            coreBuild = listOf(
                core("Warrior Boots", "Defense; you fight with HP bars."),
                core("Corrosion Scythe", "Attack speed + slow — your percent passive scales with hits."),
                core("Demon Hunter Sword", "Percent damage + lifesteal."),
                lux("Blade of Despair", "Burst per hit."),
                lux("Brute Force Breastplate", "Move + defense.")
            ),
            tips = listOf(
                "Your three HP bars let you split-push and dive towers fearlessly.",
                "Basics deal percent-HP damage — you shred tanks; build attack speed."
            ),
            powerSpike = "Strong side-laner and tower-diver all game.",
            emblem = "Fighter", emblemTalents = "Weapons Master · attack speed",
            battleSpell = "Retribution (jungle)", battleSpellAlt = "Flicker"
        ),
        Hero(
            name = "Silvanna", role = Role.FIGHTER, damageType = DamageType.MAGIC,
            threats = setOf(Threat.HEAVY_CC, Threat.BURST),
            coreBuild = listOf(
                core("Magic Shoes", "CDR for combo and ult uptime."),
                core("Calamity Reaper", "Mana + true-damage proc."),
                core("Glowing Wand", "Burn + power."),
                lux("Divine Glaive", "Penetration vs resist."),
                lux("Athena's Shield", "Magic shield to dive safely.")
            ),
            tips = listOf(
                "Ult traps a key target in your arena — isolate the enemy carry.",
                "Your passive ramps magic damage on hits — keep attacking inside the ring."
            ),
            powerSpike = "Strong single-target lockdown bruiser from the mid game.",
            emblem = "Fighter", emblemTalents = "Impure Rage · magic burst",
            battleSpell = "Flicker", battleSpellAlt = "Petrify"
        ),
        Hero(
            name = "Thamuz", role = Role.FIGHTER, damageType = DamageType.PHYSICAL,
            threats = setOf(Threat.LIFESTEAL, Threat.SUSTAINED_DPS),
            coreBuild = listOf(
                core("Warrior Boots", "Defense to sit in melee."),
                core("Bloodlust Axe", "Spell vamp pairs with his ult lifesteal."),
                core("Oracle", "Boosts his regen + shields."),
                lux("Antique Cuirass", "Armor to brawl physical comps."),
                lux("Immortality", "Front-line revive.")
            ),
            tips = listOf(
                "Ult gives lifesteal on your scythes — dive in and out-sustain their damage.",
                "Recall your scythes through enemies for extra damage on the return."
            ),
            powerSpike = "Late-game sustain tank-killer; strong in long fights.",
            emblem = "Fighter", emblemTalents = "Festival of Blood · spell vamp",
            battleSpell = "Flicker", battleSpellAlt = "Petrify"
        ),
        Hero(
            name = "Zilong", role = Role.FIGHTER, damageType = DamageType.PHYSICAL,
            threats = setOf(Threat.SUSTAINED_DPS, Threat.HIGH_MOBILITY),
            coreBuild = listOf(
                core("Swift Boots", "Attack speed for his passive double-strike."),
                core("Berserker's Fury", "Crit core."),
                core("Demon Hunter Sword", "Percent damage + lifesteal."),
                lux("Blade of Despair", "Burst spike."),
                lux("Wind of Nature", "Survive a dive while split-pushing.")
            ),
            tips = listOf(
                "Spear-dash through a target to reposition behind them and stick on.",
                "Ult boosts attack + move speed team-wide; use it to split-push or chase."
            ),
            powerSpike = "Strong duelist and split-pusher from two items.",
            emblem = "Fighter", emblemTalents = "Weapons Master · crit + attack speed",
            battleSpell = "Flicker", battleSpellAlt = "Sprint"
        ),
        Hero(
            name = "Khaleed", role = Role.FIGHTER, damageType = DamageType.PHYSICAL,
            threats = setOf(Threat.SUSTAINED_DPS, Threat.LIFESTEAL),
            coreBuild = listOf(
                core("Warrior Boots", "Defense to brawl."),
                core("Bloodlust Axe", "Spell vamp on his AoE skills."),
                core("War Axe", "Stacking power + CDR."),
                lux("Antique Cuirass", "Armor for the front line."),
                lux("Immortality", "Dive insurance.")
            ),
            tips = listOf(
                "Surf on sand (move while passive is up) to regen and reposition.",
                "Ult knocks up an area — engage onto the clumped enemy team."
            ),
            powerSpike = "Durable AoE bruiser; strong in the mid game.",
            emblem = "Fighter", emblemTalents = "Festival of Blood · spell vamp",
            battleSpell = "Flicker", battleSpellAlt = "Petrify"
        ),
        Hero(
            name = "Fredrinn", role = Role.FIGHTER, damageType = DamageType.PHYSICAL,
            threats = setOf(Threat.TANKY, Threat.HEAVY_CC),
            coreBuild = listOf(
                core("Warrior/Tough Boots", "Defense to soak as a tanky jungler."),
                core("War Axe", "Power + CDR for his sustained AoE."),
                core("Antique Cuirass", "Armor + weaken to brawl."),
                lux("Oracle", "Boosts his shields + regen."),
                lux("Immortality", "Front-line revive.")
            ),
            tips = listOf(
                "Stack damage taken, then ult to convert it into a massive AoE stun + burst.",
                "He's a tanky jungler — peel for carries while still dealing real damage."
            ),
            powerSpike = "Tanky teamfight initiator with strong AoE control.",
            emblem = "Fighter", emblemTalents = "Brave Smite · heal on damage",
            battleSpell = "Retribution (jungle)", battleSpellAlt = "Flicker"
        ),
        Hero(
            name = "Arlott", role = Role.FIGHTER, damageType = DamageType.PHYSICAL,
            threats = setOf(Threat.BURST, Threat.HEAVY_CC),
            coreBuild = listOf(
                core("Warrior Boots", "Defense to all-in."),
                core("Endless Battle", "True damage + sustain on his combos."),
                core("Hunter Strike", "Penetration + mobility."),
                lux("Blade of Despair", "Burst spike."),
                lux("Queen's Wings", "Lifeline when diving.")
            ),
            tips = listOf(
                "His dash refreshes on a kill — chain through fights for resets.",
                "Ult is a wide suppress-knock area — land it on the clumped enemy team."
            ),
            powerSpike = "Strong all-in bruiser with reset potential, mid game on.",
            emblem = "Fighter", emblemTalents = "Killing Spree · sustain on kills",
            battleSpell = "Flicker", battleSpellAlt = "Execute"
        ),
        Hero(
            name = "Julian", role = Role.FIGHTER, damageType = DamageType.MAGIC,
            threats = setOf(Threat.BURST, Threat.HEAVY_CC),
            coreBuild = listOf(
                core("Magic Shoes", "CDR for combo flexibility."),
                core("Calamity Reaper", "Mana + true-damage proc."),
                core("Glowing Wand", "Burn + power."),
                lux("Divine Glaive", "Penetration vs resist."),
                lux("Athena's Shield", "Dive safely vs mages.")
            ),
            tips = listOf(
                "Cast skills to enchant the next one — plan your combo order for max effect.",
                "Ult resets your skill enchantments — open with it for a full second combo."
            ),
            powerSpike = "Combo-burst magic bruiser; strong from the mid game.",
            emblem = "Fighter", emblemTalents = "Impure Rage · magic burst",
            battleSpell = "Flicker", battleSpellAlt = "Petrify"
        ),
        Hero(
            name = "Balmond", role = Role.FIGHTER, damageType = DamageType.PHYSICAL,
            threats = setOf(Threat.TANKY, Threat.LIFESTEAL),
            coreBuild = listOf(
                core("Warrior Boots", "Defense to brawl."),
                core("Bloodlust Axe", "Spell vamp + his passive regen sustain."),
                core("War Axe", "Power + CDR."),
                lux("Malefic Roar", "Penetration; his ult deals percent HP."),
                lux("Immortality", "Front-line revive.")
            ),
            tips = listOf(
                "Ult executes based on missing HP — finish low targets across a fight.",
                "Spin (skill 2) ramps damage — stay on top of enemies to keep it going."
            ),
            powerSpike = "Beginner-friendly tank-killer; strong all game.",
            emblem = "Fighter", emblemTalents = "Festival of Blood · spell vamp",
            battleSpell = "Flicker", battleSpellAlt = "Execute"
        ),

        // ============================== TANKS ==============================
        Hero(
            name = "Tigreal", role = Role.TANK, damageType = DamageType.PHYSICAL,
            threats = setOf(Threat.HEAVY_CC),
            coreBuild = listOf(
                core("Tough Boots", "Tenacity so you reach the back line through their CC."),
                core("Dominance Ice", "Armor, mana, attack-speed slow aura and anti-heal."),
                core("Athena's Shield", "Magic shield against burst mages."),
                lux("Antique Cuirass", "Stacks armor and weakens attackers."),
                lux("Immortality", "Front-line revive to keep peeling.")
            ),
            tips = listOf(
                "Flicker + ultimate is the classic combo to pull multiple enemies in.",
                "Build defense reactively: armor vs physical, magic resist vs mages."
            ),
            powerSpike = "Useful at all stages; engage potential from level 1.",
            emblem = "Tank", emblemTalents = "Pull Yourself Together · CD reset",
            battleSpell = "Flicker", battleSpellAlt = "Petrify"
        ),
        Hero(
            name = "Atlas", role = Role.TANK, damageType = DamageType.MAGIC,
            threats = setOf(Threat.HEAVY_CC),
            coreBuild = listOf(
                core("Tough Boots", "Reach your ult range through their crowd control."),
                core("Glowing Wand", "A little magic damage makes your combo threatening."),
                core("Dominance Ice", "Defense + anti-heal + slow aura on a frontliner."),
                lux("Athena's Shield", "Soak magic burst as you dive."),
                lux("Immortality", "Revive after a big engage.")
            ),
            tips = listOf(
                "Land your ultimate on a clumped enemy team for a multi-hero pull.",
                "Flicker mid-ult to extend your engage range and surprise the back line."
            ),
            powerSpike = "Mid-game engage king once he can survive the dive.",
            emblem = "Tank", emblemTalents = "Pull Yourself Together · CD reset",
            battleSpell = "Flicker", battleSpellAlt = "Petrify"
        ),
        Hero(
            name = "Franco", role = Role.TANK, damageType = DamageType.PHYSICAL,
            threats = setOf(Threat.HEAVY_CC),
            coreBuild = listOf(
                core("Tough Boots", "Tenacity to land hooks through CC."),
                core("Dominance Ice", "Armor + anti-heal + slow aura."),
                core("Athena's Shield", "Magic shield vs burst mages."),
                lux("Antique Cuirass", "Armor stacking."),
                lux("Immortality", "Stay alive to keep picking.")
            ),
            tips = listOf(
                "Hook lands the engage — practice flicker-hook to surprise targets.",
                "Ult suppresses a single key target; lock the enemy carry in fights."
            ),
            powerSpike = "Strong pick threat all game; relies on hook accuracy.",
            emblem = "Tank", emblemTalents = "Pull Yourself Together · CD reset",
            battleSpell = "Flicker", battleSpellAlt = "Vengeance"
        ),
        Hero(
            name = "Khufra", role = Role.TANK, damageType = DamageType.PHYSICAL,
            threats = setOf(Threat.HEAVY_CC, Threat.HIGH_MOBILITY),
            coreBuild = listOf(
                core("Tough Boots", "Tenacity to chain your CC."),
                core("Dominance Ice", "Armor + anti-heal + slow aura."),
                core("Athena's Shield", "Magic shield vs mages."),
                lux("Antique Cuirass", "Armor stacking."),
                lux("Immortality", "Front-line revive.")
            ),
            tips = listOf(
                "Your bounce (skill 2) interrupts enemy dashes — counter mobile heroes hard.",
                "Ult walls a target against terrain; pin the enemy carry for your team."
            ),
            powerSpike = "Premier anti-mobility engage tank from the mid game.",
            emblem = "Tank", emblemTalents = "Pull Yourself Together · CD reset",
            battleSpell = "Flicker", battleSpellAlt = "Petrify"
        ),
        Hero(
            name = "Akai", role = Role.TANK, damageType = DamageType.MAGIC,
            threats = setOf(Threat.HEAVY_CC),
            coreBuild = listOf(
                core("Tough Boots", "Tenacity to land your ult."),
                core("Dominance Ice", "Armor + anti-heal + slow aura."),
                core("Cursed Helmet", "AoE magic burn + HP fits his brawling."),
                lux("Athena's Shield", "Magic shield vs mages."),
                lux("Immortality", "Front-line revive.")
            ),
            tips = listOf(
                "Ult circles a target and shoves them — pin enemies against walls or your team.",
                "Use the spin to body-block and disrupt the enemy back line."
            ),
            powerSpike = "Strong displacement engage tank from the mid game.",
            emblem = "Tank", emblemTalents = "Brave Smite · heal on damage",
            battleSpell = "Flicker", battleSpellAlt = "Petrify"
        ),
        Hero(
            name = "Grock", role = Role.TANK, damageType = DamageType.PHYSICAL,
            threats = setOf(Threat.HEAVY_CC, Threat.HIGH_MOBILITY),
            coreBuild = listOf(
                core("Warrior/Tough Boots", "Defense; reach engages."),
                core("Dominance Ice", "Armor + anti-heal + slow aura."),
                core("Athena's Shield", "Magic shield vs mages."),
                lux("Antique Cuirass", "Armor stacking."),
                lux("Immortality", "Front-line revive.")
            ),
            tips = listOf(
                "Near walls you gain move + defense — fight along terrain.",
                "Ult walls enemies in or out; split their team or trap a target."
            ),
            powerSpike = "Strong engage + zoning tank; mobile near terrain.",
            emblem = "Tank", emblemTalents = "Pull Yourself Together · CD reset",
            battleSpell = "Flicker", battleSpellAlt = "Petrify"
        ),
        Hero(
            name = "Hylos", role = Role.TANK, damageType = DamageType.MAGIC,
            threats = setOf(Threat.HEAVY_CC, Threat.TANKY),
            coreBuild = listOf(
                core("Magic/Tough Boots", "CDR or tenacity; you scale with HP."),
                core("Cursed Helmet", "AoE burn scales with your huge HP pool."),
                core("Dominance Ice", "Armor + anti-heal + slow aura."),
                lux("Immortality", "Front-line revive."),
                lux("Athena's Shield", "Magic shield vs mages.")
            ),
            tips = listOf(
                "Your mana doubles as a second health bar — build HP/regen to leverage it.",
                "Ult lays a slowing path; wall off chokes to control fights."
            ),
            powerSpike = "Late-game unkillable frontline with strong zoning.",
            emblem = "Tank", emblemTalents = "Brave Smite · heal on damage",
            battleSpell = "Flicker", battleSpellAlt = "Petrify"
        ),
        Hero(
            name = "Edith", role = Role.TANK, damageType = DamageType.MAGIC,
            threats = setOf(Threat.HEAVY_CC, Threat.SUSTAINED_DPS),
            coreBuild = listOf(
                core("Tough Boots", "Tenacity in mech form to land CC."),
                core("Dominance Ice", "Armor + anti-heal + slow aura."),
                core("Athena's Shield", "Magic shield; you scale defense into ult damage."),
                lux("Antique Cuirass", "More armor = stronger ult."),
                lux("Genius Wand", "Magic shred for ranged form damage.")
            ),
            tips = listOf(
                "Tank in mech form, then ult into ranged marksman form to deal AoE magic DPS.",
                "Your ranged-form damage scales off the defense you stacked — build tanky."
            ),
            powerSpike = "Tanky front-to-back hybrid; strong from the mid game.",
            emblem = "Tank", emblemTalents = "Pull Yourself Together · CD reset",
            battleSpell = "Flicker", battleSpellAlt = "Vengeance"
        ),
        Hero(
            name = "Gatotkaca", role = Role.TANK, damageType = DamageType.MAGIC,
            threats = setOf(Threat.HEAVY_CC),
            coreBuild = listOf(
                core("Tough Boots", "Tenacity to land your ult."),
                core("Cursed Helmet", "AoE burn + HP for brawling."),
                core("Dominance Ice", "Armor + anti-heal + slow aura."),
                lux("Athena's Shield", "Magic shield vs mages."),
                lux("Immortality", "Front-line revive.")
            ),
            tips = listOf(
                "Taking damage charges your passive — taunt and ult to pull enemies together.",
                "Ult roots a whole area; combo with your team's AoE follow-up."
            ),
            powerSpike = "Strong AoE engage tank in grouped fights.",
            emblem = "Tank", emblemTalents = "Brave Smite · heal on damage",
            battleSpell = "Flicker", battleSpellAlt = "Petrify"
        ),
        Hero(
            name = "Johnson", role = Role.TANK, damageType = DamageType.MAGIC,
            threats = setOf(Threat.HEAVY_CC),
            coreBuild = listOf(
                core("Tough Boots", "Tenacity for engages."),
                core("Dominance Ice", "Armor + anti-heal + slow aura."),
                core("Athena's Shield", "Magic shield vs mages."),
                lux("Cursed Helmet", "Burn + HP."),
                lux("Immortality", "Front-line revive.")
            ),
            tips = listOf(
                "Ult turns you into a car — a teammate rides along for a cross-map stun engage.",
                "Coordinate the ult with your team's burst for a surprise gank."
            ),
            powerSpike = "Game-changing roams once ult is available; strong all game.",
            emblem = "Tank", emblemTalents = "Pull Yourself Together · CD reset",
            battleSpell = "Flicker", battleSpellAlt = "Vengeance"
        ),
        Hero(
            name = "Uranus", role = Role.TANK, damageType = DamageType.MAGIC,
            threats = setOf(Threat.LIFESTEAL, Threat.SUSTAINED_DPS),
            coreBuild = listOf(
                core("Magic/Tough Boots", "CDR or tenacity for sustained brawls."),
                core("Cursed Helmet", "AoE burn scales with HP."),
                core("Oracle", "Hugely boosts his shield regen passive."),
                lux("Dominance Ice", "Armor + anti-heal."),
                lux("Athena's Shield", "Magic shield vs mages.")
            ),
            tips = listOf(
                "Stacking your passive shields you the more you fight — never disengage early.",
                "Great side-laner: out-sustains most duelists and dives towers safely."
            ),
            powerSpike = "Late-game unkillable bruiser-tank in extended fights.",
            emblem = "Tank", emblemTalents = "Brave Smite · heal on damage",
            battleSpell = "Flicker", battleSpellAlt = "Sprint"
        ),
        Hero(
            name = "Baxia", role = Role.TANK, damageType = DamageType.MAGIC,
            threats = setOf(Threat.LIFESTEAL, Threat.HEAVY_CC),
            coreBuild = listOf(
                core("Tough Boots", "Tenacity for dives."),
                core("Dominance Ice", "Armor + anti-heal — synergizes with his heal-cut passive."),
                core("Cursed Helmet", "AoE burn + HP."),
                lux("Athena's Shield", "Magic shield vs mages."),
                lux("Immortality", "Front-line revive.")
            ),
            tips = listOf(
                "Your passive reduces enemy healing/shields — you hard-counter sustain comps.",
                "Roll around with skill 1 to engage, kite, and reposition constantly."
            ),
            powerSpike = "Strong anti-heal engage tank; great vs regen-heavy teams.",
            emblem = "Tank", emblemTalents = "Pull Yourself Together · CD reset",
            battleSpell = "Flicker", battleSpellAlt = "Sprint"
        ),
        Hero(
            name = "Belerick", role = Role.TANK, damageType = DamageType.MAGIC,
            threats = setOf(Threat.HEAVY_CC, Threat.TANKY),
            coreBuild = listOf(
                core("Tough Boots", "Tenacity to peel."),
                core("Antique Cuirass", "Armor + weaken; you reflect and absorb."),
                core("Dominance Ice", "Armor + anti-heal + slow aura."),
                lux("Athena's Shield", "Magic shield vs mages."),
                lux("Immortality", "Front-line revive.")
            ),
            tips = listOf(
                "Taunt enemies into attacking you, then reflect damage with your thorns.",
                "A peel-tank: protect your carry rather than diving the back line."
            ),
            powerSpike = "Durable peel-tank that punishes auto-attackers all game.",
            emblem = "Tank", emblemTalents = "Brave Smite · heal on damage",
            battleSpell = "Flicker", battleSpellAlt = "Vengeance"
        ),
        Hero(
            name = "Minotaur", role = Role.TANK, damageType = DamageType.MAGIC,
            threats = setOf(Threat.HEAVY_CC),
            coreBuild = listOf(
                core("Tough Boots", "Tenacity to land your ult."),
                core("Dominance Ice", "Armor + anti-heal + slow aura."),
                core("Athena's Shield", "Magic shield vs mages."),
                lux("Cursed Helmet", "Burn + HP."),
                lux("Immortality", "Front-line revive.")
            ),
            tips = listOf(
                "Build your rage bar with skills, then unleash the enraged ult for a big AoE stun + heal.",
                "Don't engage at low rage — wait until your ult is empowered."
            ),
            powerSpike = "Strong AoE engage + team-heal tank in grouped fights.",
            emblem = "Tank", emblemTalents = "Brave Smite · heal on damage",
            battleSpell = "Flicker", battleSpellAlt = "Petrify"
        ),
        Hero(
            name = "Lolita", role = Role.TANK, damageType = DamageType.MAGIC,
            threats = setOf(Threat.HEAVY_CC),
            coreBuild = listOf(
                core("Tough Boots", "Tenacity to reach engages."),
                core("Dominance Ice", "Armor + anti-heal + slow aura."),
                core("Athena's Shield", "Magic shield vs mages."),
                lux("Antique Cuirass", "Armor stacking."),
                lux("Immortality", "Front-line revive.")
            ),
            tips = listOf(
                "Your shield blocks ranged basics and projectiles — body-block for your carry.",
                "Ult stuns in a cone; channel it to catch a grouped enemy team."
            ),
            powerSpike = "Strong anti-marksman peel tank; great vs ranged comps.",
            emblem = "Tank", emblemTalents = "Pull Yourself Together · CD reset",
            battleSpell = "Flicker", battleSpellAlt = "Vengeance"
        ),

        // ============================= SUPPORTS ============================
        Hero(
            name = "Estes", role = Role.SUPPORT, damageType = DamageType.MAGIC,
            threats = setOf(Threat.LIFESTEAL),
            coreBuild = listOf(
                core("Magic Shoes", "CDR so your healing chain stays up."),
                core("Enchanted Talisman", "Mana, CDR and HP to keep healing all fight."),
                core("Oracle", "Boosts shield/heal effects — pure value on Estes."),
                lux("Immortality", "Stay alive so the team keeps its healer."),
                lux("Necklace of Durance", "If you must fight an enemy healer.")
            ),
            tips = listOf(
                "Keep your link on a low-HP ally to ramp the heal-over-time, then ult.",
                "You are immobile and a focus magnet; ward and stay behind the frontline."
            ),
            powerSpike = "Strong sustained-fight support that scales with magic power.",
            emblem = "Support", emblemTalents = "Pull Yourself Together · CD reset",
            battleSpell = "Flicker", battleSpellAlt = "Sprint"
        ),
        Hero(
            name = "Mathilda", role = Role.SUPPORT, damageType = DamageType.MAGIC,
            threats = setOf(Threat.HIGH_MOBILITY),
            coreBuild = listOf(
                core("Magic Shoes", "CDR for constant roams and engages."),
                core("Enchanted Talisman", "CDR + mana to keep skills flowing."),
                core("Dominance Ice", "Defense + anti-heal aura while you peel."),
                lux("Oracle", "Boosts your shield passive."),
                lux("Immortality", "Insurance for aggressive roams.")
            ),
            tips = listOf(
                "Use your ult to carry an ally into or out of a fight.",
                "Roam constantly; you provide vision, engage and escape for the carry."
            ),
            powerSpike = "Roaming threat from early game; great gank setup support.",
            emblem = "Support", emblemTalents = "Pull Yourself Together · CD reset",
            battleSpell = "Flicker", battleSpellAlt = "Sprint"
        ),
        Hero(
            name = "Angela", role = Role.SUPPORT, damageType = DamageType.MAGIC,
            threats = setOf(Threat.POKE),
            coreBuild = listOf(
                core("Magic Shoes", "CDR for shield + heal uptime."),
                core("Enchanted Talisman", "Mana + CDR to spam skills."),
                core("Oracle", "Boosts your shields and regen."),
                lux("Immortality", "Stay alive to keep supporting."),
                lux("Necklace of Durance", "Anti-heal if you go for poke.")
            ),
            tips = listOf(
                "Ult attaches you to an ally globally — save a diving carry or join a far fight.",
                "Keep your heart-link on the carry for shields, slows and move speed."
            ),
            powerSpike = "Global-presence enchanter support all game.",
            emblem = "Support", emblemTalents = "Pull Yourself Together · CD reset",
            battleSpell = "Flicker", battleSpellAlt = "Sprint"
        ),
        Hero(
            name = "Rafaela", role = Role.SUPPORT, damageType = DamageType.MAGIC,
            threats = setOf(Threat.HEAVY_CC),
            coreBuild = listOf(
                core("Magic Shoes", "CDR for heal + stun uptime."),
                core("Enchanted Talisman", "Mana + CDR."),
                core("Oracle", "Boosts your healing."),
                lux("Immortality", "Stay alive to keep healing."),
                lux("Dominance Ice", "Defense + anti-heal aura.")
            ),
            tips = listOf(
                "Your heal also grants move speed — use it to engage or escape with allies.",
                "Ult is a long-range stun line; catch fleeing or diving enemies."
            ),
            powerSpike = "Reliable heal + vision support; strong early roams.",
            emblem = "Support", emblemTalents = "Pull Yourself Together · CD reset",
            battleSpell = "Flicker", battleSpellAlt = "Sprint"
        ),
        Hero(
            name = "Diggie", role = Role.SUPPORT, damageType = DamageType.MAGIC,
            threats = setOf(Threat.HEAVY_CC),
            coreBuild = listOf(
                core("Magic Shoes", "CDR for constant bombs and vision."),
                core("Enchanted Talisman", "Mana + CDR."),
                core("Oracle", "Boosts your ult shields."),
                lux("Dominance Ice", "Defense + anti-heal aura."),
                lux("Immortality", "Stay alive to keep zoning.")
            ),
            tips = listOf(
                "Ult shields the whole team and cleanses CC — save it to counter their engage.",
                "Drop time-bombs to zone and reveal; you hard-counter enemy CC comps."
            ),
            powerSpike = "Anti-CC counter-engage support; great vs heavy-CC teams.",
            emblem = "Support", emblemTalents = "Pull Yourself Together · CD reset",
            battleSpell = "Flicker", battleSpellAlt = "Sprint"
        ),
        Hero(
            name = "Floryn", role = Role.SUPPORT, damageType = DamageType.MAGIC,
            threats = setOf(Threat.LIFESTEAL),
            coreBuild = listOf(
                core("Magic Shoes", "CDR for heal uptime."),
                core("Enchanted Talisman", "Mana + CDR."),
                core("Oracle", "Boosts your heals + shields."),
                lux("Immortality", "Stay alive to keep healing."),
                lux("Necklace of Durance", "Anti-heal vs sustain comps.")
            ),
            tips = listOf(
                "Ult heals the whole team globally and reveals enemies — clutch any fight.",
                "Your heal scales with allies' missing HP; top up the diving frontline."
            ),
            powerSpike = "Global-heal enchanter; strong team sustain all game.",
            emblem = "Support", emblemTalents = "Pull Yourself Together · CD reset",
            battleSpell = "Flicker", battleSpellAlt = "Sprint"
        ),
        Hero(
            name = "Kaja", role = Role.SUPPORT, damageType = DamageType.MAGIC,
            threats = setOf(Threat.HEAVY_CC),
            coreBuild = listOf(
                core("Tough Boots", "Tenacity to land your suppress."),
                core("Dominance Ice", "Defense + anti-heal + slow aura."),
                core("Athena's Shield", "Magic shield to dive in."),
                lux("Oracle", "Boosts your shields/regen."),
                lux("Immortality", "Stay alive after the pick.")
            ),
            tips = listOf(
                "Ult drags a single target back toward your team — kidnap the enemy carry.",
                "Flicker mid-ult to extend the drag and surprise an isolated target."
            ),
            powerSpike = "Premier single-target pick support from the mid game.",
            emblem = "Support", emblemTalents = "Pull Yourself Together · CD reset",
            battleSpell = "Flicker", battleSpellAlt = "Petrify"
        ),
        Hero(
            name = "Carmilla", role = Role.SUPPORT, damageType = DamageType.MAGIC,
            threats = setOf(Threat.HEAVY_CC),
            coreBuild = listOf(
                core("Tough Boots", "Tenacity to engage."),
                core("Dominance Ice", "Defense + anti-heal + slow aura."),
                core("Athena's Shield", "Magic shield to dive."),
                lux("Glowing Wand", "A little burn damage for threat."),
                lux("Immortality", "Front-line revive.")
            ),
            tips = listOf(
                "Ult links the enemy team — damage shared between them amplifies your team's burst.",
                "Engage with skill 2 stun, then ult to spread the incoming damage."
            ),
            powerSpike = "Strong AoE-amplify engage support in grouped fights.",
            emblem = "Support", emblemTalents = "Pull Yourself Together · CD reset",
            battleSpell = "Flicker", battleSpellAlt = "Petrify"
        )
    )

    /** All hero names, sorted, for the picker UI. */
    val heroNames: List<String> by lazy { heroes.map { it.name }.distinct().sorted() }

    fun byName(name: String): Hero? = heroes.firstOrNull { it.name.equals(name, ignoreCase = true) }
}
