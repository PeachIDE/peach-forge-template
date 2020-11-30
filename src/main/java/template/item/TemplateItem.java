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
        setRegistryName(Markers.$string("metadata.id + ':' + item.registerName"));
        setTranslationKey(Markers.$string("metadata.id + '.' + item.registerName"));
        setCreativeTab(Markers.$getStaticField("let ITEM_GROUP_CLASS = 'Lnet/minecraft/creativetab/CreativeTabs;';" +
                "return newField(ITEM_GROUPS_CLASS, str.toUpperCase(item.itemGroup), ITEM_GROUP_CLASS);"));
        setMaxStackSize(Markers.$int("item.maxStackSize"));
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean hasEffect(ItemStack stack) {
        return Markers.$bool("item.hasEffect");
    }
}
