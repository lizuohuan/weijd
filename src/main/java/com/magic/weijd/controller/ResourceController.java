package com.magic.weijd.controller;

import com.magic.weijd.util.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.*;


/**
 * Created by flyong86 on 2016/5/6.
 */
@Controller
@RequestMapping("/res")
public class ResourceController extends BaseController {

    @RequestMapping("/upload")
    @ResponseBody
    public ViewData upload(@RequestParam(value = "type" ,defaultValue = "other") String type, HttpServletRequest request,
                           HttpServletResponse response){
        Calendar ca = Calendar.getInstance(); 
        if (request instanceof MultipartHttpServletRequest) {
            String url = "";
            MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) request;
            Map<String, MultipartFile> multipartFileMap = multipartHttpServletRequest.getFileMap();
            if (multipartFileMap != null) {
                for (Map.Entry<String, MultipartFile> entry : multipartFileMap.entrySet()) {
                    MultipartFile multipartFile = entry.getValue();
//                    String filename = multipartFile.getOriginalFilename();
                    String filePath = "upload/" + ca.get(Calendar.YEAR) + "/" + ca.get(Calendar.MONTH) + "/" + ca.get(Calendar.DAY_OF_MONTH) + "/";
                    String resName = FileUpload.fileUp(multipartFile, filePath, CommonUtil.get32UUID());

                    StringBuffer picURL = new StringBuffer();
                    picURL.append(filePath + resName);
                    url = picURL.toString();
//					if (null != type && type.trim().length() > 0 && "1".equals(type)) {
//						String path = request.getSession().getServletContext().getRealPath("/");
//						String iconPath = path + "/" + filePath + "/icon";
//						File file = new File(iconPath);
//						if (!file.isDirectory()) {
//							file.mkdir();
//						}
//						//压缩图片
////						ImgCompress.reduceImg(path+"/"+url,iconPath+"/"+resName,32,32,null);
//					}

                }
            }
            Map<String,Object> data = new HashMap<String, Object>();
            data.put("url", url);
            return buildSuccessJson(StatusConstant.SUCCESS_CODE,"上传成功", data);
        }
            return buildFailureJson(ViewData.FlagEnum.ERROR, 202,"上传失败");

    }



    @RequestMapping("/delete")
    @ResponseBody
    public ViewData delete(String url){
        try {
            FileUpload.delete(url);
            return buildFailureJson(ViewData.FlagEnum.NORMAL, 200,"删除成功");
        } catch (Exception e) {
            e.printStackTrace();
            return buildFailureJson(ViewData.FlagEnum.ERROR, 202,"删除失败");
        }

    }

//    /**
//     * layer 富文本上传使用
//     * @param type
//     * @param request
//     * @return
//     */
//    @RequestMapping("/upload2")
//    @ResponseBody
//    public ViewData upload2(@RequestParam(value = "type" ,defaultValue = "other") String type, HttpServletRequest request){
//
//        Calendar ca = Calendar.getInstance();
//        if (request instanceof MultipartHttpServletRequest) {
//            String url = "";
//            MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) request;
//            Map<String, MultipartFile> multipartFileMap = multipartHttpServletRequest.getFileMap();
//            if (multipartFileMap != null) {
//                for (Map.Entry<String, MultipartFile> entry : multipartFileMap.entrySet()) {
//                    MultipartFile multipartFile = entry.getValue();
////                    String filename = multipartFile.getOriginalFilename();
//                    String filePath = "upload/" + ca.get(Calendar.YEAR) + "/" + ca.get(Calendar.MONTH) + "/" + ca.get(Calendar.DAY_OF_MONTH) + "/";
//                    String resName = FileUpload.fileUp(multipartFile, filePath, CommonUtil.get32UUID());
//
//                    StringBuffer picURL = new StringBuffer();
//                    picURL.append(filePath + resName);
//                    url = picURL.toString();
//                    if (null != type && type.trim().length() > 0 && "1".equals(type)) {
//                        String path = request.getSession().getServletContext().getRealPath("/");
//                        String iconPath = path + "/" + filePath + "/icon";
//                        File file = new File(iconPath);
//                        if (!file.isDirectory()) {
//                            file.mkdir();
//                        }
//                        //压缩图片
////						ImgCompress.reduceImg(path+"/"+url,iconPath+"/"+resName,32,32,null);
//                    }
//                }
//            }
//            Map<String,Object> data = new HashMap<String, Object>();
//            String s = multipartHttpServletRequest.getScheme() + "://";
//            s+=request.getHeader("host");
//            s+=multipartHttpServletRequest.getContextPath();
//            data.put("src",s + "/" + url);
//            return buildSuccessJson(0,"上传成功", data);
//        }
//        return buildFailureJson(ViewData.FlagEnum.ERROR, 202,"上传失败");
//
//    }


