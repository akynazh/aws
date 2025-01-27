package cn.edu.xidian.aws.controller;

import cn.edu.xidian.aws.constant.Constants;
import cn.edu.xidian.aws.pojo.po.Produce;
import cn.edu.xidian.aws.pojo.vo.common.RestResponse;
import cn.edu.xidian.aws.pojo.vo.produce.ProduceAddVO;
import cn.edu.xidian.aws.pojo.vo.produce.ProduceUpdateVO;
import cn.edu.xidian.aws.pojo.vo.produce.ProduceVO;
import cn.edu.xidian.aws.service.ProduceService;
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
 * @description 农作物模块
 */
@RestController
@RequestMapping("/produce")
@Slf4j
public class ProduceController {
    @Autowired
    private ProduceService produceService;

    @PostMapping
    @PreAuthorize(Constants.PRE_AUTHORIZE_ADMIN)
    public ResponseEntity<ProduceVO> addProduce(@RequestBody ProduceAddVO vo) {
        Produce produce = produceService.addProduce(vo);
        return ResponseEntity.ok(Produce.toProduceVO(produce));
    }

    @PutMapping
    @PreAuthorize(Constants.PRE_AUTHORIZE_ADMIN)
    public ResponseEntity<ProduceVO> updateProduce(@RequestBody ProduceUpdateVO vo) {
        Produce produce = produceService.updateProduce(vo);
        return ResponseEntity.ok(Produce.toProduceVO(produce));
    }

    @GetMapping("/list")
    @PreAuthorize(Constants.PRE_AUTHORIZE_EMPLOYEE)
    public RestResponse<List<ProduceVO>> getProduceList(@RequestParam(defaultValue = "0") int page) {
        Page<Produce> producesPage = produceService.getProduces(page, 10);
        List<Produce> produces = producesPage.getContent();
        List<ProduceVO> produceVOS = produces.stream().map(Produce::toProduceVO).collect(Collectors.toList());
        return new RestResponse<>(HttpStatus.OK, produceVOS);
    }

    @GetMapping("/{id}")
    @PreAuthorize(Constants.PRE_AUTHORIZE_EMPLOYEE)
    public RestResponse<ProduceVO> getProduce(@PathVariable Long id) {
        Produce produce = produceService.getProduce(id);
        return new RestResponse<>(HttpStatus.OK, Produce.toProduceVO(produce));
    }
}
