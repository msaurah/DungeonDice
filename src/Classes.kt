open class Character (){
    var type: Int = rollDice()
}

class Hero () : Character() {
    fun getType():String {
        return when(this.type) {
            1 -> "Champion"
            2 -> "Wizard"
            3 -> "Warrior"
            4 -> "Sorcerer"
            5 -> "Thief"
            6 -> "Pergamin"
            else -> "none"
        }
    }
}

class Enemy () : Character() {
    fun getType():String {
        return when(this.type) {
            1 -> "Dragon"
            2 -> "Slime"
            3 -> "Troll"
            4 -> "Undead"
            5 -> "Chest"
            6 -> "Potion"
            else -> "none"
        }
    }
}