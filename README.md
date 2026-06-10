# MLBB Advisor

A floating, on-screen **item-build and tips advisor** for Mobile Legends:
Bang Bang. It docks a small draggable bubble over the game; tap it, choose
the hero **you're** playing and the enemy heroes, and it shows:

- the recommended **emblem set + talent** and **battle spell** (with an alt),
- your **core build** path,
- **situational items** tailored to the enemy line-up (anti-heal, armor,
  magic resist, penetration, survivability actives),
- your **power spike**, and
- **hero tips** and **matchup notes**.

The knowledge base currently covers **80+ heroes** across all six roles
(assassins, marksmen, mages, fighters, tanks, supports) and is trivially
extensible.

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
| Knowledge base | `data/HeroRepository.kt` | 80+ heroes: builds, emblems, spells, tips, threat tags |
| Advisor logic | `data/BuildAdvisor.kt` | Turns your hero + enemy picks into prioritized suggestions |
| Models | `data/Models.kt` | Plain data classes, no Android deps |
| Launcher | `ui/MainActivity.kt` | Requests permissions, starts/stops the overlay |
| Overlay | `overlay/OverlayService.kt` | Draggable bubble + expandable panel, rendering |

The advisor reads the enemy team's damage split (physical/magic) and
"threat" tags (burst, heavy CC, lifesteal, tanky, mobility, poke) and
proposes counters — e.g. anti-heal vs sustain comps, penetration vs
defense-stackers, immunity actives vs burst.

## Getting the APK

A GitHub Actions workflow ([`.github/workflows/build-apk.yml`](.github/workflows/build-apk.yml))
builds the APK on every push and publishes it two ways:

1. **Release asset** — see the **`apk-latest`** release on this repo and
   download `MLBB-Advisor.apk`.
2. **Workflow artifact** — open the latest **Build APK** run under the
   *Actions* tab and download the `MLBB-Advisor-apk` artifact.

Copy the APK to your Android phone and install it (you may need to enable
*Install unknown apps* for your browser/file manager). On first launch,
grant **"Display over other apps"**.

### Building locally

Requires Android Studio (Hedgehog+) or the Android SDK command-line tools:

```bash
./gradlew assembleDebug
# APK lands in app/build/outputs/apk/debug/
```

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
in the role, damage type, threat tags, core build, tips, power spike, emblem,
emblem talents, and battle spells.
