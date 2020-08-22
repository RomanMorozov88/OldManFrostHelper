package morozov.ru.oldmanfrostservice.repositories.interfaces;

import morozov.ru.oldmanfrostservice.models.notes.NoteOfDone;

import java.util.List;

public interface DoneListRepo {
    NoteOfDone saveNote(NoteOfDone input);
    NoteOfDone getByName(String name);
    List<NoteOfDone> getAll();
}