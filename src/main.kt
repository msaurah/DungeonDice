
fun main (args: Array<String>) {
    var exit = false
    var team = createTeam()
    var dungeon = mutableListOf<Enemy>()
    var level = 1

    println("Welcome to Dungeon Dice")
    println("This is your group of heroes")
    showTeam(team)

    do {
        var newLevel = level
        println()
        println("What do you want to do?")
        when (readLine().toString()) {
            "help" -> help()
            "create team" -> team = createTeam()
            "show team" -> showTeam(team)
            "enter dungeon" -> dungeon = createEnemies(level)
            "attack" -> newLevel = startAttack(team, dungeon, level)
            "exit" -> exit = true
            else -> println("I don't know what is this ¯\\_(ツ)_/¯")
        }
        if (newLevel != level) {
            println("Next Level")
            dungeon = createEnemies(newLevel)
            level = newLevel
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