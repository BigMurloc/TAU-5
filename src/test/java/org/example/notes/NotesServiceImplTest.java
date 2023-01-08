package org.example.notes;


import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;


class NotesServiceImplTest {


    @Test
    void shouldAddNoteSuccessfully() {
        //given
        String name = "John";
        final NotesServiceImpl sut = NotesServiceImpl.createWith(new NoteStorageMock());
        final Note note = new Note(name, 2);

        //when
        sut.add(note);

        //then
        assertThat(sut.averageOf(name)).isEqualTo(2);
    }

    @Test
    void shouldCalculateAverageStudentNotesIndividually() {
        //given
        String student = "John";
        String otherStudent = "Alice";
        final NotesServiceImpl sut = NotesServiceImpl.createWith(new NoteStorageMock());
        final Note studentNote = new Note(student, 4);
        final Note studentNote1 = new Note(student, 6);
        final Note studentNote2 = new Note(student, 2);
        final Note otherStudentNote = new Note(otherStudent, 3);
        final Note otherStudentNote1 = new Note(otherStudent, 4);
        final Note otherStudentNote2 = new Note(otherStudent, 2);

        //when
        sut.add(studentNote);
        sut.add(studentNote1);
        sut.add(studentNote2);
        sut.add(otherStudentNote);
        sut.add(otherStudentNote1);
        sut.add(otherStudentNote2);

        //then
        assertThat(sut.averageOf(student)).isEqualTo(4);
        assertThat(sut.averageOf(otherStudent)).isEqualTo(3);
    }

    @Test
    void shouldCalculateAverageNote() {
        //given
        String name = "John";
        final NotesServiceImpl sut = NotesServiceImpl.createWith(new NoteStorageMock());
        final Note note1 = new Note(name, 2);
        final Note note2 = new Note(name, 3);
        final Note note3 = new Note(name, 4);

        //when
        sut.add(note1);
        sut.add(note2);
        sut.add(note3);

        //then
        assertThat(sut.averageOf(name)).isEqualTo(3);
    }

    @Test
    void shouldClearNotes() {
        //given
        final NoteStorageMock mock = new NoteStorageMock();
        final NotesServiceImpl sut = NotesServiceImpl.createWith(mock);

        //when
        assertDoesNotThrow(sut::clear);
    }
}
