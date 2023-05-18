// make a text-based game where a user can pickup a sword
import org.apache.commons.lang3.ArrayUtils;
import java.util.Scanner;

public class Goblin_Slayer {

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
            System.out.println(String.format("%s has equipped the %s", name, sword.name));
            System.out.printf("It does %d damage.", sword.damage);
        }

        public void attack(Character enemy) {
            if (this.sword != null) {
                enemy.hitpoints -= this.sword.damage;
                System.out.printf("%s hit the %s for %d damage.", name, enemy.name, sword.damage);
                System.out.printf("%s has %d hitpoints left.", enemy.name, enemy.hitpoints);                
            } else {
                System.out.printf("%s doesn't have a sword equipped.", name);

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
                System.out.printf("You killed the %s!", enemy.name);

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
    static Sword swords[] = {longsword, shortsword, rustysword};

    static void pickupSword(int swordIndex) {
        swords.remove(swordIndex);
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
        
        System.out.printf("Welcome, brave %s! You find yourself in a pitch-black dungeon filled with the stench of death, teeming with goblins.\n", player1.name);
	System.out.println("Your mission is to defeat 3 goblins to escape from this nightmarish place. But beware, for these goblins are cunning and relentless.\n");

	System.out.println("\nBefore you, a dimly lit weapons rack reveals three different swords, each appearing to hold a grim history.");

        // offer choices of swords in array
        for (int i = 0; i < swords.length; i++) {
            System.out.printf("%d: %s (Damage: %d)", i + 1, swords[i].name, swords[i].damage);

        }
        System.out.print("Choose the number of the sword you want to pick up: ");

        Scanner scanner = new Scanner(System.in);
        String swordChoice = scanner.nextLine();
        if (swordChoice.equals("1")) {
            player1.equipSword(longsword);
        } else if (swordChoice.equals("2")) {
            player1.equipSword(shortsword);
        } else if (swordChoice.equals("3")) {
            player1.equipSword(rustysword);
        } else {
            System.out.println("There is no sword with that number. You decide to move on without a weapon, your fate uncertain.");
        }

        while (player1.hitpoints > 0 && player1.numberEnemiesKilled < 3) {    
            printSeparator();
            System.out.println("\nSuddenly, a fearsome goblin emerges from the shadows, its eyes gleaming with malevolence. It thirsts for your blood!");
            Enemy enemy = new Enemy("Goblin");
            enemy.equipSword(shortsword);
    
            while (player1.hitpoints > 0 && enemy.hitpoints > 0) {
                System.out.printf("\n%s (HP: %d) vs. %s (HP: %d)\n", player1.name, player1.hitpoints, enemy.name, enemy.hitpoints);
                System.out.println("What will you do?");
                System.out.println("1: Attack the goblin");
                System.out.println("2: Attempt to flee");
                System.out.print("Choose your action (1 or 2): ");
                String action = scanner.nextLine();
                printSeparator();
                if (action.equals("1")) {
                    player1.attack(enemy);
                    if (enemy.hitpoints > 0) {
                        enemy.attack(player1);
                    }
                } else if (action.equals("2")) {
                    System.out.println("\nYou desperately try to flee from the goblin, your heart pounding in your chest.");
                    break;
                } else {
                    System.out.println("\nYour hesitation gives the goblin an opening to strike! It lunges at you with a wicked grin, landing a devastating blow!");
                    player1.hitpoints -= player1.hitpoints;
                }
            }
    
            printSeparator();
        }
    
        if (player1.hitpoints <= 0) {
            System.out.printf("\nAlas, brave %s, you have succumbed to the goblin's wrath. Your life lessens and the cold embrace of death claims you. Your valiant struggle shall be lost to the darkness of the dungeon.", player1.name);

        } else if (player1.numberEnemiesKilled >= 3) {
            System.out.printf("\nAgainst all odds, %s, you have vanquished the goblins and escaped the clutches of this hellish dungeon! Your courage and skill have led you to victory, but the scars of battle will forever remain.", player1.name);

        }

        System.out.println("\nThank you for playing Goblin Slayer! Farewell, brave adventurer, and may you find solace in the world beyond this nightmare.");
        scanner.close();
    }
}