public static class Sword {
    String name;
    int damage;

    public Sword(String name, int damage) {
        this.name = name;
        this.damage = damage;
    }
}

// base class for players and enemies
public static class Character {
    String name;
    int hitpoints;
    Sword sword;

    public Character(String name, int hitpoints) {
        this.name = name;
        this.hitpoints = hitpoints;
    }

    public void equipSword(Sword sword) {
        this.sword = sword;
        System.out.printf("%s has equipped the %s.%n", name, sword.name);
        System.out.printf("It does %d damage.%n", sword.damage);
    }

    public void attack(Character enemy) {
        if (this.sword != null) {
            enemy.hitpoints -= this.sword.damage;
            System.out.printf("%s hit the %s for %d damage.%n", name, enemy.name, this.sword.damage);
            System.out.printf("%s has %d hitpoints left.%n", enemy.name, enemy.hitpoints);
        } else {
            System.out.printf("%s doesn't have a sword equipped.%n", name);
        }
    }
}

// player class
public static class Player extends Character {
    int numberEnemiesKilled = 0;

    public Player(String name) {
        super(name, 10);
    }

    @Override
    public void attack(Character enemy) {
        super.attack(enemy);
        if (enemy.hitpoints <= 0) {
            numberEnemiesKilled++;
            System.out.printf("You killed the %s!%n", enemy.name);
        }
    }
}

// enemy class
public static class Enemy extends Character {
    public Enemy(String name) {
        super(name, 5);
    }
}

public static void printSeparator() {
    System.out.println("=".repeat(50));
}

private static Sword longsword = new Sword("Longsword", 10);
private static Sword shortsword = new Sword("Shortsword", 5);
private static Sword rustysword = new Sword("Rusty Sword", 1);
// make array of swords
static Sword[] swords = {longsword, shortsword, rustysword};

static void pickupSword(int swordIndex) {
    ArrayUtils.remove(swords, swordIndex);
}

// main
public static void main(String[] args) {
    String name;
    Player player1;

    if (args.length > 0) {
        name = args[0];
        player1 = new Player(name);
    } else {
        name = "Player 1";
        player1 = new Player(name);
    }
