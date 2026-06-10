# MLBB Advisor

A floating, on-screen **item-build and tips advisor** for Mobile Legends:
Bang Bang. It docks a small draggable bubble over the game; tap it, choose
the hero **you're** playing and the enemy heroes, and it shows:

- your **core build** path,
- **situational items** tailored to the enemy line-up (anti-heal, armor,
  magic resist, penetration, survivability actives),
- your **power spike**, and
- **hero tips** and **matchup notes**.

## This is a knowledge-base advisor, not a game hack

> [!IMPORTANT]
> The app **does not read, hook into, or intercept anything from Mobile
> Legends**. It reveals no hidden information. You enter the picks yourself
> — exactly like keeping a guide site open beside the game. All suggestions
> come from a static, hand-authored knowledge base in
> [`HeroRepository.kt`](app/src/main/java/com/mlbb/advisor/data/HeroRepository.kt).
> There is nothing here that touches the game's memory, network traffic, or
> anti-cheat surface.

The only special capability it uses is Android's standard
**"Display over other apps"** overlay permission — the same one used by
chat-head messengers and screen-recording tools.

## How it works

| Layer | File | Responsibility |
|-------|------|----------------|
| Knowledge base | `data/HeroRepository.kt` | Heroes, builds, tips, threat tags |
| Advisor logic | `data/BuildAdvisor.kt` | Turns your hero + enemy picks into prioritized suggestions |
| Models | `data/Models.kt` | Plain data classes, no Android deps |
| Launcher | `ui/MainActivity.kt` | Requests permissions, starts/stops the overlay |
| Overlay | `overlay/OverlayService.kt` | Draggable bubble + expandable panel, rendering |

The advisor reads the enemy team's damage split (physical/magic) and
"threat" tags (burst, heavy CC, lifesteal, tanky, mobility, poke) and
proposes counters — e.g. anti-heal vs sustain comps, penetration vs
defense-stackers, immunity actives vs burst.

## Building

Requires Android Studio (Hedgehog+) or the Android SDK with the command
line tools.

```bash
./gradlew assembleDebug
# APK lands in app/build/outputs/apk/debug/
```

Then install on a device and, on first launch, grant **"Display over other
apps"** when prompted.

## Using it in a match

1. Launch MLBB Advisor, grant the overlay permission, tap **Start floating
   advisor**.
2. Open Mobile Legends — a small `MA` bubble floats on screen.
3. During draft / loading, tap the bubble. Pick **your hero**, then add each
   **enemy hero** as it's revealed.
4. Read the build priority and tips. Drag the bubble anywhere; tap the `—`
   to collapse.

## Extending the knowledge base

Add a `Hero(...)` entry to the `heroes` list in `HeroRepository.kt`. The
advisor logic is generic, so new heroes need **no** code changes — just fill
in the role, damage type, threat tags, core build, tips, and power spike.
