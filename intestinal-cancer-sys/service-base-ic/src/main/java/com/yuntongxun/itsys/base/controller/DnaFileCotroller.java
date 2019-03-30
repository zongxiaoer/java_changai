package com.yuntongxun.itsys.base.controller;

import com.alibaba.fastjson.JSON;
import com.yuntongxun.itsys.base.common.constans.Constans;
import com.yuntongxun.itsys.base.common.exception.ItSysException;
import com.yuntongxun.itsys.base.common.util.CookieUtil;
import com.yuntongxun.itsys.base.common.util.GlobalErrorCode;
import com.yuntongxun.itsys.base.common.util.JSONUtils;
import com.yuntongxun.itsys.base.common.util.ResultUtil;
import com.yuntongxun.itsys.base.po.FileUploadLogPO;
import com.yuntongxun.itsys.base.service.FileUploadService;
import com.yuntongxun.itsys.base.vo.ColonoscopyVo;
import com.yuntongxun.itsys.base.vo.FileVo;
import com.yuntongxun.itsys.base.vo.Result;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;

/**
 * @author wp_sp
 * @date 2018/5/10
 */
@RestController
@RequestMapping("/dnafile")
//@CrossOrigin
public class DnaFileCotroller {

    final Logger log = LogManager.getLogger(DnaFileCotroller.class);

    @Autowired
    private FileUploadService fileUploadService;

    @Value("${prop.upload-folder}")
    private String uploadFolder;

    /**
     * 单文件上传
     *
     * @param file
     * @return
     */
    @RequestMapping(value = "/singleUpload", method = RequestMethod.POST)
    @ResponseBody
    public Result upload(@RequestParam("file") MultipartFile file, HttpServletRequest request) {
        log.debug("传入的文件参数：{}", JSON.toJSONString(file, true));
        if (!file.isEmpty()) {
            String loginName = CookieUtil.getCookieByLoginName(request);
            FileUploadLogPO fileUploadLogPO = fileUploadService.upLoadSingleFile(file, loginName, Constans.FILE_SOURCE_TYPE_4);
            return ResultUtil.success(fileUploadLogPO, "文件上传成功");
        } else {
            return ResultUtil.error(GlobalErrorCode.FILE_UPLOAD_FAIL_CODE, GlobalErrorCode.FILE_UPLOAD_FAIL_MSG);
        }
    }

    /**
     * 下载
     *
     * @param request
     * @param response
     * @return
     * @throws IOException
     * @throws Exception
     */
    @RequestMapping(value="/downFile",method = RequestMethod.GET)
    public void uploadFile(@RequestParam("filePath") String filePath, HttpServletResponse response, HttpServletRequest request){
        BufferedInputStream  bis = null;
        BufferedOutputStream bos = null;
        try {
            String url=java.net.URLDecoder.decode(uploadFolder+filePath, "UTF-8");
            String fileName = url.substring(url.lastIndexOf("/")+1);
            File f = new File(url);
            response.setContentType("text/html;charset=UTF-8");
            request.setCharacterEncoding("UTF-8");
            response.setContentType("text/html;charset=UTF-8");
            response.setHeader("Content-disposition", "attachment; filename="
                    + new String(fileName.getBytes("utf-8"), "ISO8859-1"));
            response.setHeader("Content-Length", String.valueOf(f.length()));
            bis = new BufferedInputStream(new FileInputStream(f));
            bos = new BufferedOutputStream(response.getOutputStream());
            byte[] buff = new byte[2048];
            int bytesRead;
            while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
                bos.write(buff, 0, bytesRead);
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.info("Download Error 403  文件不存在");
            throw new ItSysException(GlobalErrorCode.OBJECT_NOT_EXISTS+"",
                    GlobalErrorCode.OBJECT_NOT_EXISTS_MSG);
        }finally{
            try {
                if(null != bis){
                    bis.close();
                }
                if(null != bis){
                    bos.close();
                }
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }

}
