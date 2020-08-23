package morozov.ru.oldmanfrostservice.repositories.interfaces;

import morozov.ru.oldmanfrostservice.models.notes.NoteOfWaiting;

import java.util.List;

public interface WaitingListRepo {

    NoteOfWaiting save(NoteOfWaiting input);

    List<NoteOfWaiting> getAll();
}
