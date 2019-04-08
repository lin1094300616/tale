package com.inchwisp.tale;

import com.inchwisp.tale.framework.entity.ConstantsEnum;
import com.inchwisp.tale.framework.util.CommUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TaleApplicationTests {

    @Test
    public void contextLoads() {

        /*File file = new File(ConstantsEnum.UPLOAD + "movie" + "\\" + "aa.txt");
        if(!file.exists()){
            file.mkdirs();
        }*/

        /*Date date = CommUtil.stringToDate(string);
        System.out.println("date = " + date);*/

        String testString = "\"2019-04-07T16:00:00.000Z\"";
        String test = CommUtil.simplifyDateString(testString);

    }

}

