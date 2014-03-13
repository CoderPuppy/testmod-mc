package cpup.mc.testMod

import cpw.mods.fml.common.eventhandler.SubscribeEvent
import net.minecraftforge.event.entity.living.LivingSetAttackTargetEvent
import cpup.mc.testMod.content.ItemRangerArmor
import net.minecraft.entity.EntityLiving
import net.minecraft.item.ItemStack

class Events {
	@SubscribeEvent
	def checkCloak(e: LivingSetAttackTargetEvent) {
		if(e.target == null) {
			return
		}

		val cowl = e.target.getEquipmentInSlot(4) match {
			case stack: ItemStack => stack.getItem.isInstanceOf[ItemRangerArmor]
			case null => false
		}
		val cloak = e.target.getEquipmentInSlot(3) match {
			case stack: ItemStack => stack.getItem.isInstanceOf[ItemRangerArmor]
			case null => false
		}
		val boots = e.target.getEquipmentInSlot(1) match {
			case stack: ItemStack => stack.getItem.isInstanceOf[ItemRangerArmor]
			case null => false
		}

		val sprinting = e.target.isSprinting
		val sneaking = e.target.isSneaking
		val dist = e.target.getDistanceSqToEntity(e.entityLiving)
		val motionY = Math.abs(e.target.motionY)

//		println(sprinting, sneaking)
//		println(cowl, cloak, boots)
//		println(motionY, dist)

		if(!cloak || !cowl) {
			return
		}

		if(dist <= 1) {
			println("far too close")
			return
		}

		if(dist <= 10 && !(boots || sneaking)) {
			println("not sneaking")
			return
		}

		if(dist <= 40 && !boots && sprinting) {
			println("sprinting")
			return
		}

		if(dist <= 40 && !boots && motionY > 1) {
			println("fell")
			return
		}

		e.entityLiving.setRevengeTarget(null)

		if(e.entityLiving.isInstanceOf[EntityLiving]) {
			e.entityLiving.asInstanceOf[EntityLiving].setAttackTarget(null)
		}
	}
}