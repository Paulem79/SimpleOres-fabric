package net.paulem.simpleores.items.custom;


import net.paulem.simpleores.tooltip.TooltipItem;
//? if >1.21 {
/*import net.paulem.simpleores.items.ModItems;
import net.minecraft.ChatFormatting;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import net.minecraft.world.level.Level;

public class OnyxBow extends BowItem implements TooltipItem
{
    public OnyxBow(Properties builder)
    {
        super(builder
                .enchantable(1)
                .repairable(ModItems.ONYX_ROD)
        );
    }

    @Override
    public void appendClientTooltip(ItemStack stack, TooltipAccept tooltips) {
        tooltips.accept(Component.translatable("tips.damage_tooltip").withStyle(ChatFormatting.GREEN));
        tooltips.accept(Component.translatable("tips.flame_tooltip").withStyle(ChatFormatting.GREEN));
    }

    @Override
    public boolean releaseUsing(ItemStack stack, Level worldIn, LivingEntity entityLiving, int timeLeft)
    {
        // add the default enchantments for Onyx bow.
        ItemEnchantments oldEnchants = EnchantmentHelper.getEnchantmentsForCrafting(stack);
        stack = this.addOnyxEnchantments(oldEnchants, stack, worldIn);

        boolean stopped = super.releaseUsing(stack, worldIn, entityLiving, timeLeft);

        // remove temporary intrinsic enchantments.
        EnchantmentHelper.setEnchantments(stack, oldEnchants);
        return stopped;
    }

    private ItemStack addOnyxEnchantments(ItemEnchantments oldEnch, ItemStack stack, Level worldIn)
    {
        if (stack.isEmpty()) return stack;

        ItemEnchantments.Mutable enchMap = new ItemEnchantments.Mutable(oldEnch);

        HolderLookup.RegistryLookup<Enchantment> enchantmentImpl = worldIn.registryAccess().lookupOrThrow(Registries.ENCHANTMENT);

        // add intrinsic POWER_ARROWS enchantment only if bow does not already have
        // one >= 2.
        enchMap.upgrade(enchantmentImpl.getOrThrow(Enchantments.POWER), 2);
        enchMap.upgrade(enchantmentImpl.getOrThrow(Enchantments.FLAME), 1);

        // add intrinsic enchantments, if any.
        ItemEnchantments tmpEnchMap = enchMap.toImmutable();
        if (!tmpEnchMap.isEmpty()) {
            EnchantmentHelper.setEnchantments(stack, tmpEnchMap);
        }
        return stack;
    } // end addMythrilEnchantments()
}  // end class OnyxBow
*///?} else if 1.21 {

