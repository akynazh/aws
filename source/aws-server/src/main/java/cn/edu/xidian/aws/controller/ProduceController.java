package cn.edu.xidian.aws.controller;

import cn.edu.xidian.aws.constant.Security;
import cn.edu.xidian.aws.pojo.po.Produce;
import cn.edu.xidian.aws.pojo.vo.produce.*;
import cn.edu.xidian.aws.service.ProduceService;
import cn.edu.xidian.aws.service.RecordService;
import cn.edu.xidian.aws.service.WorkService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author akynazh@gmail.com
 * @date 2025/1/16
 * @description 果实模块
 */
@RestController
@RequestMapping("/produce")
@Slf4j
@Tag(name = "果实模块")
public class ProduceController {
    @Autowired
    private ProduceService produceService;
    @Autowired
    private WorkService workService;
    @Autowired
    private RecordService recordService;

    @Operation(summary = "添加果实")
    @PostMapping
    @PreAuthorize(Security.PRE_AUTHORIZE_ADMIN)
    public ResponseEntity<ProduceVO> addProduce(@RequestBody ProduceAddVO vo) {
        Produce produce = produceService.addProduce(vo);
        return ResponseEntity.ok(Produce.toProduceVO(produce));
    }

    @Operation(summary = "更新果实")
    @PutMapping
    @PreAuthorize(Security.PRE_AUTHORIZE_ADMIN)
    public ResponseEntity<ProduceVO> updateProduce(@RequestBody ProduceUpdateVO vo) {
        Produce produce = produceService.updateProduce(vo);
        return ResponseEntity.ok(Produce.toProduceVO(produce));
    }

    @Operation(summary = "获取果实列表")
    @GetMapping("/list")
    @PreAuthorize(Security.PRE_AUTHORIZE_EMPLOYEE)
    public ResponseEntity<ProduceListVO> getProduces(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        List<Produce> produces = produceService.getProduces(page, size);
        List<ProduceVO> produceVOS = produces.stream().map(Produce::toProduceVO).collect(Collectors.toList());
        long produceCount = produceService.getProduceCount();
        ProduceListVO produceListVO = new ProduceListVO();
        produceListVO.setCount(produceCount);
        produceListVO.setProduceList(produceVOS);
        return ResponseEntity.ok(produceListVO);
    }

    @Operation(summary = "获取果实")
    @GetMapping("/{id}")
    @PreAuthorize(Security.PRE_AUTHORIZE_EMPLOYEE)
    public ResponseEntity<ProduceVO> getProduce(@PathVariable Long id) {
        Produce produce = produceService.getProduce(id);
        return ResponseEntity.ok(Produce.toProduceVO(produce));
    }

    @Operation(summary = "根据名称获取果实")
    @GetMapping
    @PreAuthorize(Security.PRE_AUTHORIZE_EMPLOYEE)
    public ResponseEntity<ProduceVO> getProduceByName(@RequestParam String name) {
        Produce produce = produceService.getProduceByName(name);
        return ResponseEntity.ok(Produce.toProduceVO(produce));
    }

    @Operation(summary = "获取果实年产量")
    @GetMapping("/summary/year")
    @PreAuthorize(Security.PRE_AUTHORIZE_EMPLOYEE)
    public ResponseEntity<List<ProduceAnnualOutputVO>> getProduceAnnualOutput(@RequestParam Long id) {
        return ResponseEntity.ok(recordService.getProduceAnnualOutput(id));
    }

    @Operation(summary = "获取果实分批产量")
    @GetMapping("/summary/work")
    @PreAuthorize(Security.PRE_AUTHORIZE_EMPLOYEE)
    public ResponseEntity<List<ProduceWorkOutputVO>> getProduceWorkOutput(@RequestParam Long id) {
        return ResponseEntity.ok(recordService.getProduceWorkOutput(id));
    }
}
