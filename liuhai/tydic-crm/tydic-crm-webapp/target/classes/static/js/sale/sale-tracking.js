var layer;
layui.config({
	base: '../../plugins/layui/modules/'
});
layui.use(['form', 'layer', 'laydate'], function() {
	var form = layui.form(),
		layer = layui.layer;
	
});

//var treeId = 1;
//var pid = 0;
//layui.use('tree', function() {
//	
//	var data = null;
//	$.ajax({
//		type     : 'GET',
//		url      : '../../base/list',
//		dataType : 'json',
//		async    : false,
//		success  : function(result) {
//			data = result;
//			
//			layui.tree({
//			  	elem: '#org_tree' //传入元素选择器
//			  	,click:function(item) {
//			  		treeId = item.id;
//			  		var level = item.level;
//			  		pid = 0;
//			  		if (level == 3)
//			  		{
//			  			showproject(treeId);
//			  			findMeetingByProId(treeId, pid);
//			  		}
//			  	}
//			  	,nodes: result
//			});
//			
//			changeAutoHeight();
//		}
//	});
//	
//});

function changeAutoHeight()
{
	$("#docDiv").css("height",$("#txtDiv2").css("height"));
	var authHeight = $("#parentDiv").css("height").replace("px","");
	var treeHeight = $("#tree_parentDiv").css("height").replace("px","");
	//alert(authHeight + ":" + treeHeight);
	
	if (parseInt(treeHeight) < parseInt(authHeight))
	{
		//$("#tree_parentDiv").css("height",authHeight-13);
	}
}

//function delTree()
//{
//	layer.confirm('确认删除?',{
//	title:'提示:可删除区域/省份地市/客户'
//	,btn:['确认', '取消']
//	,btn1:function(index,layerno)
//	{
//		
//	}
//	})
//}
//function addTree()
//{
//	layer.open({
//	  title: '备注：同时可添加省/地市/客户'
//	  ,type: 1
//	  ,content: $('#addTree')
//	  ,shade:false
//	  ,shadeClose:false
//	  ,btn: ['新增', '取消']
//	  ,yes: function(index, layero){
//		//按钮【按钮一】的回调
//		layer.close(index); //如果设定了yes回调，需进行手工关闭
//	  }
//	  ,cancel: function(index, layero){ 
//		layer.close(index); //如果设定了yes回调，需进行手工关闭
//	  }});
//}

// 选择一个项目列表数据的处理
//var check_val = [];
//var len = 0;
//function checkVal(item) {
//	if (item.checked) {
//		check_val.push(item.value);
//		len = check_val.length;
//		if (len > 1) {
//			$("input[name=project][value='" + check_val[0] +"']").prop("checked", false);
//			var index = check_val.indexOf(check_val[0]);
//			check_val.splice(index, 1);
//			len --;
//		}
//	} else {
//		var index = check_val.indexOf(item.value);
//		check_val.splice(index, 1);
//		len --;
//	}
//	pid = item.value;
//	findMeetingByProId(treeId, pid);
//	return check_val;
//}

var meetingIndex = 0;
function changeMeeting(index,id)
{
	findMeetingById(id);
	$("#txtDiv2").css("display",index != (meetingIndex + 1)?"inline-block":"none");
	$("#txtDiv").css("display",index == (meetingIndex + 1)?"inline-block":"none");
	resetForm();
}

function clearMeeting()
{
	$("#show_theme").val("");
	$("#show_mtime").val("");
	$("#show_maddr").val("");
	$("#show_peer").val("");
	$("#show_customers").val("");
	$("#show_thirdMan").val("");
	$("#show_recordname").val("");
	$("#show_process").val("");
	$("#show_summary").val("");
	$("#show_next_plan").val("");
	$('#docDiv object').attr('data','');
}

