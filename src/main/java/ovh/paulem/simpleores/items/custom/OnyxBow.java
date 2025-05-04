package ovh.paulem.simpleores.items.custom;

import ovh.paulem.simpleores.tooltip.TooltipItem;
import ovh.paulem.simpleores.items.ModItems;
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

import java.util.function.Consumer;

/**
 * Custom bow that does extra damage (intrinsic POWER 2 enchantment) and sets
 * things on fire (intrinsic FLAME enchantment).
 */
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
    public void appendClientTooltip(ItemStack stack, TooltipContext context, Consumer<Text> tooltips, TooltipType type) {
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
