package cx.study.auction.controller;

import cx.study.auction.bean.HttpResult;
import org.hibernate.loader.custom.Return;
import org.springframework.stereotype.Controller;
import org.springframework.util.Base64Utils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Random;
import java.util.UUID;

/**
 *
 * Created by cheng.xiao on 2017/5/2.
 */
@Controller
public class FileController {

    @GetMapping("/download/{filename}")
    public void download(@PathVariable String filename, HttpServletResponse response){
        filename = new String(Base64Utils.decodeFromString(filename));
        response.setHeader("content-type", "application/octet-stream");
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment;filename=" + filename);
        byte[] buff = new byte[1024];
        BufferedInputStream bis = null;
        OutputStream os ;
        try {
            os = response.getOutputStream();
            bis = new BufferedInputStream(new FileInputStream(new File("d:/"+ filename)));
            int i = bis.read(buff);
            while (i != -1) {
                os.write(buff, 0, buff.length);
                os.flush();
                i = bis.read(buff);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bis != null) {
                try {
                    bis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        System.out.println("success");
    }

    @PostMapping("/upload/image")
    @ResponseBody
    public HttpResult<String> uploadImage(@RequestParam("file") MultipartFile file, HttpServletRequest request) throws Exception {
        if (file.isEmpty()){
            return new HttpResult<>(1,"失败",null);
        }
        String filename = file.getOriginalFilename();
        String suffix = filename.substring(filename.lastIndexOf("."));
        filename = UUID.randomUUID().toString() + suffix;
      //  String path = request.getServletContext().getRealPath("/file/image");
        String path = "D:/apache-tomcat-7.0.68-img/webapps/ROOT/file/image";
        File savePath = new File(path);
        if (!savePath.exists()){
            savePath.mkdirs();
        }
        File local = new File(savePath,filename);
        file.transferTo(local);
        return new HttpResult<>(0,"成功","/file/image/" + filename);
    }
}
