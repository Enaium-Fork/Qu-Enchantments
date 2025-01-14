package qu.quEnchantments.enchantments.armor;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.ShapeContext;
import net.minecraft.enchantment.DepthStriderEnchantment;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import qu.quEnchantments.QuEnchantments;
import qu.quEnchantments.blocks.ModBlocks;
import qu.quEnchantments.enchantments.CorruptedEnchantment;
import qu.quEnchantments.util.config.ModConfig;
import qu.quEnchantments.world.ModWorldEvents;

public class SkywalkerEnchantment extends CorruptedEnchantment {

    private static final ModConfig.SkywalkerOptions CONFIG = QuEnchantments.getConfig().skywalkerOptions;

    public SkywalkerEnchantment(EnchantmentType enchantmentType, Rarity weight, EquipmentSlot... slotTypes) {
        super(enchantmentType, weight, EnchantmentTarget.ARMOR_FEET, slotTypes);
    }

    @Override
    public int getMinPower(int level) {
        return level * 10;
    }

    @Override
    public int getMaxPower(int level) {
        return this.getMinPower(level) + 15;
    }

    @Override
    public int getMaxLevel() {
        return CONFIG.isEnabled ? 2 : 0;
    }

    @Override
    public boolean isAvailableForEnchantedBookOffer() {
        return CONFIG.bookOffer;
    }

    @Override
    public boolean isAvailableForRandomSelection() {
        return CONFIG.randomSelection;
    }

    @Override
    public boolean isAvailableForEnchantingTable() {
        return CONFIG.enchantingTable;
    }

    @Override
    public boolean canAccept(Enchantment other) {
        return !(other instanceof DepthStriderEnchantment) && super.canAccept(other);
    }

    @Override
    public void tickEquippedWhileMoving(LivingEntity entity, BlockPos pos, ItemStack stack, int level) {
        World world;
        final double sinkDistance = 0.875;
        if ((world = entity.getWorld()).isClient || !entity.isOnGround() || !entity.isSneaking()) return;
        BlockState blockState = ModBlocks.CLOUD.getDefaultState();
        int f = Math.min(16, CONFIG.radius);
        BlockPos.Mutable mutable = new BlockPos.Mutable();
        for (BlockPos blockPos2 : BlockPos.iterate(new BlockPos(entity.getBlockX() - f, (int)(entity.getY() - sinkDistance), entity.getBlockZ() - f),
                new BlockPos(entity.getBlockX() + f, (int)(entity.getY() - sinkDistance), entity.getBlockZ() + f))) {

            if (!world.getBlockState(blockPos2).equals(Blocks.AIR.getDefaultState()) ||
                    !blockPos2.isWithinDistance(entity.getPos(), Math.max(f, 1))) continue;

            mutable.set(blockPos2.getX(), blockPos2.getY() + 1, blockPos2.getZ());
            BlockState blockState2 = world.getBlockState(mutable);
            if (!blockState2.isAir() || !blockState.canPlaceAt(world, blockPos2) ||
                    !world.canPlace(blockState, blockPos2, ShapeContext.absent())) continue;

            world.setBlockState(blockPos2, blockState);
            int bl = world.getDimension().ultrawarm() ? 1 : 2;
            int duration = Math.max(1, CONFIG.cloudDuration) * bl * level;
            world.scheduleBlockTick(
                    blockPos2,
                    ModBlocks.CLOUD,
                    MathHelper.nextInt(entity.getRandom(), duration, duration * 2)
            );
            world.syncWorldEvent(ModWorldEvents.CLOUD_BLOCK_CREATION, blockPos2, 0);
        }
    }
}
