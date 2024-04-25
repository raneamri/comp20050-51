# Blackbox+

---------------------------------------
* [Rules](#rules)
* [Startup](#startup)
    * [Makefile](#makefile)
    * [Jar](#jar)
* [Implementation](#interface)

---------------------------------------


## Rules
Blackbox+ is originally a board game invented by Eric Solomon. It is a 2-player game involving finding the position of atoms on a hexagonal board using rays.

Player 1, the Setter, will place 5 atoms on the board on their turn by clicking the cells on the board.
Player 2's turn, the Experimenter's, starts and all the atoms are hidden. The Experimenter can use torches (triangles) that line the outside of the board to shine rays into the board. These rays can collide with atoms in four different ways:
- 60° reflection
- 120° reflection
- 360° reflection
- Absorption, which occurs when the center of the atom collided with is collinear with the ray

The ray is reflected throughout the board until it is absorbed or exits. If it exits, a flag will appear where the ray exit the board.

Once the Experimenter has shone a satisfactory amount of rays and believe they have enough information, they can end their turn.
In the next turn, the Experimenter is tasked to place markers on the board where they believe atoms are, and every marker successfully placed over an atom is a hit.

The score is calculated as
```bash
    (2 * hits) - (Max.(no. of rays, 12 )) - (Max(no. of markers, 5))
```

To play again, click the replay button that appears at the top.

## Startup
Open Makefile and switch LIB_DIR with your JavaFX installation folder.

### Makefile
From the blackbox directory, run
```bash
    make run
```
to launch the game.

### Jar
From the blackbox directory, run
```bash
    make jar
```
to create a new jar file. To then run it, run
```bash
    make runjar
```


## Implementation