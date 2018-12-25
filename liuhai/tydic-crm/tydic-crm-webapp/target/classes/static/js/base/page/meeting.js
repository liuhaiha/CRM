//增加客户联系人
function addMeeting()
{
	var pid = check_val[0];
	var fields = ['theme','mtime','maddr'];
	var filedNames = ['会议主题','会议时间','会议地点'];
//	var fields = ['theme','mtime','maddr','peer','customers','thirdMan','recordname','process','summary','next_plan'];
//	var filedNames = ['会议主题','会议时间','会议地点','参与人','客户方','第三方','纪要汇总','交流过程','会议总结','下一步计划'];
	
	var leng = fields.length;
	for (var i = 0; i< len; i++)
	{
		$("#link_" + fields[i]).val("");
	}
	$("#link_birthday").val("");
	$("#link_hobby").val("");
	$("#link_remark").val("");
	
	layer.open({
	  title: '请填写沟通纪要内容：'
	  ,type: 1
	  ,content: $('#txtDiv')
	  ,area:['650px','620px']
	  ,offset:10
	  ,shade:0.1
	  ,shadeClose:false
	  ,btn: ['保存', '取消']
	  ,yes: function(index, layero){
		  
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
				data     : {"cid":treeId, "pid":pid, "theme":theme, "mtime":mtime, "maddr":maddr, "peer":peer, "customers":customers, 
							"thirdman":thirdMan, "recordname":recordname, "process":process, "summary":summary, "nextPlan":next_plan},
				dataType : "json",
				success  : function(result) {
					if (result == 1) {
						//findMeetingByProId(treeId, pid);
						showMeetingList(treeId, pid);
						
						for (var i = 0; i < leng; i++) {
							$("#meeting_" + fields[i]).val('');
						}
						
//						for (var i = 0; i<checkManArray.length; i++) {
//							$("input[name=ManList][value='" + checkManArray[i].value +"']").prop("checked", false);
//						}
					}
				},
				error    : function(err) {
					alert("系统错误，请联系管理员");
				}
			});
		layer.close(index); //如果设定了yes回调，需进行手工关闭
	  }
	  ,cancel: function(index, layero){ 
		layer.close(index); //如果设定了yes回调，需进行手工关闭
	  }});
}

var check_meeting_val = [];
var mLen = 0;

function checkMeeting(val) {
	
	if (val.checked) {
		check_meeting_val.push(val.value);
		mLen = check_meeting_val.length;
		
		if (mLen > 1) {
			$("input[name=meeting][value='" + check_meeting_val[0] +"']").prop("checked", false);
			var index = check_meeting_val.indexOf(check_meeting_val[0]);
			check_meeting_val.splice(index, 1);
			mLen--;
		}
	} else {
		var index = check_meeting_val.indexOf(val.value);
		check_meeting_val.splice(index, 1);
		mLen--;
	}
	return check_meeting_val;
}

//
function modMeeting()
{
	var id = check_meeting_val[0];
	if (id == null) {
		alert('请选择一个会议纪要');
	} else {
		findMeetingById(id);
		
		var pid = check_val[0];
		var fields = ['theme','mtime','maddr'];
		var filedNames = ['会议主题','会议时间','会议地点'];
//		var fields = ['theme','mtime','maddr','peer','customers','thirdMan','recordname','process','summary','next_plan'];
//		var filedNames = ['会议主题','会议时间','会议地点','参与人','客户方','第三方','纪要汇总','交流过程','会议总结','下一步计划'];
		
		var leng = fields.length;
		for (var i = 0; i< len; i++)
		{
			$("#link_" + fields[i]).val("");
		}
		$("#link_birthday").val("");
		$("#link_hobby").val("");
		$("#link_remark").val("");
		
		layer.open({
		  title: '请填写沟通纪要内容：'
		  ,type: 1
		  ,content: $('#txtDiv')
		  ,area:['650px','620px']
		  ,offset:10
		  ,shade:0.1
		  ,shadeClose:false
		  ,btn: ['保存', '取消']
		  ,yes: function(index, layero){
			  
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
					url      : "../../track/updateMeetingRecord",
					data     : {"id":id,"cid":treeId, "pid":pid, "theme":theme, "mtime":mtime, "maddr":maddr, "peer":peer, "customers":customers, 
								"thirdman":thirdMan, "recordname":recordname, "process":process, "summary":summary, "nextPlan":next_plan},
					dataType : "json",
					success  : function(result) {
						if (result == 1) {
							layer.msg('保存成功');
							showMeetingList(treeId, pid);
							
							for (var i = 0; i < leng; i++) {
								$("#meeting_" + fields[i]).val('');
							}
							
//							for (var i = 0; i<checkManArray.length; i++) {
//								$("input[name=ManList][value='" + checkManArray[i].value +"']").prop("checked", false);
//							}
						} else {
							layer.msg('修改失败，请重试');
						}
					},
					error    : function(err) {
						alert("系统错误，请联系管理员");
					}
				});
			layer.close(index); //如果设定了yes回调，需进行手工关闭
		  }
		  ,cancel: function(index, layero){ 
			layer.close(index); //如果设定了yes回调，需进行手工关闭
		  }});
	}
}

function findMeetingById(id) {
	$.ajax({
		type        : "GET",
		url         : "../../track/getMeeting/" + id,
		dataType    : "json",
		success     : function(result) {
			if (result)
			{
				$("#meeting_theme").val(result.theme);
				$("#meeting_mtime").val(result.mtime);
				$("#meeting_maddr").val(result.maddr);
				$("#meeting_peer").val(result.peer);
				$("#meeting_peer").attr("title", result.peer);
				$("#meeting_customers").val(result.customers);
				$("#meeting_thirdMan").val(result.thirdman);
				$("#meeting_recordname").val(result.recordname);
				$("#meeting_process").val(result.process);
				$("#meeting_summary").val(result.summary);
				$("#meeting_next_plan").val(result.nextPlan);
				if (null != result.fileno) {
					$('#docDiv object').attr('data','/crm/saleFile/preview/' + result.fileno);
				} else {
					$('#docDiv object').attr('data','');
				}
			}
		}
	});
}

//删除会议纪要
function delMeeting()
{
	var id = check_meeting_val[0];
	if (id == null) {
		alert('请选择一个会议纪要');
	} else {
		$.ajax({
			type     : 'GET',
			url      : '../../track/delMeeting/' + id,
			dataType : 'json',
			async    : false,
			success  : function(result) {
				layer.msg("删除成功");
				showMeetingList(treeId, 0);
				check_meeting_val = [];
			}, error : function(err) {
				alert('删除失败，请联系管理员');
			}
		});
	}
}