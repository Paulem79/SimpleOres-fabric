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

/**
 * Custom bow that does extra damage (intrinsic POWER 2 enchantment) and sets
 * things on fire (intrinsic FLAME enchantment).
 */
public class OnyxBow extends BowItem
{
    public OnyxBow(Settings builder)
    {
        super(builder);
    }

    @Override
    public void onStoppedUsing(ItemStack stack, World worldIn, LivingEntity entityLiving, int timeLeft)
    {
        // add the default enchantments for Onyx bow.
        Map<Enchantment, Integer> enchMap = EnchantmentHelper.get(stack);
        stack = this.addOnyxEnchantments(enchMap, stack);

        super.onStoppedUsing(stack, worldIn, entityLiving, timeLeft);

        // remove temporary intrinsic enchantments.
        EnchantmentHelper.set(enchMap, stack);
    }

    private ItemStack addOnyxEnchantments(Map<Enchantment, Integer> enchMap, ItemStack stack)
    {
        if (stack.isEmpty()) return stack;

        // add intrinsic POWER enchantment only if bow does not already have
        // one >= 2.
        enchMap.put(Enchantments.POWER, 2);
        enchMap.put(Enchantments.FLAME, 1);

        // add intrinsic enchantments, if any.
        EnchantmentHelper.set(enchMap, stack);
        return stack;
    } // end addMythrilEnchantments()

    @Override
    @Environment(EnvType.CLIENT)
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        super.appendTooltip(stack, world, tooltip, context);
        tooltip.add(Text.translatable("tips.damage_tooltip").formatted(Formatting.GREEN));
        tooltip.add(Text.translatable("tips.flame_tooltip").formatted(Formatting.GREEN));
    }

    @Override
    public boolean canRepair(ItemStack pStack, ItemStack pRepairCandidate) {
        return this.getRepairIngredient().test(pRepairCandidate) || super.canRepair(pStack, pRepairCandidate);
    }
    
    public Ingredient getRepairIngredient()
    {
        return Ingredient.ofItems(ModItems.ONYX_ROD);
    }

}  // end class OnyxBow
