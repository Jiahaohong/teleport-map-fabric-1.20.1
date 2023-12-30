package net.yillia.teleportmap.data;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.yillia.teleportmap.item.ModItems;

public class ModLangProvider {
    public static class EN_US extends FabricLanguageProvider {
        public EN_US(FabricDataOutput dataOutput) {
            super(dataOutput, "en_us");
        }

        @Override
        public void generateTranslations(TranslationBuilder translationBuilder) {
            translationBuilder.add("minecraft:overworld", "Overworld");
            translationBuilder.add("minecraft:the_nether", "Nether");
            translationBuilder.add("minecraft:the_end", "End");
            translationBuilder.add(ModItems.EMPTY_TELEPORT_MAP, "Empty Teleport Map");
            translationBuilder.add(ModItems.TELEPORT_MAP, "Teleport Map");
            translationBuilder.add("teleportmap.item.teleport_map.dimension_mismatch", "Dimension Mismatches");
        }
    }

    public static class ZH_CN extends FabricLanguageProvider {
        public ZH_CN(FabricDataOutput dataOutput) {
            super(dataOutput, "zh_cn");
        }

        @Override
        public void generateTranslations(TranslationBuilder translationBuilder) {
            translationBuilder.add("minecraft:overworld", "主世界");
            translationBuilder.add("minecraft:the_nether", "下界");
            translationBuilder.add("minecraft:the_end", "末地");
            translationBuilder.add(ModItems.EMPTY_TELEPORT_MAP, "空传送地图");
            translationBuilder.add(ModItems.TELEPORT_MAP, "传送地图");
            translationBuilder.add("teleportmap.item.teleport_map.dimension_mismatch", "维度不匹配");
        }
    }
}
