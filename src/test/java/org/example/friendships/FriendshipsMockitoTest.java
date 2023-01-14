package org.example.friendships;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.Mockito.when;

class FriendshipsMockitoTest {
    @Mock
    private FriendsCollection friendsCollection;

    private FriendshipsMongo friendships;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        friendships = new FriendshipsMongo(friendsCollection);
    }

    @Test
    public void shouldHaveFriends() {
        Person john = new Person("John");
        john.addFriend("Alice");
        john.addFriend("Peter");
        john.addFriend("Bob");

        when(friendsCollection.findByName("John")).thenReturn(john);

        List<String> friends = friendships.getFriendsList("John");

        assertThat(friendships.getFriendsList("John"))
                .hasSize(3)
                .containsOnly("Alice", "Bob", "Peter");
    }

    @Test
    public void shouldReturnEmptyListWhenUnknownPerson() {
        when(friendsCollection.findByName("John")).thenReturn(null);

        assertThat(friendships.getFriendsList("John")).isEmpty();
    }

    @Test
    public void shouldMakeFriends() {
        String johnName = "John";
        String aliceName = "Alice";
        Person john = new Person(johnName);
        Person alice = new Person(aliceName);

        when(friendsCollection.findByName(johnName)).thenReturn(john);
        when(friendsCollection.findByName(aliceName)).thenReturn(alice);

        friendships.makeFriends(johnName, aliceName);

        assertThat(john.getFriends())
                .hasSize(1)
                .containsOnly(aliceName);
        assertThat(alice.getFriends())
                .hasSize(1)
                .containsOnly(johnName);
    }

    @Test
    public void shouldBeFriends() {
        Person john = new Person("John");
        john.addFriend("Alice");

        when(friendsCollection.findByName("John")).thenReturn(john);

        assertThat(friendships.areFriends("John", "Alice")).isTrue();
    }

    @Test
    void shouldNotBeFriends() {
        Person john = new Person("John");
        john.addFriend("Alice");

        when(friendsCollection.findByName("John")).thenReturn(john);

        assertThat(friendships.areFriends("John", "Bob")).isFalse();
    }

    @Test
    public void shouldAddFriend() {
        Person john = new Person("John");

        when(friendsCollection.findByName("John")).thenReturn(john);

        friendships.addFriend("John", "Alice");

        assertThat(john.getFriends())
                .hasSize(1)
                .containsOnly("Alice");
    }
}
