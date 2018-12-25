jQuery.download=function(url,param)
{
	var inputs = "";
	var params = param.split("&");
	var len = params.length;
	for (var i = 0; i < len; i++)
	{
		var nameVal = params[i];
		var name = nameVal.split("=")[0];
		var value = undefined == nameVal.split("=")[1]?"":nameVal.split("=")[1];
		inputs+="<input type='hidden' name='"+name+"' value='"+value+"' />";
	}
	jQuery("<form action='"+url+"' method='post' target='_blank'>"+inputs+"</form>").appendTo("body").submit().remove();
}

var pageNumber = parseInt($("#pageNumber").text() == ""?"1":$("#pageNumber").text());
var pageSize = $("#pageSize").val() == undefined?"10":$("#pageSize").val();

var sort;//升序降序
var field;//排序字段

function topage(topge) {
	if( $("#totalPage").text()==0 ){
		return;
	}
	if (topge == "tofirst") {
		if($("#pageNumber").text() == 1){
			layer.msg("当前第一页",{offset:'40%'});
		}else{
			pageNumber = 1;
			$("#pageNumber").text(pageNumber);
		}
	} else if (topge == "tolast") {
		if($("#pageNumber").text() == $("#totalPage").text()){
			layer.msg("当前为最后一页",{offset:'40%'});
		}else{
			pageNumber = totalPage;
			$("#pageNumber").text(parseInt($("#totalPage").text()));
		}
	}
	worklog.queryLog();
}

