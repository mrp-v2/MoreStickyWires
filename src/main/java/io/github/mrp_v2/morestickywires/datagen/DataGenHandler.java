package io.github.mrp_v2.morestickywires.datagen;

import io.github.mrp_v2.morestickywires.MoreStickyWires;
import mrp_v2.mrplibrary.datagen.DataGeneratorHelper;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.GatherDataEvent;

@Mod.EventBusSubscriber(modid = MoreStickyWires.ID, bus = Mod.EventBusSubscriber.Bus.MOD) public class DataGenHandler
{
    @SubscribeEvent public static void gatherData(GatherDataEvent event)
    {
        DataGeneratorHelper helper = new DataGeneratorHelper(event, MoreStickyWires.ID);
        helper.addTextureProvider(TextureGenerator::new);
        helper.addBlockStateProvider(BlockStateGenerator::new);
        helper.addItemModelProvider(ItemModelGenerator::new);
        helper.addLootTables(new LootTables());
        helper.addItemTagsProvider(ItemTagGenerator::new);
        helper.addLanguageProvider(EN_USTranslationGenerator::new);
        helper.addRecipeProvider(RecipeGenerator::new);
    }
}