/*import net.paulem.simpleores.items.ModItems;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.core.registries.Registries;
import net.minecraft.core.HolderLookup;
import net.minecraft.network.chat.Component;
import net.minecraft.ChatFormatting;
import net.minecraft.world.level.Level;

public class OnyxBow extends BowItem implements TooltipItem
{
    public OnyxBow(Properties builder)
    {
        super(builder);
    }

    @Override
    public void releaseUsing(ItemStack stack, Level worldIn, LivingEntity entityLiving, int timeLeft)
    {
        // add the default enchantments for Onyx bow.
        ItemEnchantments oldEnchants = EnchantmentHelper.getEnchantmentsForCrafting(stack);
        stack = this.addOnyxEnchantments(oldEnchants, stack, worldIn);

        super.releaseUsing(stack, worldIn, entityLiving, timeLeft);

        // remove temporary intrinsic enchantments.
        EnchantmentHelper.setEnchantments(stack, oldEnchants);
    }

    private ItemStack addOnyxEnchantments(ItemEnchantments oldEnch, ItemStack stack, Level worldIn)
    {
        if (stack.isEmpty()) return stack;

        ItemEnchantments.Mutable enchMap = new ItemEnchantments.Mutable(oldEnch);

        HolderLookup.RegistryLookup<Enchantment> enchantmentImpl = worldIn.registryAccess().lookupOrThrow(Registries.ENCHANTMENT);

        // add intrinsic POWER_ARROWS enchantment only if bow does not already have
        // one >= 2.
        enchMap.set(enchantmentImpl.getOrThrow(Enchantments.POWER), 2);
        enchMap.set(enchantmentImpl.getOrThrow(Enchantments.FLAME), 1);

        // add intrinsic enchantments, if any.
        ItemEnchantments tmpEnchMap = enchMap.toImmutable();
        if (!tmpEnchMap.isEmpty()) {
            EnchantmentHelper.setEnchantments(stack, tmpEnchMap);
        }
        return stack;
    } // end addMythrilEnchantments()

    @Override
    public void appendClientTooltip(ItemStack stack, TooltipAccept tooltips) {
        tooltips.accept(Component.translatable("tips.damage_tooltip").withStyle(ChatFormatting.GREEN));
        tooltips.accept(Component.translatable("tips.flame_tooltip").withStyle(ChatFormatting.GREEN));
    }

    @Override
    public boolean isValidRepairItem(ItemStack pStack, ItemStack pRepairCandidate) {
        return this.getRepairIngredient().test(pRepairCandidate) || super.isValidRepairItem(pStack, pRepairCandidate);
    }

    public Ingredient getRepairIngredient()
    {
        return Ingredient.of(ModItems.ONYX_ROD);
    }

}  // end class OnyxBow

 
*///?} else {

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.network.chat.Component;
import net.minecraft.ChatFormatting;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import net.paulem.simpleores.tags.ModTags;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OnyxBow extends BowItem implements TooltipItem
{
    public OnyxBow(Properties builder)
    {
        super(builder);
    }

    @Override
    public void appendClientTooltip(ItemStack stack, TooltipAccept tooltips) {
        tooltips.accept(Component.translatable("tips.damage_tooltip").withStyle(ChatFormatting.GREEN));
        tooltips.accept(Component.translatable("tips.flame_tooltip").withStyle(ChatFormatting.GREEN));
    }

    @Override
    public void releaseUsing(ItemStack stack, Level worldIn, LivingEntity entityLiving, int timeLeft)
    {
        // add the default enchantments for Onyx bow.
        Map<Enchantment, Integer> enchMap = EnchantmentHelper.getEnchantments(stack);
        stack = this.addOnyxEnchantments(enchMap, stack);

        super.releaseUsing(stack, worldIn, entityLiving, timeLeft);

        // remove temporary intrinsic enchantments.
        EnchantmentHelper.setEnchantments(enchMap, stack);
    }

    private ItemStack addOnyxEnchantments(Map<Enchantment,Integer> oldEnch, ItemStack stack)
    {
        if (stack.isEmpty()) return stack;

        Map<Enchantment,Integer> enchMap = new HashMap<>(oldEnch);

        // add intrinsic POWER_ARROWS enchantment only if bow does not already have
        // one >= 2.
        if (!(enchMap.containsKey(Enchantments.POWER_ARROWS) && enchMap.get(Enchantments.POWER_ARROWS) > 1) )
        {
            enchMap.put(Enchantments.POWER_ARROWS, 2);
        }

        if (!enchMap.containsKey(Enchantments.FLAMING_ARROWS)) enchMap.put(Enchantments.FLAMING_ARROWS, 1);

        // add intrinsic enchantments, if any.
        if (!enchMap.isEmpty()) {
            EnchantmentHelper.setEnchantments(enchMap, stack);
        }
        return stack;
    } // end addMythrilEnchantments()

    @Override
    public boolean isValidRepairItem(ItemStack pStack, ItemStack pRepairCandidate) {
        return this.getRepairIngredient().test(pRepairCandidate) || super.isValidRepairItem(pStack, pRepairCandidate);
    }

    public Ingredient getRepairIngredient()
    {
        return Ingredient.of(ModTags.Items.Conventional.ONYX_RODS);
    }

}  // end class OnyxBow
 
//?}
