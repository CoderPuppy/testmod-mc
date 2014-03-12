package cpup.mc.testMod.content

import net.minecraft.item.{Item, ItemSword}
import net.minecraft.item.Item.ToolMaterial
import com.google.common.collect.{HashMultimap, Multimap}
import net.minecraft.entity.SharedMonsterAttributes
import net.minecraft.entity.ai.attributes.AttributeModifier
import net.minecraft.init.Items

class ItemDagger extends ItemSword(ToolMaterial.EMERALD) with TItemBase {
	override def getItemAttributeModifiers = {
		val attributes = HashMultimap.create.asInstanceOf[Multimap[String, AttributeModifier]]
		attributes.put(SharedMonsterAttributes.attackDamage.getAttributeUnlocalizedName, new AttributeModifier(Item.field_111210_e, "Weapon modifier", 7, 0))
		attributes
	}
}