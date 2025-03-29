package cn.edu.xidian.aws.service;

import cn.edu.xidian.aws.constant.ScaleStatus;
import cn.edu.xidian.aws.exception.AwsArgumentException;
import cn.edu.xidian.aws.exception.AwsNotFoundException;
import cn.edu.xidian.aws.pojo.po.Scale;
import cn.edu.xidian.aws.pojo.vo.scale.ScaleAddVO;
import cn.edu.xidian.aws.pojo.vo.scale.ScaleUpdateVO;
import cn.edu.xidian.aws.repository.ScaleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

/**
 * @author akynazh@gmail.com
 * @date 2025/1/16
 * @description
 */
@Service
@Slf4j
public class ScaleService {
    @Autowired
    private ScaleRepository scaleRepository;
    @Autowired
    private MqttUserService mqttUserService;
    @Autowired
    private UserService userService;

    @Transactional(propagation = Propagation.REQUIRED)
    public Scale addScale(ScaleAddVO vo) {
        if (vo == null) {
            throw new AwsArgumentException(AwsArgumentException.ARGUMENT_NULL);
        }

        String sid = UUID.randomUUID().toString();
        String skey = vo.getSkey();

        Scale scale = new Scale();
        BeanUtils.copyProperties(vo, scale);
        scale.setSid(sid);
        scale.setCreateTime(System.currentTimeMillis());
        scale.setUpdateTime(System.currentTimeMillis());
        scale.setStatus(ScaleStatus.ENABLED.getCode());
        Scale savedScale = scaleRepository.save(scale);

        mqttUserService.createScalePublisher(sid, skey);
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

    public List<Scale> getScales(int page, int size) {
        PageRequest pr = PageRequest.of(page, size);
        return scaleRepository.findAll(pr).getContent();
    }

    public long getScaleCount() {
        return scaleRepository.count();
    }
}
