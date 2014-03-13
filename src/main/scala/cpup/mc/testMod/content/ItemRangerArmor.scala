package cpup.mc.testMod.content

import net.minecraft.item.{ItemArmor, ItemStack, Item}
import net.minecraftforge.common.ISpecialArmor
import net.minecraft.entity.{Entity, EntityLivingBase}
import net.minecraft.util.DamageSource
import net.minecraft.entity.player.EntityPlayer
import net.minecraftforge.common.ISpecialArmor.ArmorProperties
import net.minecraft.item.ItemArmor.ArmorMaterial

class ItemRangerArmor(armorType: Int) extends ItemArmor(ArmorMaterial.CHAIN, 0, armorType) with TItemBase with ISpecialArmor {
	override def isValidArmor(stack: ItemStack, armorType: Int, entity: Entity) = armorType == this.armorType

	def damageArmor(entity: EntityLivingBase, stack: ItemStack, source: DamageSource, dmg: Int, slot: Int) {}
	def getArmorDisplay(player: EntityPlayer, stack: ItemStack, slot: Int) = 4
	def getProperties(entity: EntityLivingBase, stack: ItemStack, source: DamageSource, dmg: Double, slot: Int) = {
		if(armorType == 3 && source == DamageSource.fall) {
			new ArmorProperties(0, 2, Int.MaxValue)
		} else {
			new ArmorProperties(0, ArmorMaterial.CLOTH.getDamageReductionAmount(armorType) / 25.0, Int.MaxValue)
		}
	}

	override def getItemEnchantability = 30

	override def getArmorTexture(stack: ItemStack, entity: Entity, slot: Int, layer: String) = {
		mod.ref.modID + ":textures/armors/ranger." + layer + ".png"
	}
}