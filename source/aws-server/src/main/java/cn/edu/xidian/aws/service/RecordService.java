package cn.edu.xidian.aws.service;

import cn.edu.xidian.aws.constant.ScaleStatus;
import cn.edu.xidian.aws.constant.ScaleUnit;
import cn.edu.xidian.aws.constant.UserStatus;
import cn.edu.xidian.aws.constant.WorkStatus;
import cn.edu.xidian.aws.exception.AwsArgumentException;
import cn.edu.xidian.aws.exception.AwsForbiddenException;
import cn.edu.xidian.aws.exception.AwsNotFoundException;
import cn.edu.xidian.aws.pojo.dto.UserWorkOutputDTO;
import cn.edu.xidian.aws.pojo.po.*;
import cn.edu.xidian.aws.pojo.po.Record;
import cn.edu.xidian.aws.pojo.vo.record.RecordAddVO;
import cn.edu.xidian.aws.pojo.vo.record.RecordsGetVO;
import cn.edu.xidian.aws.pojo.vo.user.UserWorkOutputVO;
import cn.edu.xidian.aws.pojo.vo.work.WorkUpdateVO;
import cn.edu.xidian.aws.repository.RecordRepository;
import cn.edu.xidian.aws.util.ProduceUtil;
import cn.edu.xidian.aws.util.ScaleUtil;
import jakarta.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author akynazh@gmail.com
 * @date 2025/1/16
 * @description
 */
@Service
public class RecordService {
    @Autowired
    private RecordRepository recordRepository;
    @Autowired
    private WorkService workService;
    @Autowired
    private UserService userService;
    @Autowired
    private ScaleService scaleService;
    @Autowired
    private ProduceService produceService;

    @Transactional
    public Record addRecord(RecordAddVO vo) throws IOException {
        if (vo == null) {
            throw new AwsArgumentException(AwsArgumentException.ARGUMENT_NULL);
        }
        String image = vo.getImage();
        String image64 = vo.getImage64();
        Long produceId = vo.getProduceId();
        String produceName = vo.getProduceName();
        Long employeeId = vo.getEmployeeId();
        Long scaleId = vo.getScaleId();
        Integer unit = vo.getUnit();
        BigDecimal dataValue = vo.getDataValue();

        // get produceId
        if (unit == null) {
            throw new AwsArgumentException(AwsArgumentException.PARAM_MISSING);
        }
        if (!ScaleUnit.codeExists(unit)) {
            throw new AwsArgumentException(AwsArgumentException.SCALE_UNIT_NOT_EXISTS);
        }
        if (produceId == null && !StringUtils.hasText(produceName)) {
            if (!StringUtils.hasText(image) && !StringUtils.hasText(image64)) {
                throw new AwsArgumentException(AwsArgumentException.PARAM_MISSING);
            }
            produceName = ProduceUtil.rec(image, image64);
            Produce produce = produceService.getProduceByName(produceName);
            produceId = produce.getId();
        } else if (produceId == null) {
            Produce produce = produceService.getProduceByName(produceName);
            if (produce == null) {
                throw new AwsNotFoundException(AwsNotFoundException.PRODUCE_NOT_FOUND);
            }
            produceId = produce.getId();
        } else {
            Produce produce = produceService.getProduce(produceId);
            if (produce == null) {
                throw new AwsNotFoundException(AwsNotFoundException.PRODUCE_NOT_FOUND);
            }
        }

        Work ongoingWork = workService.getProduceWorks(produceId).stream()
                .filter(work -> work.getStatus() == WorkStatus.ONGOING.getCode())
                .findFirst()
                .orElseThrow(() -> new AwsNotFoundException(AwsNotFoundException.WORK_NOT_FOUND));
        Scale scale = scaleService.getScale(scaleId);
        User employee = userService.getUserByID(employeeId);
        if (UserStatus.userNotEnabled(employee.getStatus())) {
            throw new AwsForbiddenException(UserStatus.label + UserStatus.fromCode(employee.getStatus()).getMessage());
        }
        if (ScaleStatus.scaleNotEnabled(scale.getStatus())) {
            throw new AwsForbiddenException(ScaleStatus.label + ScaleStatus.fromCode(scale.getStatus()).getMessage());
        }
        if (WorkStatus.workNotOnGoing(ongoingWork.getStatus())) {
            throw new AwsForbiddenException(WorkStatus.label + WorkStatus.fromCode(ongoingWork.getStatus()).getMessage());
        }


        BigDecimal workDataValue = ScaleUtil.convDataValue(dataValue, unit, ongoingWork.getUnit());
        WorkUpdateVO workUpdateVO = new WorkUpdateVO();
        workUpdateVO.setId(ongoingWork.getId());
        workUpdateVO.setDataValue(ongoingWork.getDataValue().add(workDataValue));
        workService.updateWork(workUpdateVO);

        Record record = new Record();
        BeanUtils.copyProperties(vo, record);
        record.setWorkId(ongoingWork.getId());
        return recordRepository.save(record);
    }

    public Record getRecord(Long id) {
        return recordRepository.findById(id).orElseThrow(
                () -> new AwsNotFoundException(AwsNotFoundException.RECORD_NOT_FOUND)
        );
    }

    public List<Record> getRecords(RecordsGetVO vo) {
        PageRequest pr = PageRequest.of(vo.getPage(), vo.getSize());
        Example<Record> example = getRecordExample(vo);
        return recordRepository.findAll(example, pr).getContent();
    }

    public long getRecordCount(RecordsGetVO vo) {
        return recordRepository.count(getRecordExample(vo));
    }

    private Example<Record> getRecordExample(RecordsGetVO vo) {
        Record probe = new Record();
        if (vo.getWorkId() > 0) probe.setWorkId(vo.getWorkId());
        if (vo.getEmployeeId() > 0) probe.setEmployeeId(vo.getEmployeeId());
        if (vo.getScaleId() > 0) probe.setScaleId(vo.getScaleId());
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withIgnoreNullValues();
        return Example.of(probe, matcher);
    }

    public List<UserWorkOutputVO> getUserWorkSummaryVO(Long id) {
        List<UserWorkOutputDTO> summaries = recordRepository.getUserWorkSummary(id);

        return summaries.stream().map(summary -> {
            UserWorkOutputVO vo = new UserWorkOutputVO();
            vo.setName(summary.getName());
            vo.setWorkId(summary.getWorkId());
            vo.setProduceName(summary.getProduceName());
            vo.setDataValue(summary.getDataValue());
            vo.setUnit(ScaleUnit.valueOf(summary.getUnit()).getMessage());
            return vo;
        }).collect(Collectors.toList());
    }
}
