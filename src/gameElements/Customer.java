package gameElements;
import java.util.ArrayList;

import core.DrawingSurface;
import processing.core.PImage;

/**
 * Represents a customer in the game.
 * Each customer has a randomly selected animal image, a favorite drink order, 
 * and a bonus value awarded when their favorite drink is served correctly.
 * 
 * 
 * @author Varshini Raja
 * @version 5-25-26
 */

public class Customer {
	private PImage animal;
	private String animalType;
	private static String[] animalTypes;
	private static DrawingSurface surface;
	private Drink favDrink;
	private int bonus;
	private static ArrayList<String> recentCustomers = new ArrayList<>();
	private boolean firstVisit;
	
	/**
	 * Constructs a new customer with a random animals,
	 * favorite drink, and bonus value
	 */
	public Customer(DrawingSurface surface, String imageLink) {
		this.surface = surface;
		String[] anTypes = {
				"bunny",
				"capybara",
				"cat",
				"dog",
				"duck",
				"frog",
				"hippo",
				"shark",
				"wolf"
			};
		animalTypes = anTypes;
		for (String s : animalTypes) {
			if (imageLink.contains(s)) {
				animalType = s;
				break;
			}
		}
		animal = surface.loadImage("customers/" + animalType + ".png");
		favDrink = favDrink();
		bonus = getBonus();
		firstVisit = true;
	}
	
	/**
	 * gets the image of the customer
	 */
	public PImage getImage() {
		return animal;
	}
	
	/**
	 * Randomly selects and returns a new customer, but it cannot be the same customer
	 * as the last two customers
	 * @param customers an Array of customers already made
	 * @return a random Customer
	 */
	public static Customer randomCustomer(Customer[] customers) {
			Customer randomAnimal = null;

			do {
				randomAnimal = customers[(int) (Math.random() * customers.length)];
			}
			while (recentCustomers.contains(randomAnimal.animalType));

			recentCustomers.add(randomAnimal.animalType);

			if (recentCustomers.size() > 2) {
				recentCustomers.remove(0);
			}

			return randomAnimal;
	}
	
	/**
	 * Determines the customer's favorite drink based on the selected animal
	 * Different animals prefer different tea flavors and toppings
	 * 
	 * @return the customer's favorite drink
	 */
	public Drink favDrink() {
		Tea tea = null;
		Topping topping = null;
		ArrayList<Topping> toppings = new ArrayList<>(); 
		
		
		if (animalType.equals("bunny")) {
			tea = new Tea("strawberry");
			topping = new Topping("lychee");
		} else if (animalType.equals("capybara")) {
			tea = new Tea("passionFruit");
			topping = new Topping("pearls");
		} else if (animalType.equals("cat")) {
			tea = new Tea("brownSugar");
			topping = new Topping("pearls");
		} else if (animalType.equals("dog")) {
			tea = new Tea("strawberry");
			topping = new Topping("popping");
		} else if (animalType.equals("duck")) {
			tea = new Tea("passionFruit");
			topping = new Topping("popping");
		} else if (animalType.equals("frog")) {
			tea = new Tea("brownSugar");
			topping = new Topping("popping");
		} else if (animalType.equals("hippo")) {
			tea = new Tea("passionFruit");
			topping = new Topping("lychee");
		} else if (animalType.equals("wolf")) {
			tea = new Tea("strawberry");
			topping = new Topping("popping");
		}
		
		toppings.add(topping);
		
		favDrink = new Drink(0, 0, 0, 0, tea, toppings);
		return favDrink;
	}
	
	/**
	 * Determines the bonus points awarded for serving this customer's favorite drink
	 * 
	 * The bonus value depends on the type of animal customer
	 * 
	 * @return the customer's bonus points
	 */
	public int getBonus() {
		if (animalType.equals("bunny")) {
			bonus = 10;
		} else if (animalType.equals("capybara")) {
			bonus = 5;
		} else if (animalType.equals("cat")) {
			bonus = 1;
		} else if (animalType.equals("dog")) {
			bonus = 3;
		} else if (animalType.equals("duck")) {
			bonus = 0;
		} else if (animalType.equals("frog")) {
			bonus = 5;
		} else if (animalType.equals("hippo")) {
			bonus = 5;
		} else if (animalType.equals("shark")) {
			bonus = 0;
		} else if (animalType.equals("wolf")) {
			bonus = 0;
		}
		
		return bonus;
	}
	

