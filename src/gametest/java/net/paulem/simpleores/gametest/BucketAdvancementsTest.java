package net.paulem.simpleores.gametest;

//? if >=26.2 {
import net.fabricmc.fabric.api.gametest.v1.GameTest;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.advancements.triggers.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.GameType;
import net.paulem.simpleores.items.ModItems;

//? hasBucketlib {
import de.cech12.bucketlib.BucketLibMod;
import de.cech12.bucketlib.item.FluidStorageData;
import de.cech12.bucketlib.util.BucketLibUtil;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidConstants;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.minecraft.world.level.material.Fluids;
//?} else {
/*import net.minecraft.world.item.Items;
import net.paulem.simpleores.items.custom.bucket.CustomBucketItem;
*///?}

/**
 * Checks that the copper bucket completes the vanilla bucket advancements, see ModVanillaAdvancements.
 */
public class BucketAdvancementsTest {

    private static final BlockPos MOB_POS = new BlockPos(1, 1, 1);

    @GameTest
    public void hotStuffWithCopperLavaBucket(GameTestHelper helper) {
        ServerPlayer player = helper.makeMockServerPlayerInLevel();
        AdvancementHolder advancement = advancement(helper, "story/lava_bucket");
        assertNotDone(helper, player, advancement);

        ItemStack lavaBucket = copperBucketWithLava();
        player.getInventory().add(lavaBucket.copy());
        CriteriaTriggers.INVENTORY_CHANGED.trigger(player, player.getInventory(), lavaBucket);

        assertDone(helper, player, advancement);
        helper.succeed();
    }

    @GameTest
    public void hotStuffNotGrantedByCopperWaterBucket(GameTestHelper helper) {
        ServerPlayer player = helper.makeMockServerPlayerInLevel();
        AdvancementHolder advancement = advancement(helper, "story/lava_bucket");

        ItemStack waterBucket = copperBucketWithWater();
        player.getInventory().add(waterBucket.copy());
        CriteriaTriggers.INVENTORY_CHANGED.trigger(player, player.getInventory(), waterBucket);

        assertNotDone(helper, player, advancement);
        helper.succeed();
    }

    @GameTest
    public void tacticalFishingWithCopperBucket(GameTestHelper helper) {
        pickupAndAssert(helper, GameType.SURVIVAL, EntityTypes.PUFFERFISH, "husbandry/tactical_fishing");
    }

    @GameTest
    public void tacticalFishingWithCopperBucketInCreative(GameTestHelper helper) {
        pickupAndAssert(helper, GameType.CREATIVE, EntityTypes.PUFFERFISH, "husbandry/tactical_fishing");
    }

    @GameTest
    public void axolotlInACopperBucket(GameTestHelper helper) {
        pickupAndAssert(helper, GameType.SURVIVAL, EntityTypes.AXOLOTL, "husbandry/axolotl_in_a_bucket");
    }

    @GameTest
    public void tadpoleInACopperBucket(GameTestHelper helper) {
        pickupAndAssert(helper, GameType.SURVIVAL, EntityTypes.TADPOLE, "husbandry/tadpole_in_a_bucket");
    }

    /**
     * Picks the mob up the way a real player does, with a copper bucket of water in the main hand.
     */
    private static void pickupAndAssert(GameTestHelper helper, GameType gameType, EntityType<? extends LivingEntity> type, String path) {
        ServerPlayer player = helper.makeMockServerPlayerInLevel();
        player.setGameMode(gameType);
        AdvancementHolder advancement = advancement(helper, path);
        assertNotDone(helper, player, advancement);

        LivingEntity mob = helper.spawn(type, MOB_POS);
        player.setItemInHand(InteractionHand.MAIN_HAND, copperBucketWithWater());
        player.interactOn(mob, InteractionHand.MAIN_HAND, mob.position());

        helper.assertTrue(mob.isRemoved(), "The mob should have been picked up");
        // In creative, the held bucket is kept and the filled one goes to the inventory
        helper.assertTrue(player.getInventory().contains(stack -> stack.is(ModItems.COPPER_BUCKET) && holdsMob(stack)),
                "The mob should be in a copper bucket");

        assertDone(helper, player, advancement);
        helper.succeed();
    }

    private static boolean holdsMob(ItemStack stack) {
        //? if hasBucketlib {
        return BucketLibUtil.containsEntityType(stack);
        //?} else {
        /*return ((CustomBucketItem) stack.getItem()).holdsEntity(stack);
        *///?}
    }

    private static ItemStack copperBucketWithWater() {
        //? if hasBucketlib {
        return BucketLibUtil.addFluid(new ItemStack(ModItems.COPPER_BUCKET), Fluids.WATER);
        //?} else {
        /*return CustomBucketItem.mix(new ItemStack(Items.WATER_BUCKET), new ItemStack(ModItems.COPPER_BUCKET));
        *///?}
    }

    private static ItemStack copperBucketWithLava() {
        //? if hasBucketlib {
        // Set directly: BucketLib refuses to fill the copper bucket with lava, which is hotter than its cracking temperature
        ItemStack stack = new ItemStack(ModItems.COPPER_BUCKET);
        stack.set(BucketLibMod.STORAGE, new FluidStorageData(FluidVariant.of(Fluids.LAVA), FluidConstants.BUCKET));
        return stack;
        //?} else {
        /*return CustomBucketItem.mix(new ItemStack(Items.LAVA_BUCKET), new ItemStack(ModItems.COPPER_BUCKET));
        *///?}
    }

    private static AdvancementHolder advancement(GameTestHelper helper, String path) {
        AdvancementHolder advancement = helper.getLevel().getServer().getAdvancements().get(Identifier.withDefaultNamespace(path));
        if(advancement == null) helper.fail("Missing advancement minecraft:" + path);
        return advancement;
    }

    private static void assertDone(GameTestHelper helper, ServerPlayer player, AdvancementHolder advancement) {
        helper.assertTrue(player.getAdvancements().getOrStartProgress(advancement).isDone(),
                advancement.id() + " should be done, completed criteria: "
                        + player.getAdvancements().getOrStartProgress(advancement).getCompletedCriteria());
    }

    private static void assertNotDone(GameTestHelper helper, ServerPlayer player, AdvancementHolder advancement) {
        helper.assertTrue(!player.getAdvancements().getOrStartProgress(advancement).isDone(),
                advancement.id() + " should not be done yet");
    }
}
//?} else {
/*// The Fabric Advancement API does not exist on these versions
public class BucketAdvancementsTest {}
*///?}
