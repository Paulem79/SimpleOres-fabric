package ovh.paulem.simpleores.items.custom;

import ovh.paulem.simpleores.items.ModItems;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.component.type.ItemEnchantmentsComponent;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.BowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;

import java.util.List;
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
        super(builder
                .repairable(ModItems.MYTHRIL_ROD)
        );
        rng = new Random();
    }

    @Override
    @Environment(EnvType.CLIENT)
    public void appendTooltip(ItemStack stack, TooltipContext pContext, List<Text> tooltip, TooltipType flagIn) {
        super.appendTooltip(stack, pContext, tooltip, flagIn);
        tooltip.add(Text.translatable("tips.damage_tooltip").formatted(Formatting.GREEN));
        tooltip.add(Text.translatable("tips.efficiency_tooltip").formatted(Formatting.GREEN));
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
