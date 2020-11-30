package template.client.item;

import com.github.mouse0w0.coffeemaker.template.Markers;
import com.github.mouse0w0.coffeemaker.template.ModifyAnnotation;
import com.github.mouse0w0.coffeemaker.template.ModifySource;
import com.github.mouse0w0.coffeemaker.template.TemplateClass;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

@ModifyAnnotation(type = Mod.EventBusSubscriber.class, values =
@ModifyAnnotation.Value(type = String.class, name = "modid", expression = "metadata.id"))
@Mod.EventBusSubscriber(modid = "examplemod", value = Side.CLIENT)
@TemplateClass
@ModifySource(sourceFile = "Peach.generated")
public class ModItemModels {
    @SubscribeEvent
    public static void registerModel(ModelRegistryEvent event) {
        Markers.$foreach("items", "item");
        registerItemModel(Markers.$getStaticField("item"));
        Markers.$endForeach();
    }

    private static void registerItemModel(Item item) {
        ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(item.getRegistryName(), "inventory"));
    }
}
