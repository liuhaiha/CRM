var layer;
layui.use([ 'form', 'layedit', 'laydate' ],
				function() {
					var form = layui.form(), layer = layui.layer, layedit = layui.layedit, laydate = layui.laydate;

					// 监听提交
					form.on('submit(save)', function(data) {
						layer.msg(JSON.stringify(data.field));
						return false;
					});
				});

// 请求获取客户来源
$(function getCustomerSource() {
	$.ajax({
		url : '/crm/sale/customerSource',
		method : 'post',
		// data:{category:$("#area").attr("data-id")},
		dataType : 'json',
		async : true,
		success : function(allData) {
			var str = {};
			var beginStr = "<option value=''>--请选择--</option>";
			for (i in allData) {
				str = str + "<option value = '" + allData[i].code + "'>"
						+ allData[i].name + "</option>";
			}
			str = beginStr + str;
			$("#customerSource").html(str);
		},
		error : function(message) {

		}
	});
});

// 请求客户区域列表
var areaList;
$(function getcustomerArea() {
	$.ajax({
		url : '/crm/sale/customerArea',
		method : 'post',
		dataType : 'json',
		async : true,
		success : function(allData) {
			areaList = allData;
			var str = {};
			var beginStr = "<option value=''>--请选择--</option>";
			for (i in allData) {
				str = str + "<option value = '" + allData[i].code + "'>"
						+ allData[i].name + "</option>";
			}
			str = beginStr + str;
			$("#customerArea").html(str);
			$("#customerArea").trigger("change");
		},
		error : function(message) {

		}
	});
});
// 客户区域选择联动改变省份
function getCustomerProv(area) {
	var len = areaList.length;
	if(area == "" || area == null){
		$("#customerProv").html("<option value=''>--请选择--</option>");
	}else{
		for (var i = 0; i < len; i++) {
			var areaCode = areaList[i];
			if (areaCode.code == area) {
				var provList = areaCode.childList;
				var str = {};
				var beginStr = "<option value=''>--请选择--</option>";
				for (j in provList) {
					str = str + "<option value = '" + provList[j].code + "'>"+ provList[j].name + "</option>";
				}
				str = beginStr + str;
				$("#customerProv").html(str);
			}
		}
	}
}

// 请求客户类型
$(function getCustomerCategory() {
	$.ajax({
		url : '/crm/sale/customerCategory',
		method : 'post',
		dataType : 'json',
		async : true,
		success : function(allData) {
			var str = {};
			var beginStr = "<option value=''>--请选择--</option>";
			for (i in allData) {
				str = str + "<option value = '" + allData[i].code + "'>"
						+ allData[i].name + "</option>";
			}
			str = beginStr + str;
			$("#customerCategory").html(str);
		},
		error : function(message) {

		}
	});
});

// 从数据字典获取客户部门、职务、性别等
$(function getLinkManSex() {
	$.ajax({
		url : '/crm/sale/linkManSex',
		method : 'post',
		dataType : 'json',
		async : true,
		success : function(allData) {
			var str = {};
			var beginStr = "<option value = ''>--请选择--</option>";
			for (i in allData) {
				str = str + "<option value = '" + allData[i].code + "'>"
						+ allData[i].name + "</option>";
			}
			str = beginStr + str;
			$("#sex").html(str);
			$("#editsex").html(str);
		},
		error : function(message) {

		}
	});
});
// 部门
$(function getLinkManDept() {
	$.ajax({
		url : '/crm/sale/linkManDept',
		method : 'post',
		dataType : 'json',
		async : true,
		success : function(allData) {
			var str = {};
			var beginStr = "<option value = ''>--请选择--</option>";
			for (i in allData) {
				str = str + "<option value = '" + allData[i].code + "'>"
						+ allData[i].name + "</option>";
			}
			str = beginStr + str;
			$("#dept").html(str);
			$("#editdept").html(str);
		},
		error : function(message) {

		}
	});
});
// 上级部门
$(function getLinkManSuperDept() {
	$.ajax({
		url : '/crm/sale/linkManSuperDept',
		method : 'post',
		dataType : 'json',
		async : true,
		success : function(allData) {
			var str = {};
			var beginStr = "<option value = ''>--请选择--</option>";
			for (i in allData) {
				str = str + "<option value = '" + allData[i].code + "'>"
						+ allData[i].name + "</option>";
			}
			str = beginStr + str;
			$("#superDept").html(str);
			$("#editsuperDept").html(str)
		},
		error : function(message) {

		}
	});
});

