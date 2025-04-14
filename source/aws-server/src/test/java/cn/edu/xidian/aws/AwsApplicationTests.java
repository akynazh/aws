//package cn.edu.xidian.aws;
//
//import cn.edu.xidian.aws.constant.ScaleUnit;
//import cn.edu.xidian.aws.constant.WorkStatus;
//import cn.edu.xidian.aws.pojo.po.Record;
//import cn.edu.xidian.aws.pojo.po.Work;
//import cn.edu.xidian.aws.pojo.vo.work.WorkAddVO;
//import cn.edu.xidian.aws.repository.RecordRepository;
//import cn.edu.xidian.aws.repository.WorkRepository;
//import cn.edu.xidian.aws.service.*;
//import cn.edu.xidian.aws.util.TimeUtil;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import java.math.BigDecimal;
//import java.text.ParseException;
//import java.util.List;
//import java.util.Random;
//
//@SpringBootTest
//class AwsApplicationTests {
//    @Autowired
//    private UserService userService;
//    @Autowired
//    private PredictService predictService;
//    @Autowired
//    private ProduceService produceService;
//    @Autowired
//    private ImageService imageService;
//    @Autowired
//    private WorkService workService;
//    @Autowired
//    private RecordService recordService;
//    @Autowired
//    private WorkRepository workRepository;
//    @Autowired
//    private RecordRepository recordRepository;
//
//    @Test
//    void test() {
//        System.out.println(imageService.handle("minio-edge/aws/DOG.jpg"));
//        predictService.predict("https://akynazh.site/images/pub/watermelon_d79dab34-ddf3-41fd-b6b6-8149bedc4670.png", "");
//        List<Work> works = workRepository.findAll();
//        for (Work work : works) {
//            recordService.updateWorkOutput(work.getId());
//        }
//    }
//
//    @Test
//    void gen() throws ParseException {
//        gen(0L, "2024-01-01", "2024-03-01");
//        for (long i = 4; i <= 21; i += 1) {
//            gen(i, "2022-01-01", "2022-03-01");
//            gen(i, "2023-01-01", "2023-03-01");
//            gen(i, "2024-01-01", "2024-03-01");
//        }
//    }
//
//    void gen(Long produceId, String startTime, String endTime) throws ParseException {
//        Work work = new Work();
//        work.setProduceId(produceId);
//        long stime = TimeUtil.getTimestampFromFormatDate(startTime + " 00:00:00");
//        work.setStartTime(stime);
//        work.setEndTime(TimeUtil.getTimestampFromFormatDate(endTime + " 00:00:00"));
//        work.setDataValue(BigDecimal.ZERO);
//        work.setUnit(ScaleUnit.KG.getCode());
//        work.setCreateTime(stime);
//        work.setUpdateTime(stime);
//        work.setStatus(WorkStatus.FINISHED.getCode());
//        Work savedWork = workRepository.save(work);
//        long[] empIds = {3, 7, 8, 18, 20, 21};
//        long[] scaleIds = {2, 3, 4, 5, 6};
//
//        for (int i = 0; i < 50; i++) {
//            Record record = new Record();
//            record.setWorkId(savedWork.getId());
//            record.setProduceId(savedWork.getProduceId());
//            record.setEmployeeId(empIds[new Random().nextInt(empIds.length)]);
//            record.setScaleId(scaleIds[new Random().nextInt(scaleIds.length)]);
//            record.setDataValue(BigDecimal.valueOf(new Random().nextDouble(10, 50)));
//            record.setDataErrorMargin(BigDecimal.valueOf(new Random().nextDouble(0.1, 1)));
//            record.setUnit(ScaleUnit.KG.getCode());
//            record.setDataTime(stime + new Random().nextLong(1000 * 3600 * 24 * 7));
//            try {
//                recordRepository.save(record);
//            } catch (Exception e) {
//                System.out.println(e.getMessage());
//            }
//        }
//        recordService.updateWorkOutput(savedWork.getId());
//    }
//
//}
