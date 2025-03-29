package cn.edu.xidian.aws.cron;

import cn.edu.xidian.aws.constant.WorkStatus;
import cn.edu.xidian.aws.pojo.po.Work;
import cn.edu.xidian.aws.repository.WorkRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author akynazh@gmail.com
 * @date 3/18/25
 * @description
 */
@Component
@Slf4j
public class WorkStatusUpdater {
    @Autowired
    private WorkRepository workRepository;

    @Scheduled(fixedRate = 5 * 1000) // 5s
    public void updateJobStatus() {
        List<Work> works = workRepository.findAllByStatus(WorkStatus.NOT_STARTED.getCode());
        for (Work work : works) {
            if (System.currentTimeMillis() >= work.getStartTime()
                    && System.currentTimeMillis() <= work.getEndTime()) {
                updateWorkStatus(work, WorkStatus.ONGOING);
            }
        }
        works = workRepository.findAllByStatus(WorkStatus.ONGOING.getCode());
        for (Work work : works) {
            if (System.currentTimeMillis() > work.getEndTime()) {
                updateWorkStatus(work, WorkStatus.FINISHED);
            }
        }
    }

    private void updateWorkStatus(Work work, WorkStatus status) {
        work.setStatus(status.getCode());
        workRepository.save(work);
        log.info("Updated work {} to status {}", work.getId(), status.getMessage());
    }
}
