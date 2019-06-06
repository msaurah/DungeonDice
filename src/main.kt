
fun main (args: Array<String>) {
    var exit = false
    var team = mutableListOf<Hero>()
    var enemies = mutableListOf<Enemy>()
    do {
        println()
        println("What do you want to do?")
        when (readLine().toString()) {
            "help" -> help()
            "roll" -> println(rollDice())
            "hero" -> createHero()
            "create team" -> team = createTeam()
            "show team" -> showTeam(team)
            "enter dungeon" -> enemies = createEnemies()
            "attack" -> startAttack(team, enemies)
            "exit" -> exit = true
            else -> println("I don't know what is this ¯\\_(ツ)_/¯")
        }
    } while (exit == false)
}

fun help() {
    println()
    println("List of commands: ")
    println(" * create team")
    println(" * show team")
    println(" * enter dungeon")
    println(" * attack")
    println(" * exit")
}