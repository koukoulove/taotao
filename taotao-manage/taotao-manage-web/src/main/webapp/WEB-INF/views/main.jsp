<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>淘淘商城后台管理系统</title>
<jsp:include page="/commons/common-js.jsp"></jsp:include>
</head>
<body class="easyui-layout">

	<div data-options="region:'north',collapsed:false" style="height:100px"  >
	</div> 
	
	<div data-options="region:'center'" > 
	
		<div class="easyui-layout" data-options="fit:true" > 
		
			<div data-options="region:'west',collapse:true,title:'菜单',split:true" style="width:180px" >
				<ul id="menu" class="easyui-tree" style="margin-top: 10px;margin-left: 5px;">
		         	<li>
		         		<span>商品管理</span>
		         		<ul>
			         		<li data-options="attributes:{'url':'/rest/page/item-add'}">新增商品</li>
			         		<li data-options="attributes:{'url':'/rest/page/item-list'}">查询商品</li>
			         		<li data-options="attributes:{'url':'/rest/page/item-param-list'}">规格参数</li>
			         	</ul>
		         	</li>
		         	<li>
		         		<span>网站内容管理</span>
		         		<ul>
			         		<li data-options="attributes:{'url':'/rest/page/content-category'}">内容分类管理</li>
			         		<li data-options="attributes:{'url':'/rest/page/content'}">内容管理</li>
			         	</ul>
		         	</li>
		         </ul>				
			</div> 
			
			<div data-options="region:'center'" >
				<div id="tabs" class="easyui-tabs">
				    <div title="首页" style="padding:20px;">
				        	Welcome to TAOTAO！
				    </div>
				</div>
			</div> 
		
		</div> 
	
		<div data-options="region:'south',title:'South Title'" style="height:100px;"></div> 

	</div> 

	<div data-options="region:'south',collapsed:false" style="height:33px;"  >
	</div> 
	
	
<script type="text/javascript">
$(function(){
	$('#menu').tree({
	  	onClick: function(node){
	  		if($('#menu').tree("isLeaf",node.target)){//如果是叶子节点
	  			var tabs = $("#tabs");
	  			var tab = tabs.tabs("getTab",node.text);
	  			if(tab){
	  				//已存在，则选中
	  				tabs.tabs("select",node.text);
	  			}else{
	  				//打开新的tab
	  				tabs.tabs('add',{ 
	  					title: node.text, 
	  					href: node.attributes.url, 
	  					closable:true
	  				}); 
	  			}
	  		}
	  	}
	  });
});
</script>

</body>
</html>