function changePage(obj) {
	if( $("#totalPage").text()==0 ){
		return;
	}
	var pageNumber = parseInt($("#pageNumber").text());
	var totalPage = parseInt($("#totalPage").text());
	if (obj == "next") {
		if (pageNumber < totalPage) {
			pageNumber = parseInt(pageNumber) + 1;
			$("#pageNumber").text(pageNumber);
		} else if ($("#pageNumber").text() == $("#totalPage").text()) {
			layer.msg("当前为最后一页",{offset:'40%'});
		}
	} else if (obj == "last") {
		if (pageNumber <= totalPage && pageNumber > 1) {
			pageNumber = parseInt(pageNumber) - 1;
			$("#pageNumber").text(pageNumber);
		}else{
			layer.msg("当前第一页",{offset:'40%'});
		}
	}
	worklog.queryLog();
}
var worklog;
var clickEno  = "";
$(function(){
	if($("#pageNumber").text() == ""){
		pageNumber = 1;
	}
//  var pageNumber = 1;
//	var pageSize = 10;
	var totalPage = 0;
	var workTypeCode; //区域字典数据
	worklog = {
		init:function()
		{
			this.queryLog();
			this.bindEvent();
		},
		queryLog:function()
		{
			pageNumber = $("#pageNumber").text();
			var content = $("#content").val();
			var creator = $("#creator").val();
			var beginDate = $("#beginDate").val();
			var endDate = $("#endDate").val();
			var eno = clickEno;
			$.ajax({
			      async: true,
				  type: 'POST',
				  url: 'queryLog?t=' + Math.random(),
				  data:{"pageNumber":pageNumber,"pageSize":pageSize,"content":content,"creator":creator,"beginDate":beginDate,"endDate":endDate,"eno":eno,"sort":sort,"field":field},
				  dataType: 'json',
				  timeout: 60000,
				  success:function(page){
					  var tbodyHtml = '';
					  if (page.results && page.results.length > 0)
					  {
						  var len = page.results.length;
						  var html = "";
						  var trTemplate = '<tr style="font-size:11px;height:35px;"><th style="font-size:11px;"><input type="checkbox" name="logid" value="{0}"></th>'
						  					+'<th name="th" style="font-size:11px;" title="{1}">{2}</th><th name="th" style="font-size:11px;" title="{3}">{4}</th><th name="th" style="font-size:11px;" title="{5}">{6}</th>'
						  					+'<th name="th" style="font-size:11px;" title="{7}">{8}</th><th name="th" style="font-size:11px;">{10}</th><th name="th" style="font-size:11px;">{9}</th>'
						  					+'<th name="th" style="font-size:11px;">{11}</th><th name="th" style="font-size:11px;">{12}</th><th name="th" style="font-size:11px;">{13}</th></tr>';
						  for (var i = 0; i < len; i++)
						  {
							  var worklog = page.results[i];
							  var trHtml = trTemplate;
							  trHtml = trHtml.replace("{0}",worklog.logid);
							  trHtml = trHtml.replace("{1}",worklog.content);
							  trHtml = trHtml.replace("{2}",'<a href="javascript:void(0);">'+showSimple(worklog.content)+'</a>');
							  trHtml = trHtml.replace("{3}",worklog.problem);
							  trHtml = trHtml.replace("{4}",showSimple(worklog.problem));
							  trHtml = trHtml.replace("{5}",worklog.nextplan);
							  trHtml = trHtml.replace("{6}",showSimple(worklog.nextplan));
							  trHtml = trHtml.replace("{7}",worklog.needhelp);
							  trHtml = trHtml.replace("{8}",showSimple(worklog.needhelp));
							  trHtml = trHtml.replace("{9}",worklog.creator);
							  if(worklog.logtime == null){
								  worklog.logtime = "";
								  trHtml = trHtml.replace("{10}",(worklog.logtime).substring(0,10));
							  }else{
								  trHtml = trHtml.replace("{10}",(worklog.logtime).substring(0,10));
							  }
							  if(worklog.state == '1'){
								  trHtml = trHtml.replace("{11}",'已读');
							  }else{
								  trHtml = trHtml.replace("{11}",'未读');
							  }
							  
							  if(worklog.opinion != null){
								  trHtml = trHtml.replace("{12}",'是');
							  }else{
								  trHtml = trHtml.replace("{12}",'否');
							  }
							  if(worklog.opinion == null){
								  trHtml = trHtml.replace("{13}",'');
							  }else{
								  trHtml = trHtml.replace("{13}",worklog.opinion);
							  }
							  tbodyHtml+=trHtml;
						  }
					  }
					  $("#logTbody").html(tbodyHtml);
					  
					  $("#logTbody").delegate("tr","mouseenter",function()
							  {	
							$(this).css("background-color","#FDE5B2")
							$(this).siblings().css("background-color","#fff")
						});
					 
					  $("th[name='th']").bind("click",function(obj){
						  var logid = $(this).parent().children().first().children().first().val();
						  location.href="toLogDetailPage?logid="+logid + "&t=" + Math.random();
					  })
					  
					  loadpageLog(page.totalSize);
					  $("#totalSizeLog").html(page.totalSize);
					  $("#totalPageLog").html(page.totalPage);
				  },error:function(XMLHttpRequest, textStatus, errorThrown) {
				   	  alert('系统错误,请联系系统管理员');
				   	  return false;
				  }
			    }); 
		},
		delLog:function(callback)
		{
			var logids = "";
			
			var checkArray = $("input[name='logid']:checked");
			if (checkArray.length == 0)
			{
				 layer.msg("请选择要删除的工作日志");
				return;
			}
			
			for (var i =0,len = checkArray.length; i < len; i++)
			{
				logids+=$(checkArray[i]).val();
				if (i != len-1)
				{
					logids+=",";
				}
			}
			
			layer.confirm('确认删除?',{
				title:''
				,btn:['确认', '取消']
				,btn1:function(index,layerno)
				{
					$.ajax({
					      async: true,
						  type: 'POST',
						  url: 'delLog?t=' + Math.random(),
						  data:{"logids":logids},
						  dataType: 'json',
						  timeout: 60000,
						  success:function(result){
							  if (result.code == "1")
							  {
								  layer.msg("删除成功");
								  callback();
							  }
							  else if (result.code == "-1")
							  {
								  layer.msg("非本人的工作日志不允许删除");
							  }
							  else
							  {
								  layer.msg('删除失败，请联系系统管理员');
							  }
						  },error:function(XMLHttpRequest, textStatus, errorThrown) {
						   	  alert('系统错误,请联系系统管理员');
						   	  return false;
						  }
					    }); 
				}
				})
		},
		bindEvent:function()
		{
			var queryLog = this.queryLog;
			
			$("#btnSearch").click(function(){
				queryLog();
			})
			
			var delLog = this.delLog;
			$("#btnDel").click(function(){
				delLog(queryLog);
			});
			
			$("#btnAdd").click(function(){
				location.href="toLogAddPage";
			});
			
			$("#btnEdit").click(function(){
				var checkArray = $("input[name='logid']:checked");
				if (checkArray.length == 0)
				{
					layer.msg("请选择要编辑的工作日志");
					return;
				}
				
				if (checkArray.length > 1)
				{
					layer.msg("不允许同时编辑多个工作日志");
					return;
				}
				var logid = $(checkArray[0]).val();
				
				$.ajax({
				      async: true,
					  type: 'POST',
					  url: 'isOwner?t=' + Math.random(),
					  data:{"logid":logid},
					  dataType: 'json',
					  timeout: 60000,
					  success:function(result){
						  if (result.code == "1")
						  {
							  location.href="toLogEditPage?logid=" + logid + "&t=" + Math.random();
						  }
						  else if (result.code == "-1")
						  {
							  layer.msg("非本人的工作日志不允许编辑");
						  }
						  else
						  {
							  layer.msg('编辑失败，请联系系统管理员');
						  }
					  },error:function(XMLHttpRequest, textStatus, errorThrown) {
					   	  alert('系统错误,请联系系统管理员');
					   	  return false;
					  }
				    }); 
			});
			
			$("#btnExport").click(function(){
				var content = $("#content").val();
				var creator = $("#creator").val();
				var beginDate = $("#beginDate").val();
				var endDate = $("#endDate").val();
				var parmas = "content=" + content + "&creator=" + creator
				+ "&beginDate=" + beginDate + "&endDate=" + endDate; 
				$.download('exportLog?t=' + Math.random(),parmas);
			});
			var click  =1;
			$("#logTime").click(function(){
				sort = "logtime";
				if(click%2 == 1){
					field = "desc";
				}else{
					field = "asc";
				}
				click = click +1;
				worklog.queryLog();
			});
			$("#logCreater").click(function(){
				sort = "creator_no";
				if(click%2 == 1){
					field = "desc";
				}else{
					field = "asc";
				}
				worklog.queryLog();
				click = click +1;
			});
			$("#logState").click(function(){
				sort = "state";
				if(click%2 == 1){
					field = "desc";
				}else{
					field = "asc";
				}
				click = click +1;
				worklog.queryLog();
			});
			$("#logReadstate").click(function(){
				sort = "readstate";
				if(click%2 == 1){
					field = "desc";
				}else{
					field = "asc";
				}
				click = click +1;
				worklog.queryLog();
			});
			$("#myLog").click(function(){
				$("#myLog").css("background-color","blue");
				$("#myStaffLog").css("background-color","");
				$("#myTeamLog").css("background-color","");
				$.ajax({
					async:true,
					url:'myLog?t=' + Math.random(),
					type:'post',
					dataType:'json',
					success:function(result){
						if(result.code == "1"){
							var eno = result.data;
							clickEno = eno;
							queryLog();
						}else{
							layer.msg("系统错误",{offset : '40%'});
						}
					},
					error:function(XMLHttpRequest, textStatus, errorThrown) {
					   	  alert('系统错误,请联系系统管理员');
					   	  return false;
					  }
				});
			});
			$("#myStaffLog").click(function(){
				$("#myStaffLog").css("background-color","blue");
				$("#myLog").css("background-color","");
				$("#myTeamLog").css("background-color","");
				$.ajax({
					async:true,
					url:'myStaffLog?t=' + Math.random(),
					type:'post',
					dataType:'json',
					success:function(result){
						if(result.code == "1"){
							var staffEno = new Array();
							var staffEnos = result.data;
							var staffEno = staffEnos.join(",");
							clickEno = staffEno;
							queryLog();
						}else{
							layer.msg("没有下属",{offset : '40%'});

						}
					},
					error:function(XMLHttpRequest, textStatus, errorThrown) {
					   	  alert('系统错误,请联系系统管理员');
					   	  return false;
					  }
				});
			});
			$("#myTeamLog").click(function(){
				$("#myTeamLog").css("background-color","blue");
				$("#myStaffLog").css("background-color","");
				$("#myLog").css("background-color","");
				$.ajax({
					async:true,
					url:'myTeamLog?t=' + Math.random(),
					type:'post',
					dataType:'json',
					success:function(result){
						if(result.code == "1"){
							var teamEno = new Array();
							var teamEnos = result.data;
							var teamEno = teamEnos.join(",");
							clickEno = teamEno;
							queryLog();
						}else{
							layer.msg("暂无团队",{offset : '40%'});
						}
					},
					error:function(XMLHttpRequest, textStatus, errorThrown) {
					   	  alert('系统错误,请联系系统管理员');
					   	  return false;
					  }
				});
			});
			$("#thisWeek").click(function(){
//				clickEno = "";
				$("#beginDate").val(weekStartDay);
				$("#endDate").val(weekEndDay);
				queryLog();
			});
			$("#thisMonth").click(function(){
//				clickEno = "";
				$("#beginDate").val(monthStartDay);
				$("#endDate").val(monthEndDay);
				queryLog();
			});
			$("#thisQuarter").click(function(){
//				clickEno  = "";
				$("#beginDate").val(quarterStartDay);
				$("#endDate").val(quarterEndDay);
				queryLog();
			});
			$("#thisYear").click(function(){
//				clickEno  = "";
				$("#beginDate").val(yearStartDay);
				$("#endDate").val(yearEndDay);
				queryLog();
			});
		},
		
	}
	worklog.init();
});

