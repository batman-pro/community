package life.majiang.community.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import life.majiang.community.dto.QuestionDTO;
import life.majiang.community.mapper.QuesstionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionService {

    @Autowired
    private QuesstionMapper quesstionMapper;

    public List<QuestionDTO> list(Integer page, int pageSize) {
        PageHelper.startPage(page, pageSize);
        List<QuestionDTO> list = quesstionMapper.findList();
        return list;
    }
}
