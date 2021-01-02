package template.item;

import com.github.mouse0w0.coffeemaker.template.DeclareFieldForeach;
import com.github.mouse0w0.coffeemaker.template.ModifyAnnotation;
import com.github.mouse0w0.coffeemaker.template.ModifySource;
import com.github.mouse0w0.coffeemaker.template.TemplateClass;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;

import static com.github.mouse0w0.coffeemaker.template.Markers.*;

@ModifyAnnotation(type = Mod.EventBusSubscriber.class,
        values =
        @ModifyAnnotation.Value(type = String.class, name = "modid", expression = "metadata.id"))
@TemplateClass
@ModifySource(sourceFile = "Peach.generated")
public class ModItems {

    @DeclareFieldForeach(iterable = "items", variableName = "item")
    public static Item EXAMPLE_ITEM;

    static {
        $foreach("items", "item");
        $setStaticField("item", $new("item.descriptor"));
        $endForeach();
    }

    @SubscribeEvent
    public static void registerItem(RegistryEvent.Register<Item> event) {
        IForgeRegistry<Item> registry = event.getRegistry();
        $foreach("items", "item");
        registry.register($getStaticField("item"));
        $endForeach();
    }
}
