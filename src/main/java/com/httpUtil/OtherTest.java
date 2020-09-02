package com.httpUtil;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.UUID;

/**
 * TODO 请详细说明该类的作用
 *
 * @author lvzhixiang
 * @since 2020/8/25
 */
@Slf4j
public class OtherTest {
    public static void main(String[] args) {
        String str = UUID.randomUUID().toString();
        //FileUtils.copyToFile();
        boolean sss = StringUtils.isEmpty("123");
        /*try() {

        } catch (IOException e){
            log.error("文件流出错：[{}]",e.getMessage());
        }*/
        System.out.println(sss);
        System.out.println(str);
    }
}
