# Snap Game

## Description

A Java Application to play the Game of Snap.

### Dependencies

Project was built using Gradle and IntelliJ IDEA.

### Executing program

* The code can easily be run using the Main.java class.
* Without any parameters, the users will have 20 seconds between each draw of Card. Add a number as a parameter for a different value.
* The game starts by asking for the settings of the game: Card Match Rule, number of Players, number of Decks.
* The Decks are standard Decks of 52 Cards. The Cards are shuffled, then dealt evenly to each Player.
* 3 Card Match Rules are implemented: on Suit, on Value, or on Both (i.e. the exact same Card). The 'Both' Rule requires at least 2 Decks to play the game.
* Each Player is automatically given a number ID, going from 1 to the total number of Players.
* When the game starts, Players take turn drawing a Card from the Face Down pile into their Face Up pile.
* After each draw, every Player can Snap by inputting the corresponding Player ID.
* If the Snap is a success, the Snapper Player will retrieve all the pile of Face Up Cards with Matching Cards on top.
* If there are multiple Matches, the Snapper Player will retrieve all the corresponding piles.
* When a Player runs out of Face Down Cards, their Face Up Cards will automatically be put in reverse order in the Face Down pile.
* When a Player runs out of Cards altogether, the Player cannot draw, but has the opportunity to Snap back into the game at any time. 
* The game ends when a Player successfully wins all the Cards.

## Authors

Julien Vaillant
j.vaillant@me.com