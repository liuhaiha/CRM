var pageNumber = parseInt($("#pageNumber").text() == ""?"1":$("#pageNumber").text());
var pageSize = $("#pageSize").val() == undefined?"10":$("#pageSize").val();
var totalPage = 0;
	var tableList = {
		init : function(fun) {
			this.menu=fun;
			this.bindEvent();
		},
		bindEvent : function() {
			//首页
			$('#code_content').on('click','.laypage_first',this.fistPage);
			//上一页
			$('#code_content').on('click','.layui-laypage-prev',this.prePage);
			//下一页
			$('#code_content').on('click','.layui-laypage-next',this.nextPage);
			//最后一页
			$('#code_content').on('click','.layui-laypage-last',this.lastPage);
			//编辑
			$('#code_content').on('click','.edit-code',this.edit);
			//删除
			$('#code_content').on('click','.delete-code',this.deleteCode);
			//增加
			$("#addCode").on("click", this.addCode);
			
			
		},
		fistPage : function() {
			if($('#pageNo').val() == 1){
				layer.msg("已经是第一页了");
			}else{
				tableList.queryDic(1);
				layer.msg('第一页');
			}
		},
		prePage : function() {
			if($('#pageNo').val() == 1){
				layer.msg("已经是第一页了");
			}else{
				tableList.queryDic(Number($(
					'#pageNo').val()) - 1);
				layer.msg('第'+$('#pageNo').val()+'页');
			}
		},
		nextPage : function() {
			if($('#pageNo').val() >= $('#totalPage').val()){
				layer.msg("已经是最后一页了");
			}else{
				 tableList.queryDic(Number($('#pageNo').val()) + 1);
				 layer.msg('第'+$('#pageNo').val()+'页');
			}
		},
		lastPage : function() {
			if($('#pageNo').val() >= $('#totalPage').val()){
				layer.msg("已经是最后一页了");
			}else{
				tableList.queryDic($('#totalPage').val());
				layer.msg('最后一页');
			}
		},
		queryDic : function(pageNo) {
			pageNo==null ? $('#pageNo').val():pageNo;
			$.ajax({
						async : false,
						type : 'GET',
						data:{'pageNo':pageNumber, 'pageSize':pageSize},
						dataType:'json',
						url : '/crm/sys/getCode/' + $('#cid').val(),
						success : function(json) {
							totalPage = json.totalPage;
							var data = json.results;
							var str = "";
							if(data!=null){
								for (var i = 0; i < data.length; i++) {
									str += '<tr style="font-size:14px;height:35px;">';
									str += '<th style="font-size:14px;">'
											+ (i+1) +'</th>';
									str += '<th style="font-size:14px;">'
											+ tableList.checkNull(data[i].name) + '</th>';
									str += '<th style="font-size:14px;display:none">'
										+ tableList.checkNull(data[i].sequence) + '</th>';
									str += '<th style="font-size:14px;">'
											+ tableList.checkNull(data[i].code) + '</th>';
									str += '<th style="font-size:14px;">'
											+ tableList.checkNull(data[i].creator) + '</th>';
									str += '<th style="font-size:14px;">'
											+ tableList.checkNull(data[i].createtime) + '</th>';
									str += '<th style="font-size:14px;">'
											+ tableList.checkNull(data[i].modifier) + '</th>';
									str += '<th style="font-size:14px;">'
											+ tableList.checkNull(data[i].modifytime) + '</th>';
									str += '<th style="font-size:14px;">'
									if(tableList.checkNull(data[i].type=='1')){
										str	+='<button data-id="'+tableList.checkNull(data[i].cid)+'" class="edit-code layui-btn layui-btn-small layui-btn-radius layui-btn-danger">编辑</button>';
										str	+='<button data-id="'+tableList.checkNull(data[i].cid)+'" class="delete-code layui-btn layui-btn-small layui-btn-radius">删除</button>';	
									}
									str+='</th>';
									str += '</tr>';
								}
							}
//							str += '</tbody>';
							$("#tableList").html(str);
							$("#tableList").delegate("tr","mouseenter",function(){
				    			$(this).css("background-color","#FDE5B2")
				        		$(this).siblings().css("background-color","#fff")
				    			});
//							str += '<tfoot><tr><td colspan="8">'
//									+ '<div class="layui-box layui-laypage layui-laypage-default"'
//									+ 'id="layui-laypage-0" style="text-align:center;width:90%;">'
//									+ '<a id="fistPage" href="#" class="laypage_first">第一页</a>'
//									+ '<a id="prePage"href="#" class="layui-laypage-prev">上一页</a>'
//									+ '<a id="nextPage"href="#" class="layui-laypage-next">下一页</a>'
//									+ '<a id="lastPage"href="#" class="layui-laypage-last">最后页</a>'
//									+ '</div></td></tr></tfoot>';
//							$('#code_content').html(str);
//							$('#pageNo').val(json.pageNo);
//							$('#totalPage').val(json.totalPage);
							
							loadpageList(json.totalSize);
							$("#totalSizePro").html(json.totalSize);
						    $("#totalPagePro").html(json.totalPage);
						}
					});
		},
		addCode : function() {
			layui.use('layer', function() {
				layer = layui.layer;
				$('#menuName').val('');
				$('#codeName').val('');
				layer.open({
					title : '添加菜单',
					type : 1,
					content : $('#addmenulist'),
					shade : 0.2,
					shadeClose : false,
					btn : [ '新增', '取消' ],
					btnAlign:'c',
					yes : function(index, layero) {
						if($('#menuName').val()==''){
							layer.msg('代码名称不能为空');
							return;
						}
						if($('#codeName').val()==''){
							layer.msg('代码不能为空');
							return;
						}
						var node = JSON.parse($('#clicked_menu').val());
						node.spread=undefined;
						delete node.spread;
						node.name=$('#menuName').val();
						node.code=$('#codeName').val();
						// 按钮【按钮一】的回调
						$.ajax({
							async : false,
							type : 'POST',
							data :node,
							url : '/crm/sys/addCodeMenu',
							success : function(data) {
								if(data.code=="-1")
								{
									layer.msg("代码'" + node.code + "'已经存在，请重新输入");
								}
								else if(data.code=="200")
								{
									layer.msg("添加字典成功");
									layer.close(index); // 如果设定了yes回调，需进行手工关闭
									tableList.queryDic();
									tableList.menu.getmenu();
								}
								else
								{
									layer.msg(data.message);
								}
							},
							error : function(xhr, e, exception) {
								layer.msg("添加字典失败");
							}
						});
						

					},
					cancel : function(index, layero) {
						// 右上角关闭回调

						// return false 开启该代码可禁止点击该按钮关闭
						layer.close(index); // 如果设定了yes回调，需进行手工关闭
					}
				});
			});
		},
		edit:function(){
			var sib=$(this).parent().siblings();
			$('#editName').val(sib.eq(1).text());
			$('#editSequence').val(sib.eq(2).text());
			$('#editCode').val(sib.eq(3).text());
			cid=$(this).attr('data-id');
			layer = layui.layer;
			layer.open({
				title : '修改字典',
				type : 1,
				content : $('#editDiv'),
				shade : false,
				shadeClose : false,
				btn : [ '确定', '取消' ],
				btnAlign:'c',
				yes : function(index, layero) {
					if($('#editName').val()==''){
						layer.msg('代码名称不能为空');
						return;
					}
					name=$('#editName').val();
					code=$('#editCode').val();
					sequence=$('#editSequence').val();
					// 按钮【按钮一】的回调
					$.ajax({
						async : false,
						type : 'POST',
						data :{"cid":cid,"name":name,"code":code,"sequence":sequence},
						url : '/crm/sys/updateCodeMenu',
						success : function(data) {
							console.log(data);
							if(data.code=="-1")
							{
								layer.msg("代码'" + code.code + "'已经存在，请重新输入");
							}
							else if(data.code=="200")
							{
								layer.msg("修改成功");
								layer.close(index); // 如果设定了yes回调，需进行手工关闭
								tableList.queryDic();
								tableList.menu.getmenu();
							}
							else
							{
								layer.msg(data.message);
							}
						},
						error : function(xhr, e, exception) {
							layer.msg("修改失败");
						}
					});
				},
				cancel : function(index, layero) {
					// 右上角关闭回调

					// return false 开启该代码可禁止点击该按钮关闭
					layer.close(index); // 如果设定了yes回调，需进行手工关闭
				}
			});
		},
		deleteCode : function() {
			cid=$(this).attr('data-id');
			layui.use('layer', function() {
				layer = layui.layer;
				layer.confirm('确认删除?', {
					title : '删除',
					btnAlign:'c',
					btn : [ '确认', '取消' ],
					btn1 : function(index, layero) {
						$
								.ajax({
									async : true,
									type : 'DELETE',
									url : '/crm/sys/deleteCodeMenu/'
											+ cid,
									success : function(data) {
										if(data.code!="200"){
											layer.msg(data.message);
										}else{
											layer.msg('删除成功');
											tableList.queryDic();
											tableList.menu.getmenu();
										}
									},
									error : function() {
										layer.msg('删除失败');
									}
								});
						layer.close(index);
					}
				})
			});
		},
		checkNull:function(obj){
			if(obj==undefined || obj==null || obj.toLowerCase=='null')
				return '';
			else
				return obj;
		}
	};

