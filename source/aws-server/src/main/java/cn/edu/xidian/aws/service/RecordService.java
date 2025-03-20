package cn.edu.xidian.aws.service;

import cn.edu.xidian.aws.constant.ScaleStatus;
import cn.edu.xidian.aws.constant.ScaleUnit;
import cn.edu.xidian.aws.constant.UserStatus;
import cn.edu.xidian.aws.constant.WorkStatus;
import cn.edu.xidian.aws.exception.AwsArgumentException;
import cn.edu.xidian.aws.exception.AwsForbiddenException;
import cn.edu.xidian.aws.exception.AwsNotFoundException;
import cn.edu.xidian.aws.pojo.dto.UserWorkOutputDTO;
import cn.edu.xidian.aws.pojo.po.Record;
import cn.edu.xidian.aws.pojo.po.Scale;
import cn.edu.xidian.aws.pojo.po.User;
import cn.edu.xidian.aws.pojo.po.Work;
import cn.edu.xidian.aws.pojo.vo.record.RecordAddVO;
import cn.edu.xidian.aws.pojo.vo.record.RecordsGetVO;
import cn.edu.xidian.aws.pojo.vo.user.UserWorkOutputVO;
import cn.edu.xidian.aws.pojo.vo.work.WorkUpdateVO;
import cn.edu.xidian.aws.repository.RecordRepository;
import cn.edu.xidian.aws.util.ScaleUtil;
import jakarta.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

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

    @Transactional
    public Record addRecord(RecordAddVO vo) {
        if (vo == null || !ScaleUnit.codeExists(vo.getUnit())) {
            throw new AwsArgumentException();
        }
        Record record = new Record();
        BeanUtils.copyProperties(vo, record);
        Long workId = record.getWorkId();
        Long employeeId = record.getEmployeeId();
        Long scaleId = record.getScaleId();
        Integer unit = record.getUnit();
        Scale scale = scaleService.getScale(scaleId);
        User employee = userService.getUserByID(employeeId);
        Work work = workService.getWork(workId);
        if (employee == null || work == null || scale == null) {
            throw new AwsNotFoundException();
        }
        if (UserStatus.userNotEnabled(employee.getStatus())) {
            throw new AwsForbiddenException(UserStatus.fromCode(employee.getStatus()).getMessage());
        }
        if (ScaleStatus.scaleNotEnabled(scale.getStatus())) {
            throw new AwsForbiddenException(ScaleStatus.fromCode(scale.getStatus()).getMessage());
        }
        if (WorkStatus.workNotOnGoing(work.getStatus())) {
            throw new AwsForbiddenException(WorkStatus.fromCode(work.getStatus()).getMessage());
        }

        BigDecimal dataValue = ScaleUtil.convDataValue(record.getDataValue(), unit, work.getUnit());
        WorkUpdateVO workUpdateVO = new WorkUpdateVO();
        workUpdateVO.setId(workId);
        workUpdateVO.setDataValue(work.getDataValue().add(dataValue));
        workService.updateWork(workUpdateVO);
        return recordRepository.save(record);
    }

    public Record getRecord(Long id) {
        return recordRepository.findById(id).orElseThrow(AwsNotFoundException::new);
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
