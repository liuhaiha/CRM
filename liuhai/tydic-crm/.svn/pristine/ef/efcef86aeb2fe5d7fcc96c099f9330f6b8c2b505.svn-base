var form;
var log;
layui.use(['form', 'layedit', 'laydate'], function() {
		form = layui.form(),
		layer = layui.layer,
		layedit = layui.layedit,
		laydate = layui.laydate;
		var $ = layui.$;
		
		initData();
});

var logtypeList;
function initData()
{
	// 加载日志类型
	$.ajax({
	      async: true,
		  type: 'POST',
		  url: '../code/getCode?t=' + Math.random(),
		  data:{"category":"00012"},
		  dataType: 'json',
		  timeout: 60000,
		  success:function(code){
			  if (code && code.childList&& code.childList.length>0)
			  {
				  logtypeList = code.childList;
			  }
			  
			  initLogDetail();
			  getProject();
		  },error:function(XMLHttpRequest, textStatus, errorThrown) {
		   	  alert('系统错误,请联系系统管理员');
		   	  return false;
		  }
	    }); 
}
var proList;
function getProject(){
	$.ajax({
	      async: false,
		  type: 'POST',
		  url: '../sale/getAllProject?t=' + Math.random(),
		  dataType: 'json',
		  timeout: 60000,
		  success:function(result){
			  proList = result;
		  },error:function(XMLHttpRequest, textStatus, errorThrown) {
		   	  alert('系统错误,请联系系统管理员');
		   	  return false;
		  }
	});
}
function initLogDetail()
{
	$.ajax({
	      async: true,
		  type: 'POST',
		  url: 'getLogDetail?t=' + Math.random(),
		  dataType: 'json',
		  timeout: 60000,
		  success:function(result){
			  if (result.code == "1")
			  {
				  log = result.data;
				  isOwner(log);
				  if(log.state == 1)
				  {
					  $("#readState").attr("disabled",true);
					  $("#readState").css("background-color","gray");
				  }
				  else
				  {
					  $("#readState").show();
				  }
				  $("#problem").val(log.problem);
				  $("#nextplan").val(log.nextplan);
				  $("#needhelp").val(log.needhelp);
				  if(log.opinion != null && log.opinion !="")
				  {
					  $("#opinionBtn").html("已批阅");
					  $("#opinionBtn").attr("disabled",true);
					  $("#opinionBtn").css("background-color","gray");
					  $("#opinionBtn").val(1);
					  $("#opinion").val(log.opinion);
				  }else
				  {
					  $("#leaderOpinion").hide();
				  }
				  
				  var len = log.itemList && log.itemList.length > 0?log.itemList.length:0;
				  for (var i = 0; i < len; i++)
				  {
					  addLogRow(log.itemList[i],i);
				  }
				  if (form)
				  form.render("select");
			  }
			  else
			  {
				  alert("查询失败");
			  }
		  },error:function(XMLHttpRequest, textStatus, errorThrown) {
		   	  alert('系统错误,请联系系统管理员');
		   	  return false;
		  }
	    }); 
}

function addLogRow(item,idx)
{
	var trHtml = '<tr><td style="width:60px;height:50px;">';
	trHtml+="工作" + (idx+1);
	trHtml+="</td>";
	trHtml+='<td style="width:180px;">';
	trHtml+='<select>';
    for (var i = 0; i < logtypeList.length; i++)
    {
	  var childCode = logtypeList[i];
	  if (item.logtype == childCode.code)
	  {
		  trHtml += '<option value="'+childCode.code+'">'+childCode.name+'</option>';
	  }
    }
    trHtml+='</select></td>';
    if(item.pid != 0 && item.pid != null){
    	trHtml+='<td style="width:180px;">';
    	trHtml+='<select>';
    	for(var i=0;i<proList.length;i++){
    		var childPro = proList[i];
    		if(item.pid == childPro.pid){
    			trHtml += '<option value="'+childPro.pid+'">'+childPro.shortName+'</option>';
    		}
    	}
    	trHtml+='</select></td>';
    }else{
    	trHtml+='<td style="width:180px;"></td>';
    }
    trHtml+='<td><textarea class="layui-textarea" style="width:99%;margin-left:10px;min-height:50px;" readonly="readonly">'+item.content+'</textarea></td>';
    trHtml+='<td style="width:60px;"></td></tr>';
    $("#logitemTable").append(trHtml);
    return false;
}

function isOwner(log){
	var logid = log.logid; 
	var creatorNo = log.creatorNo;
	$.ajax({
		url:'isOwner?t=' + Math.random(),
		method:'get',
		dataType:'json',
		data:{"logid":logid},
		async:true,
		success:function(result){
			if(result.code =="1"){
				$("#opinionBtn").attr("disabled",true);
				$("#readState").attr("disabled",true);
				$("#opinionBtn").css("background-color","gray");
				$("#readState").css("background-color","gray");
			}
			else
			{
				$.ajax({
					url:'isLeader?t=' + Math.random(),
					method:'get',
					dataType:'json',
					data:{"creatorNo":creatorNo},
					async:true,
					success:function(result){
						if(result.code =="-1"){
							$("#opinionBtn").attr("disabled",true);
							$("#readState").attr("disabled",true);
							$("#opinionBtn").css("background-color","gray");
							$("#readState").css("background-color","gray");
						}
					},
					error:function(){}
				});
			}
		},
		error:function(){}
	});
}
$("#opinionBtn").click(function opinion(){
	var logid = log.logid; 
	layer.open({
		type:1,
		title:'请填写批阅意见',
		area:['300px','200px'],
		shade:0.1,
		shadeClose:false,
		content : $('#opinionPage'),
		offset:'10%',
		btn:['确认','取消'],
		yes:function(index, layero){
			var opinion = $("#opinionDetail").val();
			if(opinion == null || opinion ==""){
				layer.msg("请输入内容",{offset:'40%'});
				return;
			}
			else
			{
				$.ajax({
					url:'saveOpinion?t=' + Math.random(),
					method:'get',
					dataType:'json',
					data:{"opinion":opinion,"logid":logid},
					async:true,
					success:function(result){
						if(result.code == "1"){
							$("#opinionBtn").html("已批阅");
							$("#opinionBtn").attr("disabled",true);
							$("#opinionBtn").css("background-color","gray");
							location.href="toLogDetailPage?logid="+logid+"";
						}
						else
						{
							layer.msg("系统错误",{offset:'40%'});
						}
					},
					error:function(){}	
				});
				}
			layer.close(index);
		},
		cancel:function(index, layero){
			layer.close(index);
		}
	});
});

$("#readState").click(function readState(){
	var logid = log.logid;
	$.ajax({
		url:'updateLogState?t=' + Math.random(),
		method:'get',
		dataType:'json',
		data:{"logid":logid},
		async:true,
		success:function(result){
			if(result.code == "1"){
				$("#readState").attr("disabled",true);
				$("#readState").css("background-color","gray");
			}
			else{
				layer.msg("系统错误",{offset:'40%'});
			}
		},
		error:function(){}	
	});
});
function goBack(){
	var logid = log.logid;
	location.href="toLogPage?t=" + Math.random();
}