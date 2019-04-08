package com.inchwisp.tale.system.service.Imp;

import com.inchwisp.tale.framework.util.FileUtil;
import com.inchwisp.tale.system.dao.DirectorRepository;
import com.inchwisp.tale.system.dao.MovieRepository;
import com.inchwisp.tale.system.model.Director;
import com.inchwisp.tale.system.service.DirectorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * @ClassName: DirectorServiceImpl
 * @Description: 导演模块服务层实现类
 * @Author: MSI
 * @Date: 2019/1/19 14:36
 * @Vresion: 1.0.0
 **/
@Service
public class DirectorServiceImp implements DirectorService {

    @Autowired
    DirectorRepository directorRepository;
    
    @Autowired
    MovieRepository movieRepository;

    /**
     * @Author MSI
     * @Description 根据姓名和生日查询导演，以免重复
     * @Content: TODO
     * @Date 2019/1/25 11:15
     * @Param [name, birthday]
     * @return Director
     **/
    @Override
    public Director findByNameAndBirthday(String name, String birthday) {
        return directorRepository.findByNameAndBirthday(name,birthday);
    }

    /**
     * @Author MSI
     * @Description 删除导演的之前需要删除此导演的照片，置空此导演关联的电影
     * @Content: TODO
     * @Date 2019/1/25 10:54
     * @Param [director]
     * @return void
     **/

    @Override
    @Transactional(rollbackFor = Exception.class) //三个步骤事务相关，必须全部完成或全部不完成
    public void deleteDirector(Director director) {
        FileUtil.deleteDir(new File(director.getImage())); //删除导演的照片
        //movieRepository.updateOnDirector(director.getId()); //置空此导演关联的电影
        directorRepository.delete(director); //删除导演
    }

    @Override
    public Page<Director> pageDirector(String name, Pageable pageable) {
        return directorRepository.findAllByNameContaining(name,pageable);
    }

    @Override
    public void saveDirector(Director director) {
        directorRepository.save(director);
    }

    @Override
    public Director findById(long id) {
        Optional<Director> directorOptional = directorRepository.findById(id);
        return directorOptional.isPresent() ? directorOptional.get() : null;
    }

    @Override
    public List<Director> findAll() {
        return directorRepository.findAll();
    }
}
