package cn.edu.xidian.aws.controller;

import cn.edu.xidian.aws.constant.Constants;
import cn.edu.xidian.aws.pojo.po.Record;
import cn.edu.xidian.aws.pojo.po.Scale;
import cn.edu.xidian.aws.pojo.vo.record.RecordAddVO;
import cn.edu.xidian.aws.pojo.vo.record.RecordListVO;
import cn.edu.xidian.aws.pojo.vo.record.RecordVO;
import cn.edu.xidian.aws.pojo.vo.record.RecordsGetVO;
import cn.edu.xidian.aws.pojo.vo.scale.ScaleAddVO;
import cn.edu.xidian.aws.pojo.vo.scale.ScaleListVO;
import cn.edu.xidian.aws.pojo.vo.scale.ScaleUpdateVO;
import cn.edu.xidian.aws.pojo.vo.scale.ScaleVO;
import cn.edu.xidian.aws.service.RecordService;
import cn.edu.xidian.aws.service.ScaleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author akynazh@gmail.com
 * @date 2025/1/16
 * @description 称重服务模块
 */
@RestController
@RequestMapping("/weigh")
@Slf4j
@Tag(name = "称重服务模块")
public class WeighController {
    @Autowired
    private ScaleService scaleService;
    @Autowired
    private RecordService recordService;

    @Operation(summary = "添加称重记录")
    @PostMapping("/record")
    @PreAuthorize(Constants.PRE_AUTHORIZE_ADMIN)
    public ResponseEntity<RecordVO> addRecord(@RequestBody RecordAddVO vo) {
        Record record = recordService.addRecord(vo);
        return ResponseEntity.ok(Record.toRecordVO(record));
    }

    @Operation(summary = "获取称重记录")
    @GetMapping("/record/list")
    @PreAuthorize(Constants.PRE_AUTHORIZE_EMPLOYEE)
    public ResponseEntity<RecordListVO> getRecords(@RequestBody RecordsGetVO vo) {
        List<Record> records = recordService.getRecords(vo);
        List<RecordVO> vos = records.stream().map(Record::toRecordVO).collect(Collectors.toList());
        long recordCount = recordService.getRecordCount(vo);
        RecordListVO recordListVO = new RecordListVO();
        recordListVO.setCount(recordCount);
        recordListVO.setRecordList(vos);
        return ResponseEntity.ok(recordListVO);
    }

    @Operation(summary = "添加电子秤")
    @PostMapping("/scale")
    @PreAuthorize(Constants.PRE_AUTHORIZE_ADMIN)
    public ResponseEntity<ScaleVO> addScale(@RequestBody ScaleAddVO vo) {
        Scale scale = scaleService.addScale(vo);
        return ResponseEntity.ok(Scale.toScaleVO(scale));
    }

    @Operation(summary = "更新电子秤信息")
    @PutMapping("/scale")
    @PreAuthorize(Constants.PRE_AUTHORIZE_ADMIN)
    public ResponseEntity<ScaleVO> updateScale(@RequestBody ScaleUpdateVO vo) {
        Scale scale = scaleService.updateScale(vo);
        return ResponseEntity.ok(Scale.toScaleVO(scale));
    }

    @Operation(summary = "获取电子秤列表")
    @GetMapping("/scale/list")
    @PreAuthorize(Constants.PRE_AUTHORIZE_EMPLOYEE)
    public ResponseEntity<ScaleListVO> getScales(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        List<Scale> scales = scaleService.getScales(page, size);
        List<ScaleVO> vos = scales.stream().map(Scale::toScaleVO).collect(Collectors.toList());
        long scaleCount = scaleService.getScaleCount();
        ScaleListVO scaleListVO = new ScaleListVO();
        scaleListVO.setCount(scaleCount);
        scaleListVO.setScaleList(vos);
        return ResponseEntity.ok(scaleListVO);
    }

    @Operation(summary = "获取电子秤")
    @GetMapping("/scale/{id}")
    @PreAuthorize(Constants.PRE_AUTHORIZE_EMPLOYEE)
    public ResponseEntity<ScaleVO> getScale(@PathVariable Long id) {
        Scale scale = scaleService.getScale(id);
        return ResponseEntity.ok(Scale.toScaleVO(scale));
    }
}
