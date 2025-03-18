package cn.edu.xidian.aws.service;

import cn.edu.xidian.aws.constant.ScaleUnit;
import cn.edu.xidian.aws.constant.WorkStatus;
import cn.edu.xidian.aws.exception.AwsArgumentException;
import cn.edu.xidian.aws.exception.AwsNotFoundException;
import cn.edu.xidian.aws.pojo.po.Produce;
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
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author akynazh@gmail.com
 * @date 2025/1/16
 * @description
 */
@Service
public class WorkService {

    @Autowired
    private WorkRepository workRepository;
    @Autowired
    private ProduceService produceService;

    public Work addWork(WorkAddVO vo) {
        if (vo == null) {
            throw new AwsArgumentException();
        }
        Long produceId = vo.getProduceId();
        Produce produce = produceService.getProduce(produceId);
        if (produce == null) {
            throw new AwsNotFoundException();
        }
        Work work = new Work();
        work.setProduceId(produceId);
        long startTime = vo.getStartTime();
        long endTime = vo.getEndTime();
        if (startTime >= endTime) {
            throw new AwsArgumentException();
        }
        work.setStartTime(startTime);
        work.setEndTime(endTime);
        work.setDataValue(new BigDecimal(0));
        work.setUnit(ScaleUnit.KG.getCode());
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

        Long produceId = vo.getProduceId();
        if  (produceId != null) {
            work.setProduceId(produceId);
        }
        Produce produce = produceService.getProduce(produceId);
        if (produce == null) {
            throw new AwsNotFoundException();
        }
        Long startTime = vo.getStartTime();
        Long endTime = vo.getEndTime();
        if (startTime != null && endTime != null) {
            if (startTime >= endTime) {
                throw new AwsArgumentException();
            }
            work.setStartTime(startTime);
            work.setEndTime(endTime);
        }
        if (vo.getDataValue() != null) {
            work.setDataValue(vo.getDataValue());
        }
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

    public List<Work> getWorks(int page, int size) {
        PageRequest pr = PageRequest.of(page, size);
        return workRepository.findAll(pr).getContent();
    }

    public long getWorkCount() {
        return workRepository.count();
    }

    public List<Work> getProduceWorks(Long id) {
        return workRepository.findAllByProduceId(id);
    }
}
