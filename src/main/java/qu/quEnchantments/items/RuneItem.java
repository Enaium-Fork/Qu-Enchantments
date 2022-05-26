package qu.quEnchantments.items;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import java.util.Map;
import java.util.Random;

public class RuneItem extends Item {
    public RuneItem(Settings settings) {
        super(settings);
    }

    @Override
    public boolean isEnchantable(ItemStack stack) {
        return true;
    }

    @Override
    public int getEnchantability() {
        return 1;
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        if (entity instanceof LivingEntity livingEntity) {
            if (entity.age % 20 == 0 && stack.hasEnchantments()) {
                if ((selected || livingEntity.getOffHandStack() == stack)
                        && !(entity instanceof PlayerEntity player && player.getAbilities().creativeMode)) {
                    if (stack.getDamage() < stack.getMaxDamage()) {
                        Map<Enchantment, Integer> map = EnchantmentHelper.get(stack);
                        map.remove(Enchantments.MENDING);
                        Integer i = map.remove(Enchantments.UNBREAKING);
                        i = i == null ? map.size() : Math.max(1, map.size() - i); // due to the way I made this work, I made my own implementation of Unbreaking.
                        stack.setDamage(Math.min(stack.getDamage() + i, stack.getMaxDamage()));
                        System.out.println(entity.age);
                    }
                } else if (stack.getDamage() > 0) {
                    stack.setDamage(Math.max(0, stack.getDamage() - 1));
                }
            }
        }
    }
}
