package com.library.ui.components;

import com.library.backend.Book;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.data.provider.ListDataProvider;
import utils.StringUtils;

import java.util.List;

public class BookGrid  extends Grid<Book> {
    private final ListDataProvider<Book> dataProvider;

    public BookGrid(List<Book> allBooks) {
        dataProvider = new ListDataProvider<>(allBooks);
        setDataProvider(dataProvider);
        addColumn(Book::getTitle).setHeader("Title").setSortable(true);
        addColumn(Book::getAuthor).setHeader("Author").setSortable(true);
        addColumn(Book::getIsbn).setHeader("ISBN");
    }

    public void filter(String searchTerm) {
        dataProvider.setFilter(book ->
            searchTerm == null ||
            searchTerm.isEmpty() ||
            StringUtils.containsIgnoreCase(book.getTitle(), searchTerm) ||
            StringUtils.containsIgnoreCase(book.getAuthor(), searchTerm) ||
            StringUtils.containsIgnoreCase(book.getIsbn(), searchTerm)
        );
    }
}