// 职位
$(function getLinkManPost() {
	$.ajax({
		url : '/crm/sale/linkManPost',
		method : 'post',
		dataType : 'json',
		async : true,
		success : function(allData) {
			var str = {};
			var beginStr = "<option value = ''>--请选择--</option>";
			for (i in allData) {
				str = str + "<option value = '" + allData[i].code + "'>"
						+ allData[i].name + "</option>";
			}
			str = beginStr + str;
			$("#post").html(str);
			$("#editpost").html(str)
		},
		error : function(message) {

		}
	});
});

// 获得交流重点
$(function getExtImport() {
	$
			.ajax({
				url : '/crm/sale/extImport',
				method : 'post',
				dataType : 'json',
				async : true,
				success : function(allData) {
					var str = {};
					var beginStr = "<tr>";
					var endStr = "</tr>";
					str = str + beginStr;
					for (i in allData) {
						str = str
								+ "<td style='width:30%;font-size:14px;'><input type='radio' name='extImport' value = '"
								+ allData[i].code + "'>" + allData[i].name
								+ "</td>";
						if ((parseInt(i) + 1) % 3 == 0) {
							str = str + "</tr><tr>";
						}
					}
					str = str + endStr;
					$("#extImport").html(str);
				},
				error : function(message) {

				}
			});
});
// 获取交流主题
$(function getExtTheme() {
	$
			.ajax({
				url : '/crm/sale/extTheme',
				method : 'post',
				dataType : 'json',
				async : true,
				success : function(allData) {
					var str = {};
					var beginStr = "<tr>";
					var endStr = "</tr>";
					str = str + beginStr;
					for (i in allData) {
						str = str
								+ "<td style='width:30%;font-size:14px;'><input type='checkbox' name='extTheme' value = '"+ allData[i].code + "'>" + allData[i].name+ "</td>";
						if ((parseInt(i) + 1) % 4 == 0) {
							str = str + "</tr><tr>";
						}
					}
					str = str + endStr;
					$("#extTheme").html(str);
				},
				error : function(message) {

				}
			});
});
// 获取请求项目阶段
$(function getItemStage() {
	$.ajax({
		url : '/crm/sale/itemStage',
		method : 'post',
		dataType : 'json',
		async : true,
		success : function(allData) {
			var str = {};
			var beginStr = "<option value=''>--选择--</option>";
			for (i in allData) {
				str = str + "<option value = '" + allData[i].code + "'>"
						+ allData[i].name + "</option>";
			}
			str = beginStr + str;
			$("#itemStage").html(str);
		},
		error : function(message) {

		}
	});
});
// 获取审批人数据字典
$(function getApprMan() {
	$.ajax({
		url : '/crm/sale/apprMan',
		method : 'post',
		dataType : 'json',
		async : true,
		success : function(allData) {
			var str = {};
			var beginStr = "<option value = ''>--请选择--</option>";
			str = beginStr + str;
			for (i in allData) {
				str = str + "<option value = '" + allData[i].eno + "'>"
						+ allData[i].ename + "</option>";
			}
			$("#apprMan").html(str);
		},
		error : function(message) {

		}
	});
});
// 保存客户数据
$("#customerProv").click(function(){
	if ($("#customerArea").val() == undefined || $("#customerArea").val() == null || $("#customerArea").val() == "") {
		layer.msg("请先选择客户区域", {offset : '40%'});
		return;
	}
});

