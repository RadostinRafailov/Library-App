package com.example.libproj;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import java.util.List;

public class BookAdapter extends ArrayAdapter<Book> {

    Context mCtx;
    int layoutRes;
    List<Book> bookList;
    DatabaseManager mDatabase;

    public BookAdapter(Context mCtx, int layoutRes, List<Book> bookList, DatabaseManager mDatabase) {
        super(mCtx, layoutRes, bookList);

        this.mCtx = mCtx;
        this.layoutRes = layoutRes;
        this.bookList = bookList;
        this.mDatabase = mDatabase;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);

        View view = inflater.inflate(layoutRes, null);

        TextView textViewBook = view.findViewById(R.id.textViewBook);
        TextView textViewGenre = view.findViewById(R.id.textViewGenre);
        TextView textViewAuthor = view.findViewById(R.id.textViewAuthor);

        final Book book = bookList.get(position);

        textViewBook.setText(book.getBook());
        textViewGenre.setText(book.getGenre());
        textViewAuthor.setText(book.getAuthor());

        view.findViewById(R.id.buttonDeleteBook).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteBook(book);
            }

        });
        view.findViewById(R.id.buttonEditBook).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateBook(book);
            }
        });
        return view;
    }

  //  private void search (SearchView searchView) {
    //    searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
      //      @Override
        //    public boolean onQueryTextSubmit(String query) {
          //      return false;
           // }

           // @Override
           // public boolean onQueryTextChange (String newText) {
             //   if (mDatabase!=null)
               //     mCtx
           // }
       // }
   // }

    private void updateBook(final Book book) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mCtx);
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.update_book, null);
        builder.setView(view);

        final AlertDialog alertDialog = builder.create();
        alertDialog.show();

        final EditText editTextBook = view.findViewById(R.id.editTextBook);
        final Spinner spinner = view.findViewById(R.id.spinnerGenre);
        final EditText editTextAuthor = view.findViewById(R.id.editTextAuthor);

        editTextBook.setText(book.getBook());
        editTextAuthor.setText(book.getAuthor());
        spinner.getSelectedItem().toString().trim();

        view.findViewById(R.id.buttonUpdateBook).setOnClickListener(new View.OnClickListener() {  //метод, който ще бъде изпълнен когато е натиснат бутона редактирай книга
            @Override
            public void onClick(View view) {
                String name = editTextBook.getText().toString().trim();
                String gen = spinner.getSelectedItem().toString().trim();
                String author = editTextAuthor.getText().toString().trim();

                if (name.isEmpty()) {
                    editTextBook.setError("Моля попълнете заглавие на книгата!");
                    editTextBook.requestFocus();
                    return;
                }

                if (author.isEmpty()) {
                    editTextAuthor.setError("Моля попълнете автор на книгата!");
                    editTextAuthor.requestFocus();
                    return;
                }

                if (mDatabase.updateBook(book.getId(), name, gen, author)) {
                    Toast.makeText(mCtx, "Книгата е редактирана!", Toast.LENGTH_SHORT).show();
                    loadBooksFromDatabaseAgain();
                } else {
                    Toast.makeText(mCtx, "Книгата не е редактирана!", Toast.LENGTH_SHORT).show();
                }

                alertDialog.dismiss();
            }
            });
        }

        private void deleteBook(final Book book) {
            AlertDialog.Builder builder = new AlertDialog.Builder(mCtx);

            builder.setTitle("Сигурни ли сте?");
            builder.setPositiveButton("Да", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int i) {

                    if (mDatabase.deleteBook(book.getId()))
                    loadBooksFromDatabaseAgain();
                }
            });

            builder.setNegativeButton("Откажи", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                }
            });

            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }

    private void loadBooksFromDatabaseAgain() {
        Cursor cursor = mDatabase.getAllBooks();

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
            notifyDataSetChanged();
        }
    }
}