package com.yuntongxun.itsys.base.service.impl;

import com.yuntongxun.itsys.base.common.constans.Constans;
import com.yuntongxun.itsys.base.common.exception.ItSysException;
import com.yuntongxun.itsys.base.common.util.DateUtil;
import com.yuntongxun.itsys.base.common.util.GlobalErrorCode;
import com.yuntongxun.itsys.base.common.util.JSONUtils;
import com.yuntongxun.itsys.base.common.util.StringUtil;
import com.yuntongxun.itsys.base.dao.ColonoscopyResultDao;
import com.yuntongxun.itsys.base.dao.FileUploadDao;
import com.yuntongxun.itsys.base.dao.FitDao;
import com.yuntongxun.itsys.base.dao.HtEventDao;
import com.yuntongxun.itsys.base.po.ColonoscopyResult;
import com.yuntongxun.itsys.base.po.FileUploadLogPO;
import com.yuntongxun.itsys.base.po.HospitalReview;
import com.yuntongxun.itsys.base.service.FileUploadService;
import net.coobird.thumbnailator.Thumbnails;
import net.sf.json.JSONArray;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @author wp_sp
 * @date 2018/5/10
 */
@Service
public class FileUploadServiceImpl implements FileUploadService {


    @Value("${prop.upload-folder}")
    private String uploadFolder;

    @Value("${prop.upload-folder-img}")
    private String uploadFolderImg;

    @Value("${prop.upload-folder-dna}")
    private String uploadFolderImgDna;

    @Value("${prop.upload-folder-img-chang-result}")
    private String uploadFolderChangImg;

    @Value("${prop.upload-folder-file-cancer-result}")
    private String uploadFolderCancerFile;

    @Autowired
    private FileUploadDao fileUploadDao;

    @Autowired
    private FitDao fitDao;

    @Autowired
    private ColonoscopyResultDao colonoscopyResultDao;

    @Autowired
    private HtEventDao htEventDao;


