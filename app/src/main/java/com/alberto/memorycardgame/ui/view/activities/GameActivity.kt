package com.alberto.memorycardgame.ui.view.activities

import android.content.DialogInterface
import android.content.res.Resources
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.alberto.memorycardgame.R
import com.alberto.memorycardgame.data.model.Card
import com.alberto.memorycardgame.databinding.ActivityGameBinding
import com.alberto.memorycardgame.ui.view.CardsAdapter
import com.alberto.memorycardgame.ui.viewmodel.CardGameVM
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class GameActivity : AppCompatActivity() {

    private lateinit var binding: ActivityGameBinding
    private val viewModel : CardGameVM by viewModels()

    companion object{
        fun getScreenWidth() : Int {
            return Resources.getSystem().displayMetrics.widthPixels
        }

        fun getScreenHeight() : Int {
            return Resources.getSystem().displayMetrics.heightPixels
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.initializeCardList()

        binding = ActivityGameBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initRecyclerView()

        viewModel.showDialog.observe(this){
            showAlertDialog()
        }
    }

    private fun initRecyclerView() {
        binding.rvCardTable.layoutManager = GridLayoutManager(this, 4)
        viewModel.cardListLD.observe(this){
            binding.rvCardTable.adapter = CardsAdapter(it){ card -> onItemSelected(card)}
        }
    }

    private fun onItemSelected(card: Card) {
        viewModel.flipCard(card)
    }

    private fun showAlertDialog() {
        val endDialog = AlertDialog.Builder(this)

        endDialog.setTitle(R.string.end_dialog_title)
        endDialog.setMessage(R.string.end_dialog_message)

        endDialog.setPositiveButton("FINISH") { dialog: DialogInterface, _: Int ->
            dialog.dismiss()
            finish()
        }

        endDialog.setNegativeButton("RESTART") {dialog: DialogInterface, _: Int ->
            dialog.dismiss()
            viewModel.restartGame()
        }

        endDialog.create().show()
    }


}