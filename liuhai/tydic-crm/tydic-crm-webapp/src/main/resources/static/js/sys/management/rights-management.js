var layer;
layui.config({
    base: '../../plugins/layui/modules/'
});
layui.use(['form', 'layer', 'laydate'], function() {
    var form = layui.form(),
        layer = layui.layer,
        laypage = layui.laypage;

});

initIcon();
function initIcon() {
	var data = ["#xe632;", "#xe616;", "#xe64a;", "#xe60f;", "#xe612;",
	            "#xe641;", "#xe629;", "#xe62d;", "#xe62e;", "#xe611;", "#xe622;", "#xe64c;", "#xe614;"];
	var html = "<option value=''>-请选择-</option>";
	if (data != null) {
		for (var i = 0; i < data.length; i++) {
			html += "<option value='" + data[i] +"'>" + "&" + data[i] +"</option>";
		}
	}
	$("#iconType").html(html);
	$("#per_iconType").html(html);
}

var treeId = 0;
var level = 0;
var pcode = 0;
var code = "1";
refleshTree();
function refleshTree() {
	$("#org_tree").html("");
	layui.use('tree', function() {
		$.ajax({
			type     : 'GET',
			url      : '../../saleTrack/getAllPermission',
			dataType : 'json',
			async    : false,
			success  : function(result) {
				layui.tree({
				  	elem: '#org_tree' //传入元素选择器
				  	,click: function(item) {
				  		treeId = item.id;
				  		level = item.level;
				  		pcode = item.pcode;
				  		code = item.code;
				  		showPermissionList(code);
				  	},nodes: result
				});
			}
		});
	});
}

var type = "01";
function addTree() {
	if (level == 0) {
		alert('请选择一个节点');
	} else if (level > 3) {
		type = "02";
		alert('该节点暂无添加操作');
	} else {
		type = "01";
		showAddTreeDiv();
	}
}

function showAddTreeDiv() {
	
	var myCode = 0;
	$.ajax({
		type     : "GET",
		url      : "../../saleTrack/getListCount/" + code,
		dataType : "json",
		success  : function(result) {
			var value=/0?[1-9]\d+$/.exec(result.code);
			if(value!=null && value!=""){
				myCode=result.code.replace(value,value*1+1);
			}else{
				myCode=result.code+"1";
			}
		},
		error    : function(err) {
			alert(err);
		}
	});
	
	$('#title').val('');
	$('#iconType').val('');
	$('#url').val('');
	layer.open({
		  title: '备注：添加子节点'
		  ,type: 1
		  ,content: $('#addTree')
		  ,offset:160
		  ,shade:false
		  ,shadeClose:false
		  ,btn: ['新增', '取消']
		  ,yes: function(index, layero){
			//按钮【按钮一】的回调
			var title = document.getElementById('title').value;
			var icon = document.getElementById('iconType').value;
			var perUrl = document.getElementById('url').value;
			if (title == "") {
				alert('权限名称不能为空');
				return false;
			}
			if (icon == "") {
				alert('请选择菜单图标');
				return false;
			}
			$.ajax({
				type     : "POST",
				url      : "../../saleTrack/addTree",
				dataType : "json",
				data     : {"title":title, "icon":"&" + icon, "url":perUrl, "code":myCode, "pcode":code, "type":type},
				success  : function(data) {
					layer.msg('新增成功');
					showPermissionList(code);
					refleshTree();
					$('#title').val('');
					$('#url').val('');
				}, 
				error    : function() {
					alert('新增失败，请重试');
				}
			});
			layer.close(index); //如果设定了yes回调，需进行手工关闭
		  }
		  ,cancel: function(index, layero){
			layer.close(index); //如果设定了yes回调，需进行手工关闭
		  }});
}

function delTree()
{
	if (treeId == 0) {
		alert('请选择一个节点');
	} else {
		layer.confirm('确认删除?',{
			title:'提示:可删除区域/省份地市/客户'
			,btn:['确认', '取消']
			,yes:function(index,layerno)
			{
				$.ajax({
					type     : "GET",
					url      : "../../saleTrack/delTree/" + treeId,
					dataType : "json",
					success  : function(result) {
						if (result) {
							refleshTree();
						} else {
							alert("删除失败，请重试");
						}
					}, 
					error    : function(err) {
						alert(err);
					}
				});
				layer.close(index);
			}
			});
	}
}

