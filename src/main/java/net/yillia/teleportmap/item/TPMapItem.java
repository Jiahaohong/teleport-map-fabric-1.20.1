package net.yillia.teleportmap.item;

import com.mojang.logging.LogUtils;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtHelper;
import net.minecraft.nbt.NbtOps;
import net.minecraft.registry.RegistryKey;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.GlobalPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;

import java.util.List;
import java.util.Optional;

public class TPMapItem extends Item {
    public TPMapItem(Settings settings) {
        super(settings);
    }

    private static final Logger LOGGER = LogUtils.getLogger();
    public static final String TAG_TPMAP_POS = "TPMapPos";
    public static final String TAG_TPMAP_DIMENSION = "TPMapDimension";

    public void writeNbt(RegistryKey<World> worldKey, BlockPos pos, NbtCompound nbt) {
        nbt.put(TAG_TPMAP_POS, NbtHelper.fromBlockPos(pos));
        World.CODEC.encodeStart(NbtOps.INSTANCE, worldKey).resultOrPartial(LOGGER::error).ifPresent(nbtElement -> nbt.put(TAG_TPMAP_DIMENSION, (NbtElement)nbtElement));
    }

    @Override
    public void appendTooltip(ItemStack pStack, @org.jetbrains.annotations.Nullable World pLevel, List<Text> pTooltipComponents, TooltipContext pIsAdvanced) {
        NbtCompound compoundTag = pStack.getNbt();
        RegistryKey<World> level = getTPMapPosition(compoundTag).getDimension();
        BlockPos blockPos = getTPMapPosition(compoundTag).getPos();
        pTooltipComponents.add(Text.translatable(level.getValue().toString()));
        pTooltipComponents.add(Text.literal(blockPos.toShortString()));
        super.appendTooltip(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }

    public static Optional<RegistryKey<World>> getTPMapDimension(NbtCompound pCompoundTag) {
        return World.CODEC.parse(NbtOps.INSTANCE, pCompoundTag.get(TAG_TPMAP_DIMENSION)).result();
    }

    @Nullable
    public static GlobalPos getTPMapPosition(NbtCompound pCompoundTag) {
        boolean flag = pCompoundTag.contains(TAG_TPMAP_POS);
        boolean flag1 = pCompoundTag.contains(TAG_TPMAP_DIMENSION);
        if (flag && flag1) {
            Optional<RegistryKey<World>> optional = getTPMapDimension(pCompoundTag);
            if (optional.isPresent()) {
                BlockPos blockpos = NbtHelper.toBlockPos(pCompoundTag.getCompound(TAG_TPMAP_POS));
                return GlobalPos.create(optional.get(), blockpos);
            }
        }

        return null;
    }

    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemstack = user.getStackInHand(hand);
        NbtCompound nbt = itemstack.getNbt();
        GlobalPos globalPos = getTPMapPosition(nbt);
        BlockPos blockPos = globalPos.getPos();

        if (world.getRegistryKey() == globalPos.getDimension()) {
            world.playSound(null, user.getX(), user.getY(), user.getZ(), SoundEvents.ENTITY_ENDER_PEARL_THROW, SoundCategory.NEUTRAL, 0.5f, 0.4f / (world.getRandom().nextFloat() * 0.4f + 0.8f));
            user.getItemCooldownManager().set(ModItems.TELEPORT_MAP, 20);
            user.getItemCooldownManager().set(ModItems.RECOVERY_MAP, 20);
            if (user.hasVehicle()) {
                user.stopRiding();
            }
            user.teleport(blockPos.getX()+0.5, blockPos.getY()+1, blockPos.getZ()+0.5, true);
        }
        else {
            if (!world.isClient()) {
                ((ServerPlayerEntity)user).sendMessage(Text.translatable("teleportmap.item.teleport_map.dimension_mismatch"), true);
            }
        }

        return TypedActionResult.success(itemstack, world.isClient());
    }


}