var mPageNumber = 1;
var mPageSize = 3;
var mTotalPage = 0;
// 根据项目id获取交流过程数据
function findMeetingByProId(treeId, pid) {
	
	$(".time-horizontal li").remove();
	
	clearMeeting();
	
	$.ajax({
		type     : "POST",
		url      : "../../track/getMeetingList",
		dataType : "json",
		data     : {"cid":treeId, "pid":pid, "pageNumber":mPageNumber, "pageSize":mPageSize},
		success  : function(result) {
			mTotalPage = result.totalPage;
			var dataArr = result.results;
			meetingIndex = dataArr.length + 1;
			
			for (var i = 0; i < dataArr.length; i++) {
				var index = i + 1;
				var mmtime = new Date(Date.parse(dataArr[i].mtime));
				var dateVal = mmtime.getFullYear() + '-' + (mmtime.getMonth() + 1) + '-' + mmtime.getDate();
				$(".time-horizontal").append("<li><b><a href='javascript:changeMeeting("+ index + ","+dataArr[i].id+");'>第" + (index + (mPageNumber - 1) * mPageSize) +"次交流<br>" + dateVal + "</a></b></li>");
			}
			
			if (dataArr.length > 0)
			{
				changeMeeting(0,dataArr[0].id);
			}
			
		},
		error    : function(err) {
			alert("系统错误，请联系管理员");
		}
	});
	$(".time-horizontal").append("<li style='float:right;'><button class='layui-btn layui-btn-small' lay-submit=''"
			+ "lay-filter='btnSearch' onclick='apply();'><i class='layui-icon'>&#xe61f;</i> 提交申请</button></li>"
			+ "<li style='float:right;'><button class='layui-btn layui-btn-small' lay-submit=''"
			+ "lay-filter='btnSearch' onclick='addMeeting();'><i class='layui-icon'>&#xe61f;</i> 新增纪要</button></li>"
			+ "<li style='float:right;'><button class='layui-btn layui-btn-small' lay-submit=''"
			+ "lay-filter='btnSearch' onclick='nextMeeting();'><i class='layui-icon'>&#xe602;</i> 下一页</button></li>"
			+ "<li style='float:right;'><button class='layui-btn layui-btn-small' lay-submit=''"
			+ "lay-filter='btnSearch' onclick='preMeeting();'><i class='layui-icon'>&#xe603;</i> 上一页</button></li>");
}

function preMeeting() {
	mPageNumber -= 1;
	if (mPageNumber < 1) {
		mPageNumber=1;
		layer.msg("当前为第一页");
	}
	findMeetingByProId(treeId, pid);
}

function nextMeeting() {
	mPageNumber += 1;
	if (mPageNumber > mTotalPage) {
		mPageNumber=mTotalPage;
		layer.msg("当前为最后一页");
	}
	findMeetingByProId(treeId, pid);
}
//a标签暂时不知道为什么不能执行到点击事件
$(function() {
//	$("#first_page1").click(function(){
//			mPageNumber = 1;
//			showproject(treeId);
//		}
//	)

	$("#pre_meeting").click(function(){
			mPageNumber -= 1;
			if (mPageNumber < 1)mPageNumber=1;
			findMeetingByProId(treeId, pid);
		}
	)

	$("#next_meeting").click(function(){
			mPageNumber += 1;
			if (mPageNumber > mTotalPage)mPageNumber=mTotalPage;
			findMeetingByProId(treeId, pid);
		}
	)
	
//	$("#last_page1").click(function(){
//			mPageNumber = mTotalPage;
//			showproject(treeId);
//		}
//	)
});

//function addMeeting() {
//	$("#txtDiv2").css("display","none");
//	$("#txtDiv").css("display","inline-block");
//}

// 根据客户id、项目id、交流申请id查询一次交流过程
//function findMeetingById(id) {
//	$.ajax({
//		type        : "GET",
//		url         : "../../track/getMeeting/" + id,
//		dataType    : "json",
//		success     : function(result) {
//			if (result)
//			{
//				$("#show_theme").val(result.theme);
//				$("#show_mtime").val(result.mtime);
//				$("#show_maddr").val(result.maddr);
//				$("#show_peer").val(result.peer);
//				$("#show_peer").attr("title", result.peer);
//				$("#show_customers").val(result.customers);
//				$("#show_thirdMan").val(result.thirdman);
//				$("#show_recordname").val(result.recordname);
//				$("#show_process").val(result.process);
//				$("#show_summary").val(result.summary);
//				$("#show_next_plan").val(result.nextPlan);
//				if (null != result.fileno) {
//					$('#docDiv object').attr('data','/crm/saleFile/preview/' + result.fileno);
//				} else {
//					$('#docDiv object').attr('data','');
//				}
//			}
//		}
//	});
//}

