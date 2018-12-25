//增加项目
function addproj()
{
	var fields = ['short_name','stage'];
	var filedNames = ['项目名称','项目阶段'];
//	var fields = ['short_name','stage','decisioner','operator','est_begintime','est_endtime','act_begintime','act_endtime',
//	              'business_leader_name','presale_leader_name','deliver_leader_name','est_money','crm_project_desc'];
//	var filedNames = ['项目名称','项目阶段','项目经办人','项目决策人','预计开始日期','预计结束日期','实际开始日期','实际结束日期','商务负责人','售前负责人',
//	                  '项目负责人','预计金额','项目描述'];
	var len = fields.length;
	for (var i = 0; i< len; i++)
	{
		$("#pro_" + fields[i]).val("");
	}
	
	$("#pro_dec").css("display", "none");
	
	layer.open({
	  title: '请填写项目相关信息'
	  ,type: 1
	  ,content: $('#addproj')
	  ,area:['750px','425px']
	  ,offset:10
	  ,shade:0.1
	  ,shadeClose:false
	  ,btn: ['保存', '取消']
	  ,yes: function(index, layero){
		//按钮【按钮一】的回调
		var shortName = document.getElementById('pro_short_name').value;
		var stage = document.getElementById('pro_stage').value;
		var decisioner = document.getElementById('pro_decisioner').value;
		var operator = document.getElementById('pro_operator').value;
		var estBegintime = document.getElementById('pro_est_begintime').value;
		var estEndtime = document.getElementById('pro_est_endtime').value;
		var actBegintime = document.getElementById('pro_act_begintime').value;
		var actEndtime = document.getElementById('pro_act_endtime').value;
		var businessLeaderNo = document.getElementById('pro_business_leader_name').value;
		var businessLeaderName = $('#pro_business_leader_name option:selected').text();
		var presaleLeaderNo = document.getElementById('pro_presale_leader_name').value;
		var presaleLeaderName = $('#pro_presale_leader_name option:selected').text();
		var deliverLeaderNo = document.getElementById('pro_deliver_leader_name').value;
		var deliverLeaderName = $('#pro_deliver_leader_name option:selected').text();
		var estMoney = document.getElementById('pro_est_money').value;
		var crmProjectDesc = document.getElementById('pro_crm_project_desc').value;
		
		for (var i = 0; i< len; i++)
		{
			if ($("#pro_" + fields[i]).val() == "")
			{
				alert(filedNames[i] + "不能为空");
				return false;
			}
		}
		$.ajax({
			type     : "POST",
			url      : "../../base/project/create",
			dataType : "json",
			data     : {"cid":treeId, "shortName":shortName, "stage":stage, "decisioner":decisioner, "operator":operator,
				"estBegintime":estBegintime, "estEndtime":estEndtime, "actBegintime":actBegintime,"actEndtime":actEndtime, 
				"businessLeaderNo":businessLeaderNo, "businessLeaderName":businessLeaderName, "presaleLeaderNo":presaleLeaderNo,
				"presaleLeaderName":presaleLeaderName,"deliverLeaderNo":deliverLeaderNo, "deliverLeaderName":deliverLeaderName, 
				"estMoney":estMoney, "crmProjectDesc":crmProjectDesc},
			success  : function(data) {
				layer.msg('新增成功');
				showproject(treeId);
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

//删除项目
function delProject()
{
	id = check_val[0];
	if (id == null) {
		alert('请选择一个项目');
	} else {
		$.ajax({
			type     : 'GET',
			url      : '../../base/delProject/' + id,
			dataType : 'json',
			async    : false,
			success  : function(result) {
				layer.msg('删除成功');
				showproject(treeId);
				check_val = [];
			}, error : function(err) {
				alert('删除失败，请联系管理员');
			}
		});
	}
}

//修改项目
function modproj() {
	id = check_val[0];
	if (id == null) {
		alert('请选择一个项目');
	} else {
		findProjectById(id);
		
		var fields = ['short_name','stage'];
		var filedNames = ['项目名称','项目阶段'];
//		var fields = ['short_name','stage','decisioner','operator','est_begintime','est_endtime','act_begintime','act_endtime',
//		              'business_leader_name','presale_leader_name','deliver_leader_name','est_money','crm_project_desc'];
//		var filedNames = ['项目名称','项目阶段','项目经办人','项目决策人','预计开始日期','预计结束日期','实际开始日期','实际结束日期','商务负责人','售前负责人',
//		                  '项目负责人','预计金额','项目描述'];
		var len = fields.length;
		layer.open({
			  title: '请填写项目相关信息'
			  ,type: 1
			  ,content: $('#addproj')
			  ,area:['750px','425px']
			  ,offset:10
			  ,shade:false
			  ,shadeClose:false
			  ,btn: ['修改', '取消']
			  ,yes: function(index, layero){
				var shortName = document.getElementById('pro_short_name').value;
				var stage = document.getElementById('pro_stage').value;
				var decisioner = document.getElementById('pro_decisioner').value;
				var operator = document.getElementById('pro_operator').value;
				var estBegintime = document.getElementById('pro_est_begintime').value;
				var estEndtime = document.getElementById('pro_est_endtime').value;
				var actBegintime = document.getElementById('pro_act_begintime').value;
				var actEndtime = document.getElementById('pro_act_endtime').value;
				var businessLeaderNo = document.getElementById('pro_business_leader_name').value;
				var businessLeaderName = $('#pro_business_leader_name option:selected').text();
				var presaleLeaderNo = document.getElementById('pro_presale_leader_name').value;
				var presaleLeaderName = $('#pro_presale_leader_name option:selected').text();
				var deliverLeaderNo = document.getElementById('pro_deliver_leader_name').value;
				var deliverLeaderName = $('#pro_deliver_leader_name option:selected').text();
				var estMoney = document.getElementById('pro_est_money').value;
				var crmProjectDesc = document.getElementById('pro_crm_project_desc').value;
				for (var i = 0; i< len; i++)
				{
					if ($("#pro_" + fields[i]).val() == "")
					{
						alert(filedNames[i] + "不能为空");
						return false;
					}
				}
			  	$.ajax({
					type     : "POST",
					url      : "../../base/project/update",
					dataType : "json",
					data     : {"pid":id, "shortName":shortName, "stage":stage, "decisioner":decisioner, "operator":operator,
						"estBegintime":estBegintime, "estEndtime":estEndtime, "actBegintime":actBegintime,"actEndtime":actEndtime, 
						"businessLeaderNo":businessLeaderNo, "businessLeaderName":businessLeaderName, "presaleLeaderNo":presaleLeaderNo, 
						"presaleLeaderName":presaleLeaderName, "deliverLeaderNo":deliverLeaderNo,"deliverLeaderName":deliverLeaderName, 
						"estMoney":estMoney, "crmProjectDesc":crmProjectDesc},
					success  : function(data) {
						if (data == 1) {
							layer.msg('修改成功');
							showproject(treeId);
							check_val = [];
						} else {
							alert('修改失败，请重试');
						}
					}, 
					error    : function() {
						alert('修改失败，请重试');
					}
				});
				  
			  	layer.close(index);
			  }
			  ,cancel: function(index, layero){
				layer.close(index);
			  }});
	}
}

//修改单条项目数据回现
function findProjectById(id) {
	$.ajax({
		type     : "GET",
		url      : "../../base/findProjectById/" + id,
		dataType : "json",
		success  : function(result) {
			$('#pro_short_name').val(result.shortName);
			$('#pro_stage').val(result.stage);
			$('#pro_decisioner').val(result.decisioner);
			$('#pro_operator').val(result.operator);
			$('#pro_est_begintime').val(result.estBegintime);
			$('#pro_est_endtime').val(result.estEndtime);
			$('#pro_act_begintime').val(result.actBegintime);
			$('#pro_act_endtime').val(result.actEndtime);
			$('#pro_business_leader_name').val(result.businessLeaderNo);
			$('#pro_presale_leader_name').val(result.presaleLeaderNo);
			$('#pro_deliver_leader_name').val(result.deliverLeaderNo);
			$('#pro_est_money').val(result.estMoney);
			$('#pro_crm_project_desc').val(result.crmProjectDesc);
		}, 
		error    : function(err) {
			alert('请选择一个项目列表复选框，' + err);
		}
	});
}