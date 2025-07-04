package ovh.paulem.simpleores.items.custom;

import ovh.paulem.simpleores.tooltip.TooltipItem;

//? if >1.21 {
import ovh.paulem.simpleores.items.ModItems;
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

import java.util.Random;

public class MythrilBow extends BowItem implements TooltipItem
{
    private static final int EFFICIENCY = 50;
    private final Random rng;

    public MythrilBow(Settings builder)
    {
        super(builder
                .enchantable(1)
                .repairable(ModItems.MYTHRIL_ROD)
        );
        rng = new Random();
    }

    @Override
    public void appendClientTooltip(ItemStack stack, TooltipAccept tooltips) {
        tooltips.accept(Text.translatable("tips.damage_tooltip").formatted(Formatting.GREEN));
        tooltips.accept(Text.translatable("tips.efficiency_tooltip").formatted(Formatting.GREEN));
    }

    @Override
    public boolean onStoppedUsing(ItemStack stack, World worldIn, LivingEntity entityLiving, int timeLeft) {
        // add the default enchantments for Mythril bow.
        ItemEnchantmentsComponent oldEnchants = EnchantmentHelper.getEnchantments(stack);
        stack = this.addMythrilEnchantments(oldEnchants, stack, worldIn);

        boolean stopped = super.onStoppedUsing(stack, worldIn, entityLiving, timeLeft);

        // remove temporary intrinsic enchantments.
        EnchantmentHelper.set(stack, oldEnchants);
        return stopped;
    }// end onPlayerStoppedUsing()

    private ItemStack addMythrilEnchantments(ItemEnchantmentsComponent oldEnch, ItemStack stack, World worldIn)
    {
        if (stack.isEmpty()) return stack;

        ItemEnchantmentsComponent.Builder enchMap = new ItemEnchantmentsComponent.Builder(oldEnch);

        RegistryWrapper.Impl<Enchantment> enchantmentImpl = worldIn.getRegistryManager().getOrThrow(RegistryKeys.ENCHANTMENT);

        // add intrinsic POWER enchantment only if bow does not already have
        // one >= 2.
        enchMap.add(enchantmentImpl.getOrThrow(Enchantments.POWER), 2);

        // add intrinsic INFINITY enchantment if RNG <= EFFICIENCY.
        if (rng.nextInt(100) < EFFICIENCY) enchMap.add(enchantmentImpl.getOrThrow(Enchantments.INFINITY), 1);

        // add intrinsic enchantments, if any.
        ItemEnchantmentsComponent tmpEnchMap = enchMap.build();
        if (!tmpEnchMap.isEmpty()) {
            EnchantmentHelper.set(stack, tmpEnchMap);
        }
        return stack;
    } // end addMythrilEnchantments()
}  // end class MythrilBow
//?} else if 1.21 {

/*import ovh.paulem.simpleores.items.ModItems;
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

import java.util.Random;

public class MythrilBow extends BowItem implements TooltipItem
{
    private static final int EFFICIENCY = 50;
    private final Random rng;

    public MythrilBow(Settings builder)
    {
        super(builder);
        rng = new Random();
    }

    @Override
    public void appendClientTooltip(ItemStack stack, TooltipAccept tooltips) {
        tooltips.accept(Text.translatable("tips.damage_tooltip").formatted(Formatting.GREEN));
        tooltips.accept(Text.translatable("tips.efficiency_tooltip").formatted(Formatting.GREEN));
    }

    @Override
    public void onStoppedUsing(ItemStack stack, World worldIn, LivingEntity entityLiving, int timeLeft) {
        // add the default enchantments for Mythril bow.
        ItemEnchantmentsComponent oldEnchants = EnchantmentHelper.getEnchantments(stack);
        stack = this.addMythrilEnchantments(oldEnchants, stack, worldIn);

        super.onStoppedUsing(stack, worldIn, entityLiving, timeLeft);

        // remove temporary intrinsic enchantments.
        EnchantmentHelper.set(stack, oldEnchants);
    }// end onPlayerStoppedUsing()

    private ItemStack addMythrilEnchantments(ItemEnchantmentsComponent oldEnch, ItemStack stack, World worldIn)
    {
        if (stack.isEmpty()) return stack;

        ItemEnchantmentsComponent.Builder enchMap = new ItemEnchantmentsComponent.Builder(oldEnch);

        RegistryWrapper.Impl<Enchantment> enchantmentImpl = worldIn.getRegistryManager().getWrapperOrThrow(RegistryKeys.ENCHANTMENT);

        // add intrinsic POWER enchantment only if bow does not already have
        // one >= 2.
        enchMap.add(enchantmentImpl.getOrThrow(Enchantments.POWER), 2);

        // add intrinsic INFINITY enchantment if RNG <= EFFICIENCY.
        if (rng.nextInt(100) < EFFICIENCY) enchMap.add(enchantmentImpl.getOrThrow(Enchantments.INFINITY), 1);

        // add intrinsic enchantments, if any.
        ItemEnchantmentsComponent tmpEnchMap = enchMap.build();
        if (!tmpEnchMap.isEmpty()) {
            EnchantmentHelper.set(stack, tmpEnchMap);
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
import ovh.paulem.simpleores.tags.ModTags;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class MythrilBow extends BowItem implements TooltipItem
{
    private static final int EFFICIENCY = 50;
    private final Random rng;

    public MythrilBow(Settings builder)
    {
        super(builder);
        rng = new Random();
    }

    @Override
    public void appendClientTooltip(ItemStack stack, TooltipAccept tooltips) {
        tooltips.accept(Text.translatable("tips.damage_tooltip").formatted(Formatting.GREEN));
        tooltips.accept(Text.translatable("tips.efficiency_tooltip").formatted(Formatting.GREEN));
    }

    @Override
    public void onStoppedUsing(ItemStack stack, World worldIn, LivingEntity entityLiving, int timeLeft)
    {
        // add the default enchantments for Mythril bow.
        Map<Enchantment, Integer> enchMap = EnchantmentHelper.get(stack);
        stack = this.addMythrilEnchantments(enchMap, stack);

        super.onStoppedUsing(stack, worldIn, entityLiving, timeLeft);

        // remove temporary intrinsic enchantments.
        EnchantmentHelper.set(enchMap, stack);
    }

    private ItemStack addMythrilEnchantments(Map<Enchantment,Integer> oldEnch, ItemStack stack)
    {
        if (stack.isEmpty()) return stack;

        Map<Enchantment,Integer> enchMap = new HashMap<>(oldEnch);

        // add intrinsic POWER enchantment only if bow does not already have
        // one >= 2.
        if (!(enchMap.containsKey(Enchantments.POWER) && enchMap.get(Enchantments.POWER) > 1) )
        {
            enchMap.put(Enchantments.POWER, 2);
        }

        // add intrinsic INFINITY enchantment if RNG <= EFFICIENCY.
        if (!enchMap.containsKey(Enchantments.INFINITY))
        {
            if (rng.nextInt(100) < EFFICIENCY) enchMap.put(Enchantments.INFINITY, 1);
        }

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
        return Ingredient.fromTag(ModTags.Items.Conventional.MYTHRIL_RODS);
    }
}  // end class MythrilBow
 
*///?}