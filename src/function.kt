import kotlin.random.Random

fun rollDice (sides: Int = 6): Int {
    return if (sides == 0) 0 else Random.nextInt(sides) + 1
}

private fun newHero (team: MutableList<Hero>) {
    team.add(Hero())
    team.sortBy { it.type }
}

private fun killHero(heroIndex: Int, heroTeam: MutableList<Hero>){ heroTeam.removeAt(heroIndex) }

fun createTeam(numberMember: Int = 6): MutableList<Hero> {
    var team = mutableListOf<Hero>()
    for (i in 1..numberMember) {
        team.add(Hero())
    }

    //Sort enemies by type
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
    if (team.count() == 0) return println("You have no team")
    for (i in 0..(team.count() - 1) ){
        println("${i}." + team[i].getType())
    }
}

fun showEnemies(team: List<Enemy>) {
    if (team.count() == 0) return println("You have no team")
    for (i in 0..(team.count() - 1) ){
        println("${i}." + team[i].getType())
    }
}

fun startAttack(heroTeam: MutableList<Hero>, enemyTeam: MutableList<Enemy>, level: Int): Int{
    var newLevel = level
    println("Who attacks?")
    var hero = readLine()!!.toInt()
    println("Who is being attacked?")
    var enemy = readLine()!!.toInt()
    attack(hero, heroTeam, enemy, enemyTeam)
    //TODO("check if the input is a number and repeat until it is")

    if (enemyTeam.count() == 0) {
        newLevel++
    }
    return newLevel
}

fun attack(heroIndex: Int, heroTeam: MutableList<Hero>, enemyIndex: Int, enemyTeam: MutableList<Enemy>) {
    if (heroIndex > heroTeam.count() || enemyIndex > enemyTeam.count()){
        return println("You missed something")
    }

    var heroType = heroTeam[heroIndex].type
    var heroName = heroTeam[heroIndex].getType()

    var enemyType = enemyTeam[enemyIndex].type
    var enemyName = enemyTeam[enemyIndex].getType()

    var enemiesTypes = listTypes(enemyTeam)

    //potions add a hero member
    if (enemyType == 6) {
        var potion = 0
        var listEnemiesKilled = mutableListOf<Int>()
        for (i in 0 until enemyTeam.count()) {
            if (enemyTeam[i].type == 6)
                listEnemiesKilled.add(i)
        }
        for (i in (listEnemiesKilled.count() - 1) downTo 0) {
            killEnemy(listEnemiesKilled[i], enemyTeam)
            potion++
            newHero(heroTeam)
        }
        println("You drink ${potion} potion")
    }
    else {
        if (enemiesTypes.contains(heroType) && heroType == enemyType) {
            var listEnemiesKilled = mutableListOf<Int>()
            for (i in 0 until enemyTeam.count()) {
                if (enemyTeam[i].type == heroType)
                    listEnemiesKilled.add(i)
            }
            for (i in (listEnemiesKilled.count() - 1) downTo 0) {
                killEnemy(listEnemiesKilled[i], enemyTeam)
            }
        } else
            killEnemy(enemyIndex, enemyTeam)

        killHero(heroIndex, heroTeam)

        println("The ${heroName} killed a ${enemyName}.")
        println()
        println("Left enemies")
        showEnemies(enemyTeam)
    }
    //TODO("create turns: first kill enemies, then use chest or potion")
}

fun attackTypes(){}

// return a list with the types of the enemies
fun listTypes(party: List<Enemy>) : List<Int> = party.map { it.type }