package com.example.fayettefun.NewEditWordActivity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.viewModels
import com.example.fayettefun.FayettefunApplication
import com.example.fayettefun.Model.Word
import com.example.fayettefun.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

const val EXTRA_ID:String = "edu.uark.ahnelson.NewWordActivity.EXTRA_ID"
class NewWordActivity : AppCompatActivity() {

    private lateinit var editWordView: EditText

    private val newWordViewModel: NewWordViewModel by viewModels {
        NewWordViewModelFactory((application as FayettefunApplication).repository,-1)
    }

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_word)
        editWordView = findViewById(R.id.edit_word)

        val id = intent.getIntExtra(EXTRA_ID,-1)
        if(id != -1){
            newWordViewModel.updateId(id)
        }
        newWordViewModel.curWord.observe(this){
            word->word?.let { editWordView.setText(word.word)}
        }

        val button = findViewById<Button>(R.id.button_save)
        button.setOnClickListener {
            CoroutineScope(SupervisorJob()).launch {
                if(id==-1) {
                    newWordViewModel.insert(
                        Word(null, editWordView.text.toString())
                    )
                }else{
                    newWordViewModel.update(Word(id,editWordView.text.toString()))
                }
            }

            setResult(RESULT_OK)
            finish()
        }
    }
}