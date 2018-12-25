

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
	form.on('submit(saveInfo)', function(data) {
		 //layer.msg(JSON.stringify(data.field));
		updateInfo(JSON.stringify(data.field));
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
	
	var editIndex = layedit.build('LAY_demo_editor');
	form.verify({
		sex: [/\S/, '请选择性别'],
		content: function(value) {
			layedit.sync(editIndex);
		}
	});
});

var user;
function showInfo()
{
	$.ajax({
	      async: true,
		  type: 'POST',
		  url: '/crm/employee/getEmpInfo?t=' + Math.random(),
		  dataType: 'json',
		  timeout: 60000,
		  success:function(result){
			  if (result.code == "1")
			  {
				  user = result.data;
				  $("#eno").val(user.eno);
				  $("#eid").val(user.eid);
				  $("#faceUrl").val(user.faceUrl);
				  
				  $("input[name='sex']").each(function(i,elemt){
					  if ($(this).val() == user.sex)
					  {
						  $(this).attr("checked",true);
					  }
					  else
					 {
						  $(this).attr("checked",false);
					 }
					})
				  $("#sexVal").val(user.sex);
				  $("#ename").val(user.ename);
				  $("#dno").val(user.dno);
				  if (user.hiredate !="" && user.hiredate.length >=10)
				  {
					  $("#hiredate").val(user.hiredate.substr(0,10));
				  }
				  $("#station").val(user.station);
				  $("#linkphone").val(user.linkphone);
				  $("#telephone").val(user.telephone);
				  $("#eMail").val(user.eMail);
				  form.render();
			  }
			  
		  },error:function(XMLHttpRequest, textStatus, errorThrown) {
		   	  alert('系统错误,请联系系统管理员');
		   	  return false;
		  }
	    }); 
}

function fillData()
{
	$.ajax({
	      async: true,
		  type: 'POST',
		  url: '../crm/code/getMultCode?t=' + Math.random(),
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
						  }
						  checkHtml += '<input type="checkbox" value="'+childCode.code+'" name="sex" title="'+childCode.name+'" '+checked+' lay-filter="sex"/>';
					  }
					  $("#sexDiv").html(checkHtml);
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
				 }
			  }
			  // 数据字典加载后查询用户信息
			  showInfo();
			  
		  },error:function(XMLHttpRequest, textStatus, errorThrown) {
		   	  alert('系统错误,请联系系统管理员');
		   	  return false;
		  }
	    }); 
}

function updateInfo(jsonData)
{
	$.ajax({
	      async: true,
		  type: 'POST',
		  url: '/crm/employee/saveInfo?t=' + Math.random(),
		  data:JSON.parse(jsonData),
		  dataType: 'json',
		  timeout: 60000,
		  success:function(result){
			  if (result && result.code == "1")
			  {
				  layer.msg('保存成功');
				  setTimeout(function close(){layer.closeAll();},1000);
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

function resetForm()
{
	showInfo();
//	location.href="/employee/toEmpInfo?t=" + Math.random();
}
