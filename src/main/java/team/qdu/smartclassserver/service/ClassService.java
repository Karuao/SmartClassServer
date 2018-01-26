package team.qdu.smartclassserver.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import team.qdu.smartclassserver.dao.ClassMapper;

@Service
public class ClassService  {

    @Autowired
    ClassMapper classMapper;

    public String getJoinedClasses(Integer userId) {
        return null;
    }
}
