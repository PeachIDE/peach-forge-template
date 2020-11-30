package template.itemGroup;

import com.github.mouse0w0.coffeemaker.template.Markers;
import com.github.mouse0w0.coffeemaker.template.ModifySource;
import com.github.mouse0w0.coffeemaker.template.TemplateClass;

@TemplateClass
@ModifySource(sourceFile = "Peach.generated")
public class ModItemGroups {
    public static void init() {
        Markers.$foreach("classes", "class");
        Markers.$new("class");
        Markers.$endForeach();
    }
}
