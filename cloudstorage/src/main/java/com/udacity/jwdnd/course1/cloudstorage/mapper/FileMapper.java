package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface FileMapper {

    @Select("SELECT * FROM FILES")
    List<File> findAll();

    @Select("SELECT * FROM FILES WHERE fileid = #{fileid}")
    File findById(int fileid);

    @Select("SELECT * FROM FILES WHERE userid = #{userid}")
    List<File> findByUserId(int userid);

    @Insert("INSERT INTO FILES (filename, contenttype, filesize, filedata, userid) " +
            "VALUES (#{file.filename}, #{file.contenttype}, #{file.filesize}, #{file.filedata}, #{userid})")
    int insert(File file, int userid);

    @Delete("DELETE FROM FILES WHERE fileid = #{fileid}")
    int delete(int fileid);
}
