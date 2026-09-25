# SimpleOres-fabric
 An unofficial port of SimpleOres for Fabric and NeoForge

## Loaders

The project is a [Stonecutter](https://stonecutter.kikugie.dev) multiloader: every Minecraft version is a `<version>-<loader>` node.

| Minecraft | Fabric | NeoForge |
|---|---|---|
| 1.19.4 → 1.20.6 | yes | no |
| 1.21 → 26.3 | yes | yes |

The datagen only exists on Fabric (`runDatagen`), the NeoForge jars reuse its output: run `runDatagen` before building a NeoForge node.

```bash
./gradlew runDatagen
./gradlew build
```

Loader specific code lives in the `fabric` and `neoforge` packages, and behind `//? if fabric` / `//? if neoforge` in the shared code.
