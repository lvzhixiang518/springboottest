package com.httpUtil;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.UUID;

/**
 * 使用HttpURLConnection发送请求
 * @author lvzhixiang
 * @since 2020年8月19日11:39:01
 */
@Controller
@Slf4j
public class HttpClientUtil {

    @RequestMapping(value={"/uploadFile"},method = {RequestMethod.POST})
    public String uploadFile(MultipartFile file) throws Exception {
        FileInputStream fileInputStream = null;
        MultipartFile multipartFile = null;
        String uuid = UUID.randomUUID().toString().replace("-", "");
        String originalFilename = file.getOriginalFilename();
        File myFile = new File("D:"+File.separator+"图片"+File.separator+"dianying.mp4");
        fileInputStream = new FileInputStream(myFile);
        //multipartFile = new Moc
        String extName = originalFilename.substring(originalFilename.lastIndexOf("."));
        String fileName=uuid+extName;
        // 10位时间戳
        //long timestamp10 = System.currentTimeMillis() / 1000L;
        // 签名算法
        @SuppressWarnings("deprecation")
        //String sign = DigestUtils.shaHex(adminemail + "&" + token + "&" + timestamp10);
        // 创建客户  url
        String url = "http://10.1.3.85:8033/xxxxx/xx/xxxx";
        log.info("创建 url= [{}]",url);
        String result="";
        try {
            // 服务器的域名
            URL url1 = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) url1.openConnection();
            //设定请求的方法为"POST"，默认是GET
            conn.setRequestMethod("POST");
            conn.setConnectTimeout(100000);// 连接超时单位毫秒
            conn.setReadTimeout(200000);// 读取超时 单位毫秒
            // 发送POST请求必须设置如下两行
            // 设置是否向httpUrlConnection输出，因为这个是post请求，参数要放在
            // http正文内，因此需要设为true, 默认情况下是false;
            conn.setDoOutput(true);// 是否输入参数
            // 设置是否从httpUrlConnection读入，默认情况下是true;
            conn.setDoInput(true);
            //Post 请求不能使用缓存
            conn.setUseCaches(false);
            // 设置请求头参数
            conn.setRequestProperty("connection", "Keep-Alive");//保持长连接
            conn.setRequestProperty("Content-Type", "application/octet-stream;charset=utf-8");//以文件流的方式进行传输
            //conn.setRequestProperty("Transfer-Encoding", "chunkEd");//
            /*// 连接，从上述url.openConnection()至此的配置必须要在connect之前完成，
            conn.connect();*/
            // 所以在开发中不调用上述的connect()也可以)，
            // 此处getOutputStream会隐含的进行connect(即：如同调用上面的connect()方法，
            // 现在通过输出流对象构建对象输出流对象，以实现输出可序列化的对象。
            OutputStream out = new DataOutputStream(conn.getOutputStream());//getInputStream()也是同理
            /*// 上传文件
            StringBuilder sb = new StringBuilder();
            // 将参数头的数据写入到输出流中
            out.write(sb.toString().getBytes());*/
            // 数据输入流,用于读取文件数据
            DataInputStream in = new DataInputStream(file.getInputStream());
            //DataInputStream in = new DataInputStream(file1.);
            byte[] bufferOut = new byte[1024*20];
            int bytes = 0;
            // 每次读  数据,并且将文件数据写入到输出流中
            while ((bytes = in.read(bufferOut)) != -1) {
                out.write(bufferOut, 0, bytes);
            }
            in.close();
            out.flush();
            out.close();
            /*虽然底层的网络连接可以被多个HttpURLConnection实例共享，但每一个HttpURLConnection实例只能发送一个请求。
            请求结束之后，应该调用HttpURLConnection实例的InputStream或OutputStream的close()方法以释放请求的网络资源，
            不过这种方式对于持久化连接没用。对于持久化连接，得用disconnect()方法关闭底层连接的socket。*/
            conn.disconnect();
            // 定义BufferedReader输入流来读取URL的响应
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line = null;
            while ((line = reader.readLine()) != null) {
                result += line; //这里读取的是上边url对应的上传文件接口的返回值，读取出来后，然后接着返回到前端，实现接口中调用接口的方式
            }
        } catch (Exception e) {
            System.out.println("发送POST请求出现异常！" + e);
            e.printStackTrace();
        }
        System.out.println(result);
        return JSON.toJSONString(result);
    }


    public static void main(String[] args) throws IOException {
        String url = "http://xxxx.xx.com/xxxxx/xx/xxxx";
        URL url1 = new URL(url);
        HttpURLConnection conn = (HttpURLConnection) url1.openConnection();
        //设定请求的方法为"POST"，默认是GET
        conn.setRequestMethod("POST");
        conn.setConnectTimeout(100000);// 连接超时单位毫秒
        conn.setReadTimeout(200000);// 读取超时 单位毫秒
        // 发送POST请求必须设置如下两行
        // 设置是否向httpUrlConnection输出，因为这个是post请求，参数要放在
        // http正文内，因此需要设为true, 默认情况下是false;
        conn.setDoOutput(true);// 是否输入参数
        // 设置是否从httpUrlConnection读入，默认情况下是true;
        conn.setDoInput(true);
        //Post 请求不能使用缓存
        conn.setUseCaches(false);
        // 设置请求头参数
        conn.setRequestProperty("connection", "Keep-Alive");//保持长连接
        conn.setRequestProperty("Content-Type", "application/octet-stream;charset=utf-8");//以文件流的方式进行传输
        //conn.setRequestProperty("Transfer-Encoding", "chunkEd");//
            /*// 连接，从上述url.openConnection()至此的配置必须要在connect之前完成，
            conn.connect();*/
        // 所以在开发中不调用上述的connect()也可以)，
        // 此处getOutputStream会隐含的进行connect(即：如同调用上面的connect()方法，
        // 现在通过输出流对象构建对象输出流对象，以实现输出可序列化的对象。
        OutputStream out = new DataOutputStream(conn.getOutputStream());//getInputStream()也是同理
        byte[] bufferOut = new byte[1024*20];
        out.write(bufferOut, 0, bufferOut.length);
        BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
    }
}
