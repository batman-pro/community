package life.majiang.community.mapper;

import life.majiang.community.dto.QuestionDTO;
import life.majiang.community.model.Question;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface QuesstionMapper {
    @Insert("insert into question (title, description, gmt_create, gmt_modified, creator, tag) " +
            "values(#{title}, #{description}, #{gmtCreate}, #{gmtModified}, #{creator}, #{tag})")
    void create(Question question);

    @Select("select * from question")
    @Results({
            @Result(
                    property = "user",
                    column = "creator",
                    one = @One(select = "life.majiang.community.mapper.UserMapper.findById"))
    })
    List<QuestionDTO> findList();

}
