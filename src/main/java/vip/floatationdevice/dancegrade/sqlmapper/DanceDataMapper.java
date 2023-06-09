package vip.floatationdevice.dancegrade.sqlmapper;

import org.apache.ibatis.annotations.*;
import vip.floatationdevice.dancegrade.data.DanceData;

import java.util.List;

@Mapper
public interface DanceDataMapper
{
// ==================== READ RELATED FUNCTIONS ====================

    @Select("SELECT COUNT(*) FROM danceData")
    int getDataCount();

    @Select("SELECT * FROM danceData")
    List<DanceData> getAllData();

    @Select("SELECT * FROM danceData LIMIT #{offset}, 10")
    List<DanceData> getPagedData(@Param("offset") int offset);

    @Select("SELECT COUNT(*) FROM danceData WHERE id = #{id}")
    int hasData(@Param("id") int id);

    @Select("SELECT * FROM danceData WHERE id = #{id}")
    DanceData getData(@Param("id") int id);

    @Select({
            "<script>",
            "SELECT * FROM danceData",
            "<where>",
            "<if test='name != null'>",
            "name LIKE printf('%%%s%%', #{name})",
            "</if>",
            "<if test='studentId != null'>",
            "AND studentId = #{studentId}",
            "</if>",
            "</where>",
            "LIMIT #{offset}, 10",
            "</script>"
    })
    List<DanceData> findData(@Param("name") String name, @Param("studentId") Integer studentId, @Param("offset") int offset);

    @Select({
            "<script>",
            "SELECT COUNT(*) FROM danceData",
            "<where>",
            "<if test='name != null'>",
            "name LIKE printf('%%%s%%', #{name})",
            "</if>",
            "<if test='studentId != null'>",
            "AND studentId = #{studentId}",
            "</if>",
            "</where>",
            "</script>"
    })
    int findCount(@Param("name") String name, @Param("studentId") Integer studentId);

// ==================== WRITE RELATED FUNCTIONS ====================

    // insert data
    @Insert("INSERT INTO danceData(studentId, name, scores, actions, scoreAvg) VALUES(#{studentId}, #{name}, #{scores}, #{actions}, #{scoreAvg})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insertData(DanceData p);

    // delete data
    @Delete("DELETE FROM danceData WHERE id = #{id}")
    int deleteData(@Param("id") int id);
}
