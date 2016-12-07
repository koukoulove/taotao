<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<table cellpadding="5" style="margin-left: 30px" id="itemParamAddTable" class="itemParam">
	<tr>
		<td>商品类目:</td>
		<td>
			<a href="javascript:void(0)" class="easyui-linkbutton selectItemCat">选择类目</a> 
			<input type="hidden" name="cid" style="width: 280px;"></input>
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
				<input class="easyui-textbox" style="width: 150px;" name="group"/>&nbsp;
				<a href="javascript:void(0)" class="easyui-linkbutton addParam"  title="添加参数" data-options="plain:true,iconCls:'icon-add'"></a>
				<a href="javascript:void(0)" class="easyui-linkbutton delParamGroup" title="删除" data-options="plain:true,iconCls:'icon-cancel'"></a>		
			</li>
			<li>
				<span>|-------</span>
				<input  style="width: 150px;" class="easyui-textbox" name="param"/>&nbsp;
				<a href="javascript:void(0)" class="easyui-linkbutton delParam" title="删除" data-options="plain:true,iconCls:'icon-cancel'"></a>						
			</li>
		</ul>
	</li>
</div>
<script style="text/javascript">
	$(function(){
		TAOTAO.initItemCat({
			fun:function(node){
				$(".addGroupTr").hide().find(".param").remove();
				//  判断选择的目录是否已经添加过规格
			  $.ajax({
				   type: "GET",
				   url: "/rest/item/param/" + node.id,
				   success: function(data){
					   if(data){
						  $.messager.alert("提示", "该类目已经添加，请选择其他类目。", undefined, function(){
							$(".selectItemCat").each(function(i,e){
    							var _ele = $(e);
    							_ele.parent().find("[name=cid]").val("");
			    				_ele.next().remove();
    						});
							$("#itemParamAddTable .selectItemCat").click();
						  });
						  return ;
					  }else{
						  $(".addGroupTr").show();
					  }
				   },
				   error: function(data){
					   if(data.status==400){
						   $.messager.alert("提示", "操作失败，"+ JSON.parse(data.responseText).msg,"error");  
					   }else{
						   $.messager.alert("提示", "系统异常！","error");  
					   }
				   }
				});
			}
		});
		
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
		
		$("#itemParamAddTable .close").click(function(){
			$(".panel-tool-close").click();
		});
		
		$("#itemParamAddTable .submit").click(function(){
			var params = [];
			var groups = $("#itemParamAddTable [name=group]");//规格数组
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
			var cid = $("#itemParamAddTable [name=cid]").val();
			if(''==cid){
				$.messager.alert('提示','请选择类目!','info');
				return ;
			}
			var url = "/rest/item/param/"+$("#itemParamAddTable [name=cid]").val();
/* 			$.post(url,{"paramData":JSON.stringify(params)},function(data){
				$.messager.alert('提示','新增商品规格成功!',undefined,function(){
					$(".panel-tool-close").click();
   					$("#itemParamList").datagrid("reload");
   				});
			}); */
			var paramData = JSON.stringify(params);
			$.ajax({
				type: "POST",
				url: url,
				data: {"paramData":paramData},
				success: function(data){
					$.messager.alert('提示','操作成功!',undefined,function(){
						//$(".panel-tool-close").click();
						$("#itemParamAddTable .close").click();
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