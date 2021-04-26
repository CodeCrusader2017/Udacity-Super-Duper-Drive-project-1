package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.Numutility;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CredentialMapper {
    @Select("SELECT * FROM CREDENTIALS WHERE userid = #{userid}")
    @Results({
            @Result(id = true, property = "credentialsId", column = "credentialid"),
            @Result(property = "url", column = "url"),
            @Result(property = "username", column = "username"),
            @Result(property = "key", column = "key"),
            @Result(property = "password", column = "password"),
            @Result(property = "userid", column = "userid")
    })
    List<Credential> getUserCredentials(Integer userid);

    @Insert("INSERT INTO CREDENTIALS (url, username, key, password, userid) VALUES(#{url},#{username}, #{key}, #{password}, #{userid})")
    @Options(useGeneratedKeys = true, keyProperty = "credentialsId")
    int insert(Credential credential);

    @Update("UPDATE CREDENTIALS SET url=#{url}, username=#{username}, key=#{key}, password=#{password} WHERE credentialid=#{credentialsId}" )
    int updateCredential(Credential credential);

    @Delete("DELETE FROM CREDENTIALS WHERE credentialid =#{credentialsId}")
    int deleteCredential(int credentialId);

    @Select("SELECT password FROM CREDENTIALS WHERE key =#{key}")
    String getPassword(String key);

    //Despite following https://knowledge.udacity.com/questions/495060, unable to get auto_increment on database column credentialid (on table CREDENTIALS) to work on insert, so created Numutility table as a workaround to store key values to use in CREDENTIALS on insert
    @Select("SELECT MAX(numid) FROM NUMUTILITY")
    Integer getNumid();

    @Insert("INSERT INTO NUMUTILITY (dummy) VALUES(#{dummy})")
    @Options(useGeneratedKeys = true, keyProperty = "numid")
    int insertUtil(Numutility numutility);

}