// 关联项目
function relProj()
{
	layer.open({
	  title: '请选择关联的项目'
	  ,type: 1
	  ,content: $('#relProj')
	  ,area:'650px'
	  ,offset:100
	  ,shade:false
	  ,shadeClose:false
	  ,btn: ['确认','取消']
	  ,cancel: function(index, layero){ 
		layer.close(index);
	  }});
}

$(function(){
	$.ajax({
		url : '/crm/sale/apprMan',
		method : 'get',
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
			var strMan = {};
			var beginStrMan = "<tr>";
			var endStrMan = "</tr>";
			strMan = strMan + beginStrMan;
			for (i in allData) {
				strMan = strMan
						+ "<td style='width:28%;height:30px;font-size:14px;'><input type='checkbox' name='ManList' value = '"
						+ allData[i].eno + "'>" + allData[i].ename
						+ "</td>";
				if ((parseInt(i) + 1) % 4 == 0) {
					strMan = strMan + "</tr><tr>";
				}
			}
			strMan = strMan + endStrMan;
			$("#extManList").html(strMan);
		},
		error : function(message) {}
	});
});
function apply(){
	if($("#midLevel").val() == '1' || $("#midLevel").val() == "")
	{
		layer.msg("请选择客户或客户省份",{offset:'40%'});
	}
	else if($("#midLevel").val() == '2')
	{
		location.href="../sale/sale-new-meeting.html";
	}
	else
	{
		$("#applyPage input").val("");
		var custId = "";
		var checkId = "";
		var checkArray = $("input[name='project']:checked");
		if(checkArray.length > 1){
			layer.msg("只能选择一个项目",{offset:'40%'});
			return;
		}
		for(var i =0;i<checkArray.length;i++){
			checkId = $(checkArray[i]).val();
		}
		if(checkArray.length != 0){
			$.ajax({
				url:'/crm/sale/getProject',
				method:'post',
				dataType:'json',
				data:{"checkId":checkId},
				async:true,
				success:function(result){
					if(result != "" || result != null){
						$("#extItem").val(result.shortName);
					}else{
						$("#extItem").val("数据异常");
					}
				},
				error:function(){}
			});
		}
		if(treeId != 0){
			var custId = treeId;
			$.ajax({
				url:'/crm/sale/getCustomer',
				method:'post',
				dataType:'json',
				data:{"custId":custId},
				async:true,
				success:function(result){
					if(result != ""|| result != null){
						$("#extCustomer").val(result.cname);
					}else{
						$("#extCustomer").val("数据异常");
					}
				},error:function(){}
			});
		}
		$("#extOwner").click(function(){
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
					$("#extOwner").val(strMan);
					$("#extOwner").html(strManNo);
					layer.close(index);
				},
				cancel:function(index,layero){
					layer.close(index);
				}
			});
		});
		layer.open({
			type:1,
			title:'请填写交流申请相关信息',
			area:['600px','600px'],
			shade:0.1,
			shadeClose:false,
			content : $('#applyPage'),
			offset:'10%',
			btn:['确认','取消'],
			yes:function(index, layero){
				var tCrmExtApply = {};
				tCrmExtApply.cid = custId;
				tCrmExtApply.pid = checkId;
				tCrmExtApply.atype = "02";
				tCrmExtApply.extTheme = $("#extTheme").val();
				if(tCrmExtApply.extTheme == "" || tCrmExtApply.extTheme == null){
					layer.msg("请填写交流主题",{offset:'40%'});
					return;
				}
				tCrmExtApply.extTime = $("#extTime").val();
				if(tCrmExtApply.extTime == "" || tCrmExtApply.extTime == null){
					layer.msg("请选择交流时间",{offset:'40%'});
					return;
				}
				tCrmExtApply.extAddr = $("#extAddr").val();
				if(tCrmExtApply.extAddr == "" || tCrmExtApply.extAddr == null){
					layer.msg("请填写交流地点",{offset:'40%'});
					return;
				}
				tCrmExtApply.extOwner = $("#extOwner").text();
				if(tCrmExtApply.extOwner == "" || tCrmExtApply.extOwner == null){
					layer.msg("请选择我方参会人员",{offset:'40%'});
					return;
				}
				tCrmExtApply.extCus = $("#extCus").val();
				if(tCrmExtApply.extCus == "" || tCrmExtApply.extCus == null){
					layer.msg("请填写客户参会人员",{offset:'40%'});
					return;
				}
				tCrmExtApply.extThird = $("#extThird").val();
				tCrmExtApply.applyState = $("#applyState").val();
				if(tCrmExtApply.applyState == "" || tCrmExtApply.applyState == null){
					layer.msg("请填写申请说明",{offset:'40%'});
					return;
				}
				
				tCrmExtApply.apprNo = $("#apprMan").val();
				tCrmExtApply.apprName = $("#apprMan").find("option:selected").text();
				$.ajax({
					url:'../../sale/saveTCrmExtApplyInfo',
					method:'post',
					dataType:'json',
					data:tCrmExtApply,
					async:true,
					success:function(result){
						if(result != 0 && result != ""){
							layer.msg("新增成功",{offset:'40%'});
						}else{
							layer.msg("新增失败",{offset:'40%'});
						}
					},error:function(XMLHttpRequest, textStatus, errorThrown) {
					   	  alert('系统错误,请联系系统管理员');
					   	  return false;
					  }	
				});
				layer.close(index);
			},
			cancel:function(index, layero){
				layer.close(index);
			}
		});
	}
}

