package com.exe.libraryapp;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class BookViewModel extends AndroidViewModel {
    private final BookRepository bookRepository;
    private final LiveData<List<Book>> books;

    public BookViewModel(@NonNull Application application) {
        super(application);
        bookRepository = new BookRepository(application);
        books = bookRepository.findAllBooks();
    }

    public LiveData<List<Book>> findAll() {
        return books;
    }

    public void insert(Book book) {
        bookRepository.insert(book);
    }

    public void update(Book book) {
        bookRepository.update(book);
    }

    public void delete(Book book) {
        bookRepository.delete(book);
    }
}
//ViewModel pośredniczy w komunikacji między repozytorium z danymi a interfejsem graficznym.
//Instancje ViewModel zostają zachowane w trakcie tworzenia od nowa aktywności lub fragmentu
//(np. przy obracaniu ekranu). Dzięki temu możliwe jest zachowanie stanu interfejsu graficznego