package cpup.mc.testMod

import cpw.mods.fml.common.eventhandler.SubscribeEvent
import net.minecraftforge.event.entity.living.LivingSetAttackTargetEvent
import cpup.mc.testMod.content.ItemCloak
import net.minecraft.entity.EntityLiving

class Events {
	@SubscribeEvent
	def checkCloak(e: LivingSetAttackTargetEvent) {
		if(e.target == null) {
			return
		}

		val dist = e.target.getDistanceSqToEntity(e.entityLiving)
		val chest = e.target.getEquipmentInSlot(3)

		println(e.target.motionX, e.target.motionY, e.target.motionZ)
		println(dist)

		if(chest == null) {
			println("no chestplate")
			return
		}

		if(!chest.getItem.isInstanceOf[ItemCloak]) {
			println("not a cloak")
			return
		}

		if(e.entityLiving.getLastAttacker == e.target && dist < 10) {
			println("attacked")
			return
		}

		if(Math.abs(e.target.motionX) > 0.1 || Math.abs(e.target.motionZ) > 0.1 || Math.abs(e.target.motionY) >= 1) {
			println("moving")
			return
		}

		if(dist <= 1) {
			println("too close")
			return
		}

		e.entityLiving.setRevengeTarget(null)

		if(e.entityLiving.isInstanceOf[EntityLiving]) {
			e.entityLiving.asInstanceOf[EntityLiving].setAttackTarget(null)
		}
	}
}