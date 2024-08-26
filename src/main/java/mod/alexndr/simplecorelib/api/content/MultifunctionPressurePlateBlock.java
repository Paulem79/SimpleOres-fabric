package mod.alexndr.simplecorelib.api.content;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockSetType;
import net.minecraft.block.BlockState;
import net.minecraft.block.WeightedPressurePlateBlock;
import net.minecraft.client.item.TooltipType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.decoration.ArmorStandEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

import java.util.List;

/**
 * Incorporate both weighted pressure plate semantics and that of stone and wood pressure plates,
 * and another type that only responds to players.
 *
 * @author Sinhika
 *
 */
public class MultifunctionPressurePlateBlock extends WeightedPressurePlateBlock
{
    protected final MultifunctionPressurePlateBlock.Sensitivity sensitivity;
    protected final boolean is_weighted;
    protected final int pressTime;

    /**
     *
     * @param pMaxWeight - 15 * number of entities per signal strength. light weighted plate=15, heavy weighted plate=150.
     * @param pSensitify - @see MultifunctionPressurePlateBlock.Sensitivity
     * @param pPressedTime - game ticks to deactivate when no longer pressed. heavy/light weighted plate=10; stone/wooden plate=20.
     * @param pProperties - usually @code{Block.Properties.of(Material.STONE).noCollission().strength(0.5F).sound(SoundType.STONE)}
     */
    public MultifunctionPressurePlateBlock(int pMaxWeight, MultifunctionPressurePlateBlock.Sensitivity pSensitify,
                                           int pPressedTime, AbstractBlock.Settings pProperties,
                                           BlockSetType pSetType)
    {
        super(pMaxWeight, pSetType, pProperties);
        this.sensitivity = pSensitify;
        this.pressTime = pPressedTime;
        this.is_weighted = List.of(Sensitivity.EVERYTHING_WEIGHTED, Sensitivity.MOBS_WEIGHTED, Sensitivity.PLAYERS_WEIGHTED,
                        Sensitivity.LIVING_WEIGHTED)
                .contains(sensitivity);
    } // end ctor


    @Override
    protected int getTickRate()
    {
        return this.pressTime;
    }


    @Override
    protected int getRedstoneOutput(World pWorld, BlockPos pPos)
    {
        List<? extends Entity> list;
        Box aabb = BOX.offset(pPos);

        switch(this.sensitivity)
        {
            case EVERYTHING:
            case EVERYTHING_WEIGHTED:
                list = pWorld.getOtherEntities(null, aabb);
                break;
            case LIVING:
            case LIVING_WEIGHTED:
                list = pWorld.getEntitiesByClass(LivingEntity.class, aabb, arg0 -> ! (arg0 instanceof ArmorStandEntity));
                break;
            case MOBS:
            case MOBS_WEIGHTED:
                list = pWorld.getNonSpectatingEntities(MobEntity.class, aabb);
                break;
            case PLAYERS:
            case PLAYERS_WEIGHTED:
                list = pWorld.getNonSpectatingEntities(PlayerEntity.class, aabb);
                break;
            default:
                return 0;
        } // end-switch

        if (is_weighted)
        {
            int i = Math.min(list.size(), this.weight);
            if (i > 0)
            {
                float f = (float) i / (float) this.weight;
                return MathHelper.ceil(f * 15.0F);
            }
            else
            {
                return 0;
            }
        }
        else
        {
            if (!list.isEmpty())
            {
                for (Entity entity : list)
                {
                    if (!entity.canAvoidTraps())
                    {
                        return 15;
                    }
                }
            }
            return 0;
        } // end-else
    } // end getSignalStrenth()

    @Override
    protected int getRedstoneOutput(BlockState pState)
    {
        if (is_weighted)
        {
            return pState.get(POWER);
        }
        else {
            return pState.get(POWER) > 0 ? 15 : 0;
        }
    } // end getSignalForState()


    @Override
    public void appendTooltip(ItemStack stack, Item.TooltipContext pContext, List<Text> pTooltip, TooltipType options) {
        super.appendTooltip(stack, pContext, pTooltip, options);
        // end-switch
        String tipKey = switch (this.sensitivity) {
            case EVERYTHING, EVERYTHING_WEIGHTED -> "tips.pressure_plate.everything";
            case LIVING, LIVING_WEIGHTED -> "tips.pressure_plate.living";
            case MOBS, MOBS_WEIGHTED -> "tips.pressure_plate.mobs";
            case PLAYERS, PLAYERS_WEIGHTED -> "tips.pressure_plate.players";
        };

        pTooltip.add(Text.translatable(tipKey).formatted(Formatting.GREEN));
    }



    /**
     * Enum that describes what the pressure-plate is sensitive to. WEIGHTED values also count entities by weight/15 units to
     * determine signal strength. Non-weighted plates are either ON (15) or OFF (0).
     *
     * @author Sinhika
     *
     */
    public enum Sensitivity {
        EVERYTHING,         // any entity whatsoever
        LIVING,             // living entities that are not armorstands.
        MOBS,               // non-player living entities only.
        PLAYERS,            // player entities only.
        EVERYTHING_WEIGHTED,
        LIVING_WEIGHTED,
        MOBS_WEIGHTED,
        PLAYERS_WEIGHTED;
    }
} // end class