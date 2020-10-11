package io.github.johnb6523.mineship

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.content.res.ResourcesCompat
import androidx.navigation.findNavController
import kotlin.math.max
import kotlin.math.min

val smallPieces = listOf(Tile.SHIP_S1, Tile.SHIP_S2)
val medPieces = listOf(Tile.SHIP_M1, Tile.SHIP_M2, Tile.SHIP_M3)
val largePieces = listOf(Tile.SHIP_L1, Tile.SHIP_L2, Tile.SHIP_L3, Tile.SHIP_L4)
val allPieces = listOf(Tile.SHIP_S1, Tile.SHIP_S2, Tile.SHIP_M1, Tile.SHIP_M2, Tile.SHIP_M3,
    Tile.SHIP_L1, Tile.SHIP_L2, Tile.SHIP_L3, Tile.SHIP_L4)

class GamePage : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_game_page, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val rulesButton = view.findViewById<Button>(R.id.rulesButton)
        rulesButton.setOnClickListener{
            view.findNavController().navigate(GamePageDirections.actionGamePageToRulesPage())
        }
        val newGameButton = view.findViewById<Button>(R.id.newGameButton)
        newGameButton.setOnClickListener {
            initGame(view)
        }
        initGame(view)
    }

    private fun initGame(view: View) {
        val state = GameState()
        val table = view.findViewById<TableLayout>(R.id.gridTable)
        for (i in 0..7) {
            val row = table.getChildAt(i) as TableRow
            for (j in 0..7) {
                val tile = row.getChildAt(j) as ImageView
                tile.setImageDrawable(ResourcesCompat.getDrawable(resources, R.drawable.tile_unrevealed, null))
                tile.rotation = 0F
                tile.setOnClickListener {
                    showTile(view,i,j,state)
                    revealTile(view,i,j,state,mutableListOf())
                }
            }
        }
        val ship2icon = view.findViewById<ImageView>(R.id.ship2Icon)
        ship2icon.setImageDrawable(ResourcesCompat.getDrawable(resources, R.drawable.icon_ship_2, null))
        val ship3icon = view.findViewById<ImageView>(R.id.ship3Icon)
        ship3icon.setImageDrawable(ResourcesCompat.getDrawable(resources, R.drawable.icon_ship_3, null))
        val ship4icon = view.findViewById<ImageView>(R.id.ship4Icon)
        ship4icon.setImageDrawable(ResourcesCompat.getDrawable(resources, R.drawable.icon_ship_4, null))
        val text = view.findViewById<TextView>(R.id.titleText)
        text.setText(R.string.app_name)
    }

    // revealTile handles gameplay logic of revealing a tapped tile
    // propagateList handles the propagation of reveals from a 0-tile, and avoids revealing an already revealed tile
    private fun revealTile(view: View, i : Int, j : Int, state : GameState, propagateList: MutableList<Pair<Int, Int>>) {
        val table = view.findViewById<TableLayout>(R.id.gridTable)
        val row = table.getChildAt(i) as TableRow
        val tile = row.getChildAt(j) as ImageView
        when (state.gridContents[i][j]) {
            in allPieces -> state.foundPieces.add(state.gridContents[i][j])
            Tile.MINE -> endGame(view, state, false)
            Tile.NUM_0 -> {
                for (i2 in max(0, i-1)..min(7, i+1)) {
                    for (j2 in max(0, j - 1)..min(7, j + 1)) {
                        if ((i2 == i && j2 == j) || Pair(i2,j2) in propagateList) continue
                        propagateList.add(Pair(i,j))
                        showTile(view,i2,j2,state)
                        revealTile(view,i2,j2,state,propagateList)
                    }
                }
            }
        }
        if (state.foundPieces.containsAll(smallPieces)) {
            val ship2icon = view.findViewById<ImageView>(R.id.ship2Icon)
            ship2icon.setImageDrawable(ResourcesCompat.getDrawable(resources, R.drawable.icon_ship_destroyed, null))
        }
        if (state.foundPieces.containsAll(medPieces)) {
            val ship3icon = view.findViewById<ImageView>(R.id.ship3Icon)
            ship3icon.setImageDrawable(ResourcesCompat.getDrawable(resources, R.drawable.icon_ship_destroyed, null))
        }
        if (state.foundPieces.containsAll(largePieces)) {
            val ship4icon = view.findViewById<ImageView>(R.id.ship4Icon)
            ship4icon.setImageDrawable(ResourcesCompat.getDrawable(resources, R.drawable.icon_ship_destroyed, null))
        }
        if (state.foundPieces.containsAll(allPieces)) {
            endGame(view, state, true)
        }
    }

    // showTile handles graphics only, is used when tapped and on showing full board on win/loss
    private fun showTile(view : View, i : Int, j : Int, state : GameState) {
        val table = view.findViewById<TableLayout>(R.id.gridTable)
        val row = table.getChildAt(i) as TableRow
        val tile = row.getChildAt(j) as ImageView
        tile.setImageDrawable(ResourcesCompat.getDrawable(resources, state.gridContents[i][j].image, null))
        when (state.gridContents[i][j]) {
            in smallPieces -> tile.rotation = state.ship2Ori * 90F
            in medPieces -> tile.rotation = state.ship3Ori * 90F
            in largePieces -> tile.rotation = state.ship4Ori * 90F
        }
    }

    private fun endGame(view: View, state : GameState, win : Boolean) {
        val table = view.findViewById<TableLayout>(R.id.gridTable)
        for (i in 0..7) {
            val row = table.getChildAt(i) as TableRow
            for (j in 0..7) {
                val tile = row.getChildAt(j) as ImageView
                showTile(view,i,j,state)
                tile.setOnClickListener(null)
            }
        }
        val text = view.findViewById<TextView>(R.id.titleText)
        text.setText(if (win) R.string.win else R.string.lose)
    }
}