package template.item;

import com.github.mouse0w0.coffeemaker.template.ModifySource;
import com.github.mouse0w0.coffeemaker.template.TemplateClass;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Multimap;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import static com.github.mouse0w0.coffeemaker.template.Markers.*;

@TemplateClass
@ModifySource(sourceFile = "Peach.generated")
public class TemplateSword extends Item {

    private final Multimap<String, AttributeModifier> modifiers;
    private final Set<EnumEnchantmentType> acceptableEnchantments;

    private final Item recipeRemain;
    private final Item repairItem;

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

        $mapVar("ENCHANTMENT_TYPE");
        $mapStaticField("ALL", EnumEnchantmentType.ALL);
        $mapStaticField("ARMOR", EnumEnchantmentType.ARMOR);
        $mapStaticField("ARMOR_FEET", EnumEnchantmentType.ARMOR_FEET);
        $mapStaticField("ARMOR_LEGS", EnumEnchantmentType.ARMOR_LEGS);
        $mapStaticField("ARMOR_CHEST", EnumEnchantmentType.ARMOR_CHEST);
        $mapStaticField("ARMOR_HEAD", EnumEnchantmentType.ARMOR_HEAD);
        $mapStaticField("WEAPON", EnumEnchantmentType.WEAPON);
        $mapStaticField("DIGGER", EnumEnchantmentType.DIGGER);
        $mapStaticField("FISHING_ROD", EnumEnchantmentType.FISHING_ROD);
        $mapStaticField("BREAKABLE", EnumEnchantmentType.BREAKABLE);
        $mapStaticField("BOW", EnumEnchantmentType.BOW);
        $mapStaticField("WEARABLE", EnumEnchantmentType.WEARABLE);
        $mapEnd();
    }

    public TemplateSword() {
        setRegistryName($string("metadata.id + ':' + item.identifier"));
        setTranslationKey($string("metadata.id + '.' + item.identifier"));
        setCreativeTab($getStaticField("" +
                "var Field = Java.type('com.github.mouse0w0.coffeemaker.template.Field');" +
                "new Field(ITEM_GROUPS_CLASS, item.itemGroup.toUpperCase(), ITEM_GROUP_CLASS.getDescriptor());"));
        setMaxStackSize(1);
        setMaxDamage($int("item.durability"));

        recipeRemain = REGISTRY.getObject(new ResourceLocation($string("item.recipeRemain.id")));
        repairItem = REGISTRY.getObject(new ResourceLocation($string("item.repairItem.id")));

        $foreach("item.toolAttributes", "tool");
        setHarvestLevel($string("tool.type"), $int("tool.level"));
        $endForeach();

        HashSet<EnumEnchantmentType> acceptableEnchantments = new HashSet<>();
        $foreach("item.acceptableEnchantments", "type");
        acceptableEnchantments.add($getStaticField("ENCHANTMENT_TYPE[type.name()]"));
        $endForeach();
        this.acceptableEnchantments = ImmutableSet.copyOf(acceptableEnchantments);

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
    }

    public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker) {
        stack.damageItem(1, attacker);
        return true;
    }

    public boolean onBlockDestroyed(ItemStack stack, World worldIn, IBlockState state, BlockPos pos, EntityLivingBase entityLiving) {
        if ((double) state.getBlockHardness(worldIn, pos) != 0.0D) {
            stack.damageItem(2, entityLiving);
        }

        return true;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean hasEffect(ItemStack stack) {
        return $bool("item.hasEffect");
    }

    @Override
    public float getDestroySpeed(ItemStack stack, IBlockState state) {
        if (canHarvestBlock(state)) {
            return $float("item.destroySpeed");
        }
        for (String type : getToolClasses(stack)) {
            if (state.getBlock().isToolEffective(type, state))
                return $float("item.destroySpeed");
        }
        return 1.0F;
    }

    /**
     * When tool level is lower than block harvest level, it be called.
     */
    @Override
    public boolean canHarvestBlock(IBlockState blockIn) {
        return $bool("item.canDestroyAnyBlock");
    }

    @Override
    public int getItemEnchantability() {
        return $int("item.enchantability");
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
    public boolean getIsRepairable(ItemStack toRepair, ItemStack repair) {
        return repair.getItem() == repairItem && repair.getMetadata() == $int("item.repairItem.metadata");
    }

    @Override
    public EntityEquipmentSlot getEquipmentSlot(ItemStack stack) {
        return $getStaticField("EQUIPMENT_SLOT[item.equipmentSlot.name()]");
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
        return acceptableEnchantments.contains(enchantment.type);
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
