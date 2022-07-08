package com.revature.trailmates.group;


import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupRepository extends CrudRepository<Group, String> {

    @Modifying
    @Query(value = "insert into group values(?1,?2,?3,0)", nativeQuery = true)
    void createGroup(String id, String gcID, String name);

    @Modifying
    @Query(value = "insert into user_groups values(?1, ?2)", nativeQuery = true)
    void addUser(String userID, String groupID);

    @Query(value = "select group_id from user_groups as u inner join \"group\" as g \n" +
            "on u.group_id = g.id;\n" +
            "where g.name = ?1", nativeQuery = true)
    String retrieveGroupID(String groupName);

    @Query(value = "select user_id from user_groups where user_id = ?1 and group_id = ?2", nativeQuery = true)
    String duplicateUser(String userID, String groupID);

    @Query(value = "select id from groups where name = ?1", nativeQuery = true)
    String duplicateGroup(String groupID);
}