	/**
	 * returns a line that the customer says, with a different set of quotes for each customer
	 * if it is the customer's first appearance, they will always begin with a greeting. Otherwise, the
	 * quote is randomized
	 * 
	 * @return String the quote that the customer should say
	 */
	public String speak() {
		String[] greetings = null;
		String[] normalPhrases = null;

		if (animalType.equals("bunny")) {

			greetings = new String[] {
				"Hiii~~ I’m Yves! Pleasure to meet you! (^v^)"};

			normalPhrases = new String[] {
				"I always spill my drink… I’m so clumsy (-.-;)y-~~~ I need to be careful this time..",
				"Oh my gosh, is that a new shirt? It looks so cute!",
				"Hm.. he's probably working right now, isn't he? I shouldn't bother him..",
				"~~ ...and you know what he said to me? \"what?\" he was like.. \"you're sooo rude!\"~~",
				"tea-rrific things are sure to come! ^^",
				"I just saw two students outside sharing boba! So cute! Oh, to be young and in love... (^u^)",
				"I can't believe he bought such an expensive necklace for me...I don't want him to waste his money >.<",
				"~~...searching for the perfect words to say, that will make you truly wanna stay with me...~~",
				"~~...got a long list of ex-lovers, they'll tell you I'm insane~~",
				"~~YOU JUST WANT ATTENTION, YOU DON'T WANT MY HEART~~",
				
			};

		}
		else if (animalType.equals("capybara")) {

			greetings = new String[] {
				"Yo, yo, yo! How’s it hangin’? The name’s Reginald, professional gamer. W fit, dude. 🌻"};

			normalPhrases = new String[] {
				"You like my cut, G?",
				"If you had to betray one person to save a thousand… would you?",
				"You only have 500 trophies on Brawl Stars? Noob behavior, no cap. 🧢",
				"You don’t have free wifi here? Say wallahi bro. I have a tournament in like 20 minutes!",
				"I’m finna crash out, broski. My MCSR rank keeps dropping, I need to lock in.",
				"Yoo can I get your CS2 nickname? Oh, you don’t play CS2? This is unbelievable. Unacceptable, I say!",
				"I can’t wait to hop on Clash and play with Jon! He’s not that good, but that’s okay because I carry. 😉",
				"Hold on chat, lemme call Jon. Our favorite streamer is going live rn!",
				"Ts would be hella cool to post on my instagram!",
				//"Ollie the shark was here? Rad! He should hit me up for a collab." this should only work if the shark already came.. ;(
			};
		}
		else if (animalType.equals("cat")) {

			greetings = new String[] {
					"Bonjour, je m'appelle Claire. I just moved here from France, so my English is not yet fluent."};

			normalPhrases = new String[] {
				"Hm, I have to practice later.. People like calling me a piano prodigy, but I guess it fits, I am named after the piece Clair de Lune by Debussy.",
				"Helio told you to say hello? *scoff* he is so annoying! -_- Just yesterday he kept following me around asking if I needed help with English...",
				"Helio keeps bugging me asking if I need help with English. He must have seen my score... Even though he's rank 1, I'm not much behind...",
				"Hmm… have you seen Helio recently? He left school early today---not that I’m worried or anything…",
				"This bracelet? Oh, Helio made me wear it after he beat me on our last test :| I’m definitely beating him next time! Well, I guess it looks okay…",
				"I always see Helio order this drink. I wonder what it tastes like..",
				"I just met the strangest duck outside! He was talking so fast I couldn't understand---was it even English?",
				"Oh, Helio. I saw him at the hospital yesterday with his mom...I hope he's okay.",
				"Euh, I don't get what people see in Helio. He's not THAT good looking..", //oh so you imply he IS somewhat good looking..?
				"Yesterday Mr. Honey brought pizza to class... I couldn't eat it tho. Olives? No thank you.",
				"Euh, Helio is so arrogant! Even his name is from the God of the sun!",
			};

		}
		else if (animalType.equals("dog")) {

			greetings = new String[] {
				"Hey, wassup?! I’m Helio, a golden retriever! If you see Claire, tell her I said hi, she’s the one who introduced me to this place! Looks sick!"};
					
			normalPhrases = new String[] {
				"Oh, why do I look so happy? I’m meeting up with Claire to study for the test. I'm so excited I can’t stop talking!",
				"Claire absolutely DESTROYED that grumpy old teacher today in an argument, she’s mad cool. Oh, not Mr. Honey of course!",
				//"Hm, did something happen? Oh, it’s nothing… I wouldn't want to waste your time. Anyway, did you say that Claire was worried about me?",
				"Hm, how am I? Oh, I'm good... it's nothing. Anyway, did you say that Claire was worried about me?",
				":( Claire got mad at me today because I yelled someone who was making fun of her. I know she can stand up for herself, I do.",
				"Did you know that Claire is SUPER AMAZING at playing the piano? I went to her recital the other day, and I LOVED it. She’s so cool. ^^",
				"Huh.. I feel like something strange is going on.. I hope Claire is safe.",
				"...My mom's not doing too well especially because of Grandmama..",
				"Rest in peace my granny, she got hit by a bazooka. Yeah, I think about her every time I hit the Humuhumunukunukuāpua'a \"Wait what?\"",
				"Haha, it's so funny that Claire doesn't like olives--Couldn't be me! She gets all annoyed when I try to give her my pizza.",
				"My mom keeps telling me to stay away from cats and frisbees. Nah, I'mma do my own thing."
			};

		}
		else if (animalType.equals("duck")) {

			greetings = new String[] {
				"Valorous aft'rnoon, this day truly is fine, isn't it? Mine own nameth is Sir Quacks-A-Lot, as I wast knight'd by the late Queen Elizabeth. Oh, I feareth I am talking much too much—hence mine own name—but I would be most delighted to try some tea."};
					
			//capitalize
			normalPhrases = new String[] {
					"I've recently cometh across a --- what wast 't again? Oh aye, a phoneth! What a marvelous invention, thee not bethink?", 
					"I bethink I'll gift a phoneth to mine own gl'rious Queen Elizabeth IV, the lady wouldst certes appreciateth one.", 
					"Doth thee knoweth of Ollie the youtub'r? His videos art quite intriguing, I might not but sayeth I has't been watching those folk a lot.",
					"'Twas a jolly night of holly and merit, though warn dang'r is lying upon thou",
					"T’day I'm mourning f'r Queen Elizabeth iii.  One timeth the lady almost behead'd me ov'r the wrong coffee.  Aye, valorous times!",
					"Watch f'r thy head! Would't want to lose 't.",
					"Mine own cabbages!",
					"At least if we're going crazy, we'll go crazy together, right?",
					
			};

		}
		else if (animalType.equals("frog")) {

			greetings = new String[] {
				"Greetings, you must be new here. My full name is Gerald Antonious Fredderick II, but you may refer to me as Mr. Fredderick."};
				
			normalPhrases = new String[] {
					"\"Failure is only the opportunity to begin again. Only this time, more wisely.\" Fascinating, no?",
					"The faster, the better. I'm in a bit of a rush for my meeting..",
					"Don't listen to what Sir Quacks-A-Lot has to say. He's gone mad since his love died.",
					"*sigh*",
					"My favorite word is \"demure\". But \"pneumonoultramicroscopicsilicovolcanoconiosis\" is a close second.",
					"This cafe is a lovely escape. My boss is quite the character, you see. He made one of our clients cry the other day.",
					"I'm looking forward to my paid vacation, I'm going to visit my dear friend Francis the Humuhumunukunukuāpua'a",
					"Mr. B has become much softer recently... I can wonder why.. (there must be someone)",
					"Do you guys have pi, like 3.14159265358979323846264338327950288419716939937510",
					
			};

		}
		else if (animalType.equals("hippo")) {

			greetings = new String[] {
				"Hello! I’m Jon Honey, our high school's math teacher. Fine day for some scrumptious boba, don't you think?"};

			normalPhrases = new String[] {
				"Today’s weather looks nice, doesn’t it?",
				"My, my, the kids sure grow up fast, don’t they?",
				"I need Helio to help Claire with her English, and her to help him with his math..",
				"I saw Claire and Helio getting boba from here too. This place is pretty gnarly.",
				"Nothing's better than boba after a long shift..",
				"Being a pizza delivery man is hard. Not harder than dealing with these kids though, they're pretty gnarly.",
				"I'm thinking of buying a treat for my students after doing so well on finals, what do you think?",
				"I have some leftover pizza to share with Reginald! I can't believe he doesn't like pineapple on pizza!",
				"Coffee for the mornings and boba for the afternoons!",
				"Snazzy jacket today! Or as Reginald would say, \"Slay!\"",
				"My coworker told me she finally finished installing Java. I was astounded, until she held her empty coffee mug."
			};

		}
		else if (animalType.equals("shark")) {

			greetings = new String[] {
				"G’day mate! It’s Ollie here, and today we’ll be trying out this new boba place to see if it’s truly tasTEA!"};

			normalPhrases = new String[] {
				"Wow! We’ve reached 100,000 followers! Thanks so much for all the support and love, so I’m back here at TeaTime, where you guys got hooked!",
				"Wait, Helio has been here too?! It's been a while since I've seen my underclassmen in person.. He's a junior now, right? Wow!",
				"Has Helio finally gotten with that girl he always talks about? They don't like each other? No way! I  can tell he DEF likes her",
				"Let's get this PAR-TEA going on!! I'm having John Cena over too!",
				"Could I do a food review of this cafe? It's nice and cozy, vibes are immaculate.",
				"Hey guys, what's up? It's Ollie the Shark, and today we're gonna be doing a boba mukbang!",
				"I need to get Helio to join some of my videos---he's SUPER popular at school (he's rank 1, and good at sports!)"
			};

		}
		else if (animalType.equals("wolf")) {

			greetings = new String[] {"Name's Bartholomew. Remember it. Chief Executive Officer of Boba.INC, so don't try anything funny."};

			normalPhrases = new String[] {
				"Hmph, could you work ANY slower? ...I don't want to keep her waiting...",
				"*chuckles* she’s so cute.. No, not you.",
				"I wonder why Yves likes this place so much..",
				"I better not find you upsetting her again... or it'll be the last thing you do",
				"...I wonder what flowers she'll like.",
				"Hmm.. Why won't she accept my gifts? Anyone would die for Chanel and a Rolex..",
				"She wants to see the top of the Eiffel tower. Euh, I hate heights.. But I guess I'm okay with anyhing as long as it's with her..",
				"...I love when she sings to herself..",
				"I'm the alpha, I'm the leader, I'm the one to trust, together we do, whatever it takes, we're in this pack for life, we're wolves, we own the night. "
			};

		}

		String[] speechOptions;

		if (firstVisit) {
			speechOptions = greetings;
			firstVisit = false;
		} else {
			speechOptions = normalPhrases;
		}

		int randomPhrase = (int)(Math.random() * speechOptions.length);

		return speechOptions[randomPhrase];
	}
	
