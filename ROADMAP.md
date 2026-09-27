# Wanderlog Roadmap

Wanderlog is an exploration progression mod for Minecraft Forge 1.20.1.

This roadmap is intentionally incremental: each phase should be tested and stable before the next one is added.

## v0.1 — Core exploration
**Status:** In progress

- [x] Forge 1.20.1 project base
- [x] Biome discovery
- [x] Explorer XP
- [x] Explorer levels
- [x] Persistent progress per player UUID
- [x] `/explorer` command
- [x] GitHub Actions build
- [x] Upload compiled JAR as workflow artifact
- [ ] Test the JAR in a real Forge 1.20.1 server
- [ ] Verify progress survives server restarts
- [ ] Verify biomes only reward XP once
- [ ] Fix any runtime issues found during testing

## v0.2 — Structures
- [ ] Detect vanilla structures
- [ ] Store discovered structures per player
- [ ] Structure XP rewards
- [ ] Structure rarity system
- [ ] Prevent duplicate rewards
- [ ] Investigate compatibility with modded structures
- [ ] Admin configuration for structure rewards

## v0.3 — Wanderlog Journal UI
- [ ] Client keybind to open Wanderlog
- [ ] Main explorer screen
- [ ] XP and level progress bar
- [ ] Biome collection screen
- [ ] Structure collection screen
- [ ] Hidden entries for undiscovered content
- [ ] Server-to-client data synchronization
- [ ] ES/EN translations

## v0.4 — Bestiary
- [ ] Register first sighting of mobs
- [ ] Support vanilla and modded entities
- [ ] Bestiary collection screen
- [ ] "Seen" state
- [ ] "Defeated" state
- [ ] XP rewards for discoveries
- [ ] Configurable entity blacklist

## v0.5 — Rewards
- [ ] Level milestone rewards
- [ ] Item rewards
- [ ] Command-based rewards
- [ ] Configurable reward file
- [ ] Claim tracking
- [ ] Admin reload command
- [ ] Optional economy/plugin integrations through commands

## v0.6 — Explorer's Compass
- [ ] Custom compass item
- [ ] Give directional hints toward undiscovered content
- [ ] Avoid exposing exact coordinates
- [ ] Improve accuracy with Explorer level
- [ ] Configurable cooldown
- [ ] Server-side validation

## v0.7 — Multiplayer
- [ ] `/explorer profile <player>`
- [ ] `/explorer top`
- [ ] Explorer leaderboard
- [ ] Completion percentage
- [ ] Privacy/config options for profiles
- [ ] Optimize data access for larger servers

## v0.8 — Expeditions
- [ ] Daily expeditions
- [ ] Weekly expeditions
- [ ] Biome discovery objectives
- [ ] Structure discovery objectives
- [ ] Bestiary objectives
- [ ] Configurable rewards
- [ ] Progress UI

## v0.9 — Polish & compatibility
- [ ] Performance profiling
- [ ] Configurable check intervals
- [ ] Better modded biome support
- [ ] Better modded structure support
- [ ] Better modded entity support
- [ ] Admin configuration GUI or commands
- [ ] Improved messages and visual feedback
- [ ] Dedicated-server testing

## v1.0 — Stable release
- [ ] Core systems fully tested
- [ ] Server restart persistence verified
- [ ] Multiplayer testing
- [ ] Configuration documentation
- [ ] Installation guide
- [ ] Known compatibility list
- [ ] Release JAR
- [ ] GitHub Release with changelog

## Future
These are ideas for after 1.0 and are not guaranteed for the first stable release.

- Explorer titles and cosmetics
- Relics tied to exploration milestones
- Global server discovery events
- Seasonal expeditions
- Custom advancements
- Optional map integration
- Ports to newer Minecraft versions
