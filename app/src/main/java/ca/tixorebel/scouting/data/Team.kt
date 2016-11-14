package ca.tixorebel.scouting.data

fun validateNumber(number: Int): Boolean { return number <= 99999 }
fun validateLetter(letter: String): Boolean { return (letter.isEmpty()) || (letter.length == 1 && letter[0].isLetter()) }
fun getNumberFromId(id: String): Int { return id.filter(Char::isDigit).toInt() }
fun getLetterFromId(id: String): String { return id.filter(Char::isLetter) }

class Team(val number: Int, val letter: String, val name: String) {
    constructor(id: String, name: String) : this(getNumberFromId(id), getLetterFromId(id), name)
    init {
        if (!validateNumber(number)) throw IllegalArgumentException("Invalid team number '$number'")
        if (!validateLetter(letter)) throw IllegalArgumentException("Invalid team letter '$letter'")
    }

    val id: String get() { return "$number$letter" }
}