var pageNumber = parseInt($("#pageNumber").text() == ""?"1":$("#pageNumber").text());
var pageSize = $("#pageSize").val() == undefined?"4":$("#pageSize").val();
var totalPage = 0;

//页面初始化显示权限列表
showPermissionList(code);
function showPermissionList(code) {
	$.ajax({
		type     : "GET",
		url      : "../../saleTrack/queryPermissionList",
		data     : {"pageNumber":pageNumber, "pageSize":pageSize, "pcode":code},
		dataType : "json",
		success  : function(result) {
			totalPage = result.totalPage;
			var dataHtml = "";
			var dataArr = result.results;
			if (dataArr != null) {
				var len = dataArr.length;
			}
			if (len > 0) {
				for (i in dataArr) {
//					dataHtml += "<tr><th style='font-size:14px;' align='center'>" + replaceNull(dataArr[i].id);
//					dataHtml += "</th><th style='font-size:14px;' align='center'>" + replaceNull(dataArr[i].pcode);
//					dataHtml += "</th><th style='font-size:14px;' align='center'>" + replaceNull(dataArr[i].code);
					dataHtml += "<tr><th style='font-size:14px;' align='center'>" + replaceNull(dataArr[i].title);
					dataHtml += "</th><th class='layui-icon' style='font-size:14px;' align='center'>" + replaceNull(dataArr[i].icon);
					dataHtml += "</th><th style='font-size:14px;' align='center'>" + replaceNull(dataArr[i].url);
					dataHtml += "</th><th style='font-size:14px;' align='center'>" + replaceNull(dataArr[i].creator);
					dataHtml += "</th><th style='font-size:14px;' align='center'>" + 
					"<button class='layui-btn layui-btn-small layui-btn-radius layui-btn-normal' onclick='detailPermission(" + dataArr[i].id +")'>&nbsp;详&nbsp;细&nbsp;</button>" + 
					"<button class='layui-btn layui-btn-small layui-btn-radius layui-btn-danger' onclick='modPermission(" + dataArr[i].id +")'>&nbsp;修&nbsp;改&nbsp;</button>" +
					"<button class='layui-btn layui-btn-small layui-btn-radius' onclick='delPermission(" + dataArr[i].id +")'>&nbsp;删&nbsp;除&nbsp;</button>" + "</th></tr>";
					$("#permList").html(dataHtml);
					$("#permList").delegate("tr","mouseenter",function(){
		    			$(this).css("background-color","#FDE5B2")
		        		$(this).siblings().css("background-color","#fff")
		    			});
				}
			} else {
				$("#permList").html("<td  colspan='20' align='center'> 没有数据！  </td>");
			}
			
			loadpagePro(result.totalSize);
			$("#totalSizePro").html(result.totalSize);
		    $("#totalPagePro").html(result.totalPage);
		}, 
		error    : function() {
			alert('查询失败，请重试');
		}
	});
}

//跳转到首页和末页
function topagepro(topge) {
	if( $("#totalPagePro").text()==0 ){
		return;
	}
	if (topge == "tofirst") {
		$("#pageNumberPro").html(1);
		pageNumber = 1;
	} else if (topge == "tolast") {
		$("#pageNumberPro").html(parseInt($("#totalPagePro").text()));
		pageNumber = totalPage;
	}
	
	showPermissionList(code);
}

//上页 下页点击事件
function changePagepro(obj) {
	if( $("#totalPagePro").text()==0 ){
		return;
	}
	var pageNumberPro = parseInt($("#pageNumberPro").text());
	var totalPagePro = parseInt($("#totalPagePro").text());
	if (obj == "next") {
		if (pageNumberPro < totalPagePro) {
			pageNumberPro = parseInt(pageNumberPro) + 1;
		} else if (pageNumberPro < totalPagePro) {
			pageNumberPro = 1;
		}
	} else if (obj == "last") {
		if (pageNumberPro <= totalPagePro && pageNumberPro > 1) {
			pageNumberPro = (pageNumberPro - 1);
		}
	}
	$("#pageNumberPro").text(pageNumberPro);
	pageNumber = pageNumberPro;
	showPermissionList(code);
}

