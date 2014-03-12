package cpup.mc.testMod

import cpup.mc.lib.CPupCommonProxy

class CommonProxy extends CPupCommonProxy[TTestMod] {
	def mod = TestMod

	def registerRenderers {}
}