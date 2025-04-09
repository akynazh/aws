package cn.edu.xidian.aws.controller;

import cn.edu.xidian.aws.constant.Security;
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
import cn.edu.xidian.aws.pojo.vo.todo.TodoListVO;
import cn.edu.xidian.aws.pojo.vo.todo.TodoVO;
import cn.edu.xidian.aws.pojo.vo.todo.TodosGetVO;
import cn.edu.xidian.aws.pojo.vo.user.UserWorkOutputVO;
import cn.edu.xidian.aws.service.*;
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
 * @description 称重模块
 */
@RestController
@RequestMapping("/weigh")
@Slf4j
@Tag(name = "称重模块")
public class WeighController {
    @Autowired
    private ScaleService scaleService;
    @Autowired
    private RecordService recordService;
    @Autowired
    private MqttUserService mqttUserService;
    @Autowired
    private TodoService todoService;

    @Operation(summary = "添加称重记录")
    @PostMapping("/record")
    @PreAuthorize(Security.PRE_AUTHORIZE_SCALE)
    public ResponseEntity<RecordVO> addRecord(@RequestBody RecordAddVO vo) {
        Record record = recordService.addRecord(vo);
        return ResponseEntity.ok(Record.toRecordVO(record));
    }

    @Operation(summary = "提交待处理称重记录")
    @PostMapping("/record/todo")
    @PreAuthorize(Security.PRE_AUTHORIZE_ADMIN)
    public ResponseEntity<String> handleTodoRecord(@RequestBody TodoVO vo) {
        recordService.handleTodo(vo);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "丢弃待处理称重记录")
    @DeleteMapping("/record/todo")
    @PreAuthorize(Security.PRE_AUTHORIZE_ADMIN)
    public ResponseEntity<String> dropTodoRecord(@RequestParam Long id) {
        recordService.dropTodo(id);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "获取称重记录")
    @PostMapping("/record/list")
    @PreAuthorize(Security.PRE_AUTHORIZE_EMPLOYEE)
    public ResponseEntity<RecordListVO> getRecords(@RequestBody RecordsGetVO vo) {
        return ResponseEntity.ok(recordService.getRecords(vo));
    }

    @Operation(summary = "获取待处理称重记录")
    @PostMapping("/record/todo/list")
    @PreAuthorize(Security.PRE_AUTHORIZE_EMPLOYEE)
    public ResponseEntity<TodoListVO> getTodos(@RequestBody TodosGetVO vo) {
        return ResponseEntity.ok(todoService.getTodos(vo));
    }

    @Operation(summary = "添加电子秤")
    @PostMapping("/scale")
    @PreAuthorize(Security.PRE_AUTHORIZE_ADMIN)
    public ResponseEntity<ScaleVO> addScale(@RequestBody ScaleAddVO vo) {
        Scale scale = scaleService.addScale(vo);
        return ResponseEntity.ok(Scale.toScaleVO(scale));
    }

    @Operation(summary = "添加称重结果监控者")
    @PostMapping("/monitor")
    @PreAuthorize(Security.PRE_AUTHORIZE_ADMIN)
    public ResponseEntity<String> addMonitor(@RequestParam String username,
                                             @RequestParam String password) {
        mqttUserService.createResultSubscriber(username, password);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "更新电子秤信息")
    @PutMapping("/scale")
    @PreAuthorize(Security.PRE_AUTHORIZE_ADMIN)
    public ResponseEntity<ScaleVO> updateScale(@RequestBody ScaleUpdateVO vo) {
        Scale scale = scaleService.updateScale(vo);
        return ResponseEntity.ok(Scale.toScaleVO(scale));
    }

    @Operation(summary = "获取电子秤列表")
    @GetMapping("/scale/list")
    @PreAuthorize(Security.PRE_AUTHORIZE_EMPLOYEE)
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
    @PreAuthorize(Security.PRE_AUTHORIZE_EMPLOYEE)
    public ResponseEntity<ScaleVO> getScale(@PathVariable Long id) {
        Scale scale = scaleService.getScale(id);
        return ResponseEntity.ok(Scale.toScaleVO(scale));
    }

    @Operation(summary = "获取员工各作业采摘量")
    @GetMapping("/summary")
    @PreAuthorize(Security.PRE_AUTHORIZE_EMPLOYEE)
    public ResponseEntity<List<UserWorkOutputVO>> getUserWorkSummary(@RequestParam Long id) {
        return ResponseEntity.ok(recordService.getUserWorkSummaryVO(id));
    }
}
