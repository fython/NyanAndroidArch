package moe.feng.arch.demo.adapter

import moe.feng.common.kt.singleton

class AnotherSimpleCardBinder : SimpleCardBinder() {

    override val text = "Easy to create another simple card."

    companion object {

        val INSTANCE: AnotherSimpleCardBinder by singleton()

    }

}
