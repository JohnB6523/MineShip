package io.github.johnb6523.mineship

import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TableLayout
import android.widget.TableRow
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val constraintLayout = findViewById<ConstraintLayout>(R.id.constraintLayout)
        val newGameButton = findViewById<Button>(R.id.newGameButton)
        newGameButton.setOnClickListener {
            initGame()
        }
        initGame()
    }

    private fun initGame() {
        val table = findViewById<TableLayout>(R.id.gridTable)
        for (i in 0..7) {
            val row = table.getChildAt(i) as TableRow
            for (j in 0..7) {
                val tile = row.getChildAt(j) as ImageView
                tile.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.tile_unrevealed))
                tile.setOnClickListener {
                    tile.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.tile_alpha))
                }
            }
        }
    }
}