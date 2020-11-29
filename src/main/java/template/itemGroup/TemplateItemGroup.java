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
            @ModifyAnnotation.Value(type = String.class, name = "value", expression = "icon.id")
    })
    private static final Item ICON = null;

    public TemplateItemGroup() {
        super(Markers.$string("registerName"));
    }

    @Override
    public String getTranslationKey() {
        return Markers.$string("translationKey");
    }

    @Override
    public ItemStack createIcon() {
        return new ItemStack(ICON, 1, Markers.$int("icon.metadata"));
    }

    @Override
    public boolean hasSearchBar() {
        return Markers.$bool("hasSearchBar");
    }

    @Override
    @SideOnly(Side.CLIENT)
    public ResourceLocation getBackgroundImage() {
        return new ResourceLocation(Markers.$string("background"));
    }
}
