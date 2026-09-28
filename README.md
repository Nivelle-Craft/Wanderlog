<div align="center">

# 🧭 Wanderlog

### Turn exploration into progression.

**Wanderlog is an exploration progression mod for Minecraft Forge.**  
Discover new places, earn Explorer XP, level up and turn travelling through the world into something worth tracking.

[![Minecraft](https://img.shields.io/badge/Minecraft-1.20.1-62B47A?style=for-the-badge)](https://www.minecraft.net/)
[![Forge](https://img.shields.io/badge/Forge-47.4.x-E88C43?style=for-the-badge)](https://files.minecraftforge.net/)
[![Java](https://img.shields.io/badge/Java-17-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)](https://adoptium.net/)
[![Build](https://img.shields.io/github/actions/workflow/status/nazhida/Wanderlog/build.yml?branch=main&style=for-the-badge&label=Build)](https://github.com/nazhida/Wanderlog/actions)

**[⚙ Builds](https://github.com/nazhida/Wanderlog/actions) · [🗺️ Roadmap](ROADMAP.md) · [🐛 Issues](https://github.com/nazhida/Wanderlog/issues) · [✦ Kivra](https://github.com/nazhida/Kivra)**

</div>

---

## 🌍 Why Wanderlog?

Minecraft gives you an enormous world to explore, but after the first few trips there is usually very little sense of long-term exploration progress.

Wanderlog changes that.

Every new discovery contributes to an **Explorer profile** that grows alongside the player. The idea is simple: travelling should feel like its own progression path instead of just being the distance between two destinations.

> **Explore. Discover. Progress.**

---

## ✨ Current features

| Feature | What it does | Status |
| :--- | :--- | :---: |
| 🌿 **Biome discovery** | Detects biomes the player discovers for the first time | ✅ |
| ⭐ **Explorer XP** | New discoveries reward exploration XP | ✅ |
| 📈 **Explorer Levels** | XP contributes to a persistent Explorer level | ✅ |
| 💾 **Persistent progress** | Exploration data survives server restarts | ✅ |
| 🧭 **Player statistics** | Tracks discovered biomes and progression | ✅ |
| ✦ **Kivra integration** | Optional economy rewards and Admin GUI statistics | ✅ |

Wanderlog is under active development. The current systems form the foundation for a much larger exploration experience.

---

## 🧭 How exploration works

Wanderlog periodically checks the biome a player is travelling through.

When that player enters a biome they have **never discovered before**:

```text
New biome discovered
        ↓
+10 Explorer XP
        ↓
Progress toward the next Explorer Level
```

A biome only counts the **first time** that player discovers it, so progression represents actual exploration rather than repeatedly walking through the same location.

---

## ⭐ Explorer progression

Every player has their own persistent Explorer profile containing information such as:

- Explorer Level
- current Explorer XP
- XP required for the next level
- discovered biome count
- discovered biome history

This progression is stored server-side, making Wanderlog suitable for multiplayer survival and modded servers.

---

## ✦ Works with Kivra

Wanderlog has an **optional integration with [Kivra](https://github.com/nazhida/Kivra)**, the modular server core.

Neither mod requires the other:

```text
Wanderlog alone  → exploration progression works normally
Kivra alone      → server core works normally
Both installed   → integration activates automatically
```

When both are installed, discovering a new biome currently gives:

```text
+10 Explorer XP
+5 Kivra coins
```

Kivra can also read Wanderlog's public statistics API. Administrators can inspect Explorer information from **`/kivra admin`**, including a player's level, XP and discovered biome count.

This keeps the two projects independent while allowing them to feel like one ecosystem when used together.

---

## 🚀 Installation

### Requirements

- **Minecraft:** 1.20.1
- **Forge:** 47.4.x
- **Java:** 17

### Install

1. Download a successful Wanderlog build.
2. Place the Wanderlog `.jar` inside the server/client `mods/` folder.
3. Start Minecraft or the server with Forge.
4. Join a world and start exploring.

For the optional Kivra features, simply install **Kivra** alongside Wanderlog. No hard dependency is required.

---

## 🧩 Architecture

Wanderlog is deliberately kept focused.

```text
Wanderlog
├── Exploration detection
├── Explorer progression
├── Persistent player data
├── Public integration API
└── Optional integrations
    └── Kivra
```

The public API allows other systems to read Explorer statistics without coupling Wanderlog directly to them.

---

## 🔌 Integration API

Wanderlog exposes a small API intended for optional server integrations:

```java
WanderlogApi.getStats(server, playerUuid);
```

The returned Explorer statistics include:

```text
level
xp
xpForNextLevel
discoveredBiomes
```

Integrations should still check whether Wanderlog is installed before referencing the API at runtime.

---

## 🗺️ What's next?

The long-term goal is to make exploration feel like a complete gameplay path. Planned ideas and development phases include richer discoveries, progression rewards, statistics and additional exploration systems.

For the detailed development plan, see **[ROADMAP.md](ROADMAP.md)**.

---

## 🧑‍💻 Building from source

Clone the repository and run:

```bash
./gradlew build
```

On Windows:

```powershell
gradlew.bat build
```

The compiled mod will be generated under:

```text
build/libs/
```

GitHub Actions also builds the project automatically when changes are pushed.

---

## 🐛 Bugs & feedback

Found a biome that does not register, progression behaving strangely, or an integration problem?

Open an [issue](https://github.com/nazhida/Wanderlog/issues) and include your Minecraft/Forge version plus the relevant log when possible.

---

<div align="center">

### 🧭 Wanderlog

**The world is bigger when every discovery matters.**

Explore farther. Keep the journey.

</div>
