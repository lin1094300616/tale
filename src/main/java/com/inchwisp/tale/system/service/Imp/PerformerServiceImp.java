package com.inchwisp.tale.system.service.Imp;

import com.inchwisp.tale.framework.util.FileUtil;
import com.inchwisp.tale.system.dao.PerformerRepository;
import com.inchwisp.tale.system.model.Performer;
import com.inchwisp.tale.system.service.PerformerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.sql.Date;
import java.util.List;
import java.util.Optional;

/**
 * @ClassName: PerformerServiceImp
 * @Description: 演员模块服务层实现类
 * @Author: MSI
 * @Date: 2019/1/31 10:47
 * @Vresion: 1.0.0
 **/
@Service
public class PerformerServiceImp implements PerformerService {

    @Autowired
    PerformerRepository performerRepository;

    @Override
    public List<Performer> findByIdIn(List<Long> id) {
        return performerRepository.findAllByIdIn(id);
    }

    @Override
    public Performer findByNameAndBirthday(String name, Date birthday) {
        return performerRepository.findByNameAndBirthday(name, birthday);
    }

    @Override
    public void save(Performer performer) {
        performerRepository.save(performer);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Performer performer) {
        FileUtil.deleteDir(new File(performer.getImage()));
        performerRepository.delete(performer);
    }

    @Override
    public Performer findById(Long id) {
        Optional<Performer> performerOptional = performerRepository.findById(id);
        return performerOptional.isPresent() ? performerOptional.get() : null;
    }

    @Override
    public List findAll() {
        return performerRepository.findAll();
    }

    @Override
    public Page<Performer> pagePerformer(Pageable pageable) {
        return performerRepository.findAll(pageable);
    }
}
