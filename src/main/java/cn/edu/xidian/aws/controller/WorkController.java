package cn.edu.xidian.aws.controller;

import cn.edu.xidian.aws.constant.Constants;
import cn.edu.xidian.aws.pojo.po.User;
import cn.edu.xidian.aws.pojo.po.Work;
import cn.edu.xidian.aws.pojo.vo.assignment.*;
import cn.edu.xidian.aws.pojo.vo.common.RestResponse;
import cn.edu.xidian.aws.pojo.vo.work.WorkAddVO;
import cn.edu.xidian.aws.pojo.vo.work.WorkUpdateVO;
import cn.edu.xidian.aws.pojo.vo.work.WorkVO;
import cn.edu.xidian.aws.service.AssignmentService;
import cn.edu.xidian.aws.service.JwtService;
import cn.edu.xidian.aws.service.UserService;
import cn.edu.xidian.aws.service.WorkService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
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
public class WorkController {
    @Autowired
    private WorkService workService;
    @Autowired
    private AssignmentService assignmentService;
    @Autowired
    private UserService userService;
    @Autowired
    private JwtService jwtService;

    @PostMapping
    @PreAuthorize(Constants.PRE_AUTHORIZE_ADMIN)
    public RestResponse<WorkVO> addWork(@RequestBody WorkAddVO vo) {
        Work work = workService.addWork(vo);
        return new RestResponse<>(HttpStatus.OK, Work.toWorkVO(work));
    }

    @PutMapping
    @PreAuthorize(Constants.PRE_AUTHORIZE_ADMIN)
    public RestResponse<WorkVO> updateWork(@RequestBody WorkUpdateVO vo) {
        Work work = workService.updateWork(vo);
        return new RestResponse<>(HttpStatus.OK, Work.toWorkVO(work));
    }

    @GetMapping("/{id}")
    @PreAuthorize(Constants.PRE_AUTHORIZE_ADMIN)
    public RestResponse<WorkVO> getWork(@PathVariable long id) {
        Work work = workService.getWork(id);
        return new RestResponse<>(HttpStatus.OK, Work.toWorkVO(work));
    }

    @GetMapping("/list")
    @PreAuthorize(Constants.PRE_AUTHORIZE_ADMIN)
    public RestResponse<List<WorkVO>> getWorks(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        List<Work> works = workService.getWorks(page, size);
        List<WorkVO> workVOS = works.stream().map(Work::toWorkVO).collect(Collectors.toList());
        return new RestResponse<>(HttpStatus.OK, workVOS);
    }

    @GetMapping("/produce/{id}")
    @PreAuthorize(Constants.PRE_AUTHORIZE_ADMIN)
    public RestResponse<List<WorkVO>> getProduceWorks(@PathVariable long id) {
        List<Work> produceWorks = workService.getProduceWorks(id);
        List<WorkVO> workVOS = produceWorks.stream().map(Work::toWorkVO).collect(Collectors.toList());
        return new RestResponse<>(HttpStatus.OK, workVOS);
    }

    @PreAuthorize(Constants.PRE_AUTHORIZE_ADMIN)
    @PostMapping("/assign")
    public RestResponse<WorkAssignmentsVO> assignWork(@RequestBody WorkAssignVO vo) {
        return new RestResponse<>(HttpStatus.OK, assignmentService.assignWork(vo));
    }

    @PreAuthorize(Constants.PRE_AUTHORIZE_ADMIN)
    @PutMapping("/assign")
    public RestResponse<WorkAssignmentsVO> reassignWork(@RequestBody WorkReassignVO vo) {
        return new RestResponse<>(HttpStatus.OK, assignmentService.reassignWork(vo));
    }

    @PreAuthorize(Constants.PRE_AUTHORIZE_EMPLOYEE)
    @GetMapping("/assignments/me")
    public RestResponse<MyAssignmentsVO> getMyAssignments(HttpServletRequest request) {
        User user = userService.getUser(jwtService.extractUsername(jwtService.extractToken(request)));
        return new RestResponse<>(HttpStatus.OK, assignmentService.getMyAssignments(user));
    }

    @PreAuthorize(Constants.PRE_AUTHORIZE_ADMIN)
    @GetMapping("/{id}/assignments")
    public RestResponse<WorkAssignmentsVO> getWorkAssignments(@PathVariable Long id) {
        return new RestResponse<>(HttpStatus.OK, assignmentService.getWorkAssignments(id));
    }
}
