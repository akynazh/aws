package cn.edu.xidian.aws.service;

import cn.edu.xidian.aws.constant.ProduceStatus;
import cn.edu.xidian.aws.constant.Produces;
import cn.edu.xidian.aws.constant.UserRole;
import cn.edu.xidian.aws.constant.UserStatus;
import cn.edu.xidian.aws.exception.AwsArgumentException;
import cn.edu.xidian.aws.exception.AwsNotFoundException;
import cn.edu.xidian.aws.pojo.po.Produce;
import cn.edu.xidian.aws.pojo.po.User;
import cn.edu.xidian.aws.pojo.vo.produce.ProduceAddVO;
import cn.edu.xidian.aws.pojo.vo.produce.ProduceUpdateVO;
import cn.edu.xidian.aws.repository.ProduceRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * @author akynazh@gmail.com
 * @date 2025/1/16
 * @description
 */
@Service
@Slf4j
public class ProduceService {
    @Autowired
    private ProduceRepository produceRepository;
    private static final long USER_DEFINED_START_ID = 1000000;

    private long genNextId() {
        long maxId = produceRepository.findMaxId();
        return maxId >= USER_DEFINED_START_ID ? maxId + 1 : USER_DEFINED_START_ID;
    }

    public Produce addProduce(ProduceAddVO vo) {
        if (vo == null) {
            throw new AwsArgumentException(AwsArgumentException.ARGUMENT_NULL);
        }
        Produce produce = new Produce();
        produce.setName(vo.getName());
        produce.setNameEn(vo.getNameEn());
        produce.setId(genNextId());
        produce.setCreateTime(System.currentTimeMillis());
        produce.setUpdateTime(System.currentTimeMillis());
        produce.setStatus(ProduceStatus.ENABLED.getCode());
        return produceRepository.save(produce);
    }

    public Produce updateProduce(ProduceUpdateVO vo) {
        if (vo == null) {
            throw new AwsArgumentException(AwsArgumentException.ARGUMENT_NULL);
        }
        if (vo.getStatus() != null && !ProduceStatus.codeExists(vo.getStatus())) {
            throw new AwsArgumentException(AwsArgumentException.STATUS_PRODUCE_NOT_EXISTS);
        }
        Produce originProduce = produceRepository.findById(vo.getId())
                .orElseThrow(() -> new AwsNotFoundException(AwsNotFoundException.ITEM_NOT_FOUND));

        if (StringUtils.hasText(vo.getName())) {
            originProduce.setName(vo.getName());
        }
        if (vo.getStatus() != null) {
            originProduce.setStatus(vo.getStatus());
        }
        originProduce.setUpdateTime(System.currentTimeMillis());
        return produceRepository.save(originProduce);
    }

    public Produce getProduce(Long id) {
        return produceRepository.findById(id).orElseThrow(
                () -> new AwsNotFoundException(AwsNotFoundException.PRODUCE_NOT_FOUND)
        );
    }

    public Produce getProduceByName(String name) {
        return produceRepository.findByName(name).orElseThrow(
                () -> new AwsNotFoundException(AwsNotFoundException.PRODUCE_NOT_FOUND)
        );
    }

    public List<Produce> getProduces(int page, int size) {
        PageRequest pr = PageRequest.of(page, size);
        return produceRepository.findAll(pr).getContent();
    }

    public long getProduceCount() {
        return produceRepository.count();
    }

    public void initProduces() {
        for (Produces p : Produces.values()) {
            long id = p.getClazz();
            if (produceRepository.findById(id).isPresent()) {
                continue;
            }
            Produce produce = new Produce();
            produce.setId(id);
            produce.setName(p.getName());
            produce.setNameEn(p.getNameEn());
            produce.setStatus(ProduceStatus.ENABLED.getCode());
            produce.setCreateTime(System.currentTimeMillis());
            produce.setUpdateTime(System.currentTimeMillis());
            produceRepository.save(produce);
            log.info("init produce: {}", produce);
        }
    }

}
