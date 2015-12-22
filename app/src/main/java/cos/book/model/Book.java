package cos.book.model;


public class Book {

    private String title, name;
    private int id;

    public Book(int id, String title, String name) {
        this.id = id;
        this.title = title;
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }
}