function saveCustomer(code){
	var tCrmCustomer = {};
	tCrmCustomer.cname = $("#customerName").val();
	tCrmCustomer.addr = $("#customerAddr").val();
	tCrmCustomer.region = $("#customerArea").val();
	tCrmCustomer.province = $("#customerProv").val();
	tCrmCustomer.resource = $("#customerSource").val();
	tCrmCustomer.unitType = $("#customerCategory").val();
	tCrmCustomer.cid = $("#customerInfoId").val();
	if (tCrmCustomer.cname == undefined || tCrmCustomer.cname == null || tCrmCustomer.cname == "") {
		layer.msg("请输入客户名称", {offset : '40%'});
		$("#customerName").focus();
		return;
	}
	if (tCrmCustomer.addr == undefined || tCrmCustomer.addr == null || tCrmCustomer.addr == "") {
		layer.msg("请输入客户地址", {offset : '40%'});
		$("#customerAddr").focus();
		return;
	}
	if (tCrmCustomer.region == undefined || tCrmCustomer.region == null || tCrmCustomer.region == "") {
		layer.msg("请选择客户所在地区", {offset :'40%'});
		return;
	}
	if (tCrmCustomer.province == undefined || tCrmCustomer.province == null || tCrmCustomer.province == "") {
		layer.msg("请选择客户省份", {offset : '40%'});
		return;
	}
	if (tCrmCustomer.resource == undefined || tCrmCustomer.resource == null || tCrmCustomer.resource == "") {
		layer.msg("请选择客户来源", {offset : '40%'});
		return;
	}
	if (tCrmCustomer.unitType == undefined || tCrmCustomer.unitType == null || tCrmCustomer.unitType == "") {
		layer.msg("请选择客户单位类别", {offset : '40%'});
		return;
	}
	$.ajax({
		url : '/crm/sale/saveCustomerInfo',
		method : 'post',
		contentType : 'application/json;charset=utf-8',
		data : tCrmCustomer,
		dataType : 'json',
		async : true,
		success : function(result) {
			if(result != null && result!= "" && code == "1"){
				$("#customerInfoId").val(result);
				$("#linkManName").val("").focus();
				$("#sex").val("");
				$("#dept").val("");
				$("#superDept").val("");
				$("#post").val("");
				$("#officephone").val("");
				$("#telephone").val("");
				$("#email").val("");
				layer.open({
					type : 1, // Page层类型
					title : '请填写与会人员信息',
					shade : 0.1, // 遮罩透明度
					shadeClose : false,
					content : $('#addlinkman'),
					offset : '15%'
				});
				
			}
			else
			{
				$("#customerInfoId").val(result);
			}
		},
		error : function() {
			layer.msg("保存失败", {
				offset : '40%'
			});
		}

	});

}
//$("#saveCustomerInfoBtn").click(
//		function() {});

// 保存与会人员信息
// 打开保存页面
function openAddlinkManPage() {
	saveCustomer(1);
}
//function addLinkManInfo(){
//	saveCustomer();
//}
// 保存与会人信息
function addLinkManInfo() {
	var tCrmLinkman = {};
	tCrmLinkman.name = $("#linkManName").val();
	if (tCrmLinkman.name == "") {
		layer.msg("请填写姓名", {
			offset : '40%'
		});
		return;
	}
	tCrmLinkman.sex = $("#sex").val();
	if (tCrmLinkman.sex == "") {
		layer.msg("请选择性别", {
			offset : '40%'
		});
		return;
	}
	tCrmLinkman.dept = $("#dept").val();
	tCrmLinkman.superDept = $("#superDept").val();
	tCrmLinkman.post = $("#post").val();
	tCrmLinkman.officephone = $("#officephone").val();
	tCrmLinkman.telephone = $("#telephone").val();
	var regephone = /^1[34578]\d{9}$/;
	if (tCrmLinkman.telephone != "" && !regephone.test(tCrmLinkman.telephone)) {
		layer.msg("不是有效的手机号码", {
			offset : '40%'
		});
		return;
	}
	tCrmLinkman.email = $("#email").val();
	var regemail = /^[A-Za-z0-9\u4e00-\u9fa5]+@[a-zA-Z0-9_-]+(\.[a-zA-Z0-9_-]+)+$/;
	if (tCrmLinkman.email != "" && !regemail.test(tCrmLinkman.email)) {
		layer.msg("邮箱格式不正确", {
			offset : '40%'
		});
		return;
	}
	if(tCrmLinkman.officephone!="" && /^\d{3}?-?\d{8}/.test(tCrmLinkman.officephone)){
		
	}

	tCrmLinkman.cid = $("#customerInfoId").val();
	$.ajax({
		type : 'post',
		url : '/crm/sale/saveLinkManInfo',
		contentType : 'application/json;charset=utf-8',
		dataType : 'json',
		data : JSON.stringify(tCrmLinkman),
		async : true,
		success : function(result) {
			layer.closeAll();
			layer.msg("保存成功", {
				offset : '40%'
			});
			$("#getLinkMan").append(addTCrmExtCust(result));
			$("#getLinkMan").delegate("tr","mouseenter",function(){
    			$(this).css("background-color","#FDE5B2")
    			$(this).siblings().css("background-color","#fff")
    			});
		},
		error : function() {
		}
	});
}

