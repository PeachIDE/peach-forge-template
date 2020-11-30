package template.init;

import com.github.mouse0w0.coffeemaker.template.DeclareFieldForeach;
import com.github.mouse0w0.coffeemaker.template.Markers;
import com.github.mouse0w0.coffeemaker.template.ModifySource;
import com.github.mouse0w0.coffeemaker.template.TemplateClass;
import net.minecraft.creativetab.CreativeTabs;

@TemplateClass
@ModifySource(sourceFile = "Peach.generated")
public class ItemGroups {

    @DeclareFieldForeach(iterable = "itemGroups", variableName = "itemGroup", expression = "itemGroup.field")
    public static CreativeTabs MISC;

    static {
        Markers.$foreach("itemGroups", "itemGroup");
        Markers.$setStaticField("itemGroup.field", find(Markers.$string("itemGroup.name")));
        Markers.$endForeach();
    }

    private static CreativeTabs find(String name) {
        for (CreativeTabs creativeTabs : CreativeTabs.CREATIVE_TAB_ARRAY) {
            if (name.equals(creativeTabs.getTabLabel())) return creativeTabs;
        }
        return null;
    }
}
