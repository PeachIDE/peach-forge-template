package template.itemGroup;

import com.github.mouse0w0.coffeemaker.template.Markers;
import com.github.mouse0w0.coffeemaker.template.ModifyAnnotation;
import com.github.mouse0w0.coffeemaker.template.ModifySource;
import com.github.mouse0w0.coffeemaker.template.TemplateClass;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@TemplateClass
@ModifySource(sourceFile = "Peach.generated")
public class TemplateItemGroup extends CreativeTabs {
    @ModifyAnnotation(type = GameRegistry.ObjectHolder.class, values = {
            @ModifyAnnotation.Value(type = String.class, name = "value", expression = "itemGroup.icon.id")
    })
    private static final Item ICON = null;

    public TemplateItemGroup() {
        super(Markers.$string("itemGroup.registerName"));
    }

    @Override
    public String getTranslationKey() {
        return Markers.$string("'itemGroup.' + metadata.id + '.' + itemGroup.registerName");
    }

    @Override
    public ItemStack createIcon() {
        return new ItemStack(ICON, 1, Markers.$int("itemGroup.icon.metadata"));
    }

    @Override
    public boolean hasSearchBar() {
        return Markers.$bool("itemGroup.hasSearchBar");
    }

    @Override
    @SideOnly(Side.CLIENT)
    public ResourceLocation getBackgroundImage() {
        return new ResourceLocation(Markers.$string("" +
                "let SEARCH_BAR_BACKGROUND = 'textures/gui/container/creative_inventory/tab_item_search.png';" +
                "let DEFAULT_BACKGROUND = 'textures/gui/container/creative_inventory/tab_items.png';" +
                "if (str.length(itemGroup.background) > 0) " +
                "{ " +
                "return itemGroup.background; " +
                "} else {" +
                "return itemGroup.hasSearchBar ? SEARCH_BAR_BACKGROUND : DEFAULT_BACKGROUND;" +
                "}"));
    }
}
