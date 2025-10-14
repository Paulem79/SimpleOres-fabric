package net.paulem.simpleores.datagen.providers.langs;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.registry.RegistryWrapper;

import java.util.concurrent.CompletableFuture;

public class ChineseSimplifiedLangProvider extends GlobalLangProvider {
    public ChineseSimplifiedLangProvider(FabricDataOutput dataOutput, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
        super(dataOutput, "zh_cn", registryLookup);
    }

    @Override
    public void generateTranslations(TranslationBuilder translationBuilder) {
        super.generateTranslations(translationBuilder);

        add(translationBuilder, "tin_ore", "锡矿石");
        add(translationBuilder, "deepslate_tin_ore", "深层锡矿石");
        add(translationBuilder, "tin_block", "锡块");
        add(translationBuilder, "raw_tin_block", "粗锡块");
        add(translationBuilder, "mythril_ore", "秘银矿石");
        add(translationBuilder, "deepslate_mythril_ore", "深层秘银矿石");
        add(translationBuilder, "mythril_block", "秘银块");
        add(translationBuilder, "raw_mythril_block", "粗秘银块");
        add(translationBuilder, "adamantium_ore", "精金矿石");
        add(translationBuilder, "deepslate_adamantium_ore", "深层精金矿石");
        add(translationBuilder, "adamantium_block", "精金块");
        add(translationBuilder, "raw_adamantium_block", "粗精金块");
        add(translationBuilder, "onyx_ore", "缟玛瑙矿石");
        add(translationBuilder, "onyx_block", "缟玛瑙块");

        add(translationBuilder, "copper_bricks", "铜砖块");
        add(translationBuilder, "tin_bricks", "锡砖块");
        add(translationBuilder, "mythril_bricks", "秘银砖块");
        add(translationBuilder, "adamantium_bricks", "艾德曼砖块");
        add(translationBuilder, "onyx_bricks", "缟玛瑙砖块");

        add(translationBuilder, "copper_brick_stairs", "铜砖楼梯");
        add(translationBuilder, "tin_brick_stairs", "锡砖楼梯");
        add(translationBuilder, "mythril_brick_stairs", "秘银砖楼梯");
        add(translationBuilder, "adamantium_brick_stairs", "精金砖楼梯");
        add(translationBuilder, "onyx_brick_stairs", "缟玛瑙砖楼梯");

        add(translationBuilder, "copper_brick_slab", "铜砖台阶");
        add(translationBuilder, "tin_brick_slab", "锡砖台阶");
        add(translationBuilder, "mythril_brick_slab", "秘银砖台阶");
        add(translationBuilder, "adamantium_brick_slab", "精金砖台阶");
        add(translationBuilder, "onyx_brick_slab", "缟玛瑙砖台阶");

        add(translationBuilder, "copper_bars", "铜栏杆");
        add(translationBuilder, "tin_bars", "锡栏杆");
        add(translationBuilder, "mythril_bars", "秘银栏杆");
        add(translationBuilder, "adamantium_bars", "精金栏杆");
        add(translationBuilder, "onyx_bars", "缟玛瑙栏杆");

        add(translationBuilder, "adamantium_door", "精金门");
        add(translationBuilder, "copper_door", "铜门");
        add(translationBuilder, "mythril_door", "秘银门");
        add(translationBuilder, "onyx_door", "缟玛瑙门");
        add(translationBuilder, "tin_door", "锡门");

        add(translationBuilder, "copper_pressure_plate", "铜压力板");
        add(translationBuilder, "tin_pressure_plate", "锡压力板");
        add(translationBuilder, "mythril_pressure_plate", "秘银压力板");
        add(translationBuilder, "adamantium_pressure_plate", "精金压力板");
        add(translationBuilder, "onyx_pressure_plate", "缟玛瑙压力板");
    }
}
