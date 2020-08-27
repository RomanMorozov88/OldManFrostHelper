package morozov.ru.oldmanfrostservice.repositories.interfaces;

import morozov.ru.oldmanfrostservice.models.notes.NoteOfWaiting;

import java.util.List;

public interface WaitingListRepo {

    NoteOfWaiting saveNote(NoteOfWaiting input);

    List<NoteOfWaiting> getAll();

    void delete(NoteOfWaiting input);
}
