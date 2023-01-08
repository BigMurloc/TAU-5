package org.example.notes;

class Note {
    private final String name;
    private final float note;

    float getNote() {
        return note;
    }

    String getName() {
        return name;
    }

    public Note(String name, float note) {
        this.note = note;
        this.name = name;
    }
}
