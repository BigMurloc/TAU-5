package org.example.friendships;

import org.easymock.EasyMockExtension;
import org.easymock.Mock;
import org.easymock.MockType;
import org.easymock.TestSubject;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.easymock.EasyMock.*;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


@ExtendWith(EasyMockExtension.class)
class FriendshipsMongoEasyMockTest {

    @Mock(type = MockType.NICE)
    FriendsCollection friends;
    @TestSubject
    FriendshipsMongo friendships = new FriendshipsMongo(friends);

    @Test
    void shouldNotHaveFriends() {
        assertThat(friendships.getFriendsList("Alice")).isEmpty();
    }

    @Test
    void shouldHaveFriends() {
        Person john = new Person("John");
        john.addFriend("Alice");
        john.addFriend("Peter");
        john.addFriend("Bob");

        expect(friends.findByName("John")).andReturn(john);
        replay(friends);

        assertThat(friendships.getFriendsList("John"))
                .hasSize(3)
                .containsOnly("Alice", "Bob", "Peter");
    }

    @Test
    void shouldBeFriends() {
        Person john = new Person("John");
        john.addFriend("Alice");

        expect(friends.findByName("John")).andReturn(john);
        replay(friends);

        assertTrue(friendships.areFriends("John", "Alice"));
    }

    @Test
    void shouldNotBeFriends() {
        Person john = new Person("John");
        john.addFriend("Alice");

        expect(friends.findByName("John")).andReturn(john);
        replay(friends);

        assertFalse(friendships.areFriends("John", "Bob"));
    }
}
