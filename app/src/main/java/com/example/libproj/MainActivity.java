package com.example.libproj;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    DatabaseManager mDatabase;

    EditText editTextBook, editTextAuthor;
    Spinner spinnerGenre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mDatabase = new DatabaseManager(this);

        editTextBook = findViewById(R.id.editTextBook);
        editTextAuthor = findViewById(R.id.editTextAuthor);
        spinnerGenre = findViewById(R.id.spinnerGenre);

        findViewById(R.id.buttonAddBook).setOnClickListener(this);
        findViewById(R.id.textViewViewBooks).setOnClickListener(this);
        findViewById(R.id.buttonClear).setOnClickListener(this);
    }

    private void addBook() {
        String name = editTextBook.getText().toString().trim();
        String genre = spinnerGenre.getSelectedItem().toString();
        String author = editTextAuthor.getText().toString().trim();

        if (name.isEmpty()) {
            editTextBook.setError("Моля попълнете заглавие на книгата!");
            editTextBook.requestFocus();
            return;
        }

        if(author.isEmpty()) {
            editTextAuthor.setError("Моля попълнете автор на книгата!");
            editTextAuthor.requestFocus();
            return;
        }

        if(mDatabase.addBook(name, genre, author))
            Toast.makeText(this, "Книгата е добавена!", Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(this, "Книгата не е добавена!", Toast.LENGTH_SHORT).show();
    }

    private void clearBook() {
        String name = editTextBook.getText().toString().trim();
        String author = editTextAuthor.getText().toString().trim();

        if (name.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Вече е празно!", Toast.LENGTH_SHORT).show();
        } else {
            editTextBook.setText("");
        }

        if (author.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Вече е празно!", Toast.LENGTH_SHORT).show();
        } else {
            editTextAuthor.setText("");
        }
    }

   @Override
    public void onClick(View view) {
        switch(view.getId()) {
            case R.id.buttonAddBook:
                addBook();
                break;

            case R.id.textViewViewBooks:
                startActivity(new Intent(this, BookActivity.class));
                break;

            case R.id.buttonClear:
                clearBook();
                break;
        }
    }
}