package template.item;

import com.github.mouse0w0.coffeemaker.template.*;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;

@ModifyAnnotation(type = Mod.EventBusSubscriber.class,
        values =
        @ModifyAnnotation.Value(type = String.class, name = "modid", expression = "metadata.id"))
@TemplateClass
@ModifySource(sourceFile = "Peach.generated")
public class ModItems {

    @DeclareFieldForeach(iterable = "items", variableName = "item")
    public static Item EXAMPLE_ITEM;

    static {
        Markers.$foreach("items", "item");
        Markers.$setStaticField("item", Markers.$new("item.descriptor"));
        Markers.$endForeach();
    }

    @SubscribeEvent
    public static void registerItem(RegistryEvent.Register<Item> event) {
        IForgeRegistry<Item> registry = event.getRegistry();
        Markers.$foreach("items", "item");
        registry.register(Markers.$getStaticField("item"));
        Markers.$endForeach();
    }
}