//
//    @RequestMapping("/uploadBase64")
//    @ResponseBody
//    public ViewData uploadBase64(String base64,HttpServletRequest request) throws IOException {
//        if (null == base64 || "".equals(base64.trim())) {
//            return buildFailureJson(StatusConstant.FIELD_NOT_NULL,"字段不能为空");
//        }
//        try {
//            ServletContext servletContext = request.getSession().getServletContext();
//            Calendar ca = Calendar.getInstance();
//            String url;
//            String filePath = "upload/" + ca.get(Calendar.YEAR) + "/" + ca.get(Calendar.MONTH) + "/" + ca.get(Calendar.DAY_OF_MONTH) + "/";
//            String resName = ImageBase64Utils.saveBase64ImageStringToImage(servletContext.getRealPath("/" + filePath),CommonUtil.get32UUID(),base64);
//            StringBuffer picURL = new StringBuffer();
//            picURL.append(filePath + resName);
//            url = picURL.toString();
//            Map<String,Object> data = new HashMap<String, Object>();
//            data.put("url", url);
//            return buildSuccessJson(StatusConstant.SUCCESS_CODE,"上传成功", data);
//        } catch (IOException e) {
//            e.printStackTrace();
//            return buildFailureJson(ViewData.FlagEnum.ERROR, 202,"上传失败");
//        }
//
//    }


    /**
     * 多上传
     * @param type
     * @param request
     * @return
     */
    @RequestMapping("/upload3")
    @ResponseBody
    public ViewData upload3(@RequestParam(value = "type" ,defaultValue = "other") String type, HttpServletRequest request){

        Calendar ca = Calendar.getInstance();
        if (request instanceof MultipartHttpServletRequest) {
            String url = null;

            MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) request;
            //存放上传文件的集合
            List<MultipartFile> files = new ArrayList<MultipartFile>();
            Map<String, MultipartFile> multipartFileMap = multipartHttpServletRequest.getFileMap();
            if (multipartFileMap != null) {
                for (Map.Entry<String, MultipartFile> entry : multipartFileMap.entrySet()) {
                    files.addAll(multipartHttpServletRequest.getFiles(entry.getKey()));
                }
            }
            if (files.size() > 0) {
                for (MultipartFile multipartFile : files) {
                    String filePath = "upload/" + ca.get(Calendar.YEAR) + "/" + ca.get(Calendar.MONTH) + "/" + ca.get(Calendar.DAY_OF_MONTH) + "/";
                    String resName = FileUpload.fileUp(multipartFile, filePath, CommonUtil.get32UUID());
                    StringBuffer picURL = new StringBuffer();
                    picURL.append(filePath);
                    picURL.append(resName);
                    if (null != url) {
                        url += "," + picURL.toString();
                    } else {
                        url = picURL.toString();
                    }
                    //处理ios上传图片旋转
                    int angle = RotateImage.getRotateAngleForPhoto(request.getSession().getServletContext().getRealPath("/"+url));
                    RotateImage.rotatePhonePhoto(request.getSession().getServletContext().getRealPath("/"+url), angle);
                }

            }
            Map<String,Object> data = new HashMap<String, Object>();
            data.put("url", url);
            return buildSuccessJson(StatusConstant.SUCCESS_CODE,"上传成功", data);
        }
        return buildFailureJson(ViewData.FlagEnum.ERROR, 202,"上传失败");

    }


 /*   public static void main(String[] args) {

        File file = new File("E:\\lzhWorkspaces\\AIMAI\\images\\target\\images\\upload");
        File[] tempList = file.listFiles();
        if (file.exists()) {
            List<String> list = new ArrayList<String>();
            packagingFilePath(tempList,list);
            for (String s : list) {
                System.out.println(s );
            }

        }
    }


    public static void packagingFilePath(File[] tempList,List<String> list) {
        for (File file : tempList) {
            if (null != file.listFiles()) {
                packagingFilePath(file.listFiles(),list);
            } else {
                String path = file.toString();
                if (path.contains("upload")) {
                    list.add("upload" + path.split("upload")[1]);
                }
            }
        }
    }*/
}
