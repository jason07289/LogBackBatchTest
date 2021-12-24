package com.jaehyuk.batch.schedule;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.jaehyuk.batch.dao.TestDao;
import com.jaehyuk.batch.vo.TestVo;

@Component
public class ScheduledTasks {
	private static final Logger log = LoggerFactory.getLogger(ScheduledTasks.class);

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    @Autowired
    private TestDao testDao;
    
    private TestVo vo;
    //5초마다 로그를 뿌린다
    @Scheduled(fixedRate = 5000) //5초마다 실행하도록 스케쥴을 추가한다
    public void reportCurrentTime() {
        log.info("The time is now {}", dateFormat.format(new Date()));
        if( vo == null ) {
        	vo = new TestVo();
        }
       try {
    	   testDao.getTestInfo(vo);
       } catch( Exception  e) {
    	   e.printStackTrace();
       }
     }
}