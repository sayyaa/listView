package io.mosin.listviewadapter

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.widget.ArrayAdapter
import androidx.appcompat.app.AlertDialog
import io.mosin.listviewadapter.databinding.ActivityMainBinding
import io.mosin.listviewadapter.databinding.DialogAddCharacterBinding
import java.util.UUID

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    private lateinit var adapter: ArrayAdapter<Character>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupListWithArrayAdapter()
        binding.btnAddButton.setOnClickListener { onAddPressed() }

    }

    private fun setupListWithArrayAdapter() {
        val data = mutableListOf(
            Character(id = UUID.randomUUID().toString(), name = "Fedor"),
            Character(id = UUID.randomUUID().toString(), name = "Ivan"),
            Character(id = UUID.randomUUID().toString(), name = "Egor"),
            Character(id = UUID.randomUUID().toString(), name = "Leo"),
            Character(id = UUID.randomUUID().toString(), name = "Nikita"),
        )
        adapter = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            android.R.id.text1,
            data
        )
        binding.lvListOfCharacter.adapter = adapter
        binding.lvListOfCharacter.setOnItemClickListener { parent, view, position, id ->
            adapter.getItem(position)?.let {
                deleteCharacter(it)
            }
        }
    }

    private fun onAddPressed() {
        val dialogBinding = DialogAddCharacterBinding.inflate(layoutInflater)
        val dialog = AlertDialog.Builder(this)
            .setTitle("Create character")
            .setView(dialogBinding.root)
            .setPositiveButton("Add") { dialog, which ->
                val name = dialogBinding.teCharacterNameEditText.text.toString()
                if (name.isNotBlank()) {
                    createCharacter(name)
                }
            }.create()
        dialog.show()
    }

    private fun createCharacter(name: String) {
        val character = Character(id = UUID.randomUUID().toString(), name = name)
        adapter.add(character)
    }
    private fun deleteCharacter(character: Character) {
        val listener = DialogInterface.OnClickListener { dialog, which ->
            if (which == DialogInterface.BUTTON_POSITIVE) {
                adapter.remove(character)
            }
        }
        val dialog = AlertDialog.Builder(this)
            .setTitle("Delete character")
            .setMessage("Are you sure? Delete the character $character")
            .setPositiveButton("Delete", listener)
            .setNegativeButton("Cansel", listener)
            .create()
        dialog.show()
    }

    class Character(
        val id: String,
        val name: String
    ) {
        override fun toString(): String {
            return name
        }
    }
}

