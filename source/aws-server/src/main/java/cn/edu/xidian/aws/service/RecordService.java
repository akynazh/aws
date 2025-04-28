package cn.edu.xidian.aws.service;

import cn.edu.xidian.aws.constant.*;
import cn.edu.xidian.aws.exception.AwsArgumentException;
import cn.edu.xidian.aws.exception.AwsForbiddenException;
import cn.edu.xidian.aws.exception.AwsNotFoundException;
import cn.edu.xidian.aws.exception.AwsRecordDupException;
import cn.edu.xidian.aws.pojo.dto.UserWorkOutputDTO;
import cn.edu.xidian.aws.pojo.po.*;
import cn.edu.xidian.aws.pojo.po.Record;
import cn.edu.xidian.aws.pojo.vo.produce.ProduceAnnualOutputVO;
import cn.edu.xidian.aws.pojo.vo.produce.ProduceWorkOutputVO;
import cn.edu.xidian.aws.pojo.vo.record.RecordAddVO;
import cn.edu.xidian.aws.pojo.vo.record.RecordListVO;
import cn.edu.xidian.aws.pojo.vo.record.RecordVO;
import cn.edu.xidian.aws.pojo.vo.record.RecordsGetVO;
import cn.edu.xidian.aws.pojo.vo.todo.TodoVO;
import cn.edu.xidian.aws.pojo.vo.user.UserWorkOutputVO;
import cn.edu.xidian.aws.pojo.vo.work.WorkUpdateVO;
import cn.edu.xidian.aws.repository.RecordRepository;
import cn.edu.xidian.aws.repository.WorkRepository;
import cn.edu.xidian.aws.util.ScaleUtil;
import com.alibaba.fastjson.JSON;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author akynazh@gmail.com
 * @date 2025/1/16
 * @description
 */
