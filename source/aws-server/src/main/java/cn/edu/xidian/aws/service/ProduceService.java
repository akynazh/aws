package cn.edu.xidian.aws.service;

import cn.edu.xidian.aws.constant.ProduceStatus;
import cn.edu.xidian.aws.exception.AwsArgumentException;
import cn.edu.xidian.aws.exception.AwsNotFoundException;
import cn.edu.xidian.aws.pojo.po.Produce;
import cn.edu.xidian.aws.pojo.vo.produce.ProduceAddVO;
import cn.edu.xidian.aws.pojo.vo.produce.ProduceUpdateVO;
import cn.edu.xidian.aws.repository.ProduceRepository;
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
public class ProduceService {
    @Autowired
    private ProduceRepository produceRepository;

    public Produce addProduce(ProduceAddVO vo) {
        if (vo == null) {
            throw new AwsArgumentException();
        }
        Produce produce = new Produce();
        produce.setName(vo.getName());
        produce.setCreateTime(System.currentTimeMillis());
        produce.setUpdateTime(System.currentTimeMillis());
        produce.setStatus(ProduceStatus.ENABLED.getCode());
        return produceRepository.save(produce);
    }

    public Produce updateProduce(ProduceUpdateVO vo) {
        if (vo == null) {
            throw new AwsArgumentException();
        }
        if (vo.getStatus() != null && !ProduceStatus.codeExists(vo.getStatus())) {
            throw new AwsArgumentException();
        }
        Produce originProduce = produceRepository.findById(vo.getId()).orElseThrow(AwsNotFoundException::new);

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
        return produceRepository.findById(id).orElseThrow(AwsNotFoundException::new);
    }

    public Produce getProduceByName(String name) {
        return produceRepository.findByName(name).orElseThrow(AwsNotFoundException::new);
    }

    public List<Produce> getProduces(int page, int size) {
        PageRequest pr = PageRequest.of(page, size);
        return produceRepository.findAll(pr).getContent();
    }

    public long getProduceCount() {
        return produceRepository.count();
    }
}
