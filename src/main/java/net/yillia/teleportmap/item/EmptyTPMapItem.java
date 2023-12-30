package net.yillia.teleportmap.item;


import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.EmptyMapItem;
import net.minecraft.item.FilledMapItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class EmptyTPMapItem extends EmptyMapItem {
    public EmptyTPMapItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemstack = user.getStackInHand(hand);
        if (world.isClient) {
            return TypedActionResult.success(itemstack);
        } else {
            if (!user.getAbilities().creativeMode) {
                itemstack.decrement(1);
            }

            user.incrementStat(Stats.USED.getOrCreateStat(this));
            user.getWorld().playSoundFromEntity(null, user, SoundEvents.UI_CARTOGRAPHY_TABLE_TAKE_RESULT, user.getSoundCategory(), 1.0f, 1.0f);
            BlockPos blockpos = user.getBlockPos();
            ItemStack itemstack1 = new ItemStack(ModItems.TELEPORT_MAP, 1);
            NbtCompound compoundtag = itemstack.hasNbt() ? itemstack.getNbt().copy() : new NbtCompound();
            itemstack1.setNbt(compoundtag);
            ((TPMapItem)itemstack1.getItem()).writeNbt(world.getRegistryKey(), blockpos, compoundtag);
            if (itemstack.isEmpty()) {
                return TypedActionResult.consume(itemstack1);
            }
            if (!user.getInventory().insertStack(itemstack1.copy())) {
                user.dropItem(itemstack1, false);
            }
            return TypedActionResult.consume(itemstack);
        }
    }
}
