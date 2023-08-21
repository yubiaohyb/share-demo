package com.yubiaohyb.sharedemo.controller;

import com.yubiaohyb.sharedemo.annotation.ResponseHeader;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Objects;
import java.util.Random;
import java.util.UUID;

/**
 * 人若志趣不远，心不在焉，虽学不成。
 * <p>
 * description  -  functionDescrption
 *
 * @author Yubiao Huang (https://github.com/yubiaohyb)
 * @version $$Id$$
 * @since 2023/4/14 11:42
 */
@Controller
@RequestMapping("/test2")
@Slf4j
public class TestController2 {
    @RequestMapping("/chunk")
    public void chunk(HttpServletResponse response) {
        response.setContentType("text/html; charset=utf-8");
        response.setHeader("Transfer-Encoding", "chunked");
//        response.setHeader("Connection", "keep-alive");
        DataOutputStream dataOutputStream = null;
        Random random = new Random();
        try {
            ServletOutputStream outputStream = response.getOutputStream();
            dataOutputStream = new DataOutputStream(outputStream);
            int count = 0;
            int lineCount = 0;
            while(count++ < 1000) {

                int i = random.nextInt(10);
                if ( lineCount++ < 50) {
                    dataOutputStream.writeUTF(i+"");
                } else {
                    dataOutputStream.writeUTF(i+"\n");
                }
//                dataOutputStream.writeInt(i);


//                dataOutputStream.writeUTF(UUID.randomUUID().toString());
                dataOutputStream.flush();
                Thread.sleep(500);
            }


        } catch (Exception e){
            log.info("主体报错", e);
        } finally {
            if (Objects.isNull(dataOutputStream)) {
                return;
            }
            try {
                dataOutputStream.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }


    }
}
