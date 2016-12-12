<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	
<form id="itemeParamEditForm" method="post">
	<table cellpadding="5" style="margin-left: 30px" id="itemParamEditTable" class="itemParam">
		<tr>
			<td>商品类目:</td>
			<td>
				<!-- <a href="javascript:void(0)" class="easyui-linkbutton selectItemCat">选择类目</a>  -->
				<input id="id" type="hidden" name="id"/>
				<input id="paramData" type="hidden" name="paramData"/>
				<input id="itemCatId" type="hidden" name="itemCatId"/>
				<input type="text" name="itemCatName" readonly="readonly" style="background:transparent;border:0"/>
			</td>
		</tr>
		<tr class="hide addGroupTr">
			<td>规格参数:</td>
			<td>
				<ul>
					<li>
					<a href="javascript:void(0)" class="easyui-linkbutton addGroup">添加分组</a>
					</li>
				</ul>
			</td>
		</tr>
		<tr>
			<td></td>
			<td>
				<a href="javascript:void(0)" class="easyui-linkbutton submit">提交</a>
		    	<a href="javascript:void(0)" class="easyui-linkbutton close">关闭</a>
			</td>
		</tr>
	</table>
	<div  class="itemParamAddTemplate" style="display: none;">
		<li class="param">
			<ul>
				<li>
					<input class="easyui-textbox" style="width: 150px;" name="group"/>
					<a href="javascript:void(0)" class="easyui-linkbutton addParam" title="添加参数" data-options="plain:true,iconCls:'icon-add'"></a>
					<a href="javascript:void(0)" class="easyui-linkbutton delParamGroup" title="删除" data-options="plain:true,iconCls:'icon-cancel'"></a>		
				</li>
				<li>
					<span>|-------</span>
					<input style="width: 150px;" class="easyui-textbox" name="param"/>&nbsp;
					<a href="javascript:void(0)" class="easyui-linkbutton delParam" title="删除" data-options="plain:true,iconCls:'icon-cancel'"></a>						
				</li>
			</ul>
		</li>
	</div>
</form>
<script style="text/javascript">

	//添加分组
	function addGroup(data){
		var temple = $(".itemParamAddTemplate li").eq(0).clone();
		 $(data).parent().parent().append(temple);
	}
	//删除分组
	function delParamGroup(data){
		$(data).parent().parent().parent().remove();
	}
	//添加参数
	function addParam(data){
		var li = $(".itemParamAddTemplate li").eq(2).clone();
		$(data).parent().parent().append(li);
	}
	//删除参数
	function delParam(data){
		$(data).parent().remove();
	}


	$(function(){
		$(".addGroupTr").show();
		
		$(".addGroup").click(function(){
			  var temple = $(".itemParamAddTemplate li").eq(0).clone();
			  
			  $(this).parent().parent().append(temple);
			  
			  temple.find(".addParam").click(function(){
				  
				  var li = $(".itemParamAddTemplate li").eq(2).clone();
				  //复制的li增加删除方法
				  li.find(".delParam").click(function(){
					  $(this).parent().remove();
				  }); 
				  
				  li.appendTo($(this).parentsUntil("ul").parent());
			  });
			  
			  temple.find(".delParam").click(function(){
				  $(this).parent().remove();
			  });
			  
			  temple.find(".delParamGroup").click(function(){
				  $(this).parent().parent().parent().remove();
			  });
			  
		 });
		
		//关闭
		$("#itemParamEditTable .close").click(function(){
			$(".panel-tool-close").click();
		});
		
		//提交
		$("#itemParamEditTable .submit").click(function(){
			var params = [];
			var groups = $("#itemParamEditTable [name=group]");//规格数组
			groups.each(function(i,e){
				var p = $(e).parentsUntil("ul").parent().find("[name=param]");//参数数组
				var _ps = [];
				p.each(function(_i,_e){
					var _val = $(_e).siblings("input").val();//一个参数值
					if($.trim(_val).length>0){
						_ps.push(_val);						
					}
				});
				var _val = $(e).siblings("input").val();//一个规格值
				if($.trim(_val).length>0 && _ps.length > 0){
					params.push({
						"group":_val,
						"params":_ps
					});					
				}
			});
			
			var url = "/rest/item/param/"+$("#id").val();
			var paramData = JSON.stringify(params);
			$.ajax({
				type: "PUT",
				url: url,
				data: {"paramData":paramData},
				success: function(data){
					$.messager.alert('提示','操作成功!',undefined,function(){
						//$(".panel-tool-close").click();
						$("#itemParamEditTable .close").click();
	   					$("#itemParamList").datagrid("reload");
					});
				},
				error: function(data){
					if(data.status=='400'){
						$.messager.alert('提示','操作失败，'+JSON.parse(data.responseText).msg);
					}else{
						$.messager.alert('提示','系统异常!');
					}
				} 
			});
		});
	});
	
</script>