<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" isErrorPage="true"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>ERROR</title>
</head>
<style type="text/css">
	.errorWrap{position:absolute;top:50%;left:50%;width:896px;height:197px;margin-top:-122px;margin-left:-448px;padding-top:47px}
	.errorWrap .contents{position:relative;padding:20px;border:solid 1px #ebebeb;background-color:#fdfdfd;}
	.errorWrap .contents h1{margin:0;padding:0;height:25px;font-size:12px;font-family:dotum;color:#000}
	.errorWrap .contents p{margin:0;padding:0;color:#333;font-size:12px;font-family:dotum;line-height:130%}
	.errorWrap .contents p.help{position:absolute;right:0;top:19px;width:121px;height:39px;border-left:solid 1px #e5e5e5;padding:18px 0px 0px 15px;}
	.errorWrap .contents p.help a{display:block;width:83px;height:18px;padding:4px 0px 0px 11px;border:solid 1px #d5d5d5;text-decoration:none;color:#333}
	.errorWrap .contents p.help a:hover{color:#c40452}
	.errorWrap .btn{padding-top:30px;text-align:center;}
	.errorWrap .btn a{display:inline-block;width:176px;height:22px;padding-top:10px;text-align:center;font-size:12px;font-family:dotum;text-decoration:none;border:solid 1px #d5d5d5;color:#333}
	.errorWrap .btn a.home{background-color:#c40452;border:solid 1px #c40452;color:#fff}
</style>
<body>
	<div class="errorWrap">
		<div class="contents">
			<h1>이용에 불편을 드려 죄송합니다.</h1>
			<p>요청하신 웹 페이지를 찾을 수 없습니다. 찾으시는 웹 페이지가 현재 사용할 수 없거나 웹 페이지의 이름이 변경 또는 삭제 되었습니다. <br /></p>
		</div>
		<div class="btn">
			<a href="#back" onclick="history.back(-1);">뒤로 가기 </a>
		</div>
	</div>
</body>
</html>
