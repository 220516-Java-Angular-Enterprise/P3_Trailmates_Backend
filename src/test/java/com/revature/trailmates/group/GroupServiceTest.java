package com.revature.trailmates.group;

import com.revature.trailmates.user.User;
import com.revature.trailmates.util.custom_exception.InvalidRequestException;
import com.revature.trailmates.util.custom_exception.ResourceConflictException;
import org.hibernate.collection.internal.PersistentList;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GroupServiceTest {

    @Mock
    private GroupRepository repo;

    @InjectMocks
    private GroupService service;

    @Spy
    Group group;

    @Test
    void getGroups(){
        List<Group> groupList = new ArrayList<>();
        groupList.add(new Group());
        groupList.get(0).setName("213");
        group.setName("name");
        group.setId("123");
        when(repo.getAllGroups()).thenReturn(groupList);
        assertEquals(groupList, service.getAllGroups());
    }

    @Test
    void createNewGroup(){
        String groupID = UUID.randomUUID().toString();
        String conversationID = UUID.randomUUID().toString();
        doAnswer(invocationOnMock -> null).when(repo).createGroup(any(String.class), any(String.class), any(String.class));
        repo.createGroup(groupID,conversationID,"name");
        service.createNewGroup("ad", "name");
    }

    @Test
    void addUserToGroup(){
        doAnswer(invocationOnMock -> null).when(repo).addUser(any(String.class), any(String.class));
        when(repo.retrieveGroupID(any(String.class))).thenReturn("123");
        repo.addUser("123", "1234");
        service.addUserToGroup("1234", "name");
    }

    @Test
    void editGroup(){
        doAnswer(invocationOnMock -> null).when(repo).editGroupName(any(String.class), any(String.class));
        when(repo.retrieveGroupID(any(String.class))).thenReturn("123");
        repo.editGroupName("name", "123");
        service.editGroup("name", "123456");
    }

    @Test
    void removeUserFromGroup(){
        doAnswer(invocationOnMock -> null).when(repo).removeUser(any(String.class), any(String.class));
        when(repo.retrieveGroupID(any(String.class))).thenReturn("123");
        repo.removeUser("1234", "123");
        service.removeUserFromGroup("1234", "name");
    }

    @Test
    void duplicateGroup() {
        String groupName = "";
        when(repo.duplicateGroup("demo2")).thenReturn(groupName);
        Exception e = assertThrows(ResourceConflictException.class, () -> service.createNewGroup("das", "demo2"));
        assertEquals("Group already exists.", e.getMessage());
    }

    @Test
    void addUserToNonExistentGroup() {
        when(repo.retrieveGroupID(any(String.class))).thenReturn(null);
        Exception e = assertThrows(InvalidRequestException.class, () -> service.addUserToGroup("123", "demo2"));
        assertEquals("Group doesn't exist.", e.getMessage());
    }

    @Test
    void addUserToSameGroup() {
        String groupName = "demo";
        when(repo.retrieveGroupID(any(String.class))).thenReturn("123");
        when(repo.duplicateUser(any(String.class), any(String.class))).thenReturn(groupName);
        Exception e = assertThrows(ResourceConflictException.class, () -> service.addUserToGroup("c4f9758d-75db-41bf-9432-3c24eea6700b", "demo2"));
        assertEquals("You are already in this group.", e.getMessage());
    }

    @Test
    void removeNonExistentGroup() {
        when(repo.retrieveGroupID(any(String.class))).thenReturn(null);
        Exception e = assertThrows(InvalidRequestException.class, () -> service.removeUserFromGroup("123", "demo2"));
        assertEquals("Group doesn't exist.", e.getMessage());
    }


    @Test
    void editGroupSameName() {
        Exception e = assertThrows(ResourceConflictException.class, () -> service.editGroup("demo", "demo"));
        assertEquals("Group name needs to be different.", e.getMessage());
    }

    @Test
    void editGroupDuplicateName() {
        String groupName = "";
        when(repo.duplicateGroup("demo2")).thenReturn(groupName);
        Exception e = assertThrows(ResourceConflictException.class, () -> service.editGroup("demo2", "demo"));
        assertEquals("Name is already taken.", e.getMessage());
    }

    @Test
    void getUsers() {
        List<User> userList = new ArrayList<>();
        userList.add(new User());
        userList.get(0).setUsername("213");
        group.setUsers(userList);
        group.setName("name");
        group.setId("123");
        when(repo.retrieveGroupID(any(String.class))).thenReturn("123");
        when(repo.getUsers(any(String.class))).thenReturn(group);
        assertEquals(group.getUsers(), service.getUsers(group.getName()));
    }
}