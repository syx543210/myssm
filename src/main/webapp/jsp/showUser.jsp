<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-1.8.3.js"></script>
<title>Insert title here</title>
</head>
<body>
	<c:forEach items="${userList}" var="user">
		id:<p>${user.id}</p>姓名:<p>${user.name}</p>电话:<p>${user.phone}</p>地址:<p>${user.address}</p>
	</c:forEach>
	
	<button onclick="exportme()">点我</button>>
	<form action="${pageContext.request.contextPath}/user/upload" method="post" enctype="multipart/form-data">
	<!-- <input type="file" accept="image/jpeg" name="imgs" required="required"/>
	<input type="file" accept="image/jpeg" name="imgs" required="required"/> -->
	<!-- <input type="submit" value="提交"/> -->
	</form>
	
	<input type="file" id="imgs" name="duoimgs" accept="image/jpeg,image/png" multiple/>
	<input type="button" value="提交文件" onclick="mutiFiles()"/>
	
	<input type="button" value="提交文件" onclick="mutiFiles()"/>
	<input type="button" value="点击获取验证码" onclick="countdown()" id="abc"/>
</body>
<script type="text/javascript">
	function exportme() {
		window.location.href="${pageContext.request.contextPath}/user/export";
	}
	
	/*  多文件上传(多选) */
    function mutiFiles(){
      var form = new FormData();
      var fileObj = $("#imgs")[0].files;
      var length = fileObj.length;
      //将fileObj转换成数组
      //var filese = Array.from(fileObj);
      alert(length);
      for(var i = 0;i < length;i++){
          form.append("doc",fileObj[i]);
      }
      $.ajax({
          type:"post",
     	  dataType:"text",
          data:form,
          url:"${pageContext.request.contextPath}/user/upload1",
          contentType: false, //必须false才会自动加上正确的Content-Type
          /*
              必须false才会避开jQuery对 formdata 的默认处理
             XMLHttpRequest会对 formdata 进行正确的处理
          */
          processData: false,
          success:function(data){
        	  alert(data);
              //清除文件表单
              $("#imgs").val("");
          },
          error:function(data){
        	  alert("请求失败");
          }
      });
    }
	
	
	function countdown() {
		$("#abc").attr("disabled","disabled");
		var a=59;
		var timer=window.setInterval(function () {
			if(a>0){
				$("#abc").val(a+"秒后重新获取");
				a--;
			}else{
				window.clearInterval(timer);
				$("#abc").val("点击获取验证码");
				$("#abc").removeAttr("disabled");
			}
		}, 1000);
	}
</script>
</html>