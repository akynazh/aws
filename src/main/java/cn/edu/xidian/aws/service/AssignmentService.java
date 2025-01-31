package cn.edu.xidian.aws.service;

import cn.edu.xidian.aws.constant.AssignmentStatus;
import cn.edu.xidian.aws.pojo.po.Assignment;
import cn.edu.xidian.aws.pojo.po.User;
import cn.edu.xidian.aws.pojo.vo.assignment.MyAssignmentsVO;
import cn.edu.xidian.aws.pojo.vo.assignment.WorkAssignVO;
import cn.edu.xidian.aws.pojo.vo.assignment.WorkAssignmentsVO;
import cn.edu.xidian.aws.pojo.vo.assignment.WorkReassignVO;
import cn.edu.xidian.aws.repository.AssignmentRepository;
import com.google.common.collect.Sets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author akynazh@gmail.com
 * @date 2025/1/16
 * @description
 */
@Service
public class AssignmentService {
    @Autowired
    private AssignmentRepository assignmentRepository;

    public WorkAssignmentsVO assignWork(WorkAssignVO vo) {
        long workId = vo.getWorkId();
        List<Long> employeeIdList = vo.getEmployeeIdList();
        List<Assignment> assignments = getAssignments(workId, employeeIdList);
        assignmentRepository.saveAll(assignments);
        WorkAssignmentsVO result = new WorkAssignmentsVO();
        result.setEmployeeIds(employeeIdList);
        result.setWorkId(workId);
        return result;
    }

    public WorkAssignmentsVO reassignWork(WorkReassignVO vo) {
        long workId = vo.getWorkId();
        List<Long> employeeIdList = vo.getEmployeeIdList();
        List<Assignment> prevAssignments = assignmentRepository.findAllByWorkId(workId);
        Set<Long> employeeIdSet = new HashSet<>(employeeIdList);
        Set<Long> prevEmployeeIdSet = prevAssignments.stream().map(Assignment::getEmployeeId).collect(Collectors.toSet());
        List<Long> newIds = Sets.difference(employeeIdSet, prevEmployeeIdSet).stream().toList();
        Set<Long> noIds = new HashSet<>(Sets.difference(prevEmployeeIdSet, employeeIdSet));
        for (Assignment prevAssignment : prevAssignments) {
            if (noIds.contains(prevAssignment.getEmployeeId())) {
                prevAssignment.setStatus(AssignmentStatus.DISABLED.getCode());
            }
        }
        List<Assignment> assignments = getAssignments(workId, newIds);
        prevAssignments.addAll(assignments);
        assignmentRepository.saveAll(assignments);
        WorkAssignmentsVO result = new WorkAssignmentsVO();
        result.setEmployeeIds(employeeIdList);
        result.setWorkId(workId);
        return result;
    }

    public MyAssignmentsVO getMyAssignments(User user) {
        List<Assignment> assignments = assignmentRepository.findAllByEmployeeId(user.getId());
        MyAssignmentsVO vo = new MyAssignmentsVO();
        List<Long> ids = assignments.stream().filter(x -> x.getStatus() == AssignmentStatus.ENABLE.getCode()).map(Assignment::getWorkId).collect(Collectors.toList());
        vo.setWorkIds(ids);
        return vo;
    }

    public WorkAssignmentsVO getWorkAssignments(long workId) {
        List<Assignment> assignments = assignmentRepository.findAllByWorkId(workId);
        WorkAssignmentsVO vo = new WorkAssignmentsVO();
        List<Long> ids = assignments.stream().filter(x -> x.getStatus() == AssignmentStatus.ENABLE.getCode()).map(Assignment::getEmployeeId).collect(Collectors.toList());
        vo.setEmployeeIds(ids);
        vo.setWorkId(workId);
        return vo;
    }

    private List<Assignment> getAssignments(long workId, List<Long> employeeIds) {
        List<Assignment> assignments = new ArrayList<>();
        for (Long id : employeeIds) {
            Assignment assignment = new Assignment();
            assignment.setWorkId(workId);
            assignment.setEmployeeId(id);
            assignment.setCreateTime(System.currentTimeMillis());
            assignment.setUpdateTime(System.currentTimeMillis());
            assignment.setStatus(AssignmentStatus.ENABLE.getCode());
            assignments.add(assignment);
        }
        return assignments;
    }
}
