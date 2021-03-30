package com.example.libproj;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.List;

public class BookActivity extends AppCompatActivity {

    DatabaseManager mDatabase;
    List<Book> bookList;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);

        mDatabase = new DatabaseManager(this);
        bookList = new ArrayList<>();
        listView = findViewById(R.id.listViewBooks);
        loadBooksFromDatabase();
    }

    private void loadBooksFromDatabase() {

        Cursor cursor = mDatabase.getAllBooks(); //
        if(cursor.moveToFirst()) {
            bookList.clear();
            do{
                bookList.add(new Book(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3)
                ));
            } while(cursor.moveToNext());

            BookAdapter adapter = new BookAdapter(this, R.layout.list_layout_books, bookList, mDatabase );
            listView.setAdapter(adapter);
        }
    }
}