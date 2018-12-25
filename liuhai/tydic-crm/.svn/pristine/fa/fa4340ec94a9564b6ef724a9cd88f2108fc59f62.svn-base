var form;
layui.use(['form', 'layedit', 'laydate'], function() {
		form = layui.form(),
		layer = layui.layer,
		layedit = layui.layedit,
		laydate = layui.laydate;

	form.on('submit(save)', function(data) {
		
		var jsonParam = JSON.stringify(data.field);
		saveOrUpdate(jsonParam);
		return false;
	});
	
	form.on('select(logtype)',function(data){
		var value = data.value;
		if (value.split("_").length == 2 && value.split("_")[1] == "01")
		{
			insertProjCell(value.split("_")[0]);
		}
		else
		{
			removeProjCell(value.split("_")[0]);
		}
	});
	
	checkFields(form,layedit);
	
	form.render();
});
var rowLen;
function addLogRow()
{
	rowLen = $("#logitemTable").find("tr").length;
	var trHtml = '<tr id="tr'+rowLen+'"><td style="width:60px;height:50px;">';
	trHtml+="工作" + (rowLen+1);
	trHtml+="</td>";
	trHtml+='<td style="width:180px;">';
	trHtml+='<select name="itemList['+rowLen+'].logtype" id="logtype" lay-filter="logtype">';
    for (var i = 0; i < logtypeList.length; i++)
    {
	  var childCode = logtypeList[i];
	  trHtml += '<option value="'+rowLen + "_" + childCode.code+'">'+childCode.name+'</option>';
    }
    trHtml+='</select></td>';
	trHtml+='<td style="width:180px;">';
	trHtml+='<select name="itemList['+rowLen+'].pid" id="pid" lay-filter="'+rowLen+'_pid">';
	trHtml+="<option value=''>--请选择--</option>"
	for(var i=0;i<proList.length;i++){
		trHtml += '<option value="'+proList[i].pid+'">'+proList[i].shortName+'</option>';
	}
	trHtml+='</select></td>';
    trHtml+='<td><textarea class="layui-textarea" name="itemList['+rowLen+'].content" id="content" style="width:99%;margin-left:10px;min-height:50px;" lay-verify="content_verify" placeholder="填写工作内容" maxlength="2000"></textarea></td>';
    trHtml+='<td style="width:60px;"><i class="layui-icon" style="float:right;margin-right:10px;" onclick="delRow(this);">&#xe640;删除</i> </td></tr>';
    $("#logitemTable").append(trHtml);
    form.render("select");
    autoAdjuct();
    return false;
}

function insertProjCell(idx)
{
	var tdHtml='<td style="width:180px;">';
	tdHtml+='<select name="itemList['+idx+'].pid" id="pid" lay-filter="'+idx+'_pid">';
	tdHtml+="<option value=''>--请选择--</option>"
	for(var i=0;i<proList.length;i++){
		tdHtml += '<option value="'+proList[i].pid+'">'+proList[i].shortName+'</option>';
	}
	tdHtml+='</select></td>';
	$($("#tr" + idx).children("td")[1]).after(tdHtml);
	form.render("select");
	autoAdjuct();
}

function removeProjCell(idx)
{
	var len = $("#tr" + idx).children("td").length;
	if (len >=5)
	{
		$($("#tr" + idx).children("td")[2]).remove();
		form.render("select");
		autoAdjuct();
	}
}

function autoAdjuct()
{
	var maxCellLen = 0;
	$("#logitemTable tr").each(function(){
		var len = $(this).children("td").length;
		maxCellLen = len > maxCellLen?len:maxCellLen;
	});
	
	$("#logitemTable tr").each(function(){
		var len = $(this).children("td").length;
		if (maxCellLen > len)
		{
			$($(this).children("td")[2]).attr("colspan","2");
		}
		else
		{
			$($(this).children("td")[2]).attr("colspan","1");
		}
	});
}


function delRow(obj)
{
	var tr = $(obj).parent().parent();
	$(tr).remove();
	resetSeq();
}

// 重新排序日志编号
function resetSeq()
{
	$("#logitemTable").find("tr").each(function(idx,obj){
		var tdArray = $(this).children();
		tdArray.eq(0).html("工作" + (idx+1));//重新排序
	});
}

function checkFields(form,layedit)
{
	// 自定义验证规则
	var editIndex = layedit.build('LAY_demo_editor');
	form.verify({
		content_verify: [/\S/, '工作内容不能为空'],
		total:function(){
			if ($("#logitemTable").find("tr").length == 0)
			{
				return "至少要有一个工作项";
			}
		},
		content: function(value) {
			layedit.sync(editIndex);
		}
	});
}

function isNull(value)
{
	return value == undefined ||$.trim(value).length == 0;
}

var proList;
var logtypeList;
$(function(){
	var worklog = {
		init:function()
		{
			this.fillData();
			this.getProject();
		},
		getProject:function(){
			$.ajax({
			      async: false,
				  type: 'POST',
				  url: '../sale/getAllProject?t=' + Math.random(),
				  dataType: 'json',
				  timeout: 60000,
				  success:function(result){
					  proList = result;
					  var proStr;
					  for(var i=0;i<result.length;i++){
						  proStr += '<option value="'+result[i].pid+'">'+result[i].shortName+'</option>';
					  }
					  $("#pid").html(proStr);
				  },error:function(XMLHttpRequest, textStatus, errorThrown) {
				   	  alert('系统错误,请联系系统管理员');
				   	  return false;
				  }
			});
		},
		fillData:function()
		{
			// 加载日志类型
			$.ajax({
			      async: false,
				  type: 'POST',
				  url: '../code/getCode?t=' + Math.random(),
				  data:{"category":"00012"},
				  dataType: 'json',
				  timeout: 60000,
				  success:function(code){
					  if (code && code.childList&& code.childList.length>0)
					  {
						  logtypeList = code.childList;
						  var len = logtypeList.length;
						  var optionHtml = '';
//						  var prefixStr = "<option value='choose'>--请选择--</option>"
						  for (var i = 0; i < len; i++)
						  {
							  var childCode = logtypeList[i];
							  optionHtml += '<option value="'+childCode.code+'">'+childCode.name+'</option>';
						  }
//						  optionHtml = prefixStr + optionHtml;
						  $("#logtype").html(optionHtml);
						  
						  if (form)
						  {
							  form.render("select");
						  }
					  }
				  },error:function(XMLHttpRequest, textStatus, errorThrown) {
				   	  alert('系统错误,请联系系统管理员');
				   	  return false;
				  }
			    }); 
		}
	}
	worklog.init();
});

function saveOrUpdate(jsonData)
{
	if($("#logtime").val() == "" || $("#logtime").val() ==null ){
		layer.msg("请选择日志时间");
		return;
	}
	else
	{
		$.ajax({
			async: true,
			type: 'POST',
			url: 'saveOrUpdLog?t=' + Math.random(),
			data:JSON.parse(jsonData),
			dataType: 'json',
			timeout: 60000,
			success:function(result){
				if (result && result.code == "1")
				{
					layer.msg('保存成功');
					location.href= 'toLogPage?t=' + Math.random();
				}
				else
				{
					layer.msg('保存失败,请联系系统管理员');
				}
			},error:function(XMLHttpRequest, textStatus, errorThrown) {
				alert('系统错误,请联系系统管理员');
				return false;
			}
		}); 
	}
}
