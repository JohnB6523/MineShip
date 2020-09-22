package io.github.johnb6523.mineship

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat

val smallPieces = listOf(Tile.SHIP_S1, Tile.SHIP_S2)
val medPieces = listOf(Tile.SHIP_M1, Tile.SHIP_M2, Tile.SHIP_M3)
val largePieces = listOf(Tile.SHIP_L1, Tile.SHIP_L2, Tile.SHIP_L3, Tile.SHIP_L4)
val allPieces = listOf(Tile.SHIP_S1, Tile.SHIP_S2, Tile.SHIP_M1, Tile.SHIP_M2, Tile.SHIP_M3,
    Tile.SHIP_L1, Tile.SHIP_L2, Tile.SHIP_L3, Tile.SHIP_L4)


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val newGameButton = findViewById<Button>(R.id.newGameButton)
        newGameButton.setOnClickListener {
            initGame()
        }
        initGame()
    }

    private fun initGame() {
        val state = GameState()
        val table = findViewById<TableLayout>(R.id.gridTable)
        for (i in 0..7) {
            val row = table.getChildAt(i) as TableRow
            for (j in 0..7) {
                val tile = row.getChildAt(j) as ImageView
                tile.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.tile_unrevealed))
//                revealTile(i,j,tile,state)
                tile.setOnClickListener {
                    revealTile(i,j,tile,state)
                }
            }
        }
        val ship2icon = findViewById<ImageView>(R.id.ship2Icon)
        ship2icon.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.icon_ship_2))
        val ship3icon = findViewById<ImageView>(R.id.ship3Icon)
        ship3icon.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.icon_ship_3))
        val ship4icon = findViewById<ImageView>(R.id.ship4Icon)
        ship4icon.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.icon_ship_4))
        val text = findViewById<TextView>(R.id.textView)
        text.setText(R.string.app_name)
    }

    private fun revealTile(i : Int, j : Int, tile : ImageView, state : GameState) {
        tile.setImageDrawable(ContextCompat.getDrawable(this, state.gridContents[i][j].image))
        when (state.gridContents[i][j]) {
            Tile.MINE -> { endGame(state, false) }
            in allPieces -> {state.foundPieces.add(state.gridContents[i][j]) }
        }
        if (state.foundPieces.containsAll(smallPieces)) {
            val ship2icon = findViewById<ImageView>(R.id.ship2Icon)
            ship2icon.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.icon_ship_4))
        }
        if (state.foundPieces.containsAll(medPieces)) {
            val ship3icon = findViewById<ImageView>(R.id.ship3Icon)
            ship3icon.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.icon_ship_2))
        }
        if (state.foundPieces.containsAll(largePieces)) {
            val ship4icon = findViewById<ImageView>(R.id.ship4Icon)
            ship4icon.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.icon_ship_3))
        }
        if (state.foundPieces.containsAll(allPieces)) {
            endGame(state, true)
        }
    }

    private fun endGame(state : GameState, win : Boolean) {
        val table = findViewById<TableLayout>(R.id.gridTable)
        for (i in 0..7) {
            val row = table.getChildAt(i) as TableRow
            for (j in 0..7) {
                val tile = row.getChildAt(j) as ImageView
                tile.setImageDrawable(ContextCompat.getDrawable(this, state.gridContents[i][j].image))
                tile.setOnClickListener(null)
            }
        }
        val text = findViewById<TextView>(R.id.textView)
        text.setText(if (win) R.string.win else R.string.lose)
    }
}