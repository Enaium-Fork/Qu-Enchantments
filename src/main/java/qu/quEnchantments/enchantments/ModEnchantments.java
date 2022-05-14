package qu.quEnchantments.enchantments;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import qu.quEnchantments.QuEnchantments;

public class ModEnchantments {
    public static final Enchantment FREEZING_ASPECT = register("freezing_aspect", new FreezingAspectEnchantment(Enchantment.Rarity.RARE, EnchantmentTarget.WEAPON, EquipmentSlot.MAINHAND));
    public static final Enchantment LEECHING_ASPECT = register("leeching_aspect", new LeechingAspectEnchantment(Enchantment.Rarity.RARE, EnchantmentTarget.WEAPON, EquipmentSlot.MAINHAND));
    public static final Enchantment SHAPED_GLASS = register("shaped_glass", new ShapedGlassEnchantment(CorruptedEnchantment.EnchantmentType.DAMAGE, Enchantment.Rarity.VERY_RARE, EnchantmentTarget.WEAPON, EquipmentSlot.MAINHAND));

    public static final Enchantment NIGHTBLOOD = register("nightblood", new NightbloodEnchantment(CorruptedEnchantment.EnchantmentType.ASPECT, Enchantment.Rarity.VERY_RARE, EnchantmentTarget.WEAPON, EquipmentSlot.MAINHAND));

    public static final Enchantment MOLTEN_WALKER = register("molten_walker", new MoltenWalkerEnchantment(Enchantment.Rarity.RARE, EquipmentSlot.FEET));

    public static final Enchantment SKYWALKER = register("skywalker", new SkywalkerEnchantment(CorruptedEnchantment.EnchantmentType.WALKER, Enchantment.Rarity.VERY_RARE, EquipmentSlot.FEET));

    private static Enchantment register(String name, Enchantment enchantment) {
        return Registry.register(Registry.ENCHANTMENT, new Identifier(QuEnchantments.MOD_ID, name), enchantment);
    }

    public static void registerModEnchantments() {
        QuEnchantments.LOGGER.info("Registering ModEnchantments for " + QuEnchantments.MOD_ID);
    }
}