function replaceNull(value)
{
	if (value == undefined)
	{
		return '';
	}
	return value;
}

function showSimple(value)
{
	if (value && value.length > 30)
	{
		value = value.substr(0,30) + "...";
	}
	return replaceNull(value);
}

//
var now = new Date();
var nowDayOfWeek = now.getDay();
var nowDay =now.getDate();
var nowMonth = now.getMonth();
var nowYear = now.getYear();
nowYear += (nowYear<2000)?1900:0;

function formatDate(date){
	var myyear = date.getFullYear();
	var mymonth = date.getMonth()+1;
	var myweekday= date.getDate();
	if(mymonth<10){
		mymonth="0" + mymonth;
	}
	if(myweekday<10){
		myweekday="0" + myweekday;
	}
	return(myyear+"-"+mymonth+"-"+myweekday);
}
function getMonthDays(myMonth){
	var monthStartDate = new Date(nowYear,myMonth,1);
	var monthEndDate = new Date(nowYear,myMonth+1,1);
	var days = (monthEndDate - monthStartDate)/(1000*60*60*24);
	return days;
}

function getQuarterStartMonth(){
	var getQuarterStartMonth = 0;
	if(nowMonth<3){
		quarterSatrtMonth = 0;
	}
	if(2<nowMonth && nowMonth <6){
		quarterSatrtMonth = 3;
	}
	if(5<nowMonth && nowMonth <9){
		quarterSatrtMonth = 6
	}
	if(nowMonth>8){
		quarterSatrtMonth = 9;
	}
	return quarterSatrtMonth;
}
var weekStartDay = formatDate(new Date(nowYear,nowMonth,(nowDay - nowDayOfWeek)+1));
var weekEndDay = formatDate(new Date(nowYear,nowMonth,nowDay +(5 - nowDayOfWeek)));
var monthStartDay = formatDate(new Date(nowYear,nowMonth,1));
var monthEndDay = formatDate(new Date(nowYear,nowMonth,getMonthDays(nowMonth)));
var quarterStartDay = formatDate(new Date(nowYear,getQuarterStartMonth(),1));
var quarterEndMonth = getQuarterStartMonth()+2;
var quarterEndDay = formatDate(new Date(nowYear,quarterEndMonth,getMonthDays(quarterEndMonth)));
var yearStartDay = formatDate(new Date(nowYear,0,1));
var yearEndDay = formatDate(new Date(nowYear,11,getMonthDays(nowMonth)));





layui.use(['form', 'layedit', 'laydate'], function() {
	var form = layui.form(),
		layer = layui.layer,
		layedit = layui.layedit,
		laydate = layui.laydate;
	
	// 设置开始时间不能大于结束时间
	$("#beginDate").click(function(){
		layui.laydate({elem: document.getElementById("beginDate"),max:$("#endDate").val()});
	});
	
	// 设置结束时间的不能小于开始时间
	$("#endDate").click(function(){
		layui.laydate({elem: document.getElementById("endDate"),min:$("#beginDate").val()});
	});
    

	// 监听提交
	form.on('submit(save)', function(data) {
		 layer.msg(JSON.stringify(data.field));
		return false;
	});
});

$("#btnReset").click(function(){
	$("#myStaffLog").css("background-color","");
	$("#myLog").css("background-color","");
	$("#myTeamLog").css("background-color","");
	$("#content").val("");
	$("#beginDate").val("");
	$("#endDate").val("");
	$("#creator").val("");
	clickEno = "";
	worklog.queryLog();
});