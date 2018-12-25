// 座机号码格式
var isPhone = /^([0-9]{3,4}-)?[0-9]{7,8}$/;
// 手机号码格式
var isMob = /^((\+?86)|(\+86))?(13[012356789][0-9]{8}|15[012356789][0-9]{8}|18[02356789][0-9]{8}|147[0-9]{8}|1349[0-9]{7})$/;
// 邮箱格式
var emailRule = new RegExp("^[a-z0-9]+([._\\-]*[a-z0-9])*@([a-z0-9]+[-a-z0-9]*[a-z0-9]+.){1,63}[a-z0-9]+$");
var check_linkman_val = [];
var len = 0;

//增加客户联系人
function addlinkman()
{
	var pid = check_val[0];
	var fields = ['name'];
	var filedNames = ['客户姓名'];
//	var fields = ['name','dept','super_dept','post','sex','officephone','telephone','email'];
//	var filedNames = ['客户信息','所在部门','上级部门','职务','性别','座机号码','手机号码','E-Mail'];
	var len = fields.length;
	for (var i = 0; i< len; i++)
	{
		$("#link_" + fields[i]).val("");
	}
	$("#link_birthday").val("");
	$("#link_hobby").val("");
	$("#link_remark").val("");
	
	layer.open({
	  title: '请填写客户联系人信息'
	  ,type: 1
	  ,content: $('#addlinkman')
	  ,area:['650px','385px']
	  ,offset:10
	  ,shade:0.1
	  ,shadeClose:false
	  ,btn: ['保存', '取消']
	  ,yes: function(index, layero){
		//按钮【按钮一】的回调
		
		var name = document.getElementById('link_name').value;
		var dept = document.getElementById('link_dept').value;
		var superDept = document.getElementById('link_super_dept').value;
		var post = document.getElementById('link_post').value;
		var sex = document.getElementById('link_sex').value;
		var officephone = document.getElementById('link_officephone').value;
		var telephone = document.getElementById('link_telephone').value;
		var email = document.getElementById('link_email').value;
		var birthday = document.getElementById('link_birthday').value;
		var hobby = document.getElementById('link_hobby').value;
		var remark = document.getElementById('link_remark').value;
		
		for (var i = 0; i< len - 2; i++)
		{
			if ($("#link_" + fields[i]).val() == "")
			{
				alert(filedNames[i] + "不能为空");
				return false;
			}
		}
		
//		if (!isPhone.test(officephone)) {
//			alert('座机号码格式错误');
//			return false;
//		}
//		if ($("#link_telephone").val() == "") {
//			alert('手机号码不能为空');
//			return false;
//		}
//		if (!isMob.test(telephone)) {
//			alert('手机号码格式错误');
//			return false;
//		}
//		if ($("#link_email").val() == "") {
//			alert('邮箱不能为空');
//			return false;
//		}
//		if (!emailRule.test(email)) {
//			alert('邮箱格式错误');
//			return false;
//		}
		
		$.ajax({
			type     : "POST",
			url      : "../../base/linkman/create",
			dataType : "json",
			data     : {"cid": treeId, "name":name, "dept":dept, "superDept":superDept, "post":post,
				"sex":sex, "officephone":officephone, "telephone":telephone, "email":email,
				"birthday":birthday, "hobby":hobby, "remark":remark, "pid":pid},
			success  : function(data) {
				check_linkman_val = [];
				showLinkman(treeId);
			},
			error    : function() {
				alert('新增失败，请重试');
			}
		});
		
		layer.close(index); //如果设定了yes回调，需进行手工关闭
		
	  }
	  ,cancel: function(index, layero){ 
		//右上角关闭回调
		
		//return false 开启该代码可禁止点击该按钮关闭
		layer.close(index); //如果设定了yes回调，需进行手工关闭
	  }});
}

function checkLinkman(val) {
	
	if (val.checked) {
		check_linkman_val.push(val.value);
		len = check_linkman_val.length;
		
		if (len > 1) {
			$("input[name=linkman][value='" + check_linkman_val[0] +"']").prop("checked", false);
			var index = check_linkman_val.indexOf(check_linkman_val[0]);
			check_linkman_val.splice(index, 1);
			len--;
		}
	} else {
		var index = check_linkman_val.indexOf(val.value);
		check_linkman_val.splice(index, 1);
		len--;
	}
	return check_linkman_val;
}

