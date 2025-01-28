package cn.edu.xidian.aws.service;

import cn.edu.xidian.aws.constant.ScaleStatus;
import cn.edu.xidian.aws.exception.AwsArgumentException;
import cn.edu.xidian.aws.exception.AwsNotFoundException;
import cn.edu.xidian.aws.pojo.po.Record;
import cn.edu.xidian.aws.pojo.po.Scale;
import cn.edu.xidian.aws.pojo.vo.record.RecordAddVO;
import cn.edu.xidian.aws.pojo.vo.record.RecordsGetVO;
import cn.edu.xidian.aws.pojo.vo.scale.ScaleAddVO;
import cn.edu.xidian.aws.pojo.vo.scale.ScaleUpdateVO;
import cn.edu.xidian.aws.repository.RecordRepository;
import cn.edu.xidian.aws.repository.ScaleRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

/**
 * @author akynazh@gmail.com
 * @date 2025/1/16
 * @description
 */
@Service
public class RecordService {
    private RecordRepository recordRepository;

    public Record addRecord(RecordAddVO vo) {
        if (vo == null) {
            throw new AwsArgumentException();
        }
        Record record = new Record();
        BeanUtils.copyProperties(vo, record);
        return recordRepository.save(record);
    }

    public Record getRecord(Long id) {
        return recordRepository.findById(id).orElseThrow(AwsNotFoundException::new);
    }

    public Page<Record> getRecords(RecordsGetVO vo) {
        PageRequest pr = PageRequest.of(vo.getPage(), vo.getSize());

        // Create a probe Record object for filtering
        Record probe = new Record();
        if (vo.getWorkId() > 0) probe.setWorkId(vo.getWorkId());
        if (vo.getEmployeeId() > 0) probe.setEmployeeId(vo.getEmployeeId());
        if (vo.getScaleId() > 0) probe.setScaleId(vo.getScaleId());

        ExampleMatcher matcher = ExampleMatcher.matching()
                .withIgnoreNullValues(); // Ignore null fields

        Example<Record> example = Example.of(probe, matcher);

        return recordRepository.findAll(example, pr);
    }
}
