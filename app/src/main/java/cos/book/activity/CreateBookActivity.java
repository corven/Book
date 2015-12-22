package cos.book.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

import cos.book.R;
import cos.book.database.dao.BookDao;
import cos.book.model.Book;

public class CreateBookActivity extends AppCompatActivity {

    EditText title, name;
    BookDao bookDao;
    public final static String TITLE = "title", NAME = "name";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_book);

        final Button addBtn = (Button)findViewById(R.id.btnAdd);
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Book book = createBook();
                if (!bookExists(book)) {
                    bookDao.create(book);
                    Intent intent = new Intent();
                    intent.putExtra(TITLE, title.getText().toString());
                    intent.putExtra(NAME, name.getText().toString());
                    setResult(RESULT_OK, intent);
                    Toast.makeText(getApplicationContext(), title.getText().toString() +
                                    " был добавлен в список", Toast.LENGTH_SHORT).show();
                    finish();

                } else {
                    Toast.makeText(getApplicationContext(), title.getText().toString() +
                                    " уже существует. Пожалуйста, используйте другое название",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

        title = (EditText)findViewById(R.id.txtTitle);
        title.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                addBtn.setEnabled(!title.getText().toString().trim().isEmpty());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        name = (EditText)findViewById(R.id.txtName);

        bookDao = new BookDao(this);
    }

    private Book createBook() {
        return new Book(bookDao.getBooksCount(),
                title.getText().toString(),
                name.getText().toString());
    }

    private boolean bookExists(Book book){
        String title = book.getTitle();
        List<Book> books = bookDao.getAllBooks();
        int bookCount = books.size();

        for (int i = 0; i < bookCount; i++){
            if (title.compareToIgnoreCase(books.get(i).getTitle()) == 0){
                return true;
            }
        }
        return false;
    }

}