var pageNumber = 1;
var pageSize = 5;
var totalPage = 0;
//显示项目列表
//showproject(treeId);
//function showproject(id)
//{
//	$.ajax({
//		type     : 'GET',
//		url      : '../../base/queryProjectList',
//		data     : {"pageNumber":pageNumber, "pageSize":pageSize, "id":id},
//		dataType : 'json',
//		async    : false,
//		success  : function(result) {
//			totalPage = result.totalPage;
//			var dataArr = result.results;
//			if (dataArr != null) {
//				var leng = dataArr.length;
//			}
//			var datahtml = "";
//			if (leng > 0) {
//				for (i in dataArr) {
//					datahtml += "<tr style='font-size:14px;height:35px;'><td style='font-size:14px;'><input type='checkbox' name='project' value='" + dataArr[i].pid + "' onclick='checkVal(this)'>";
//					datahtml += "</td><td class='dv_tb_jy_td' align='center'>" + replaceNull(dataArr[i].shortName);
//					datahtml += "</td><td class='dv_tb_jy_td' align='center'>" + replaceNull(dataArr[i].stageName);
//					datahtml += "</td><td class='dv_tb_jy_td' align='center'>" + replaceNull(dataArr[i].decisioner);
//					datahtml += "</td><td class='dv_tb_jy_td' align='center'>" + replaceNull(dataArr[i].businessLeaderName);
//					datahtml += "</td><td class='dv_tb_jy_td' align='center'>" + replaceNull(dataArr[i].estMoney) + "</td></tr>";
//				}
//				
//				findMeetingByProId(treeId,0);
//				showCust(treeId);
//				
//				$("#projectList").html(datahtml);
//				$("#projectList").delegate("tr","mouseenter",function(){
//		    		$(this).css("background-color","#FDE5B2")
//		    		$(this).siblings().css("background-color","#fff")
//		    		});
//			} else {
//				$("#projectList").html("<td  colspan='20' class='dv_tb_jy_td' align='center'> 没有数据！  </td>");
//			}
//		}
//	});
//}

