package org.example.notes;

import org.easymock.EasyMock;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;

class NotesServiceImplTestUsingEasyMock {

    private final NotesStorage mock = EasyMock.createMock(NotesStorage.class);

    @Test
    void shouldAddNoteSuccessfully() {
        //given
        String name = "John";
        final NotesServiceImpl sut = NotesServiceImpl.createWith(mock);
        final Note note = new Note(name, 2);
        expect(mock.getAllNotesOf(name)).andReturn(List.of(note));
        replay(mock);

        //then
        assertThat(sut.averageOf(name)).isEqualTo(2);
    }

    @Test
    void shouldCalculateAverageStudentNotesIndividually() {
        //given
        String student = "John";
        String otherStudent = "Alice";
        final NotesServiceImpl sut = NotesServiceImpl.createWith(mock);
        final Note studentNote = new Note(student, 4);
        final Note studentNote1 = new Note(student, 6);
        final Note studentNote2 = new Note(student, 2);
        final Note otherStudentNote = new Note(otherStudent, 3);
        final Note otherStudentNote1 = new Note(otherStudent, 4);
        final Note otherStudentNote2 = new Note(otherStudent, 2);

        expect(mock.getAllNotesOf(student)).andReturn(List.of(studentNote, studentNote1, studentNote2));
        expect(mock.getAllNotesOf(otherStudent)).andReturn(List.of(otherStudentNote, otherStudentNote1, otherStudentNote2));
        replay(mock);

        //then
        assertThat(sut.averageOf(student)).isEqualTo(4);
        assertThat(sut.averageOf(otherStudent)).isEqualTo(3);
    }

    @Test
    void shouldCalculateAverageNote() {
        //given
        String name = "John";
        final NotesServiceImpl sut = NotesServiceImpl.createWith(mock);
        final Note note1 = new Note(name, 2);
        final Note note2 = new Note(name, 3);
        final Note note3 = new Note(name, 4);

        expect(mock.getAllNotesOf(name)).andReturn(List.of(note1, note2, note3));
        replay(mock);

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


    @Test
    void shouldAddToList() {
        NotesStorage mock = mock(NotesStorage.class);

        List<Note> notes = new ArrayList<>();
        Note note = new Note("John", 4);

        doAnswer(new Answer<Void>() {
            @Override
            public Void answer(InvocationOnMock invocation) throws Throwable {
                Note note = invocation.getArgument(0);
                notes.add(note);

                return null;
            }
        }).when(mock).add(note);

        mock.add(note);

        Mockito.verify(mock).add(note);
        assertTrue(notes.contains(note));
    }

    @Test
    void shouldClearList() {
        NotesStorage mock = mock(NotesStorage.class);

        List<Note> notes = new ArrayList<>();
        Note note = new Note("John", 4);
        notes.add(note);

        doAnswer(new Answer<Void>() {
            @Override
            public Void answer(InvocationOnMock invocation) throws Throwable {
                notes.clear();

                return null;
            }
        }).when(mock).clear();

        mock.clear();

        Mockito.verify(mock).clear();
        assertTrue(notes.isEmpty());
    }
}
