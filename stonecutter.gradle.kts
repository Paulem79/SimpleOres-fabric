plugins {
    id("dev.kikugie.stonecutter")
}

stonecutter active "26.2"

stonecutter parameters {
    filters.exclude("**/*.aw")

    // Calcul de hasBucketlib dynamique pour chaque sous-projet
    // On vérifie la propriété sur le projet spécifique à la version (node.project)
    val bucketLibProp = node.project.findProperty("deps.bucketlib")
    val hasBucketlib = bucketLibProp != null && bucketLibProp != "[VERSIONED]"
    val hasMidnightLib: Boolean = node.project.findProperty("deps.midnightlib")?.takeIf { it != "[VERSIONED]" } != null
    val isLegacyMidnightLib: Boolean = hasMidnightLib && node.project.findProperty("deps.midnightlib").toString().endsWith("-fabric") && !node.project.findProperty("deps.midnightlib").toString().contains("+")

    val current = node.metadata
    val afterDeobf = eval(current.version, ">1.21.11")

    val containsBucket = true

    constants.put("hasBucketlib", hasBucketlib)

    constants.put("hasMidnightlib", hasMidnightLib)
    constants.put("isLegacyMidnightLib", isLegacyMidnightLib)

    constants.put("hasCopperTools", afterDeobf || eval(node.project.findProperty("max_version_range") as String, ">1.21.8"))
    constants.put("containsBucket", containsBucket)
    constants.put("afterDeobf", afterDeobf)

    swaps["armorType"] = when {
        eval(current.version, "<=1.21") -> "net.minecraft.world.item.ArmorItem.Type"
        else -> "net.minecraft.world.item.equipment.ArmorType"
    }

    swaps["armorRegistry"] = when {
        eval(current.version, "<=1.20.4") -> "net.paulem.simpleores.armors.ModArmorMaterials"
        eval(current.version, "=1.20.6") -> "net.minecraft.core.Holder<net.minecraft.world.item.ArmorMaterial>"
        eval(current.version, "=1.21") -> "net.minecraft.core.Holder<net.minecraft.world.item.ArmorMaterial>"
        else -> "net.minecraft.world.item.equipment.@org.jspecify.annotations.Nullable ArmorMaterial"
    }

    swaps["tagOrIngredient"] = when {
        eval(current.version, "<=1.21") -> "java.util.function.Supplier<net.minecraft.world.item.crafting.Ingredient>"
        else -> "net.minecraft.tags.TagKey<net.minecraft.world.item.Item>"
    }

    swaps["generatorOrExporter"] = when {
        eval(current.version, ">=1.21.3") -> "net.minecraft.data.recipes.RecipeProvider"
        eval(current.version, ">1.20.1") -> "net.minecraft.data.recipes.RecipeOutput"
        else -> "java.util.function.Consumer<net.minecraft.data.recipes.FinishedRecipe>"
    }

    swaps["advancementEntry"] = when {
        eval(current.version, "<=1.20.1") -> "net.minecraft.advancements.Advancement"
        else -> "net.minecraft.advancements.@org.jspecify.annotations.Nullable AdvancementHolder"
    }

    swaps["location"] = when {
        eval(current.version, ">1.21.10") -> ".identifier("
        else -> ".location("
    }

    swaps["villagerProfession"] = when {
        eval(current.version, ">=1.21.5") -> "ResourceKey<VillagerProfession>"
        else -> "VillagerProfession"
    }

    replacements {
        string {
            direction = eval(current.version, ">26.1")
            replace("net.minecraft.advancements.CriteriaTriggers", "net.minecraft.advancements.triggers.CriteriaTriggers")
            replace("net.minecraft.advancements.criterion.InventoryChangeTrigger", "net.minecraft.advancements.triggers.InventoryChangeTrigger")
            replace("net.minecraft.advancements.Criterion", "net.minecraft.advancements.triggers.Criterion")
            replace("net.minecraft.advancements.criterion.ItemPredicate", "net.minecraft.advancements.predicates.ItemPredicate")
        }

        string {
            direction = eval(current.version, ">1.21.10")
            replace("net.minecraft.advancements.critereon.ItemPredicate", "net.minecraft.advancements.criterion.ItemPredicate")
            replace("net.minecraft.advancements.critereon.InventoryChangeTrigger", "net.minecraft.advancements.criterion.InventoryChangeTrigger")
        }

        string {
            direction = afterDeobf
            replace("org.jetbrains.annotations.NotNull", "org.jspecify.annotations.NonNull")
        }

        string {
            direction = afterDeobf
            replace("org.jetbrains.annotations.Nullable", "org.jspecify.annotations.Nullable")
        }

        string {
            direction = afterDeobf
            replace("@NotNull", "@NonNull")
        }

        string {
            direction = afterDeobf
            replace("FluidRenderHandlerRegistry.INSTANCE.", "FluidRenderingRegistry.")
        }

        string {
            direction = afterDeobf
            replace("FabricDataOutput", "FabricPackOutput")
        }

        string {
            direction = afterDeobf
            replace("FabricBlockLootTableProvider", "FabricBlockLootSubProvider")
        }

        string {
            direction = afterDeobf
            replace("FabricTagProvider", "FabricTagsProvider")
        }

        string {
            direction = afterDeobf
            replace(".ItemTagProvider", ".ItemTagsProvider")
        }

        string {
            direction = afterDeobf
            replace(".BlockTagProvider", ".BlockTagsProvider")
        }

        string {
            direction = afterDeobf
            replace("BlockRenderLayerMap", "ChunkSectionLayerMap")
        }

        string {
            direction = afterDeobf
            replace("dataOutput", "packOutput")
        }

        string {
            direction = afterDeobf
            replace("inputs, category, ", "inputs, category, CookingBookCategory.BLOCKS, ")
        }

        string {
            // After 1.21.11
            direction = afterDeobf
            replace("level.random", "level.getRandom()")
        }

        string {
            direction = eval(current.version, ">1.21.10")
            replace("RenderType", "RenderTypes")
            replace("net.minecraft.client.renderer.RenderType", "net.minecraft.client.renderer.rendertype.RenderTypes")
        }

        string {
            direction = eval(current.version, "<=1.19.4")
            replace("MapColor", "MaterialColor")
        }

        string {
            direction = eval(current.version, "<=1.19.4")
            replace(".pushReaction(PushReaction.DESTROY)", "/*Removed push reaction*/")
        }

        string {
            direction = eval(current.version, "<=1.19.4")
            replace(").mapColor(", "net.minecraft.world.level.material.Material.STONE, ")
        }

        string {
            direction = eval(current.version, "<=1.20.1")
            replace("AdvancementType", "FrameType")
        }

        string {
            direction = eval(current.version, "<=1.20.1")
            replace("net.minecraft.advancements.AdvancementType", "net.minecraft.advancements.FrameType")
        }

        string {
            direction = eval(current.version, ">1.21.10")
            replace("ResourceLocation", "Identifier")
            replace("getIdentifier()", "getIdentifier()") // Don't replace it
        }

        string {
            direction = eval(current.version, ">1.21.10")
            replace("net.minecraft.Util", "net.minecraft.util.Util")
        }

        string {
            direction = eval(current.version, ">1.21.10")
            replace("critereon", "criterion")
        }

        string {
            direction = eval(current.version, ">1.21.10")
            replace("entity, random", "level, entity, random")
        }

        string {
            direction = eval(current.version, ">1.21.10")
            replace("net.minecraft.world.entity.npc", "net.minecraft.world.entity.npc.villager")
        }

        string {
            direction = eval(current.version, ">=1.21.9")
            replace(".noCollission()", ".noCollision()")
        }

        string {
            direction = eval(current.version, "=1.21.3")
            replace("net.minecraft.client.resources.model.EquipmentClientInfo", "net.minecraft.world.item.equipment.EquipmentModel")
        }

        string {
            direction = eval(current.version, "<=1.21.3")
            replace("net.minecraft.client.data.models", "net.minecraft.data.models")
        }

        for (cls in listOf(
            Pair("EquipmentClientInfo", Pair("EquipmentModel", "=1.21.3")),
            Pair("KeyTagProvider", Pair("TagsProvider", ">26.1"))
        )) {
            string {
                direction = eval(node.metadata.version, cls.second.second)
                replace("${cls.first};", "${cls.second.first};")
            }

            string {
                direction = eval(node.metadata.version, cls.second.second)
                replace("${cls.first} ", "${cls.second.first} ")
            }

            string {
                direction = eval(node.metadata.version, cls.second.second)
                replace("${cls.first}>", "${cls.second.first}>")
            }

            string {
                direction = eval(node.metadata.version, cls.second.second)
                replace("${cls.first}.", "${cls.second.first}.")
            }
        }

        string {
            direction = eval(current.version, "<=1.20.4")
            replace("BootstrapContext", "BootstapContext")
        }

        string {
            direction = eval(current.version, "<=1.21")
            replace("ToolMaterial ", "Tier ")
        }

        string {
            direction = eval(current.version, "<=1.21")
            replace("net.minecraft.world.item.equipment.ArmorMaterial", "net.minecraft.world.item.ArmorMaterial")
        }

        string {
            direction = eval(current.version, "<=1.21")
            replace("ToolMaterial;", "Tier;")
        }

        string {
            direction = eval(current.version, "<=1.21.3")
            replace("net.minecraft.client.data.models.MultiVariant", "net.minecraft.client.renderer.block.model.MultiVariant")
        }

        for (cls in listOf("TagRegistration")) {
            string {
                direction = eval(node.metadata.version, "<=1.20.4")
                replace(
                    "net.fabricmc.fabric.impl.tag.convention.v2.$cls;",
                    "net.fabricmc.fabric.impl.tag.convention.$cls;"
                )
            }
        }

        for (cls in listOf("ConventionalItemTags", "ConventionalBlockTags")) {
            string {
                direction = eval(node.metadata.version, "<=1.20.4")
                replace(
                    "net.fabricmc.fabric.api.tag.convention.v2.$cls;",
                    "net.fabricmc.fabric.api.tag.convention.v1.$cls;"
                )
            }
        }

        for (cls in listOf("FabricModelProvider")) {
            string {
                direction = eval(node.metadata.version, ">=1.21.5")
                replace(
                    "import net.fabricmc.fabric.api.datagen.v1.provider.$cls;",
                    "import net.fabricmc.fabric.api.client.datagen.v1.provider.$cls;"
                )
            }
        }

        string {
            direction = eval(node.metadata.version, "<=1.21")
            replace(
                "import net.minecraft.item.equipment.ArmorMaterial;",
                "import net.minecraft.item.ArmorMaterial;"
            )
        }

        string {
            direction = eval(node.metadata.version, "<=1.20.1")
            replace(
                "RecipeOutput ",
                "java.util.function.Consumer<net.minecraft.data.recipes.FinishedRecipe> "
            )
        }

        string {
            direction = eval(node.metadata.version, "<=1.20.1")
            replace(
                "de.cech12.bucketlib",
                "com.github.cech12.BucketLib"
            )
        }
    }
}