//function showCust(id)
//{
//	//点击菜单选项重现客户信息
//	$.ajax({
//		type     : "GET",
//		url      : "../../base/findCustById/" + id,
//		dataType : "json",
//		success  : function(data) {
//			$('#line_text').html("跟踪客户：" + data.extend1 + "-" + data.extend2 + "-" + data.cname);
//		}, 
//		error    : function() {
//			alert('客户信息查询失败，请重试');
//		}
//	});
//}

$(function() {
	$("#first_page").click(function(){
			pageNumber = 1;
			showproject(treeId);
			layer.msg("当前为第一页");
		}
	)

	$("#pre_page").click(function(){
			pageNumber-=1;
			if (pageNumber < 1) {
				pageNumber=1;
				layer.msg("当前为第一页");
			}
			showproject(treeId);
		}
	)

	$("#next_page").click(function(){
			pageNumber+=1;
			if (pageNumber > totalPage) {
				pageNumber=totalPage;
				layer.msg("当前为最后一页");
			}
			showproject(treeId);
		}
	)
	
	$("#last_page").click(function(){
			pageNumber=totalPage;
			showproject(treeId);
			layer.msg("当前为最后一页");
		}
	)
});

function replaceNull(value)
{
	if (value == undefined)
	{
		return "";
	}
	return value;
}

$("#meeting_peer").click(function(){
	$("#empTree").html("");
	layui.use('tree',function(){
		$.ajax({
			url:'/crm/sale/getEmp?t=' + Math.random(),
			type:'post',
			dataType:'json',
			success:function(result){
				console.log(result);
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
			$("#meeting_peer").val(strMan);
			$("#meeting_peer").html(strManNo);
			layer.close(index);
		},
		cancel:function(index,layero){
			layer.close(index);
		}
	});
});
function resetForm() {
	var fields = ['theme','mtime','maddr','peer','customers','thirdMan','recordname','process','summary','next_plan'];
	for (var i = 0; i < fields.length; i++) {
		$("#meeting_" + fields[i]).val('');
	}
}

function saveCom() {
	var fields = ['theme','mtime','maddr','peer','customers','thirdMan','recordname','process','summary','next_plan'];
	var filedNames = ['会议主题','会议时间','会议地点','参与人','客户方','第三方','纪要汇总','交流过程','会议总结','下一步计划'];
	var leng = fields.length;
	
	var theme = $('#meeting_theme').val();
	var mtime = $('#meeting_mtime').val();
	var maddr = $('#meeting_maddr').val();
	var peer = $('#meeting_peer').text();
	var customers = $('#meeting_customers').val();
	var thirdMan = $('#meeting_thirdMan').val();
	var recordname = $('#meeting_recordname').val();
	var process = $('#meeting_process').val();
	var summary = $('#meeting_summary').val();
	var next_plan = $('#meeting_next_plan').val();
	
	for (var i = 0; i < leng; i++) {
		if ($("#meeting_" + fields[i]).val() == "") {
			alert(filedNames[i] + "不能为空");
			return false;
		}
	}
	
	$.ajax({
		type     : "POST",
		url      : "../../track/create",
		data     : {"cid":treeId, "pid":pid, "aid":meetingIndex, "theme":theme, "mtime":mtime, "maddr":maddr, "peer":peer, "customers":customers, 
					"thirdman":thirdMan, "recordname":recordname, "process":process, "summary":summary, "nextPlan":next_plan},
		dataType : "json",
		success  : function(result) {
			if (result == 1) {
				layer.msg('新增成功');
				findMeetingByProId(treeId, pid);
				
				for (var i = 0; i < leng; i++) {
					$("#meeting_" + fields[i]).val('');
				}
				
				for (var i = 0; i<checkManArray.length; i++) {
					$("input[name=ManList][value='" + checkManArray[i].value +"']").prop("checked", false);
				}
			}
		},
		error    : function(err) {
			alert("系统错误，请联系管理员");
		}
	});
	
}