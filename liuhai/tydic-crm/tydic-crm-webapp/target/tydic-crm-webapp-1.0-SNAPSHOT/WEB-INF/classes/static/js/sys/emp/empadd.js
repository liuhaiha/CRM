

var form;
layui.use(['form', 'layedit', 'laydate'], function() {
		form = layui.form(),
		layer = layui.layer,
		layedit = layui.layedit,
		laydate = layui.laydate;

//	// 创建一个编辑器
//	var editIndex = layedit.build('LAY_demo_editor');
//	// 自定义验证规则
//	form.verify({
//		pass: [/(.+){6,12}$/, '密码必须6到12位'],
//		content: function(value) {
//			layedit.sync(editIndex);
//		}
//	});

	// 监听提交
	form.on('submit(save)', function(data) {
		 console.log(JSON.stringify(data.field));
		saveInfo(JSON.stringify(data.field));
		return false;
	});
	
	fillData();
	
	form.on('checkbox(sex)', function(cxb) {
		if (cxb.elem.checked == true)
		{
			$("#sexVal").val(cxb.value);
		}
		$("input[name='sex']").each(function(idx,sex){
			if ($(this).val() == cxb.value)
			{
				$(this).attr("checked",cxb.elem.checked);
			}
			else
			{
				$(this).attr("checked",!cxb.elem.checked);
			}
			form.render("checkbox");
		});
		return false;
	});
	
	form.render();
	
	var editIndex = layedit.build('LAY_demo_editor');
	form.verify({
		sex: [/\S/, '请选择性别'],
		content: function(value) {
			layedit.sync(editIndex);
		}
	});
});

function fillData()
{
	$.ajax({
	      async: true,
		  type: 'POST',
		  url: '../code/getMultCode?t=' + Math.random(),
		  data:{"category":"00001,00015,00016"},
		  dataType: 'json',
		  timeout: 60000,
		  success:function(codeList){
			  
			  if (codeList.length > 0)
			  {
				  var sexCode = codeList[0];
				  if (sexCode.childList&& sexCode.childList.length>0)
				  {
					  var childList = sexCode.childList;
					  var len = childList.length;
					  var checkHtml = "";
					  for (var i = 0; i < len; i++)
					  {
						  var childCode = childList[i];
						  var checked = "";
						  if (i == 0)
						  {
							  checked = "checked";// 默认第一个选中
							  $("#sexVal").val(childCode.code);
						  }
						  checkHtml += '<input type="checkbox" value="'+childCode.code+'" name="sex" title="'+childCode.name+'" '+checked+' lay-filter="sex"/>';
					  }
					  $("#sexDiv").html(checkHtml);
					  form.render("checkbox");
				  }
				 var deptCode = codeList[1];
				 if (deptCode.childList&& deptCode.childList.length>0)
				 {
					 var childList = deptCode.childList;
					  var len = childList.length;
					  var optionHtml = "";
					  for (var i = 0; i < len; i++)
					  {
						  var childCode = childList[i];
						  var selected = "";
						  if (i == 0)
						  {
							  selected = "selected";// 默认第一个选中
						  }
						  optionHtml += '<option value="'+childCode.code+'" '+selected+'>'+childCode.name+'</option>';
					  }
					  $("#dno").html(optionHtml);
					  form.render("select");
				 }
				 
				 var stationCode = codeList[2];
				 if (stationCode.childList&& stationCode.childList.length>0)
				 {
					 var childList = stationCode.childList;
					  var len = childList.length;
					  var optionHtml = "";
					  for (var i = 0; i < len; i++)
					  {
						  var childCode = childList[i];
						  var selected = "";
						  if (i == 0)
						  {
							  selected = "selected";// 默认第一个选中
						  }
						  optionHtml += '<option value="'+childCode.code+'" '+selected+'>'+childCode.name+'</option>';
					  }
					  $("#station").html(optionHtml);
					  form.render("select");
				 }
			  }
			  
		  },error:function(XMLHttpRequest, textStatus, errorThrown) {
		   	  alert('系统错误,请联系系统管理员');
		   	  return false;
		  }
	    }); 
}

function saveInfo(jsonData)
{
	$.ajax({
	      async: true,
		  type: 'POST',
		  url: 'saveOrUpdWorkEmp?t=' + Math.random(),
		  data:JSON.parse(jsonData),
		  dataType: 'json',
		  timeout: 60000,
		  success:function(result){
			  if (result && result.code == "-1")
			  {
				  layer.msg('员工编号' + $("#eno").val() + "已经存在，请重新输入");
			  }
		      else if (result && result.code == "1")
			  {
				  location.href="toEmpList?t=" + Math.random();
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
//获取员工信息
$(function(){
	$.ajax({
		async:false,
		type:'post',
		url:'../sale/apprMan?t=' + Math.random(),
		dataType:'json',
		success:function(result){
			var str = {};
			var beginStr = "<option value = ''>--请选择--</option>";
			str = beginStr + str;
			for (i in result) {
				str = str + "<option value = '" + result[i].eno + "' name='upperLeader'>"
						+ result[i].ename + "</option>";
			}
			$("#leader").html(str);
		},
		error:function(){}
	});
});
function goback()
{
	 location.href="toEmpList?t=" + Math.random();
}
