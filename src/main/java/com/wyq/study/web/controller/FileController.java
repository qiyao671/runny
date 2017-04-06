package com.wyq.study.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 模块名称：study
 * 功能说明：<br>
 * 功能描述：<br>
 * 开发人员：wangyiqiang
 * 创建时间： 2017-03-24 上午8:35
 * 系统版本：1.0.0
 **/
@Controller
@RequestMapping("/file")
public class FileController extends BaseController {

//    @RequestMapping(value = "/upload", method = {RequestMethod.GET, RequestMethod.POST})
//    @ResponseBody
//    public String uploadFile(@RequestParam(value = "file", required = false) MultipartFile file) {
//        DiskFileItemFactory factory = new DiskFileItemFactory();
//        // 设置缓冲区大小和临时目录
//        factory.setSizeThreshold(1024 * 1024 * 8);// 8M 临时缓冲区（上传文件不大于8M不会产生临时文件）
//        //创建文件上传核心类对象
//        ServletFileUpload fileUpload = new ServletFileUpload(factory);
//        //设置单个文件最大30M
//        fileUpload.setFileSizeMax(30 * 1024 * 1024);
//        //设置总文件大小：50M
//        fileUpload.setSizeMax(50 * 1024 * 1024);
//
//        //判断，当前表单是否为文件上传表单
//        if (fileUpload.isMultipartContent(request)) {
//
//            try {
//                //3.把请求数据转换为FileItem对象的集合
//                List<FileItem> list = fileUpload.parseRequest(request);
//                for (FileItem item : list) {
//                    //判断：是普通表单，还是文件上传表单
//                    if (item.isFormField()) {
//                        String filedName = item.getFieldName(); //获取元素名称
//                        String value = item.getString("utf-8"); //获取元素值
//                        System.out.println(filedName + " : " + value);
//                    } else {
//                        //文件上传表单
//
//                        String name = item.getName(); //上传的文件名称
//
//                        /**
//                         * 【四、文件重名】
//                         * 对于不同的用户的test.txt文件，不希望覆盖，
//                         * 后台处理：给用户添加一个唯一标记！
//                         */
//                        //a.随机生成一个唯一标记
//                        String id = UUID.randomUUID().toString();
//                        //与文件名拼接
//                        name = id + name;
//
//
//                        //【三、上传到指定目录：获取上传目录路径】
//                        String realPath = request.getServletContext().getRealPath("/upload");
//                        //创建文件对象
//                        File fileobj = new File(realPath, name);
//                        item.write(fileobj);
//                        item.delete();
//                    }
//                }
//            } catch (FileUploadException e) {
//                e.printStackTrace();
//            } catch (UnsupportedEncodingException e) {
//                e.printStackTrace();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//
//        } else {
//            System.out.println("不处理！");
//        }
//        return null;
//    }

//    @RequestMapping(value = "/upload", method = {RequestMethod.GET, RequestMethod.POST})
//    @ResponseBody
//    public Callback uploadFile(HttpServletRequest request) throws IOException {
//        long startTime = System.currentTimeMillis();
//        //将当前上下文初始化给  CommonsMutipartResolver （多部分解析器）
//        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
//        //检查form中是否有enctype="multipart/form-data"
//        if (multipartResolver.isMultipart(request)) {
//            //将request变成多部分request
//            MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
//            //获取multiRequest 中所有的文件名
//            Iterator iterator = multiRequest.getFileNames();
//
//            while (iterator.hasNext()) {
//                //一次遍历所有文件
//                MultipartFile file = multiRequest.getFile(iterator.next().toString());
//                if (file != null) {
//                    String path = "/Users/wangyiqiang/Downloads/picPath/" + file.getOriginalFilename();
//                    //上传
//                    file.transferTo(new File(path));
//                }
//            }
//        }
//
//        return returnCallback(true, "成功", null);
//
//    }
//    @RequestMapping(value = "/upload", method = {RequestMethod.GET, RequestMethod.POST})
//    @ResponseBody
//    public Callback uploadFile(HttpServletRequest request, String fload) throws IOException {
//        Date currData = new Date();
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//        String classPath = this.getClass().getClassLoader().getResource("").getPath();
//        String projectPath = classPath.substring(0, classPath.length() - "/WEB-INF/classes/".length());
//        String projectName = projectPath.substring(projectPath.lastIndexOf("/") + 1);
//        String filePath = projectPath + fload + "/" + sdf.format(currData);  //文件夹存放路径
//        String relativePath = "/" + projectName + fload + "/" + sdf.format(currData); //文件夹存放相对路径
//        String fileAllPath = null;     //文件存放路径
//        String relativeAllPath = null;   //文件存放相对路径
//
//        System.out.println("文件存放的文件夹为----:" + filePath);
//        System.out.println("文件存放的文件夹相对路径为----:" + relativePath);
//
//        File saveFolder = new File(filePath);
//        if (!saveFolder.isDirectory()) {
//            boolean falg = saveFolder.mkdirs();
//            System.out.println("文件夹创建状态-----:" + falg);
//        }
//
//        String realFileName = null;     //真实上传文件名
//        String fileType = null;         //文件类型
//        List<Map<String, String>> result = new ArrayList<Map<String, String>>();
//
//        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
//        Iterator<String> fileNameIte = multipartRequest.getFileNames();
//        while (fileNameIte.hasNext()) {
//            String fileName = fileNameIte.next();
//            MultipartFile mr = multipartRequest.getFile(fileName);
//
//            realFileName = mr.getOriginalFilename();
//            if (StringUtils.isNotBlank(realFileName)) {
//
//                System.out.println("上传的文件名为-----:" + realFileName);
//                fileType = realFileName.substring(realFileName.lastIndexOf(".") + 1);
//                realFileName = realFileName.substring(0, realFileName.lastIndexOf(".")) + "-" + System.nanoTime() + "." + fileType;  //对上传文件进行重命名
//                fileAllPath = filePath + "/" + realFileName;
//                relativeAllPath = relativePath + "/" + realFileName;
//
//                File localFile = new File(fileAllPath);
//                mr.transferTo(localFile);
//
//                Map<String, String> map = new HashMap<String, String>();
//                map.put("fileName", realFileName);
//                map.put("fileType", fileType);
//                map.put("fileAllPath", fileAllPath);
//                map.put("relativeAllPath", relativeAllPath);
//
//                result.add(map);
//            }
//        }
//
//        return returnCallback(true, result, null);
//
//    }

}
