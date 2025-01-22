package cn.edu.xidian.aws.service;

import cn.edu.xidian.aws.constant.WorkStatus;
import cn.edu.xidian.aws.exception.AwsArgumentException;
import cn.edu.xidian.aws.exception.AwsNotFoundException;
import cn.edu.xidian.aws.pojo.po.Work;
import cn.edu.xidian.aws.pojo.vo.work.WorkAddVO;
import cn.edu.xidian.aws.pojo.vo.work.WorkUpdateVO;
import cn.edu.xidian.aws.repository.WorkRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author akynazh@gmail.com
 * @date 2025/1/16
 * @description
 */
@Service
public class WorkService {

    @Autowired
    private WorkRepository workRepository;

    public Work addWork(WorkAddVO vo) {
        if (vo == null) {
            throw new AwsArgumentException();
        }
        Work work = new Work();
        work.setProduceId(vo.getProduceId());
        long startTime = vo.getStartTime();
        long endTime = vo.getEndTime();
        work.setStartTime(startTime);
        work.setEndTime(endTime);
        work.setDataValue(new BigDecimal(0));
        work.setUnit(0);
        work.setCreateTime(System.currentTimeMillis());
        work.setUpdateTime(System.currentTimeMillis());
        if (System.currentTimeMillis() >= startTime && System.currentTimeMillis() <= endTime) {
            work.setStatus(WorkStatus.ONGOING.getCode());
        } else if (System.currentTimeMillis() > endTime) {
            work.setStatus(WorkStatus.FINISHED.getCode());
        } else if (System.currentTimeMillis() < startTime) {
            work.setStatus(WorkStatus.NOT_STARTED.getCode());
        }
        return workRepository.save(work);
    }

    public Work updateWork(WorkUpdateVO vo) {
        if (vo == null) {
            throw new AwsArgumentException();
        }
        Work work = workRepository.findById(vo.getId()).orElseThrow(AwsNotFoundException::new);
        if (work == null) {
            throw new AwsNotFoundException();
        }
        BeanUtils.copyProperties(vo, work);
        work.setUpdateTime(System.currentTimeMillis());
        if (System.currentTimeMillis() >= work.getStartTime() && System.currentTimeMillis() <= work.getEndTime()) {
            work.setStatus(WorkStatus.ONGOING.getCode());
        } else if (System.currentTimeMillis() > work.getEndTime()) {
            work.setStatus(WorkStatus.FINISHED.getCode());
        } else if (System.currentTimeMillis() < work.getStartTime()) {
            work.setStatus(WorkStatus.NOT_STARTED.getCode());
        }
        return workRepository.save(work);
    }

    public Work getWork(Long id) {
        return workRepository.findById(id).orElseThrow(AwsNotFoundException::new);
    }

    public Page<Work> getWorks(int page, int size) {
        PageRequest pr = PageRequest.of(page, size);
        return workRepository.findAll(pr);
    }

    public List<Work> getProduceWorks(Long id) {
        return workRepository.findByProduceId(id);
    }
}
