package com.example.todolistfinal;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Action;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class AddNoteViewModel extends AndroidViewModel {
    private NoteDatabase noteDatabase;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private MutableLiveData<Boolean> shouldClose = new MutableLiveData<>();

    public AddNoteViewModel(@NonNull Application application) {
        super(application);
        noteDatabase = NoteDatabase.getInstance(application);
    }

    public void add(Note note) {
        Disposable disposable = noteDatabase.notesDao().add(note)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action() {
                    @Override
                    public void run() throws Throwable {
                        shouldClose.setValue(true);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Throwable {
                        Log.d("main model", "error");
                    }
                });
        compositeDisposable.add(disposable);
    }


    public LiveData<Boolean> getShouldClose() {
        return shouldClose;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.dispose();
    }
}
