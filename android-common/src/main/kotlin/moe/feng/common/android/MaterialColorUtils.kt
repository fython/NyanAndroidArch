package moe.feng.common.android

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.content.res.Resources

@SuppressLint("StaticFieldLeak")
object MaterialColorUtils {

    private lateinit var context: Context

    fun init(app: Application) {
        context = app.applicationContext
    }

    operator fun get(colorName: MaterialColor): MaterialColorGetter = MaterialColorGetter(colorName)

    fun getColorId(colorName: MaterialColor, brightness: ColorLevel = ColorLevel.`500`): Int {
        val resourceId = context.resources.getIdentifier(
                "material_${colorName.value}_${brightness.value}",
                "color",
                context.packageName
        )
        return resourceId
    }

    fun getColorInt(colorName: MaterialColor, brightness: ColorLevel): Int {
        return try {
            context.resources.getColor(getColorId(colorName, brightness))
        } catch (e: Resources.NotFoundException) {
            0x00000000
        }
    }

    fun parseIdToColor(id: Int): MaterialColor {
        MaterialColor.values().forEach { color ->
            ColorLevel.values().forEach { brightness ->
                if (getColorId(color, brightness) == id) {
                    return color
                }
            }
        }
        throw IllegalArgumentException("Unsupported color resource id.")
    }

    fun parseStringToColor(value: String): MaterialColor = MaterialColor.values().find { it.value == value }!!

    class MaterialColorGetter internal constructor(private val colorName: MaterialColor) {
        operator fun get(brightness: ColorLevel): Int = getColorInt(colorName, brightness)
    }

}

enum class MaterialColor(val value: String) {
    Amber("amber"), Blue("blue"), Brown("brown"), BlueGrey("blue_grey"), Cyan("cyan"),
    DeepOrange("deep_orange"), DeepPurple("deep_purple"), Green("green"), Grey("grey"),
    Indigo("indigo"), Lime("lime"), LightBlue("light_blue"), LightGreen("light_green"),
    Orange("orange"), Purple("purple"), Pink("pink"), Red("red"), Teal("teal"), Yellow("yellow")
}

enum class ColorLevel(val value: String) {
    A100("A100"), A200("A200"), A400("A400"), A700("A700"), `50`("50"),
    `100`("100"), `200`("200"), `300`("300"), `400`("400"),
    `500`("500"), `600`("600"), `700`("700"), `800`("800"), `900`("900");

    infix fun of(colorName: MaterialColor): Int = MaterialColorUtils.getColorInt(colorName, this)
}