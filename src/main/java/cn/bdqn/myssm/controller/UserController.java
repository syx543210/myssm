package cn.bdqn.myssm.controller;

import java.io.File;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import cn.bdqn.myssm.entity.User;
import cn.bdqn.myssm.service.UserService;
import cn.bdqn.myssm.utils.ExportExcelSeedBack;

/**
 * @description
 * @author 盛毅欣
 * @address 北大青鸟沈阳三好中心
 * @created 2018年4月11日 上午11:38:28
 * @version 1.0.0
 */
@Controller
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	/*@RequestMapping("/selectUser")
	@ResponseBody
	public List<User> userAllSelect(){
		System.out.println(userService.userAllSelect().size());
		return userService.userAllSelect();
	}*/
	@RequestMapping("/selectUser")
	public String userAllSelect(Model model){
		System.out.println(userService.userAllSelect().size());
		List<User> userList = userService.userAllSelect();
		model.addAttribute("userList", userList);
		return "showUser";
	}
	
	  @RequestMapping("/export")
	     public void export(HttpServletResponse response){
	        List<User> userList =userService.userAllSelect();
	        if (userList != null && userList.size()>0) {//查询的数据不为空就对数据进行导出
	            //导出文件的标题
	            String title = "用户"+".xls";
	            //设置表格标题行
	            String[] headers = new String[] {"id","姓名", "电话","地址"};
	            List<Object[]> dataList = new ArrayList<Object[]>();
	            Object[] objs = null;
	            int i=1;
	            for (User user : userList) {//循环每一条数据
	                objs = new Object[headers.length];
	                objs[0] = user.getId();//序号
	                objs[1] = user.getName();//姓名
	                objs[2] = user.getPhone();
	                /*if(user.getStatus()==1){
	                    objs[3]="正常";
	                }else {
	                    objs[3]="停用";
	                }*/
	                objs[3] = user.getAddress();
	                //数据添加到excel表格
	                dataList.add(objs);
	                i++;
	            }
	            //使用流将数据导出
	            OutputStream out = null;
	            try {
	                //防止中文乱码
	                String headStr = "attachment; filename=\"" + new String( title.getBytes("gb2312"), "ISO8859-1" ) + "\"";
	                response.setContentType("octets/stream");
	                response.setContentType("APPLICATION/OCTET-STREAM");
	                response.setHeader("Content-Disposition", headStr);
	                out = response.getOutputStream();
	                //ExportExcel ex = new ExportExcel(title, headers, dataList);//有标题
	                ExportExcelSeedBack ex = new ExportExcelSeedBack(title, headers, dataList);//没有标题
	                ex.export(out);
	            } catch (Exception e) {
	                e.printStackTrace();
	            }
	        }
	    }
	  
	  @RequestMapping("/upload")
		public String upload(@RequestParam MultipartFile[] imgs,
				HttpServletRequest request) throws Exception{
			for(MultipartFile file : imgs){
				if(file.isEmpty()){
					System.out.println("文件未上传...");
				}else {
					try {
						//得到服务器项目发布运行所在地址  
						String uploadPath = request.getSession().getServletContext()
								.getRealPath("/upload");
						System.out.println(uploadPath);
						//得到上传的文件名
						String fileName = file.getOriginalFilename();
						String fileEx = fileName.substring(fileName.indexOf("."),fileName.length());
						String filePath = System.currentTimeMillis() + fileEx;
						String pathName = uploadPath + File.separator + filePath;
						//把文件上传至path的路径  
						File localFile = new File(pathName);  
						FileUtils.copyInputStreamToFile(file.getInputStream(),localFile);
					} catch (Exception e) {
						e.printStackTrace();
					} 
				}
			}
			return "redirect:/list.jsp";
		}
	  
	  @ResponseBody
	  @RequestMapping("/upload1")
		public String upload1(@RequestParam MultipartFile[] doc,
				HttpServletRequest request) throws Exception{
			for(MultipartFile file : doc){
				if(file.isEmpty()){
					System.out.println("文件未上传...");
				}else {
					try {
						//得到服务器项目发布运行所在地址  
						String uploadPath = request.getSession().getServletContext()
								.getRealPath("/upload");
						System.out.println(uploadPath);
						//得到上传的文件名
						String fileName = file.getOriginalFilename();
						String fileEx = fileName.substring(fileName.indexOf("."),fileName.length());
						String filePath = System.currentTimeMillis() + fileEx;
						String pathName = uploadPath + File.separator + filePath;
						//把文件上传至path的路径  
						File localFile = new File(pathName);  
						FileUtils.copyInputStreamToFile(file.getInputStream(),localFile);
					} catch (Exception e) {
						e.printStackTrace();
					} 
				}
			}
			return "666";
		}
	
}
