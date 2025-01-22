package cn.edu.xidian.aws.controller;

import cn.edu.xidian.aws.constant.Constants;
import cn.edu.xidian.aws.pojo.po.User;
import cn.edu.xidian.aws.pojo.po.Work;
import cn.edu.xidian.aws.pojo.vo.common.RestResponse;
import cn.edu.xidian.aws.pojo.vo.user.UserRegisterVO;
import cn.edu.xidian.aws.pojo.vo.user.UserVO;
import cn.edu.xidian.aws.pojo.vo.work.WorkAddVO;
import cn.edu.xidian.aws.pojo.vo.work.WorkUpdateVO;
import cn.edu.xidian.aws.pojo.vo.work.WorkVO;
import cn.edu.xidian.aws.service.WorkService;
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
 * @description
 */
@RestController
@Slf4j
@RequestMapping("/work")
public class WorkController {
    @Autowired
    private WorkService workService;

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
    public RestResponse<List<WorkVO>> getWorks(@RequestParam(defaultValue = "0") int page) {
        Page<Work> worksPage = workService.getWorks(page, 10);
        List<Work> works = worksPage.getContent();
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

}
