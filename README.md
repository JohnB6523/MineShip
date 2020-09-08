# MineShip

A combination of the games Minesweeper and Battleship, basically playing like a single-player version of Battleship with elements of Minesweeper added in.
The objective is the same as in Battleship; shoot three battleships, of lengths 2, 3 and 4; but with 10 mines placed randomly through the 8x8 board. 
Shooting a mine makes you lose, while shooting an empty tile gives the number of surrounding tiles that are not empty.
The colour of the number indicates whether any of the peices are battleships; a red number means only mines, while a blue number means at least one tile contains part of a battleship.
For example, a red 3 means 3 of the surrounding 8 tiles are mines, but a blue 3 could mean 1 ship piece and 2 mines, 2 ship pieces and 1 mine, or 3 ship pieces.

This was made as a small project to teach myself Kotlin/Android app development. 
I had considered simply recreating either of the individual games, or others such as Chess, but decided this would be a little more interesting.
Since the point of this project was primarily technical, I haven't put an extensive amount of time or thought into the game design, or the premise of the game.
