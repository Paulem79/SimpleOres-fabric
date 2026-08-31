package net.paulem.simpleores.items.custom.bows;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;

import java.util.Random;

//? if >1.21 {
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import net.paulem.simpleores.items.ModItems;
//?} else if 1.20.6 || 1.21 {
/*import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import net.minecraft.world.item.crafting.Ingredient;
import net.paulem.simpleores.items.ModItems;
*///?} else {
/*import net.minecraft.world.item.crafting.Ingredient;
import net.paulem.simpleores.tags.ModTags;
import java.util.HashMap;
import java.util.Map;
*///?}

public class MythrilBow extends CustomBow
{
    private static final int EFFICIENCY = 50;
    private final Random rng;

    public MythrilBow(Properties builder)
    {
        //? if >1.21 {
        super(builder
                .enchantable(1)
                .repairable(ModItems.MYTHRIL_ROD)
        );
        //?} else {
        /*super(builder);
        *///?}
        rng = new Random();
    }

    @Override
    public void appendClientTooltip(ItemStack stack, TooltipAccept tooltips) {
        tooltips.accept(Component.translatable("tips.damage_tooltip").withStyle(ChatFormatting.GREEN));
        tooltips.accept(Component.translatable("tips.efficiency_tooltip").withStyle(ChatFormatting.GREEN));
    }

    @Override
    public //? if >1.21 {
    boolean
    //?} else {
    /*void
    *///?}
    releaseUsing(ItemStack stack, Level worldIn, LivingEntity entityLiving, int timeLeft) {
        // add the default enchantments for Mythril bow.
        //? if >=1.20.6 {
        ItemEnchantments oldEnchants = EnchantmentHelper.getEnchantmentsForCrafting(stack);
        stack = this.addMythrilEnchantments(oldEnchants, stack, worldIn);
        //?} else {
        /*Map<Enchantment, Integer> oldEnchants = EnchantmentHelper.getEnchantments(stack);
        stack = this.addMythrilEnchantments(oldEnchants, stack);
        *///?}

        //? if >1.21 {
        boolean stopped = super.releaseUsing(stack, worldIn, entityLiving, timeLeft);
        //?} else {
        /*super.releaseUsing(stack, worldIn, entityLiving, timeLeft);
        *///?}

        // remove temporary intrinsic enchantments.
        //? if >=1.20.6 {
        EnchantmentHelper.setEnchantments(stack, oldEnchants);
        //?} else {
        /*EnchantmentHelper.setEnchantments(oldEnchants, stack);
        *///?}
        //? if >1.21 {
        return stopped;
        //?}
    } // end onPlayerStoppedUsing()

    private //? if >=1.20.6 {
    ItemStack addMythrilEnchantments(ItemEnchantments oldEnch, ItemStack stack, Level worldIn)
    //?} else {
    /*ItemStack addMythrilEnchantments(Map<Enchantment,Integer> oldEnch, ItemStack stack)
    *///?}
    {
        if (stack.isEmpty()) return stack;

        //? if >=1.20.6 {
        ItemEnchantments.Mutable enchMap = new ItemEnchantments.Mutable(oldEnch);

        HolderLookup.RegistryLookup<Enchantment> enchantmentImpl = worldIn.registryAccess().lookupOrThrow(Registries.ENCHANTMENT);

        // add intrinsic POWER enchantment only if bow does not already have
        // one >= 2.
        //? if >1.21 {
        enchMap.upgrade(enchantmentImpl.getOrThrow(Enchantments.POWER), 2);
        //?} else if 1.20.6 || 1.21 {
        /*enchMap.set(getEnchantment(enchantmentImpl, Enchantments.POWER), 2);
        *///?}

        // add intrinsic INFINITY enchantment if RNG <= EFFICIENCY.
        //? if >1.21 {
        if (rng.nextInt(100) < EFFICIENCY) enchMap.upgrade(enchantmentImpl.getOrThrow(Enchantments.INFINITY), 1);
        //?} else if 1.20.6 || 1.21 {
        /*if (rng.nextInt(100) < EFFICIENCY) enchMap.set(getEnchantment(enchantmentImpl, Enchantments.INFINITY), 1);
        *///?}

        // add intrinsic enchantments, if any.
        ItemEnchantments tmpEnchMap = enchMap.toImmutable();
        if (!tmpEnchMap.isEmpty()) {
            EnchantmentHelper.setEnchantments(stack, tmpEnchMap);
        }
        //?} else {
        /*Map<Enchantment,Integer> enchMap = new HashMap<>(oldEnch);

        // add intrinsic POWER enchantment only if bow does not already have
        // one >= 2.
        if (!(enchMap.containsKey(Enchantments.POWER_ARROWS) && enchMap.get(Enchantments.POWER_ARROWS) > 1))
        {
            enchMap.put(Enchantments.POWER_ARROWS, 2);
        }

        // add intrinsic INFINITY enchantment if RNG <= EFFICIENCY.
        if (!enchMap.containsKey(Enchantments.INFINITY_ARROWS))
        {
            if (rng.nextInt(100) < EFFICIENCY) enchMap.put(Enchantments.INFINITY_ARROWS, 1);
        }

        // add intrinsic enchantments, if any.
        if (!enchMap.isEmpty()) {
            EnchantmentHelper.setEnchantments(enchMap, stack);
        }
        *///?}
        return stack;
    } // end addMythrilEnchantments()

    //? if 1.20.6 || 1.21 {
    /*@Override
    public boolean isValidRepairItem(ItemStack pStack, ItemStack pRepairCandidate)
    {
        return this.getRepairIngredient().test(pRepairCandidate) || super.isValidRepairItem(pStack, pRepairCandidate);
    }

    public Ingredient getRepairIngredient()
    {
        return Ingredient.of(ModItems.MYTHRIL_ROD);
    }
    *///?}

    //? if <1.20.6 {
    /*@Override
    public boolean isValidRepairItem(ItemStack pStack, ItemStack pRepairCandidate)
    {
        return this.getRepairIngredient().test(pRepairCandidate) || super.isValidRepairItem(pStack, pRepairCandidate);
    }

    public Ingredient getRepairIngredient()
    {
        return Ingredient.of(ModTags.Items.Conventional.MYTHRIL_RODS);
    }
    *///?}
}  // end class MythrilBow
