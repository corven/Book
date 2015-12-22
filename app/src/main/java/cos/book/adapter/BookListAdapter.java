package cos.book.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import cos.book.R;
import cos.book.model.Book;

public class BookListAdapter extends ArrayAdapter<Book> {
    private List<Book> books;

    public BookListAdapter(Context context, List<Book> books) {
        super(context, R.layout.listview_item, books);
        this.books = books;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.listview_item, parent, false);
        }

        Book book = books.get(position);

        TextView title = (TextView)view.findViewById(R.id.title);
        TextView name= (TextView)view.findViewById(R.id.name);

        title.setText(book.getTitle());
        name.setText(book.getName());

        return view;
    }
}
