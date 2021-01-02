package template.item;

import com.github.mouse0w0.coffeemaker.template.ModifySource;
import com.github.mouse0w0.coffeemaker.template.TemplateClass;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.init.Items;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;
import java.util.UUID;

import static com.github.mouse0w0.coffeemaker.template.Markers.*;

@TemplateClass
@ModifySource(sourceFile = "Peach.generated")
public class TemplateFood extends ItemFood {

    private final Multimap<String, AttributeModifier> modifiers;

    private final Item recipeRemain;
    private final Item foodContainer;

    static {
        $classVar("ITEM_GROUP_CLASS", CreativeTabs.class);

        $mapVar("EQUIPMENT_SLOT");
        $mapStaticField("MAINHAND", EntityEquipmentSlot.MAINHAND);
        $mapStaticField("OFFHAND", EntityEquipmentSlot.OFFHAND);
        $mapStaticField("HEAD", EntityEquipmentSlot.HEAD);
        $mapStaticField("CHEST", EntityEquipmentSlot.CHEST);
        $mapStaticField("LEGS", EntityEquipmentSlot.LEGS);
        $mapStaticField("FEET", EntityEquipmentSlot.FEET);
        $mapEnd();

        $mapVar("USE_ANIMATION");
        $mapStaticField("NONE", EnumAction.NONE);
        $mapStaticField("EAT", EnumAction.EAT);
        $mapStaticField("DRINK", EnumAction.DRINK);
        $mapStaticField("BLOCK", EnumAction.BLOCK);
        $mapStaticField("BOW", EnumAction.BOW);
        $mapEnd();
    }

    public TemplateFood() {
        super($int("item.hunger"), $float("item.saturation"), $bool("item.isWolfFood()"));
        setRegistryName($string("metadata.id + ':' + item.identifier"));
        setTranslationKey($string("metadata.id + '.' + item.identifier"));
        setCreativeTab($getStaticField("" +
                "var Field = Java.type('com.github.mouse0w0.coffeemaker.template.Field');" +
                "new Field(ITEM_GROUPS_CLASS, item.itemGroup.toUpperCase(), ITEM_GROUP_CLASS.getDescriptor());"));
        setMaxStackSize($int("item.maxStackSize"));

        recipeRemain = REGISTRY.getObject(new ResourceLocation($string("item.recipeRemain.id")));
        foodContainer = REGISTRY.getObject(new ResourceLocation($string("item.foodContainer.id")));

        UUID uuid = UUID.randomUUID();
        Multimap<String, AttributeModifier> modifiers = HashMultimap.create();
        $foreach("item.attributeModifiers", "modifier");
        modifiers.put($string("modifier.attribute"),
                new AttributeModifier(uuid,
                        $string("modifier.attribute"),
                        $double("modifier.amount"),
                        $int("modifier.operation.ordinal()")));
        $endForeach();
        this.modifiers = ImmutableMultimap.copyOf(modifiers);

        $if("item.alwaysEdible");
        setAlwaysEdible();
        $endIf();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean hasEffect(ItemStack stack) {
        return $bool("item.hasEffect");
    }

    @Override
    public boolean hasContainerItem(ItemStack stack) {
        return $bool("item.recipeRemain != null && !item.recipeRemain.isAir()");
    }

    @Override
    public ItemStack getContainerItem(ItemStack itemStack) {
        return new ItemStack(recipeRemain, 1, $int("item.recipeRemain.metadata"));
    }

    @Override
    public EntityEquipmentSlot getEquipmentSlot(ItemStack stack) {
        return $getStaticField("EQUIPMENT_SLOT[item.equipmentSlot.name()]");
    }

    @Override
    public EnumAction getItemUseAction(ItemStack stack) {
        return $getStaticField("USE_ANIMATION[item.useAnimation.name()]");
    }

    @Override
    public int getMaxItemUseDuration(ItemStack stack) {
        return $int("item.useDuration");
    }

    @Override
    public ItemStack onItemUseFinish(ItemStack stack, World worldIn, EntityLivingBase entityLiving) {
        ItemStack result = super.onItemUseFinish(stack, worldIn, entityLiving);
        if (foodContainer != null && foodContainer != Items.AIR) {
            result = new ItemStack(foodContainer, 1, $int("item.foodContainer.metadata"));
        }
        return result;
    }

    @Override
    public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        $foreach("" +
                        "'use function';" +
                        "function main() {" +
                        "   var list = new java.util.ArrayList();" +
                        "   if (item.information == null || item.information.isEmpty()) {" +
                        "       return list;" +
                        "   } else {" +
                        "       var prefix = 'item.' + metadata.id + '.' + item.identifier + '.tooltip.';" +
                        "       var length = item.information.split('\\n').length;" +
                        "       for (i = 0; i < length; i++) {" +
                        "           list.add(prefix + i);" +
                        "       }" +
                        "       return list;" +
                        "   }" +
                        "}",
                "s");
        tooltip.add(I18n.format($string("s")));
        $endForeach();
    }

    @Override
    public Multimap<String, AttributeModifier> getAttributeModifiers(EntityEquipmentSlot slot, ItemStack stack) {
        return slot == getEquipmentSlot(stack) ? modifiers : super.getAttributeModifiers(slot, stack);
    }
}