function addTCrmExtCust(linkman) {
	var str = "<tr style='font-size: 14px; height: 35px;' id = 'linkman"+ linkman.lid + "'>"
			+ "<th style='font-size: 14px;'><input name='listLinkman' type='checkbox' value='"+ linkman.lid + "'></th>"
			+ "<th style='font-size: 14px;'>" + linkman.name
			+ "</th>" + "<th style='font-size: 14px;'>" + linkman.sex + "</th>"
			+ "<th style='font-size: 14px;'>" + linkman.dept + "</th>"
			+ "<th style='font-size: 14px;'>" + linkman.superDept + "</th>"
			+ "<th style='font-size: 14px;'>" + linkman.post + "</th>"
			+ "<th style='font-size: 14px;'>" + linkman.officephone + "</th>"
			+ "<th style='font-size: 14px;'>" + linkman.telephone + "</th>"
			+ "<th style='font-size: 14px;'>" + linkman.email + "</th>"
//			+ "<th><button type= 'button' cust-id="+cust.id+" class='layui-btn layui-btn-small' onclick = 'saveCustomerLinkman(this)'>保存客户联系人</button></th>" 
			+ "</tr>";
	return str;
}
// 编辑与会人员信息
function editTCrmExtCustpage() {
	var checkArray = $("input[name='listLinkman']:checked");
	if (checkArray.length == 0) {
		layer.msg("请选择要修改的与会人员", {
			offset : '40%'
		});
		return;
	}
	if (checkArray.length > 1) {
		layer.msg("不能同时编辑多个人员信息", {
			offset : '40%'
		});
	}
	var checkid = $(checkArray[0]).val();
	if (checkArray.length == 1) {
		layer.open({
			type : 1, // Page层类型
			title : '请填写需要修改的信息',
			shade : false, // 遮罩透明度
			shadeClose : false,
			content : $('#editlinkman'),
			offset : '10%'
		});
		$.ajax({
			url : '/crm/sale/editTCrmLinkman',
			async : true,
			type : 'POST',
			data : {
				"checkid" : checkid
			},
			dataType : 'json',
			timeout : 60000,
			success : function(result) {
				$("#editlinkManName").val(result.name);
				$("#editsex").val(result.sex);
				$("#editdept").val(result.dept);
				$("#editsuperDept").val(result.superDept);
				$("#editpost").val(result.post);
				$("#editofficephone").val(result.officephone);
				$("#edittelephone").val(result.telephone);
				$("#editemail").val(result.email);
			},
			error : function() {
			}
		});
	}
}
//编辑
function editTCrmExtCustInfo() {
	var checkArray = $("input[name='listLinkman']:checked");
	var checkid = $(checkArray[0]).val();
	var tCrmLinkman = {};
	tCrmLinkman.cid = $("#customerInfoId").val();
	tCrmLinkman.name = $("#editlinkManName").val();
	tCrmLinkman.lid = checkid;
	if (tCrmLinkman.name == "") {
		layer.msg("请填写姓名", {
			offset : '40%'
		});
		return;
	}
	tCrmLinkman.sex = $("#editsex").val();
	if (tCrmLinkman.sex == "") {
		layer.msg("请选择性别", {
			offset : '40%'
		});
		return;
	}
	tCrmLinkman.dept = $("#editdept").val();
	tCrmLinkman.superDept = $("#editsuperDept").val();
	tCrmLinkman.post = $("#editpost").val();
	tCrmLinkman.officephone = $("#editofficephone").val();
	tCrmLinkman.telephone = $("#edittelephone").val();
	var regephone = /^1[34578]\d{9}$/;
	if (tCrmLinkman.telephone != "" && !regephone.test(tCrmLinkman.telephone)) {
		layer.msg("不是有效的电话号码", {
			offset : '40%'
		});
		return;
	}
	tCrmLinkman.email = $("#editemail").val();
	var regemail = /^[A-Za-z0-9\u4e00-\u9fa5]+@[a-zA-Z0-9_-]+(\.[a-zA-Z0-9_-]+)+$/;
	if (tCrmLinkman.email != "" && !regemail.test(tCrmLinkman.email)) {
		layer.msg("邮箱格式不正确", {
			offset : '40%'
		});
		return;
	}
	$.ajax({
		type : 'post',
		url : '/crm/sale/saveLinkManInfo',
		contentType : 'application/json;charset=utf-8',
		dataType : 'json',
		data : JSON.stringify(tCrmLinkman),
		async : true,
		success : function(result) {
			layer.closeAll();
			var linkmanid = result.lid;
			if(linkmanid != "" && linkmanid != null){
				layer.msg("修改成功", {offset : '40%'});
				var editLinkId=$("#linkman" + linkmanid).children().last().children().first().attr("link-id");
				$("#linkman" + linkmanid).prop("outerHTML", addTCrmExtCust(result));
				$("#linkman" + linkmanid).children().last().children().first().attr("link-id",editLinkId);
			}else{
				layer.msg("修改失败", {offset : '40%'});
			}
		},
		error : function() {
		}
	});
}
// 删除与会人员信息
function delTCrmExtCust() {
	var checkids = "";
	var checkArray = $("input[name='listLinkman']:checked");
	if (checkArray.length == 0) {
		layer.msg("请选择要删除的与会人员", {
			offset : '40%'
		});
		return;
	}
	for (var i = 0, len = checkArray.length; i < len; i++) {
		checkids += $(checkArray[i]).val();
		if (i != len - 1) {
			checkids += ",";
		}
	}
	layer.confirm('确认删除?', {
		title : '',
		btn : [ '确认', '取消' ],
		offset : '30%',
		btn1 : function(index, layerno) {
			$.ajax({
				async : true,
				type : 'POST',
				url : '/crm/sale/delTCrmExtCust',
				data : {
					"checkids" : checkids
				},
				dataType : 'json',
				timeout : 60000,
				success : function(result) {
					if (result.code == "1") {
						layer.msg("删除成功", {
							offset : '40%'
						});
						delRows();
					} else {
						layer.msg('删除失败，请联系系统管理员', {
							offset : '40%'
						});
					}
				},
				error : function(XMLHttpRequest, textStatus, errorThrown) {
					alert('系统错误,请联系系统管理员');
					return false;
				}
			});
		}
	})
}

