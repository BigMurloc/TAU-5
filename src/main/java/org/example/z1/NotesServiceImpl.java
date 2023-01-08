package org.example.z1;

import java.util.Collection;

class NotesServiceImpl implements NotesService {

    public static NotesServiceImpl createWith(final NotesStorage storageService) {
        return new NotesServiceImpl(storageService);
    }

    private final NotesStorage storageService;
    private NotesServiceImpl(final NotesStorage storageService) {
        this.storageService = storageService;
    }

    @Override
    public void add(Note note) {
        storageService.add(note);
    }

    @Override
    public float averageOf(String name) {
        float sum = 0.0f;
        final Collection<Note> notes = storageService.getAllNotesOf(name);
        for (final Note note: notes) {
            sum += note.getNote();
        }
        return sum / notes.size();
    }

    @Override
    public void clear() {
        storageService.clear();
    }

}
