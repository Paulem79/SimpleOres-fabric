package io.github.paulem.simpleores.items.custom;

import io.github.paulem.simpleores.items.ModItems;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.BowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Ingredient;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 *  A bow with some special features: Efficiency, which makes it act like an
 *  INFINITY bow sometimes, and extra damage (equivalent to POWER 2).
 */
public class MythrilBow extends BowItem
{
    private static final int EFFICIENCY = 50;
    private final Random rng;

    public MythrilBow(Settings builder)
    {
        super(builder);
        rng = new Random();
    }

    @Override
    @Environment(EnvType.CLIENT)
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        super.appendTooltip(stack, world, tooltip, context);
        tooltip.add(Text.translatable("tips.damage_tooltip").formatted(Formatting.GREEN));
        tooltip.add(Text.translatable("tips.efficiency_tooltip").formatted(Formatting.GREEN));
    }

    @Override
    public void onStoppedUsing(ItemStack stack, World worldIn, LivingEntity entityLiving, int timeLeft) {
        // add the default enchantments for Mythril bow.
        Map<Enchantment, Integer> enchMap = EnchantmentHelper.get(stack);
        stack = this.addMythrilEnchantments(enchMap, stack);

        super.onStoppedUsing(stack, worldIn, entityLiving, timeLeft);

        // remove temporary intrinsic enchantments.
        EnchantmentHelper.set(enchMap, stack);
    }// end onPlayerStoppedUsing()

    private ItemStack addMythrilEnchantments(Map<Enchantment, Integer> enchMap, ItemStack stack)
    {
        if (stack.isEmpty()) return stack;

        // add intrinsic POWER enchantment only if bow does not already have
        // one >= 2.
        enchMap.put(Enchantments.POWER, 2);

        // add intrinsic INFINITY enchantment if RNG <= EFFICIENCY.
        if (rng.nextInt(100) < EFFICIENCY) enchMap.put(Enchantments.INFINITY, 1);

        // add intrinsic enchantments, if any.
        if (!enchMap.isEmpty()) {
            EnchantmentHelper.set(enchMap, stack);
        }
        return stack;
    } // end addMythrilEnchantments()

    @Override
    public boolean canRepair(ItemStack pStack, ItemStack pRepairCandidate)
    {
        return this.getRepairIngredient().test(pRepairCandidate) || super.canRepair(pStack, pRepairCandidate);
    }
    
    public Ingredient getRepairIngredient()
    {
        return Ingredient.ofItems(ModItems.MYTHRIL_ROD);
    }
}  // end class MythrilBow
