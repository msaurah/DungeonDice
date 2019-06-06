import kotlin.random.Random

fun createHero(){
    var hero = Hero()
    println(hero.getType())
}

fun createTeam(numberMember: Int = 6): MutableList<Hero> {
    var team = mutableListOf<Hero>()
    for (i in 1..numberMember) {
        team.add(Hero())
    }
    showTeam(team)
    return team
}

fun createEnemies(levelDungeon: Int = 6): MutableList<Enemy> {
    var team = mutableListOf<Enemy>()
    for (i in 1..levelDungeon) {
        team.add(Enemy())
    }
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

fun startAttack(heroTeam: MutableList<Hero>, enemyTeam: MutableList<Enemy>){
    println("Who attacks?")
    var hero = readLine()!!.toInt()
    println("Who is being attacked?")
    var enemy = readLine()!!.toInt()
    attack(hero, heroTeam, enemy, enemyTeam)
    //TODO("check if the input is a number and repeat until it is")
}

fun attack(hero: Int, heroTeam: MutableList<Hero>, enemy: Int, enemyTeam: MutableList<Enemy>) {
    if (hero > heroTeam.count() || enemy > enemyTeam.count()){
        return println("You missed something")
    }
    var heroType = heroTeam[hero].type
    var heroName = heroTeam[hero].getType()
    var enemyType = enemyTeam[enemy].type
    var enemyName = enemyTeam[enemy].getType()

    killEnemy(enemy, enemyTeam)
    killHero(hero, heroTeam)
    println("The ${heroName} killed a ${enemyName}.")
}
//TODO("basic attack 1vs1")
fun attackTypes(){}

fun killEnemy(enemy: Int, enemyTeam: MutableList<Enemy>) {
    enemyTeam.removeAt(enemy)
}

fun killHero(hero: Int, heroTeam: MutableList<Hero>){heroTeam.removeAt(hero)}

fun rollDice (sides: Int = 6): Int {
    return if (sides == 0) 0 else Random.nextInt(sides) + 1
}

open class Character (){
    var type: Int
    init {
        type = rollDice()
    }
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