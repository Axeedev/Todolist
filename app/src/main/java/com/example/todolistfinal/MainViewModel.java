package com.example.todolistfinal;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Action;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MainViewModel extends AndroidViewModel {
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private  NoteDatabase noteDatabase;
    private MutableLiveData<List<Note>> notes = new MutableLiveData<>();


    public MainViewModel(@NonNull Application application) {
        super(application);
        noteDatabase = NoteDatabase.getInstance(application);
    }

    public void removeAll(){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                noteDatabase.notesDao().removeAll();
            }
        });
        thread.start();
    }
    public LiveData<List<Note>> getNotes(){
        return noteDatabase.notesDao().getNotes();
    }


    public void remove(Note note){
        Disposable disposable = noteDatabase.notesDao().remove(note.getId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action() {
                    @Override
                    public void run() throws Throwable {
                        Log.d("tag", "msg");
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Throwable {
                        Log.d("tag", "msg123");
                    }
                });
        compositeDisposable.add(disposable);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.dispose();
    }
}
