package cn.edu.xidian.aws.service;

import cn.edu.xidian.aws.constant.ScaleUnit;
import cn.edu.xidian.aws.constant.WorkStatus;
import cn.edu.xidian.aws.exception.AwsArgumentException;
import cn.edu.xidian.aws.exception.AwsForbiddenException;
import cn.edu.xidian.aws.exception.AwsNotFoundException;
import cn.edu.xidian.aws.pojo.po.Produce;
import cn.edu.xidian.aws.pojo.po.Work;
import cn.edu.xidian.aws.pojo.vo.work.WorkAddVO;
import cn.edu.xidian.aws.pojo.vo.work.WorkUpdateVO;
import cn.edu.xidian.aws.repository.WorkRepository;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author akynazh@gmail.com
 * @date 2025/1/16
 * @description
 */
@Service
@Slf4j
public class WorkService {

    @Autowired
    private WorkRepository workRepository;
    @Autowired
    private ProduceService produceService;

    public Work addWork(WorkAddVO vo) {
        if (vo == null) {
            throw new AwsArgumentException(AwsArgumentException.ARGUMENT_NULL);
        }
        if (vo.getStartTime() == null || vo.getEndTime() == null || vo.getStartTime() >= vo.getEndTime()) {
            throw new AwsArgumentException(AwsArgumentException.TIME_ERROR);
        }
        Long produceId = vo.getProduceId();
        Produce produce = produceService.getProduce(produceId);
        if (produce == null) {
            throw new AwsNotFoundException(AwsNotFoundException.PRODUCE_NOT_FOUND);
        }
        boolean hasProduceWork = getProduceWorks(produceId).stream()
                .anyMatch(work -> work.getStatus() == WorkStatus.ONGOING.getCode());
        if (hasProduceWork) {
            throw new AwsForbiddenException(AwsForbiddenException.WORK_ALREADY_HAS_PRODUCE_WORK);
        }

        Work work = new Work();
        work.setProduceId(produceId);
        long startTime = vo.getStartTime();
        long endTime = vo.getEndTime();
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
            throw new AwsArgumentException(AwsArgumentException.ARGUMENT_NULL);
        }
        if (vo.getStatus() != null && !WorkStatus.codeExists(vo.getStatus())) {
            throw new AwsArgumentException(AwsArgumentException.STATUS_WORK_NOT_EXISTS);
        }
        if (vo.getUnit() != null && !ScaleUnit.codeExists(vo.getUnit())) {
            throw new AwsArgumentException(AwsArgumentException.SCALE_UNIT_NOT_EXISTS);
        }
        Long startTime = vo.getStartTime();
        Long endTime = vo.getEndTime();
        if (startTime != null && endTime != null && startTime >= endTime) {
            throw new AwsArgumentException(AwsArgumentException.TIME_ERROR);
        }
        Work work = workRepository.findById(vo.getId()).orElseThrow(
                () -> new AwsNotFoundException(AwsNotFoundException.WORK_NOT_FOUND));
        if (work == null) {
            throw new AwsNotFoundException(AwsNotFoundException.WORK_NOT_FOUND);
        }
        Long produceId = vo.getProduceId();
        if (produceId != null) {
            Produce produce = produceService.getProduce(produceId);
            if (produce == null) {
                throw new AwsNotFoundException(AwsNotFoundException.PRODUCE_NOT_FOUND);
            }
            work.setProduceId(produceId);
        }

        if (startTime != null && endTime != null) {
            work.setStartTime(startTime);
            work.setEndTime(endTime);
        }
        if (vo.getDataValue() != null) {
            work.setDataValue(vo.getDataValue());
        }
        work.setUpdateTime(System.currentTimeMillis());
        if (WorkStatus.DELETED.getCode() != work.getStatus() && WorkStatus.CANCELED.getCode() != work.getStatus()) {
            if (System.currentTimeMillis() >= work.getStartTime() && System.currentTimeMillis() <= work.getEndTime()) {
                work.setStatus(WorkStatus.ONGOING.getCode());
            } else if (System.currentTimeMillis() > work.getEndTime()) {
                work.setStatus(WorkStatus.FINISHED.getCode());
            } else if (System.currentTimeMillis() < work.getStartTime()) {
                work.setStatus(WorkStatus.NOT_STARTED.getCode());
            }
        }
        if (vo.getStatus() != null && (
                WorkStatus.DELETED.getCode() == vo.getStatus() || WorkStatus.CANCELED.getCode() == vo.getStatus()
        )) {
            work.setStatus(vo.getStatus());
        }
        return workRepository.save(work);
    }

    public Work getWork(Long id) {
        return workRepository.findById(id).orElseThrow(
                () -> new AwsNotFoundException(AwsNotFoundException.WORK_NOT_FOUND)
        );
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
