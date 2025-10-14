package net.paulem.simpleores.items.custom;

import net.paulem.simpleores.tooltip.TooltipItem;

//? if >1.21 {
import net.paulem.simpleores.items.ModItems;
import net.minecraft.component.type.ItemEnchantmentsComponent;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.BowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;

public class OnyxBow extends BowItem implements TooltipItem
{
    public OnyxBow(Settings builder)
    {
        super(builder
                .enchantable(1)
                .repairable(ModItems.ONYX_ROD)
        );
    }

    @Override
    public void appendClientTooltip(ItemStack stack, TooltipAccept tooltips) {
        tooltips.accept(Text.translatable("tips.damage_tooltip").formatted(Formatting.GREEN));
        tooltips.accept(Text.translatable("tips.flame_tooltip").formatted(Formatting.GREEN));
    }

    @Override
    public boolean onStoppedUsing(ItemStack stack, World worldIn, LivingEntity entityLiving, int timeLeft)
    {
        // add the default enchantments for Onyx bow.
        ItemEnchantmentsComponent oldEnchants = EnchantmentHelper.getEnchantments(stack);
        stack = this.addOnyxEnchantments(oldEnchants, stack, worldIn);

        boolean stopped = super.onStoppedUsing(stack, worldIn, entityLiving, timeLeft);

        // remove temporary intrinsic enchantments.
        EnchantmentHelper.set(stack, oldEnchants);
        return stopped;
    }

    private ItemStack addOnyxEnchantments(ItemEnchantmentsComponent oldEnch, ItemStack stack, World worldIn)
    {
        if (stack.isEmpty()) return stack;

        ItemEnchantmentsComponent.Builder enchMap = new ItemEnchantmentsComponent.Builder(oldEnch);

        RegistryWrapper.Impl<Enchantment> enchantmentImpl = worldIn.getRegistryManager().getOrThrow(RegistryKeys.ENCHANTMENT);

        // add intrinsic POWER enchantment only if bow does not already have
        // one >= 2.
        enchMap.add(enchantmentImpl.getOrThrow(Enchantments.POWER), 2);
        enchMap.add(enchantmentImpl.getOrThrow(Enchantments.FLAME), 1);

        // add intrinsic enchantments, if any.
        ItemEnchantmentsComponent tmpEnchMap = enchMap.build();
        if (!tmpEnchMap.isEmpty()) {
            EnchantmentHelper.set(stack, tmpEnchMap);
        }
        return stack;
    } // end addMythrilEnchantments()
}  // end class OnyxBow
//?} else if 1.21 {

/*import net.paulem.simpleores.items.ModItems;
import net.minecraft.component.type.ItemEnchantmentsComponent;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.BowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Ingredient;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;

public class OnyxBow extends BowItem implements TooltipItem
{
    public OnyxBow(Settings builder)
    {
        super(builder);
    }

    @Override
    public void onStoppedUsing(ItemStack stack, World worldIn, LivingEntity entityLiving, int timeLeft)
    {
        // add the default enchantments for Onyx bow.
        ItemEnchantmentsComponent oldEnchants = EnchantmentHelper.getEnchantments(stack);
        stack = this.addOnyxEnchantments(oldEnchants, stack, worldIn);

        super.onStoppedUsing(stack, worldIn, entityLiving, timeLeft);

        // remove temporary intrinsic enchantments.
        EnchantmentHelper.set(stack, oldEnchants);
    }

    private ItemStack addOnyxEnchantments(ItemEnchantmentsComponent oldEnch, ItemStack stack, World worldIn)
    {
        if (stack.isEmpty()) return stack;

        ItemEnchantmentsComponent.Builder enchMap = new ItemEnchantmentsComponent.Builder(oldEnch);

        RegistryWrapper.Impl<Enchantment> enchantmentImpl = worldIn.getRegistryManager().getWrapperOrThrow(RegistryKeys.ENCHANTMENT);

        // add intrinsic POWER enchantment only if bow does not already have
        // one >= 2.
        enchMap.add(enchantmentImpl.getOrThrow(Enchantments.POWER), 2);
        enchMap.add(enchantmentImpl.getOrThrow(Enchantments.FLAME), 1);

        // add intrinsic enchantments, if any.
        ItemEnchantmentsComponent tmpEnchMap = enchMap.build();
        if (!tmpEnchMap.isEmpty()) {
            EnchantmentHelper.set(stack, tmpEnchMap);
        }
        return stack;
    } // end addMythrilEnchantments()

    @Override
    public void appendClientTooltip(ItemStack stack, TooltipAccept tooltips) {
        tooltips.accept(Text.translatable("tips.damage_tooltip").formatted(Formatting.GREEN));
        tooltips.accept(Text.translatable("tips.flame_tooltip").formatted(Formatting.GREEN));
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

 
*///?} else {

/*import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
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
import net.paulem.simpleores.tags.ModTags;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OnyxBow extends BowItem implements TooltipItem
{
    public OnyxBow(Settings builder)
    {
        super(builder);
    }

    @Override
    public void appendClientTooltip(ItemStack stack, TooltipAccept tooltips) {
        tooltips.accept(Text.translatable("tips.damage_tooltip").formatted(Formatting.GREEN));
        tooltips.accept(Text.translatable("tips.flame_tooltip").formatted(Formatting.GREEN));
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

    private ItemStack addOnyxEnchantments(Map<Enchantment,Integer> oldEnch, ItemStack stack)
    {
        if (stack.isEmpty()) return stack;

        Map<Enchantment,Integer> enchMap = new HashMap<>(oldEnch);

        // add intrinsic POWER enchantment only if bow does not already have
        // one >= 2.
        if (!(enchMap.containsKey(Enchantments.POWER) && enchMap.get(Enchantments.POWER) > 1) )
        {
            enchMap.put(Enchantments.POWER, 2);
        }

        if (!enchMap.containsKey(Enchantments.FLAME)) enchMap.put(Enchantments.FLAME, 1);

        // add intrinsic enchantments, if any.
        if (!enchMap.isEmpty()) {
            EnchantmentHelper.set(enchMap, stack);
        }
        return stack;
    } // end addMythrilEnchantments()

    @Override
    public boolean canRepair(ItemStack pStack, ItemStack pRepairCandidate) {
        return this.getRepairIngredient().test(pRepairCandidate) || super.canRepair(pStack, pRepairCandidate);
    }

    public Ingredient getRepairIngredient()
    {
        return Ingredient.fromTag(ModTags.Items.Conventional.ONYX_RODS);
    }

}  // end class OnyxBow
 
*///?}