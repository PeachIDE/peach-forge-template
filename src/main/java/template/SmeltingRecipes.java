package template;

import com.github.mouse0w0.coffeemaker.template.Markers;
import com.github.mouse0w0.coffeemaker.template.ModifySource;
import com.github.mouse0w0.coffeemaker.template.TemplateClass;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.common.registry.GameRegistry;

@TemplateClass
@ModifySource(sourceFile = "Peach.generated")
public class SmeltingRecipes {
    public static void init() {
        Markers.$foreach("recipes", "recipe");
        addSmelting(Markers.$string("recipe.input.id"), Markers.$int("recipe.input.metadata"), Markers.$string("recipe.output.item.id"), Markers.$int("recipe.output.amount"), Markers.$int("recipe.output.item.metadata"), Markers.$int("recipe.xp"));
        Markers.$endForeach();
    }

    private static void addSmelting(String input, int inputMetadata, String output, int outputAmount, int outputMetadata, float xp) {
        GameRegistry.addSmelting(
                new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation(input)), 1, inputMetadata),
                new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation(output)), outputAmount, outputMetadata),
                xp);
    }
}
