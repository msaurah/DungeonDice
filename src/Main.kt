fun main (args: Array<String>) {
    var exit = false
    var heroTeam = createTeam()
    var dungeon = mutableListOf<Enemy>()
    var level = 1

    println("Welcome to Dungeon Dice!")
    terminalMessage("This is your group of heroes:")
    showTeam(heroTeam)

    do {
        var newLevel = level
        terminalMessage("What do you want to do?")
        when (readLine().toString()) {
            "help" -> help()
            "show team" -> showTeam(heroTeam)
            "enter dungeon" -> dungeon = createEnemies(level)
            "attack" -> newLevel = startAttack(heroTeam, dungeon, level)
            "exit" -> exit = true
            else -> terminalMessage("I don't know what is this ¯\\_(ツ)_/¯")
        }
        if (newLevel != level) {
            level = newLevel
            terminalMessage("Level ${level}:")
            showTeam(heroTeam)
            dungeon = createEnemies(newLevel)
        }
    } while (!exit)
}

fun help() {
    println()
    println("List of commands: ")
    println(" * show team")
    println(" * enter dungeon")
    println(" * attack")
    println(" * exit")
}