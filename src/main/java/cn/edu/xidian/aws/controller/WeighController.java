package cn.edu.xidian.aws.controller;

import cn.edu.xidian.aws.constant.Constants;
import cn.edu.xidian.aws.pojo.po.Produce;
import cn.edu.xidian.aws.pojo.po.Record;
import cn.edu.xidian.aws.pojo.po.Scale;
import cn.edu.xidian.aws.pojo.vo.common.RestResponse;
import cn.edu.xidian.aws.pojo.vo.produce.ProduceAddVO;
import cn.edu.xidian.aws.pojo.vo.produce.ProduceUpdateVO;
import cn.edu.xidian.aws.pojo.vo.produce.ProduceVO;
import cn.edu.xidian.aws.pojo.vo.record.RecordAddVO;
import cn.edu.xidian.aws.pojo.vo.record.RecordVO;
import cn.edu.xidian.aws.pojo.vo.record.RecordsGetVO;
import cn.edu.xidian.aws.pojo.vo.scale.ScaleAddVO;
import cn.edu.xidian.aws.pojo.vo.scale.ScaleUpdateVO;
import cn.edu.xidian.aws.pojo.vo.scale.ScaleVO;
import cn.edu.xidian.aws.service.RecordService;
import cn.edu.xidian.aws.service.ScaleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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
public class WeighController {
    @Autowired
    private ScaleService scaleService;
    @Autowired
    private RecordService recordService;

    @PostMapping("/record")
    @PreAuthorize(Constants.PRE_AUTHORIZE_ADMIN)
    public ResponseEntity<RecordVO> addRecord(@RequestBody RecordAddVO vo) {
        Record record = recordService.addRecord(vo);
        return ResponseEntity.ok(Record.toRecordVO(record));
    }

    @GetMapping("/record/list")
    @PreAuthorize(Constants.PRE_AUTHORIZE_EMPLOYEE)
    public ResponseEntity<List<RecordVO>> getRecordList(@RequestBody RecordsGetVO vo) {
        Page<Record> recordsPage = recordService.getRecords(vo);
        List<Record> records = recordsPage.getContent();
        List<RecordVO> vos = records.stream().map(Record::toRecordVO).collect(Collectors.toList());
        return ResponseEntity.ok(vos);
    }
    
    @PostMapping("/scale")
    @PreAuthorize(Constants.PRE_AUTHORIZE_ADMIN)
    public ResponseEntity<ScaleVO> addScale(@RequestBody ScaleAddVO vo) {
        Scale scale = scaleService.addScale(vo);
        return ResponseEntity.ok(Scale.toScaleVO(scale));
    }

    @PutMapping("/scale")
    @PreAuthorize(Constants.PRE_AUTHORIZE_ADMIN)
    public ResponseEntity<ScaleVO> updateScale(@RequestBody ScaleUpdateVO vo) {
        Scale scale = scaleService.updateScale(vo);
        return ResponseEntity.ok(Scale.toScaleVO(scale));
    }

    @GetMapping("/scale/list")
    @PreAuthorize(Constants.PRE_AUTHORIZE_EMPLOYEE)
    public RestResponse<List<ScaleVO>> getScaleList(@RequestParam(defaultValue = "0") int page) {
        Page<Scale> scalesPage = scaleService.getScales(page, 10);
        List<Scale> scales = scalesPage.getContent();
        List<ScaleVO> vos = scales.stream().map(Scale::toScaleVO).collect(Collectors.toList());
        return new RestResponse<>(HttpStatus.OK, vos);
    }

    @GetMapping("/scale/{id}")
    @PreAuthorize(Constants.PRE_AUTHORIZE_EMPLOYEE)
    public RestResponse<ScaleVO> getScale(@PathVariable Long id) {
        Scale scale = scaleService.getScale(id);
        return new RestResponse<>(HttpStatus.OK, Scale.toScaleVO(scale));
    }

}
