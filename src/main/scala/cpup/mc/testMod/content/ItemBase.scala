package cpup.mc.testMod.content

import net.minecraft.item.Item
import cpup.mc.lib.content.CPupItem
import cpup.mc.testMod.{TestMod, TTestMod}

trait TItemBase extends CPupItem[TTestMod] {
	def mod = TestMod
}
class ItemBase extends Item with TItemBase