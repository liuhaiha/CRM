var layer;
layui.use([ 'form', 'layedit', 'laydate' ],
				function() {
					var form = layui.form(), layer = layui.layer, layedit = layui.layedit, laydate = layui.laydate;

					// 监听提交
					form.on('submit(save)', function(data) {
						layer.msg(JSON.stringify(data.field));
						return false;
					});
});

//var apply;
//$(function getApplyDetail() {
//	$.ajax({
//				url : '/crm/sale/getApplyDetail',
//				method : 'post',
//				dataType : 'json',
//				async : true,
//				success : function(list) {
//					if (list && list.length > 0)
//					{
//						apply = list[0];
//						$("#customerName").val(replaceNull(apply.cname));
//						$("#customerArea").val(replaceNull(apply.region));
//						$("#customerAddr").val(replaceNull(apply.addr));
//						$("#customerProv").val(replaceNull(apply.province));
//						$("#customerSource").val(replaceNull(apply.source));
//						$("#customerCategory").val(replaceNull(apply.unitType));
//						$("#itemStatus").val(replaceNull(apply.extend1));
//						if (apply.status == "0") {
//							$("#itemName").css("display", "none");
//						} else {
//							$("#itemName").val(replaceNull(apply.pname));
//							$("#itemName").css("display", "");
//						}
//						$("#itemStage").val(replaceNull(apply.stage));
//						$("#itemMoney").val(replaceNull(apply.money));
//						$("#itemEstInvite").val(replaceNull(apply.estInvite).substr(0,10));
//						$("#itemPeriod").val(replaceNull(apply.period));
//						$("#cooperOther").val(replaceNull(apply.cooperOther));
//						$("#winRole").val(replaceNull(apply.winRole));
//						$("#hardware").val(replaceNull(apply.hardware));
//						$("#technology").val(replaceNull(apply.technology));
//						$("#cloud").val(replaceNull(apply.cloud));
//						$("#bigdata").val(replaceNull(apply.bigdata));
//						$("#extTime").val(replaceNull(apply.extTime));
//						$("#extAddr").val(replaceNull(apply.extAddr));
//						$("#apprMan").val(replaceNull(apply.applyName));
//						$("#aid").val(replaceNull(apply.aid));
//						
//						selectExtImport();
//						
//						selectExtTheme();
//					}
//					
//					if (list && list.length > 1)
//					{
//						var cusList = list[1];
//						if (!cusList)
//						{
//							return;
//						}
//						
//						for (var i = 0; i < cusList.length;i++)
//						{
//							$("#getLinkMan").append(addTCrmExtCust(cusList[i]));
//						}
//					}
//				},
//				error : function(message) {
//
//				}
//			});
//});

var apply;
$(function getApplyDetail() {
	$.ajax({
				url : '/crm/sale/getApplyDetail',
				method : 'post',
				dataType : 'json',
				async : true,
				success : function(list) {
					if (list && list.length > 0)
					{
						apply = list[0];
						$("#customerName").val(replaceNull(apply.cname));
						$("#customerArea").val(replaceNull(apply.region));
						$("#customerAddr").val(replaceNull(apply.addr));
						$("#customerProv").val(replaceNull(apply.province));
						$("#customerSource").val(replaceNull(apply.source));
						$("#customerCategory").val(replaceNull(apply.unitType));
						$("#extTime").val(replaceNull(apply.extTime));
						$("#apprMan").val(replaceNull(apply.applyName));
						$("#extAddr").val(replaceNull(apply.extAddr));
						$("#extTheme").val(replaceNull(apply.extTheme));
						$("#extCus").val(replaceNull(apply.extCus));
						$("#extThird").val(replaceNull(apply.extThird));
						$("#extOwner").val(replaceNull(apply.extend2));
						$("#applyState").val(replaceNull(apply.applyState));
						$("#aid").val(replaceNull(apply.aid));
						$("#approvalOpinion").val(replaceNull(apply.approvalOpinion));
						showExtOwner();
						if(apply.pid != "" && apply.pid !=null){
							$.ajax({
								url:'/crm/sale/getProDetail',
								type:'post',
								data:{"pid":apply.pid},
								dataType:'json',
								async:true,
								success:function(result)
								{
									if (result && result.code == "1")
									{
										proData = result.data;
										$("#shortName").val(proData.shortName);
										$("#fullName").val(proData.fullName);
										$("#stage").val(proData.stage);
										$("#decisioner").val(proData.decisioner);
										$("#operator").val(proData.operator);
									}
									else
									{
									}
								},
								error:function(){
									alert("系统错误，请联系系统管理员");
								}
							});
						}else{
							$("#extProDetail").css('display','none')
						}
					}
				},
				error : function(message) {

				}
			});
});

