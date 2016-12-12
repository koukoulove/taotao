<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<table class="easyui-datagrid" id="itemParamList" title="商品规格参数列表" 
       data-options="singleSelect:false,collapsible:true,pagination:true,url:'/rest/item/param',method:'get',
       pageSize:30,toolbar:itemParamListToolbar,onLoadError:onloadDataGridError">
    <thead>
        <tr>
        	<th data-options="field:'ck',checkbox:true"></th>
        	<th data-options="field:'id',width:60">ID</th>
        	<th data-options="field:'itemCatId',width:80">商品类目ID</th>
        	<th data-options="field:'itemCatName',width:100">商品类目</th>
            <th data-options="field:'paramData',width:300,formatter:formatItemParamData">规格(只显示分组名称)</th>
            <th data-options="field:'created',width:130,align:'center',formatter:TAOTAO.formatDateTime">创建日期</th>
            <th data-options="field:'updated',width:130,align:'center',formatter:TAOTAO.formatDateTime">更新日期</th>
        </tr>
    </thead>
</table>
<div id="itemParamEditWindow" class="easyui-window" title="编辑商品规格参数" data-options="modal:true,closed:true,iconCls:'icon-save',href:'/rest/page/item-param-edit'" style="width:80%;height:80%;padding:10px;">
</div>
<script>

	function formatItemParamData(value , index){
		if(''!=value){
			var json = JSON.parse(value);
			var array = [];
			$.each(json,function(i,e){
				array.push(e.group);
			});
			return array.join(",");
		}else{
			return '';
		}
		
	}

    function getSelectionsIds(){
    	var itemList = $("#itemParamList");
    	var sels = itemList.datagrid("getSelections");
    	var ids = [];
    	for(var i in sels){
    		ids.push(sels[i].id);
    	}
    	ids = ids.join(",");
    	return ids;
    }
    
    var itemParamListToolbar = [{
        text:'新增',
        iconCls:'icon-add',
        handler:function(){
        	TAOTAO.createWindow({
        		url : "/rest/page/item-param-add",
        	});
        }
    },{
        text:'编辑',
        iconCls:'icon-edit',
        handler:function(){
        	
        	var ids = getSelectionsIds();
        	if(ids.length == 0){
        		$.messager.alert('提示','必须选择一条记录才能编辑!');
        		return ;
        	}
        	if(ids.indexOf(',') > 0){
        		$.messager.alert('提示','只能选择一条记录!');
        		return ;
        	}
        	
        	//$.messager.alert('提示','该功能未实现!');
        	var data = $("#itemParamList").datagrid("getSelections")[0];
        	$("#itemParamEditWindow").window({
        		onLoad :function(){
		        	$("#itemeParamEditForm").form("load",data);
					//[{"group":"主体","params":["书名","作者","价格","页数","字体"]}]
		        	//回显商品规格
					var paramData = JSON.parse(data.paramData);
					var temple="";
		        	for(var i in paramData){
						var pd = paramData[i];
						temple +="<li class=\"param\"><ul>"+
							"<li>"+
								"<input class=\"easyui-textbox\" style=\"width: 150px;\" name=\"group\" value=\""+pd.group+"\"/>"+
								"<a href=\"javascript:void(0)\" class=\"easyui-linkbutton addParam\" onclick=\"addParam(this)\"  title=\"添加参数\" data-options=\"plain:true,iconCls:\'icon-add\'\"></a>"+
								"<a href=\"javascript:void(0)\" class=\"easyui-linkbutton delParamGroup\" onclick=\"delParamGroup(this)\" title=\"删除\" data-options=\"plain:true,iconCls:\'icon-cancel\'\"></a>"+	
							"</li>";
									
						for(var j in pd.params){
							temple += 
							"<li>"+
								"<span>|-------</span>  "+
								"<input style=\"width: 150px;\" class=\"easyui-textbox\" name=\"param\" value=\""+pd.params[j]+"\"/>&nbsp;   "+
								"<a href=\"javascript:void(0)\" class=\"easyui-linkbutton delParam\" onclick=\"delParam(this)\" title=\"删除\" data-options=\"plain:true,iconCls:\'icon-cancel\'\"></a>"+
							"</li>";
						}
			        	temple += "</ul></li>";
		        	}
		        	
					$("#itemParamEditTable .addGroup").parent().parent().append(temple);
					$.parser.parse($("#itemParamEditTable .addGroup").parent().parent());
        		}
        	}).window("open");
        	
        }
    },{
        text:'删除',
        iconCls:'icon-cancel',
        handler:function(){
        	var ids = getSelectionsIds();
        	if(ids.length == 0){
        		$.messager.alert('提示','未选中商品规格!');
        		return ;
        	}
        	$.messager.confirm('确认','确定删除ID为 '+ids+' 的商品规格吗？',function(r){
        	    if (r){
        	    	var params = {"ids":ids};
/*         	    	$.post("/rest/item/param/delete",params, function(data){
            			if(data.status == 200){
            				$.messager.alert('提示','删除商品规格成功!',undefined,function(){
            					$("#itemParamList").datagrid("reload");
            				});
            			}
            		}); */
        	    	$.ajax({
        				type: "POST",
        				url: "/rest/item/param/delete",
        				data: params,  
        				success: function(data){
        						$.messager.alert('提示','操作成功!',undefined,function(){
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
        	    }
        	});
        }
    }];
</script>