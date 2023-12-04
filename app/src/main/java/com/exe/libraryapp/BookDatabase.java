package com.exe.libraryapp;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Book.class}, version = 1, exportSchema = false)
public abstract class BookDatabase extends RoomDatabase {
    private static BookDatabase databaseInstance;
    static final ExecutorService databaseWriteExecutor = Executors.newSingleThreadExecutor();

    public abstract BookDao bookDao();

    static BookDatabase getDatabase(final Context context) {
        if (databaseInstance == null) {
            databaseInstance = Room.databaseBuilder(context.getApplicationContext(),
                            BookDatabase.class, "book_database")
                    .addCallback(roomDatabaseCallback)
                    .build();
        }
        return databaseInstance;
    }

    private static final Callback roomDatabaseCallback = new Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            databaseWriteExecutor.execute(() -> {
                BookDao dao = databaseInstance.bookDao();
                Book book = new Book("Clean Code", "Robert C.Martin");
                dao.insert(book);
                Book book2 = new Book("The Pragmatic Programmer", "Andrew Hunt, David Thomas");
                dao.insert(book2);
                Book book3 = new Book("The Hobbit", "J.R.R. Tolkien");
                dao.insert(book3);
            });
        }
    };
}
//onCreate:
//Metoda ta jest wywoływana, gdy instancja bazy danych jest tworzona po raz pierwszy na urządzeniu.
//Jest to miejsce, gdzie można zdefiniować początkowe ustawienia bazy danych, stworzyć początkowe tabele i/lub wypełnić danymi bazę, jeżeli to konieczne.
//Ta metoda jest wywoływana tylko raz, gdy baza danych jest tworzona.

//onOpen:
//Metoda ta jest wywoływana, gdy baza danych zostanie otwarta.
//Można w niej wykonywać operacje, które są wykonywane przy każdym otwarciu bazy danych, na przykład wykonanie aktualizacji struktury bazy danych.
//Jest wywoływana zarówno przy pierwszym otwarciu bazy danych, jak i przy późniejszych otwarciach.