function delRows() {
	$.each($("input[name='listLinkman']:checked"), function(idx, obj) {
		$(this).parent().parent().remove();
	})
}

// 保存与会人员为客户联系人
var tCrmLinkman = {};
function saveCustomerLinkman(pstr) {
	$.ajax({
		url : '/crm/sale/getTCrmExtCust',
		method : 'post',
		dataType : 'json',
		data : {
			"extcustid" : $(pstr).attr("cust-id")
		},
		async : false,
		success : function(result) {
			tCrmLinkman.cid = parseInt($("#customerInfoId").val());
			tCrmLinkman.name = result.name;
			tCrmLinkman.sex = result.sex;
			tCrmLinkman.dept = result.dept;
			tCrmLinkman.superDept = result.superDept;
			tCrmLinkman.post = result.post;
			tCrmLinkman.officephone = result.officephone;
			tCrmLinkman.telephone = result.telephone;
			tCrmLinkman.email = result.email;
			tCrmLinkman.lid = $(pstr).attr("link-id");
			if ($("#customerInfoId").val() != null && $("#customerInfoId").val() != "") {
				$.ajax({
					type : 'post',
					url : '/crm/sale/saveLinkManInfo',
					contentType : 'application/json;charset=utf-8',
					dataType : 'json',
					data : JSON.stringify(tCrmLinkman),
					async : true,
					success : function(result) {
						if (result != "" && result!= null ) {
							layer.msg("保存成功", {offset : '40%'});
							$(pstr).attr("link-id",result);
						}
						else{
							layer.msg("保存失败", {offset : '40%'});
						}
					},
					error : function() {
					}
				});
			} else {
				layer.msg("请先保存客户信息", {offset : '40%'});
				return;
			}
		},
		error : function() {
			return null;
		}
	});
}