//跳转到首页和末页
function topagepro(topge) {
	if( $("#totalPagePro").text()==0 ){
		return;
	}
	if (topge == "tofirst") {
		$("#pageNumberPro").html(1);
		pageNumber = 1;
	} else if (topge == "tolast") {
		$("#pageNumberPro").html(parseInt($("#totalPagePro").text()));
		pageNumber = totalPage;
	}
	
	tableList.queryDic();
}

//上页 下页点击事件
function changePagepro(obj) {
	if( $("#totalPagePro").text()==0 ){
		return;
	}
	var pageNumberPro = parseInt($("#pageNumberPro").text());
	var totalPagePro = parseInt($("#totalPagePro").text());
	if (obj == "next") {
		if (pageNumberPro < totalPagePro) {
			pageNumberPro = parseInt(pageNumberPro) + 1;
		} else if (pageNumberPro < totalPagePro) {
			pageNumberPro = 1;
		}
	} else if (obj == "last") {
		if (pageNumberPro <= totalPagePro && pageNumberPro > 1) {
			pageNumberPro = (pageNumberPro - 1);
		}
	}
	$("#pageNumberPro").text(pageNumberPro);
	pageNumber = pageNumberPro;
	tableList.queryDic();
}

//跳转到某一页
function tpagePro() {
	var tpnum = $("#jpageNumberPro option:selected").text();
	$("#pageNumberPro").text(tpnum);
	pageNumber = tpnum;
	tableList.queryDic();
}

//每页显示数
function jpagePro() {
	pageSize = $("#pageSizePro").val();
	tableList.queryDic();
}