function updatePwdForm(jsonData)
{

	var saveurl = '/crm/user/changePwd?t=' + Math.random();
	var pwd =new Object();
	jsonData=JSON.parse(jsonData);
	pwd.upass=pidCrypt.SHA256(jsonData.upass);
	pwd.newpass=pidCrypt.SHA256(jsonData.newpass);
	pwd.confirmpwd=pidCrypt.SHA256(jsonData.confirmpwd);
	// 验证通过进行提交
	$.ajax({
	      async:false,
		  type: 'POST',
		  url:saveurl,
		  data: pwd,
		  dataType: 'json',
	      success:function(result){
		         if(result.code=='1'){
		       		 $('#upass').val('');
		       		 $('#newpass').val('');
		       		 $('#confirmpwd').val('');
		        	 layer.msg('修改密码成功!');
		        	 setTimeout(function close(){layer.closeAll();},1000);
		      	 }
		         else if (result.code == "-1")
				 {
					 alert("密码错误");
				 }
				 else
				 {
					 alert('系统错误,请联系系统管理员');
				 }
	      	}
	      	,error:function(XMLHttpRequest, textStatus, errorThrown) {
			   	  alert('系统错误,请联系系统管理员');
			   	  return false;
			  }
     });	
}

layui.use(['form', 'layedit', 'laydate'], function() {
	var form = layui.form(),
		layer = layui.layer,
		layedit = layui.layedit,
		laydate = layui.laydate;

	// 创建一个编辑器
	var editIndex = layedit.build('LAY_demo_editor');
	// 自定义验证规则
	form.verify({
		pass: [/(.+){6,12}$/, '密码必须6到12位'],
		confirmpwd:function(value,item){
			if ($("#newpass").val() !=$("#confirmpwd").val())
			{
				return "两次密码不一致";
			}
		},
		content: function(value) {
			layedit.sync(editIndex);
		}
	});

	// 监听提交
	form.on('submit(savePwd)', function(data) {
		var jsonparams = JSON.stringify(data.field);
		updatePwdForm(jsonparams);
		return false;
	});
});
