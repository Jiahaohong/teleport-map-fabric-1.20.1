package net.yillia.teleportmap.item;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.EmptyMapItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.GlobalPos;
import net.minecraft.world.World;

import java.util.Optional;

public class EmptyRCMapItem extends EmptyMapItem {
    public EmptyRCMapItem(Settings settings) {
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
            Optional<GlobalPos> lastDeathPos = user.getLastDeathPos();
            if (lastDeathPos.isEmpty()) {
                ((ServerPlayerEntity)user).sendMessage(Text.translatable("teleportmap.item.recovery_map.death_point_not_found"), true);
                return TypedActionResult.success(itemstack);
            } else {
                BlockPos blockPos = lastDeathPos.get().getPos();
                ItemStack itemstack1 = new ItemStack(ModItems.RECOVERY_MAP, 1);
                NbtCompound compoundtag = itemstack.hasNbt() ? itemstack.getNbt().copy() : new NbtCompound();
                itemstack1.setNbt(compoundtag);
                ((TPMapItem)itemstack1.getItem()).writeNbt(world.getRegistryKey(), blockPos, compoundtag);
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
}
