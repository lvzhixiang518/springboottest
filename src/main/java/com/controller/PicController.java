package com.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Controller
public class PicController implements ServletContextAware {
    private ServletContext servletContext;

    @Override
    public void setServletContext(ServletContext arg) {
        this.servletContext = arg;
    }

    @RequestMapping(value = "/imgupload.do", method = RequestMethod.GET)
    @ResponseBody//加上该注解返回json数据     imgs页面的参数
    public String imgLoad(@RequestParam("imgs") MultipartFile commonsMultipartFile) {
//        定义上传图片的路径
        String realPath = "D:"+File.separator+"系统test"+File.separator+"springbootTest"+File.separator+"src"+File.separator+"main"+File.separator+"resources"+File.separator+"public"+File.separator+"images"+File.separator;
        System.out.println("上传路径：" + realPath);
//        得到文件名  通过CommonsMultipartFilename得到图片的名称
        String originalFilename = commonsMultipartFile.getOriginalFilename();
        System.out.println("文件名：" + originalFilename);
        File file = new File(realPath, originalFilename);
        if (!file.exists()){
            file.mkdirs();
        }
        //往服务器写入数据 上传文件
        try {
            commonsMultipartFile.transferTo(file);
            System.out.println("上传成功！");
        } catch (Exception e) {
            e.printStackTrace();
        }//将文件名与路径拼接在一起
        String path = "./images/" + originalFilename;
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("paths", path);//将路径放入json对象中
        String json = JSON.toJSONString(jsonObject);
        System.out.println("JSON:" + json);
        return json;//返回json对象
    }





    /**
     * 图片上传
     * @param photo
     * @param request
     * @return
     */
    @RequestMapping(value = "/uploadPhoto", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, String> uploadPhoto(MultipartFile photo, HttpServletRequest request) {
        String s = "";
        try {
            s = getContent(request);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Map<String, String> ret = new HashMap<String, String>();
        if (photo == null) {
            ret.put("type", "error");
            ret.put("msg", "选择要上传的文件！");
            return ret;
        }
        if (photo.getSize() > 1024 * 1024 * 10) {
            ret.put("type", "error");
            ret.put("msg", "文件大小不能超过10M！");
            return ret;
        }
        //获取文件后缀
        String suffix = photo.getOriginalFilename().substring(photo.getOriginalFilename().lastIndexOf(".") + 1, photo.getOriginalFilename().length());
        if (!"jpg,jpeg,gif,png".toUpperCase().contains(suffix.toUpperCase())) {
            ret.put("type", "error");
            ret.put("msg", "请选择jpg,jpeg,gif,png格式的图片！");
            return ret;
        }
        //获取项目根目录加上图片目录 webapp/static/imgages/upload/
        String savePath = "D:"+File.separator+"系统test"+File.separator+"springbootTest"+File.separator+"src"+File.separator+"main"+File.separator+"resources"+File.separator+"public" +File.separator+"images"+File.separator;
        File savePathFile = new File(savePath);
        if (!savePathFile.exists()) {
            //若不存在该目录，则创建目录
            savePathFile.mkdirs();
        }
        String filename = new Date().getTime() + "." + suffix;
        try {
            //将文件保存指定目录
            photo.transferTo(new File(savePath + filename));
        } catch (Exception e) {
            ret.put("type", "error");
            ret.put("msg", "保存文件异常！");
            e.printStackTrace();
            return ret;
        }
        ret.put("type", "success");
        ret.put("msg", "上传图片成功！");
        String url = request.getSession().getServletContext().getContextPath();
        System.out.println(url);
        ret.put("filepath", "images/");
        ret.put("filename", filename);
        return ret;
    }

    /**
     * 输入流转成String
     */
    public static String getContent(HttpServletRequest request) throws Exception {
        String content = "";
        try {
            InputStreamReader inputReader = new InputStreamReader(request.getInputStream(), "UTF-8");
            BufferedReader bufferReader = new BufferedReader(inputReader);
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = bufferReader.readLine()) != null) {
                sb.append(line);
            }
            content = sb.toString();
            return content;
        } catch (Exception e) {
            throw new Exception("InputStream to String error");
        }
    }


}