    @Override
    public String getFileNameSuffix(MultipartFile file) {
        try {
            return file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1);
        } catch (Exception e) {
            throw new ItSysException(GlobalErrorCode.FILE_UPLOAD_FAIL_CODE, GlobalErrorCode.FILE_UPLOAD_FAIL_MSG);
        }
    }

    @Override
    @Transactional
    public FileUploadLogPO upLoadSingleFile(MultipartFile file, String loginName, Integer fileSourceType4) {

        if (StringUtil.isEmpty(loginName)) {
            throw new ItSysException(GlobalErrorCode.FILE_UPLOAD_FAIL_CODE, GlobalErrorCode.FILE_UPLOAD_FAIL_MSG);
        }

        try {
            String suffix = getFileNameSuffix(file);
            String pdf = suffix.toUpperCase();

            InputStream inputStream = file.getInputStream();

            //校验是不是pdf格式
            if(!Constans.FILE_PDF.equals(pdf)){
                throw new ItSysException(GlobalErrorCode.FILE_UPLOAD_TYPE_CODE, GlobalErrorCode.FILE_UPLOAD_TYPE_MSG);
            }
            String fileName = UUID.randomUUID().toString().replaceAll("-", "") + "." + suffix;
            FileUploadLogPO fileUploadLogPO = new FileUploadLogPO();
            fileUploadLogPO.setId(UUID.randomUUID().toString().replaceAll("-", ""));
            fileUploadLogPO.setFileSuffix(suffix);
            fileUploadLogPO.setFileNickname(file.getOriginalFilename());
            fileUploadLogPO.setFileBusinessType(fileSourceType4);
            fileUploadLogPO.setFilePath(uploadFolderImgDna+"/" + DateUtil.formatDate(new Date(),"yyyyMMdd")+ File.separator + fileName);
            fileUploadLogPO.setFileName(fileName);
            fileUploadLogPO.setFileSize(Double.parseDouble(String.valueOf(file.getSize())));
            fileUploadLogPO.setFileUploadUser(loginName);
            fileUploadLogPO.setFileUploadTime(new Date());
            File dest = new File(uploadFolder+uploadFolderImgDna+"/" + DateUtil.formatDate(new Date(),"yyyyMMdd")+ File.separator + fileName);

            // 检测是否存在目录
            if (!dest.getParentFile().exists()) {
                dest.getParentFile().mkdirs();
            }
            BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(dest));
            out.write(file.getBytes());
            out.flush();
            out.close();

            if (fileUploadDao.insertFileUploadLog(fileUploadLogPO) == 1) {
                return fileUploadLogPO;
            } else {
                throw new ItSysException(GlobalErrorCode.FILE_UPLOAD_FAIL_CODE, GlobalErrorCode.FILE_UPLOAD_FAIL_MSG);
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new ItSysException(GlobalErrorCode.FILE_UPLOAD_FAIL_CODE, GlobalErrorCode.FILE_UPLOAD_FAIL_MSG);
        }

    }

    @Override
    public FileUploadLogPO upLoadImgeFile(MultipartFile file, String loginName, Integer fileSourceType5,Integer id) {
        if (StringUtil.isEmpty(loginName)) {
            throw new ItSysException(GlobalErrorCode.FILE_UPLOAD_FAIL_CODE, GlobalErrorCode.FILE_UPLOAD_FAIL_MSG);
        }

        try {
            String suffix = getFileNameSuffix(file);
            String imge = suffix.toUpperCase();
            if(!Constans.PictureTypes.contains(imge)){
                throw new ItSysException(GlobalErrorCode.FILE_UPLOAD_TYPE_CODE, GlobalErrorCode.FILE_UPLOAD_TYPE_MSG);
            }
            String fileName = UUID.randomUUID().toString().replaceAll("-", "") + "." + suffix;
            FileUploadLogPO fileUploadLogPO = new FileUploadLogPO();
            fileUploadLogPO.setId(UUID.randomUUID().toString().replaceAll("-", ""));
            fileUploadLogPO.setFileSuffix(suffix);
            fileUploadLogPO.setFileNickname(file.getOriginalFilename());
            fileUploadLogPO.setFileBusinessType(fileSourceType5);
            fileUploadLogPO.setFilePath(uploadFolderImg+"/" + DateUtil.formatDate(new Date(),"yyyyMMdd")+ File.separator + fileName);
            fileUploadLogPO.setFileName(fileName);
            fileUploadLogPO.setFileSize(Double.parseDouble(String.valueOf(file.getSize())));
            fileUploadLogPO.setFileUploadUser(loginName);
            fileUploadLogPO.setFileUploadTime(new Date());
            File dest = new File(uploadFolder+uploadFolderImg+"/" + DateUtil.formatDate(new Date(),"yyyyMMdd")+ File.separator + fileName);


            // 检测是否存在目录
            if (!dest.getParentFile().exists()) {
                dest.getParentFile().mkdirs();
            }
            BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(dest));
            out.write(file.getBytes());
            out.flush();
            out.close();
            Thumbnails.of(dest).scale(1f).outputQuality(0.2f).toFile(uploadFolder+uploadFolderImg+"/" + DateUtil.formatDate(new Date(),"yyyyMMdd")+ File.separator + fileName);

            if(id!=null){
                if(fitDao.updateUrlByID(uploadFolderImg+"/" + DateUtil.formatDate(new Date(),"yyyyMMdd")+ File.separator + fileName,id)!=1){
                    throw new ItSysException(GlobalErrorCode.FILE_UPLOAD_FAIL_CODE, GlobalErrorCode.FILE_UPLOAD_FAIL_MSG);
                }
            }

            if (fileUploadDao.insertFileUploadLog(fileUploadLogPO) == 1) {
                return fileUploadLogPO;
            } else {
                throw new ItSysException(GlobalErrorCode.FILE_UPLOAD_FAIL_CODE, GlobalErrorCode.FILE_UPLOAD_FAIL_MSG);
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new ItSysException(GlobalErrorCode.FILE_UPLOAD_FAIL_CODE, GlobalErrorCode.FILE_UPLOAD_FAIL_MSG);
        }
    }

    @Override
    public List<FileUploadLogPO> upLoadColonoscopyImgeFiles(MultipartFile[] files, String loginName, Integer fileSourceType6, Integer id) {
        if (StringUtil.isEmpty(loginName)) {
            throw new ItSysException(GlobalErrorCode.FILE_UPLOAD_FAIL_CODE, GlobalErrorCode.FILE_UPLOAD_FAIL_MSG);
        }
        try {
            List<FileUploadLogPO>  fileUploadLogPOS=new ArrayList<>();
            for (MultipartFile file:files) {
                String suffix = getFileNameSuffix(file);
                String imge = suffix.toUpperCase();
                //校验是不是pdf格式
                if(!Constans.PictureTypes.contains(imge)){
                    throw new ItSysException(GlobalErrorCode.FILE_UPLOAD_TYPE_CODE, GlobalErrorCode.FILE_UPLOAD_TYPE_MSG);
                }
                String fileName = UUID.randomUUID().toString().replaceAll("-", "") + "." + suffix;
                FileUploadLogPO fileUploadLogPO = new FileUploadLogPO();
                fileUploadLogPO.setId(UUID.randomUUID().toString().replaceAll("-", ""));
                fileUploadLogPO.setFileSuffix(suffix);
                fileUploadLogPO.setFileNickname(file.getOriginalFilename());
                fileUploadLogPO.setFileBusinessType(fileSourceType6);
                fileUploadLogPO.setFilePath(uploadFolderChangImg+"/" + DateUtil.formatDate(new Date(),"yyyyMMdd")+ File.separator + fileName);
                fileUploadLogPO.setFileName(fileName);
                fileUploadLogPO.setFileSize(Double.parseDouble(String.valueOf(file.getSize())));
                fileUploadLogPO.setFileUploadUser(loginName);
                fileUploadLogPO.setFileUploadTime(new Date());
                fileUploadLogPOS.add(fileUploadLogPO);
                File dest = new File(uploadFolder+uploadFolderChangImg+"/" + DateUtil.formatDate(new Date(),"yyyyMMdd")+ File.separator + fileName);
                // 检测是否存在目录
                if (!dest.getParentFile().exists()) {
                    dest.getParentFile().mkdirs();
                }
                BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(dest));
                out.write(file.getBytes());
                out.flush();
                out.close();
                //Thumbnails.of(dest).scale(1f).outputQuality(0.2f).toFile(uploadFolderChangImg+"/" + DateUtil.formatDate(new Date(),"yyyyMMdd")+ File.separator + fileName);
            }

            if(id!=null){
                List<ColonoscopyResult> colonoscopyResults = colonoscopyResultDao.queryById(id);
                if(colonoscopyResults.size()!=1){
                    throw new ItSysException(GlobalErrorCode.FILE_UPLOAD_FAIL_CODE, GlobalErrorCode.FILE_UPLOAD_FAIL_MSG);
                }
                String imagePath = colonoscopyResults.get(0).getImagePath();
                List<FileUploadLogPO> fileUploadLogPOList=new ArrayList<>();
                if(!StringUtils.isEmpty(imagePath)){
                    fileUploadLogPOList = (List<FileUploadLogPO>) JSONArray.toList(JSONArray.fromObject(imagePath),FileUploadLogPO.class);
                }else{
                    htEventDao.updateStatus(colonoscopyResults.get(0).getSid(), colonoscopyResults.get(0).getId(), Constans.PERSON_TODO_EVENT_TYPE19, Constans.PERSON_TODO_EVENT_STATUS2);//待办完成
                }
                String pathUrl;
                if(fileUploadLogPOList.size()>0){
                    fileUploadLogPOList.addAll(fileUploadLogPOS);
                    pathUrl=JSONUtils.objectToJsonDateSerializer(fileUploadLogPOList,"yyyy-MM-dd");
                }else{
                    pathUrl=JSONUtils.objectToJsonDateSerializer(fileUploadLogPOS,"yyyy-MM-dd");
                }

                if(colonoscopyResultDao.updateUrlByID(pathUrl,id)!=1){
                    throw new ItSysException(GlobalErrorCode.FILE_UPLOAD_FAIL_CODE, GlobalErrorCode.FILE_UPLOAD_FAIL_MSG);
                }
            }
            return fileUploadLogPOS;
        } catch (IOException e) {
            e.printStackTrace();
            throw new ItSysException(GlobalErrorCode.FILE_UPLOAD_FAIL_CODE, GlobalErrorCode.FILE_UPLOAD_FAIL_MSG);
        }
    }

    @Override
    public List<FileUploadLogPO> upLoadCancerFiles(MultipartFile[] files, String loginName, Integer fileSourceType7, Integer id) {
        if (StringUtil.isEmpty(loginName)) {
            throw new ItSysException(GlobalErrorCode.FILE_UPLOAD_FAIL_CODE, GlobalErrorCode.FILE_UPLOAD_FAIL_MSG);
        }
        try {
            List<FileUploadLogPO>  fileUploadLogPOS=new ArrayList<>();
            for (MultipartFile file:files) {
                String suffix = getFileNameSuffix(file);

                String fileName = UUID.randomUUID().toString().replaceAll("-", "") + "." + suffix;
                FileUploadLogPO fileUploadLogPO = new FileUploadLogPO();
                fileUploadLogPO.setId(UUID.randomUUID().toString().replaceAll("-", ""));
                fileUploadLogPO.setFileSuffix(suffix);
                fileUploadLogPO.setFileNickname(file.getOriginalFilename());
                fileUploadLogPO.setFileBusinessType(fileSourceType7);
                fileUploadLogPO.setFilePath(uploadFolderCancerFile+"/" + DateUtil.formatDate(new Date(),"yyyyMMdd")+ File.separator + fileName);
                fileUploadLogPO.setFileName(fileName);
                fileUploadLogPO.setFileSize(Double.parseDouble(String.valueOf(file.getSize())));
                fileUploadLogPO.setFileUploadUser(loginName);
                fileUploadLogPO.setFileUploadTime(new Date());
                fileUploadLogPOS.add(fileUploadLogPO);
                File dest = new File(uploadFolder+uploadFolderCancerFile+"/" + DateUtil.formatDate(new Date(),"yyyyMMdd")+ File.separator + fileName);
                // 检测是否存在目录
                if (!dest.getParentFile().exists()) {
                    dest.getParentFile().mkdirs();
                }
                BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(dest));
                out.write(file.getBytes());
                out.flush();
                out.close();
                //Thumbnails.of(dest).scale(1f).outputQuality(0.2f).toFile(uploadFolderCancerFile+"/" + DateUtil.formatDate(new Date(),"yyyyMMdd")+ File.separator + fileName);
            }


            return fileUploadLogPOS;
        } catch (IOException e) {
            e.printStackTrace();
            throw new ItSysException(GlobalErrorCode.FILE_UPLOAD_FAIL_CODE, GlobalErrorCode.FILE_UPLOAD_FAIL_MSG);
        }
    }

    public static void main(String[] args) throws IOException {
        ///Users/zongtong/Desktop/img/
        File dest = new File("/Users/zongtong/Desktop/img"+"cancer/result"+"/" + DateUtil.formatDate(new Date(),"yyyyMMdd")+ File.separator + "11.txt");
        // dest = new File("/app/nfs/"+"cancer/result"+"/" + DateUtil.formatDate(new Date(),"yyyyMMdd")+ File.separator + "11.txt");
        // 检测是否存在目录
        if (!dest.getParentFile().exists()) {
            dest.getParentFile().mkdirs();
        }
        BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(dest));
        out.write("asdas".getBytes());
        out.flush();
        out.close();

        //Thumbnails.of(new File("/Users/zongtong/Downloads/BI库表2.png")).scale(1f).outputQuality(0.25f).toFile("/Users/zongtong/Desktop/get_pdf/111");
