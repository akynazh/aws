package cn.edu.xidian.aws.service;

import cn.edu.xidian.aws.constant.ScaleProtocol;
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
            throw new AwsArgumentException(AwsArgumentException.ARGUMENT_NULL);
        }
        if (!ScaleProtocol.codeExists(vo.getProtocol())) {
            throw new AwsArgumentException(AwsArgumentException.SCALE_PROTOCOL_NOT_EXISTS);
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
            throw new AwsArgumentException(AwsArgumentException.ARGUMENT_NULL);
        }
        if (vo.getStatus() != null && !ScaleStatus.codeExists(vo.getStatus())) {
            throw new AwsArgumentException(AwsArgumentException.STATUS_SCALE_NOT_EXISTS);
        }
        Scale originScale = scaleRepository.findById(vo.getId()).orElseThrow(
                () -> new AwsNotFoundException(AwsNotFoundException.SCALE_NOT_FOUND)
        );

        if (vo.getStatus() != null) {
            originScale.setStatus(vo.getStatus());
        }
        originScale.setUpdateTime(System.currentTimeMillis());
        return scaleRepository.save(originScale);
    }

    public Scale getScale(Long id) {
        return scaleRepository.findById(id).orElseThrow(
                () -> new AwsNotFoundException(AwsNotFoundException.SCALE_NOT_FOUND)
        );
    }

    public Scale getScaleByKey(String key) {
        return scaleRepository.findBySkey(key).orElseThrow(
                () -> new AwsNotFoundException(AwsNotFoundException.SCALE_NOT_FOUND)
        );
    }

    public List<Scale> getScales(int page, int size) {
        PageRequest pr = PageRequest.of(page, size);
        return scaleRepository.findAll(pr).getContent();
    }

    public long getScaleCount() {
        return scaleRepository.count();
    }
}