@Service
@Slf4j
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
    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    @Autowired
    private PredictService produceUtil;
    @Autowired
    private TodoService todoService;
    @Autowired
    private ImageService imageService;
    @Autowired
    private WorkRepository workRepository;

    private boolean isDuplicateRecord(RecordAddVO vo) {
        Record record = recordRepository.getRecordByScaleIdAndEmployeeIdAndDataTime(
                vo.getScaleId(), vo.getEmployeeId(), vo.getDataTime()
        );
        return record != null;
    }

    @Transactional
    public Record addRecord(RecordAddVO vo) {
        if (vo == null) {
            throw new AwsArgumentException(AwsArgumentException.ARGUMENT_NULL);
        }
        if (isDuplicateRecord(vo)) {
            throw new AwsRecordDupException();
        }
        String image = vo.getImage();
//        String image64 = vo.getImage64();
        Long produceId = vo.getProduceId();
        String produceName = vo.getProduceName();
        Long employeeId = vo.getEmployeeId();
        Long scaleId = vo.getScaleId();
        Integer unit = vo.getUnit();
        BigDecimal dataValue = vo.getDataValue();
        if (StringUtils.hasText(image)) {
            image = imageService.handle(image);
        }
        if (unit == null) {
            throw new AwsArgumentException(AwsArgumentException.PARAM_MISSING);
        }
        if (!ScaleUnit.codeExists(unit)) {
            throw new AwsArgumentException(AwsArgumentException.SCALE_UNIT_NOT_EXISTS);
        }

        produceId = getProduceId(produceId, produceName, image);
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

        updateWorkOutput(dataValue, unit, ongoingWork);
        return saveRecord(vo, ongoingWork, produceId, image);
    }

    @NotNull
    private Record saveRecord(RecordAddVO vo, Work ongoingWork, Long produceId, String image) {
        Record record = new Record();
        BeanUtils.copyProperties(vo, record);
        record.setWorkId(ongoingWork.getId());
        record.setProduceId(produceId);
        record.setImage(image);
        return recordRepository.save(record);
    }

    private void updateWorkOutput(BigDecimal dataValue, Integer unit, Work ongoingWork) {
        BigDecimal workDataValue = ScaleUtil.convDataValue(dataValue, unit, ongoingWork.getUnit());
        WorkUpdateVO workUpdateVO = new WorkUpdateVO();
        workUpdateVO.setId(ongoingWork.getId());
        workUpdateVO.setDataValue(ongoingWork.getDataValue().add(workDataValue));
        workService.updateWork(workUpdateVO);
    }

    private Long getProduceId(Long produceId, String produceName, String image) {
        if (produceId == null && !StringUtils.hasText(produceName)) {
            if (!StringUtils.hasText(image)) {
                throw new AwsArgumentException(AwsArgumentException.PARAM_MISSING);
            }
            produceName = produceUtil.predict(image, null);
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
        return produceId;
    }

    @Transactional
    public void handleTodo(TodoVO todoVO) {
        RecordAddVO recordAddVO = new RecordAddVO();
        BeanUtils.copyProperties(todoVO, recordAddVO);
        addRecord(recordAddVO);
        todoService.delTodo(todoVO.getId());
    }

    public void dropTodo(Long id) {
        todoService.delTodo(id);
    }

    public Record getRecord(Long id) {
        return recordRepository.findById(id).orElseThrow(
                () -> new AwsNotFoundException(AwsNotFoundException.RECORD_NOT_FOUND)
        );
    }

    public RecordListVO getRecords(RecordsGetVO vo) {
        PageRequest pr = PageRequest.of(vo.getPage(), vo.getSize());
        Example<Record> example = getRecordExample(vo);
        List<Record> recordList = recordRepository.findAll(example, pr).getContent();
        long count = recordRepository.count(example);

        List<RecordVO> recordVOList = recordList.stream().map(Record::toRecordVO).collect(Collectors.toList());
        RecordListVO recordListVO = new RecordListVO();
        recordListVO.setCount(count);
        recordListVO.setRecordList(recordVOList);
        return recordListVO;
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
        Cache CA = Cache.USER_WORK_SUMMARY;
        String s = redisTemplate.opsForValue().get(CA.getPrefix() + id);
        if (s != null) {
            return JSON.parseArray(s, UserWorkOutputVO.class);
        }

        List<UserWorkOutputDTO> summaries = recordRepository.getUserWorkSummary(id);
        List<UserWorkOutputVO> result = summaries.stream().map(sm -> {
            UserWorkOutputVO vo = new UserWorkOutputVO();
            vo.setName(sm.getName());
            vo.setWorkId(sm.getWorkId());
            vo.setProduceName(sm.getProduceName());
            vo.setDataValue(sm.getDataValue());
            vo.setUnit(ScaleUnit.valueOf(sm.getUnit()).getMessage());
            return vo;
        }).collect(Collectors.toList());
        redisTemplate.opsForValue().set(CA.getPrefix() + id,
                JSON.toJSONString(result), CA.getDuration(), CA.getUnit());
        return result;
    }

    public List<ProduceAnnualOutputVO> getProduceAnnualOutput(Long id) {
        Cache CA = Cache.PRODUCE_ANNUAL_OUTPUT;
        String s = redisTemplate.opsForValue().get(CA.getPrefix() + id);
        if (s != null) {
            return JSON.parseArray(s, ProduceAnnualOutputVO.class);
        }

        List<Work> produceWorks = workService.getProduceWorks(id);
        Produce produce = produceService.getProduce(id);
        List<ProduceAnnualOutputVO> result = ProduceAnnualOutputVO.build(produce, produceWorks);
        redisTemplate.opsForValue().set(CA.getPrefix() + id,
                JSON.toJSONString(result), CA.getDuration(), CA.getUnit());
        return result;
    }

    public List<ProduceWorkOutputVO> getProduceWorkOutput(Long id) {
        Cache CA = Cache.PRODUCE_WORK_OUTPUT;
        String s = redisTemplate.opsForValue().get(CA.getPrefix() + id);
        if (s != null) {
            return JSON.parseArray(s, ProduceWorkOutputVO.class);
        }

        List<Work> produceWorks = workService.getProduceWorks(id);
        Produce produce = produceService.getProduce(id);
        List<ProduceWorkOutputVO> result = produceWorks.stream()
                .map(work -> ProduceWorkOutputVO.build(produce, work))
                .collect(Collectors.toList());
        redisTemplate.opsForValue().set(CA.getPrefix() + id,
                JSON.toJSONString(result), CA.getDuration(), CA.getUnit());
        return result;
    }

    public void updateWorkOutput(Long workId) {
        Work work = workRepository.getWorkById(workId);
        List<Record> records = recordRepository.getRecordByWorkId(workId);
        BigDecimal result = BigDecimal.ZERO;
        for (Record record : records) {
            result = result.add(ScaleUtil.convDataValue(record.getDataValue(), record.getUnit(), work.getUnit()));
        }
        WorkUpdateVO workUpdateVO = new WorkUpdateVO();
        workUpdateVO.setId(workId);
        workUpdateVO.setDataValue(result);
        workService.updateWork(workUpdateVO);
    }
}
