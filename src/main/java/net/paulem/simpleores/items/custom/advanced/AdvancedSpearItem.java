package net.paulem.simpleores.items.custom.advanced;

//? if >=1.21.11 {
/*import net.minecraft.world.item.Item;
import net.minecraft.world.item.Tier;
import net.paulem.simpleores.items.ModToolMaterials;

public class AdvancedSpearItem extends Item {
    private Tier material;
    
    public AdvancedSpearItem(Tier material, Properties settings) {
        super(settings.spear(material, getAttackDuration(material), getDamageMultiplier(material), getDelay(material), getDismountTime(material), getDismountThreshold(material), getKnockbackTime(material), getKnockbackThreshold(material), getDamageTime(material), getDamageThreshold(material)));
        
        this.material = material;
    }

    public Tier getMaterial() {
        return material;
    }

    private static float getAttackDuration(Tier material) {
        if(material == ModToolMaterials.TIN) {
            return 0.9F;
        } else if(material == ModToolMaterials.MYTHRIL) {
            return 1F;
        } else if(material == ModToolMaterials.ADAMANTIUM) {
            return 1.1F;
        }
        
        return 1.25F; // For onyx
    }
    
    private static float getDamageMultiplier(Tier material) {
        if(material == ModToolMaterials.TIN) {
            return 0.9F;
        } else if(material == ModToolMaterials.MYTHRIL) {
            return 1F;
        } else if(material == ModToolMaterials.ADAMANTIUM) {
            return 1.1F;
        }
        
        return 1.25F; // For onyx
    }
    
    private static float getDelay(Tier material) {
        if(material == ModToolMaterials.TIN) {
            return 0.6F;
        } else if(material == ModToolMaterials.MYTHRIL) {
            return 0.55F;
        } else if(material == ModToolMaterials.ADAMANTIUM) {
            return 0.45F;
        }
        
        return 0.3F; // For onyx
    }

    private static float getDismountTime(Tier material) {
        if(material == ModToolMaterials.TIN) {
            return 3.75F;
        } else if(material == ModToolMaterials.MYTHRIL) {
            return 2.75F;
        } else if(material == ModToolMaterials.ADAMANTIUM) {
            return 2.25F;
        }
        
        return 2.0F; // For onyx
    }

    private static float getDismountThreshold(Tier material) {
        if(material == ModToolMaterials.TIN) {
            return 8.5F;
        } else if(material == ModToolMaterials.MYTHRIL) {
            return 7.75F;
        } else if(material == ModToolMaterials.ADAMANTIUM) {
            return 7.25F;
        }
        
        return 6.5F; // For onyx
    }

    private static float getKnockbackTime(Tier material) {
        if(material == ModToolMaterials.TIN) {
            return 7.5F;
        } else if(material == ModToolMaterials.MYTHRIL) {
            return 6.6F;
        } else if(material == ModToolMaterials.ADAMANTIUM) {
            return 6.0F;
        }
        
        return 5.0F; // For onyx
    }

    private static float getKnockbackThreshold(Tier material) {
        return 5.1F; // Constant across vanilla tiers
    }

    private static float getDamageTime(Tier material) {
        if(material == ModToolMaterials.TIN) {
            return 11.8F;
        } else if(material == ModToolMaterials.MYTHRIL) {
            return 10.6F;
        } else if(material == ModToolMaterials.ADAMANTIUM) {
            return 9.4F;
        }
        
        return 8.0F; // For onyx
    }

    private static float getDamageThreshold(Tier material) {
        return 4.6F; // Constant across vanilla tiers
    }
}
*///?} else {
public class AdvancedSpearItem {}
//?}