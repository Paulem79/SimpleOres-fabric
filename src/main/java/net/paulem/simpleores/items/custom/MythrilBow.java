package net.paulem.simpleores.items.custom;

import net.paulem.simpleores.tooltip.TooltipItem;
//? if >1.21 {
/*import net.paulem.simpleores.items.ModItems;
import java.util.Random;
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

public class MythrilBow extends BowItem implements TooltipItem
{
    private static final int EFFICIENCY = 50;
    private final Random rng;

    public MythrilBow(Properties builder)
    {
        super(builder
                .enchantable(1)
                .repairable(ModItems.MYTHRIL_ROD)
        );
        rng = new Random();
    }

    @Override
    public void appendClientTooltip(ItemStack stack, TooltipAccept tooltips) {
        tooltips.accept(Component.translatable("tips.damage_tooltip").withStyle(ChatFormatting.GREEN));
        tooltips.accept(Component.translatable("tips.efficiency_tooltip").withStyle(ChatFormatting.GREEN));
    }

    @Override
    public boolean releaseUsing(ItemStack stack, Level worldIn, LivingEntity entityLiving, int timeLeft) {
        // add the default enchantments for Mythril bow.
        ItemEnchantments oldEnchants = EnchantmentHelper.getEnchantmentsForCrafting(stack);
        stack = this.addMythrilEnchantments(oldEnchants, stack, worldIn);

        boolean stopped = super.releaseUsing(stack, worldIn, entityLiving, timeLeft);

        // remove temporary intrinsic enchantments.
        EnchantmentHelper.setEnchantments(stack, oldEnchants);
        return stopped;
    }// end onPlayerStoppedUsing()

    private ItemStack addMythrilEnchantments(ItemEnchantments oldEnch, ItemStack stack, Level worldIn)
    {
        if (stack.isEmpty()) return stack;

        ItemEnchantments.Mutable enchMap = new ItemEnchantments.Mutable(oldEnch);

        HolderLookup.RegistryLookup<Enchantment> enchantmentImpl = worldIn.registryAccess().lookupOrThrow(Registries.ENCHANTMENT);

        // add intrinsic POWER enchantment only if bow does not already have
        // one >= 2.
        enchMap.upgrade(enchantmentImpl.getOrThrow(Enchantments.POWER), 2);

        // add intrinsic INFINITY enchantment if RNG <= EFFICIENCY.
        if (rng.nextInt(100) < EFFICIENCY) enchMap.upgrade(enchantmentImpl.getOrThrow(Enchantments.INFINITY), 1);

        // add intrinsic enchantments, if any.
        ItemEnchantments tmpEnchMap = enchMap.toImmutable();
        if (!tmpEnchMap.isEmpty()) {
            EnchantmentHelper.setEnchantments(stack, tmpEnchMap);
        }
        return stack;
    } // end addMythrilEnchantments()
}  // end class MythrilBow
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

import java.util.Random;

public class MythrilBow extends BowItem implements TooltipItem
{
    private static final int EFFICIENCY = 50;
    private final Random rng;

    public MythrilBow(Properties builder)
    {
        super(builder);
        rng = new Random();
    }

    @Override
    public void appendClientTooltip(ItemStack stack, TooltipAccept tooltips) {
        tooltips.accept(Component.translatable("tips.damage_tooltip").withStyle(ChatFormatting.GREEN));
        tooltips.accept(Component.translatable("tips.efficiency_tooltip").withStyle(ChatFormatting.GREEN));
    }

    @Override
    public void releaseUsing(ItemStack stack, Level worldIn, LivingEntity entityLiving, int timeLeft) {
        // add the default enchantments for Mythril bow.
        ItemEnchantments oldEnchants = EnchantmentHelper.getEnchantmentsForCrafting(stack);
        stack = this.addMythrilEnchantments(oldEnchants, stack, worldIn);

        super.releaseUsing(stack, worldIn, entityLiving, timeLeft);

        // remove temporary intrinsic enchantments.
        EnchantmentHelper.setEnchantments(stack, oldEnchants);
    }// end onPlayerStoppedUsing()

    private ItemStack addMythrilEnchantments(ItemEnchantments oldEnch, ItemStack stack, Level worldIn)
    {
        if (stack.isEmpty()) return stack;

        ItemEnchantments.Mutable enchMap = new ItemEnchantments.Mutable(oldEnch);

        HolderLookup.RegistryLookup<Enchantment> enchantmentImpl = worldIn.registryAccess().lookupOrThrow(Registries.ENCHANTMENT);

        // add intrinsic POWER enchantment only if bow does not already have
        // one >= 2.
        enchMap.set(enchantmentImpl.getOrThrow(Enchantments.POWER), 2);

        // add intrinsic INFINITY enchantment if RNG <= EFFICIENCY.
        if (rng.nextInt(100) < EFFICIENCY) enchMap.set(enchantmentImpl.getOrThrow(Enchantments.INFINITY), 1);

        // add intrinsic enchantments, if any.
        ItemEnchantments tmpEnchMap = enchMap.toImmutable();
        if (!tmpEnchMap.isEmpty()) {
            EnchantmentHelper.setEnchantments(stack, tmpEnchMap);
        }
        return stack;
    } // end addMythrilEnchantments()

    @Override
    public boolean isValidRepairItem(ItemStack pStack, ItemStack pRepairCandidate)
    {
        return this.getRepairIngredient().test(pRepairCandidate) || super.isValidRepairItem(pStack, pRepairCandidate);
    }

    public Ingredient getRepairIngredient()
    {
        return Ingredient.of(ModItems.MYTHRIL_ROD);
    }
}  // end class MythrilBow
 
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
import java.util.Random;

public class MythrilBow extends BowItem implements TooltipItem
{
    private static final int EFFICIENCY = 50;
    private final Random rng;

    public MythrilBow(Properties builder)
    {
        super(builder);
        rng = new Random();
    }

    @Override
    public void appendClientTooltip(ItemStack stack, TooltipAccept tooltips) {
        tooltips.accept(Component.translatable("tips.damage_tooltip").withStyle(ChatFormatting.GREEN));
        tooltips.accept(Component.translatable("tips.efficiency_tooltip").withStyle(ChatFormatting.GREEN));
    }

    @Override
    public void releaseUsing(ItemStack stack, Level worldIn, LivingEntity entityLiving, int timeLeft)
    {
        // add the default enchantments for Mythril bow.
        Map<Enchantment, Integer> enchMap = EnchantmentHelper.getEnchantments(stack);
        stack = this.addMythrilEnchantments(enchMap, stack);

        super.releaseUsing(stack, worldIn, entityLiving, timeLeft);

        // remove temporary intrinsic enchantments.
        EnchantmentHelper.setEnchantments(enchMap, stack);
    }

    private ItemStack addMythrilEnchantments(Map<Enchantment,Integer> oldEnch, ItemStack stack)
    {
        if (stack.isEmpty()) return stack;

        Map<Enchantment,Integer> enchMap = new HashMap<>(oldEnch);

        // add intrinsic POWER enchantment only if bow does not already have
        // one >= 2.
        if (!(enchMap.containsKey(Enchantments.POWER_ARROWS) && enchMap.get(Enchantments.POWER_ARROWS) > 1) )
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
        return stack;
    } // end addMythrilEnchantments()

    @Override
    public boolean isValidRepairItem(ItemStack pStack, ItemStack pRepairCandidate)
    {
        return this.getRepairIngredient().test(pRepairCandidate) || super.isValidRepairItem(pStack, pRepairCandidate);
    }

    public Ingredient getRepairIngredient()
    {
        return Ingredient.of(ModTags.Items.Conventional.MYTHRIL_RODS);
    }
}  // end class MythrilBow
 
//?}
