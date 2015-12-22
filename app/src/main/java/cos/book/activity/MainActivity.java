package cos.book.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import cos.book.R;
import cos.book.adapter.BookListAdapter;
import cos.book.database.dao.BookDao;
import cos.book.model.Book;

public class MainActivity extends AppCompatActivity {

    ListView lvBooks;
    ArrayList<Book> books = new ArrayList<>();
    ArrayAdapter<Book> adapter;
    private EditText nameTxt, titleTxt;
    private static final int ADD = 0;
    BookDao bookDao;
    ArrayList<String> selectPositions = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_book);

        nameTxt = (EditText)findViewById(R.id.txtName);
        titleTxt = (EditText)findViewById(R.id.txtTitle);

        FloatingActionButton fab = (FloatingActionButton)findViewById(R.id.fabAdd);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, CreateBookActivity.class);
                startActivityForResult(intent, ADD);
            }
        });

        lvBooks = (ListView)findViewById(R.id.lvBook);
        bookDao = new BookDao(this);
        books = bookDao.getAllBooks();
        adapter = new BookListAdapter(this, books);
        lvBooks.setAdapter(adapter);
        lvBooks.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
        lvBooks.setMultiChoiceModeListener(new AbsListView.MultiChoiceModeListener() {
            @Override
            public void onItemCheckedStateChanged(ActionMode mode, int position, long id, boolean checked) {
                if (checked) {
                    selectPositions.add(String.valueOf(position));
                } else {
                    selectPositions.remove(String.valueOf(position));
                    int a = 5;

                }
            }

            @Override
            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                mode.getMenuInflater().inflate(R.menu.menu_context_book, menu);
                return true;
            }

            @Override
            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                return false;
            }

            @Override
            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                //Удаление
                if (item.getItemId() == R.id.item_delete_books) {
                    ArrayList<Book> cloneLessons = (ArrayList<Book>) books.clone();
                    for (String position : selectPositions) {
                        Book book = cloneLessons.get(Integer.parseInt(position));
                        bookDao.delete(book);
                        books.remove(book);
                    }
                }
                mode.finish();
                return false;
            }

            @Override
            public void onDestroyActionMode(ActionMode mode) {
                selectPositions.clear();
                mode.finish();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ADD & resultCode == RESULT_OK){
            String title = data.getStringExtra(CreateBookActivity.TITLE);
            String name = data.getStringExtra(CreateBookActivity.NAME);
            books.add(createBook(title, name));
            adapter.notifyDataSetChanged();
            Toast.makeText(getApplicationContext(), title + " был добавлен в список",
                    Toast.LENGTH_SHORT).show();
        }
    }

    private Book createBook(String title, String name) {
        return new Book(bookDao.getBooksCount(), title, name);
    }
}
