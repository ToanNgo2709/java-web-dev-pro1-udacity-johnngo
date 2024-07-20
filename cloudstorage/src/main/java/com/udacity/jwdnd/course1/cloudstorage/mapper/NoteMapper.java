package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface NoteMapper {

    @Select("SELECT * FROM NOTES")
    List<Note> findAll();

    @Select("SELECT * FROM NOTES WHERE noteid = #{noteid}")
    Note findById(int noteid);

    @Select("SELECT * FROM NOTES WHERE userid = #{userid}")
    List<Note> findByUserId(int userid);

    @Insert("INSERT INTO NOTES (notetitle, notedescription, userid) " +
            "VALUES (#{note.notetitle}, #{note.notedescription}, #{userid})")
    int insert(Note note, int userid);

    @Delete("DELETE FROM NOTES WHERE noteid = #{noteid}")
    int delete(int noteid);

    @Update("UPDATE NOTES SET notetitle = #{notetitle}, notedescription = #{notedescription} WHERE noteid = #{noteid}")
    int update(Note note);
}
