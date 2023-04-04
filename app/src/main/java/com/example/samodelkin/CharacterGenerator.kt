package com.example.samodelkin

import java.io.Serializable
import java.net.URL

private const val CHARACTER_DATA_API = "http://chargen-api.herokuapp.com/"

private fun <T> List<T>.rand() = shuffled().first()

private fun Int.roll() = (0 until this)  // высчитывает какое-то свойство игрока
    .map { (1..6).toList().rand() }
    .sum()
    .toString()

private  val firstName = listOf("Eli", "Alex", "Sophie")
private val lastName = listOf("Lightweaver", "Greatfoot", "Oakenfled")

object CharacterGenerator{
    data class CharacterData(val name: String,
                             val race: String,
                             val dex: String,
                             val wis: String,
                             val str: String): Serializable

    private  fun name() = "${firstName.rand()} ${lastName.rand()}"

    private fun race() = listOf("dwarf", "elf", "human", "halfling").rand()

    private fun dexterity() = 4.roll()  // ловкость

    private fun wisdom() = 3.roll() // мудрость

    private fun strength() = 5.roll() // сила

    fun generate() = CharacterData(name = name(),     //генерирует имя и другие своймтва конкретного игрока
                                    race = race(),
                                    dex = dexterity(),
                                    wis = wisdom(),
                                    str = strength())

    fun fromApiData(apiData: String): CharacterData {
        val (race, name, dex, wis, str) = apiData.split(",")
        return CharacterData(name, race, dex, wis, str)
    }

}

fun fetchCharacterData(): CharacterGenerator.CharacterData {
    val apiData = URL(CHARACTER_DATA_API).readText()
    return CharacterGenerator.fromApiData(apiData)
}