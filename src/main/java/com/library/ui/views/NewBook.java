package com.library.ui.views;

import com.library.backend.Book;
import com.library.backend.MockBookRepository;
import com.library.ui.components.BookForm;
import com.library.ui.components.ViewToolbar;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route("books/new")
public class NewBook extends VerticalLayout {
    private final MockBookRepository bookRepo;

    public NewBook(MockBookRepository bookRepo) {
        this.bookRepo = bookRepo;

        BookForm bookform = new BookForm();
        bookform.setBook(new Book());
        bookform.setEditable(true);
        bookform.addSaveListener(this::saveBook);
        bookform.addCancelListener(() -> getUI().ifPresent(ui -> ui.navigate("books")));

        add(new ViewToolbar("New Book"), bookform);
    }

    private void saveBook(Book book) {
        bookRepo.save(book);
        getUI().ifPresent(ui -> ui.navigate("books"));
    }
}
