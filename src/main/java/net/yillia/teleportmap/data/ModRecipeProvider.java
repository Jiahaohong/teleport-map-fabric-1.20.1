package net.yillia.teleportmap.data;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.data.server.recipe.RecipeJsonProvider;
import net.minecraft.data.server.recipe.ShapelessRecipeJsonBuilder;
import net.minecraft.item.Items;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.util.Identifier;
import net.yillia.teleportmap.item.ModItems;

import java.util.function.Consumer;

public class ModRecipeProvider extends FabricRecipeProvider {
    public ModRecipeProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generate(Consumer<RecipeJsonProvider> exporter) {
        ShapelessRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.EMPTY_TELEPORT_MAP, 1)
                .input(Items.ENDER_PEARL)
                .input(Items.MAP)
                .criterion(hasItem(Items.MAP), conditionsFromItem(Items.MAP))
                .offerTo(exporter, new Identifier(getRecipeName(ModItems.EMPTY_TELEPORT_MAP)));
        ShapelessRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.EMPTY_TELEPORT_MAP, 1)
                .input(Items.ENDER_PEARL)
                .input(ModItems.TELEPORT_MAP)
                .criterion(hasItem(ModItems.TELEPORT_MAP), conditionsFromItem(ModItems.TELEPORT_MAP))
                .offerTo(exporter, new Identifier(ModItems.EMPTY_RECOVERY_MAP.toString()+"_erased"));
        ShapelessRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.EMPTY_RECOVERY_MAP, 1)
                .input(ModItems.EMPTY_TELEPORT_MAP)
                .input(Items.RECOVERY_COMPASS)
                .criterion(hasItem(ModItems.EMPTY_TELEPORT_MAP), conditionsFromItem(ModItems.EMPTY_TELEPORT_MAP))
                .offerTo(exporter, new Identifier(getRecipeName(ModItems.EMPTY_RECOVERY_MAP)));
    }
}
