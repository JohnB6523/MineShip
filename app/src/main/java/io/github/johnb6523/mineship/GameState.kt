package io.github.johnb6523.mineship

import kotlin.math.max
import kotlin.math.min
import kotlin.random.Random

enum class Tile(val image: Int) {
    SHIP_L1(R.drawable.tile_ship_placeholder), SHIP_L2(R.drawable.tile_ship_placeholder), SHIP_L3(R.drawable.tile_ship_placeholder), SHIP_L4(R.drawable.tile_ship_placeholder),
    SHIP_M1(R.drawable.tile_ship_placeholder), SHIP_M2(R.drawable.tile_ship_placeholder), SHIP_M3(R.drawable.tile_ship_placeholder),
    SHIP_S1(R.drawable.tile_ship_placeholder), SHIP_S2(R.drawable.tile_ship_placeholder), MINE(R.drawable.tile_mine), NUM_0(R.drawable.tile_0),
    NUM_1B(R.drawable.tile_1b), NUM_1R(R.drawable.tile_1r), NUM_2B(R.drawable.tile_2b), NUM_2R(R.drawable.tile_2r),
    NUM_3B(R.drawable.tile_3b), NUM_3R(R.drawable.tile_3r), NUM_4B(R.drawable.tile_4b), NUM_4R(R.drawable.tile_4r),
    NUM_5B(R.drawable.tile_5b), NUM_5R(R.drawable.tile_5r), NUM_6B(R.drawable.tile_6b), NUM_6R(R.drawable.tile_6r),
    NUM_7B(R.drawable.tile_7b), NUM_7R(R.drawable.tile_7r), NUM_8B(R.drawable.tile_8b), NUM_8R(R.drawable.tile_8r)
}

class GameState {
    val gridContents : List<List<Tile>> = initGrid()

