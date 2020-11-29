package template.item;

import com.github.mouse0w0.coffeemaker.template.Markers;
import com.github.mouse0w0.coffeemaker.template.ModifySource;
import com.github.mouse0w0.coffeemaker.template.TemplateClass;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@TemplateClass
@ModifySource(sourceFile = "Peach.generated")
public class TemplateItem extends Item {

    public TemplateItem() {
        setRegistryName(Markers.$string("registerName"));
        setTranslationKey(Markers.$string("translationKey"));
        setCreativeTab(Markers.$staticField("itemGroup"));
        setMaxStackSize(Markers.$int("maxStackSize"));
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean hasEffect(ItemStack stack) {
        return Markers.$bool("hasEffect");
    }
}
