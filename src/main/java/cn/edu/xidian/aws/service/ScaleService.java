package cn.edu.xidian.aws.service;

import cn.edu.xidian.aws.constant.ProduceStatus;
import cn.edu.xidian.aws.constant.ScaleStatus;
import cn.edu.xidian.aws.exception.AwsArgumentException;
import cn.edu.xidian.aws.exception.AwsNotFoundException;
import cn.edu.xidian.aws.pojo.po.Produce;
import cn.edu.xidian.aws.pojo.po.Scale;
import cn.edu.xidian.aws.pojo.vo.produce.ProduceAddVO;
import cn.edu.xidian.aws.pojo.vo.produce.ProduceUpdateVO;
import cn.edu.xidian.aws.pojo.vo.scale.ScaleAddVO;
import cn.edu.xidian.aws.pojo.vo.scale.ScaleUpdateVO;
import cn.edu.xidian.aws.repository.ScaleRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * @author akynazh@gmail.com
 * @date 2025/1/16
 * @description
 */
@Service
public class ScaleService {
    private ScaleRepository scaleRepository;

    public Scale addScale(ScaleAddVO vo) {
        if (vo == null) {
            throw new AwsArgumentException();
        }
        Scale scale = new Scale();
        BeanUtils.copyProperties(vo, scale);
        scale.setCreateTime(System.currentTimeMillis());
        scale.setUpdateTime(System.currentTimeMillis());
        scale.setStatus(ScaleStatus.ENABLE.getCode());
        return scaleRepository.save(scale);
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

    public Page<Scale> getScales(int page, int size) {
        PageRequest pr = PageRequest.of(page, size);
        return scaleRepository.findAll(pr);
    }
}