    private fun initGrid(): List<List<Tile>> {
        // Initialise grid
        // Use Tile.NUM_0 for empty tiles for now
        val rows : MutableList<MutableList<Tile>> = mutableListOf()
        for (i in 0..7) {
            val row : MutableList<Tile> = mutableListOf(Tile.NUM_0, Tile.NUM_0, Tile.NUM_0, Tile.NUM_0, Tile.NUM_0, Tile.NUM_0, Tile.NUM_0, Tile.NUM_0)
            rows.add(row)
        }
        // Add ships
        var numShips = 0
        while (numShips < 3) {
            val row = Random.nextInt(0,7)
            val col = Random.nextInt(0,7)
            if (rows[row][col] != Tile.NUM_0) continue
            // Get direction (0 is up, 1 is right, 2 is down, 3 is left)
            when (numShips) {
                0 -> when (Random.nextInt(0, 3)) {
                    0 -> if (row - 1 >= 0 && rows[row - 1][col] == Tile.NUM_0) {
                        rows[row][col] = Tile.SHIP_S1; rows[row - 1][col] = Tile.SHIP_S2; numShips++ }
                    1 -> if (col + 1 <= 7 && rows[row][col + 1] == Tile.NUM_0) {
                        rows[row][col] = Tile.SHIP_S1; rows[row][col + 1] = Tile.SHIP_S2; numShips++ }
                    2 -> if (row + 1 <= 7 && rows[row + 1][col] == Tile.NUM_0) {
                        rows[row][col] = Tile.SHIP_S1; rows[row + 1][col] = Tile.SHIP_S2; numShips++ }
                    3 -> if (col - 1 >= 0 && rows[row][col - 1] == Tile.NUM_0) {
                        rows[row][col] = Tile.SHIP_S1; rows[row][col - 1] = Tile.SHIP_S2; numShips++ }
                }
                1 -> when (Random.nextInt(0, 3)) {
                    0 -> if (row - 2 >= 0 && rows[row - 1][col] == Tile.NUM_0 && rows[row - 2][col] == Tile.NUM_0) {
                        rows[row][col] = Tile.SHIP_M1; rows[row - 1][col] = Tile.SHIP_M2; rows[row - 2][col] = Tile.SHIP_M3; numShips++ }
                    1 -> if (col + 2 <= 7 && rows[row][col + 1] == Tile.NUM_0 && rows[row][col + 2] == Tile.NUM_0) {
                        rows[row][col] = Tile.SHIP_M1; rows[row][col + 1] = Tile.SHIP_M2; rows[row][col + 2] = Tile.SHIP_M3; numShips++ }
                    2 -> if (row + 2 <= 7 && rows[row + 1][col] == Tile.NUM_0 && rows[row + 2][col] == Tile.NUM_0) {
                        rows[row][col] = Tile.SHIP_M1; rows[row + 1][col] = Tile.SHIP_M2; rows[row + 2][col] = Tile.SHIP_M3; numShips++ }
                    3 -> if (col - 2 >= 0 && rows[row][col - 1] == Tile.NUM_0 && rows[row][col - 2] == Tile.NUM_0) {
                        rows[row][col] = Tile.SHIP_M1; rows[row][col - 1] = Tile.SHIP_M2; rows[row][col - 2] = Tile.SHIP_M3; numShips++ }
                }
                2 -> when (Random.nextInt(0, 3)) {
                    0 -> if (row - 3 >= 0 && rows[row - 1][col] == Tile.NUM_0 && rows[row - 2][col] == Tile.NUM_0 && rows[row - 3][col] == Tile.NUM_0) {
                        rows[row][col] = Tile.SHIP_L1; rows[row - 1][col] = Tile.SHIP_L2; rows[row - 2][col] = Tile.SHIP_L3; rows[row - 3][col] = Tile.SHIP_L4; numShips++ }
                    1 -> if (col + 3 <= 7 && rows[row][col + 1] == Tile.NUM_0 && rows[row][col + 2] == Tile.NUM_0 && rows[row][col + 3] == Tile.NUM_0) {
                        rows[row][col] = Tile.SHIP_L1; rows[row][col + 1] = Tile.SHIP_L2; rows[row][col + 2] = Tile.SHIP_L3; rows[row][col + 3] = Tile.SHIP_L4; numShips++ }
                    2 -> if (row + 3 <= 7 && rows[row + 1][col] == Tile.NUM_0 && rows[row + 2][col] == Tile.NUM_0 && rows[row + 3][col] == Tile.NUM_0) {
                        rows[row][col] = Tile.SHIP_L1; rows[row + 1][col] = Tile.SHIP_L2; rows[row + 2][col] = Tile.SHIP_L3; rows[row + 3][col] = Tile.SHIP_L4; numShips++ }
                    3 -> if (col - 3 >= 0 && rows[row][col - 1] == Tile.NUM_0 && rows[row][col - 2] == Tile.NUM_0 && rows[row][col - 3] == Tile.NUM_0) {
                        rows[row][col] = Tile.SHIP_L1; rows[row][col - 1] = Tile.SHIP_L2; rows[row][col - 2] = Tile.SHIP_L3; rows[row][col - 3] = Tile.SHIP_L4; numShips++ }
                }
            }
        }
        // Add mines
        var numMines = 0
        while (numMines < 10) {
            val row = Random.nextInt(0,7)
            val col = Random.nextInt(0,7)
            if (rows[row][col] == Tile.NUM_0) {
                rows[row][col] = Tile.MINE
                numMines++
            }
        }
        // Update numbers
        for (i in 0..7) {
            for (j in 0..7) {
                if (rows[i][j] != Tile.NUM_0) continue
                var objects = 0
                var blue = false
                for (i2 in max(0, i-1)..min(7, i+1)) {
                    for (j2 in max(0, j-1)..min(7, j+1)) {
                        if (i2 == i && j2 == j) continue
                        when (rows[i2][j2]) {
                            Tile.MINE -> objects++
                            Tile.SHIP_S1, Tile.SHIP_S2, Tile.SHIP_M1, Tile.SHIP_M2, Tile.SHIP_M3,
                                Tile.SHIP_L1, Tile.SHIP_L2, Tile.SHIP_L3, Tile.SHIP_L4 -> { objects++; blue = true; }
                        }
                    }
                }
                when (objects) {
                    1 -> rows[i][j] = if (blue) Tile.NUM_1B else Tile.NUM_1R
                    2 -> rows[i][j] = if (blue) Tile.NUM_2B else Tile.NUM_2R
                    3 -> rows[i][j] = if (blue) Tile.NUM_3B else Tile.NUM_3R
                    4 -> rows[i][j] = if (blue) Tile.NUM_4B else Tile.NUM_4R
                    5 -> rows[i][j] = if (blue) Tile.NUM_5B else Tile.NUM_5R
                    6 -> rows[i][j] = if (blue) Tile.NUM_6B else Tile.NUM_6R
                    7 -> rows[i][j] = if (blue) Tile.NUM_7B else Tile.NUM_7R
                    8 -> rows[i][j] = if (blue) Tile.NUM_8B else Tile.NUM_8R
                }
            }
        }
        return rows
    }
}