//删除客户联系人
function delLinkman()
{
	var id = check_linkman_val[0];
	if (id == null) {
		alert('请选择一个客户联系人');
	} else {
		$.ajax({
			type     : 'GET',
			url      : '../../base/delLinkman/' + id,
			dataType : 'json',
			async    : false,
			success  : function(result) {
				layer.msg('删除成功');
				showLinkman(treeId);
				check_linkman_val = [];
			}, error : function(err) {
				alert('删除失败，请联系管理员');
			}
		});
	}
}

//修改客户联系人
function modlinkman() {
	var id = check_linkman_val[0];
	if (id == null) {
		alert('请选择一个客户联系人');
	} else {
		findLinkmanById(id);
		
		var fields = ['name'];
		var filedNames = ['客户姓名'];
//		var fields = ['name','dept','super_dept','post','sex','officephone','telephone','email'];
//		var filedNames = ['客户信息','所在部门','上级部门','职务','性别','办公电话','联系电话','E-Mail'];
		var len = fields.length;
		layer.open({
			  title: '请填写客户联系人信息：'
			  ,type: 1
			  ,content: $('#addlinkman')
			  ,area:['650px','385px']
			  ,offset:10
			  ,shade:false
			  ,shadeClose:false
			  ,btn: ['修改', '取消']
			  ,yes: function(index, layero){
				//按钮【按钮一】的回调
				
				var name = document.getElementById('link_name').value;
				var dept = document.getElementById('link_dept').value;
				var superDept = document.getElementById('link_super_dept').value;
				var post = document.getElementById('link_post').value;
				var sex = document.getElementById('link_sex').value;
				var officephone = document.getElementById('link_officephone').value;
				var telephone = document.getElementById('link_telephone').value;
				var email = document.getElementById('link_email').value;
				var birthday = document.getElementById('link_birthday').value;
				var hobby = document.getElementById('link_hobby').value;
				var remark = document.getElementById('link_remark').value;
				
				for (var i = 0; i< len - 2; i++)
				{
					if ($("#link_" + fields[i]).val() == "")
					{
						alert(filedNames[i] + "不能为空");
						return false;
					}
				}
				
//				if (!isPhone.test(officephone)) {
//					alert('座机号码格式错误');
//					return false;
//				}
//				if ($("#link_telephone").val() == "") {
//					alert('手机号码不能为空');
//					return false;
//				}
//				if (!isMob.test(telephone)) {
//					alert('手机号码格式错误');
//					return false;
//				}
//				if ($("#link_email").val() == "") {
//					alert('邮箱不能为空');
//					return false;
//				}
//				if (!emailRule.test(email)) {
//					alert('邮箱格式错误');
//					return false;
//				}
				
				$.ajax({
					type     : "POST",
					url      : "../../base/linkman/update",
					dataType : "json",
					data     : {"lid": id, "name":name, "dept":dept, "superDept":superDept, "post":post,
						"sex":sex, "officephone":officephone, "telephone":telephone, "email":email,
						"birthday":birthday, "hobby":hobby, "remark":remark},
					success  : function(data) {
						if (data == 1) {
							layer.msg('修改成功');
							showLinkman(treeId);
							check_linkman_val = [];
						}
					}, 
					error    : function() {
						alert('修改失败，请重试');
					}
				});
				layer.close(index); //如果设定了yes回调，需进行手工关闭
			  }
			  ,cancel: function(index, layero){ 
				//右上角关闭回调
				//return false 开启该代码可禁止点击该按钮关闭
				layer.close(index); //如果设定了yes回调，需进行手工关闭
		  }});
	}
}

//修改单条客户联系人数据回现
function findLinkmanById(id) {
	/* checkLinkman(this); */
	$.ajax({
		type     : "GET",
		url      : "../../base/findLinkmanById/" + id,
		dataType : "json",
		success  : function(result) {
			$('#link_name').val(result.name);
			$('#link_dept').val(result.dept);
			$('#link_super_dept').val(result.superDept);
			$('#link_post').val(result.post);
			$('#link_sex').val(result.sex);
			$('#link_officephone').val(result.officephone);
			$('#link_telephone').val(result.telephone);
			$('#link_email').val(result.email);
			$('#link_birthday').val(result.birthday);
			$('#link_hobby').val(result.hobby);
			$('#link_remark').val(result.remark);
		}, 
		error    : function(err) {
			alert(err);
		}
	});
}