// 保存项目
$("#savePro").click(function() {
	var tCrmProject = {};
	if ($("#customerInfoId").val() != null&& $("#customerInfoId").val() != "") {
		tCrmProject.cid = parseInt($("#customerInfoId").val());
		tCrmProject.shortName = $("#itemName").val();
		tCrmProject.pid = $("#pid").val();
		if ($("#itemName").val() == ""
			|| $("#itemName").val() == null) {
			layer.msg("请输入项目名", {
				offset : '40%'
			});
			return;
		}
		tCrmProject.fullName = $("#itemName").val();
		$.ajax({
			url : '/crm/sale/saveTCrmProject',
			method : 'post',
			dataType : 'json',
			data : tCrmProject,
			async : false,
			success : function(result) {
				$("#pid").val(result);
				if (!result) {
					layer.msg("保存成功", {offset : '40%'});
				}
			},
			error : function() {
				return null;
			}
		});
	} else {
		layer.msg("请先保存客户信息", {
			offset : '40%'
		});
		return;
	}
	
});

$("#apprMan").click(function(){
	$("#empTree").html("");
	layui.use('tree',function(){
		$.ajax({
			url:'/crm/sale/getEmp?t=' + Math.random(),
			type:'post',
			dataType:'json',
			success:function(result){
				layui.tree({
					elem: '#empTree',
					check:'checkbox',
					drag:true,
					checkboxName:'checkTree',
					click:function(item){
						treeid = item.eid;
						level = item.level;
						eno = item.code;
						ename = item.name;
					}, nodes:result
				});
			},
			error:function(){}
		});
	});
	layer.open({
		type:1,
		area:['300px','400px'],
		title:'选择我方与会人员',
		shade:0.1,
		shadeClose:true,
		content:$('#extManTree'),
		offset:'20%',
		btn:['确认','取消'],
		yes:function(index, layero){
			var strMan = "";
			var strManNo ="";
			var checkManArray = $("input[name='checkTree']:checked");
			for(var i=0; i<checkManArray.length; i++){
				checkValue = checkManArray[i].value;
				str = checkValue.split('_');
				for(var j=0;j<str.length;j++){
					if(str[j] == "emp"){
						strMan = strMan + str[1] + ",";
						strManNo = strManNo + str[2] + ",";
					}
				}
			}
			strMan=strMan.replace(/,$/,"");
			strManNo=strManNo.replace(/,$/,"");
			if(strMan.split(",").length > 1){
				layer.msg("只能选择一个审批人",{offset:'30%'});
				return;
			}
			$("#apprMan").val(strMan);
			$("#apprMan").html(strManNo);
			layer.close(index);
		},
		cancel:function(index,layero){
			layer.close(index);
		}
	});
});
// 提交x项目交流申请
function saveTCrmExtApply() {
	saveCustomer(2);
	var tCrmExtApply = {};
	tCrmExtApply.aid = $("#extApplyId").val();
	tCrmExtApply.atype = "01";
	tCrmExtApply.cid = $("#customerInfoId").val();
	tCrmExtApply.cname = $("#customerName").val();
	if (tCrmExtApply.cname == undefined || tCrmExtApply.cname == null
			|| tCrmExtApply.cname == "") {
		layer.msg("请输入客户名称", {
			offset : '40%'
		});
		$("#customerName").focus();
		return;
	}
	tCrmExtApply.addr = $("#customerAddr").val();
	if (tCrmExtApply.addr == undefined || tCrmExtApply.addr == null
			|| tCrmExtApply.addr == "") {
		layer.msg("请输入客户地址", {
			offset : '40%'
		});
		$("#customerAddr").focus();
		return;
	}
	tCrmExtApply.region = $("#customerArea").val();
	tCrmExtApply.province = $("#customerProv").val();
	tCrmExtApply.source = $("#customerSource").val();
	tCrmExtApply.unitType = $("#customerCategory").val();
	if (tCrmExtApply.region == undefined || tCrmExtApply.region == null || tCrmExtApply.region == "") {
		layer.msg("请选择客户所在地区", {offset :'40%'});
		return;
	}
	if (tCrmExtApply.province == undefined || tCrmExtApply.province == null || tCrmExtApply.province == "") {
		layer.msg("请选择客户省份", {offset : '40%'});
		return;
	}
	if (tCrmExtApply.source == undefined || tCrmExtApply.source == null || tCrmExtApply.source == "") {
		layer.msg("请选择客户来源", {offset : '40%'});
		return;
	}
	if (tCrmExtApply.unitType == undefined || tCrmExtApply.unitType == null || tCrmExtApply.unitType == "") {
		layer.msg("请选择客户单位类别", {offset : '40%'});
		return;
	}
	tCrmExtApply.extImportant = $("input[type='radio']:checked").val();
	if(tCrmExtApply.extImportant == undefined){
		tCrmExtApply.extImportant = "";
	}
	extTheme = document.getElementsByName("extTheme");
	var check_extTheme = "";
	for (i in extTheme) {
//		if(extTheme[i].nextSibling.nodeValue = "其他"){
//			alert(1);
//		}
		if (extTheme[i].checked)
			check_extTheme = check_extTheme + extTheme[i].value + ",";
	}
	check_extTheme=check_extTheme.replace(/,$/,"");
	tCrmExtApply.extTheme = check_extTheme;
	tCrmExtApply.status = $("#itemStatus").val();
	if ($("#itemStatus").val() == 1) {
		tCrmExtApply.pname = $("#itemName").val();
		if(tCrmExtApply.pname == ""){
			layer.msg('请填写项目名称',{offset:'40%'});
			return;
		}
	}
	tCrmExtApply.stage = $("#itemStage").val();
	tCrmExtApply.money = $("#itemMoney").val();
	tCrmExtApply.estInvite = $("#itemEstInvite").val();
	var choseDate = new Date(Date.parse($("#itemEstInvite").val().replace(/-/g,"/")));
	var nowDate = new Date();
	if(choseDate < nowDate){
		layer.msg('招标日期需大于当前日期！',{offset : '40%'});
		return;
	}
	tCrmExtApply.period = $("#itemPeriod").val();
	tCrmExtApply.cooperOther = $("#cooperOther").val();
	tCrmExtApply.winRole = $("#winRole").val();
	tCrmExtApply.hardware = $("#hardware").val();
	tCrmExtApply.technology = $("#technology").val();
	tCrmExtApply.cloud = $("#cloud").val();
	tCrmExtApply.bigdata = $("#bigdata").val();
	var apprMan = $("#apprMan option:selected");
	var listLinkmans = "";
	$("input[name='listLinkman']").each(function(){
		listLinkmans+=$(this).val() + ",";
	});
	if (listLinkmans.length > 0)
	{
		listLinkmans.substr(0, listLinkmans.length-2);
	}
	
	
	tCrmExtApply.apprNo = apprMan.val();
	tCrmExtApply.apprName = apprMan.text();
	tCrmExtApply.listLinkmans = listLinkmans;
	if (tCrmExtApply.apprNo == undefined || tCrmExtApply.apprNo == null || tCrmExtApply.apprNo == "") {
		layer.msg("请选择审批人", {offset : '40%'});
		return;
	}
	tCrmExtApply.extTime = $("#extTime").val();;
	if (tCrmExtApply.extTime == undefined || tCrmExtApply.extTime == null || tCrmExtApply.extTime == "") {
		layer.msg("请选择交流时间", {offset : '40%'});
		return;
	}
	tCrmExtApply.extAddr = $("#extAddr").val();;
	if (tCrmExtApply.extAddr == undefined || tCrmExtApply.extAddr == null || tCrmExtApply.extAddr == "") {
		layer.msg("请填写交流地点", {offset : '40%'});
		return;
	}
	$.ajax({
		url : '/crm/sale/saveTCrmExtApplyInfo',
		method : 'POST',
		dataType : 'json',
		data : tCrmExtApply,
		async : true,
		success : function(result) {
			if(result != null){
				$("#extApplyId").val(result);
				layer.msg("保存成功", {offset : '40%'});
				setTimeout(function fun(){location.href='../base/base-customer.html';},500);
			}
			else
			{
				layer.msg("保存失败", {offset : '40%'});
			}
		},
		error : function() {

		}
	});
}

// 根据项目状态判断是否需要填写项目名
function itemStatusChange() {
	if ($("#itemStatus").val() == 0) {
		$("#itemName").css("display", "none");
		$("#savePro").css("display", "none");
	} else {
		$("#itemName").css("display", "inline");
		$("#savePro").css("display", "inline");
	}
}
//取消
function cancel(){
	layer.closeAll();
}