/*        Thumbnails.of("/Users/zongtong/Downloads/她/mmexport1529852611002.jpg")
                .scale(1f)
                .outputQuality(0.25f)
                .outputFormat("jpg")
                .toFile("/Users/zongtong/Desktop/get_pdf/111");*/
    }

 /*   public static BaseResult uploadFileAndCreateThumbnail(MultipartFile imageFile,HttpServletRequest request,String uploadPath) {
        if(imageFile == null ){
            return new BaseResult(false, "imageFile不能为空");
        }

        if (imageFile.getSize() >= 10*1024*1024)
        {
            return new BaseResult(false, "文件不能大于10M");
        }
        String uuid = UUID.randomUUID().toString();

        String fileDirectory = CommonDateUtils.date2string(new Date(), CommonDateUtils.YYYY_MM_DD);

        //拼接后台文件名称
        String pathName = fileDirectory + File.separator + uuid + "."
                + FilenameUtils.getExtension(imageFile.getOriginalFilename());
        //构建保存文件路劲
        //2016-5-6 yangkang 修改上传路径为服务器上
        String realPath = request.getServletContext().getRealPath("uploadPath");
        //获取服务器绝对路径 linux 服务器地址  获取当前使用的配置文件配置
        //String urlString=PropertiesUtil.getInstance().getSysPro("uploadPath");
        //拼接文件路劲
        String filePathName = realPath + File.separator + pathName;
        log.info("图片上传路径："+filePathName);
        //判断文件保存是否存在
        File file = new File(filePathName);
        if (file.getParentFile() != null || !file.getParentFile().isDirectory()) {
            //创建文件
            file.getParentFile().mkdirs();
        }

        InputStream inputStream = null;
        FileOutputStream fileOutputStream = null;
        try {
            inputStream = imageFile.getInputStream();
            fileOutputStream = new FileOutputStream(file);
            //写出文件
            //2016-05-12 yangkang 改为增加缓存
//            IOUtils.copy(inputStream, fileOutputStream);
            byte[] buffer = new byte[2048];
            IOUtils.copyLarge(inputStream, fileOutputStream, buffer);
            buffer = null;

        } catch (IOException e) {
            filePathName = null;
            return new BaseResult(false, "操作失败", e.getMessage());
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
                if (fileOutputStream != null) {
                    fileOutputStream.flush();
                    fileOutputStream.close();
                }
            } catch (IOException e) {
                filePathName = null;
                return new BaseResult(false, "操作失败", e.getMessage());
            }
        }


        //        String fileId = FastDFSClient.uploadFile(file, filePathName);

        *//**
         * 缩略图begin
         *//*

        //拼接后台文件名称
        String thumbnailPathName = fileDirectory + File.separator + uuid + "small."
                + FilenameUtils.getExtension(imageFile.getOriginalFilename());
        //added by yangkang 2016-3-30 去掉后缀中包含的.png字符串
        if(thumbnailPathName.contains(".png")){
            thumbnailPathName = thumbnailPathName.replace(".png", ".jpg");
        }
        long size = imageFile.getSize();
        double scale = 1.0d ;
        if(size >= 200*1024){
            if(size > 0){
                scale = (200*1024f) / size  ;
            }
        }


        //拼接文件路劲
        String thumbnailFilePathName = realPath + File.separator + thumbnailPathName;
        try {
            //added by chenshun 2016-3-22 注释掉之前长宽的方式，改用大小
//            Thumbnails.of(filePathName).size(width, height).toFile(thumbnailFilePathName);
            if(size < 200*1024){
                Thumbnails.of(filePathName).scale(1f).outputFormat("jpg").toFile(thumbnailFilePathName);
            }else{
                Thumbnails.of(filePathName).scale(1f).outputQuality(scale).outputFormat("jpg").toFile(thumbnailFilePathName);
            }

        } catch (Exception e1) {
            return new BaseResult(false, "操作失败", e1.getMessage());
        }
        *//**
         * 缩略图end
         *//*

        Map<String, Object> map = new HashMap<String, Object>();
        //原图地址
        map.put("originalUrl", pathName);
        //缩略图地址
        map.put("thumbnailUrl", thumbnailPathName);
        return new BaseResult(true, "操作成功", map);
    }*/

}
