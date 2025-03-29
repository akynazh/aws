package cn.edu.xidian.aws.controller;

import cn.edu.xidian.aws.constant.Security;
import cn.edu.xidian.aws.pojo.po.Work;
import cn.edu.xidian.aws.pojo.vo.work.WorkAddVO;
import cn.edu.xidian.aws.pojo.vo.work.WorkListVO;
import cn.edu.xidian.aws.pojo.vo.work.WorkUpdateVO;
import cn.edu.xidian.aws.pojo.vo.work.WorkVO;
import cn.edu.xidian.aws.service.JwtService;
import cn.edu.xidian.aws.service.UserService;
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
 * @description 工作服务模块
 */
@RestController
@RequestMapping("/work")
@Slf4j
@Tag(name = "工作服务模块")
public class WorkController {
    @Autowired
    private WorkService workService;
    @Autowired
    private UserService userService;
    @Autowired
    private JwtService jwtService;

    @Operation(summary = "添加采摘工作")
    @PostMapping
    @PreAuthorize(Security.PRE_AUTHORIZE_ADMIN)
    public ResponseEntity<WorkVO> addWork(@RequestBody WorkAddVO vo) {
        Work work = workService.addWork(vo);
        return ResponseEntity.ok(Work.toWorkVO(work));
    }

    @Operation(summary = "更新采摘工作")
    @PutMapping
    @PreAuthorize(Security.PRE_AUTHORIZE_ADMIN)
    public ResponseEntity<WorkVO> updateWork(@RequestBody WorkUpdateVO vo) {
        Work work = workService.updateWork(vo);
        return ResponseEntity.ok(Work.toWorkVO(work));
    }

    @Operation(summary = "获取采摘工作")
    @GetMapping("/{id}")
    @PreAuthorize(Security.PRE_AUTHORIZE_EMPLOYEE)
    public ResponseEntity<WorkVO> getWork(@PathVariable long id) {
        Work work = workService.getWork(id);
        return ResponseEntity.ok(Work.toWorkVO(work));
    }

    @Operation(summary = "获取采摘工作列表")
    @GetMapping("/list")
    @PreAuthorize(Security.PRE_AUTHORIZE_EMPLOYEE)
    public ResponseEntity<WorkListVO> getWorks(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        List<Work> works = workService.getWorks(page, size);
        List<WorkVO> workVOS = works.stream().map(Work::toWorkVO).collect(Collectors.toList());
        long workCount = workService.getWorkCount();
        WorkListVO workListVO = new WorkListVO();
        workListVO.setWorkList(workVOS);
        workListVO.setCount(workCount);
        return ResponseEntity.ok(workListVO);
    }

    @Operation(summary = "获取果实的采摘工作列表")
    @GetMapping("/produce/{id}")
    @PreAuthorize(Security.PRE_AUTHORIZE_EMPLOYEE)
    public ResponseEntity<List<WorkVO>> getProduceWorks(@PathVariable long id) {
        List<Work> produceWorks = workService.getProduceWorks(id);
        List<WorkVO> workVOS = produceWorks.stream().map(Work::toWorkVO).collect(Collectors.toList());
        return ResponseEntity.ok(workVOS);
    }
}
