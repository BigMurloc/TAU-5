package org.example.notes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class NoteStorageMock implements NotesStorage {

    private final Map<String, List<Note>> nameToNoteMap = new HashMap<>();
    @Override
    public void add(Note note) {
        nameToNoteMap.computeIfAbsent(note.getName(), it -> new ArrayList<>()).add(note);
    }

    @Override
    public List<Note> getAllNotesOf(String name) {
        return nameToNoteMap.get(name);
    }

    @Override
    public void clear() {
        nameToNoteMap.clear();
    }
}