//跳转到某一页
function tpagePro() {
	var tpnum = $("#jpageNumberPro option:selected").text();
	$("#pageNumberPro").text(tpnum);
	pageNumber = tpnum;
	showPermissionList(code);
}

//每页显示数
function jpagePro() {
	pageSize = $("#pageSizePro").val();
	showPermissionList(code);
}

$(function() {
	$("#first_page").click(function(){
			pageNumber = 1;
			showPermissionList(code);
			layer.msg("当前为第一页");
		}
	);

	$("#pre_page").click(function(){
			pageNumber-=1;
			if (pageNumber < 1) {
				pageNumber=1;
				layer.msg("当前为第一页");
			}
			showPermissionList(code);
		}
	);

	$("#next_page").click(function(){
			pageNumber+=1;
			if (pageNumber > totalPage) {
				pageNumber = totalPage;
				layer.msg("当前为最后一页");
			}
			showPermissionList(code);
		}
	);
	
	$("#last_page").click(function(){
			pageNumber = totalPage;
			showPermissionList(code);
			layer.msg("当前为最后一页");
		}
	);
});

function replaceNull(value)
{
	if (value == undefined)
	{
		return "";
	}
	return value;
}

function addIcon() {
	layer.open({
		  title: '备注：添加子节点'
		  ,type: 1
		  ,content: $('#iconDiv')
		  ,offset:160
		  ,shade:false
		  ,shadeClose:false
		  ,btn: ['确定']
		  ,yes: function(index, layero){
			//按钮【按钮一】的回调
			layer.close(index); //如果设定了yes回调，需进行手工关闭
		  }
	});
}

// 条件搜索
function btnSearch() {
	var searchText = document.getElementById('search').value;
	if (searchText != '') {
		$.ajax({
			type     : "POST",
			url      : "../../saleTrack/search",
			dataType : "json",
			data     : {"title":searchText},
			success  : function(result) {
				var dataHtml = "";
				var dataArr = result.results;
				if (dataArr != null) {
					var len = dataArr.length;
				}
				if (len > 0) {
					for (i in dataArr) {
						dataHtml += "<tr><th style='font-size:14px;' align='center'>" + replaceNull(dataArr[i].title);
						dataHtml += "</th><th class='layui-icon' style='font-size:14px;' align='center'>" + replaceNull(dataArr[i].icon);
						dataHtml += "</th><th style='font-size:14px;' align='center'>" + replaceNull(dataArr[i].url);
						dataHtml += "</th><th style='font-size:14px;' align='center'>" + replaceNull(dataArr[i].creator);
						dataHtml += "</th><th style='font-size:14px;' align='center'>" + 
						"<button class='layui-btn layui-btn-small layui-btn-radius layui-btn-normal' onclick='detailPermission(" + dataArr[i].id +")'>&nbsp;详&nbsp;细&nbsp;</button>" + 
						"<button class='layui-btn layui-btn-small layui-btn-radius layui-btn-danger' onclick='modPermission(" + dataArr[i].id +")'>&nbsp;修&nbsp;改&nbsp;</button>" +
						"<button class='layui-btn layui-btn-small layui-btn-radius' onclick='delPermission(" + dataArr[i].id +")'>&nbsp;删&nbsp;除&nbsp;</button>" + "</th></tr>";
						$("#permList").html(dataHtml);
						loadpagePro(result.totalSize);
						$("#totalSizePro").html(result.totalSize);
					    $("#totalPagePro").html(result.totalPage);
					}
				} else {
					$("#permList").html("<td  colspan='20' align='center'> 没有数据！  </td>");
				}
			},
			error    : function(err) {
				alert('搜索失败，请重试' + err);
			}
		});
	} else {
		alert('请填写查询条件');
	}
}