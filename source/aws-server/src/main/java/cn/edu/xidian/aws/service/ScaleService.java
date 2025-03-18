package cn.edu.xidian.aws.service;

import cn.edu.xidian.aws.constant.ScaleStatus;
import cn.edu.xidian.aws.exception.AwsArgumentException;
import cn.edu.xidian.aws.exception.AwsNotFoundException;
import cn.edu.xidian.aws.pojo.po.Scale;
import cn.edu.xidian.aws.pojo.vo.scale.ScaleAddVO;
import cn.edu.xidian.aws.pojo.vo.scale.ScaleUpdateVO;
import cn.edu.xidian.aws.repository.ScaleRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.UUID;

/**
 * @author akynazh@gmail.com
 * @date 2025/1/16
 * @description
 */
@Service
public class ScaleService {
    @Autowired
    private ScaleRepository scaleRepository;
    @Autowired
    private MqttUserService mqttUserService;

    @Transactional(propagation = Propagation.REQUIRED)
    public Scale addScale(ScaleAddVO vo) throws NoSuchAlgorithmException {
        if (vo == null) {
            throw new AwsArgumentException();
        }
        Scale scale = new Scale();
        BeanUtils.copyProperties(vo, scale);
        String sid = UUID.randomUUID().toString();
        scale.setSid(sid);
        scale.setCreateTime(System.currentTimeMillis());
        scale.setUpdateTime(System.currentTimeMillis());
        scale.setStatus(ScaleStatus.ENABLED.getCode());
        Scale savedScale = scaleRepository.save(scale);
        mqttUserService.createScalePublisher(sid, savedScale.getSkey());
        return savedScale;
    }

    public Scale updateScale(ScaleUpdateVO vo) {
        if (vo == null) {
            throw new AwsArgumentException();
        }
        Scale originScale = scaleRepository.findById(vo.getId()).orElseThrow(AwsNotFoundException::new);
        if (ScaleStatus.codeExists(vo.getStatus())) {
            originScale.setStatus(vo.getStatus());
        }
        originScale.setUpdateTime(System.currentTimeMillis());
        return scaleRepository.save(originScale);
    }

    public Scale getScale(Long id) {
        return scaleRepository.findById(id).orElseThrow(AwsNotFoundException::new);
    }

    public Scale getScaleByKey(String key) {
        return scaleRepository.findBySkey(key).orElseThrow(AwsNotFoundException::new);
    }

    public List<Scale> getScales(int page, int size) {
        PageRequest pr = PageRequest.of(page, size);
        return scaleRepository.findAll(pr).getContent();
    }

    public long getScaleCount() {
        return scaleRepository.count();
    }
}