	/**
	 * Gets the customer's response to the user's drink based on how many stars it has
	 * 
	 * @param fiveStars an Array of each star of the drink
	 * @return the customer's response to the drink's score
	 */
	public String getRating(String[] fiveStars) {
		int numStars = 0;
		for (int i = 0; i < fiveStars.length; i++) {
			if (fiveStars[i].equals("full star")) {
				numStars++;
			}
		}
		if (animalType.equals("bunny")) {
			if (numStars <= 1)
				return "Mmm… are you doing okay?";
			else if (numStars == 2)
				return "Oh! It’s definitely got a kick to it...";
			else if (numStars == 3)
				return "Good as usual~";
			else if (numStars == 4)
				return "This is my favorite place to get boba :)";
			else 
				return "OH MY GOSH! THIS IS AMAZING!!";
			
		}
		else if (animalType.equals("capybara")) {
			if (numStars <= 1) {
				String[] phrases = {"Yo, Im bouta throw hands. I don’t know how to say this…but boba-making might not be your calling. Gaming perchance? 🤨", "This is how my ranked teammates are like.."};
				return phrases[(int)(Math.random()*phrases.length)];
			}
			else if (numStars == 2) {
				String[] phrases = {"Ts is buns, dude. 🍞",};
				return phrases[(int)(Math.random()*phrases.length)];
			}else if (numStars == 3)
				return "Mmm, this one’s pretty chill. 👍";
			else if (numStars == 4)
				return "Ooooh, let her cook! 🍳";
			else 
				return "No glaze, this might be the best drink I’ve ever tasted. Pop off queen! ✌️";

		}
		else if (animalType.equals("cat")) {
			if (numStars <= 1)
				return "…This is quite distasteful";
			else if (numStars == 2)
				return "I fear it’s not to my liking.";
			else if (numStars == 3)
				return "The flavors are incredibly subtle..";
			else if (numStars == 4)
				return "Mm, it’s quite delectable (=^x^=)";
			else 
				return "C’est exquis!️";

		}
		else if (animalType.equals("dog")) {
			if (numStars <= 1)
				return "Uhh, did I do something wrong?";
			else if (numStars == 2)
				return "Hey, they can’t all be winners!";
			else if (numStars == 3)
				return "Love this, but it could’ve been so much more…";
			else if (numStars == 4)
				return "This is amazing. No notes.";
			else 
				return "WOAHH I THINK I JUST FELL IN LOVE WITH MY DRINK️";

		}
		else if (animalType.equals("duck")) {
			if (numStars <= 1)
				return "Gah! tis’t'rrible! It may as well bringeth the late queen backeth if 't be true the lady were to tryeth this in her grave six feet und’r.";
			else if (numStars == 2)
				return "Tis leaves a foul gust. It may beest due to the int'resting flavor's palate -—did thee measureth thy ingredients c'rrectly?";
			else if (numStars == 3)
				return "Tis’ much mediocre. It truly hast did enlighten me, for how is this what “boba” tastes liketh? I am much disappoint'd.";
			else if (numStars == 4)
				return "The gust is truly significant! Remarkable! Outstounding! Oh, how it may coequal surpass coffee… Nay, I musn’t betray mine own homeland.";
			else 
				return "Wow! This is most wondrous! Its dazzle hast coequal hath left me—-a chronic talketh'r—-speechless. It coequal maketh coffee gust bland...";
		}
		else if (animalType.equals("frog")) {
			if (numStars <= 1)
				return "…Not my cup of tea ";
			else if (numStars == 2)
				return "Hmm.. you should try following what the order is.";
			else if (numStars == 3)
				return "It’s a tad different from my usual.";
			else if (numStars == 4)
				return "It hits the spot.";
			else 
				return "This is splendid. Truly scrumptious. I give my finest thanks.";

		}
		else if (animalType.equals("hippo")) {
			if (numStars <= 1)
				return "I’ve tasted worse.. <My wife is an interesting cook.>";
			else if (numStars == 2)
				return "This almost tastes like a mud pie—in a good way…";
			else if (numStars == 3)
				return "Not bad. But don't worry, you can do better next time.";
			else if (numStars == 4)
				return "I'm proud of you, kid.";
			else 
				return "I’ve got to tell Reginald to try some of this!";

		}
		else if (animalType.equals("shark")) {
			if (numStars <= 1)
				return "BLEGH! Oh, sorry, haha… it’s *gag* great!";
			else if (numStars == 2)
				return "Ooh, buddy… that is not the stuff";
			else if (numStars == 3)
				return "A-OK!";
			else if (numStars == 4)
				return "Dangg you got skills!";
			else 
				return "THIS IS SO FIRE I’M DOING MY HAPPY DANCE (:";
		}
		else if (animalType.equals("wolf")) {
			if (numStars <= 1)
				return "Eugh. This is terrible.";
			else if (numStars == 2)
				return "How do you even earn money?";
			else if (numStars == 3)
				return "Could be much better. It’s tolerable, I guess. Barely.";
			else if (numStars == 4)
				return "At least it tastes better than Red’s Granny...";
			else 
				return "Maybe you’re not so bad...";

		} else
			return "no such customer";
	}
	
	public boolean isFirstVisit() {
		return firstVisit;
	}
	
	/**
	 * gets the animal's sound effect that will play when they are clicked
	 * @return an integer that represents the index of that sound effect in GameScreen
	 */
	public int getSoundEffect() {
		if (animalType.equals("bunny"))
			return 5;
		else if (animalType.equals("capybara"))
			return 6;
		else if (animalType.equals("cat"))
			return 8;
		else if (animalType.equals("dog"))
			return 4;
		else if (animalType.equals("duck"))
			return 9;
		else if (animalType.equals("frog"))
			return 10;
		else if (animalType.equals("hippo"))
			return 12;
		else if (animalType.equals("shark"))
			return 11;
		else if (animalType.equals("wolf"))
			return 7;
		else 
			return -1;
	}
	
	public String getType() {
		return animalType;
	}
	

}
