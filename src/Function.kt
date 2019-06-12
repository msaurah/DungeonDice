import kotlin.random.Random

private val POTION_TYPE = 6

fun rollDice (sides: Int = 6): Int {
    return if (sides == 0) 0 else Random.nextInt(sides) + 1
}

private fun newHero (team: MutableList<Hero>) {
    team.add(Hero())
    terminalMessage("A ${team.last().getType()} joined the team.")
    team.sortBy { it.type }
}

private fun killHero(heroIndex: Int, heroTeam: MutableList<Hero>){ heroTeam.removeAt(heroIndex) }

fun createTeam(numberMember: Int = 6): MutableList<Hero> {
    val team = mutableListOf<Hero>()
    for (i in 1..numberMember) team.add(Hero())

    //Sort heroes by type
    team.sortBy { it.type }

    return team
}


private fun killEnemy(enemyIndex: Int, enemyTeam: MutableList<Enemy>) { enemyTeam.removeAt(enemyIndex) }

fun createEnemies(levelDungeon: Int = 6): MutableList<Enemy> {
    var team = mutableListOf<Enemy>()
    for (i in 1..levelDungeon) {
        team.add(Enemy())
    }

    //Sort enemies by type
    team.sortBy { it.type }

    showEnemies(team)
    return team
}

fun showTeam(team: List<Hero>) {
    if (team.count() == 0) return terminalMessage("You have no team.")
    //println("Your team:")
    team.forEachIndexed { index, hero -> println("${index}."+hero.getType()) }
}

fun showEnemies(team: List<Enemy>) {
    println()
    if (team.count() == 0) return terminalMessage("Dungeon clear.")
    println("Dungeon:")
    team.forEachIndexed { index, enemy -> println("${index}."+enemy.getType()) }
}

fun startAttack(heroTeam: MutableList<Hero>, enemyTeam: MutableList<Enemy>, level: Int) : Int{
    var newLevel: Int = level
    terminalMessage("Who attacks?")
    val heroIndex: Int = validInt(heroTeam.size)
    terminalMessage("Who is being attacked?")
    val enemyIndex: Int = validInt(enemyTeam.size)
    attack(heroIndex, heroTeam, enemyIndex, enemyTeam)

    if (enemyTeam.count() == 0) {
        newLevel++
    }
    else {
        terminalMessage("Your team:")
        showTeam(heroTeam)

        terminalMessage("Left enemies:")
        showEnemies(enemyTeam)
    }

    return newLevel
}

fun validInt(teamSize: Int = 0): Int {
    // the team size is n+1 where n is the last index of the array
    var input: Int
    do {
        input = readLine()!!.toInt()
        if (input >= teamSize) {
            terminalMessage("Wrong number. Try again.")
        }
    } while (input >= teamSize)
    return input
}

fun listEnemiesToKill(enemyTeam: MutableList<Enemy>, targetType: Int) : List<Int> {
    // returns the list of the enemies to kill in reverse order
    val listEnemiesKilled = mutableListOf<Int>()
    enemyTeam.forEachIndexed { index, enemy -> if (enemy.type == targetType) listEnemiesKilled.add(index) }
    return listEnemiesKilled.reversed()
}

fun drinkPotion(heroTeam: MutableList<Hero>, enemyTeam: MutableList<Enemy>, potionIndex: Int) {
    killEnemy(potionIndex,enemyTeam)
    newHero(heroTeam)
}

fun potionInteract(heroTeam: MutableList<Hero>, enemyTeam: MutableList<Enemy>) {
    val listEnemiesKilled = listEnemiesToKill(enemyTeam, POTION_TYPE)

    listEnemiesKilled.forEach { potionIndex -> drinkPotion(heroTeam, enemyTeam, potionIndex) }

    terminalMessage("You drank ${listEnemiesKilled.size} Potion.")
}

fun enemyInteract (heroTeam: MutableList<Hero>, heroType: Int, heroIndex: Int,
                   enemyTeam: MutableList<Enemy>, enemyType: Int, enemyIndex: Int, enemyTypeList: List<Int>) {

    val heroName = heroTeam[heroIndex].getType()

    val enemyName = enemyTeam[enemyIndex].getType()
    var enemyCount = 0

    if (enemyTypeList.contains(heroType) && heroType == enemyType) {
        val listEnemiesKilled = listEnemiesToKill(enemyTeam, enemyType)
        listEnemiesKilled.forEach { enemyListedIndex -> killEnemy(enemyListedIndex, enemyTeam) }
        enemyCount = listEnemiesKilled.size
    }
    else {
        killEnemy(enemyIndex, enemyTeam)
        enemyCount++
    }

    killHero(heroIndex, heroTeam)

    terminalMessage("The ${heroName} killed ${enemyCount} ${enemyName}.")
}

fun dungeonEmpty (enemyTeam: MutableList<Enemy>) : Boolean {
    return enemyTeam.count() < 0
}

fun attack (heroIndex: Int, heroTeam: MutableList<Hero>, enemyIndex: Int, enemyTeam: MutableList<Enemy>) {

    if (heroIndex > heroTeam.count() || enemyIndex > enemyTeam.count()) {
        return terminalMessage("You missed something.")
    }

    val heroType = heroTeam[heroIndex].type

    val enemyType = enemyTeam[enemyIndex].type
    val enemyTypeList = listTypes(enemyTeam)

    //potions add a hero member
    when (enemyType) {
        POTION_TYPE -> {
            if (checkListEnemyTypes(enemyTypeList)) {
                terminalMessage("You must defeat the enemies before.")
            }
            else { potionInteract(heroTeam, enemyTeam) }
        }
        else -> enemyInteract(heroTeam, heroType, heroIndex, enemyTeam, enemyType, enemyIndex, enemyTypeList)
    }

    //TODO("create turns: first kill enemies, then use chest ")
}

fun terminalMessage(message: String) {
    // prints a white line before the message
    println()
    println(message)
}

fun checkListEnemyTypes (enemyTypeList: List<Int>) : Boolean {
    // check the type list and returns true if there is any enemy
    return enemyTypeList.contains(1) || enemyTypeList.contains(2) || enemyTypeList.contains(3) || enemyTypeList.contains(4)
}

// return a list with the types of the enemies
fun listTypes(party: List<Enemy>) : List<Int> = party.map { it.type }