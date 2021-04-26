package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Files;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface FilesMapper {

    @Select("SELECT * FROM FILES WHERE userid = #{userid}")
    List<Files> getAllFiles(Integer userid);

    @Select("SELECT * FROM FILES WHERE fileName = #{fileName} AND userid = #{userid}")
    Files getFileDetail(String fileName, Integer userid);

    @Select("SELECT count(*) FROM FILES WHERE fileName = #{fileName} AND userid = #{userid}")
    boolean fileNameExists(String fileName, Integer userid);

    @Delete("DELETE FROM FILES WHERE fileName = #{fileName} AND userid = #{userid}")
    boolean deleteFileForUser(String fileName, Integer userid);

    @Insert("INSERT INTO FILES (fileName, contenttype, filesize, userid, fileData) VALUES(#{fileName}, #{contenttype}, #{filesize}, #{userid}, #{fileData} )")
    @Options(useGeneratedKeys = true, keyProperty = "fileId")
    int addFile(Files files);

}