function replaceNull(value)
{
	if (value == undefined)
	{
		value = "";
	}
	return value;
}

var emplist;
$(function(){
	$.ajax({
		url : '/crm/sale/apprMan',
		method : 'get',
		dataType : 'json',
		async : true,
		success : function(allData) {
			emplist = allData;
			var empHtml = "<tr>";
			for (i in allData) {
				empHtml += "<td style='width:28%;height:30px;font-size:14px;'><input type='checkbox' style='margin-left:5px;' name='ManList' value = '"+ allData[i].eno + "'/>" + allData[i].ename+ "</td>";
				if ((parseInt(i) + 1) % 4 == 0) {
					empHtml = empHtml + "</tr><tr>";
				}
			}
			empHtml +="</tr>";
			$("#extOwnerDiv").html(empHtml);
			showExtOwner();
		},
		error : function(message) {}
	});
});

function showExtOwner()
{
	if (emplist && apply)
	{
		var extOwner = apply.extOwner;
		if (!extOwner || emplist.length == 0 || extOwner == "")
		{
			return;
		}
		
		var extOwners = extOwner.split(",");
		var showExtOwner = "";
		for (i in extOwners) 
		{
			var owner = extOwners[i];
			$("input[name='ManList']").each(function(){
				if (owner == $(this).val())
				{
					$(this).attr("checked",true);
					showExtOwner += this.nextSibling.nodeValue + ",";
				}
			});
		}
		showExtOwner=showExtOwner.replace(/,$/,"");
		$("#extOwnerText").val(showExtOwner);
	}
}

$("#extOwnerText").click(function(){
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
			$("#extOwnerText").val(strMan);
			$("#extOwnerText").html(strManNo);
			layer.close(index);
		},
		cancel:function(index,layero){
			layer.close(index);
		}
	});
});


function approval(apprStatus)
{
	var extOwner = "";
	$("input[name='ManList']:checked").each(function(){
		extOwner += $(this).val() + ",";
	});
	extOwner = extOwner.replace(/,$/,"");
	
	if (apprStatus == "1" && extOwner == "")
	{
		layer.msg("请选择参与人");
		return false;
	}
	
	layer.open({
		type:1,
		title:'请填写批阅意见',
		area:['300px','200px'],
		shade:0.1,
		shadeClose:false,
		content : $('#opinionPage'),
		offset:'30%',
		btn:['确认','取消'],
		yes:function(index, layero){
			var approvalOpinion =$("#approvalOpinion").val();
			if(approvalOpinion == undefined || approvalOpinion.length == 0){
				layer.msg("请填写审批意见",{offset:'40%'});
				return false;
			}
			else
			{
				$.ajax({
					url : '/crm/sale/approval',
					dataType : 'json',
					data:{"aid":$("#aid").val(),"apprStatus":apprStatus,"extOwner":extOwner},
					type: 'POST',
					success : function(result) {
						if (result && result.code == "1")
						{
							location.href= "../view/personal/desk/workdesk.html";
						}
						else
						{
							alert('系统错误,请联系系统管理员');
						}
					},
					error : function(message) {
						 alert('系统错误,请联系系统管理员');
					}
				});
				}
			layer.close(index);
		},
		cancel:function(index, layero){
			layer.close(index);
		}
	});
}
